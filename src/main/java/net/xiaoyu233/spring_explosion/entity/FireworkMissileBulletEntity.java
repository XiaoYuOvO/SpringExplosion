package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.util.EntityUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class FireworkMissileBulletEntity extends FireworkBulletEntity{
    private Optional<UUID> targetEntityUuid = Optional.empty();
    private Optional<Entity>  targetEntityCache = Optional.empty();
    private boolean chasingTarget = false;
    private float lerpProgress = 0;
    private double targetHeight;
    @Nullable
    private Vec2f targetRotation;
    private final Vec2f from = new Vec2f(0, -90);

    public FireworkMissileBulletEntity(EntityType<? extends OwnedGeoEntity> entityType, World world) {
        super(entityType, world);
    }

    public Optional<Entity> getTargetEntity() {
        if (this.targetEntityCache.isPresent() && !this.targetEntityCache.get().isRemoved()) {
            return this.targetEntityCache;
        } else if (this.targetEntityUuid.isPresent() && this.getWorld() instanceof ServerWorld) {
            this.targetEntityCache = Optional.ofNullable(((ServerWorld) this.getWorld()).getEntity(this.targetEntityUuid.get()));
            return this.targetEntityCache;
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        chasingTarget = nbt.getBoolean("Chasing");
        lerpProgress = nbt.getFloat("LerpProgress");
        targetHeight = nbt.getDouble("TargetHeight");
        if (nbt.containsUuid("Target")) {
            targetEntityUuid = Optional.of(nbt.getUuid("Target"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Chasing", chasingTarget);
        nbt.putFloat("LerpProgress", lerpProgress);
        nbt.putDouble("TargetHeight", targetHeight);
        targetEntityUuid.ifPresent(uuid -> nbt.putUuid("Target", uuid));
        targetEntityCache.ifPresent(uuid -> nbt.putUuid("Target", uuid.getUuid()));
    }

    public void setTargetHeight(double height){
        targetHeight = height;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Vec3d pos = entity.getBoundingBox().raycast(this.getPos(), entity.getPos()).orElse(entityHitResult.getPos());
        this.setPos(pos.x,pos.y,pos.z);
        super.serversideExplodeAndUpdateClient();
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        Vec3d pos = blockHitResult.getPos();
        this.setPos(pos.x,pos.y,pos.z);
        serversideExplodeAndUpdateClient();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient){
            if (this.chasingTarget){
                this.getTargetEntity().ifPresent(target -> this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, target.getPos()));
            }else if (this.lerpProgress < 1){
                if (this.getY() > this.targetHeight - 2){
                    this.getTargetEntity().ifPresent(target -> {
                        if (targetRotation == null) this.targetRotation = calcTargetAngle(target.getPos());
                        lerpProgress += 0.1f;
                        Vec2f vec2f = lerpAngle(lerpProgress, from, targetRotation);
                        this.setYaw(vec2f.x);
                        this.setPitch(vec2f.y);
                    });
                }else {
                    this.setRotation(0,-90);
                }
            }else if (this.targetEntityUuid.isPresent()){

                this.getTargetEntity().ifPresent(target -> {
                    this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, target.getPos());
                });
                this.chasingTarget = true;
            }
        }
    }

    public void setVelocityClient(double x, double y, double z) {
        this.setVelocity(x, y, z);
    }

    private Vec2f lerpAngle(float progress, Vec2f from, Vec2f target){
        return new Vec2f(MathHelper.lerp(progress, from.x, target.x), MathHelper.lerp(progress, from.y, target.y));
    }

    private Vec2f calcTargetAngle(Vec3d target){
        Vec3d vec3d = EntityAnchorArgumentType.EntityAnchor.EYES.positionAt(this);
        double d = target.x - vec3d.x;
        double e = target.y - vec3d.y;
        double f = target.z - vec3d.z;
        double g = Math.sqrt(d * d + f * f);
        return new Vec2f(MathHelper.wrapDegrees((float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F), MathHelper.wrapDegrees((float)(-(MathHelper.atan2(e, g) * 57.2957763671875))));
    }

    public void setTarget(Entity entity){
        this.targetEntityUuid = Optional.of(entity.getUuid());
        this.targetEntityCache = Optional.of(entity);
    }

    @Override
    protected void tickAcceleration() {
        this.setVelocity(EntityUtil.getRotationVector(this.getPitch(), this.getYaw()));
    }

}

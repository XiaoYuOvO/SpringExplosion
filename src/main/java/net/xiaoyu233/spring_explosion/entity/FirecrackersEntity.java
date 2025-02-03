package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FirecrackersEntity extends OwnedGeoEntity implements IFireworkEntity{
    @Nullable
    private FirecrackersEntity lastFirecracker;
    private UUID lastFirecrackerUUID;
    @Nullable
    private FirecrackersEntity nextFirecracker;
    private UUID nextFirecrackerUUID;
    private boolean explodeNextTick = false;
    private int explodeInterval = 3;
    private int life = 120 * 20;
    public FirecrackersEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void setLastFirecracker(@NotNull FirecrackersEntity lastFirecracker) {
        this.lastFirecracker = lastFirecracker;
        this.lastFirecrackerUUID = lastFirecracker.getUuid();
    }

    public void setNextFirecracker(@NotNull FirecrackersEntity nextFirecracker) {
        this.nextFirecracker = nextFirecracker;
        this.nextFirecrackerUUID = nextFirecracker.getUuid();
    }

    @Override
    public void discardFirework() {
        this.discard();
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch) {
        super.playSound(sound, volume, pitch);
    }


    @Nullable
    private FirecrackersEntity getLastFirecracker(){
        if (this.getWorld().isClient || this.lastFirecrackerUUID == null) return null;
        if (this.lastFirecracker == null){
            if (((ServerWorld) this.getWorld()).getEntity(this.lastFirecrackerUUID) instanceof FirecrackersEntity firecracker) {
                this.lastFirecracker = firecracker;
            }
        }
        return this.lastFirecracker;
    }

    @Override
    public boolean isGlowing() {
        if (this.getWorld().isClient) return MinecraftClient.getInstance().player == this.getOwner();
        return super.isGlowing();
    }

    @Nullable
    private FirecrackersEntity getNextFirecracker(){
        if (this.getWorld().isClient || this.nextFirecrackerUUID == null) return null;
        if (this.nextFirecracker == null){
            if (((ServerWorld) this.getWorld()).getEntity(this.nextFirecrackerUUID) instanceof FirecrackersEntity firecracker) {
                this.nextFirecracker = firecracker;
            }
        }
        return this.nextFirecracker;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("LastFirecrackers")) this.lastFirecrackerUUID = nbt.getUuid("LastFirecrackers");
        if(nbt.contains("NextFirecrackers")) this.nextFirecrackerUUID = nbt.getUuid("NextFirecrackers");
        explodeNextTick = nbt.getBoolean("ExplodeNextTick");
        this.life = nbt.getInt("Life");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (lastFirecracker != null) nbt.putUuid("LastFirecrackers", lastFirecrackerUUID);
        if (nextFirecracker != null) nbt.putUuid("NextFirecrackers", nextFirecrackerUUID);
        nbt.putBoolean("ExplodeNextTick", explodeNextTick);
        nbt.putInt("Life",this.life);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        if (this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.5));
        }
        this.move(MovementType.SELF, this.getVelocity());
        if (!this.isOnGround()) {
            this.setVelocity(this.getVelocity().multiply(0.95));
        }

        if (!this.getWorld().isClient){
            if (explodeNextTick){
                this.explodeInterval--;
                if (explodeInterval <= 0){
                    this.explode();
                }
            }
            if (this.isSubmergedInWater()) {
                disposeOnWater();
            }
            if (this.life > 0){
                this.life--;
            }else {
                this.discard();
            }


        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {}

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        if (this.getOwner() == player) {
            this.explode();
            return ActionResult.SUCCESS;
        }
        return super.interactAt(player, hitPos, hand);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.IS_EXPLOSION) && !(source.getSource() instanceof FirecrackersEntity) && this.getOwner() != null && source.getAttacker() == this.getOwner()){
            this.explodeNextTick = true;
        }
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (this.getOwner() == player) {
            this.explode();
            return ActionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }

    public void explode(){
        World world = this.getWorld();
        if (!world.isClient){
            world.sendEntityStatus(this, EXPLODE_STATUS);
            world.createExplosion(this, this.getWorld().getDamageSources().explosion(this, this.getOwner()), new FireworkExplosionBehavior<>(this), this.getX(), this.getY(), this.getZ(), 1.2f, false, World.ExplosionSourceType.MOB, false);
            FirecrackersEntity lastFirecracker1 = getLastFirecracker();
            if (lastFirecracker1 != null) lastFirecracker1.explodeNextTick = true;
            FirecrackersEntity nextFirecracker1 = getNextFirecracker();
            if (nextFirecracker1 != null) nextFirecracker1.explodeNextTick = true;
            this.playSound(SESoundEvents.FIRECRACKER_EXPLODE, 1.5F, 0.9F + random.nextFloat() * 0.2f);
            this.discard();
        }else {
            for (int i = 0; i < 10; ++i){
                world.addImportantParticle(ParticleTypes.FLAME,true, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() / 8 ,this.random.nextGaussian() / 8 ,this.random.nextGaussian() / 8);
            }
            this.discard();
        }
    }

    @Override
    public void setOnGround(boolean onGround) {
        if (onGround) {
            this.setPitch(0);
        }
        super.setOnGround(onGround);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EXPLODE_STATUS) {
            this.explode();
        }
        super.handleStatus(status);
    }

    @Override
    public boolean isImmuneToExplosion() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void initDataTracker() {

    }

}

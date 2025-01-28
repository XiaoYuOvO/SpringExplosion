package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.fireworks.StickyBomb;
import net.xiaoyu233.spring_explosion.util.EntityUtil;
import net.xiaoyu233.spring_explosion.util.NbtUtil;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.UUID;

public class StickyBombEntity extends ProjectileFireworkEntity<StickyBombEntity, StickyBomb> {
    private static final TrackedData<Boolean> IS_STUCK = DataTracker.registerData(StickyBombEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> STUCK_ENTITY_ID = DataTracker.registerData(StickyBombEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Direction> STUCK_FACING = DataTracker.registerData(StickyBombEntity.class, TrackedDataHandlerRegistry.FACING);
    private static final TrackedData<Vector3f> ENTITY_STUCK_OFFSET = DataTracker.registerData(StickyBombEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    private Optional<Entity> stuckEntityCache = Optional.empty();
    private Optional<UUID> stuckEntityUUID = Optional.empty();
    private boolean detached = false;
    public StickyBombEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    @Override
    protected float getTrailOffset() {
        return 0;
    }

    public Optional<Entity> getStuckEntity() {
        if (this.stuckEntityCache.isPresent() && !this.stuckEntityCache.get().isRemoved()) {
            this.dataTracker.set(STUCK_ENTITY_ID,this.stuckEntityCache.get().getId());
            return this.stuckEntityCache;
        } else if (this.stuckEntityUUID.isPresent() && this.getWorld() instanceof ServerWorld) {
            this.stuckEntityCache = Optional.ofNullable(((ServerWorld) this.getWorld()).getEntity(this.stuckEntityUUID.get()));
            this.stuckEntityCache.ifPresent(e -> this.dataTracker.set(STUCK_ENTITY_ID,e.getId()));
            return this.stuckEntityCache;
        } else {
            Integer id = this.dataTracker.get(STUCK_ENTITY_ID);
            if (this.getWorld().isClient && id != 0){
                return this.stuckEntityCache = Optional.ofNullable(this.getWorld().getEntityById(id));
            }
            return Optional.empty();
        }
    }

    @Override
    public boolean isImmuneToExplosion() {
        return true;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(IS_STUCK,false);
        dataTracker.startTracking(STUCK_ENTITY_ID,0);
        dataTracker.startTracking(STUCK_FACING,Direction.DOWN);
        dataTracker.startTracking(ENTITY_STUCK_OFFSET, new Vector3f());
    }

    private boolean isStuck(){
        return dataTracker.get(IS_STUCK);
    }

    private void setStuck(Optional<Entity> stuckEntity){
        dataTracker.set(IS_STUCK, true);
        this.setVelocity(Vec3d.ZERO);
        stuckEntity.ifPresent(entity -> {
            this.stuckEntityCache = Optional.of(entity);
            this.stuckEntityUUID = Optional.of(entity.getUuid());
            dataTracker.set(STUCK_ENTITY_ID, entity.getId());
        });
    }

    @Override
    protected void onEntityStopFiring() {
        super.onEntityStopFiring();
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EXPLODE_STATUS);
            this.getWorld()
                    .createExplosion(this, this.getWorld()
                            .getDamageSources()
                            .explosion(this, this.getOwner()), new FireworkExplosionBehavior<>(this), this.getX(), this.getY(), this.getZ(), 2f, false, World.ExplosionSourceType.MOB, false);
            this.playSound(SESoundEvents.BOMB_EXPLODE, 2.0f, (float) (1.0 + this.random.nextGaussian() * 0.2f));
            this.discard();
        }
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    protected boolean shouldShowTrail() {
        return !isStuck();
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EXPLODE_STATUS){
            ParticleUtil.explodeBallRandomColor((ClientWorld) getWorld(),this.getPos(), this.random, ParticleTypes.FIREWORK, 0.3, 3, true);
            this.discard();
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.getBoolean("Stuck")) {
            if (nbt.contains("StuckEntity")){
                this.setStuck(Optional.empty());
                this.stuckEntityUUID = Optional.of(nbt.getUuid("StuckEntity"));
                this.dataTracker.set(ENTITY_STUCK_OFFSET, NbtUtil.readVector3f(nbt,"StuckOffset"));
            }else {
                this.setStuck(Optional.empty());
            }
            this.dataTracker.set(STUCK_FACING, NbtUtil.readDirection(nbt,"StuckFacing"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (isStuck()){
            nbt.putBoolean("Stuck", true);
            this.stuckEntityUUID.ifPresent(uuid -> {
                nbt.putUuid("StuckEntity", uuid);
                NbtUtil.writeVector3f(nbt,"StuckOffset",this.dataTracker.get(ENTITY_STUCK_OFFSET));
            });
            NbtUtil.writeDirection(nbt, "StuckFacing", this.dataTracker.get(STUCK_FACING));
        }else nbt.putBoolean("Stuck", false);
    }

    @Override
    public void tick() {
        super.tick();
        if (isStuck()){
            if (this.age % 20 == 0) {
                this.playSound(SESoundEvents.FIREWORK_IGNITING, 2.0f, (float) (1.0 * this.random.nextGaussian() * 0.1f));
                if (this.getWorld().isClient){
                    ParticleUtil.spawnConicalParticlesFromFacing(this.getWorld(), ParticleTypes.FLAME, this.getRotationClient(), this.getPos(), Vec3d.of(this.getStuckFacing().getVector()).multiply(0.05f),10,0.8f,50);
                }
            }
            getStuckEntity().ifPresent((entity) -> {
                if (entity.isAlive()) {
                    this.setPosition(entity.getPos().add(EntityUtil.toVec3d(this.dataTracker.get(ENTITY_STUCK_OFFSET))));
                } else {
                    this.detached = true;
                    this.dataTracker.set(IS_STUCK, false);
                    this.dataTracker.set(STUCK_FACING, Direction.DOWN);
                }

            });
        }
    }

    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        if (this.getWorld().isClient && this.isStuck()) return true;
        return super.isLogicalSideForUpdatingMovement();
    }

    @Override
    public float modifyExplosionDamage(Explosion explosion, Entity target, float originalDamage, double exposure) {
        return (float) Math.min(10 * exposure, originalDamage);
    }

    @Override
    public boolean isFiring() {
        return !isStuck();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        Vec3d pos = blockHitResult.getPos().offset(blockHitResult.getSide(),0.1f);
        Direction side = blockHitResult.getSide();
        this.dataTracker.set(STUCK_FACING, side);
        Vec2f entityRotation = EntityUtil.toEntityRotation(side);
        this.updatePositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), entityRotation.x, entityRotation.y);
        setStuck(Optional.empty());
    }

    public Direction getStuckFacing(){
        return this.dataTracker.get(STUCK_FACING);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.getBoundingBox().raycast(this.getPos(), entity.getPos().add(0,entity.getHeight() / 2,0))
                .or(() -> Optional.ofNullable(entity.getBoundingBox()
                        .intersection(entity.getBoundingBox())
                        .getCenter()))
                .ifPresent(collide -> {
                    Direction value = EntityUtil.sideFromBoxCenter(entity.getBoundingBox(), collide);
                    Vec2f entityRotation = EntityUtil.toEntityRotation(value);
                    this.setRotation(entityRotation.x,entityRotation.y);
                    this.dataTracker.set(STUCK_FACING, value);
                    this.dataTracker.set(ENTITY_STUCK_OFFSET, collide.subtract(entity.getPos()).toVector3f());
                }
        );
        setStuck(Optional.of(entity));
    }

    @Override
    public boolean hasNoGravity() {
        return isStuck() && !detached;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (data == STUCK_ENTITY_ID){
            Entity entityById = this.getWorld().getEntityById(dataTracker.get(STUCK_ENTITY_ID));
            if (entityById != null) {
                stuckEntityUUID = Optional.of(entityById.getUuid());
                stuckEntityCache = Optional.of(entityById);
            }
        }
    }

    @Override
    protected StickyBomb getFirework() {
        return StickyBomb.INSTANCE;
    }
}

package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.fireworks.BaseFirework;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;

public abstract class BaseFireworkEntity<E extends BaseFireworkEntity<E,?>, F extends BaseFirework<E,?,?>> extends Entity implements GeoEntity {
    private static final TrackedData<Integer> DURATION_REMAIN = DataTracker.registerData(BaseFireworkEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> FUSE_REMAIN = DataTracker.registerData(BaseFireworkEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private Entity owner = null;
    @Nullable
    private UUID ownerUuid = null;

    public BaseFireworkEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    protected abstract F getFirework();

    protected int getDurationRemain() {
        return this.dataTracker.get(DURATION_REMAIN);
    }

    protected int getFuseRemain() {
        return this.dataTracker.get(FUSE_REMAIN);
    }


    public void setDurationRemain(int duration) {
        this.dataTracker.set(DURATION_REMAIN, duration);
    }

    public void setFuseRemain(int fuse) {
        this.dataTracker.set(FUSE_REMAIN, fuse);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(DURATION_REMAIN, this.getFirework().getFiringTime());
        this.dataTracker.startTracking(FUSE_REMAIN, this.getFirework().getFusingTime());
    }

    public float getStrength(){
        return this.getDurationRemain() / (float) this.getFirework().getFiringTime();
    }

    @Override
    public void tick() {
        super.tick();
        int fuseRemain = this.getFuseRemain();
        if (fuseRemain > 0) {
            this.setFuseRemain(fuseRemain - 1);
            this.getFirework().onEntityFusing((E) this);
        }else {
            int durationRemain = this.getDurationRemain();
            if (durationRemain > 0) {
                this.setDurationRemain(durationRemain - 1);
//            if (!this.getWorld().isClient) {
                this.getFirework().onEntityFiring((E) this);
//            }
            } else {
                this.discard();
            }
        }
        if (!this.getWorld().isClient){
            double d;
            if (!this.hasNoGravity()) {
                d = this.isTouchingWater() ? -0.005 : -0.04;
                this.setVelocity(this.getVelocity().add(0.0, d, 0.0));
            }
            if (this.isOnGround()) {
                this.setVelocity(this.getVelocity().multiply(0.5));
            }

            this.move(MovementType.SELF, this.getVelocity());
            if (!this.isOnGround()) {
                this.setVelocity(this.getVelocity().multiply(0.95));
            }
        }
    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    public void setOwner(@Nullable Entity owner) {
        this.owner = owner;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Owner")) {
            ownerUuid = nbt.getUuid("Owner");
        }
        this.setDurationRemain(nbt.getInt("DurationRemain"));
        this.setFuseRemain(nbt.getInt("FuseRemain"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.owner != null) {
            nbt.putUuid("Owner", owner.getUuid());
        }else if (this.ownerUuid != null) {
            nbt.putUuid("Owner", ownerUuid);
        }
        nbt.putInt("DurationRemain", this.getDurationRemain());
        nbt.putInt("FuseRemain", this.getFuseRemain());
    }

    @Nullable
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        } else if (this.ownerUuid != null && this.getWorld() instanceof ServerWorld) {
            this.owner = ((ServerWorld)this.getWorld()).getEntity(this.ownerUuid);
            return this.owner;
        } else {
            return null;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}

package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.BaseFirework;
import org.jetbrains.annotations.Nullable;

public abstract class BaseFireworkEntity<E extends BaseFireworkEntity<E,?>, F extends BaseFirework<E,?,?>> extends OwnedGeoEntity implements IFireworkEntity {
    private static final TrackedData<Integer> DURATION_REMAIN = DataTracker.registerData(BaseFireworkEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> FUSE_REMAIN = DataTracker.registerData(BaseFireworkEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public BaseFireworkEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    protected abstract F getFirework();

    protected int getDurationRemain() {
        return this.dataTracker.get(DURATION_REMAIN);
    }

    @Override
    public void discardFirework() {
        this.discard();
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch) {
        super.playSound(sound, volume, pitch);
    }

    @Override
    public @Nullable AbstractTeam getScoreboardTeam() {
        Entity owner = this.getOwner();
        if (owner != null) return owner.getScoreboardTeam();
        else return super.getScoreboardTeam();
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

    protected boolean shouldTickFiring(){
        return true;
    }
    @Override
    public void tick() {
        super.tick();
        int fuseRemain = this.getFuseRemain();
        if (fuseRemain > 0) {
            this.setFuseRemain(fuseRemain - 1);
            this.onEntityFusing();
        }else if (shouldTickFiring()){
            int durationRemain = this.getDurationRemain();
            if (fuseRemain == 0 && durationRemain == this.getFirework().getFiringTime()){
                this.onEntityStartFiring();
            }
            if (durationRemain > 0) {
                this.setDurationRemain(durationRemain - 1);
                this.onEntityFiring();
            } else {
                this.onEntityStopFiring();
                if (!this.getWorld().isClient)this.discard();
            }
        }
//        if (!this.getWorld().isClient){
            if (this.isSubmergedInWater()) {
                disposeOnWater();
            }
            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0, -this.getGravity(), 0.0));
            }
            if (this.isOnGround()) {
                this.setVelocity(this.getVelocity().multiply(0.5));
            }

            if (!this.isOnGround()) {
                this.setVelocity(this.getVelocity().multiply(0.95));
            }
//        }
        if (this.shouldSelfMove()) {
            this.move(MovementType.SELF, this.getVelocity());
        }
    }

    protected void onEntityFusing() {
        this.getFirework().onEntityFusing((E) this);
    }

    protected void onEntityStartFiring() {
        this.getFirework().onEntityStartFiring((E) this);
    }

    protected void onEntityStopFiring() {
        this.getFirework().onEntityStopFiring((E) this);
    }

    protected void onEntityFiring() {
        this.getFirework().onEntityFiring((E) this);
    }

    protected boolean shouldSelfMove(){
        return true;
    }

    protected double getGravity() {
        return this.isTouchingWater() ? 0.005 : 0.04;
    }

    public boolean isFusing(){
        return this.getFuseRemain() > 0;
    }

    public boolean isFiring(){
        return this.getFuseRemain() <= 0;
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
    public boolean isAttackable() {
        return false;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.setVelocity(f, g, h, speed, divergence);
        Vec3d vec3d = shooter.getVelocity();
        this.setVelocity(this.getVelocity().add(vec3d.x, shooter.isOnGround() ? 0.0 : vec3d.y, vec3d.z));
    }

    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence)).multiply(speed);
        this.setVelocity(vec3d);
        double d = vec3d.horizontalLength();
        this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
        this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setDurationRemain(nbt.getInt("DurationRemain"));
        this.setFuseRemain(nbt.getInt("FuseRemain"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("DurationRemain", this.getDurationRemain());
        nbt.putInt("FuseRemain", this.getFuseRemain());
    }
}

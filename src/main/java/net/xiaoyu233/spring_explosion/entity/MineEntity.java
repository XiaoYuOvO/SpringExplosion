package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.xiaoyu233.spring_explosion.item.MineItem;
import net.xiaoyu233.spring_explosion.util.FireworkUtil;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class MineEntity extends OwnedGeoEntity implements IFireworkEntity{
    public static final String DEPLOY_ANIM_NAME = "animation.firework_mine.deploy";
    private static final RawAnimation DEPLOY_ANIM = RawAnimation.begin().thenPlay(DEPLOY_ANIM_NAME);
    public static final String DEPLOY_CONTROLLER_NAME = "Deploy";
    private static final TrackedData<Integer> PREPARE_TIME  = DataTracker.registerData(MineEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public MineEntity(EntityType<?> type, World world) {
        super(type, world);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
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
    public boolean isImmuneToExplosion() {
        return true;
    }

    @Override
    public float modifyExplosionDamage(Explosion explosion, Entity target, float originalDamage, double exposure) {
        return Math.min(8, originalDamage);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(PREPARE_TIME, MineItem.PREPARE_TIME);
    }

    public void setPrepareTime(int time){
        this.dataTracker.set(PREPARE_TIME,time);
    }

    public int getPrepareTime(){
        return this.dataTracker.get(PREPARE_TIME);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setPrepareTime(nbt.getInt("PrepareTime"));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, DEPLOY_CONTROLLER_NAME, 0, state -> PlayState.STOP).triggerableAnim(DEPLOY_ANIM_NAME, DEPLOY_ANIM));
    }

    @Override
    public double getTick(Object entity) {
        return this.age * 2;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient){
            if (this.getPrepareTime() > 0 && this.isOnGround()) {
                if (getPrepareTime() == MineItem.PREPARE_TIME){
                    this.triggerAnim(DEPLOY_CONTROLLER_NAME, DEPLOY_ANIM_NAME);
                }
                this.setPrepareTime(this.getPrepareTime() - 1);
            }
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
        }
    }

    @Override
    public void setOnGround(boolean onGround) {
        super.setOnGround(onGround);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("PrepareTime",this.getPrepareTime());
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EXPLODE_STATUS) {
            this.ignite();
        }
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    public boolean ignite(){
        if (this.getPrepareTime() != 0) return false;
        this.getWorld().createExplosion(this, this.getWorld().getDamageSources().explosion(this, this.getOwner()), new FireworkExplosionBehavior<>(this),this.getX(), this.getY(),this.getZ(), 2.2f, false, World.ExplosionSourceType.MOB, false);
        if (this.getWorld().isClient) {
            this.getWorld().addFireworkParticle(this.getX(), this.getY(), this.getZ(), 0, 0, 0, FireworkUtil.randomFirework(this.getWorld().random));
        }else{
            this.getWorld().sendEntityStatus(this, EXPLODE_STATUS);
        }
        this.discard();
        return true;
    }
}

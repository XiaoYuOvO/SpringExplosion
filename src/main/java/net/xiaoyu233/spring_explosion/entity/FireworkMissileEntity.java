package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.fireworks.FireworkMissile;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Objects;
import java.util.Optional;

public class FireworkMissileEntity extends BaseFireworkEntity<FireworkMissileEntity, FireworkMissile>{
    public static final String LAUNCH_ANIM_NAME = "animation.firework_missile.launch";
    private static final RawAnimation LAUNCH_ANIM = RawAnimation.begin().thenPlay(LAUNCH_ANIM_NAME);
    public static final String LAUNCH_CONTROLLER_NAME = "Launch";
    public FireworkMissileEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, LAUNCH_CONTROLLER_NAME, 0, state -> PlayState.STOP).triggerableAnim(LAUNCH_ANIM_NAME, LAUNCH_ANIM));
    }

    @Override
    protected FireworkMissile getFirework() {
        return FireworkMissile.INSTANCE;
    }

    @Override
    protected void onEntityFiring() {
        super.onEntityFiring();
        if (this.getOwner() != null) {
            if ((getDurationRemain() + 1) % 60 == 0) {
                FireworkMissileBulletEntity bulletEntity = Objects.requireNonNull(SEEntityTypes.FIREWORK_MISSILE_BULLET.create(this.getWorld()));
                bulletEntity.setTargetHeight(this.getMaxHeight());
                bulletEntity.setOwner(this.getOwner());
                bulletEntity.setDurationRemain(200);
                this.findTarget().ifPresent(bulletEntity::setTarget);
                bulletEntity.refreshPositionAndAngles(this.getX(), this.getY() + 0.5d, this.getZ(), 0, -90);
                this.getWorld().spawnEntity(bulletEntity);
                this.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0F, (float) (1 + 0.1 * random.nextGaussian()));
            }
        }
    }

    @Override
    protected void onEntityStartFiring() {
        super.onEntityStartFiring();
        if (this.getOwner() != null) {
            this.triggerAnim(LAUNCH_CONTROLLER_NAME, LAUNCH_ANIM_NAME);
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (this.isFiring()){
            if (this.getOwner() != null) {
                this.triggerAnim(LAUNCH_CONTROLLER_NAME, LAUNCH_ANIM_NAME);
            }
        }
    }

    @Override
    public double getTick(Object entity) {
        return Math.min(this.getFirework().getFiringTime() - this.getDurationRemain(), 660);
    }

    @Override
    public boolean isImmuneToExplosion() {
        return true;
    }

    private double getMaxHeight(){
        this.setRotation(0,-90);
        HitResult raycast = this.raycast(16, 0, false);
        if (Objects.requireNonNull(raycast.getType()) == HitResult.Type.BLOCK) {
            return raycast.getPos().y;
        }
        return this.getY() + 8;
    }

    private Optional<LivingEntity> findTarget(){
        return Optional.ofNullable(this.getWorld()
                .getClosestEntity(LivingEntity.class, TargetPredicate.createAttackable()
                        .setPredicate(PredicateUtil.getVisibleRangeAttackPredicate(this.getWorld(), this.getOwner(), this)), null, this.getX(), this.getY(), this.getZ(), this.getBoundingBox()
                        .expand(32)));
    }
}

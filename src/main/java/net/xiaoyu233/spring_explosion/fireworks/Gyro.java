package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.GyroItemRenderer;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.entity.GyroEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.GyroItem;
import net.xiaoyu233.spring_explosion.util.CollisionUtil;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.xiaoyu233.spring_explosion.item.GyroItem.IGNITE_ANIM_NAME;
import static net.xiaoyu233.spring_explosion.item.GyroItem.IGNITE_CONTROLLER_NAME;

public class Gyro extends BaseFirework<GyroEntity, GyroItem, GyroItemRenderer>{
    public static final Gyro INSTANCE = new Gyro();

    @Override
    public void onEntityFiring(GyroEntity entity) {
        float strength = entity.getStrength();
        if (!entity.getWorld().isClient) {
            entity.setYaw((entity.getYaw() + 30 * strength) % 360);
        }
        if (!entity.getWorld().isClient) {
            Predicate<Entity> entityPredicate = EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(PredicateUtil.getNotSameTeamAsOwnerPredicate(entity.getOwner()));
            LivingEntity closestTarget = entity.getEntityWorld().getClosestEntity(LivingEntity.class, TargetPredicate.createAttackable().setPredicate(entityPredicate::test), null, entity.getX(), entity.getY(), entity.getZ(), entity.getBoundingBox().expand(10.0));
            Vec3d speed;
            if (closestTarget != null) {
                speed = closestTarget.getPos().subtract(entity.getPos()).multiply(0.1);
                double squaredLength = speed.squaredDistanceTo(0.0, 0.0, 0.0);
                if (squaredLength > 0.1) {
                    speed.normalize().multiply(0.1);
                }

                entity.setVelocity(speed);
            }

            Vec3d rotationVec = entity.getRotationVec(0.0F);
            List<Entity> entities = CollisionUtil.collideInConical(entity, rotationVec.negate(), entity.getWorld(), 3.0, 40.0, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(PredicateUtil.getVisibleRangeAttackPredicate(entity.getWorld(), entity, entity.getOwner())));
            entities.addAll(CollisionUtil.collideInConical(entity, rotationVec, entity.getWorld(), 3.0, 40.0, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(PredicateUtil.getVisibleRangeAttackPredicate(entity.getWorld(), entity, entity.getOwner()))));

            for (Entity target : entities) {
                target.damage(target.getWorld().getDamageSources().explosion(entity, entity.getOwner()), 3.0F * strength);
            }

            if (entity.age % 10 == 0) {
                entity.playSound(SESoundEvents.GYRO_FIRING, strength, 1.0F);
            }
        } else {
            ParticleUtil.spawnConicalParticlesFromFacing(entity.getWorld(), entity.getRotationClient(), entity.getPos().add(0.0, 0.2, 0.0), entity.getVelocity(), (int)(10.0F * strength), 3.0F, 40.0F);
            ParticleUtil.spawnConicalParticlesFromFacing(entity.getWorld(), entity.getRotationClient().add(new Vec2f(0.0F, 180.0F)), entity.getPos().add(0.0, 0.2, 0.0), entity.getVelocity(), (int)(10.0F * strength), 3.0F, 40.0F);
        }
    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public void onStartUsing(GyroItem item, ItemStack stack, World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld serverWorld){
            item.triggerAnim(user, GeoItem.getOrAssignId(user.getStackInHand(hand), serverWorld), IGNITE_CONTROLLER_NAME, IGNITE_ANIM_NAME);
        }
    }

    @Override
    public Supplier<GyroItemRenderer> getRenderer() {
        return GyroItemRenderer::new;
    }

    @Override
    public FireworkItemToEntityAction getDropAction() {
        return FireworkItemToEntityAction.THROW_NO_COPY_ROTATION;
    }

    @Override
    public FireworkItemToEntityAction getOffhandAction() {
        return FireworkItemToEntityAction.DROP_NO_COPY_ROTATION;
    }

    @Override
    public int getFusingTime() {
        return 50;
    }

    @Override
    public int getFiringTime() {
        return 100;
    }

    @Override
    protected @NotNull EntityType<GyroEntity> getEntityType() {
        return SEEntityTypes.GYRO;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.BOTH;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.ENTITY;
    }
}

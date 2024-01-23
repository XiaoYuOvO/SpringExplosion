package net.xiaoyu233.spring_explosion.fireworks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.SparkSwordItemRenderer;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.entity.SparkSwordEntity;
import net.xiaoyu233.spring_explosion.item.SEItems;
import net.xiaoyu233.spring_explosion.item.SparkSwordItem;
import net.xiaoyu233.spring_explosion.util.CollisionUtil;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import net.xiaoyu233.spring_explosion.util.PredicateUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SparkSword extends BaseFirework<SparkSwordEntity, SparkSwordItem, SparkSwordItemRenderer>{
    public static final SparkSword INSTANCE = new SparkSword();
    @Override
    public void onEntityFiring(SparkSwordEntity entity) {
        damageEntityAndSpawnParticles(entity.getEntityWorld(), entity, entity.getOwner(), entity.getStrength());
    }

    public static void damageEntityAndSpawnParticles(World world, @NotNull Entity attacker, @Nullable Entity source, float strength) {
        float maxDistance = 4 - 2 * strength;
        Vec3d rotationVec = attacker.getRotationVec(0);
        Vec3d startPos;
        if (attacker instanceof PlayerEntity) {
            startPos = attacker.getEyePos().add(attacker.getHandPosOffset(SEItems.SPARK_SWORD)).subtract(0, 0.5, 0).add(rotationVec);
        } else {
            startPos = attacker.getEyePos().add(attacker.getHandPosOffset(SEItems.SPARK_SWORD)).add(0, 0.08, 0).add(rotationVec.multiply(0.5f));
        }
        float angle = 60 - 20 * strength;
        if (!world.isClient) {
            for (Entity entity : CollisionUtil.collideInConical(attacker, world, maxDistance, angle * MathHelper.RADIANS_PER_DEGREE, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(
                    PredicateUtil.getVisibleRangeAttackPredicate(world, attacker, source)))) {
                entity.damage(world.getDamageSources().explosion(attacker, source), 5 - 2 * strength);
            }
        }else {
            ParticleUtil.spawnConicalParticlesFromFacing(world, attacker.getRotationClient(), startPos, attacker.getVelocity(), (int) (10 * strength), maxDistance, angle);
        }
    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {
        damageEntityAndSpawnParticles(world, user, user, strength);
    }

    @Override
    public void onItemFusing(ItemStack itemStack, LivingEntity user, int slot) {

    }

    @Override
    public void onStartUsing(SparkSwordItem item, ItemStack stack, World world, PlayerEntity user, Hand hand) {
        world.playSoundFromEntity(null, user, SESoundEvents.SPARK_SWORD_ON, SoundCategory.PLAYERS, 1f, 1f);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Supplier<SparkSwordItemRenderer> getRenderer() {
        return SparkSwordItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 0;
    }

    @Override
    public int getFiringTime() {
        return 100;
    }

    @Override
    protected @NotNull EntityType<SparkSwordEntity> getEntityType() {
        return SEEntityTypes.SPARK_SWORD;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.NONE;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.BOTH;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BipedEntityModel.ArmPose getArmPose() {
        return BipedEntityModel.ArmPose.BOW_AND_ARROW;
    }
}

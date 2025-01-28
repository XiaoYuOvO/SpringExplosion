package net.xiaoyu233.spring_explosion.fireworks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FireworkJetpackItemRenderer;
import net.xiaoyu233.spring_explosion.entity.FireworkJetpackEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FireworkJetpackItem;
import net.xiaoyu233.spring_explosion.util.EntityUtil;
import net.xiaoyu233.spring_explosion.util.ItemUtil;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FireworkJetpack extends BaseFirework<FireworkJetpackEntity, FireworkJetpackItem, FireworkJetpackItemRenderer>{
    public static final FireworkJetpack INSTANCE = new FireworkJetpack();
    @Override
    public void onEntityFiring(FireworkJetpackEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {
        if (user.getFluidHeight(FluidTags.WATER) > 0.7) {
            itemStack.decrement(1);
            user.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH,1,1);
            user.playSound(SoundEvents.ENTITY_ITEM_BREAK,1,1);
        }
        if (itemStack.getDamage() ==  100 || itemStack.getDamage() == 1){
            if (world.isClient){
                spawnParticle(user,20, new Vec3d(0,1,0));
            }else {
                if (itemStack.getDamage() == 1){
                    user.setNoGravity(itemStack.getDamage() != 100);
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 7, 10));
                }else {
                    user.setNoGravity(false);
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 25, 15));
                }

            }

            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 160 ,0));
            user.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH,1,1);
        }
        if (world.isClient) {
            spawnParticle(user, 2, new Vec3d(0, -0.1, 0));
        }
        ItemUtil.damageItem(itemStack, 1, user);
    }

    private static void spawnParticle(Entity entity, int count, Vec3d relVec) {
        World world = entity.getWorld();
        Vec3d rotationVector = EntityUtil.getRotationVector(0, entity.getBodyYaw());
        Vec3d left = rotationVector.rotateY(90).multiply(0.3);
        Vec3d right = rotationVector.rotateY(-90).multiply(0.3);
        ParticleUtil.spawnDownwardParticles(world, entity.getPos().add(left.x, 0.7, left.z),relVec, count,20);
        ParticleUtil.spawnDownwardParticles(world,  entity.getPos().add(right.x, 0.7, right.z),relVec, count,20);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Supplier<FireworkJetpackItemRenderer> getRenderer() {
        return FireworkJetpackItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 0;
    }

    @Override
    public int getFiringTime() {
        return 260;
    }

    @Override
    protected @NotNull EntityType<FireworkJetpackEntity> getEntityType() {
        return SEEntityTypes.FIREWORK_JETPACK;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.NONE;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.BOTH;
    }
}

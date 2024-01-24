package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.RocketAcceleratorItemRenderer;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.RocketAcceleratorEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.RocketAcceleratorItem;
import net.xiaoyu233.spring_explosion.util.ItemUtil;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RocketAccelerator extends BaseFirework<RocketAcceleratorEntity, RocketAcceleratorItem, RocketAcceleratorItemRenderer>{
    public static final RocketAccelerator INSTANCE = new RocketAccelerator();
    @Override
    public void onEntityFiring(RocketAcceleratorEntity entity) {
        if(!entity.getWorld().isClient){
            if (entity.age % 10 == 0) {
                entity.playSound(SESoundEvents.ROCKET_ACCELERATOR, 1  * entity.getStrength(), 1);
            }
        }else {
            addVelocityAndSpawnParticle(entity);
        }
        Vec3d rotationVector = ParticleUtil.getRotationVector(0, entity.getBodyYaw());
        entity.addVelocity(rotationVector.multiply(entity.isOnGround() ? 0.5 : 0.1));
    }



    @Override
    public void onItemFiring(World world, ItemStack stack, LivingEntity livingEntity, int slot, float strength) {
        if (livingEntity.getFluidHeight(FluidTags.WATER) > 0.5) {
            stack.decrement(1);
            livingEntity.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH,1,1);
            livingEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK,1,1);
        }
        if (!world.isClient) {
            ItemUtil.damageItem(stack, 1, livingEntity);
        }else {
            if (livingEntity.age % 10 == 0) {
                livingEntity.playSound(SESoundEvents.ROCKET_ACCELERATOR, 1 * strength, 1);
            }
            Vec3d rotationVector = ParticleUtil.getRotationVector(0, livingEntity.getBodyYaw());
            livingEntity.addVelocity(rotationVector.multiply(livingEntity.isOnGround() ? 0.5 : 0.1));
            addVelocityAndSpawnParticle(livingEntity);
        }
    }

    private static void addVelocityAndSpawnParticle(Entity entity) {
        World world = entity.getWorld();
        Vec3d rotationVector = ParticleUtil.getRotationVector(0, entity.getBodyYaw());
        Vec3d left = rotationVector.rotateY(90).multiply(0.5);
        Vec3d right = rotationVector.rotateY(-90).multiply(0.5);
        Vec2f back = new Vec2f(0, entity.getBodyYaw() + 180);
        ParticleUtil.spawnConicalParticlesFromFacing(world, back, entity.getPos().add(left.x, 0.5, left.z),Vec3d.ZERO, 3,3,30);
        ParticleUtil.spawnConicalParticlesFromFacing(world, back, entity.getPos().add(right.x, 0.5, right.z),Vec3d.ZERO, 3,3,30);
    }

    @Override
    public Supplier<RocketAcceleratorItemRenderer> getRenderer() {
        return RocketAcceleratorItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 0;
    }

    @Override
    public int getFiringTime() {
        return 40;
    }

    @Override
    protected @NotNull EntityType<RocketAcceleratorEntity> getEntityType() {
        return SEEntityTypes.ROCKET_ACCELERATOR;
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getOffhandAction() {
        return FireworkItemToEntityAction.offhandCopyYaw();
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.NONE;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.ENTITY;
    }
}

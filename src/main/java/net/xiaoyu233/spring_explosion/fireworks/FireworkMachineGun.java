package net.xiaoyu233.spring_explosion.fireworks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FireworkMachineGunItemRenderer;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.FireworkBulletEntity;
import net.xiaoyu233.spring_explosion.entity.FireworkMachineGunEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FireworkMachineGunItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FireworkMachineGun extends BaseFirework<FireworkMachineGunEntity, FireworkMachineGunItem, FireworkMachineGunItemRenderer> {
    public static final FireworkMachineGun INSTANCE = new FireworkMachineGun();
    public static final int FIRING_TIME = 80;
    public static final int FUSING_TIME = 40;
    @Override
    public void onEntityFiring(FireworkMachineGunEntity entity) {
        if (entity.age % 3 == 0) {
            World world = entity.getWorld();
            if (!world.isClient) {
                FireworkBulletEntity fireworkBulletEntity = SEEntityTypes.FIREWORK_BULLET.create(world);
                fireworkBulletEntity.setOwner(entity.getOwner());
                world.spawnEntity(fireworkBulletEntity);
                fireworkBulletEntity.updatePositionAndAngles(entity.getX(), entity.getEyeY(), entity.getZ(), entity.getYaw(), entity.getPitch());
            }
            entity.playSound(SESoundEvents.FIREWORK_MACHINE_GUN_FIRE, 1, 0.9f + world.random.nextFloat() * 0.2f);
        }
    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {
        if (user.age % 3 == 0) {
            if (!world.isClient) {
                FireworkBulletEntity fireworkBulletEntity = SEEntityTypes.FIREWORK_BULLET.create(world);
                fireworkBulletEntity.setOwner(user);
//                fireworkBulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 0.3f, 0);
                world.spawnEntity(fireworkBulletEntity);
                fireworkBulletEntity.updatePositionAndAngles(user.getX(), user.getEyeY() - 0.3d, user.getZ(), user.getYaw(), user.getPitch());
            }
            user.playSound(SESoundEvents.FIREWORK_MACHINE_GUN_FIRE, 1, 0.9f + world.random.nextFloat() * 0.2f);
            user.setVelocity(user.getVelocity().multiply(0.1));
        }
    }

    @Override
    public Supplier<FireworkMachineGunItemRenderer> getRenderer() {
        return FireworkMachineGunItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return FUSING_TIME;
    }

    @Override
    public int getFiringTime() {
        return FIRING_TIME;
    }

    @Override
    protected @NotNull EntityType<FireworkMachineGunEntity> getEntityType() {
        return SEEntityTypes.FIREWORK_MACHINE_GUN;
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getFusingToEntityFiringAction() {
        return FireworkItemToEntityAction.offhandCopyYaw();
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getDropAction() {
        return FireworkItemToEntityAction.dropCopyYaw();
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getOffhandAction() {
        return FireworkItemToEntityAction.offhandCopyYaw();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public BipedEntityModel.ArmPose getArmPose() {
        return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.BOTH;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.BOTH;
    }
}

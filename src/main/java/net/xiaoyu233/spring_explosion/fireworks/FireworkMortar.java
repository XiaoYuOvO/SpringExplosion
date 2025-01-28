package net.xiaoyu233.spring_explosion.fireworks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FireworkMortarItemRenderer;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.FireworkMortarEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FireworkMortarItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.function.Supplier;

public class FireworkMortar extends BaseFirework<FireworkMortarEntity, FireworkMortarItem, FireworkMortarItemRenderer>{
    public static final FireworkMortar INSTANCE = new FireworkMortar();
    @Override
    public void onEntityFiring(FireworkMortarEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {}

    @Override
    public Supplier<FireworkMortarItemRenderer> getRenderer() {
        return FireworkMortarItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 20;
    }

    @Override
    public int getFiringTime() {
        return 2000;
    }

    @Override
    protected @NotNull EntityType<FireworkMortarEntity> getEntityType() {
        return SEEntityTypes.FIREWORK_MORTAR;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.BOTH;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.ENTITY;
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getDropAction() {
        return FireworkItemToEntityAction.dropCopyYaw();
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getFusingToEntityFiringAction() {
        return FireworkItemToEntityAction.offhandHead();
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
    public void onEntityStartFiring(FireworkMortarEntity fireworkMortarEntity) {
        fireworkMortarEntity.playSound(SESoundEvents.FIREWORK_MORTAR_LAUNCH, 1, 1);
    }

    @Override
    public void onStartUsing(FireworkMortarItem item, ItemStack stack, World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld serverWorld){
            item.triggerAnim(user, GeoItem.getOrAssignId(user.getStackInHand(hand), serverWorld), FireworkMortarItem.IGNITE_CONTROLLER_NAME, FireworkMortarItem.IGNITE_ANIM_NAME);
        }
    }
}

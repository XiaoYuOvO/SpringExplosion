package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FireCircleItemRenderer;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.FireCircleEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FireCircleItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FireCircle extends BaseFirework<FireCircleEntity, FireCircleItem, FireCircleItemRenderer>{
    public static final FireCircle INSTANCE = new FireCircle();
    public static final int SUB_COUNT = 36;
    public static final int SUB_RADIUS = 8;
    public static final int SUMMON_INTERVAL = 0;
    public static final int FUSE_TIME = 40;

    @Override
    public void onEntityFiring(FireCircleEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<FireCircleItemRenderer> getRenderer() {
        return FireCircleItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return FUSE_TIME;
    }

    @Override
    public int getFiringTime() {
        return 40;
    }

    @Override
    protected @NotNull EntityType<FireCircleEntity> getEntityType() {
        return SEEntityTypes.FIRE_CIRCLE;
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
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getOffhandAction() {
        return FireworkItemToEntityAction.offhandCopyYaw();
    }
}

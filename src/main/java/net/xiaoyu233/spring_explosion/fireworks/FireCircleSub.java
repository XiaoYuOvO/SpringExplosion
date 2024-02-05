package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FireCircleItemRenderer;
import net.xiaoyu233.spring_explosion.entity.FireCircleSubEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FireCircleItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FireCircleSub extends BaseFirework<FireCircleSubEntity, FireCircleItem, FireCircleItemRenderer>{
    public static final FireCircleSub INSTANCE = new FireCircleSub();
    @Override
    public void onEntityFiring(FireCircleSubEntity entity) {

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
        return 0;
    }

    @Override
    public int getFiringTime() {
        return 100;
    }

    @Override
    protected @NotNull EntityType<FireCircleSubEntity> getEntityType() {
        return SEEntityTypes.FIRE_CIRCLE_SUB;
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

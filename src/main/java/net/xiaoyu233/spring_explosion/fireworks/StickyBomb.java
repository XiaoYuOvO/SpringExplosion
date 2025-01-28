package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.StickyBombItemRenderer;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.entity.StickyBombEntity;
import net.xiaoyu233.spring_explosion.item.StickyBombItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class StickyBomb extends BaseFirework<StickyBombEntity, StickyBombItem, StickyBombItemRenderer> {
    public static final StickyBomb INSTANCE = new StickyBomb();
    @Override
    public void onEntityFiring(StickyBombEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<StickyBombItemRenderer> getRenderer() {
        return StickyBombItemRenderer::new;
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
    protected @NotNull EntityType<StickyBombEntity> getEntityType() {
        return SEEntityTypes.STICKY_BOMB;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.NONE;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.ENTITY;
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getDropAction() {
        return FireworkItemToEntityAction.throwFirework(1.2f);
    }
}

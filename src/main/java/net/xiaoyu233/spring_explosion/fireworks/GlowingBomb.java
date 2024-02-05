package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.GlowingBombItemRenderer;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.GlowingBombEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.GlowingBombItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class GlowingBomb extends BaseFirework<GlowingBombEntity, GlowingBombItem, GlowingBombItemRenderer> {
    public static final GlowingBomb INSTANCE = new GlowingBomb();
    @Override
    public void onEntityFiring(GlowingBombEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<GlowingBombItemRenderer> getRenderer() {
        return GlowingBombItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 0;
    }

    @Override
    public int getFiringTime() {
        return 60;
    }

    @Override
    protected @NotNull EntityType<GlowingBombEntity> getEntityType() {
        return SEEntityTypes.GLOWING_BOMB;
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
        return FireworkItemToEntityAction.throwFirework();
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getFusingToEntityFiringAction() {
        return FireworkItemToEntityAction.throwFirework();
    }
}

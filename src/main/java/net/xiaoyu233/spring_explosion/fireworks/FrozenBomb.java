package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FrozenBombItemRenderer;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.FrozenBombEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FrozenBombItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FrozenBomb extends BaseFirework<FrozenBombEntity, FrozenBombItem, FrozenBombItemRenderer>{
    public static final FrozenBomb INSTANCE = new FrozenBomb();
    @Override
    public void onEntityFiring(FrozenBombEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<FrozenBombItemRenderer> getRenderer() {
        return FrozenBombItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 0;
    }

    @Override
    protected int getThrownUseCooldown() {
        return 20;
    }

    @Override
    public int getFiringTime() {
        return 60;
    }

    @Override
    protected @NotNull EntityType<FrozenBombEntity> getEntityType() {
        return SEEntityTypes.FROZEN_BOMB;
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
}

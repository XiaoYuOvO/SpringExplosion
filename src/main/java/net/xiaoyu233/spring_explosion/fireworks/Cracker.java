package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.CrackerItemRenderer;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.CrackerEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.CrackerItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Cracker extends BaseFirework<CrackerEntity, CrackerItem, CrackerItemRenderer>{
    public static final Cracker INSTANCE = new Cracker();
    private Cracker(){}
    @Override
    public void onEntityFiring(CrackerEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    protected int getThrownUseCooldown() {
        return 3;
    }

    @Override
    public Supplier<CrackerItemRenderer> getRenderer() {
        return CrackerItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 0;
    }

    @Override
    public int getFiringTime() {
        return 2000;
    }

    @Override
    protected @NotNull EntityType<CrackerEntity> getEntityType() {
        return SEEntityTypes.CRACKER;
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
        return FireworkItemToEntityAction.throwFirework(2f);
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getFusingToEntityFiringAction() {
        return FireworkItemToEntityAction.throwFirework(2f);
    }
}

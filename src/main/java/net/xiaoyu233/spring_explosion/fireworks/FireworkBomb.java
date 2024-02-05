package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FireworkBombItemRenderer;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.FireworkBombEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FireworkBombItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FireworkBomb extends BaseFirework<FireworkBombEntity, FireworkBombItem, FireworkBombItemRenderer>{
    public static final  FireworkBomb INSTANCE = new FireworkBomb();
    @Override
    public void onEntityFiring(FireworkBombEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<FireworkBombItemRenderer> getRenderer() {
        return FireworkBombItemRenderer::new;
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
    protected @NotNull EntityType<FireworkBombEntity> getEntityType() {
        return SEEntityTypes.FIREWORK_BOMB;
    }


    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getOffhandAction() {
        return super.getOffhandAction();
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getFusingToEntityFiringAction() {
        return FireworkItemToEntityAction.throwFirework();
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

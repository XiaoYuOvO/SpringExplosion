package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.OilyBombItemRenderer;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.OilyBombEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.OilyBombItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class OilyBomb extends BaseFirework<OilyBombEntity, OilyBombItem, OilyBombItemRenderer>{
    public static final OilyBomb INSTANCE = new OilyBomb();
    @Override
    public void onEntityFiring(OilyBombEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<OilyBombItemRenderer> getRenderer() {
        return OilyBombItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 0;
    }

    @Override
    protected int getThrownUseCooldown() {
        return 30;
    }

    @Override
    public int getFiringTime() {
        return 60;
    }

    @Override
    protected @NotNull EntityType<OilyBombEntity> getEntityType() {
        return SEEntityTypes.OILY_BOMB;
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

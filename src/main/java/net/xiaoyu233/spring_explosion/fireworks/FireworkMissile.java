package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.FireworkMissileItemRenderer;
import net.xiaoyu233.spring_explosion.entity.FireworkMissileEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.FireworkMissileItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FireworkMissile extends BaseFirework<FireworkMissileEntity, FireworkMissileItem, FireworkMissileItemRenderer>{
    public static final FireworkMissile INSTANCE = new FireworkMissile();
    @Override
    public void onEntityFiring(FireworkMissileEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<FireworkMissileItemRenderer> getRenderer() {
        return FireworkMissileItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 80;
    }

    @Override
    public int getFiringTime() {
        return 540;
    }

    @Override
    protected @NotNull EntityType<FireworkMissileEntity> getEntityType() {
        return SEEntityTypes.FIREWORK_MISSILE;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.ENTITY;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.ENTITY;
    }
}

package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.entity.FireworkBulletEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.item.BaseFireworkItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Supplier;

public class FireworkBullet extends BaseFirework<FireworkBulletEntity, BaseFireworkItem<FireworkBullet, ?, ?>, GeoItemRenderer<BaseFireworkItem<FireworkBullet, ?, ?>>>{
    public static final FireworkBullet INSTANCE = new FireworkBullet();
    @Override
    public void onEntityFiring(FireworkBulletEntity entity) {

    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<GeoItemRenderer<BaseFireworkItem<FireworkBullet, ?, ?>>> getRenderer() {
        return null;
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
    protected @NotNull EntityType<FireworkBulletEntity> getEntityType() {
        return SEEntityTypes.FIREWORK_BULLET;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.NONE;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.NONE;
    }
}

package net.xiaoyu233.spring_explosion.fireworks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.components.items.FireworkItemBaseComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.item.DefaultGeoItem;
import net.xiaoyu233.spring_explosion.item.IFireworkItem;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;

import java.util.function.Supplier;

public abstract class BaseFirework<E extends BaseFireworkEntity<E, ?>, I extends Item & IFireworkItem<?> & GeoAnimatable, IR extends GeoRenderer<I>> {

    public abstract void onEntityFiring(E entity);
    public void onEntityFusing(E entity){
        World world = entity.getWorld();
        if (world.isClient){
            world.addParticle(ParticleTypes.SMALL_FLAME, entity.getX(), entity.getY(), entity.getZ(), world.random.nextGaussian() * 0.05, world.random.nextGaussian() * 0.02, world.random.nextGaussian() *  0.05);
        }
        if (entity.age % 10 == 0){
            entity.playSound(SESoundEvents.FIREWORK_IGNITING, 1,1);
        }
    }
    public abstract void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength);
    public void onItemFusing(ItemStack itemStack, LivingEntity user, int slot){
        if (itemStack.getDamage() % 10 == 0){
            user.playSound(SESoundEvents.FIREWORK_IGNITING, 1,1);
        }
    }
    public void onStartUsing(I item, ItemStack stack, World world, PlayerEntity user, Hand hand) {}

    @Environment(EnvType.CLIENT)
    public abstract Supplier<IR> getRenderer();

    public abstract int getFusingTime();

    public abstract int getFiringTime();

    @NotNull
    public E createEntity(World world, ItemStack stack, LivingEntity user) {
        E fireworkEntity = getEntityType().create(world);
        fireworkEntity.setOwner(user);
        FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stack);
        if (fireworkItemBaseComponent.isFusing() && this.getFuseUsage() != FireworkUsage.NONE){
            fireworkEntity.setFuseRemain(stack.getMaxDamage() - stack.getDamage());
        }else if (fireworkItemBaseComponent.isFiring() && this.getFireUsage() != FireworkUsage.NONE){
            fireworkEntity.setFuseRemain(0);
            fireworkEntity.setDurationRemain(stack.getMaxDamage() - stack.getDamage());
        }
        return fireworkEntity;
    }

    @NotNull
    protected abstract EntityType<E> getEntityType();

    @NotNull
    public abstract FireworkUsage getFuseUsage();
    @NotNull
    public abstract FireworkUsage getFireUsage();

    public FireworkItemToEntityAction getOffhandAction(){
        return FireworkItemToEntityAction.DROP;
    }
    public FireworkItemToEntityAction getDropAction(){
        return FireworkItemToEntityAction.THROW;
    }
}

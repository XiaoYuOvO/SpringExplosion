package net.xiaoyu233.spring_explosion.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.components.items.FireworkItemBaseComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.fireworks.BaseFirework;
import net.xiaoyu233.spring_explosion.fireworks.FireworkItemToEntityAction;
import net.xiaoyu233.spring_explosion.fireworks.FireworkUsage;
import net.xiaoyu233.spring_explosion.util.ItemUtil;
import software.bernie.geckolib.renderer.GeoRenderer;

import java.util.function.Supplier;

public abstract class BaseFireworkItem<F extends BaseFirework<?, I, IR>, I extends BaseFireworkItem<F,I,IR>, IR extends GeoRenderer<I>>  extends DefaultGeoItem<I, IR> implements IFireworkItem<F>, IDropUseItem{
    private final F firework;
    public BaseFireworkItem(F firework, Settings settings) {
        super(settings);
        this.firework = firework;
    }

    @Override
    @Environment(EnvType.CLIENT)
    Supplier<IR> getRendererSupplier() {
        return firework.getRenderer();
    }

    public F getFirework(){
        return this.firework;
    }

    public void createAndThrowFirework(World world, ItemStack stack, LivingEntity user, FireworkItemToEntityAction action){
        BaseFireworkEntity<?, ?> fireworkEntity = this.firework.createEntity(world, stack, user);
        world.spawnEntity(fireworkEntity);
        action.apply(fireworkEntity, user);
    }

    @Override
    public ItemEntity useOnDrop(World world, ItemStack stack, LivingEntity user) {
        BaseFireworkEntity<?, ?> fusingEntity = this.getFirework().createEntity(world, stack, user);
        world.spawnEntity(fusingEntity);
        this.getFirework().getDropAction().apply(fusingEntity, user);
        return null;
    }

    @Override
    public boolean canUseOnDrop(ItemStack stack) {
        return SEItemComponents.FIREWORK_ITEM_BASE.get(stack).isUsed();
    }

    @Override
    //@Environment(EnvType.CLIENT, EnvType.SERVER)
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingEntity){
            FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stack);
            if (fireworkItemBaseComponent.isFusing()){
                FireworkUsage fuseUsage = firework.getFuseUsage();
                if (fuseUsage.canUseOnHand() && selected) {
                    if (!world.isClient) {
                        ItemUtil.damageItem(stack, 1, livingEntity);
                    }
                    this.firework.onItemFusing(stack, livingEntity, slot);
                    if (stack.getMaxDamage() - stack.getDamage() == 1) {
                        stack.setDamage(0);
                        fireworkItemBaseComponent.setFusing(false);
                        fireworkItemBaseComponent.setFiring(true);
                    }
                }else if (fuseUsage == FireworkUsage.ENTITY){
                    createAndThrowFirework(world, stack, livingEntity, firework.getDropAction());
                    stack.decrement(1);
                }else if (!selected){
                    createAndThrowFirework(world, stack, livingEntity, firework.getOffhandAction());
                    stack.decrement(1);
                }else {
                    fireworkItemBaseComponent.setFusing(false);
                    fireworkItemBaseComponent.setFiring(true);
                }
            }else if (fireworkItemBaseComponent.isFiring()){
                FireworkUsage fireUsage = firework.getFireUsage();
                if (fireUsage.canUseOnHand() && selected) {
                    this.firework.onItemFiring(world, stack, livingEntity, slot, (float) (this.getMaxDamage() - stack.getDamage()) / this.getMaxDamage());
                    if (!world.isClient) {
                        ItemUtil.damageItem(stack, 1, livingEntity);
                    }
                }else {
                    if (fireUsage == FireworkUsage.ENTITY) {
                        createAndThrowFirework(world, stack, livingEntity, firework.getFusingToEntityFiringAction());
                    } else if (fireUsage == FireworkUsage.BOTH && !selected) {
                        createAndThrowFirework(world, stack, livingEntity, firework.getOffhandAction());
                    }
                    stack.decrement(1);
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stackInHand);
        if (!fireworkItemBaseComponent.isFusing()){
            fireworkItemBaseComponent.setFusing(true);
            this.firework.onStartUsing((I) this, stackInHand, world, user, hand);
            return TypedActionResult.success(stackInHand, false);
        }
        return TypedActionResult.pass(stackInHand);
    }

}

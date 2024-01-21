package net.xiaoyu233.spring_explosion.item;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.RocketAcceleratorArmorRenderer;
import net.xiaoyu233.spring_explosion.client.render.item.RocketAcceleratorItemRenderer;
import net.xiaoyu233.spring_explosion.components.items.FireworkItemBaseComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.RocketAcceleratorEntity;
import net.xiaoyu233.spring_explosion.fireworks.RocketAccelerator;
import net.xiaoyu233.spring_explosion.util.ItemUtil;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RocketAcceleratorItem extends ArmorItem implements Equipment, IFireworkItem<RocketAccelerator>, GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public RocketAcceleratorItem(Item.Settings settings) {
        super(ArmorMaterials.IRON, Type.LEGGINGS, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity livingEntity && livingEntity.getEquippedStack(EquipmentSlot.LEGS) == stack) {
            FireworkItemBaseComponent fireworkItemBaseComponent = SEItemComponents.FIREWORK_ITEM_BASE.get(stack);
            if (fireworkItemBaseComponent.isFiring()) {
                int firingTime = this.getFirework().getFiringTime();
                getFirework().onItemFiring(world,stack, livingEntity, slot, (float) (firingTime - stack.getDamage()) /  firingTime);
            }
        }
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {

            private GeoArmorRenderer<?> renderer;
            private GeoItemRenderer<?> itemRenderer;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.itemRenderer == null)
                    itemRenderer = new RocketAcceleratorItemRenderer();
                return this.itemRenderer;
            }

            @Override
            public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if(this.renderer == null) // Important that we do this. If we just instantiate  it directly in the field it can cause incompatibilities with some mods.
                    this.renderer = new RocketAcceleratorArmorRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        SEItemComponents.FIREWORK_ITEM_BASE.get(stackInHand).setFiring(true);
        ItemStack equippedStack = user.getEquippedStack(EquipmentSlot.LEGS);
        if (user.isSneaking()){
            if (!world.isClient) {
                RocketAcceleratorEntity entity = this.getFirework().createEntity(world, stackInHand, user);
                this.getFirework().getOffhandAction().apply(entity, user);
                world.spawnEntity(entity);
                stackInHand.decrement(1);
            }
            return TypedActionResult.success(stackInHand);
        }else if (equippedStack.isOf(SEItems.ROCKET_ACCELERATOR) && SEItemComponents.FIREWORK_ITEM_BASE.get(equippedStack).isUsed()){
            return TypedActionResult.fail(stackInHand);
        }
        return this.equipAndSwap(this, world, user, hand);
    }

    @Override
    public RocketAccelerator getFirework() {
        return RocketAccelerator.INSTANCE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}

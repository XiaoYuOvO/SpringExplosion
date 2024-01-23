package net.xiaoyu233.spring_explosion.item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.MineControllerItemRenderer;
import net.xiaoyu233.spring_explosion.components.items.MineControllerItemComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.MineEntity;
import net.xiaoyu233.spring_explosion.util.ItemUtil;

import java.util.Optional;
import java.util.function.Supplier;

public class MineControllerItem extends DefaultGeoItem<MineControllerItem, MineControllerItemRenderer> {
    public MineControllerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        Optional<MineControllerItemComponent> maybe = SEItemComponents.FIREWORK_MINE_CONTROLLER.maybeGet(stackInHand);
        if (maybe.isPresent() && world instanceof ServerWorld serverWorld) {
            MineControllerItemComponent component = maybe.get();
            Optional<MineEntity> target = component.getTarget(serverWorld);
            if (target.isPresent() && component.getPrepareTime() == 0) {
                MineEntity mineEntity = target.get();
                if (mineEntity.getPrepareTime() == 0 && mineEntity.ignite()) {
                    stackInHand.decrement(1);
                    return TypedActionResult.success(ItemStack.EMPTY);
                }
            }
        }
        return TypedActionResult.fail(stackInHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        Optional<MineControllerItemComponent> maybe = SEItemComponents.FIREWORK_MINE_CONTROLLER.maybeGet(stack);
        if (maybe.isPresent() && entity instanceof LivingEntity livingEntity) {
            MineControllerItemComponent component = maybe.get();
            if (component.getPrepareTime() > 0) {
                component.setPrepareTime(component.getPrepareTime() - 1);
                if (component.getPrepareTime() == 0) {
                    stack.addEnchantment(Enchantments.POWER, 1);
                    component.setPrepareTime(0);
                    stack.setDamage(0);
                }else {
                    ItemUtil.damageItem(stack, 1 , livingEntity);
                }
            }
        }
    }

    @Override
    Supplier<MineControllerItemRenderer> getRendererSupplier() {
        return MineControllerItemRenderer::new;
    }
}

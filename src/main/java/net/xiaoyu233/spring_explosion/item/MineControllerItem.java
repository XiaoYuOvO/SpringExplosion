package net.xiaoyu233.spring_explosion.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.MineControllerItemRenderer;
import net.xiaoyu233.spring_explosion.components.items.MineControllerItemComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.MineEntity;
import net.xiaoyu233.spring_explosion.util.ItemUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MineControllerItem extends DefaultGeoItem<MineControllerItem, MineControllerItemRenderer> {
    private final @NotNull Supplier<List<Text>> lazyTooltip;

    public MineControllerItem(Settings settings) {
        super(settings);
        this.lazyTooltip = BaseFireworkItem.createTooltipCache(Optional.empty(), this);
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
    public boolean hasGlint(ItemStack stack) {
        return SEItemComponents.FIREWORK_MINE_CONTROLLER.maybeGet(stack).map(component -> component.getPrepareTime() <= 0).orElse(false);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.addAll(this.lazyTooltip.get());
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        Optional<MineControllerItemComponent> maybe = SEItemComponents.FIREWORK_MINE_CONTROLLER.maybeGet(stack);
        if (maybe.isPresent() && world instanceof ServerWorld serverWorld && entity instanceof LivingEntity livingEntity) {
            MineControllerItemComponent component = maybe.get();
            Optional<MineEntity> target = component.getTarget(serverWorld);
            if (target.isPresent() && target.get().isOnGround() && component.getPrepareTime() > 0) {
                component.setPrepareTime(component.getPrepareTime() - 1);
                if (component.getPrepareTime() == 0) {
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

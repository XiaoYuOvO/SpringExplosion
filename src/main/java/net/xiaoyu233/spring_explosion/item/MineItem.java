package net.xiaoyu233.spring_explosion.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.MineItemRenderer;
import net.xiaoyu233.spring_explosion.components.items.MineControllerItemComponent;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.entity.MineEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.fireworks.FireworkItemToEntityAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MineItem extends DefaultGeoItem<MineItem, MineItemRenderer> implements IDropUseItem {
    public static int PREPARE_TIME = 20;
    private final @NotNull Supplier<List<Text>> lazyTooltip;

    public MineItem(Settings settings) {
        super(settings);
        this.lazyTooltip = BaseFireworkItem.createTooltipCache(Optional.empty(), this);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stackInHand = user.getStackInHand(hand);
        if (!world.isClient) {
            ItemStack controller = useMine(world, user, stackInHand);
            return TypedActionResult.success(controller, true);
        }
        return TypedActionResult.fail(stackInHand);
    }

    @NotNull
    private static ItemStack useMine(World world, LivingEntity user, ItemStack stackInHand) {
        MineEntity mineEntity = SEEntityTypes.FIREWORK_MINE.create(world);
        mineEntity.setOwner(user);
        mineEntity.setPrepareTime(PREPARE_TIME);
        FireworkItemToEntityAction.dropNoCopyRotation().apply(mineEntity, user);
        world.spawnEntity(mineEntity);
        ItemStack controller = new ItemStack(SEItems.FIREWORK_MINE_CONTROLLER);
        MineControllerItemComponent mineControllerItemComponent = SEItemComponents.FIREWORK_MINE_CONTROLLER.get(controller);
        mineControllerItemComponent.setTarget(mineEntity);
        stackInHand.decrement(1);
        return controller;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.addAll(this.lazyTooltip.get());
    }

    @Override
    Supplier<MineItemRenderer> getRendererSupplier() {
        return MineItemRenderer::new;
    }

    @Override
    public boolean canUseOnDrop(ItemStack stack) {
        return true;
    }

    @Override
    public ItemEntity useOnDrop(World world, ItemStack stack, LivingEntity user) {
        user.setStackInHand(Hand.MAIN_HAND, useMine(world, user, stack));
        return null;
    }
}

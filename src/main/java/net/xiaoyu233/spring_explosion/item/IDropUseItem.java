package net.xiaoyu233.spring_explosion.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface IDropUseItem {
    @Nullable
    ItemEntity useOnDrop(World world, ItemStack stack, LivingEntity user);

    boolean canUseOnDrop(ItemStack stack);
}

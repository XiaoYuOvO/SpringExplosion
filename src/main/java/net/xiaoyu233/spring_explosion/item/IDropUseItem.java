package net.xiaoyu233.spring_explosion.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IDropUseItem {
    ItemEntity useOnDrop(World world, ItemStack stack, LivingEntity user);

    boolean canUseOnDrop(ItemStack stack);
}

package net.xiaoyu233.spring_explosion.item;

import net.minecraft.item.ItemStack;
import net.xiaoyu233.spring_explosion.components.items.SEItemComponents;
import net.xiaoyu233.spring_explosion.fireworks.BaseFirework;

public interface IFireworkItem<F extends BaseFirework<?,?,?>> {
    F getFirework();
    default int getMaxDamage(ItemStack stack){
        if (SEItemComponents.FIREWORK_ITEM_BASE.get(stack).isFusing()) {
            return getFirework().getFusingTime();
        }else {
            return getFirework().getFiringTime();
        }
    }
}

package net.xiaoyu233.spring_explosion.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class ItemUtil {
    /**
     * Damage a item without creative checking
     */
    public static void damageItem(ItemStack stack, int amount, LivingEntity entity) {
        if (!entity.getWorld().isClient && stack.isDamageable()) {
            if (stack.damage(amount,
                    entity.getRandom(),
                    entity instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity : null)) {
                Item item = stack.getItem();
                stack.decrement(1);
                if (entity instanceof PlayerEntity) {
                    ((PlayerEntity) entity).incrementStat(Stats.BROKEN.getOrCreateStat(item));
                }
                stack.setDamage(0);
            }
        }
    }
}

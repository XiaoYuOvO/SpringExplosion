package net.xiaoyu233.spring_explosion.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.xiaoyu233.spring_explosion.client.render.item.SparkSwordItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.SparkSword;

public class SparkSwordItem extends BaseFireworkItem<SparkSword,SparkSwordItem, SparkSwordItemRenderer> {
    public SparkSwordItem(Settings settings) {
        super(SparkSword.INSTANCE, settings);
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

}

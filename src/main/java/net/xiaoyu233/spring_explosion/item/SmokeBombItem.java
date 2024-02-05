package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.SmokeBombItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.SmokeBomb;

public class SmokeBombItem extends BaseFireworkItem<SmokeBomb, SmokeBombItem, SmokeBombItemRenderer>{
    public SmokeBombItem(Settings settings) {
        super(SmokeBomb.INSTANCE, settings);
    }
}

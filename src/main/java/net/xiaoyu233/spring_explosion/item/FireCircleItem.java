package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.FireCircleItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FireCircle;

public class FireCircleItem extends BaseFireworkItem<FireCircle, FireCircleItem, FireCircleItemRenderer>{
    public FireCircleItem(Settings settings) {
        super(FireCircle.INSTANCE, settings);
    }
}

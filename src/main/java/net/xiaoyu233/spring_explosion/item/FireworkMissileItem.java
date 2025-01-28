package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.FireworkMissileItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FireworkMissile;

public class FireworkMissileItem extends BaseFireworkItem<FireworkMissile, FireworkMissileItem, FireworkMissileItemRenderer>{
    public FireworkMissileItem(Settings settings) {
        super(FireworkMissile.INSTANCE, settings);
    }
}

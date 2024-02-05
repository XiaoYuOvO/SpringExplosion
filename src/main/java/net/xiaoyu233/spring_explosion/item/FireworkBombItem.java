package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.FireworkBombItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FireworkBomb;

public class FireworkBombItem extends BaseFireworkItem<FireworkBomb, FireworkBombItem, FireworkBombItemRenderer>{
    public FireworkBombItem(Settings settings) {
        super(FireworkBomb.INSTANCE, settings);
    }
}

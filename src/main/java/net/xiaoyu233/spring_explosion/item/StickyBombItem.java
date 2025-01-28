package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.StickyBombItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.StickyBomb;

public class StickyBombItem extends BaseFireworkItem<StickyBomb, StickyBombItem, StickyBombItemRenderer>{
    public StickyBombItem(Settings settings) {
        super(StickyBomb.INSTANCE, settings);
    }
}

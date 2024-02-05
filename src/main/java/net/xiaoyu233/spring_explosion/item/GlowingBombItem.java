package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.GlowingBombItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.GlowingBomb;

public class GlowingBombItem extends BaseFireworkItem<GlowingBomb, GlowingBombItem, GlowingBombItemRenderer>{
    public GlowingBombItem(Settings settings) {
        super(GlowingBomb.INSTANCE, settings);
    }
}

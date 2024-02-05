package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.OilyBombItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.OilyBomb;

public class OilyBombItem extends BaseFireworkItem<OilyBomb, OilyBombItem, OilyBombItemRenderer>{
    public OilyBombItem(Settings settings) {
        super(OilyBomb.INSTANCE, settings);
    }
}

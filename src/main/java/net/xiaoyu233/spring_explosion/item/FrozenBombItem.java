package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.FrozenBombItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FrozenBomb;

public class FrozenBombItem extends BaseFireworkItem<FrozenBomb, FrozenBombItem, FrozenBombItemRenderer>{
    public FrozenBombItem(Settings settings) {
        super(FrozenBomb.INSTANCE, settings);
    }
}

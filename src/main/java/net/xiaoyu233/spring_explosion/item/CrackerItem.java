package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.CrackerItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.Cracker;

public class CrackerItem extends BaseFireworkItem<Cracker, CrackerItem, CrackerItemRenderer> {
    public CrackerItem(Settings settings) {
        super(Cracker.INSTANCE, settings);
    }
}

package net.xiaoyu233.spring_explosion.item;

import net.xiaoyu233.spring_explosion.client.render.item.FireworkMachineGunItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FireworkMachineGun;

public class FireworkMachineGunItem extends BaseFireworkItem<FireworkMachineGun, FireworkMachineGunItem, FireworkMachineGunItemRenderer> {
    public FireworkMachineGunItem(Settings settings) {
        super(FireworkMachineGun.INSTANCE, settings);
    }
}

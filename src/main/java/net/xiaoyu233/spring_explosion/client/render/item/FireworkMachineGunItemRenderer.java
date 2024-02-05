package net.xiaoyu233.spring_explosion.client.render.item;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.item.FireworkMachineGunItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FireworkMachineGunItemRenderer extends GeoItemRenderer<FireworkMachineGunItem> {
    public FireworkMachineGunItemRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(SpringExplosion.MOD_ID, "firework_machine_gun")));
    }
}

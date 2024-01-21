package net.xiaoyu233.spring_explosion.client.render.item;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.item.RocketAcceleratorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class RocketAcceleratorArmorRenderer extends GeoArmorRenderer<RocketAcceleratorItem> {
    public RocketAcceleratorArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(SpringExplosion.MOD_ID, "armor/rocket_accelerator")));
    }
}
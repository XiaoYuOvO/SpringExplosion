package net.xiaoyu233.spring_explosion.client.render.item;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.item.FireworkMortarItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FireworkMortarItemRenderer extends GeoItemRenderer<FireworkMortarItem> {
    public FireworkMortarItemRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(SpringExplosion.MOD_ID, "firework_mortar")));
    }
}

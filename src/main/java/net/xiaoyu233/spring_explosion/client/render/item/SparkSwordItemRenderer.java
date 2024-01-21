package net.xiaoyu233.spring_explosion.client.render.item;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.item.SparkSwordItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SparkSwordItemRenderer extends GeoItemRenderer<SparkSwordItem> {
    public SparkSwordItemRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(SpringExplosion.MOD_ID,"spark_sword")));
    }
}

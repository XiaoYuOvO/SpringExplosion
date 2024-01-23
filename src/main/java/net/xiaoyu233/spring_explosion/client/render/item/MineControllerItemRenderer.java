package net.xiaoyu233.spring_explosion.client.render.item;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.item.MineControllerItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MineControllerItemRenderer extends GeoItemRenderer<MineControllerItem> {
    public MineControllerItemRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(SpringExplosion.MOD_ID, "firework_mine_controller")));
    }
}

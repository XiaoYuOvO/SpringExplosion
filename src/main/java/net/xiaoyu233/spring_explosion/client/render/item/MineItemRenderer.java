package net.xiaoyu233.spring_explosion.client.render.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.item.MineItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MineItemRenderer extends GeoItemRenderer<MineItem> {
    public MineItemRenderer() {
        super(new DefaultedItemGeoModel<>(new Identifier(SpringExplosion.MOD_ID, "firework_mine")));
    }
}

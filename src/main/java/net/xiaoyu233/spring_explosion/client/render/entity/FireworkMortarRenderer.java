package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireworkMortarModel;
import net.xiaoyu233.spring_explosion.entity.FireworkMortarEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkMortarRenderer extends GeoEntityRenderer<FireworkMortarEntity> {
    public FireworkMortarRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireworkMortarModel());
    }
}

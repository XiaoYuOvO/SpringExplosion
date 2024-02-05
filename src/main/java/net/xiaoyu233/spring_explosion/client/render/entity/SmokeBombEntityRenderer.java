package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.SmokeBombEntityModel;
import net.xiaoyu233.spring_explosion.entity.SmokeBombEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmokeBombEntityRenderer extends GeoEntityRenderer<SmokeBombEntity> {
    public SmokeBombEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SmokeBombEntityModel());
    }
}

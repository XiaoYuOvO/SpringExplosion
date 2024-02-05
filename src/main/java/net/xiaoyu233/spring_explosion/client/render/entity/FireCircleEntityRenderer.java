package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireCircleEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireCircleEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireCircleEntityRenderer extends GeoEntityRenderer<FireCircleEntity> {
    public FireCircleEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireCircleEntityModel());
    }
}

package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireCircleSubEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireCircleSubEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireCircleSubEntityRenderer extends GeoEntityRenderer<FireCircleSubEntity> {
    public FireCircleSubEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireCircleSubEntityModel());
    }
}

package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.SparkSwordModel;
import net.xiaoyu233.spring_explosion.entity.SparkSwordEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SparkSwordRenderer extends GeoEntityRenderer<SparkSwordEntity> {
    public SparkSwordRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SparkSwordModel());
    }
}

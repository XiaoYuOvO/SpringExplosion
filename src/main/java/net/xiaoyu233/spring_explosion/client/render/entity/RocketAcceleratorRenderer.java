package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.RocketAcceleratorModel;
import net.xiaoyu233.spring_explosion.entity.RocketAcceleratorEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RocketAcceleratorRenderer extends GeoEntityRenderer<RocketAcceleratorEntity> {
    public RocketAcceleratorRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RocketAcceleratorModel());
    }
}

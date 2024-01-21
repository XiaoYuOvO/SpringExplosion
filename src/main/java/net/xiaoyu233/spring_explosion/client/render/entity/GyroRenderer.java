package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.GyroModel;
import net.xiaoyu233.spring_explosion.entity.GyroEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GyroRenderer extends GeoEntityRenderer<GyroEntity> {
    public GyroRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GyroModel());
    }
}

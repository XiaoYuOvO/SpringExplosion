package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.CrackerEntityModel;
import net.xiaoyu233.spring_explosion.entity.CrackerEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrackerEntityRenderer extends GeoEntityRenderer<CrackerEntity> {
    public CrackerEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CrackerEntityModel());
    }
}

package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.GlowingBombModel;
import net.xiaoyu233.spring_explosion.entity.GlowingBombEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GlowingBombEntityRenderer extends GeoEntityRenderer<GlowingBombEntity> {
    public GlowingBombEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new GlowingBombModel());
    }
}

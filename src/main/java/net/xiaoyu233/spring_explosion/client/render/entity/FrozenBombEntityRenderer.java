package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FrozenBombEntityModel;
import net.xiaoyu233.spring_explosion.entity.FrozenBombEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FrozenBombEntityRenderer extends GeoEntityRenderer<FrozenBombEntity> {
    public FrozenBombEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FrozenBombEntityModel());
    }
}

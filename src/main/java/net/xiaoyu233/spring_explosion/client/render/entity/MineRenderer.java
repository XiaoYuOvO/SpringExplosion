package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.MineEntityModel;
import net.xiaoyu233.spring_explosion.entity.MineEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MineRenderer extends GeoEntityRenderer<MineEntity> {
    public MineRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MineEntityModel());
    }
}

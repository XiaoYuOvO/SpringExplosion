package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FirecrackersEntityModel;
import net.xiaoyu233.spring_explosion.entity.FirecrackersEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FirecrackersEntityRenderer extends GeoEntityRenderer<FirecrackersEntity> {
    public FirecrackersEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FirecrackersEntityModel());
    }
}

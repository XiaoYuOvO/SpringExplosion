package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireworkMissileEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireworkMissileEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkMissileEntityRenderer extends GeoEntityRenderer<FireworkMissileEntity> {
    public FireworkMissileEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireworkMissileEntityModel());
    }
}

package net.xiaoyu233.spring_explosion.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.xiaoyu233.spring_explosion.client.render.entity.models.FireworkMachineGunEntityModel;
import net.xiaoyu233.spring_explosion.entity.FireworkMachineGunEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkMachineGunEntityRenderer extends GeoEntityRenderer<FireworkMachineGunEntity> {
    public FireworkMachineGunEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new FireworkMachineGunEntityModel());
    }
}

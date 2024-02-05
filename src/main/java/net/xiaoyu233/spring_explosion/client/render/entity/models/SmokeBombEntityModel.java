package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.SmokeBombEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SmokeBombEntityModel extends DefaultedEntityGeoModel<SmokeBombEntity> {
    public SmokeBombEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "smoke_bomb"));
    }
}

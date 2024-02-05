package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FrozenBombEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FrozenBombEntityModel extends DefaultedEntityGeoModel<FrozenBombEntity> {
    public FrozenBombEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "frozen_bomb"));
    }
}

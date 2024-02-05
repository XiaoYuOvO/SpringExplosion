package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.OilyBombEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class OilyBombEntityModel extends DefaultedEntityGeoModel<OilyBombEntity> {
    public OilyBombEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "oily_bomb"));
    }
}

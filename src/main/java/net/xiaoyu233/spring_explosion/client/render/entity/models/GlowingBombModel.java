package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.GlowingBombEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GlowingBombModel extends DefaultedEntityGeoModel<GlowingBombEntity> {
    public GlowingBombModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "glowing_bomb"));
    }
}

package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FireworkBombEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FireworkEntityModel extends DefaultedEntityGeoModel<FireworkBombEntity> {
    public FireworkEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "firework_bomb"));
    }
}

package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.CrackerEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CrackerEntityModel extends DefaultedEntityGeoModel<CrackerEntity> {
    public CrackerEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "cracker"));
    }
}

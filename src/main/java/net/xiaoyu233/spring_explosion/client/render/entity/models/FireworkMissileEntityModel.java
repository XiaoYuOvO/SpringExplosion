package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FireworkMissileEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FireworkMissileEntityModel extends DefaultedEntityGeoModel<FireworkMissileEntity> {
    public FireworkMissileEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "firework_missile"));
    }
}

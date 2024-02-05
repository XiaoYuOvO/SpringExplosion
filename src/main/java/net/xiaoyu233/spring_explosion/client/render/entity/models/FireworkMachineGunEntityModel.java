package net.xiaoyu233.spring_explosion.client.render.entity.models;

import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.entity.FireworkMachineGunEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FireworkMachineGunEntityModel extends DefaultedEntityGeoModel<FireworkMachineGunEntity> {
    public FireworkMachineGunEntityModel() {
        super(new Identifier(SpringExplosion.MOD_ID, "firework_machine_gun"));
    }
}

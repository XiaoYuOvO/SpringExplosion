package net.xiaoyu233.spring_explosion.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;
import net.xiaoyu233.spring_explosion.fireworks.RocketAccelerator;

public class SEEntityTypes {
    public static final EntityType<SparkSwordEntity> SPARK_SWORD = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "spark_sword"), EntityType.Builder.create(SparkSwordEntity::new, SpawnGroup.MISC).setDimensions(0.8F, 0.1F).build("spark_sword"));
    public static final EntityType<GyroEntity> GYRO = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "gyro"), EntityType.Builder.create(GyroEntity::new, SpawnGroup.MISC).setDimensions(0.5F, 0.3F).build("gyro"));
    public static final EntityType<RocketAcceleratorEntity> ROCKET_ACCELERATOR = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "rocket_accelerator"), EntityType.Builder.create(RocketAcceleratorEntity::new, SpawnGroup.MISC).setDimensions(0.8F, 0.4F).build("rocket_accelerator"));

    public static void registerEntities() {
//        FabricDefaultAttributeRegistry.register(SPARK_SWORD, SparkSwordEntity.createAttributes());
    }
}

package net.xiaoyu233.spring_explosion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;

public class SEEntityTypes {
    public static final EntityType<SparkSwordEntity> SPARK_SWORD = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "spark_sword"), EntityType.Builder.create(SparkSwordEntity::new, SpawnGroup.MISC).setDimensions(0.8F, 0.1F).maxTrackingRange(4).trackingTickInterval(20).build("spark_sword"));
    public static final EntityType<GyroEntity> GYRO = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "gyro"), EntityType.Builder.create(GyroEntity::new, SpawnGroup.MISC).setDimensions(0.5F, 0.3F).maxTrackingRange(4).trackingTickInterval(20).build("gyro"));
    public static final EntityType<RocketAcceleratorEntity> ROCKET_ACCELERATOR = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "rocket_accelerator"), EntityType.Builder.create(RocketAcceleratorEntity::new, SpawnGroup.MISC).setDimensions(0.8F, 0.4F).maxTrackingRange(4).trackingTickInterval(20).build("rocket_accelerator"));
    public static final EntityType<FireworkMortarEntity> FIREWORK_MORTAR = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firework_mortar"), EntityType.Builder.create(FireworkMortarEntity::new, SpawnGroup.MISC).setDimensions(0.4F, 0.2F).maxTrackingRange(4).trackingTickInterval(20).build("firework_mortar"));
    public static final EntityType<MineEntity> FIREWORK_MINE = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firework_mine"), EntityType.Builder.create(MineEntity::new, SpawnGroup.MISC).setDimensions(0.6F, 0.1F).maxTrackingRange(4).trackingTickInterval(1).build("firework_mine"));

    public static void registerEntities() {
//        FabricDefaultAttributeRegistry.register(SPARK_SWORD, SparkSwordEntity.createAttributes());
    }
}

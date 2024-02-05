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
    public static final EntityType<MineEntity> FIREWORK_MINE = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firework_mine"), EntityType.Builder.create(MineEntity::new, SpawnGroup.MISC).setDimensions(0.6F, 0.1F).maxTrackingRange(4).trackingTickInterval(2).build("firework_mine"));
    public static final EntityType<FireCircleEntity> FIRE_CIRCLE = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "fire_circle"), EntityType.Builder.create(FireCircleEntity::new, SpawnGroup.MISC).setDimensions(0.6F, 0.2F).maxTrackingRange(4).trackingTickInterval(3).build("fire_circle"));
    public static final EntityType<FireCircleSubEntity> FIRE_CIRCLE_SUB = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "fire_circle_sub"), EntityType.Builder.create(FireCircleSubEntity::new, SpawnGroup.MISC).setDimensions(0.6F, 0.2F).maxTrackingRange(6).trackingTickInterval(3).build("fire_circle_sub"));
    public static final EntityType<FireworkBombEntity> FIREWORK_BOMB = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firework_bomb"), EntityType.Builder.create(FireworkBombEntity::new, SpawnGroup.MISC).setDimensions(0.4F, 0.4F).maxTrackingRange(6).trackingTickInterval(20).build("firework_bomb"));
    public static final EntityType<OilyBombEntity> OILY_BOMB = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "oily_bomb"), EntityType.Builder.create(OilyBombEntity::new, SpawnGroup.MISC).setDimensions(0.4F, 0.4F).maxTrackingRange(6).trackingTickInterval(20).build("oily_bomb"));
    public static final EntityType<GlowingBombEntity> GLOWING_BOMB = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "glowing_bomb"), EntityType.Builder.create(GlowingBombEntity::new, SpawnGroup.MISC).setDimensions(0.4F, 0.4F).maxTrackingRange(6).trackingTickInterval(20).build("glowing_bomb"));
    public static final EntityType<SmokeBombEntity> SMOKE_BOMB = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "smoke_bomb"), EntityType.Builder.create(SmokeBombEntity::new, SpawnGroup.MISC).setDimensions(0.4F, 0.4F).maxTrackingRange(6).trackingTickInterval(20).build("smoke_bomb"));
    public static final EntityType<FrozenBombEntity> FROZEN_BOMB = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "frozen_bomb"), EntityType.Builder.create(FrozenBombEntity::new, SpawnGroup.MISC).setDimensions(0.4F, 0.4F).maxTrackingRange(6).trackingTickInterval(20).build("frozen_bomb"));
    public static final EntityType<FirecrackersEntity> FIRECRACKERS = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firecrackers"), EntityType.Builder.create(FirecrackersEntity::new, SpawnGroup.MISC).setDimensions(0.7F, 0.2F).maxTrackingRange(6).trackingTickInterval(3).build("firecrackers"));
    public static final EntityType<FireworkBulletEntity> FIREWORK_BULLET = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firework_bullet"), EntityType.Builder.create(FireworkBulletEntity::new, SpawnGroup.MISC).setDimensions(0.2F, 0.1F).maxTrackingRange(8).trackingTickInterval(20).build("firework_bullet"));
    public static final EntityType<FireworkMachineGunEntity> FIREWORK_MACHINE_GUN = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firework_machine_gun"), EntityType.Builder.create(FireworkMachineGunEntity::new, SpawnGroup.MISC).setDimensions(0.6F, 0.15F).maxTrackingRange(6).trackingTickInterval(3).build("firework_machine_gun"));
    public static final EntityType<FireworkJetpackEntity> FIREWORK_JETPACK = Registry.register(Registries.ENTITY_TYPE, new Identifier(SpringExplosion.MOD_ID, "firework_jetpack"), EntityType.Builder.create(FireworkJetpackEntity::new, SpawnGroup.MISC).setDimensions(0.3F, 0.6F).maxTrackingRange(6).trackingTickInterval(20).build("firework_jetpack"));
    public static void registerEntities() {
//        FabricDefaultAttributeRegistry.register(SPARK_SWORD, SparkSwordEntity.createAttributes());
    }
}

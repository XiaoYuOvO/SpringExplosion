package net.xiaoyu233.spring_explosion.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;

public class SEParticles {
    public static final DefaultParticleType BATTLE_SMOKE = FabricParticleTypes.simple(true);

    public static void initialize() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(SpringExplosion.MOD_ID, "battle_smoke"), BATTLE_SMOKE);
    }

    @Environment(EnvType.CLIENT)
    public static void clientInitialize() {
//        SpriteReg.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
//            registry.register(new Identifier("modid", "particle/green_flame"));
//        }));
        ParticleFactoryRegistry.getInstance().register(BATTLE_SMOKE, BattleSmokeParticle.BattleSmokeFactory::new);
    }
}

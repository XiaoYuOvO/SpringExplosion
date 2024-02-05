package net.xiaoyu233.spring_explosion.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class BattleSmokeParticle extends SpriteBillboardParticle {

    protected BattleSmokeParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y, z);
        this.scale(6.0F);
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.maxAge = 240;
        this.gravityStrength = 0.006f;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age >= this.maxAge - 60 && this.alpha > 0.01F) {
            this.alpha -= 0.016666668f;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class BattleSmokeFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public BattleSmokeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            BattleSmokeParticle smoke = new BattleSmokeParticle(clientWorld, d, e, f, g, h, i);
            smoke.setSprite(this.spriteProvider);
            return smoke;
        }
    }
}

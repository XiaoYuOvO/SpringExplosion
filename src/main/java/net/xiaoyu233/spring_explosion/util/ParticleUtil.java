package net.xiaoyu233.spring_explosion.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public class ParticleUtil {
    @Environment(EnvType.CLIENT)
    public static void spawnConicalParticlesFromFacing(World world, Vec2f facing, Vec3d startPos, Vec3d relativeSpeed, int count, float maxDistance, float angle) {
        for (int i = 0; i < count; ++i) {
            Random random = world.random;
            float angle2 = angle / 2;
            Vec3d rotationVector = getRotationVector(MathHelper.nextBetween(random, -angle / 2, angle2) + facing.x, MathHelper.nextBetween(random, -angle / 2,
                    angle2) + facing.y);
            world.addParticle(ParticleTypes.FIREWORK,
                    startPos.x,
                    startPos.y,
                    startPos.z,
                    rotationVector.x * maxDistance * 0.15 + relativeSpeed.x,
                    rotationVector.y * maxDistance * 0.15 + relativeSpeed.y,
                    rotationVector.z * maxDistance * 0.15 + relativeSpeed.z);
        }

    }

    @Environment(EnvType.CLIENT)
    public static void spawnDownwardParticles(World world, Vec3d startPos, Vec3d relativeSpeed, int count, float radius) {
        for (int i = 0; i < count; ++i) {
            Random random = world.random;
            world.addParticle(ParticleTypes.FIREWORK,
                    startPos.x,
                    startPos.y,
                    startPos.z,
                    random.nextGaussian() * radius * 0.01 + relativeSpeed.x,
                    -random.nextDouble() + relativeSpeed.y,
                    random.nextGaussian() * radius * 0.01 + relativeSpeed.z);
        }

    }

    public static Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    public static void explodeBall(ClientWorld world, Vec3d pos, Random random, ParticleEffect type, double size, int amount, boolean important){
        explodeBall(world, pos, random, type, size, amount, important, (particle,speed) -> {});
    }

    public static void explodeBall(ClientWorld world, Vec3d pos, Random random, ParticleEffect type, double size, int amount, boolean important, BiConsumer<Particle, Vec3d> particleConsumer) {
        double d = pos.x;
        double e = pos.y;
        double f = pos.z;

        for(int i = -amount; i <= amount; ++i) {
            for(int j = -amount; j <= amount; ++j) {
                for(int k = -amount; k <= amount; ++k) {
                    double g = (double)j + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double h = (double)i + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double l = (double)k + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double m = Math.sqrt(g * g + h * h + l * l) / size + random.nextGaussian() * 0.05;
                    if (important) {
                        Particle particle = MinecraftClient.getInstance().particleManager.addParticle(type, d, e, f, g / m, h / m, l / m);
                        particleConsumer.accept(particle, new Vec3d(g / m, h / m, l / m));
                    } else world.addParticle(type, d, e, f, g / m, h / m, l / m);
                    if (i != -amount && i != amount && j != -amount && j != amount) {
                        k += amount * 2 - 1;
                    }
                }
            }
        }

    }


}

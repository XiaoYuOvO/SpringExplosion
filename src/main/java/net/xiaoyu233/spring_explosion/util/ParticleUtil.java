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
import org.joml.Vector3f;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class ParticleUtil {
    @Environment(EnvType.CLIENT)
    public static void spawnConicalParticlesFromFacing(World world, Vec2f facing, Vec3d startPos, Vec3d relativeSpeed, int count, float maxDistance, float angle) {
        for (int i = 0; i < count; ++i) {
            Random random = world.random;
            float angle2 = angle / 2;
            Vec3d rotationVector = EntityUtil.getRotationVector(MathHelper.nextBetween(random, -angle / 2, angle2) + facing.x, MathHelper.nextBetween(random, -angle / 2,
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

    public static Vector3f HSBtoRGB(float hue, float saturation, float brightness) {
        float r = 0;
        float g = 0;
        float b = 0;
        if (saturation == 0.0F) {
            r = g = b = (int)(brightness + 0.5F);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0F;
            float f = h - (float)Math.floor(h);
            float p = brightness * (1.0F - saturation);
            float q = brightness * (1.0F - saturation * f);
            float t = brightness * (1.0F - saturation * (1.0F - f));
            switch ((int)h) {
                case 0:
                    r = (brightness);
                    g = (t);
                    b = (p);
                    break;
                case 1:
                    r = (q);
                    g = (brightness);
                    b = (p);
                    break;
                case 2:
                    r = (p);
                    g = (brightness);
                    b = (t);
                    break;
                case 3:
                    r = (p);
                    g = (q);
                    b = (brightness);
                    break;
                case 4:
                    r = (t);
                    g = (p);
                    b = (brightness);
                    break;
                case 5:
                    r = (brightness);
                    g = (p);
                    b = (q);
            }
        }

        return new Vector3f(r,g,b);
    }

    @Environment(EnvType.CLIENT)
    public static void spawnConicalParticlesFromFacing(World world, Vec2f facing, Vec3d startPos, Vec3d relativeSpeed, int count, float maxDistance, float angle, Consumer<Particle> postApply) {
        for (int i = 0; i < count; ++i) {
            Random random = world.random;
            float angle2 = angle / 2;
            Vec3d rotationVector = EntityUtil.getRotationVector(MathHelper.nextBetween(random, -angle / 2, angle2) + facing.x, MathHelper.nextBetween(random, -angle / 2,
                    angle2) + facing.y);
            Particle particle = MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.FIREWORK,
                    startPos.x,
                    startPos.y,
                    startPos.z,
                    rotationVector.x * maxDistance * 0.15 + relativeSpeed.x,
                    rotationVector.y * maxDistance * 0.15 + relativeSpeed.y,
                    rotationVector.z * maxDistance * 0.15 + relativeSpeed.z);
            postApply.accept(particle);
        }

    }

    @Environment(EnvType.CLIENT)
    public static void spawnConicalParticlesFromFacing(World world,ParticleEffect effect, Vec2f facing, Vec3d startPos, Vec3d relativeSpeed, int count, float maxDistance, float angle) {
        for (int i = 0; i < count; ++i) {
            Random random = world.random;
            float angle2 = angle / 2;
            Vec3d rotationVector = EntityUtil.getRotationVector(MathHelper.nextBetween(random, -angle / 2, angle2) + facing.x, MathHelper.nextBetween(random, -angle / 2,
                    angle2) + facing.y);
            world.addParticle(effect,
                    startPos.x,
                    startPos.y,
                    startPos.z,
                    rotationVector.x * maxDistance * 0.15 + relativeSpeed.x,
                    rotationVector.y * maxDistance * 0.15 + relativeSpeed.y,
                    rotationVector.z * maxDistance * 0.15 + relativeSpeed.z);
        }

    }

//    @Environment(EnvType.CLIENT)
//    public static void spawnConicalParticlesFromFacing(World world, Vec3d facing, Vec3d startPos, Vec3d relativeSpeed, int count, float maxDistance, float angle) {
//        spawnConicalParticlesFromFacing(world, facing.,startPos,relativeSpeed,count,maxDistance,angle);
//    }

    @Environment(EnvType.CLIENT)
    public static void spawnDownwardParticles(World world, Vec3d startPos, Vec3d relativeSpeed, int count, float radius, Consumer<Particle> particleRenderer) {
        for (int i = 0; i < count; ++i) {
            Random random = world.random;
            particleRenderer.accept(MinecraftClient.getInstance().particleManager.addParticle(ParticleTypes.FIREWORK,
                    startPos.x,
                    startPos.y,
                    startPos.z,
                    random.nextGaussian() * radius * 0.01 + relativeSpeed.x,
                    -random.nextDouble() + relativeSpeed.y,
                    random.nextGaussian() * radius * 0.01 + relativeSpeed.z));
        }

    }

    public static void spawnDownwardParticles(World world, Vec3d startPos, Vec3d relativeSpeed, int count, float radius) {
        spawnDownwardParticles(world, startPos, relativeSpeed, count, radius, (p)->{});
    }

    @Environment(EnvType.CLIENT)
    public static void explodeBall(ClientWorld world, Vec3d pos, Random random, ParticleEffect type, double size, int amount, boolean important){
        explodeBall(world, pos, random, type, size, amount, important, (particle,speed) -> {});
    }

    @Environment(EnvType.CLIENT)
    public static void explodeBallRandomColor(ClientWorld world, Vec3d pos, Random random, ParticleEffect type, double size, int amount, boolean important){
        explodeBall(world, pos, random, type, size, amount, important, (particle,speed) -> particle.setColor(random.nextFloat(), random.nextFloat(), random.nextFloat()));
    }

    @Environment(EnvType.CLIENT)
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

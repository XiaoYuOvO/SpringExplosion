package net.xiaoyu233.spring_explosion.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

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

    public static Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

}

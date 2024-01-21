package net.xiaoyu233.spring_explosion.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class EntityUtil {
    public static void throwEntity(LivingEntity thrower, Entity entity, boolean copyYaw, boolean copyPitch) {
        Random random = thrower.getRandom();
        double d = thrower.getEyeY() - 0.30000001192092896;
        entity.refreshPositionAndAngles(thrower.getX(), d, thrower.getZ(), copyYaw ? thrower.getYaw() : 0, copyPitch ? thrower.getPitch() : 0);
        float g = MathHelper.sin(thrower.getPitch() * 0.017453292F);
        float h = MathHelper.cos(thrower.getPitch() * 0.017453292F);
        float i = MathHelper.sin(thrower.getYaw() * 0.017453292F);
        float j = MathHelper.cos(thrower.getYaw() * 0.017453292F);
        float k = random.nextFloat() * 6.2831855F;
        float l = 0.02F * random.nextFloat();
        entity.setVelocity((double)(-i * h * 0.3F) + Math.cos(k) * (double)l, -g * 0.3F + 0.1F + (random.nextFloat() - random.nextFloat()) * 0.1F, (double)(j * h * 0.3F) + Math.sin(k) * (double)l);
    }
}

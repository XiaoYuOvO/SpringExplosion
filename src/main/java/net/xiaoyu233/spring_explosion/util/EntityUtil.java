package net.xiaoyu233.spring_explosion.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import org.joml.Vector3f;

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

    public static Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

    public static Direction sideFromBoxCenter(Box box, Vec3d pos){
        Vec3d subtract = pos.subtract(box.getCenter());
        return Direction.getFacing(subtract.x, subtract.y, subtract.z);
    }

    public static Vec2f toEntityRotation(Direction direction){
        if (direction.getHorizontal() != -1){
            //Horizontal
            return new Vec2f(direction.asRotation(), 0);
        }else {
            return direction == Direction.UP ? new Vec2f(0, -90) : new Vec2f(0, 90);
        }
    }

    public static Vec3d toVec3d(Vector3f vector3f){
        return new Vec3d(vector3f.x(), vector3f.y(), vector3f.z());
    }
}

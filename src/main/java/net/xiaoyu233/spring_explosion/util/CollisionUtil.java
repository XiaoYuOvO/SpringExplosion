package net.xiaoyu233.spring_explosion.util;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class CollisionUtil {
    public static List<Entity> collideInConical(Entity source, World world, double radius, double angle, Predicate<? super Entity> predicate){
        return collideInConical(source, source.getRotationVector(), world, radius, angle, predicate);
    }
    public static List<Entity> collideInConical(Entity source,Vec3d rotationVec, World world, double radius, double angle, Predicate<? super Entity> predicate){
        List<Entity> result = Lists.newArrayList();
//        double k = rotationVec.z / rotationVec.x;
//        double a = source.getZ() - source.getX() * k;
//        double sqrt = Math.sqrt(k * k + a * a);
//        double sinRange = MathHelper.sin((float) angle / 2);
        double cosRange = MathHelper.cos((float) angle / 2);
        for (Entity otherEntity : world.getOtherEntities(source, source.getBoundingBox().stretch(rotationVec.multiply(radius)).expand(1.0, 1.0, 1.0), predicate)) {
            double squarededDistanceTo = otherEntity.squaredDistanceTo(source);
            if (squarededDistanceTo <= radius * radius) {
//                double distanceToViewLine = Math.abs(otherEntity.getZ()  - k * otherEntity.getX() - a) / sqrt;
                if (otherEntity.getPos().subtract(source.getPos()).normalize().dotProduct(rotationVec) > cosRange){
                    result.add(otherEntity);
                }
            }
        }
        return result;
    }
}

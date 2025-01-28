package net.xiaoyu233.spring_explosion.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class NbtUtil {
    public static Vec3d readVec3d(NbtCompound nbt, String key){
        NbtCompound nbtElement = nbt.getCompound(key);
        return new Vec3d(nbtElement.getDouble("x"), nbtElement.getDouble("y"), nbtElement.getDouble("z"));
    }

    public static Vector3f readVector3f(NbtCompound nbt, String key){
        NbtCompound nbtElement = nbt.getCompound(key);
        return new Vector3f(nbtElement.getFloat("x"), nbtElement.getFloat("y"), nbtElement.getFloat("z"));
    }

    public static void writeVector3f(NbtCompound nbt, String key, Vector3f vec){
        NbtCompound nbtElement = new NbtCompound();
        nbtElement.putFloat("x", vec.x());
        nbtElement.putFloat("y", vec.y());
        nbtElement.putFloat("z", vec.z());
        nbt.put(key, nbtElement);
    }

    public static Direction readDirection(NbtCompound nbt, String key){
        return Direction.byName(nbt.getString(key));
    }

    public static void writeDirection(NbtCompound nbt, String key, Direction dir){
        nbt.putString(key, dir.getName());
    }
}

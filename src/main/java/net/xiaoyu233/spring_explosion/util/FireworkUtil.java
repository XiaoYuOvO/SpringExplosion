package net.xiaoyu233.spring_explosion.util;

import net.minecraft.item.FireworkRocketItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.random.Random;

public class FireworkUtil {
    public static NbtCompound randomFirework(Random random) {
        NbtCompound nbtCompound = new NbtCompound();
        NbtList explosions = new NbtList();
        int explosionCount = random.nextInt(3) + 1;
        for (int i = 0; i < explosionCount; i++) {
            NbtCompound explosion = new NbtCompound();
            explosion.putInt("Type", random.nextInt(FireworkRocketItem.Type.values().length));
            explosion.putBoolean("Flicker", random.nextBoolean());
            explosion.putIntArray("Colors", randomColor(random));
            if (random.nextBoolean()) {
                explosion.putIntArray("FadeColor", randomColor(random));
            }
            explosions.add(explosion);
        }
        nbtCompound.put("Explosions", explosions);
        return nbtCompound;
    }

    private static int[] randomColor(Random r) {
        int size = r.nextInt(2) + 1;
        int[] color = new int[size];
        for (int i = 0; i < size; i++) {
            color[i] = r.nextInt(0xffffff);
        }
        return color;
    }
}

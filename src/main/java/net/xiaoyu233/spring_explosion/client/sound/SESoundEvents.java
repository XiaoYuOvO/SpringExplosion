package net.xiaoyu233.spring_explosion.client.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;

public class SESoundEvents {
    public static final SoundEvent SPARK_SWORD_ON = registerSound("item.spring_explosion.spark_sword.on");
    public static final SoundEvent GYRO_FIRING = registerSound("item.spring_explosion.gyro_firing");
    public static final SoundEvent FIREWORK_IGNITING = registerSound("item.spring_explosion.firework_igniting");
    public static final SoundEvent ROCKET_ACCELERATOR = registerSound("item.spring_explosion.rocket_accelerator");
    public static final SoundEvent FIREWORK_MORTAR_LAUNCH = registerSound("item.spring_explosion.firework_mortar_launch");

    public static void registerSounds(){

    }

    private static SoundEvent registerSound(String name){
        Identifier id = new Identifier(SpringExplosion.MOD_ID, name);
        SoundEvent event = SoundEvent.of(id);
        return Registry.register(Registries.SOUND_EVENT, id, event);
    }
}

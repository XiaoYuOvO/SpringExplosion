package net.xiaoyu233.spring_explosion.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.xiaoyu233.spring_explosion.SpringExplosion;

public class SEEffects {
    public static final StatusEffect OILY =  register("oily", new OilyEffect());
    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(SpringExplosion.MOD_ID, id), entry);
    }

    public static void register(){}
}

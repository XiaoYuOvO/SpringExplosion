package net.xiaoyu233.spring_explosion.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class OilyEffect extends StatusEffect {
    protected OilyEffect() {
        super(StatusEffectCategory.HARMFUL, 0x2B2B2B);
    }
}

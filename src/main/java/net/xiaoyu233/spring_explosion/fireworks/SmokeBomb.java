package net.xiaoyu233.spring_explosion.fireworks;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.xiaoyu233.spring_explosion.client.render.item.SmokeBombItemRenderer;
import net.xiaoyu233.spring_explosion.client.sound.SESoundEvents;
import net.xiaoyu233.spring_explosion.entity.BaseFireworkEntity;
import net.xiaoyu233.spring_explosion.entity.SEEntityTypes;
import net.xiaoyu233.spring_explosion.entity.SmokeBombEntity;
import net.xiaoyu233.spring_explosion.item.SmokeBombItem;
import net.xiaoyu233.spring_explosion.particle.SEParticles;
import net.xiaoyu233.spring_explosion.util.ParticleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SmokeBomb extends BaseFirework<SmokeBombEntity, SmokeBombItem, SmokeBombItemRenderer>{
    public static final SmokeBomb INSTANCE = new SmokeBomb();
    @Override
    public void onEntityFiring(SmokeBombEntity entity) {
        if (entity.getWorld().isClient){
            ParticleUtil.explodeBall((ClientWorld) entity.getWorld(), entity.getPos(), entity.getWorld().getRandom(), SEParticles.BATTLE_SMOKE, 0.2  * entity.getStrength(), (int) (4 * entity.getStrength()),true);
        }else if (entity.age % 10 == 0){
            entity.playSound(SESoundEvents.SMOKE_BOMB_FIRING, 1 * entity.getStrength(), 1);
        }
    }

    @Override
    public void onItemFiring(World world, ItemStack itemStack, LivingEntity user, int slot, float strength) {

    }

    @Override
    public Supplier<SmokeBombItemRenderer> getRenderer() {
        return SmokeBombItemRenderer::new;
    }

    @Override
    public int getFusingTime() {
        return 40;
    }

    @Override
    protected int getThrownUseCooldown() {
        return 60;
    }

    @Override
    public int getFiringTime() {
        return 100;
    }

    @Override
    protected @NotNull EntityType<SmokeBombEntity> getEntityType() {
        return SEEntityTypes.SMOKE_BOMB;
    }

    @Override
    public @NotNull FireworkUsage getFuseUsage() {
        return FireworkUsage.ENTITY;
    }

    @Override
    public @NotNull FireworkUsage getFireUsage() {
        return FireworkUsage.ENTITY;
    }

    @Override
    public FireworkItemToEntityAction<BaseFireworkEntity<?, ?>> getDropAction() {
        return FireworkItemToEntityAction.throwFirework();
    }
}

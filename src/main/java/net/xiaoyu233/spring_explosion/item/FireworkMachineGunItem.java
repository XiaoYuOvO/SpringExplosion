package net.xiaoyu233.spring_explosion.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.xiaoyu233.spring_explosion.client.render.item.FireworkMachineGunItemRenderer;
import net.xiaoyu233.spring_explosion.fireworks.FireworkMachineGun;

import java.util.UUID;

public class FireworkMachineGunItem extends BaseFireworkItem<FireworkMachineGun, FireworkMachineGunItem, FireworkMachineGunItemRenderer> {
    public FireworkMachineGunItem(Settings settings) {
        super(FireworkMachineGun.INSTANCE, settings);
    }

    private static final UUID SLOW_MODIFIER_ID = UUID.fromString("af90d67b-9c16-4e31-ad22-13e6e12d3a54");
    private static final ImmutableMultimap<EntityAttribute, EntityAttributeModifier> SLOW_ATTR = ImmutableMultimap.
            of(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    new EntityAttributeModifier(SLOW_MODIFIER_ID, "MachineGunSlow", -0.2d, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot != EquipmentSlot.MAINHAND && slot != EquipmentSlot.OFFHAND) return super.getAttributeModifiers(stack,slot);
        return SLOW_ATTR;
    }
}

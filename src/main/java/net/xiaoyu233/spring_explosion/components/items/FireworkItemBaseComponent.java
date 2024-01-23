package net.xiaoyu233.spring_explosion.components.items;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.item.ItemStack;

public class FireworkItemBaseComponent extends ItemComponent {
    private static final String FUSING_KEY = "Fusing";
    private static final String FIRING_KEY = "Firing";
    public boolean isFusing() {
        if (!this.hasTag(FUSING_KEY)) this.putBoolean(FUSING_KEY, false);
        return getBoolean(FUSING_KEY);
    }

    public boolean isUsed(){
        return isFusing() || isFiring();
    }

    public boolean isFiring() {
        if (!this.hasTag(FIRING_KEY)) this.putBoolean(FIRING_KEY, false);
        return getBoolean(FIRING_KEY);
    }

    public void setFusing(boolean fusing) {
        this.putBoolean(FUSING_KEY, fusing);
    }

    public void setFiring(boolean firing) {
        this.putBoolean(FIRING_KEY, firing);
    }


    public FireworkItemBaseComponent(ItemStack stack) {
        super(stack);
    }
}

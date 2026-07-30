package net.redreaper.twilight_spellbooks.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModUtils {

    public static float getEntitySpeed(LivingEntity entity) {
        if (entity != null) {
            float entityArmor = (float) (entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
            return entityArmor;
        }
        return 0;
    }
}

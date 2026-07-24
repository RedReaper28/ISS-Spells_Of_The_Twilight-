package net.redreaper.twilight_spellbooks.init;

import io.redspace.ironslib.registry.IronsLibRegistries;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.IronsWeaponTier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ModStaffMaterials  implements IronsWeaponTier {
    public static ModStaffMaterials KNIGHTMETAL;
    float damage;
    float speed;
    AttributeContainer[] attributeContainers;


    public ModStaffMaterials(float damage, float speed, AttributeContainer... attributeContainers) {
        this.damage = damage;
        this.speed = speed;
        this.attributeContainers = attributeContainers;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public float getSpeed() {
        return this.speed;
    }

    public AttributeContainer[] getAdditionalAttributes() {
        return this.attributeContainers;
    }

    static {
        KNIGHTMETAL = new ModStaffMaterials(9.0F, -3.0F, new AttributeContainer[]{
                new AttributeContainer(IronsLibRegistries.AttributeRegistry.ARMOR_PIERCE, 2, AttributeModifier.Operation.ADD_VALUE),
                new AttributeContainer(AttributeRegistry.SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)});
    }
}


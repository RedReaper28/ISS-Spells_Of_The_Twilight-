package net.redreaper.twilight_spellbooks.item.extended.magic_bow;

import io.redspace.ironslib.registry.IronsLibRegistries;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.IronsWeaponTier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ExtendedBowItem extends BowItem {
    public ExtendedBowItem(Properties pProperties) {
        super(pProperties);
    }

    public static ItemAttributeModifiers createAttributes(IronsWeaponTier pTier) {
        var builder = ItemAttributeModifiers.builder()
                .add(
                        IronsLibRegistries.AttributeRegistry.ARROW_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, pTier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                );
        for (AttributeContainer holder : pTier.getAdditionalAttributes()) {
            builder.add(holder.attribute(), holder.createModifier(EquipmentSlot.MAINHAND.getName()), EquipmentSlotGroup.MAINHAND);
        }
        return builder.build();
    }
}

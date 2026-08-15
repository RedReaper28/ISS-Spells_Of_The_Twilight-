package net.redreaper.twilight_spellbooks.item.curios.spellbooks;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Rarity;

public class CarminiteSpellBookItem extends SpellBook {
    public CarminiteSpellBookItem() {
        super(10, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1).rarity(Rarity.UNCOMMON));
        withSpellbookAttributes(new AttributeContainer(AttributeRegistry.LIGHTNING_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.MAX_MANA, 200, AttributeModifier.Operation.ADD_VALUE));
    }
}

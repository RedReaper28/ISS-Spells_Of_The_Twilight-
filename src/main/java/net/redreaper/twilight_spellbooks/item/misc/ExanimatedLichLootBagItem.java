package net.redreaper.twilight_spellbooks.item.misc;

import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.items.custom.LootBagItem;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.resources.ResourceLocation;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

public class ExanimatedLichLootBagItem extends LootBagItem {
    static ResourceLocation lootTable = ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "entities/ominous_lich_bag");

    public ExanimatedLichLootBagItem() {
        super(
                ItemPropertiesHelper.equipment(8).fireResistant().rarity(ASRarities.COSMIC_RARITY_PROXY.getValue()),
                lootTable
        );
    }
}

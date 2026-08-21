package net.redreaper.twilight_spellbooks.item.weapon;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.redreaper.twilight_spellbooks.init.ModExtendedWeaponTier;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LichGreatswordItem extends MagicSwordItem implements UniqueItem {
    public LichGreatswordItem() {
        super(
                ModExtendedWeaponTier.LICH_GREATSWORD,
                new Properties()
                        .stacksTo(1)
                        .rarity(ASRarities.COSMIC_RARITY_PROXY.getValue())
                        .fireResistant()
                        .attributes(ExtendedSwordItem.createAttributes(ModExtendedWeaponTier.LICH_GREATSWORD)
                        ),
                SpellDataRegistryHolder.of(
                        new SpellDataRegistryHolder(ModSpells.EXANIMATED_FIREBALL, 1)
                )
        );
    }



    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, TooltipContext context, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, context, lines, flag);
        var affinityData = AffinityData.getAffinityData(itemStack);
        if (!affinityData.affinityData().isEmpty()) {
            int i = TooltipsUtils.indexOfComponent(lines, "tooltip.irons_spellbooks.spellbook_spell_count");
            lines.addAll(i < 0 ? lines.size() : i + 1, affinityData.getDescriptionComponent());
        }
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
package net.redreaper.twilight_spellbooks.item.curios.spellbooks;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.acetheeldritchking.aces_spell_utils.items.curios.PassiveAbilityUniqueSpellbook;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExanimatedSpellbookItem extends PassiveAbilityUniqueSpellbook {
    public static final int COOLDOWN = 10*20;
    public ExanimatedSpellbookItem() {
        super(SpellDataRegistryHolder.of(
                new SpellDataRegistryHolder(ModSpells.EXANIMATED_RAY, 6),
                new SpellDataRegistryHolder(ModSpells.EXANIMATED_STEP, 4),
                new SpellDataRegistryHolder(ModSpells.EXANIMATED_TRIDENTS, 5)
        ), 12, new Properties().fireResistant().stacksTo(1).rarity(ASRarities.COSMIC_RARITY_PROXY.getValue()));
        withSpellbookAttributes(
                new AttributeContainer(AttributeRegistry.BLOOD_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.ENDER_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.FIRE_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.MAX_MANA, 250, AttributeModifier.Operation.ADD_VALUE));
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

    protected int getCooldownTicks() {
        return COOLDOWN;
    }
}

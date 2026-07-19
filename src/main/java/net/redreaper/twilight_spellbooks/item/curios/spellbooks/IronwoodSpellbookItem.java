package net.redreaper.twilight_spellbooks.item.curios.spellbooks;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.acetheeldritchking.aces_spell_utils.items.curios.PassiveAbilitySpellbook;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber
public class IronwoodSpellbookItem extends PassiveAbilitySpellbook {
    public IronwoodSpellbookItem() {
        super(8, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1).rarity(ASRarities.VERDANT_RARITY_PROXY.getValue()));
        withSpellbookAttributes(new AttributeContainer(AttributeRegistry.NATURE_MAGIC_RESIST, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.MAX_MANA, 150, AttributeModifier.Operation.ADD_VALUE));
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

    @SubscribeEvent
    public static void reduceDamage(LivingIncomingDamageEvent event) {
        var entity = event.getEntity();
        if (entity instanceof Player player) {
            if (ASUtils.hasCurio(player, ModItems.IRONWOOD_SPELLBOOK.get())) {
                float lvl = .15f;
                float before = event.getAmount();
                float multiplier = 1 - lvl;
                event.setAmount(event.getAmount() * multiplier);
            }
        }
    }

}

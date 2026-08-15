package net.redreaper.twilight_spellbooks.item.curios.spellbooks;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.acetheeldritchking.aces_spell_utils.items.curios.PassiveAbilitySpellbook;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import org.jetbrains.annotations.NotNull;
import twilightforest.init.TFMobEffects;

import java.util.List;

@EventBusSubscriber
public class SnowQueenSpellbookItem extends PassiveAbilitySpellbook {
    public static final int COOLDOWN = 10*20;
    public SnowQueenSpellbookItem() {
        super(12, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1).rarity(ASRarities.GLACIAL_RARITY_PROXY.getValue()));
        withSpellbookAttributes(new AttributeContainer(AttributeRegistry.ICE_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.HOLY_SPELL_POWER, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
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

    @SubscribeEvent
    public static void livingDamageEventPost(LivingDamageEvent.Post event) {
        var sourceEntity = event.getSource().getEntity();
        var target = event.getEntity();
        if (sourceEntity != null) {
            if (sourceEntity instanceof Player player) {
                if (ASUtils.hasCurio(player, ModItems.SNOW_QUEEN_SPELL_BOOK.get()) && (!player.getCooldowns().isOnCooldown(ModItems.SNOW_QUEEN_SPELL_BOOK.get()))) {
                    if (event.getSource().is(ISSDamageTypes.ICE_MAGIC)) {
                        target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY,5*20,2));
                        player.getCooldowns().addCooldown(ModItems.SNOW_QUEEN_SPELL_BOOK.get(), SnowQueenSpellbookItem.COOLDOWN);
                    }
                }
            }
        }
    }

    protected int getCooldownTicks() {
        return COOLDOWN;
    }
}

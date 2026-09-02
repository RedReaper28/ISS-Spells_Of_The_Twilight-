package net.redreaper.twilight_spellbooks.item.weapon;

import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.redreaper.twilight_spellbooks.init.ModExtendedWeaponTier;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import net.redreaper.twilight_spellbooks.spells.AbstractExanimatedSpell;

import java.util.List;

public class LichGreatswordItem extends MagicSwordItem {
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
                        new SpellDataRegistryHolder(ModSpells.EXANIMATED_FIREBALL, 3)
                )
        );
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class SpellEvents {
        @SubscribeEvent
        public static void onModifySpellLevel(ModifySpellLevelEvent event) {
            LivingEntity caster = event.getEntity();
            if (caster == null) return;

            if (!(event.getSpell() instanceof AbstractExanimatedSpell)) return;

            boolean fullSet =caster.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof LichGreatswordItem;

            if (fullSet) {
                event.addLevels(1);
            }
        }
    }


    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.irons_spellbooks.passive_ability_no_cooldown").withStyle(ChatFormatting.DARK_PURPLE));
            tooltipComponents.add(Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".desc")).withStyle(ChatFormatting.YELLOW));
        }
        else {
            tooltipComponents.add(Component.translatable("item.aces_spell_utils.more_details1").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
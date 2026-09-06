package net.redreaper.twilight_spellbooks.item.staffs.ultimateScepter;

import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.acetheeldritchking.aces_spell_utils.items.example.items.weapons.ASWeaponTiers;
import net.acetheeldritchking.aces_spell_utils.items.weapons.MagicGunItem;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.redreaper.twilight_spellbooks.init.ModDispatcher;
import net.redreaper.twilight_spellbooks.init.ModStaffMaterials;
import net.redreaper.twilight_spellbooks.item.weapon.LichGreatswordItem;
import net.redreaper.twilight_spellbooks.spells.AbstractScepterSpell;

import java.util.List;

public class UltimateScepterItem extends MagicGunItem {
    public final ModDispatcher dispatcher = new ModDispatcher();

    public UltimateScepterItem() {
        super(
                ASWeaponTiers.EXAMPLE_GUN,
                new Properties()
                        .stacksTo(1)
                        .fireResistant()
                        .rarity(ASRarities.COSMIC_RARITY_PROXY.getValue())
                        .attributes(ExtendedSwordItem.createAttributes(ModStaffMaterials.ULTIMATE_SCEPTER)),
                SpellDataRegistryHolder.of(
                )
        );
    }

    @Override
    public boolean isHeavyGun() {
        return true;
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class SpellEvents {
        @SubscribeEvent
        public static void onModifySpellLevel(ModifySpellLevelEvent event) {
            LivingEntity caster = event.getEntity();
            if (caster == null) return;

            if (!(event.getSpell() instanceof AbstractScepterSpell)) return;

            boolean fullSet =caster.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof UltimateScepterItem;

            if (fullSet) {
                event.addLevels(1);
            }
        }
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            this.dispatcher.idle(player, stack);
        }

    }
}
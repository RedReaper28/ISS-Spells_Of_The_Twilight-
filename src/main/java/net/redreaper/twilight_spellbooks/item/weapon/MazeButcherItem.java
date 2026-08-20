package net.redreaper.twilight_spellbooks.item.weapon;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModExtendedWeaponTier;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import org.jetbrains.annotations.NotNull;
import twilightforest.item.MinotaurAxeItem;

import java.util.List;

@EventBusSubscriber
public class MazeButcherItem extends MagicSwordItem {
    public MazeButcherItem() {
        super(
                ModExtendedWeaponTier.MAZE_BUTCHERER,
                ItemPropertiesHelper.equipment(1).fireResistant().rarity(Rarity.EPIC).attributes(ExtendedSwordItem.createAttributes(ModExtendedWeaponTier.MAZE_BUTCHERER)),
                SpellDataRegistryHolder.of(new SpellDataRegistryHolder(ModSpells.BEAST_LUNGE, 3))
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
        lines.add(Component.translatable("item.twilightforest.minotaur_axe.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @SubscribeEvent
    public static void onMinotaurAxeCharge(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        DamageContainer container = event.getContainer();
        if (!target.level().isClientSide() && container.getSource().getDirectEntity() instanceof LivingEntity living && living.isSprinting() && (container.getSource().getMsgId().equals("player") || container.getSource().getMsgId().equals("mob"))) {
            ItemStack weapon = living.getMainHandItem();
            if (!weapon.isEmpty() && weapon.getItem() instanceof MazeButcherItem) {
                container.setNewDamage(container.getOriginalDamage() + 10);
                // enchantment attack sparkles
                ((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
            }
        }
    }

}
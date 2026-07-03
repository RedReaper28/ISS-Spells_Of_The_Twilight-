package net.redreaper.twilight_spellbooks.item.staffs;

import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModStaffMaterials;

import java.util.List;

@EventBusSubscriber
public class KnightmetalStaff extends StaffItem {
    private static final float KNIGHTMETAL_MULT_DAMAGE = 0.20F;
    public KnightmetalStaff() {
        super(ItemPropertiesHelper.equipment(1).fireResistant().rarity(ASRarities.FORBIDDEN_RARITY_PROXY.getValue())
                .attributes(ExtendedSwordItem.createAttributes(ModStaffMaterials.KNIGHTMETAL)));
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.irons_spellbooks.passive_ability", new Object[]{Component.literal(Utils.timeFromTicks((float)this.getPassiveCooldownTicks(), 1)).withStyle(ChatFormatting.LIGHT_PURPLE)}).withStyle(ChatFormatting.DARK_PURPLE));
            tooltipComponents.add(Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".desc")).withStyle(Style.EMPTY.withColor(12900014)));
        }
        else {
            tooltipComponents.add(Component.translatable("item.aces_spell_utils.more_details1").withStyle(ChatFormatting.GRAY));
        }

    }

    @SubscribeEvent
    public static void increaseDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        var attacker = event.getSource().getEntity();
        if (attacker instanceof Player player) {
            ItemStack mainhandItem = player.getMainHandItem();
            if (target instanceof LivingEntity living) {
                if (mainhandItem.getItem() instanceof KnightmetalStaff) {
                    if (event.getSource() instanceof SpellDamageSource) {
                        if (target.getArmorValue() == 0) {
                            if (target.getArmorCoverPercentage() > 0) {
                                event.setAmount(event.getAmount() * KNIGHTMETAL_MULT_DAMAGE);
                            }
                            // enchantment attack sparkles
                            ((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
                        }
                    }
                }
            }
        }
    }

    protected int getPassiveCooldownTicks() {
        return 0;
    }
}

package net.redreaper.twilight_spellbooks.item.curios;

import io.redspace.ironslib.registry.IronsLibRegistries;
import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.acetheeldritchking.aces_spell_utils.items.curios.PassiveAbilitySpellbook;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
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
public class KnightMetalSpellbookItem extends PassiveAbilitySpellbook {
    private static final float KNIGHTMETAL_MULT_DAMAGE = 0.20F;
    public KnightMetalSpellbookItem() {
        super(12, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1).rarity(ASRarities.FORBIDDEN_RARITY_PROXY.getValue()));
        withSpellbookAttributes(new AttributeContainer(IronsLibRegistries.AttributeRegistry.ARMOR_PIERCE, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                new AttributeContainer(AttributeRegistry.MAX_MANA, 200, AttributeModifier.Operation.ADD_VALUE));
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
    public static void increaseDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        var attacker = event.getSource().getEntity();
        if (attacker instanceof Player player) {
            if (target instanceof LivingEntity living) {
                if (event.getSource() instanceof SpellDamageSource) {
                    if (ASUtils.hasCurio(player, ModItems.KNIGHTMETAL_SPELLBOOK.get())) {
                        if (target.getArmorValue() > 0) {
                            if (target.getArmorCoverPercentage() > 0) {
                                    int moreBonus = (int) (KNIGHTMETAL_MULT_DAMAGE * target.getArmorCoverPercentage());
                                    event.setAmount(event.getAmount() * moreBonus);
                            } else {
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


}

package net.redreaper.twilight_spellbooks.item.curios.sheath;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironslib.registry.IronsLibRegistries;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.acetheeldritchking.aces_spell_utils.items.curios.SheathCurioItem;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class SteeleafSheath extends SheathCurioItem {
    public static int COOLDOWN = 5 * 20;

    public SteeleafSheath() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE), null);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(AttributeRegistry.NATURE_SPELL_POWER, new AttributeModifier(id, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attr.put(AttributeRegistry.NATURE_MAGIC_RESIST, new AttributeModifier(id, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attr.put(IronsLibRegistries.AttributeRegistry.CRIT_DAMAGE, new AttributeModifier(id, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return attr;
    }

    @SubscribeEvent
    public static void increaseDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        var attacker = event.getSource().getEntity();
        if (attacker instanceof Player player) {
            if (target instanceof LivingEntity living) {
                if (ASUtils.hasCurio(player, ModItems.STEELEAF_SHEATH.get()) && (!player.getCooldowns().isOnCooldown(ModItems.STEELEAF_SHEATH.get()))) {
                    if (event.getSource().is(ISSDamageTypes.NATURE_MAGIC)) {
                        int randomNum = (int) (Math.random() * 5); // 0 to 4
                        if (randomNum ==1 ) {
                            float multiplier = 1 + 0.25f;
                            event.setAmount(event.getAmount() * multiplier);
                            player.getCooldowns().addCooldown(ModItems.STEELEAF_SHEATH.get(), SteeleafSheath.COOLDOWN);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected int getCooldownTicks() {
        return 5*20;
    }
}

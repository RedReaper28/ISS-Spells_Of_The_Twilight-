package net.redreaper.twilight_spellbooks.item.curios.body;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.items.curios.FlatCooldownPassiveAbilityCurio;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.redreaper.twilight_spellbooks.init.ModItems;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class CarminiteMantle  extends FlatCooldownPassiveAbilityCurio {
    public static final int COOLDOWN_IN_TICKS = 4 * 20;
    public CarminiteMantle() {
        super(ItemPropertiesHelper.equipment().stacksTo(1), null);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext
    slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(Attributes.SNEAKING_SPEED, new AttributeModifier(id, .15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attr.put(AttributeRegistry.LIGHTNING_SPELL_POWER, new AttributeModifier(id, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return attr;
    }

    @SubscribeEvent
    public static void refundMana(SpellOnCastEvent event) {
        var caster = event.getEntity();
        var spell = SpellRegistry.getSpell(event.getSpellId());
        if ((caster instanceof ServerPlayer player)) {
            if (ASUtils.hasCurio(player, ModItems.CARMINITE_MANTLE.get()) && (!player.getCooldowns().isOnCooldown(ModItems.CARMINITE_MANTLE.get()))) {
                if (spell.getSchoolType() == SchoolRegistry.LIGHTNING.get()) {
                    caster.addEffect(new MobEffectInstance(MobEffectRegistry.TRUE_INVISIBILITY, 3*20, 0, true, true, true));
                    caster.addEffect(new MobEffectInstance(MobEffectRegistry.CHARGED, 3*20, 0, true, true, true));
                    player.getCooldowns().addCooldown(ModItems.CARMINITE_MANTLE.get(), CarminiteMantle.COOLDOWN_IN_TICKS);
                }
            }
        }
    }


    @Override
    protected int getCooldownTicks() {
        return COOLDOWN_IN_TICKS;
    }
}


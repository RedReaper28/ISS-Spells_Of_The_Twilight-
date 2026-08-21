package net.redreaper.twilight_spellbooks.item.curios.body;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class NagaMantle extends SimpleDescriptiveCurio {
    public NagaMantle() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.RARE));
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(Attributes.BURNING_TIME, new AttributeModifier(id, -.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attr.put(AttributeRegistry.FIRE_MAGIC_RESIST, new AttributeModifier(id, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return attr;
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        var livingEntity = event.getEntity();
        if ((livingEntity instanceof ServerPlayer player)) {
            if (ASUtils.hasCurio(player, ModItems.NAGA_MANTLE.get())) {
                if (event.getSource().is(DamageTypeTags.IS_FIRE) || event.getSource().is(ISSDamageTypes.FIRE_MAGIC )) {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5*20, 0, true, true, true));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5*20, 0, true, true, true));
                    return;
                }
            }
        }
    }

}

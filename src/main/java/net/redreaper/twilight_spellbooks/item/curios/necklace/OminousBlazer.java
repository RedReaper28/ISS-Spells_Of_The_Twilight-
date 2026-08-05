package net.redreaper.twilight_spellbooks.item.curios.necklace;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class OminousBlazer extends SimpleDescriptiveCurio {
    public OminousBlazer() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.UNCOMMON), Curios.NECKLACE_SLOT);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(AttributeRegistry.FIRE_SPELL_POWER, new AttributeModifier(id, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attr.put(AttributeRegistry.ENDER_SPELL_POWER, new AttributeModifier(id, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attr.put(AttributeRegistry.SUMMON_DAMAGE, new AttributeModifier(id, 0.10F, AttributeModifier.Operation.ADD_VALUE));
        return attr;
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity entityTarget = event.getEntity();
        Entity entityAttacker = event.getSource().getDirectEntity();

        if (entityAttacker instanceof IMagicSummon summon && summon.getSummoner() instanceof Player summoner) {
            if (ASUtils.hasCurio(summoner, ModItems.OMINOUS_BLAZER.get())) {
                entityTarget.addEffect(new MobEffectInstance(ModMobEffects.OMINOUS_BURN, 3*20, 0, true, true, true));
            }
        }
    }
}

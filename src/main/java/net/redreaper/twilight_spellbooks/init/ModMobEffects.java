package net.redreaper.twilight_spellbooks.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.effect.HydraFireEffect;
import net.redreaper.twilight_spellbooks.effect.HydraRevengeEffect;
import net.redreaper.twilight_spellbooks.effect.OminousBurnEffect;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect>MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> OMINOUS_BURN = MOB_EFFECT_DEFERRED_REGISTER.register("ominous_burn", () -> new OminousBurnEffect(MobEffectCategory.HARMFUL, 8084223));
    public static final DeferredHolder<MobEffect, MobEffect> HYDRA_REVENGE = MOB_EFFECT_DEFERRED_REGISTER.register("hydra_revenge", () -> new HydraRevengeEffect(MobEffectCategory.HARMFUL, 16775830));
    public static final DeferredHolder<MobEffect, MobEffect> HYDRA_FIRE = MOB_EFFECT_DEFERRED_REGISTER.register("hydra_fire", () -> new HydraFireEffect(MobEffectCategory.HARMFUL, 5505115)
            .addAttributeModifier(Attributes.ARMOR, TwilightSpellbooks.id("mobeffect_hydra_fire"), HydraFireEffect.ARMOR_PER_LEVEL, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));


    public static void register(IEventBus eventBus) {
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }
}

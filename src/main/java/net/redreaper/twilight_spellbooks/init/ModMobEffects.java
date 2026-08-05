package net.redreaper.twilight_spellbooks.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.effect.OminousBurnEffect;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect>MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> OMINOUS_BURN = MOB_EFFECT_DEFERRED_REGISTER.register("ominous_burn", () -> new OminousBurnEffect(MobEffectCategory.HARMFUL, 8084223));


    public static void register(IEventBus eventBus) {
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }
}

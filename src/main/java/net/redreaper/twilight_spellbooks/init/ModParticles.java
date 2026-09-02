package net.redreaper.twilight_spellbooks.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, TwilightSpellbooks.MOD_ID);

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

    public static final Supplier<SimpleParticleType> MOSQUITO_PARTICLE = PARTICLE_TYPES.register("mosquito", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> EXANIMATED_SMOKE_PARTICLE = PARTICLE_TYPES.register("exanimated_smoke", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> EXANIMATED_FIRE_PARTICLE = PARTICLE_TYPES.register("exanimated_fire", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> EXANIMATED_EMBER_PARTICLE = PARTICLE_TYPES.register("exanimated_embers", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> HYDRA_FIRE_PARTICLE = PARTICLE_TYPES.register("hydra_fire", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> HYDRA_EMBER_PARTICLE = PARTICLE_TYPES.register("hydra_embers", () -> new SimpleParticleType(false));

}

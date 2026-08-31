package net.redreaper.twilight_spellbooks.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

public class ModDamageTypes {
    public static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, name));
    }

    // Spell School Related
    public static final ResourceKey<DamageType> EXANIMATED_MAGIC = register("exanimated_magic");

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(EXANIMATED_MAGIC, new DamageType(EXANIMATED_MAGIC.location().getPath(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));

    }
}

package net.redreaper.twilight_spellbooks.init;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

import java.util.function.Supplier;

import static io.redspace.ironsspellbooks.api.registry.SchoolRegistry.SCHOOL_REGISTRY_KEY;

public class ModSpellSubSchool {
    private static final DeferredRegister<SchoolType> HNS_SCHOOLS = DeferredRegister.create(SCHOOL_REGISTRY_KEY, TwilightSpellbooks.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        HNS_SCHOOLS.register(eventBus);
    }

    private static Supplier<SchoolType> registerSchool(SchoolType type)
    {
        return HNS_SCHOOLS.register(type.getId().getPath(), () -> type);
    }

    public static final ResourceLocation EXANIMATED_RESOURCE = TwilightSpellbooks.id("exanimated");
    public static final Supplier<SchoolType> EXANIMATED = registerSchool(new SchoolType
            (
                    EXANIMATED_RESOURCE,
                    ModTags.Items.EXANIMATED_FOCUS,
                    Component.translatable("school.twilight_spellbooks.exanimated").withStyle(Style.EMPTY.withColor(11436779)),
                    AttributeRegistry.FIRE_SPELL_POWER,
                    AttributeRegistry.FIRE_MAGIC_RESIST,
                    SoundRegistry.FIRE_CAST,
                    ModDamageTypes.EXANIMATED_MAGIC
            ));

}

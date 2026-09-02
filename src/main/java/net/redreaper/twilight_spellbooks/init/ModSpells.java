package net.redreaper.twilight_spellbooks.init;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.spells.*;

import java.util.function.Supplier;

import static io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY;

public class ModSpells  {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SPELL_REGISTRY_KEY, TwilightSpellbooks.MOD_ID);


    public static Supplier<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    //BLOOD
    public static final Supplier<AbstractSpell> LIFE_SACRIFICE = registerSpell(new LifeSacrificeSpell());
    public static final Supplier<AbstractSpell> MOSQUITO_SWARM = registerSpell(new MosquitoSwarmSpell());
    public static final Supplier<AbstractSpell> SUMMON_LOYAL_ZOMBIE = registerSpell(new SummonLoyalZombieSpell());

    //ENDER
    public static final Supplier<AbstractSpell> SUMMON_DEATH_TOME = registerSpell(new SummonDeathLibrarySpell());
    public static final Supplier<AbstractSpell> TWILIGHT_BOLT = registerSpell(new TwilightBoltSpell());

    //EXANIMATED?
    public static final Supplier<AbstractSpell> EXANIMATED_FIREBALL = registerSpell(new ExanimatedFireballSpell());
    public static final Supplier<AbstractSpell> EXANIMATED_RAY = registerSpell(new ExanimatedRaySpell());
    public static final Supplier<AbstractSpell> EXANIMATED_STEP = registerSpell(new ExanimatedStepSpell());
    public static final Supplier<AbstractSpell> EXANIMATED_TRIDENTS = registerSpell(new ExanimatedTridentsSpell());

    //FIRE
    public static final Supplier<AbstractSpell> FIRE_BETTLE_SPIT = registerSpell(new FireBettleSpitSpell());

    //HOLY
    public static final Supplier<AbstractSpell> FORTIFYING_SHIELDS = registerSpell(new FortifyingShieldsSpell());

    //ICE
    public static final Supplier<AbstractSpell> AVALANCHE = registerSpell(new AvalancheSpell());
    public static final Supplier<AbstractSpell> ICE_BOMB = registerSpell(new IceBombSpell());
    public static final Supplier<AbstractSpell> SUMMON_WINTER_WOLVES = registerSpell(new SummonWinterWolvesSpell());

    //LIGHTING
    public static final Supplier<AbstractSpell> CARMINITE_PULL = registerSpell(new CarminitePullSpell());
    public static final Supplier<AbstractSpell> SUMMON_CARMINITE_GOLEMS = registerSpell(new SummonCarminiteGolemSpell());

    //NATURE
    public static final Supplier<AbstractSpell> DRUID_BOLT = registerSpell(new DruidBoltSpell());
    public static final Supplier<AbstractSpell> BEAST_LUNGE = registerSpell(new MinotaurLungeSpell());
    public static final Supplier<AbstractSpell> HYDRA_MORTAR_SHOT = registerSpell(new HydraMortarShotSpell());

    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
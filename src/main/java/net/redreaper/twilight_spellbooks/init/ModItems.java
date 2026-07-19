package net.redreaper.twilight_spellbooks.init;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.item.curios.ring.FieryRing;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.IronwoodSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.KnightMetalSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.fierySpellbook.FierySpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.ring.ThornRoseRing;
import net.redreaper.twilight_spellbooks.item.staffs.KnightmetalStaff;

import java.util.Collection;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS= DeferredRegister.createItems(TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<Item, Item> IRONWOOD_SPELLBOOK = ITEMS.register("ironwood_spellbook",
            IronwoodSpellbookItem::new);
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_SPELLBOOK = ITEMS.register("knightmetal_spellbook",
            KnightMetalSpellbookItem::new);
    public static final DeferredHolder<Item, Item> FIERY_SPELL_BOOK = ITEMS.register("fiery_spell_book",
            FierySpellbookItem::new);

    public static final DeferredHolder<Item, Item> THORN_ROSE_RING = ITEMS.register("thorn_rose_ring",
            ThornRoseRing::new);
    public static final DeferredHolder<Item, Item> FIERY_RING = ITEMS.register("fiery_ring",
            FieryRing::new);

    public static final DeferredHolder<Item, Item> KNIGHTMETAL_STAFF = ITEMS.register("knightmetal_staff",
            KnightmetalStaff::new);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getModItems()
    {
        return ITEMS.getEntries();
    }
}

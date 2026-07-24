package net.redreaper.twilight_spellbooks.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.item.curios.ring.FieryRing;
import net.redreaper.twilight_spellbooks.item.curios.ring.IronwoodRing;
import net.redreaper.twilight_spellbooks.item.curios.ring.KnightMetalRing;
import net.redreaper.twilight_spellbooks.item.curios.sheath.SteeleafSheath;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.IronwoodSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.KnightMetalSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.fierySpellbook.FierySpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.ring.ThornRoseRing;
import net.redreaper.twilight_spellbooks.item.staffs.KnightmetalStaff;

import java.util.Collection;

public class ModItems {
    public static final DeferredRegister.Items ITEMS= DeferredRegister.createItems(TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<Item, Item> AURORA_SHARD= ITEMS.register("aurora_shard",
            ()->new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> IRONWOOD_SPELLBOOK = ITEMS.register("ironwood_spellbook",
            IronwoodSpellbookItem::new);
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_SPELLBOOK = ITEMS.register("knightmetal_spellbook",
            KnightMetalSpellbookItem::new);
    public static final DeferredHolder<Item, Item> FIERY_SPELL_BOOK = ITEMS.register("fiery_spell_book",
            FierySpellbookItem::new);



    public static final DeferredHolder<Item, Item> IRONWOOD_RING = ITEMS.register("ironwood_ring",
            IronwoodRing::new);
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_RING = ITEMS.register("knightmetal_ring",
            KnightMetalRing::new);
    public static final DeferredHolder<Item, Item> FIERY_RING = ITEMS.register("fiery_ring",
            FieryRing::new);
    public static final DeferredHolder<Item, Item> THORN_ROSE_RING = ITEMS.register("thorn_rose_ring",
            ThornRoseRing::new);

    public static final DeferredHolder<Item, Item> KNIGHTMETAL_STAFF = ITEMS.register("knightmetal_staff",
            KnightmetalStaff::new);

    public static final DeferredHolder<Item, Item> STEELEAF_SHEATH = ITEMS.register("steeleaf_sheath",
            SteeleafSheath::new);

    public static Collection<DeferredHolder<Item, ? extends Item>> getSOTItems()
    {
        return ITEMS.getEntries();
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getModItems()
    {
        return ITEMS.getEntries();
    }
}

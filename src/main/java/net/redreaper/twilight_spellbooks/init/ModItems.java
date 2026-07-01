package net.redreaper.twilight_spellbooks.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.item.fierySpellbook.FierySpellbookItem;

import java.util.Collection;

public class ModItems {
    public static final DeferredRegister.Items ITEMS= DeferredRegister.createItems(TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<Item, Item> FIERY_SPELL_BOOK = ITEMS.register("fiery_spell_book",
            FierySpellbookItem::new);


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getModItems()
    {
        return ITEMS.getEntries();
    }
}

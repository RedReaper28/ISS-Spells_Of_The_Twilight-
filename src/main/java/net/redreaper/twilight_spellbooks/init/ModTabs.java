package net.redreaper.twilight_spellbooks.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

import java.util.function.Supplier;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB=
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TwilightSpellbooks.MOD_ID);


    public static final Supplier<CreativeModeTab> MONSTERS_AND_GEAR_TAB=CREATIVE_MODE_TAB.register("twilight_spellbooks",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.FIERY_SPELL_BOOK.get()))
                    .title(Component.translatable("creative_tab.twilight_spellbooks.backported_spellbooks"))
                    .displayItems((itemDisplayParameters, output) ->{
                        output.accept(ModItems.IRONWOOD_SPELLBOOK.get());
                        output.accept(ModItems.KNIGHTMETAL_SPELLBOOK.get());
                        output.accept(ModItems.FIERY_SPELL_BOOK.get());
                        output.accept(ModItems.KNIGHTMETAL_STAFF.get());

                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
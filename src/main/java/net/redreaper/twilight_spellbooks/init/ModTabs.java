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
                    .title(Component.translatable("creative_tab.twilight_spellbooks.twilight_spellbooks"))
                    .displayItems((itemDisplayParameters, output) ->{
                        output.accept(ModItems.IRONWOOD_SPELLBOOK.get());
                        output.accept(ModItems.KNIGHTMETAL_SPELLBOOK.get());
                        output.accept(ModItems.CARMINITE_SPELL_BOOK.get());
                        output.accept(ModItems.FIERY_SPELL_BOOK.get());
                        output.accept(ModItems.SNOW_QUEEN_SPELL_BOOK.get());

                        output.accept(ModItems.STEELEAF_STAFF.get());
                        output.accept(ModItems.KNIGHTMETAL_STAFF.get());
                        output.accept(ModItems.MAZE_BUTCHERER.get());
                        output.accept(ModItems.LICH_GREATSWORD.get());

                        output.accept(ModItems.TARNISHED_LICH_CROWN.get());
                        output.accept(ModItems.TARNISHED_QUEEN_CROWN.get());
                        output.accept(ModItems.EXANIMATED_LICH_HELMET.get());
                        output.accept(ModItems.EXANIMATED_LICH_CHESTPLATE.get());
                        output.accept(ModItems.EXANIMATED_LICH_LEGGINGS.get());
                        output.accept(ModItems.EXANIMATED_LICH_BOOTS.get());

                        output.accept(ModItems.IRONWOOD_RING.get());
                        output.accept(ModItems.FIERY_RING.get());
                        output.accept(ModItems.THORN_ROSE_RING.get());
                        output.accept(ModItems.NAGA_MANTLE.get());
                        output.accept(ModItems.CARMINITE_MANTLE.get());
                        output.accept(ModItems.KNIGHTMETAL_BRACE.get());
                        output.accept(ModItems.YETI_BRACE.get());
                        output.accept(ModItems.OMINOUS_BLAZER.get());
                        output.accept(ModItems.STEELEAF_SHEATH.get());

                        output.accept(ModItems.IRONWOOD_HELVE.get());
                        output.accept(ModItems.FROST_ESSENCE.get());
                        output.accept(ModItems.SNOW_SILVER_INGOT.get());
                        output.accept(ModItems.LICH_PHYLACTERY.get());
                        output.accept(ModItems.AURORA_PHYLACTERY.get());
                        output.accept(ModItems.AURORA_SHARD.get());
                        output.accept(ModItems.DEATHS_ESSENCE.get());


                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
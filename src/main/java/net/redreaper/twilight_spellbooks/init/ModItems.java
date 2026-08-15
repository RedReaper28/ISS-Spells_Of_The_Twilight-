package net.redreaper.twilight_spellbooks.init;

import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.item.armor.ExanimatedLichArmorItem;
import net.redreaper.twilight_spellbooks.item.armor.TarnishedLichCrownItem;
import net.redreaper.twilight_spellbooks.item.curios.necklace.OminousBlazer;
import net.redreaper.twilight_spellbooks.item.curios.ring.FieryRing;
import net.redreaper.twilight_spellbooks.item.curios.ring.IronwoodRing;
import net.redreaper.twilight_spellbooks.item.curios.ring.KnightMetalRing;
import net.redreaper.twilight_spellbooks.item.curios.sheath.SteeleafSheath;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.IronwoodSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.KnightMetalSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.SnowQueenSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.fierySpellbook.FierySpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.ring.ThornRoseRing;
import net.redreaper.twilight_spellbooks.item.staffs.KnightmetalStaff;
import net.redreaper.twilight_spellbooks.item.weapon.LichGreatswordItem;

import java.util.Collection;

public class ModItems {
    public static final DeferredRegister.Items ITEMS= DeferredRegister.createItems(TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<Item, Item> AURORA_SHARD= ITEMS.register("aurora_shard",
            () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON).fireResistant()));

    public static final DeferredHolder<Item, Item> FROST_ESSENCE = ITEMS.register("frost_essence",
            () -> new Item(ItemPropertiesHelper.material()));
    public static final DeferredHolder<Item, Item> SNOW_SILVER_INGOT = ITEMS.register("snow_silver_ingot",
            () -> new Item(ItemPropertiesHelper.material()));


    public static final DeferredHolder<Item, Item> LICH_PHYLACTERY= ITEMS.register("lich_phylactery",
            () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final DeferredHolder<Item, Item> AURORA_PHYLACTERY= ITEMS.register("aurora_phylactery",
            () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON).fireResistant()));

    public static final DeferredHolder<Item, Item> IRONWOOD_SPELLBOOK = ITEMS.register("ironwood_spellbook",
            IronwoodSpellbookItem::new);
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_SPELLBOOK = ITEMS.register("knightmetal_spellbook",
            KnightMetalSpellbookItem::new);
    public static final DeferredHolder<Item, Item> FIERY_SPELL_BOOK = ITEMS.register("fiery_spell_book",
            FierySpellbookItem::new);
    public static final DeferredHolder<Item, Item> SNOW_QUEEN_SPELL_BOOK = ITEMS.register("snow_queen_spell_book",
            SnowQueenSpellbookItem::new);



    public static final DeferredHolder<Item, Item> IRONWOOD_RING = ITEMS.register("ironwood_ring",
            IronwoodRing::new);
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_RING = ITEMS.register("knightmetal_ring",
            KnightMetalRing::new);
    public static final DeferredHolder<Item, Item> FIERY_RING = ITEMS.register("fiery_ring",
            FieryRing::new);
    public static final DeferredHolder<Item, Item> THORN_ROSE_RING = ITEMS.register("thorn_rose_ring",
            ThornRoseRing::new);

    public static final DeferredHolder<Item, Item> OMINOUS_BLAZER = ITEMS.register("ominous_blazer",
            OminousBlazer::new);
    public static final DeferredHolder<Item, Item> STEELEAF_SHEATH = ITEMS.register("steeleaf_sheath",
            SteeleafSheath::new);

    public static final DeferredHolder<Item, Item> KNIGHTMETAL_STAFF = ITEMS.register("knightmetal_staff",
            KnightmetalStaff::new);

    public static final DeferredHolder<Item, Item> LICH_GREATSWORD = ITEMS.register("lich_greatsword",
            LichGreatswordItem::new);

    public static final DeferredHolder<Item, Item> EXANIMATED_LICH_HELMET = ITEMS.register("exanimated_lich_helmet",
            () -> new ExanimatedLichArmorItem(   ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC).durability(ArmorItem.Type.HELMET.getDurability(48))));
    public static final DeferredHolder<Item, Item> EXANIMATED_LICH_CHESTPLATE = ITEMS.register("exanimated_lich_chestplate",
            () -> new ExanimatedLichArmorItem(   ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC).durability(ArmorItem.Type.CHESTPLATE.getDurability(48))));
    public static final DeferredHolder<Item, Item> EXANIMATED_LICH_LEGGINGS = ITEMS.register("exanimated_lich_leggings",
            () -> new ExanimatedLichArmorItem(   ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC).durability(ArmorItem.Type.LEGGINGS.getDurability(48))));
    public static final DeferredHolder<Item, Item> EXANIMATED_LICH_BOOTS = ITEMS.register("exanimated_lich_boots",
            () -> new ExanimatedLichArmorItem(   ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC).durability(ArmorItem.Type.BOOTS.getDurability(48))));

    public static final DeferredHolder<Item, Item> TARNISHED_LICH_CROWN = ITEMS.register("tarnished_lich_crown",
            () -> new TarnishedLichCrownItem(   ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.HELMET.getDurability(48))));



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

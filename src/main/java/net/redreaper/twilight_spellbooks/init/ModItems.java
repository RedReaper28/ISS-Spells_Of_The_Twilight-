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
import net.redreaper.twilight_spellbooks.item.armor.KnightmetalWarmageArmorItem;
import net.redreaper.twilight_spellbooks.item.armor.TarnishedLichCrownItem;
import net.redreaper.twilight_spellbooks.item.armor.TarnishedQueenCrownItem;
import net.redreaper.twilight_spellbooks.item.curios.body.CarminiteMantle;
import net.redreaper.twilight_spellbooks.item.curios.body.NagaMantle;
import net.redreaper.twilight_spellbooks.item.curios.braces.KnightMetalBrace;
import net.redreaper.twilight_spellbooks.item.curios.braces.YetiBrace;
import net.redreaper.twilight_spellbooks.item.curios.necklace.OminousBlazer;
import net.redreaper.twilight_spellbooks.item.curios.ring.FieryRing;
import net.redreaper.twilight_spellbooks.item.curios.ring.IronwoodRing;
import net.redreaper.twilight_spellbooks.item.curios.ring.KnightMetalRing;
import net.redreaper.twilight_spellbooks.item.curios.sheath.SteeleafSheath;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.CarminiteSpellBookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.IronwoodSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.KnightMetalSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.SnowQueenSpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.fierySpellbook.FierySpellbookItem;
import net.redreaper.twilight_spellbooks.item.curios.ring.ThornRoseRing;
import net.redreaper.twilight_spellbooks.item.misc.ExanimatedLichLootBagItem;
import net.redreaper.twilight_spellbooks.item.staffs.KnightmetalStaff;
import net.redreaper.twilight_spellbooks.item.staffs.SteeleafStaff;
import net.redreaper.twilight_spellbooks.item.weapon.LichGreatswordItem;
import net.redreaper.twilight_spellbooks.item.weapon.MazeButcherItem;

import java.util.Collection;

public class ModItems {
    public static final DeferredRegister.Items ITEMS= DeferredRegister.createItems(TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<Item, Item> IRONWOOD_HELVE= ITEMS.register("ironwood_helve",
            () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.RARE).fireResistant()));
    public static final DeferredHolder<Item, Item> AURORA_SHARD= ITEMS.register("aurora_shard",
            () -> new Item(ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final DeferredHolder<Item, Item> DEATHS_ESSENCE= ITEMS.register("deaths_essence",
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
    public static final DeferredHolder<Item, Item> CARMINITE_SPELL_BOOK = ITEMS.register("carminite_spell_book",
            CarminiteSpellBookItem::new);
    public static final DeferredHolder<Item, Item> FIERY_SPELL_BOOK = ITEMS.register("fiery_spell_book",
            FierySpellbookItem::new);
    public static final DeferredHolder<Item, Item> SNOW_QUEEN_SPELL_BOOK = ITEMS.register("snow_queen_spell_book",
            SnowQueenSpellbookItem::new);

    public static final DeferredHolder<Item, Item> EXANIMATED_LICH_LOOT_BAG = ITEMS.register("exanimated_lich_loot_bag",
            ExanimatedLichLootBagItem::new);


    public static final DeferredHolder<Item, Item> IRONWOOD_RING = ITEMS.register("ironwood_ring",
            IronwoodRing::new);
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_RING = ITEMS.register("knightmetal_ring",
            KnightMetalRing::new);
    public static final DeferredHolder<Item, Item> FIERY_RING = ITEMS.register("fiery_ring",
            FieryRing::new);
    public static final DeferredHolder<Item, Item> THORN_ROSE_RING = ITEMS.register("thorn_rose_ring",
            ThornRoseRing::new);

    public static final DeferredHolder<Item, Item> NAGA_MANTLE = ITEMS.register("naga_mantle",
            NagaMantle::new);
    public static final DeferredHolder<Item, Item> CARMINITE_MANTLE = ITEMS.register("carminite_mantle",
            CarminiteMantle::new);
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_BRACE = ITEMS.register("knightmetal_brace",
            KnightMetalBrace::new);
    public static final DeferredHolder<Item, Item> YETI_BRACE = ITEMS.register("yeti_brace",
            YetiBrace::new);
    public static final DeferredHolder<Item, Item> OMINOUS_BLAZER = ITEMS.register("ominous_blazer",
            OminousBlazer::new);
    public static final DeferredHolder<Item, Item> STEELEAF_SHEATH = ITEMS.register("steeleaf_sheath",
            SteeleafSheath::new);

    public static final DeferredHolder<Item, Item> KNIGHTMETAL_STAFF = ITEMS.register("knightmetal_staff",
            KnightmetalStaff::new);
    public static final DeferredHolder<Item, Item> STEELEAF_STAFF = ITEMS.register("steeleaf_staff",
            SteeleafStaff::new);

    public static final DeferredHolder<Item, Item> MAZE_BUTCHERER = ITEMS.register("maze_butcher",
            MazeButcherItem::new);
    public static final DeferredHolder<Item, Item> LICH_GREATSWORD = ITEMS.register("lich_greatsword",
            LichGreatswordItem::new);

    public static final DeferredHolder<Item, Item> KNIGHTMETAL_MAGE_HELMET = ITEMS.register("knightmetal_mage_helmet",
            () -> new KnightmetalWarmageArmorItem(   ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(48))));
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_MAGE_CHESTPLATE = ITEMS.register("knightmetal_mage_chestplate",
            () -> new KnightmetalWarmageArmorItem(   ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(48))));
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_MAGE_LEGGINGS = ITEMS.register("knightmetal_mage_leggings",
            () -> new KnightmetalWarmageArmorItem(   ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(48))));
    public static final DeferredHolder<Item, Item> KNIGHTMETAL_MAGE_BOOTS = ITEMS.register("knightmetal_mage_boots",
            () -> new KnightmetalWarmageArmorItem(   ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(48))));

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
    public static final DeferredHolder<Item, Item> TARNISHED_QUEEN_CROWN = ITEMS.register("tarnished_queen_crown",
            () -> new TarnishedQueenCrownItem(   ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).rarity(Rarity.UNCOMMON).durability(ArmorItem.Type.HELMET.getDurability(48))));



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

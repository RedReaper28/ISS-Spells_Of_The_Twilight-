package net.redreaper.twilight_spellbooks.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

public class ModTags {
    public static class Items{

        public static final TagKey<Item> EXANIMATED_FOCUS=createTag("exanimated_focus");


        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID,name));
        }
    }
}

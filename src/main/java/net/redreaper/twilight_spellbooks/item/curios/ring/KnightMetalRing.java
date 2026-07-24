package net.redreaper.twilight_spellbooks.item.curios.ring;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironslib.registry.IronsLibRegistries;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class KnightMetalRing extends CurioBaseItem {
    public KnightMetalRing() {
        super(ItemPropertiesHelper.equipment().stacksTo(1));
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(IronsLibRegistries.AttributeRegistry.ARMOR_PIERCE, new AttributeModifier(id, 3, AttributeModifier.Operation.ADD_VALUE));
        return attr;
    }
}

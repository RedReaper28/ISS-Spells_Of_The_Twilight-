package net.redreaper.twilight_spellbooks.item.armor;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ArmorMaterialRegistry;
import net.acetheeldritchking.aces_spell_utils.entity.render.armor.EmissiveGenericCustomArmorRenderer;
import net.acetheeldritchking.aces_spell_utils.items.example.items.armor.ImbuableExtendedGeoArmorItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.List;

public class FieryWarmageArmorItem extends ImbuableExtendedGeoArmorItem {
    public FieryWarmageArmorItem(Type slot, Properties settings) {
        super(ArmorMaterialRegistry.NETHERITE_BATTLEMAGE, slot, settings,
                new AttributeContainer(AttributeRegistry.MAX_MANA, 125, AttributeModifier.Operation.ADD_VALUE),
                new AttributeContainer(AttributeRegistry.FIRE_SPELL_POWER, 0.15, AttributeModifier.Operation.ADD_VALUE),
                new AttributeContainer(AttributeRegistry.SPELL_POWER, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !level.isClientSide() && isWearingFullSet(player)) {
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player) {
        if (!player.hasEffect(ModMobEffects.HYDRA_REVENGE)) {
            player.addEffect(new MobEffectInstance(ModMobEffects.HYDRA_REVENGE, 10*20, 0, false, false, false));
        }
    }

    private boolean isWearingFullSet(Player player) {
        return player.getItemBySlot(Type.HELMET.getSlot()).getItem() instanceof FieryWarmageArmorItem &&
                player.getItemBySlot(Type.CHESTPLATE.getSlot()).getItem() instanceof FieryWarmageArmorItem &&
                player.getItemBySlot(Type.LEGGINGS.getSlot()).getItem() instanceof FieryWarmageArmorItem &&
                player.getItemBySlot(Type.BOOTS.getSlot()).getItem() instanceof FieryWarmageArmorItem;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, TooltipContext context, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, context, lines, flag);
        if (this.type == Type.CHESTPLATE) {
            lines.add(Component.translatable("tooltip.twilight_spellbooks.on_full_set"));
            lines.add(Component.translatable("tooltip.twilight_spellbooks.hydra_set_passive").withStyle(Style.EMPTY.withColor(16493860)));
        }
    }

    private static final ResourceLocation LAYER = ResourceLocation.fromNamespaceAndPath(
            TwilightSpellbooks.MOD_ID,
            "textures/armor/fiery_mage_armor.png");

    @Override
    @OnlyIn(Dist.CLIENT)
    public GeoArmorRenderer<?> supplyRenderer() {
        RenderType GLOW_RENDER_TYPE = RenderType.breezeEyes(LAYER);

        return new EmissiveGenericCustomArmorRenderer<>(new FieryWarmageArmorModel(), LAYER, GLOW_RENDER_TYPE);
    }
}

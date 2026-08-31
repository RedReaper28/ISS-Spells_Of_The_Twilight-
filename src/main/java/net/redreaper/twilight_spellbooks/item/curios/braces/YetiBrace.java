package net.redreaper.twilight_spellbooks.item.curios.braces;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.items.curios.FlatCooldownPassiveAbilityCurio;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.entity.spells.avalanche.IceChunkProjectile;
import net.redreaper.twilight_spellbooks.init.ModItems;
import top.theillusivec4.curios.api.SlotContext;

@EventBusSubscriber
public class YetiBrace extends FlatCooldownPassiveAbilityCurio {
    public static final int COOLDOWN_IN_TICKS = 5 * 20;
    public YetiBrace() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.RARE), null);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, 2, AttributeModifier.Operation.ADD_VALUE));
        return attr;
    }

    @SubscribeEvent
    public static void handleAbility(LivingIncomingDamageEvent event) {
        var attacker = event.getSource().getEntity();
        var target = event.getEntity();
        if (attacker instanceof ServerPlayer player) {
            if (ASUtils.hasCurio(player, ModItems.YETI_BRACE.get()) && (!player.getCooldowns().isOnCooldown(ModItems.YETI_BRACE.get()))) {
                if (event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
                    target.invulnerableTime = 0;
                    IceChunkProjectile comet = new IceChunkProjectile(player.level(), player);
                    comet.setDamage(5);
                    comet.setPos(target.getX(), target.getY() + 7, target.getZ());
                    var trajectory = new Vec3(0.05F, -0.85F, 0).normalize();
                    comet.shoot(trajectory, 0.045F);
                    comet.setExplosionRadius(4.5F);
                    player.level().addFreshEntity(comet);
                    player.getCooldowns().addCooldown(ModItems.YETI_BRACE.get(), YetiBrace.COOLDOWN_IN_TICKS);
                }
            }
        }
    }


    @Override
    protected int getCooldownTicks() {
        return COOLDOWN_IN_TICKS;
    }
}

package net.redreaper.twilight_spellbooks.item.curios.braces;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.PassiveAbilityCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.entity.spells.avalanche.IceChunkProjectile;
import net.redreaper.twilight_spellbooks.init.ModItems;
import top.theillusivec4.curios.api.SlotContext;

public class YetiBrace extends PassiveAbilityCurio {
    public static final int COOLDOWN_IN_TICKS = 5 * 20;
    public YetiBrace() {
        super(ItemPropertiesHelper.equipment().stacksTo(1), Curios.NECKLACE_SLOT);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(id, 2, AttributeModifier.Operation.ADD_VALUE));
        return attr;
    }

    @SubscribeEvent
    public static void increaseDamage(LivingIncomingDamageEvent event) {
        var attacker = event.getSource().getEntity();
        var target = event.getEntity();
        if (attacker instanceof Player player) {
            if (ASUtils.hasCurio(player, ModItems.YETI_BRACE.get()) && (!player.getCooldowns().isOnCooldown(ModItems.YETI_BRACE.get()))) {
                if (event.getSource().is(DamageTypes.PLAYER_ATTACK)) {
                    if (target.isFreezing()) {
                        IceChunkProjectile comet = new IceChunkProjectile(attacker.level(), (LivingEntity) attacker);
                        comet.setDamage(5);
                        comet.setPos(target.getX(), target.getY() + 7, target.getZ());
                        var trajectory = new Vec3(0.05F, -0.85F, 0).normalize();
                        comet.shoot(trajectory, 0.045F);
                        comet.setExplosionRadius(2.5F);
                        player.getCooldowns().addCooldown(ModItems.YETI_BRACE.get(), YetiBrace.COOLDOWN_IN_TICKS);
                    }
                }
            }
        }
    }

    @Override
    protected int getCooldownTicks() {
        return COOLDOWN_IN_TICKS;
    }
}

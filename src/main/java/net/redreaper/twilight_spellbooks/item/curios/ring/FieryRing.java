package net.redreaper.twilight_spellbooks.item.curios.ring;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFDamageTypes;

@EventBusSubscriber
public class FieryRing extends SimpleDescriptiveCurio {
    public FieryRing() {
        super(ItemPropertiesHelper.equipment().stacksTo(1), Curios.RING_SLOT);
    }


    @SubscribeEvent
    public static void livingDamageEventPost(LivingDamageEvent.Post event) {
        var sourceEntity = event.getSource().getEntity();
        var target = event.getEntity();
        var projectile = event.getSource().getDirectEntity();
        // Curios

        if (sourceEntity != null) {
            if (sourceEntity instanceof Player player) {
                double baseDamage = damageFor(player);
                float damage = (float) (baseDamage);
                // FIERY RING
                if (ASUtils.hasCurio(player, ModItems.FIERY_RING.get())) {
                    if (event.getSource() instanceof SpellDamageSource) {
                        target.setRemainingFireTicks(2*20);
                        target.hurt(TFDamageTypes.getIndirectEntityDamageSource(player.level(), ISSDamageTypes.FIRE_MAGIC, player, player), damage);
                    }
                }
            }
        }
    }


    public static double damageFor(@Nullable Entity entity) {
        double baseDamage = 2.5f;
        if (entity instanceof LivingEntity livingAttacker) {
            baseDamage = baseDamage * livingAttacker.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingAttacker.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER);
        }
        return baseDamage;
    }
}

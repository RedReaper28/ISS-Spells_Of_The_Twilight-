package net.redreaper.twilight_spellbooks.item.curios.ring;

import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;

@EventBusSubscriber
public class FieryRing extends SimpleDescriptiveCurio {
    public FieryRing() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.UNCOMMON), Curios.RING_SLOT);
    }

    @SubscribeEvent
    public static void increaseDamage(LivingIncomingDamageEvent event) {
        var attacker = event.getSource().getEntity();
        var target = event.getEntity();
        if (attacker instanceof Player player) {
            if (ASUtils.hasCurio(player, ModItems.FIERY_RING.get())) {
                if (event.getSource().is(DamageTypes.PLAYER_ATTACK)) {

                    if (target.isOnFire()) {
                        float multiplier = 1 + 0.25f;
                        event.setAmount(event.getAmount() * multiplier);
                    }
                }
            }
        }
    }
}

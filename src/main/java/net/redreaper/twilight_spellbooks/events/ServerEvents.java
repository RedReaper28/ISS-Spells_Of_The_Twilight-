package net.redreaper.twilight_spellbooks.events;

import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import twilightforest.init.TFDamageTypes;

@EventBusSubscriber
public class ServerEvents {


    @SubscribeEvent
    public static void onBeforeDamageTaken(LivingDamageEvent.Pre event) {
        var livingEntity = event.getEntity();
        var entity = event.getEntity();
        var source = event.getSource();
        var attacker = event.getSource().getEntity();

    }
}

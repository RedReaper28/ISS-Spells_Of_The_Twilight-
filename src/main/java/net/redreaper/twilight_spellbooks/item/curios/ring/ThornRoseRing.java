package net.redreaper.twilight_spellbooks.item.curios.ring;

import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import twilightforest.init.TFDamageTypes;

@EventBusSubscriber
public class ThornRoseRing extends SimpleDescriptiveCurio {
    public ThornRoseRing() {
        super(ItemPropertiesHelper.equipment().stacksTo(1), Curios.RING_SLOT);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity entityTarget = event.getEntity();
        Entity entityAttacker = event.getSource().getDirectEntity();

        if (entityTarget instanceof Player player) {
            if (ASUtils.hasCurio(player, ModItems.THORN_ROSE_RING.get())) {
                if (entityAttacker instanceof LivingEntity) {
                    entityAttacker.hurt(TFDamageTypes.getIndirectEntityDamageSource(player.level(), TFDamageTypes.THORNS, player, player), 2.5f);
                }
            }
        }
    }

}

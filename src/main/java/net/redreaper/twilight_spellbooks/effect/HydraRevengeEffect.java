package net.redreaper.twilight_spellbooks.effect;

import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import twilightforest.init.TFDamageTypes;

@EventBusSubscriber
public class HydraRevengeEffect extends MagicMobEffect implements ISyncedMobEffect {
    public HydraRevengeEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entityTarget = event.getEntity();
        Entity entityAttacker = event.getSource().getDirectEntity();
        MobEffectInstance inst = event.getEntity().getEffect(ModMobEffects.HYDRA_REVENGE);

        if (inst == null) {
            return;
        }

        if (entityTarget instanceof Player player) {
                if (entityAttacker instanceof LivingEntity) {
                    float thorn = event.getAmount()* 0.25f;
                    entityAttacker.hurt(TFDamageTypes.getIndirectEntityDamageSource(player.level(), ISSDamageTypes.FIRE_MAGIC, player, player), thorn);
                }
        }
    }

}

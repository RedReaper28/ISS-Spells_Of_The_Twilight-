package net.redreaper.twilight_spellbooks.effect;

import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import net.redreaper.twilight_spellbooks.particle.ModParticleHelper;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFParticleType;

@EventBusSubscriber
public class HydraFireEffect extends MagicMobEffect implements ISyncedMobEffect {
    public HydraFireEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public boolean applyEffectTick(LivingEntity p_296276_, int p_296233_) {
        Registry<DamageType> dTypeReg = p_296276_.damageSources().damageTypes;
        Holder.Reference<DamageType> dType = dTypeReg.getHolder(NeoForgeMod.POISON_DAMAGE).orElse(dTypeReg.getHolderOrThrow(ISSDamageTypes.POISON_CLOUD));
        p_296276_.hurt(new DamageSource(dType), 1);
        return true;
    }

    public void clientTick(LivingEntity livingEntity, MobEffectInstance instance) {
        ParticleOptions particle = ModParticleHelper.HYDRA_FIRE_PARTICLE;
        var random = livingEntity.getRandom();
        for (int i = 0; i < 2; i++) {
            Vec3 motion = new Vec3(
                    random.nextFloat() * 2 - 1,
                    random.nextFloat() * 2 - 1,
                    random.nextFloat() * 2 - 1
            );
            motion = motion.scale(.04f);
            livingEntity.level().addParticle(particle, livingEntity.getRandomX(.4f), livingEntity.getRandomY(), livingEntity.getRandomZ(.4f), motion.x, motion.y, motion.z);
        }
    }

    public boolean shouldApplyEffectTickThisTick(int p_295629_, int p_295734_) {
        int i = 40 >> p_295734_;
        return i > 0 ? p_295629_ % i == 0 : true;
    }

    @SubscribeEvent
    public static void soulBurnReduceHealing(LivingHealEvent event) {
        MobEffectInstance inst = event.getEntity().getEffect(ModMobEffects.OMINOUS_BURN);
        if (inst == null) {
            return;
        }
        event.setAmount(event.getAmount() * .50f);
    }
}

package net.redreaper.twilight_spellbooks.events;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.redreaper.twilight_spellbooks.particle.ModParticleHelper;
import twilightforest.init.TFParticleType;

public class ModClientSpellCastHelper {

    public static void handleClientboundExanimatedExplosion(Vec3 pos, float radius) {
        MinecraftInstanceHelper.ifPlayerPresent(player -> {
            var level = player.level();
            var x = pos.x;
            var y = pos.y;
            var z = pos.z;
            //Blastwave
            level.addParticle(new BlastwaveParticleOptions(SpellRegistry.STARFALL_SPELL.get().getSchoolType().getTargetingColor(), radius + 1), x, y, z, 0, 0, 0);
            //Billowing wave
            int c = (int) (6.28 * radius) * 2;
            float step = 360f / c * Mth.DEG_TO_RAD;
            float speed = (0.06f + 0.01f * radius) * 2;
            for (int i = 0; i < c; i++) {
                Vec3 vec3 = new Vec3(Mth.cos(step * i), 0, Mth.sin(step * i)).scale(speed);
                Vec3 posOffset = Utils.getRandomVec3(.5f).add(vec3.scale(10));
                vec3 = vec3.add(Utils.getRandomVec3(0.01));
                level.addParticle(ModParticleHelper.EXANIMATED_SMOKE, x + posOffset.x, y + posOffset.y, z + posOffset.z, vec3.x, vec3.y, vec3.z);
            }
            //Smoke Cloud
            int cloudDensity = 50 + (int) (25 * radius);
            for (int i = 0; i < cloudDensity; i++) {
                Vec3 posOffset = Utils.getRandomVec3(1).scale(radius * .125f);
                Vec3 motion = posOffset.normalize().scale(speed * .5f);
                posOffset = posOffset.add(motion.scale(Utils.getRandomScaled(1)));
                motion = motion.add(Utils.getRandomVec3(speed * .1f));
                level.addParticle(ModParticleHelper.EXANIMATED_SMOKE, x + posOffset.x, y + posOffset.y, z + posOffset.z, motion.x, motion.y, motion.z);
            }
            //Cloud
            for (int i = 0; i < cloudDensity; i += 2) {
                Vec3 posOffset = Utils.getRandomVec3(1).scale(radius * .4f);
                Vec3 motion = posOffset.normalize().scale(speed * .5f);
                motion = motion.add(Utils.getRandomVec3(0.25));
                level.addParticle(ModParticleHelper.EXANIMATED_EMBERS, true, x + posOffset.x, y + posOffset.y, z + posOffset.z, motion.x, motion.y, motion.z);
                level.addParticle(ModParticleHelper.EXANIMATED_FIRE, x + posOffset.x * .5f, y + posOffset.y * .5f, z + posOffset.z * .5f, motion.x, motion.y, motion.z);
                level.addParticle(TFParticleType.OMINOUS_FLAME.get(), x + posOffset.x * .5f, y + posOffset.y * .5f, z + posOffset.z * .5f, motion.x, motion.y, motion.z);
            }
            //Sparks
            for (int i = 0; i < cloudDensity; i += 2) {
                Vec3 posOffset = Utils.getRandomVec3(radius).scale(.2f);
                Vec3 motion = posOffset.normalize().scale(0.8);
                motion = motion.add(Utils.getRandomVec3(0.18));
                level.addParticle(ParticleHelper.ENDER_SPARKS, x + posOffset.x * .5f, y + posOffset.y * .5f, z + posOffset.z * .5f, motion.x, motion.y, motion.z);
            }
        });
    }
}

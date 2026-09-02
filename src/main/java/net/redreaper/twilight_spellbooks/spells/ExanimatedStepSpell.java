package net.redreaper.twilight_spellbooks.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.spells.ender.TeleportSpell;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import net.redreaper.twilight_spellbooks.init.ModSpellSubSchool;
import net.redreaper.twilight_spellbooks.particle.ExanimatedStepParticlePacket;
import net.redreaper.twilight_spellbooks.particle.ModParticleHelper;

import java.util.List;
import java.util.Optional;

public class ExanimatedStepSpell extends AbstractExanimatedSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "exanimated_step");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.distance", Utils.stringTruncation(getDistance(spellLevel, caster), 1)),
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2))
        );

    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(ModSpellSubSchool.EXANIMATED_RESOURCE)
                .setMaxLevel(3)
            .setCooldownSeconds(12)
            .build();

    public ExanimatedStepSpell() {
        this.baseSpellPower = 8;
        this.spellPowerPerLevel = 1;
        this.baseManaCost = 30;
        this.manaCostPerLevel = 10;
        this.castTime = 0;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.empty();
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.LIGHTNING_WOOSH_01.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Vec3 dest = null;
        float radius = getRadius(spellLevel, entity);
        Vec3 edge = new Vec3(.7f, 1f, 1f);
        Vec3 center = new Vec3(1, 1f, 1f);
        TeleportSpell.TeleportData teleportData = (TeleportSpell.TeleportData)playerMagicData.getAdditionalCastData();



        if (teleportData != null) {
            Vec3 potentialTarget = teleportData.getTeleportTargetPosition();
            dest = potentialTarget;
        }

        if (dest == null) {
            dest = this.findTeleportLocation(spellLevel, level, entity);
            MagicManager.spawnParticles(entity.level(), new BlastwaveParticleOptions(SchoolRegistry.ENDER.get().getTargetingColor(), radius * 2), dest.x(), dest.y(), dest.z(), 2, 0, 0, 0, 0, true);
            MagicManager.spawnParticles(level, ModParticleHelper.EXANIMATED_FIRE, dest.x(), dest.y() + 1, dest.z(), 80, .25, .25, .25, 0.7f + radius * .1f, false);
            CameraShakeManager.addCameraShake(new CameraShakeData(level, 30, entity.position(), radius * 2));

            float damage = getDamage(spellLevel, entity);
            var entities = level.getEntities(entity, AABB.ofSize(dest, radius * 2, radius * 4, radius * 2));
            var damageSource = this.getDamageSource(entity);
            for (Entity targetEntity : entities) {
                if (targetEntity.isAlive() && targetEntity.isPickable() && Utils.hasLineOfSight(level, dest.add(0, 1, 0), targetEntity.getBoundingBox().getCenter(), true)) {
                    if (DamageSources.applyDamage(targetEntity, getDamage(spellLevel, entity), damageSource)) {
                        if (targetEntity instanceof  LivingEntity livingEntity) {
                            livingEntity.setRemainingFireTicks(5*20);
                            livingEntity.addEffect(new MobEffectInstance(ModMobEffects.OMINOUS_BURN, 5 * 20, 0, true, true, true));
                        }
                    }
                }
            }
        }

        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new ExanimatedStepParticlePacket(entity.position(), dest), new CustomPacketPayload[0]);
        if (entity.isPassenger()) {
            entity.stopRiding();
        }

        Utils.handleSpellTeleport(this, entity, dest);
        entity.resetFallDistance();
        level.playSound(null, dest.x, dest.y, dest.z, this.getCastFinishSound().get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        playerMagicData.resetAdditionalCastData();

        entity.resetFallDistance();
        level.playSound(null, dest.x, dest.y, dest.z, getCastFinishSound().get(), SoundSource.NEUTRAL, 1f, 1f);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private boolean canHit(Entity owner, Entity target) {
        return target != owner && target.isAlive() && target.isPickable() && !target.isSpectator();
    }

    private Vec3 findTeleportLocation(int spellLevel, Level level, LivingEntity entity) {
        return TeleportSpell.findTeleportLocation(level, entity, getDistance(spellLevel, entity));
    }

    public static void particleCloud(Level level, Vec3 pos) {
        if (level.isClientSide) {
            double width = 0.5;
            float height = 1;
            for (int i = 0; i < 25; i++) {
                double x = pos.x + Utils.random.nextDouble() * width * 2 - width;
                double y = pos.y + height + Utils.random.nextDouble() * height * 1.2 * 2 - height * 1.2;
                double z = pos.z + Utils.random.nextDouble() * width * 2 - width;
                double dx = Utils.random.nextDouble() * .1 * (Utils.random.nextBoolean() ? 1 : -1);
                double dy = Utils.random.nextDouble() * .1 * (Utils.random.nextBoolean() ? 1 : -1);
                double dz = Utils.random.nextDouble() * .1 * (Utils.random.nextBoolean() ? 1 : -1);
                level.addParticle(ModParticleHelper.EXANIMATED_SMOKE, true, x, y, z, dx, dy, dz);
                level.addParticle(ParticleHelper.ENDER_SPARKS, true, x, y, z, -dx, -dy, -dz);
            }
        }
    }

    public float getRadius(int spellLevel, LivingEntity caster) {
        return 2.5f + spellLevel * .5f;
    }


    private float getDistance(int spellLevel, LivingEntity sourceEntity) {
        return (float) (Utils.softCapFormula(getEntityPowerMultiplier(sourceEntity)) * getSpellPower(spellLevel, null));
    }

    public float getDamage(int spellLevel, LivingEntity caster) {
        if (caster == null) {
            return this.getSpellPower(spellLevel, null) * 2;
        } else {
            double firePower = caster.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER);
            double bloodPower = caster.getAttributeValue(AttributeRegistry.BLOOD_SPELL_POWER);
            double enderPower = caster.getAttributeValue(AttributeRegistry.ENDER_SPELL_POWER);
            if (firePower == bloodPower && bloodPower == enderPower) {
                return (float)((double)5 * ((double)0.75F * firePower + (double)0.75F * enderPower + (double)0.75F * bloodPower));
            }
            else {
                return (float)((double)5 * ((double)0.5F * firePower + (double)0.5F * enderPower + (double)0.5F * bloodPower));
            }
        }
    }


    @Override
    public AnimationHolder getCastStartAnimation() {
        return AnimationHolder.none();
    }
}

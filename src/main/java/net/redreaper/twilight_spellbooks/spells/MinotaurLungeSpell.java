package net.redreaper.twilight_spellbooks.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.ImpulseCastData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.spells.fire.BurningDashSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.utils.ModUtils;
import twilightforest.init.TFSounds;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MinotaurLungeSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "minotaur_lunge");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", getDamageText(spellLevel, caster)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(15)
            .build();

    public MinotaurLungeSpell() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 40;
        this.baseManaCost = 30;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
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
    public void onClientCast(Level level, int spellLevel, LivingEntity entity, ICastData castData) {
        if (castData instanceof ImpulseCastData bdcd) {
            entity.hasImpulse = bdcd.hasImpulse;
            entity.setDeltaMovement(entity.getDeltaMovement().add(bdcd.x, bdcd.y, bdcd.z));
        }
        super.onClientCast(level, spellLevel, entity, castData);
        // align body with arms so the sword animation plays more smoothly
        entity.setYBodyRot(entity.getYRot());
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new ImpulseCastData();
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.hasImpulse = true;

        //Direction for Mobs to cast in
        Vec3 forward = entity.getLookAngle();
        if (playerMagicData.getAdditionalCastData() instanceof BurningDashSpell.BurningDashDirectionOverrideCastData) {
            if (Utils.random.nextBoolean())
                forward = forward.yRot(90);
            else
                forward = forward.yRot(-90);
        }

        float distance = 8; //todo: scale with power
        float multiplier = (15 + getSpellPower(spellLevel, entity)) / 12f;
        Vec3 end = Utils.raycastForBlock(level, entity.getEyePosition(), entity.getEyePosition().add(forward.scale(distance)), ClipContext.Fluid.NONE).getLocation();
        AABB hitbox = entity.getHitbox().expandTowards(end.subtract(entity.getEyePosition())).inflate(2);
        var targetableEntities = level.getEntities(entity, hitbox, e ->
                !e.isSpectator() &&
                        (e instanceof LivingEntity) &&
                        e.getBoundingBox().getCenter().subtract(entity.getBoundingBox().getCenter()).normalize().dot(entity.getForward()) >= .85);
        targetableEntities.sort(Comparator.comparingDouble(e -> e.distanceToSqr(entity)));
        if (!targetableEntities.isEmpty() && targetableEntities.get(0).distanceToSqr(entity) < distance * distance) {
            var closestEntity = targetableEntities.get(0);

            float radius = 2.50f;
            AABB damageBox = AABB.ofSize(closestEntity.getBoundingBox().getCenter(), radius, radius + 1, radius).move(forward.scale(radius / 2));
            // dampen end position by taking average of it and the impact location
            end = damageBox.getCenter().add(end).scale(0.5);
            var damageEntities = level.getEntities(entity, damageBox);
            var damageSource = this.getDamageSource(entity);
            for (Entity targetEntity : damageEntities) {
                if (targetEntity.isAlive() &&
                        entity.isPickable() &&
                        Utils.hasLineOfSight(level, entity.getEyePosition(), targetEntity.getBoundingBox().getCenter(), true)) {
                    if (DamageSources.applyDamage(targetEntity, getDamage(spellLevel, entity), damageSource)) {
                        EnchantmentHelper.doPostAttackEffects((ServerLevel) level, targetEntity, damageSource);
                        Vec3 knockback = targetEntity.position().subtract(entity.position()).normalize().add(0, 0.5, 0).normalize();
                        knockback.scale(Utils.random.nextIntBetweenInclusive(70, 100) / 100f *
                                Utils.clampedKnockbackResistanceFactor(targetEntity, .2f, 1f) * .1f);
                        targetEntity.setDeltaMovement(targetEntity.getDeltaMovement().add(knockback));

                        targetEntity.hurtMarked = true;
                    }
                }
            }

        }
        //Create Dashing Movement Impulse
        var vec = forward.multiply(3, 1, 3).normalize().add(0, .25, 0).scale(multiplier);
        playerMagicData.setAdditionalCastData(new ImpulseCastData((float) vec.x, (float) vec.y, (float) vec.z, true));
        //entity.setDeltaMovement(entity.getDeltaMovement().add(vec));
        entity.setDeltaMovement(new Vec3(
                Mth.lerp(.75f, entity.getDeltaMovement().x, vec.x),
                Mth.lerp(.75f, entity.getDeltaMovement().y, vec.y),
                Mth.lerp(.75f, entity.getDeltaMovement().z, vec.z)
        ));
        entity.invulnerableTime = 20;

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        float extraDamage =1+ ModUtils.getEntitySpeed(entity) ;
        return getSpellPower(spellLevel, entity) * extraDamage;
    }

    private String getDamageText(int spellLevel, LivingEntity caster)
    {
        if (caster != null)
        {
            float extraDamage = ModUtils.getEntitySpeed(caster)+1 ;
            String plus = "";
            if (extraDamage > 0)
            {
                plus = String.format(" (x%s)", Utils.stringTruncation(extraDamage, 1));
            }
            String damage = Utils.stringTruncation(getDamage(spellLevel, caster), 1);
            return damage + plus;
        }
        return "" + getSpellPower(spellLevel, caster);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(TFSounds.MINOTAUR_ATTACK.get());
    }
    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.COW_AMBIENT);
    }
}

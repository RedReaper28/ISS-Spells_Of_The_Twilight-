package net.redreaper.twilight_spellbooks.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.acetheeldritchking.aces_spell_utils.utils.RibbonHandler;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.ColorRamp;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.Curve;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.Easing;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.RibbonConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.spells.aurora_missile.AuroraMissileProjectile;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AuroraMissileSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "aurora_missile");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(1)
            .build();

    public AuroraMissileSpell() {
        this.manaCostPerLevel = 2;
        this.baseSpellPower = 12;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 10;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        AuroraMissileProjectile magicMissileProjectile = new AuroraMissileProjectile(world, entity);
        magicMissileProjectile.setCursorHoming(true);
        magicMissileProjectile.setPos(entity.position().add(0, entity.getEyeHeight() - magicMissileProjectile.getBoundingBox().getYsize() * .5f, 0));
        magicMissileProjectile.shoot(entity.getLookAngle());
        magicMissileProjectile.setDamage(getDamage(spellLevel, entity));
        world.addFreshEntity(magicMissileProjectile);
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    public float getDamage(int spellLevel, LivingEntity caster) {
        if (caster == null) {
            return this.getSpellPower(spellLevel, null) * 7.0F;
        } else {
            double icePower = caster.getAttributeValue(AttributeRegistry.ICE_SPELL_POWER);
            double holyPower = caster.getAttributeValue(AttributeRegistry.HOLY_SPELL_POWER);
            if (icePower == holyPower) {
                return (float) (icePower*1.5 + holyPower*1.5);
            }
            else {
                return (float) (icePower*0.5f + holyPower*0.5f);
            }
        }
    }

    @Override
    public SpellDamageSource getDamageSource(@Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setFreezeTicks(80);
    }
}

package net.redreaper.twilight_spellbooks.events;

import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.redreaper.twilight_spellbooks.entity.living.hydra_soul.HydraSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.lich_soul.LichSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.snow_queen_soul.SnowQueenSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.urghast_soul.UrGhastSoulEntity;
import net.redreaper.twilight_spellbooks.init.ModItems;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.SnowQueenSpellbookItem;
import twilightforest.entity.boss.Hydra;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.boss.SnowQueen;
import twilightforest.entity.boss.UrGhast;
import twilightforest.init.TFMobEffects;

@EventBusSubscriber
public class ServerEvents {
    private static final float KNIGHTMETAL_MULT_DAMAGE = 0.20F;

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        if (attacker != null) {
            if (attacker instanceof Player livingAttacker) {
                if (target instanceof Lich lich) {
                    if (lich.getSpawnType() != MobSpawnType.SPAWN_EGG) {
                        Vec3 spawnPos = Vec3.atCenterOf(lich.getRestrictionPoint().pos());
                        if (spawnPos != null) {
                            var soul = new LichSoulEntity(lich.level(), Vec3.ZERO, lich.position());
                            soul.setRespawnPos(spawnPos);
                            soul.moveTo(lich.getBoundingBox().getCenter());
                            lich.level().addFreshEntity(soul);
                        }
                    }
                }
                if (target instanceof SnowQueen snowQueen) {
                    if (snowQueen.getSpawnType() != MobSpawnType.SPAWN_EGG) {
                        Vec3 spawnPos = Vec3.atCenterOf(snowQueen.getRestrictionPoint().pos());
                        if (spawnPos != null) {
                            var soul = new SnowQueenSoulEntity(snowQueen.level(), Vec3.ZERO, snowQueen.position());
                            soul.setRespawnPos(spawnPos);
                            soul.moveTo(snowQueen.getBoundingBox().getCenter());
                            snowQueen.level().addFreshEntity(soul);
                        }
                    }
                }
                if (target instanceof UrGhast ghast) {
                    if (ghast.getSpawnType() != MobSpawnType.SPAWN_EGG) {
                        Vec3 spawnPos = Vec3.atCenterOf(ghast.getRestrictionPoint().pos());
                        if (spawnPos != null) {
                            var soul = new UrGhastSoulEntity(ghast.level(), Vec3.ZERO, ghast.position());
                            soul.setRespawnPos(spawnPos);
                            soul.moveTo(ghast.getBoundingBox().getCenter());
                            ghast.level().addFreshEntity(soul);
                        }
                    }
                }
                if (target instanceof Hydra hydra) {
                    if (hydra.getSpawnType() != MobSpawnType.SPAWN_EGG) {
                        Vec3 spawnPos = Vec3.atCenterOf(hydra.getRestrictionPoint().pos());
                        if (spawnPos != null) {
                            var soul = new HydraSoulEntity(hydra.level(), Vec3.ZERO, hydra.position());
                            soul.setRespawnPos(spawnPos);
                            soul.moveTo(hydra.getBoundingBox().getCenter());
                            hydra.level().addFreshEntity(soul);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void livingDamageEventPost(LivingDamageEvent.Post event) {
        var sourceEntity = event.getSource().getEntity();
        var target = event.getEntity();
        var projectile = event.getSource().getDirectEntity();
        if (sourceEntity != null) {
            if (sourceEntity instanceof Player player) {
                // FIERY SPELLBOOK
                if (ASUtils.hasCurio(player, ModItems.FIERY_SPELL_BOOK.get())) {
                    if (event.getSource() instanceof SpellDamageSource) {
                        target.setRemainingFireTicks(2*20);
                    }
                }

                if (ASUtils.hasCurio(player, ModItems.SNOW_QUEEN_SPELL_BOOK.get()) && (!player.getCooldowns().isOnCooldown(ModItems.SNOW_QUEEN_SPELL_BOOK.get()))) {
                    if (event.getSource().is(ISSDamageTypes.ICE_MAGIC)) {
                        target.addEffect(new MobEffectInstance(TFMobEffects.FROSTY,5*20,2));
                        player.getCooldowns().addCooldown(ModItems.SNOW_QUEEN_SPELL_BOOK.get(), SnowQueenSpellbookItem.COOLDOWN);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void livingIncomingDamage(LivingIncomingDamageEvent event) {
        var entity = event.getEntity();
        if (entity instanceof Player player) {
            if (ASUtils.hasCurio(player, ModItems.IRONWOOD_SPELLBOOK.get())) {
                float lvl = .15f;
                float before = event.getAmount();
                float multiplier = 1 - lvl;
                event.setAmount(event.getAmount() * multiplier);
            }
        }
    }

    @SubscribeEvent
    public static void increaseDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        var attacker = event.getSource().getEntity();
        if (attacker instanceof Player player) {
            if (target instanceof LivingEntity living) {
                if (event.getSource() instanceof SpellDamageSource) {
                    if (ASUtils.hasCurio(player, ModItems.KNIGHTMETAL_SPELLBOOK.get())) {
                        if (target.getArmorValue() > 0) {
                            if (target.getArmorCoverPercentage() > 0) {
                                int moreBonus = (int) (KNIGHTMETAL_MULT_DAMAGE * target.getArmorCoverPercentage());
                                event.setAmount(event.getAmount() * moreBonus);
                            } else {
                                event.setAmount(event.getAmount() * KNIGHTMETAL_MULT_DAMAGE);
                            }
                            // enchantment attack sparkles
                            ((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
                        }
                    }
                }
            }
        }
    }

}


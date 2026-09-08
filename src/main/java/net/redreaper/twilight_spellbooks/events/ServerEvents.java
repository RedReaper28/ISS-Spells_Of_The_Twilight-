package net.redreaper.twilight_spellbooks.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.redreaper.twilight_spellbooks.entity.living.hydra_soul.HydraSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.lich_soul.LichSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.snow_queen_soul.SnowQueenSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.urghast_soul.UrGhastSoulEntity;
import twilightforest.entity.boss.Hydra;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.boss.SnowQueen;
import twilightforest.entity.boss.UrGhast;

@EventBusSubscriber
public class ServerEvents {


    @SubscribeEvent
    public static void onBeforeDamageTaken(LivingDamageEvent.Pre event) {
        var livingEntity = event.getEntity();
        var entity = event.getEntity();
        var source = event.getSource();
        var attacker = event.getSource().getEntity();

    }

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

}


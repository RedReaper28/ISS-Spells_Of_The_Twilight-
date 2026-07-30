package net.redreaper.twilight_spellbooks.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.redreaper.twilight_spellbooks.entity.living.lich_soul.LichSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.snow_queen_soul.SnowQueenSoulEntity;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.boss.SnowQueen;

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
                        Vec3 spawnPos = Vec3.atLowerCornerOf(lich.getRestrictionPoint().pos());
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
                        Vec3 spawnPos = Vec3.atLowerCornerOf(snowQueen.getRestrictionPoint().pos());
                        if (spawnPos != null) {
                            var soul = new SnowQueenSoulEntity(snowQueen.level(), Vec3.ZERO, snowQueen.position());
                            soul.setRespawnPos(spawnPos);
                            soul.moveTo(snowQueen.getBoundingBox().getCenter());
                            snowQueen.level().addFreshEntity(soul);
                        }
                    }
                }
            }
        }
    }

}


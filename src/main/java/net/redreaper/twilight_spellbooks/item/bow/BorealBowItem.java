package net.redreaper.twilight_spellbooks.item.bow;

import io.redspace.ironslib.registry.IronsLibRegistries;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.redreaper.twilight_spellbooks.entity.spells.ice_arrow.IceArrowProjectile;
import net.redreaper.twilight_spellbooks.init.ModExtendedWeaponTier;
import net.redreaper.twilight_spellbooks.item.extended.magic_bow.ExtendedBowItem;

import java.util.List;
import java.util.function.Predicate;

public class BorealBowItem extends ExtendedBowItem {
    public int MAX_MANA_COST = 50;
    public int BASE_ICE_POWER_SCALE = 10;

    public BorealBowItem() {
        super(
                new Item
                        .Properties()
                        .stacksTo(1)
                        .durability(451)
                        .rarity(ASRarities.GLACIAL_RARITY_PROXY.getValue())
                        .fireResistant()
                        .attributes(ExtendedBowItem.createAttributes(ModExtendedWeaponTier.BOREAL_BOW)
                        )
        );
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int use) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack, livingEntity) - use;
            if (i < 0) {
                return;
            }

            float f = getPowerForTime(i);
            float damage = (f * (float) (player.getAttributeValue(AttributeRegistry.ICE_SPELL_POWER) * (BASE_ICE_POWER_SCALE + getSpiritPowerScale(stack, livingEntity))));
            float extraDamage = getArrowDamage(livingEntity);
            float totalDamage = damage * extraDamage;
            IceArrowProjectile magicArrow = new IceArrowProjectile(level, livingEntity);
            magicArrow.setPos(player.position().add(0, 1.5, 0));
            magicArrow.shoot(player.getLookAngle());
            magicArrow.setDamage(totalDamage);
            magicArrow.tick();

            level.addFreshEntity(magicArrow);

            int manaConsume = MAX_MANA_COST + getManaOnUse(stack, player);
            MagicData playerMana = MagicData.getPlayerMagicData(player);
            if (!player.isCreative()) {
                playerMana.addMana(-(f * manaConsume));
            }
            stack.hurtAndBreak(this.getDurabilityUse(stack), livingEntity, LivingEntity.getSlotForHand(livingEntity.getUsedItemHand()));

            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundRegistry.BOW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

    }

    public static int getSpiritPowerScale(ItemStack stack, LivingEntity livingEntity) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            int powerScalePoint = 10;
            return Mth.floor(powerScalePoint);
        } else return 0;
    }

    public static int getManaOnUse(ItemStack stack, LivingEntity livingEntity) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            int manaUseAmount = 40;
            return Mth.floor(manaUseAmount);
        } else return 0;
    }

    public static float getArrowDamage(LivingEntity entity) {
        if (entity != null) {
            return (float) (entity.getAttributeValue(IronsLibRegistries.AttributeRegistry.ARROW_DAMAGE));
        }
        return 0;
    }

    @Override
    protected void shootProjectile(LivingEntity p_331372_, Projectile p_332000_, int p_330631_, float p_331251_, float p_331199_, float p_330857_, @javax.annotation.Nullable LivingEntity p_331572_) {
        p_332000_.shootFromRotation(p_331372_, p_331372_.getXRot(), p_331372_.getYRot() + p_330857_, 0.0F, p_331251_, p_331199_);
    }

    public static float getPowerForTime(int p_40662_) {
        float f = (float)p_40662_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
        return 7200;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        MagicData magicData = MagicData.getPlayerMagicData(player);
        if (magicData.getMana() < (MAX_MANA_COST + getManaOnUse(itemstack, player)) && !player.isCreative()) {
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.twilight_spellbooks.not_enough_mana").withStyle(ChatFormatting.RED)));
            }
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    public int getDefaultProjectileRange() {
        return 20;
    }

    public int getEnchantmentValue() {
        return 1;
    }

    //Client call for damage tooltip
    public static double getDisplayMaxDamage(ItemStack stack, LivingEntity livingEntity) {
        double baseMaxDamage = 10;
        if (livingEntity instanceof Player player) {
            double baseDamage= baseMaxDamage * livingEntity.getAttributeValue(AttributeRegistry.ICE_SPELL_POWER);
            return baseDamage * getArrowDamage(livingEntity);
        }
        return baseMaxDamage;
    }

    //Client call for mana cost tooltip
    public static double getDisplayManaCost(ItemStack stack, LivingEntity livingEntity) {
        return 50 + getManaOnUse(stack, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        components.add(Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".description")).withStyle(ChatFormatting.BLUE));
        components.add(Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".description.damage",
                Component.literal(Utils.stringTruncation(getDisplayMaxDamage(stack, MinecraftInstanceHelper.getPlayer()), 1)).withStyle(ChatFormatting.WHITE))
        ).withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".description.mana_cost",
                Component.literal(Utils.stringTruncation(getDisplayManaCost(stack, MinecraftInstanceHelper.getPlayer()), 1)).withStyle(ChatFormatting.WHITE))
        ).withStyle(ChatFormatting.AQUA));
    }

}

package net.redreaper.twilight_spellbooks.entity.spells.twilight_bolt;

import io.redspace.ironsspellbooks.entity.spells.fireball.FireballRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

public class TwilightBoltRenderer extends FireballRenderer {
    private final static ResourceLocation BASE_TEXTURE = TwilightSpellbooks.id("textures/entity/twilight_bolt.png");
    private final static ResourceLocation FIRE_TEXTURES = TwilightSpellbooks.id("textures/entity/none.png");


    public TwilightBoltRenderer(EntityRendererProvider.Context context, float scale) {
        super(context, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(Projectile entity) {
        return BASE_TEXTURE;
    }

    public ResourceLocation getFireTextureLocation(Projectile entity) {
        return FIRE_TEXTURES;
    }
}

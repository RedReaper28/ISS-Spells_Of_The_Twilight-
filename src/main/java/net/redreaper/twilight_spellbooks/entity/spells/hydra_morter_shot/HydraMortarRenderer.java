package net.redreaper.twilight_spellbooks.entity.spells.hydra_morter_shot;

import io.redspace.ironsspellbooks.entity.spells.fireball.FireballRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

public class HydraMortarRenderer extends FireballRenderer {
    private final static ResourceLocation BASE_TEXTURE = TwilightSpellbooks.id("textures/entity/hydra_fireball/fireball_core.png");
    private final static ResourceLocation FIRE_TEXTURES[] = {
            TwilightSpellbooks.id("textures/entity/hydra_fireball/fire_1.png"),
            TwilightSpellbooks.id("textures/entity/hydra_fireball/fire_2.png"),
            TwilightSpellbooks.id("textures/entity/hydra_fireball/fire_3.png"),
            TwilightSpellbooks.id("textures/entity/hydra_fireball/fire_4.png")
    };

    public HydraMortarRenderer(EntityRendererProvider.Context context, float scale) {
        super(context, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(Projectile entity) {
        return BASE_TEXTURE;
    }

    public ResourceLocation getFireTextureLocation(Projectile entity) {
        int frame = (entity.tickCount / 2) % FIRE_TEXTURES.length;
        return FIRE_TEXTURES[frame];
    }

}
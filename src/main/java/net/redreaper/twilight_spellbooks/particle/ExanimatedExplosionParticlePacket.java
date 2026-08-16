package net.redreaper.twilight_spellbooks.particle;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.events.ModClientSpellCastHelper;

public class ExanimatedExplosionParticlePacket implements CustomPacketPayload {
    private final Vec3 pos1;
    private final float radius;
    public static final CustomPacketPayload.Type<ExanimatedExplosionParticlePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "ender_explosion_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ExanimatedExplosionParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(ExanimatedExplosionParticlePacket::write, ExanimatedExplosionParticlePacket::new);

    public ExanimatedExplosionParticlePacket(Vec3 pos1, float radius) {
        this.pos1 = pos1;
        this.radius = radius;
    }

    public ExanimatedExplosionParticlePacket(FriendlyByteBuf buf) {
        pos1 = buf.readVec3();
        radius = buf.readFloat();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVec3(pos1);
        buf.writeFloat(radius);
    }

    public static void handle(ExanimatedExplosionParticlePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ModClientSpellCastHelper.handleClientboundExanimatedExplosion(packet.pos1, packet.radius);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

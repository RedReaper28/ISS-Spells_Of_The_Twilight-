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

public class ExanimatedStepParticlePacket implements CustomPacketPayload {
    private final Vec3 pos1;
    private final Vec3 pos2;
    public static final CustomPacketPayload.Type<ExanimatedStepParticlePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "exanimated_step_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ExanimatedStepParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(ExanimatedStepParticlePacket::write, ExanimatedStepParticlePacket::new);

    public ExanimatedStepParticlePacket(Vec3 pos1, Vec3 pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public ExanimatedStepParticlePacket(FriendlyByteBuf buf) {
        pos1 = buf.readVec3();
        pos2 = buf.readVec3();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVec3(pos1);
        buf.writeVec3(pos2);
    }

    public static void handle(ExanimatedStepParticlePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ModClientSpellCastHelper.handleClientboundThunderStep(packet.pos1, packet.pos2);
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}


package steelworks.core.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import steelworks.SteelworksWeapons;
import steelworks.core.api.ItemAbilityEvent;

public record AbilityKeyPayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AbilityKeyPayload> TYPE =
            new CustomPacketPayload.Type<>(SteelworksWeapons.resourceLocation("ability_key"));

    public static final StreamCodec<ByteBuf, AbilityKeyPayload> STREAM_CODEC =
            StreamCodec.unit(new AbilityKeyPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void send() {
        PacketDistributor.sendToServer(new AbilityKeyPayload());
    }

    public static void handle(AbilityKeyPayload payload, IPayloadContext context) {
        if (context.player() instanceof ServerPlayer player) {
            NeoForge.EVENT_BUS.post(new ItemAbilityEvent(player));
        }
    }
}

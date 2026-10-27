package steelworks.core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkEvent;
import steelworks.core.api.ItemAbilityEvent;

import java.util.function.Supplier;

public class AbilityKeyPacket {

    private boolean pressed;

    public AbilityKeyPacket(boolean pressed) {
        this.pressed = pressed;
    }

    public AbilityKeyPacket(FriendlyByteBuf buf) {
        this.pressed = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(pressed);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {
                MinecraftForge.EVENT_BUS.post(new ItemAbilityEvent(player));
            }
        });
        context.get().setPacketHandled(true);
    }
}

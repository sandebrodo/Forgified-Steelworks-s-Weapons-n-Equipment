package steelworks.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import steelworks.SteelworksWeapons;
import steelworks.core.network.AbilityKeyPacket;

public class ModNetwork {
    private static int packetId = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(SteelworksWeapons.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public static void register() {
        CHANNEL.registerMessage(
                packetId++,
                AbilityKeyPacket.class,
                AbilityKeyPacket::encode,
                AbilityKeyPacket::new,
                AbilityKeyPacket::handle
        );
    }


}

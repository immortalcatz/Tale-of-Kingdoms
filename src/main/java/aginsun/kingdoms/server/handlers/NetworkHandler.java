package aginsun.kingdoms.server.handlers;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import aginsun.kingdoms.server.handlers.packets.CPacketSyncDataPlayer;

public final class NetworkHandler
{
    private byte packetId;
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("TokChannel");

    public NetworkHandler()
    {
        register(CPacketSyncDataPlayer.class, Side.CLIENT);
    }

    private void register(Class clazz, Side side)
    {
        INSTANCE.registerMessage(clazz, clazz, packetId++, side);
    }
}
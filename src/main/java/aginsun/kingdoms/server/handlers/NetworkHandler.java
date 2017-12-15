package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.server.handlers.packets.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public final class NetworkHandler
{
    private byte packetId;
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("TokChannel");

    public NetworkHandler()
    {
        register(CPacketSyncDataPlayer.class, Side.CLIENT);
        register(CPacketSyncShopItems.class, Side.CLIENT);
        register(SPacketBuild.class, Side.SERVER);
        register(SPacketBuy.class, Side.SERVER);
        register(SPacketFilter.class, Side.SERVER);
        register(SPacketOpenGui.class, Side.SERVER);
    }

    private void register(Class clazz, Side side)
    {
        INSTANCE.registerMessage(clazz, clazz, packetId++, side);
    }
}
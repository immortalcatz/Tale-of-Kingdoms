package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.server.handlers.packets.CPacketSyncShopItems;
import aginsun.kingdoms.server.handlers.packets.SPacketBuild;
import aginsun.kingdoms.server.handlers.packets.SPacketBuy;
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
        register(CPacketSyncShopItems.class, Side.CLIENT);
        register(SPacketBuild.class, Side.SERVER);
        register(SPacketBuy.class, Side.SERVER);
    }

    private void register(Class clazz, Side side)
    {
        INSTANCE.registerMessage(clazz, clazz, packetId++, side);
    }
}
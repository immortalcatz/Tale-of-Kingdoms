package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.server.handlers.packets.client.CPacketSyncBuildings;
import aginsun.kingdoms.server.handlers.packets.client.CPacketSyncDataPlayer;
import aginsun.kingdoms.server.handlers.packets.client.CPacketSyncShopItems;
import aginsun.kingdoms.server.handlers.packets.server.SPacketBuild;
import aginsun.kingdoms.server.handlers.packets.server.SPacketBuy;
import aginsun.kingdoms.server.handlers.packets.server.SPacketOpenGui;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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
        register(SPacketOpenGui.class, Side.SERVER);
        register(CPacketSyncBuildings.class, Side.CLIENT);
    }

    private void register(Class clazz, Side side)
    {
        INSTANCE.registerMessage(clazz, clazz, packetId++, side);
    }
}
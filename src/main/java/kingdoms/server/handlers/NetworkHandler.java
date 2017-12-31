package kingdoms.server.handlers;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.handlers.packets.client.*;
import kingdoms.server.handlers.packets.server.*;
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
        register(SPacketOpenGui.class, Side.SERVER);
        register(CPacketSyncBuildings.class, Side.CLIENT);
        register(CPacketSyncBank.class, Side.CLIENT);
        register(SPacketAdd.class, Side.SERVER);

        //Entities
        register(SPacketBank.class, Side.SERVER);
        register(SPacketWarden.class, Side.SERVER);
    }

    private void register(Class<? extends AbstractPacket> packet, Side side)
    {
        try
        {
            INSTANCE.registerMessage(packet.newInstance(), packet, packetId++, side);
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
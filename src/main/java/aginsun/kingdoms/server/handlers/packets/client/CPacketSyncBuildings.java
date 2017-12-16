package aginsun.kingdoms.server.handlers.packets.client;

import aginsun.kingdoms.api.network.AbstractPacket;
import aginsun.kingdoms.server.handlers.Buildings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public final class CPacketSyncBuildings extends AbstractPacket<CPacketSyncBuildings>
{
    private static List<Boolean> buildings;

    public CPacketSyncBuildings(){}
    public CPacketSyncBuildings(List<Boolean> buildings)
    {
        this.buildings = buildings;
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        if (buildings != null)
            Buildings.INSTANCE.setBuildingList(buildings);
    }

    @Override
    public void handleServerSide(EntityPlayerMP player) {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        buildings = new ArrayList<>();

        for (byte i = 0; i < buf.readByte(); i++)
            buildings.add(buf.readBoolean());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(buildings.size());

        buildings.forEach(buf::writeBoolean);
    }
}
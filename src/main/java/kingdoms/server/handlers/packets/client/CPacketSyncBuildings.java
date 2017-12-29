package kingdoms.server.handlers.packets.client;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.handlers.Buildings;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public final class CPacketSyncBuildings extends AbstractPacket
{
    public CPacketSyncBuildings(){}
    public CPacketSyncBuildings(List<Boolean> buildings)
    {
        buf().writeByte(buildings.size());

        buildings.forEach(buf()::writeBoolean);
    }

    @Override
    public void client(EntityPlayer player)
    {
        List<Boolean> buildings = new ArrayList<>();

        for (byte i = 0; i < buf().readByte(); i++)
            buildings.add(buf().readBoolean());

        Buildings.INSTANCE.setBuildingList(buildings);
    }
}
package kingdoms.server.handlers.packets.server;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.handlers.Buildings;
import kingdoms.server.handlers.schematic.Schematic;
import kingdoms.server.handlers.schematic.SchematicHandler;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketBuild extends AbstractPacket
{
    public SPacketBuild(){}
    public SPacketBuild(short id)
    {
        buf().writeShort(id);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        buf().readShort();

        Schematic schematic = (new Schematic("/schematics/GuildCastle")).setPosition((int) player.posX, (int) player.posY, (int) player.posZ).setSpeed(300);
        SchematicHandler.INSTANCE.addBuilding(schematic);
        Buildings.INSTANCE.setBuildingTrue(0);
    }
}
package aginsun.kingdoms.server.handlers.packets.server;

import aginsun.kingdoms.api.network.AbstractPacket;
import aginsun.kingdoms.server.handlers.Buildings;
import aginsun.kingdoms.server.handlers.schematic.Schematic;
import aginsun.kingdoms.server.handlers.schematic.SchematicHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketBuild extends AbstractPacket<SPacketBuild>
{
    private static short id;

    public SPacketBuild(){}
    public SPacketBuild(short id)
    {
        this.id = id;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayerMP player)
    {
        Schematic schematic = (new Schematic("/schematics/GuildCastle")).setPosition((int) player.posX, (int) player.posY, (int) player.posZ).setSpeed(300);
        SchematicHandler.INSTANCE.addBuilding(schematic);
        Buildings.INSTANCE.setBuildingTrue(0);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeShort(id);
    }
}
package kingdoms.server.handlers.packets.server;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.WorldProvider;
import kingdoms.server.handlers.Buildings;
import kingdoms.server.handlers.schematic.Schematic;
import kingdoms.server.handlers.schematic.SchematicHandler;
import kingdoms.server.handlers.schematic.SchematicRegistry;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketBuild extends AbstractPacket
{
    public SPacketBuild(){}
    public SPacketBuild(byte id, boolean guild)
    {
        buf().writeByte(id);
        buf().writeBoolean(guild);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        int id = buf().readByte();

        WorldProvider worldProvider = WorldProvider.get(player.worldObj);

        if (buf().readBoolean())
        {
            Schematic schematic = (new Schematic("/schematics/GuildCastle")).setPosition((int) player.posX, (int) player.posY, (int) player.posZ).setSpeed(300);
            SchematicHandler.INSTANCE.addBuilding(schematic);
            Buildings.INSTANCE.setBuildingTrue(0);
        }
        else {
            SchematicRegistry registry = SchematicRegistry.INSTANCE;

            if (!registry.isRegistered())
            {
                registry.registerAllBuildings((int) player.posX, (int) player.posY, (int) player.posZ);

                worldProvider.guildPosX = (int) player.posX;
                worldProvider.guildPosY = (int) player.posY;
                worldProvider.guildPosZ = (int) player.posZ;
                worldProvider.markDirty();
            }

            Schematic schematic = registry.getSchematic(id).setSpeed(300);
            SchematicHandler.INSTANCE.addBuilding(schematic);
            Buildings.INSTANCE.setBuildingTrue(id);
        }
    }
}
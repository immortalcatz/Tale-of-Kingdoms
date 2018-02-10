package kingdoms.server.handlers.packets.server

import kingdoms.api.network.SimplePacket
import kingdoms.server.TaleOfKingdoms
import kingdoms.server.WorldProvider
import kingdoms.server.handlers.Buildings
import kingdoms.server.handlers.schematic.Schematic
import kingdoms.server.handlers.schematic.SchematicHandler
import kingdoms.server.handlers.schematic.SchematicRegistry
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.ChatComponentText

class SPacketBuild(): SimplePacket()
{
    constructor(id: Byte, guild: Boolean): this()
    {
        buf().writeByte(id.toInt())
        buf().writeBoolean(guild)
    }

    override fun server(player: EntityPlayerMP)
    {
        val id = buf().readByte().toInt()
        val guild = buf().readBoolean()

        if (TaleOfKingdoms.proxy.config.isOp())
        {
            if (MinecraftServer.getServer().configurationManager.func_152596_g(player.gameProfile))
            {
                build(id, guild, player)
            }
            else
            {
                player.addChatMessage(ChatComponentText("Sorry, you can\'t not building on this server!"))
            }
        }
        else
        {
            build(id, guild, player)
        }
    }

    private fun build(id: Int, guild: Boolean, player: EntityPlayerMP)
    {
        val worldProvider = WorldProvider.get(player.worldObj)

        val posX: Int = player.posX.toInt()
        val posY: Int = player.posY.toInt()
        val posZ: Int = player.posZ.toInt()

        if (guild)
        {
            val schematic = (Schematic("/assets/tok/schematics/GuildCastle")).setPosition(posX, posY, posZ).setSpeed(300)
            SchematicHandler.INSTANCE.addBuilding(schematic)
            worldProvider.guild = true
        }
        else
        {
            val registry = SchematicRegistry.INSTANCE

            if (!registry.isRegistered)
            {
                registry.registerAllBuildings(posX, posY, posZ)

                worldProvider.townPosX = posX
                worldProvider.townPosY = posY
                worldProvider.townPosZ = posZ
                worldProvider.markDirty()
            }

            val schematic = registry.getSchematic(id).setSpeed(300)
            SchematicHandler.INSTANCE.addBuilding(schematic)
            Buildings.INSTANCE.setBuildingTrue(id)
        }
    }
}
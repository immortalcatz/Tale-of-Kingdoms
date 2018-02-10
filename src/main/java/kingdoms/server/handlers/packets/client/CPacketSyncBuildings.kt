package kingdoms.server.handlers.packets.client

import kingdoms.api.network.SimplePacket
import net.minecraft.entity.player.EntityPlayer

class CPacketSyncBuildings(): SimplePacket()
{
    constructor(buildings: BooleanArray): this()
    {
        buf().writeByte(buildings.size)
        buildings.forEach { build -> buf().writeBoolean(build) }
    }

    override fun client(player: EntityPlayer)
    {
        val build = buf().readByte()

        val buildings = mutableListOf<Boolean>()

        (0 until build).forEach { _ -> buildings.add(buf().readBoolean()) }

        //Buildings.INSTANCE.setBuildingList(buildings);
    }
}
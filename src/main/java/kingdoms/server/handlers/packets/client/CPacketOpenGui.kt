package kingdoms.server.handlers.packets.client

import kingdoms.api.network.SimplePacket
import kingdoms.server.TaleOfKingdoms
import net.minecraft.entity.player.EntityPlayer

class CPacketOpenGui(): SimplePacket()
{
    constructor(id: Int): this()
    {
        buf().writeInt(id)
    }

    override fun client(player: EntityPlayer)
    {
        player.openGui(TaleOfKingdoms.instance, buf().readInt(), player.worldObj, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
    }
}
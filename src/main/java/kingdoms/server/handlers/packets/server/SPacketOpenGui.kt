package kingdoms.server.handlers.packets.server

import kingdoms.api.network.SimplePacket
import kingdoms.server.TaleOfKingdoms
import net.minecraft.entity.player.EntityPlayerMP

class SPacketOpenGui(): SimplePacket()
{
    constructor(id: Int): this()
    {
        buf().writeInt(id)
    }

    override fun server(player: EntityPlayerMP)
    {
        player.openGui(TaleOfKingdoms.instance, buf().readInt(), player.worldObj, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
    }
}
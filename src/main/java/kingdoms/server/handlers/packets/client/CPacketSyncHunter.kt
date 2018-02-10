package kingdoms.server.handlers.packets.client

import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import net.minecraft.entity.player.EntityPlayer

class CPacketSyncHunter(): SimplePacket()
{
    constructor(data: Boolean): this()
    {
        buf().writeBoolean(data)
    }

    override fun client(player: EntityPlayer)
    {
        val playerProvider = PlayerProvider.get(player)

        playerProvider.hunter = buf().readBoolean()
    }
}
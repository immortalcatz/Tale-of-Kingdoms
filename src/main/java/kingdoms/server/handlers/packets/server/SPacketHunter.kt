package kingdoms.server.handlers.packets.server

import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import kingdoms.server.handlers.NetworkHandler
import kingdoms.server.handlers.packets.client.CPacketSyncHunter
import net.minecraft.entity.player.EntityPlayerMP

class SPacketHunter(data: PlayerProvider): SimplePacket()
{
    init
    {
        buf().writeBoolean(data.hunter)
    }

    override fun server(player: EntityPlayerMP)
    {
        val playerProvider = PlayerProvider.get(player)
        playerProvider.hunter = buf().readBoolean()

        NetworkHandler.sendTo(CPacketSyncHunter(playerProvider.hunter), player)
    }
}
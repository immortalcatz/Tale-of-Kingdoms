package kingdoms.server.handlers.packets.client

import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import net.minecraft.entity.player.EntityPlayer

class CPacketSyncDataPlayer(): SimplePacket()
{
    constructor(provider: PlayerProvider): this()
    {
        buf().writeInt(provider.goldTotal)
        buf().writeInt(provider.bankGold)
        buf().writeInt(provider.glory)
        buf().writeBoolean(provider.hunter)
    }

    override fun client(player: EntityPlayer)
    {
        if (player.worldObj != null)
        {
            val provider = PlayerProvider.get(player)
            provider.goldTotal = buf().readInt()
            provider.bankGold = buf().readInt()
            provider.glory = buf().readInt()
            provider.hunter = buf().readBoolean()
        }
    }
}
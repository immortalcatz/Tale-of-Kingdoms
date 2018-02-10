package kingdoms.server.handlers.packets.client

import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import net.minecraft.entity.player.EntityPlayer

class CPacketSyncBank(): SimplePacket()
{
    constructor(count: Int, bank: Boolean): this()
    {
        buf().writeInt(count)
        buf().writeBoolean(bank)
    }

    override fun client(player: EntityPlayer)
    {
        val provider = PlayerProvider.get(player)

        val count = buf().readInt()
        val bank = buf().readBoolean()

        if (bank)
        {
            provider.bankGold = count
        }
        else
        {
            provider.goldTotal = count
        }
    }
}
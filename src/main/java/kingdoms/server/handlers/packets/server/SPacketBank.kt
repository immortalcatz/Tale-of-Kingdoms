package kingdoms.server.handlers.packets.server

import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import net.minecraft.entity.player.EntityPlayerMP

class SPacketBank(): SimplePacket()
{
    constructor(count: Short, deposit: Boolean): this()
    {
        buf().writeShort(count.toInt())
        buf().writeBoolean(deposit)
    }

    override fun server(player: EntityPlayerMP)
    {
        val provider = PlayerProvider.get(player)

        val count = buf().readShort().toInt()
        val deposit = buf().readBoolean()

        if (deposit)
        {
            if (provider.goldTotal >= count)
            {
                provider.addBankGold(count)
                provider.decreaseGold(count)
            }
        }
        else
        {
            if (provider.bankGold >= count)
            {
                provider.addGold(count)
                provider.decreaseBankGold(count)
            }
        }
    }
}
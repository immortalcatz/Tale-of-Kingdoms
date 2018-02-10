package kingdoms.server.handlers.packets.server

import cpw.mods.fml.common.network.ByteBufUtils
import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack

class SPacketBuy(): SimplePacket()
{
    constructor(stack: ItemStack, count: Byte, price: Int): this()
    {
        ByteBufUtils.writeItemStack(buf(), stack)
        buf().writeByte(count.toInt())
        buf().writeInt(price)
    }

    override fun server(player: EntityPlayerMP)
    {
        val stack = ByteBufUtils.readItemStack(buf())

        stack.stackSize = buf().readByte().toInt()

        val price = buf().readInt()

        val pr = PlayerProvider.get(player)

        if (pr.goldTotal >= price)
        {
            pr.decreaseGold(price)
        }

        if (!player.inventory.addItemStackToInventory(stack))
            player.entityDropItem(stack, 0F)
    }
}
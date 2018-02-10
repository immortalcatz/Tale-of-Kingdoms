package kingdoms.server.handlers.packets.server

import cpw.mods.fml.common.network.ByteBufUtils
import kingdoms.api.network.SimplePacket
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentTranslation

class SPacketAdd(): SimplePacket()
{
    constructor(stack: ItemStack, count: Int, message: String): this()
    {
        ByteBufUtils.writeItemStack(buf(), stack)
        buf().writeInt(count)
        ByteBufUtils.writeUTF8String(buf(), message)

        val buildings = mutableListOf<Boolean>()
    }

    override fun server(player: EntityPlayerMP)
    {
        val stack = ByteBufUtils.readItemStack(buf())

        stack.stackSize = buf().readInt()

        if (!player.inventory.addItemStackToInventory(stack))
            player.entityDropItem(stack, 0F)

        player.addChatMessage(ChatComponentTranslation(ByteBufUtils.readUTF8String(buf())))
    }
}
package kingdoms.server.handlers.packets.client

import cpw.mods.fml.common.network.ByteBufUtils
import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import kingdoms.server.TaleOfKingdoms
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class CPacketSyncShopItems(): SimplePacket()
{
    constructor(id: Int, stacks: List<ItemStack>): this()
    {
        buf().writeInt(id)
        buf().writeInt(stacks.size)

        stacks.forEach { stack -> ByteBufUtils.writeItemStack(buf(), stack) }
    }

    override fun client(player: EntityPlayer)
    {
        val id = buf().readInt()
        val ss = buf().readInt()

        val stacks = mutableListOf<ItemStack>()

        (0 until ss).forEach { _ -> stacks.add(ByteBufUtils.readItemStack(buf())); }

        PlayerProvider.get(player).stacks = stacks
        player.openGui(TaleOfKingdoms.instance, id, player.worldObj, player.posX.toInt(), player.posY.toInt(), player.posZ.toInt())
    }
}
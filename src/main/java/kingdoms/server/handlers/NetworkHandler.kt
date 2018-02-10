package kingdoms.server.handlers

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.relauncher.Side
import kingdoms.api.network.SimplePacket
import kingdoms.server.handlers.packets.client.*
import kingdoms.server.handlers.packets.server.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP

object NetworkHandler: SimpleNetworkWrapper("TokChannel")
{
    private var packetId: Int = 0

    init
    {
        register(CPacketSyncDataPlayer::class.java, Side.CLIENT)
        register(CPacketSyncShopItems::class.java, Side.CLIENT)
        register(SPacketBuild::class.java, Side.SERVER)
        register(SPacketBuy::class.java, Side.SERVER)
        register(SPacketOpenGui::class.java, Side.SERVER)
        register(CPacketSyncBuildings::class.java, Side.CLIENT)
        register(CPacketSyncBank::class.java, Side.CLIENT)
        register(SPacketAdd::class.java, Side.SERVER)

        //General
        register(CPacketOpenGui::class.java, Side.CLIENT)

        //Entities - S
        register(SPacketBank::class.java, Side.SERVER)
        register(SPacketWarden::class.java, Side.SERVER)
        register(SPacketHunter::class.java, Side.SERVER)

        //Entities - C
        register(CPacketSyncHunter::class.java, Side.CLIENT)
    }

    fun openGui(id: Int, player: EntityPlayer)
    {
        if (!player.worldObj.isRemote)
            sendTo(CPacketOpenGui(id), player as EntityPlayerMP)
    }

    private fun register(packet: Class<out SimplePacket>, side: Side)
    {
        try
        {
            registerMessage(packet.newInstance(), packet, packetId++, side)
        }
        catch (e: InstantiationException)
        {
            e.printStackTrace()
        }
        catch (s: IllegalAccessException)
        {
            s.printStackTrace()
        }
    }
}
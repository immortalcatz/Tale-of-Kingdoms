package kingdoms.server.handlers.packets.server

import cpw.mods.fml.common.network.ByteBufUtils
import kingdoms.api.network.SimplePacket
import kingdoms.server.PlayerProvider
import kingdoms.server.handlers.UltimateHelper
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayerMP

class SPacketWarden(): SimplePacket()
{
    constructor(name: String): this()
    {
        ByteBufUtils.writeUTF8String(buf(), name)
    }

    override fun server(player: EntityPlayerMP)
    {
        val provider = PlayerProvider.get(player)

        if (provider.goldTotal >= 1000)
        {
            val entity: EntityLiving = UltimateHelper.INSTANCE.getEntity(ByteBufUtils.readUTF8String(buf()), player.worldObj) as EntityLiving
            entity.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0F, 0.0F)
            player.worldObj.spawnEntityInWorld(entity)

            PlayerProvider.get(player).decreaseGold(1000)
        }
    }
}
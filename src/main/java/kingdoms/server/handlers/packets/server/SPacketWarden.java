package kingdoms.server.handlers.packets.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import kingdoms.api.network.AbstractPacket;
import kingdoms.server.PlayerProvider;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketWarden extends AbstractPacket
{
    public SPacketWarden(){}
    public SPacketWarden(String name)
    {
        ByteBufUtils.writeUTF8String(buf(), name);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        PlayerProvider provider = PlayerProvider.get(player);

        if (provider.getGoldTotal() >= 1000)
        {
            EntityLiving entity = (EntityLiving) UltimateHelper.INSTANCE.getEntity(ByteBufUtils.readUTF8String(buf()), player.worldObj);
            entity.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0F, 0.0F);
            player.worldObj.spawnEntityInWorld(entity);

            PlayerProvider.get(player).decreaseGold(1000);
        }
    }
}
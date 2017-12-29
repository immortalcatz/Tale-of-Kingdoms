package kingdoms.server.handlers.packets.server;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.handlers.UltimateHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketSpawnEntity extends AbstractPacket
{
    public SPacketSpawnEntity(){}
    public SPacketSpawnEntity(String name)
    {
        ByteBufUtils.writeUTF8String(buf(), name);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        EntityLiving entity = (EntityLiving) UltimateHelper.INSTANCE.getEntity(ByteBufUtils.readUTF8String(buf()), player.worldObj);
        entity.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0F, 0.0F);
        player.worldObj.spawnEntityInWorld(entity);
    }
}
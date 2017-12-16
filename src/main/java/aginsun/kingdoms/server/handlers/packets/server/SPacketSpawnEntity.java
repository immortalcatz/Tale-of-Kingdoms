package aginsun.kingdoms.server.handlers.packets.server;

import aginsun.kingdoms.api.network.AbstractPacket;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketSpawnEntity extends AbstractPacket<SPacketSpawnEntity>
{
    private static String name;

    public SPacketSpawnEntity(){}
    public SPacketSpawnEntity(String name)
    {
        this.name = name;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayerMP player)
    {
        EntityLiving entity = (EntityLiving) UltimateHelper.INSTANCE.getEntity(name, player.worldObj);
        entity.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0F, 0.0F);
        player.worldObj.spawnEntityInWorld(entity);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, name);
    }
}
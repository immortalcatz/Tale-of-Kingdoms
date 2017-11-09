package aginsun.kingdoms.server.handlers.packets;

import aginsun.kingdoms.server.PlayerProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public final class CPacketSyncDataPlayer extends AbstractPacket
{
    public CPacketSyncDataPlayer(){}
    public CPacketSyncDataPlayer(PlayerProvider provider)
    {

    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        if (player != null && player.worldObj != null)
        {

        }
    }

    @Override
    public void handleServerSide(EntityPlayerMP player) {}

    @Override
    public void fromBytes(ByteBuf buf)
    {

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }
}
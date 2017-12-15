package aginsun.kingdoms.server.handlers.packets;

import aginsun.kingdoms.api.network.AbstractPacket;
import aginsun.kingdoms.server.PlayerProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public final class CPacketSyncDataPlayer extends AbstractPacket
{
    private static int goldTotal;
    private static int glory;

    public CPacketSyncDataPlayer(){}
    public CPacketSyncDataPlayer(PlayerProvider provider)
    {
        this.goldTotal = provider.getEconomy().getGoldTotal();
        this.glory = provider.getGlory();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        if (player != null && player.worldObj != null)
        {
            PlayerProvider.get(player).getEconomy().setGoldTotal(goldTotal);
            PlayerProvider.get(player).setGlory(glory);
        }
    }

    @Override
    public void handleServerSide(EntityPlayerMP player) {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        goldTotal = buf.readInt();
        glory = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(goldTotal);
        buf.writeInt(glory);
    }
}
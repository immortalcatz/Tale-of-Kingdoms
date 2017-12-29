package kingdoms.server.handlers.packets.client;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.PlayerProvider;
import net.minecraft.entity.player.EntityPlayer;

public final class CPacketSyncDataPlayer extends AbstractPacket
{
    public CPacketSyncDataPlayer(){}
    public CPacketSyncDataPlayer(PlayerProvider provider)
    {
        buf().writeInt(provider.getGoldTotal());
        buf().writeInt(provider.getBankGold());
        buf().writeInt(provider.getGlory());
    }

    @Override
    public void client(EntityPlayer player)
    {
        if (player != null && player.worldObj != null)
        {
            PlayerProvider.get(player).setGoldTotal(buf().readInt());
            PlayerProvider.get(player).setBankGold(buf().readInt());
            PlayerProvider.get(player).setGlory(buf().readInt());
        }
    }
}
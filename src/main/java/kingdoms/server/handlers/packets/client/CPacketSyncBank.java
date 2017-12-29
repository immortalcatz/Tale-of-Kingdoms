package kingdoms.server.handlers.packets.client;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.PlayerProvider;
import net.minecraft.entity.player.EntityPlayer;

public final class CPacketSyncBank extends AbstractPacket
{
    public CPacketSyncBank(){}
    public CPacketSyncBank(int count, boolean bank)
    {
        buf().writeInt(count);
        buf().writeBoolean(bank);
    }

    @Override
    public void client(EntityPlayer player)
    {
        PlayerProvider provider = PlayerProvider.get(player);

        int count = buf().readInt();
        boolean bank = buf().readBoolean();

        if (bank)
        {
            provider.setBankGold(count);
        }
        else {
            provider.setGoldTotal(count);
        }
    }
}
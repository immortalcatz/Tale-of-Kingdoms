package kingdoms.server.handlers.packets.server;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.PlayerProvider;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketBank extends AbstractPacket
{
    public SPacketBank(){}
    public SPacketBank(short count, boolean deposit)
    {
        buf().writeShort(count);
        buf().writeBoolean(deposit);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        PlayerProvider provider = PlayerProvider.get(player);

        int count = buf().readShort();
        boolean deposit = buf().readBoolean();

        if (deposit)
        {
            if (provider.getGoldTotal() >= count)
            {
                provider.addBankGold(count);
                provider.decreaseGold(count);
            }
        }
        else {
            if (provider.getBankGold() >= count)
            {
                provider.addGold(count);
                provider.decreaseBankGold(count, player);
            }
        }
    }
}
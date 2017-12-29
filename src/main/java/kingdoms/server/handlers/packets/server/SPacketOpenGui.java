package kingdoms.server.handlers.packets.server;

import kingdoms.api.network.AbstractPacket;
import kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketOpenGui extends AbstractPacket
{
    public SPacketOpenGui(){}
    public SPacketOpenGui(int id)
    {
        buf().writeInt(id);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        player.openGui(TaleOfKingdoms.instance, buf().readInt(), player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }
}
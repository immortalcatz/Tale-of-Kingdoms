package aginsun.kingdoms.server.handlers.packets;

import aginsun.kingdoms.api.network.AbstractPacket;
import aginsun.kingdoms.server.TaleOfKingdoms;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public final class SPacketOpenGui extends AbstractPacket<SPacketOpenGui>
{
    private static int id;

    public SPacketOpenGui(){}
    public SPacketOpenGui(int id)
    {
        this.id = id;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayerMP player)
    {
        player.openGui(TaleOfKingdoms.instance, id, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
    }
}
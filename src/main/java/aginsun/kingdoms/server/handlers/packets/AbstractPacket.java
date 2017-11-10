package aginsun.kingdoms.server.handlers.packets;

import aginsun.kingdoms.server.TaleOfKingdoms;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public abstract class AbstractPacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>
{
    @Override
    public REQ onMessage(final REQ message, final MessageContext ctx)
    {
        if(ctx.side == Side.SERVER)
        {
            handleServerSide(ctx.getServerHandler().playerEntity);
        }
        else
        {
            handleClientSide(TaleOfKingdoms.proxy.getPlayer(ctx));
        }
        return null;
    }

    public abstract void handleClientSide(final EntityPlayer player);
    public abstract void handleServerSide(final EntityPlayerMP player);
}
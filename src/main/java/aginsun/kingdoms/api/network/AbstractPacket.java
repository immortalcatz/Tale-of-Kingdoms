package aginsun.kingdoms.api.network;

import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class AbstractPacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>
{
    @Override
    public REQ onMessage(final REQ message, final MessageContext ctx)
    {
        if (ctx.side.isServer())
            handleServerSide(ctx.getServerHandler().player);
        else
            handleClientSide(TaleOfKingdoms.proxy.getPlayer(ctx));
        return null;
    }

    public abstract void handleClientSide(final EntityPlayer player);
    public abstract void handleServerSide(final EntityPlayerMP player);
}
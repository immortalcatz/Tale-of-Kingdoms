package aginsun.kingdoms.client;

import aginsun.kingdoms.server.ServerProxy;
import aginsun.kingdoms.server.handlers.EntitiesRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public final class ClientProxy extends ServerProxy
{
    @Override
    public void pre(FMLPreInitializationEvent e)
    {
        super.pre(e);
        EntitiesRegister.INSTANCE.render();
        new ClientEvents();
    }

    @Override
    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayer(ctx);
    }
}
package aginsun.kingdoms.client;

import aginsun.kingdoms.server.ServerProxy;
import aginsun.kingdoms.server.handlers.EntitiesRegister;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

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
        return ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayer(ctx);
    }
}
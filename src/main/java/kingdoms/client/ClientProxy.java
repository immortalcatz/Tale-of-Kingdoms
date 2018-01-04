package kingdoms.client;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import kingdoms.server.ServerProxy;
import kingdoms.server.handlers.EntitiesRegister;

public final class ClientProxy extends ServerProxy
{
    @Override
    public void pre(FMLPreInitializationEvent e)
    {
        super.pre(e);
        EntitiesRegister.INSTANCE.render();
        new ClientEvents();
    }
}
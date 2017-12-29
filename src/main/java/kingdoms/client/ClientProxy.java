package kingdoms.client;

import kingdoms.server.ServerProxy;
import kingdoms.server.handlers.EntitiesRegister;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
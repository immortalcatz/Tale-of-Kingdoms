package kingdoms.client

import cpw.mods.fml.common.event.FMLPreInitializationEvent
import kingdoms.server.ServerProxy
import kingdoms.server.handlers.EntitiesRegister

class ClientProxy: ServerProxy()
{
    override fun pre(e: FMLPreInitializationEvent)
    {
        super.pre(e)
        EntitiesRegister.render()
        ClientEvents()
    }
}
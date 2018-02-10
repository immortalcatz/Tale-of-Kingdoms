package kingdoms.server

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.Mod.Instance
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent

@Mod(modid = "tok", name = "Tale of Kingdoms", version = "1.0.14")
class TaleOfKingdoms
{
    companion object
    {
        @Instance("tok")
        lateinit var instance: TaleOfKingdoms

        @SidedProxy(clientSide = "kingdoms.client.ClientProxy", serverSide = "kingdoms.server.ServerProxy")
        lateinit var proxy: ServerProxy
    }

    @EventHandler
    fun pre(e: FMLPreInitializationEvent)
    {
        proxy.pre(e)
    }

    @EventHandler
    fun init(e: FMLInitializationEvent)
    {
        proxy.init(e)
    }

    @EventHandler
    fun starting(e: FMLServerStartingEvent)
    {
        proxy.starting(e)
    }
}
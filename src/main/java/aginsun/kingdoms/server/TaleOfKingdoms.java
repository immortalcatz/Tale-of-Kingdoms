package aginsun.kingdoms.server;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "taleofkingdoms", name = "Tale of Kingdoms", version = "1.0.6")
public final class TaleOfKingdoms
{
    @Instance("taleofkingdoms")
    public static TaleOfKingdoms instance;

    @SidedProxy(clientSide = "aginsun.kingdoms.client.ClientProxy", serverSide = "aginsun.kingdoms.server.ServerProxy")
    public static ServerProxy proxy;

    @EventHandler
    public void pre(FMLPreInitializationEvent e)
    {
        proxy.pre(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @EventHandler
    public void starting(FMLServerStartingEvent e)
    {
        proxy.starting(e);
    }
}
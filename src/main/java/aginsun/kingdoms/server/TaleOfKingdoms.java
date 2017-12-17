package aginsun.kingdoms.server;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "taleofkingdoms", name = "Tale of Kingdoms", version = "1.0.12")
public final class TaleOfKingdoms
{
    @Mod.Instance("taleofkingdoms")
    public static TaleOfKingdoms instance;

    @SidedProxy(clientSide = "aginsun.kingdoms.client.ClientProxy", serverSide = "aginsun.kingdoms.server.ServerProxy")
    public static ServerProxy proxy;

    @Mod.EventHandler
    public void pre(FMLPreInitializationEvent e)
    {
        proxy.pre(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void starting(FMLServerStartingEvent e)
    {
        proxy.starting(e);
    }
}
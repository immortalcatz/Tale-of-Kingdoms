package aginsun.kingdoms.server;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import aginsun.kingdoms.server.entities.EntityRegistryToK;
import aginsun.kingdoms.server.items.ItemCoin;
import net.minecraft.item.Item;

@Mod(modid = "taleofkingdoms", name = "Tale of Kingdoms", version = "1.0.4")
public final class TaleOfKingdoms
{
    @Instance("taleofkingdoms")
    public static TaleOfKingdoms instance;

    @SidedProxy(clientSide = "aginsun.kingdoms.client.ClientProxy", serverSide = "aginsun.kingdoms.server.ServerProxy")
    public static ServerProxy proxy;

    public static final Item coins = new ItemCoin().setUnlocalizedName("Coins");

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        proxy.registerRenderers();
        proxy.pre(e);
        GameRegistry.registerItem(coins, "Coints");
    }

    @EventHandler
    public void load(FMLInitializationEvent e)
    {
        proxy.init(e);
        new EntityRegistryToK().registerEntities();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent e)
    {
        proxy.starting(e);
    }
}
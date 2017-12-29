package kingdoms.server;

import kingdoms.server.commands.CommandTOK;
import kingdoms.server.handlers.*;
import kingdoms.server.items.ItemCoin;
import kingdoms.server.items.ItemSpawnEgg;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.CommandHandler;
import net.minecraft.item.Item;

public class ServerProxy
{
    private Config config;
    public static final Item coins = new ItemCoin().setUnlocalizedName("Coins");

    public void pre(FMLPreInitializationEvent e)
    {
        config = new Config(e);
        EntitiesRegister.INSTANCE.register();

        GameRegistry.registerItem(coins, "Coins");
        GameRegistry.registerItem(new ItemSpawnEgg(), "MonsterPlacer");

        new ServerEvents();
        new NetworkHandler();
    }

    public void init(FMLInitializationEvent e)
    {
        new GuiHandler();
    }

    public void starting(FMLServerStartingEvent e)
    {
        ((CommandHandler) e.getServer().getCommandManager()).registerCommand(new CommandTOK());
    }

    public Config getConfig()
    {
        return config;
    }
}
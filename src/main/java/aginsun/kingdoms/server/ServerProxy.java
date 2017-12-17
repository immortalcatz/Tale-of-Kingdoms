package aginsun.kingdoms.server;

import aginsun.kingdoms.server.commands.CommandTOK;
import aginsun.kingdoms.server.handlers.*;
import aginsun.kingdoms.server.items.ItemCoin;
import aginsun.kingdoms.server.items.ItemSpawnEgg;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ServerProxy
{
    private Config config;
    public static final Item
            coins = new ItemCoin().setUnlocalizedName("Coins").setRegistryName("Coints"),
            egg = new ItemSpawnEgg().setRegistryName("MonsterPlacer");

    public void pre(FMLPreInitializationEvent e)
    {
        config = new Config(e);
        EntitiesRegister.INSTANCE.register();

        ForgeRegistries.ITEMS.registerAll(coins, egg);

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

    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.getServerHandler().player;
    }
}
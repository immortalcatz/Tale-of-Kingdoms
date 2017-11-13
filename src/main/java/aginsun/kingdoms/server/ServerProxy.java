package aginsun.kingdoms.server;

import aginsun.kingdoms.server.commands.CommandTOK;
import aginsun.kingdoms.server.handlers.EntitiesRegister;
import aginsun.kingdoms.server.handlers.GuiHandler;
import aginsun.kingdoms.server.handlers.NetworkHandler;
import aginsun.kingdoms.server.handlers.ServerEvents;
import aginsun.kingdoms.server.items.ItemCoin;
import aginsun.kingdoms.server.items.ItemSpawnEgg;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ServerProxy
{
    public static final Item coins = new ItemCoin().setUnlocalizedName("Coins");

    public void pre(FMLPreInitializationEvent e)
    {
        EntitiesRegister.INSTANCE.register();
        GameRegistry.registerItem(coins, "Coints");
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

    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.getServerHandler().playerEntity;
    }
}
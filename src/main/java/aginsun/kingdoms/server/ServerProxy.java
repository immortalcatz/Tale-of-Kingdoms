package aginsun.kingdoms.server;

import aginsun.kingdoms.client.gui.GuiSell;
import aginsun.kingdoms.server.entities.*;
import aginsun.kingdoms.server.handlers.EntitiesRegister;
import aginsun.kingdoms.server.handlers.NetworkHandler;
import aginsun.kingdoms.server.commands.CommandTOK;
import aginsun.kingdoms.server.handlers.ServerEvents;
import aginsun.kingdoms.server.items.ItemCoin;
import aginsun.kingdoms.server.items.ItemSpawnEgg;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import aginsun.kingdoms.server.container.ContainerSell;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

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
        NetworkRegistry.INSTANCE.registerGuiHandler(TaleOfKingdoms.instance, new IGuiHandler()
        {
            @Override
            public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
            {
                if (ID == 1)
                {
                    return new ContainerSell(new TileEntitySell(), player.inventory);
                }
                return null;
            }

            @Override
            public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
            {
                if (ID == 1)
                {
                    return new GuiSell(player.inventory, new TileEntitySell());
                }
                return null;
            }
        });
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
package aginsun.kingdoms.server;

import aginsun.kingdoms.server.handlers.resources.GoldKeeper;
import aginsun.kingdoms.server.commands.CommandTOK;
import aginsun.kingdoms.server.handlers.ServerEvents;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import aginsun.kingdoms.server.entities.TileEntitySell;
import aginsun.kingdoms.server.inventory.ContainerSell;
import net.minecraft.command.CommandHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ServerProxy implements IGuiHandler
{
    public GoldKeeper gold;

    public void pre(FMLPreInitializationEvent e)
    {
        new ServerEvents();
    }

    public void init(FMLInitializationEvent e)
    {

    }

    public void starting(FMLServerStartingEvent e)
    {
        ((CommandHandler) e.getServer().getCommandManager()).registerCommand(new CommandTOK());
    }

    @Deprecated
    public void registerRenderers() {}

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        if (id == 1)
        {
            return new ContainerSell(new TileEntitySell(), player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
}
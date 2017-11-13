package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.client.gui.GuiBank;
import aginsun.kingdoms.client.gui.GuiSell;
import aginsun.kingdoms.client.gui.GuiWardenMenu;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.container.ContainerSell;
import aginsun.kingdoms.server.entities.TileEntitySell;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class GuiHandler implements IGuiHandler
{
    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(TaleOfKingdoms.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 1: return new ContainerSell(new TileEntitySell(), player.inventory);
            default: return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case 0: return new GuiBank(player, world);
            case 1: return new GuiSell(player.inventory, new TileEntitySell());
            case 2: return new GuiWardenMenu(player, world);
            default: return null;
        }
    }
}
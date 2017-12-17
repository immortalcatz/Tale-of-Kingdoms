package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.client.gui.*;
import aginsun.kingdoms.server.PlayerProvider;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.container.ContainerSell;
import aginsun.kingdoms.server.entities.TileEntitySell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public final class GuiHandler implements IGuiHandler
{
    public static final int
            GUI_BANK = 0, GUI_SELL = 1, GUI_WARDEN = 2, GUI_BUILD = 3, GUI_FARMER = 4, GUI_FISHER = 5, GUI_SHOPLIST = 6,
            GUI_HEADCOMMANDER = 7, GUI_HUNTER = 8, GUI_INN = 9, GUI_LUMBER = 10, GUI_MAGEHALL = 11, GUI_PRIEST = 12,
            GUI_QUARRY = 13, GUI_STABLE = 14, GUI_STOCK = 15, GUI_TAVERN = 16, GUI_CONQUEST = 17;

    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(TaleOfKingdoms.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case GUI_SELL: return new ContainerSell(new TileEntitySell(), player.inventory);
            default: return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        /*for (Object o : world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(x, y, z, x, y, z)))
        {
            EntityCreature creature = (EntityCreature) o;
            if (creature instanceof EntityWorkerMember)
            {
                return new GuiWorker(player, (EntityWorkerMember) creature);
            }
        }*/

        switch (ID)
        {
            case GUI_BANK: return new GuiBank(player, world);
            case GUI_SELL: return new GuiSell(player.inventory, new TileEntitySell());
            case GUI_WARDEN: return new GuiWardenMenu(player, world);
            case GUI_BUILD: return new GuiBuildScreen(world);
            case GUI_FARMER: return new GuiFoodKeeper(player, world);
            case GUI_FISHER: return new GuiFisher(player);
            case GUI_SHOPLIST: return new GuiShopList(player, world, PlayerProvider.get(player).stacks);
            case GUI_HEADCOMMANDER: return new GuiReinforcementPool(world);
            case GUI_HUNTER: return new GuiHunter(player, world);
            case GUI_INN: return new GuiInnMenu(player, world);
            case GUI_LUMBER: return new GuiLumber(player, world);
            case GUI_MAGEHALL: return new GuiMageHall(player, world);
            case GUI_PRIEST: return new GuiPriest(player, world);
            case GUI_QUARRY: return new GuiQuarry(player, world);
            case GUI_STABLE: return new GuiStableMaster(player, world);
            case GUI_STOCK: return new GuiStockList(player, world);
            case GUI_TAVERN: return new GuiTavernGame(player, world);
            case GUI_CONQUEST: return new GuiStartConquest(player);
            default: return null;
        }
    }
}
package kingdoms.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kingdoms.api.gui.GuiPriceBar;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.entities.EntityBuilderKeeper;
import kingdoms.server.handlers.Buildings;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.UltimateHelper;
import kingdoms.server.handlers.packets.server.SPacketBuild;
import kingdoms.server.handlers.resources.ResourcesHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.stream.IntStream;

@SideOnly(Side.CLIENT)
public class GuiBuildScreen extends GuiScreenToK
{
    private final EntityBuilderKeeper builder;
    private GuiPriceBar woodBar, cobblestoneBar;

    public GuiBuildScreen(EntityPlayer player, World world, EntityBuilderKeeper builder)
    {
        super(player, world);
        this.builder = builder;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();

        int bar = this.width / 2 - 100;

        this.woodBar = new GuiPriceBar(0, bar, 40, 90, 12, 1.0F, "red");
        this.woodBar.setBar((float) ResourcesHandler.INSTANCE.getwoodResource() / 320.0F);

        this.cobblestoneBar = new GuiPriceBar(1, bar, 60, 90, 12, 1.0F, "red");
        this.cobblestoneBar.setBar((float) ResourcesHandler.INSTANCE.getcobbleResource() / 320.0F);

        this.buttonList.add(new GuiButton(1, this.width / 2 - 25, 220, 50, 20, I18n.format("gui.exit")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 110, 80, 110, 20, I18n.format("gui.build.give.logs")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 5, 80, 110, 20, I18n.format("gui.build.give.cobble")));

        if (!Buildings.INSTANCE.getBuilding(1))
        {
            this.addButton(4, this.width / 2 - 55, 130, I18n.format("gui.build.buildings.stage_1"));
        }
        else if (!Buildings.INSTANCE.getBuilding(8) && Buildings.INSTANCE.getBuilding(1))
        {
            if (!Buildings.INSTANCE.getBuilding(2))
            {
                this.addButton(5, this.width / 2 - 120, 120,  I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(3))
            {
                this.addButton(6, this.width / 2 + 5, 120, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(4))
            {
                this.addButton(7, this.width / 2 - 120, 180, I18n.format("gui.build.buildings.largeHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(6))
            {
                this.addButton(8, this.width / 2 - 120, 140, I18n.format("gui.build.buildings.itemsShop"));
            }

            if (!Buildings.INSTANCE.getBuilding(7))
            {
                this.addButton(9, this.width / 2 - 120, 160, I18n.format("gui.build.buildings.stockMarket"));
            }

            if (!Buildings.INSTANCE.getBuilding(8) && this.buttonList.size() == 3)
            {
                this.addButton(10, this.width / 2 - 55, 130, I18n.format("gui.build.buildings.stage_2"));
            }
        }
        else if (Buildings.INSTANCE.getBuilding(8) && !Buildings.INSTANCE.getBuilding(16))
        {
            if (!Buildings.INSTANCE.getBuilding(9))
            {
                this.addButton(11, this.width / 2 - 120, 120, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(10))
            {
                this.addButton(12, this.width / 2 - 120, 180, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(11))
            {
                this.addButton(13, this.width / 2 - 120, 140, I18n.format("gui.build.buildings.largeHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(12))
            {
                this.addButton(14, this.width / 2 - 120, 160, I18n.format("gui.build.buildings.builderHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(13))
            {
                this.addButton(15, this.width / 2 + 5, 120, I18n.format("gui.build.buildings.barracks"));
            }

            if (!Buildings.INSTANCE.getBuilding(14))
            {
                this.addButton(16, this.width / 2 + 5, 140, I18n.format("gui.build.buildings.foodShop"));
            }

            if (!Buildings.INSTANCE.getBuilding(15))
            {
                this.addButton(17, this.width / 2 + 5, 160, I18n.format("gui.build.buildings.blocksShop"));
            }

            if (!Buildings.INSTANCE.getBuilding(16) && this.buttonList.size() == 3)
            {
                this.addButton(18, this.width / 2 - 55, 130, I18n.format("gui.build.buildings.stage_3"));
            }
        }
        else if (Buildings.INSTANCE.getBuilding(16) && !Buildings.INSTANCE.getBuilding(25))
        {
            if (!Buildings.INSTANCE.getBuilding(17))
            {
                this.addButton(19, this.width / 2 - 120, 140, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(18))
            {
                this.addButton(20, this.width / 2 - 120, 160, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(19))
            {
                this.addButton(21, this.width / 2 - 120, 180, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(20))
            {
                this.addButton(22, this.width / 2 + 5, 160, I18n.format("gui.build.buildings.largeHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(21))
            {
                this.addButton(23, this.width / 2 - 120, 120, I18n.format("gui.build.buildings.tavern"));
            }

            if (!Buildings.INSTANCE.getBuilding(22))
            {
                this.addButton(24, this.width / 2 + 5, 140, I18n.format("gui.build.buildings.chapel"));
            }
            if (!Buildings.INSTANCE.getBuilding(23))
            {
                this.addButton(25, this.width / 2 + 5, 180, I18n.format("gui.build.buildings.library"));
            }

            if (!Buildings.INSTANCE.getBuilding(24))
            {
                this.addButton(26, this.width / 2 + 5, 120, I18n.format("gui.build.buildings.mageHall"));
            }

            if (!Buildings.INSTANCE.getBuilding(25) && this.buttonList.size() == 3)
            {
                this.addButton(27, this.width / 2 - 55, 130, I18n.format("gui.build.buildings.stage_4"));
            }
        }
        else if (Buildings.INSTANCE.getBuilding(25) && !Buildings.INSTANCE.getBuilding(26))
        {
            if (!Buildings.INSTANCE.getBuilding(31))
            {
                this.addButton(28, this.width / 2 - 120, 120, I18n.format("gui.build.buildings.lightHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(29))
            {
                this.addButton(29, this.width / 2 - 120, 140, I18n.format("gui.build.buildings.easternTower"));
            }

            if (!Buildings.INSTANCE.getBuilding(30))
            {
                this.addButton(30, this.width / 2 - 120, 160, I18n.format("gui.build.buildings.fishermanHut"));
            }

            if (!Buildings.INSTANCE.getBuilding(32))
            {
                this.addButton(31, this.width / 2 + 5, 140, I18n.format("gui.build.buildings.windmill"));
            }

            if (!Buildings.INSTANCE.getBuilding(33))
            {
                this.addButton(32, this.width / 2 + 5, 120, I18n.format("gui.build.buildings.observerPost"));
            }

            if (!Buildings.INSTANCE.getBuilding(26) && this.buttonList.size() == 3)
            {
                this.buttonList.add(new GuiButton(33, this.width / 2 - 65, 130, 140, 20, I18n.format("gui.build.buildings.stage_5")));
            }
        }
        else if (Buildings.INSTANCE.getBuilding(26) && !Buildings.INSTANCE.getBuilding(27))
        {
            if (!Buildings.INSTANCE.getBuilding(34))
            {
                this.addButton(34, this.width / 2 - 120, 120, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(35))
            {
                this.addButton(35, this.width / 2 - 120, 140, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(36))
            {
                this.addButton(36, this.width / 2 - 120, 160, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(37))
            {
                this.addButton(37, this.width / 2 - 120, 180, I18n.format("gui.build.buildings.smallHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(38))
            {
                this.addButton(38, this.width / 2 - 120, 200, I18n.format("gui.build.buildings.largeHouse"));
            }

            if (!Buildings.INSTANCE.getBuilding(39))
            {
                this.addButton(39, this.width / 2 + 5, 120, I18n.format("gui.build.buildings.centerTower"));
            }

            if (!Buildings.INSTANCE.getBuilding(40))
            {
                this.addButton(40, this.width / 2 + 5, 140, I18n.format("gui.build.buildings.northernTower"));
            }

            if (!Buildings.INSTANCE.getBuilding(28))
            {
                this.addButton(41, this.width / 2 + 5, 160, I18n.format("gui.build.buildings.coliseum"));
            }

            if (!Buildings.INSTANCE.getBuilding(41))
            {
                this.addButton(42, this.width / 2 + 5, 180, I18n.format("gui.build.buildings.stables"));
            }

            if (!Buildings.INSTANCE.getBuilding(42))
            {
                this.addButton(43, this.width / 2 + 5, 200, I18n.format("gui.build.buildings.zeppelin"));
            }

            if (!Buildings.INSTANCE.getBuilding(27) && this.buttonList.size() == 3)
            {
                this.addButton(44, this.width / 2 - 65, 130, I18n.format("gui.build.buildings.castle"));
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton guibutton)
    {
        int j;

        switch(guibutton.id)
        {
            case 1:
                this.mc.displayGuiScreen(null);
                break;
            case 2:
                j = getInventorySlotContainItem(Item.getItemFromBlock(Blocks.log), mc.thePlayer.inventory.mainInventory);

                if (j >= 0 && this.mc.thePlayer.inventory.getStackInSlot(j).stackSize == 64)
                {
                    this.mc.thePlayer.inventory.setInventorySlotContents(j, null);
                    ResourcesHandler.INSTANCE.addwoodResource(64);
                }
                break;
            case 3:
                j = getInventorySlotContainItem(Item.getItemFromBlock(Blocks.cobblestone), mc.thePlayer.inventory.mainInventory);

                if (j >= 0 && this.mc.thePlayer.inventory.getStackInSlot(j).stackSize == 64)
                {
                    this.mc.thePlayer.inventory.setInventorySlotContents(j, null);
                    ResourcesHandler.INSTANCE.addcobbleResource(64);
                }
                break;
            case 4:
                this.createBuilding(1, 0, 0);
                break;
            case 5:
                if (this.x(128, 192))
                {
                    this.createBuilding(2, 128, 192);
                }
                break;
            case 6:
                if (this.x(128, 192))
                {
                    this.createBuilding(3, 128, 192);
                }
                break;
            case 7:
                if (this.x(320, 192))
                {
                    this.createBuilding(4, 320, 192);
                }
                break;
            case 8:
                if (this.x(256, 256))
                {
                    this.createBuilding(6, 256, 256);
                }
                break;
            case 9:
                if (this.x(128, 128))
                {
                    this.createBuilding(7, 192, 192);
                }
                break;
            case 10:
                if (this.x(128, 128))
                {
                    this.createBuilding(8, 128, 128);
                }
                break;
            case 11:
                if (this.x(128, 192))
                {
                    this.createBuilding(9, 128, 192);
                }
                break;
            case 12:
                if (this.x(128, 192))
                {
                    this.createBuilding(10, 128, 192);
                }
                break;
            case 13:
                if (this.x(320, 192))
                {
                    this.createBuilding(11, 320, 192);
                }
                break;
            case 14:
                if (this.x(128, 128))
                {
                    this.createBuilding(12, 128, 128);
                }
                break;
            case 15:
                if (this.x(320, 320))
                {
                    this.createBuilding(13, 320, 320);
                }
                break;
            case 16:
                if (this.x(256, 192))
                {
                    this.createBuilding(14, 256, 192);
                }
                break;
            case 17:
                if (this.x(320, 256))
                {
                    this.createBuilding(15, 320, 256);
                }
                break;
            case 18:
                if (this.x(128, 128))
                {
                    this.createBuilding(16, 128, 128);
                }
                break;
            case 19:
                if (this.x(128, 192))
                {
                    this.createBuilding(17, 128, 192);
                }
                break;
            case 20:
                if (this.x(128, 192))
                {
                    this.createBuilding(18, 128, 192);
                }
                break;
            case 21:
                if (this.x(128, 192))
                {
                    this.createBuilding(19, 128, 192);
                }
                break;
            case 22:
                if (this.x(320, 192))
                {
                    this.createBuilding(20, 320, 192);
                }
                break;
            case 23:
                if (this.x(128, 320))
                {
                    this.createBuilding(21, 128, 320);
                }
                break;
            case 24:
                if (this.x(320, 320))
                {
                    this.createBuilding(22, 320, 320);
                }
                break;
            case 25:
                if (this.x(256, 256))
                {
                    this.createBuilding(23, 256, 256);
                }
                break;
            case 26:
                if (this.x(320, 256))
                {
                    this.createBuilding(24, 320, 256);
                }
                break;
            case 27:
                if (this.x(256, 256))
                {
                    this.createBuilding(25, 256, 256);
                }
                break;
            case 28:
                if (this.x(128, 128))
                {
                    this.createBuilding(31, 128, 128);
                }
                break;
            case 29:
                if (this.x(256, 128))
                {
                    this.createBuilding(29, 256, 128);
                }
                break;
            case 30:
                if (this.x(128, 192))
                {
                    this.createBuilding(30, 128, 192);
                }
                break;
            case 31:
                if (this.x(128, 128))
                {
                    this.createBuilding(32, 128, 128);
                }
                break;
            case 32:
                if (this.x(64, 128))
                {
                    this.createBuilding(33, 64, 128);
                }
                break;
            case 33:
                if (this.x(64, 0))
                {
                    this.createBuilding(26, 64, 0);
                }
                break;
            case 34:
                if (this.x(320, 192))
                {
                    this.createBuilding(34, 320, 192);
                }
                break;
            case 35:
                if (this.x(320, 192))
                {
                    this.createBuilding(35, 320, 192);
                }
                break;
            case 36:
                if (this.x(128, 192))
                {
                    this.createBuilding(36, 128, 192);
                }
                break;
            case 37:
                if (this.x(128, 192))
                {
                    this.createBuilding(37, 128, 192);
                }
                break;
            case 38:
                if (this.x(320, 256))
                {
                    this.createBuilding(38, 320, 256);
                }
                break;
            case 39:
                if (this.x(256, 256))
                {
                    this.createBuilding(39, 256, 256);
                }
                break;
            case 40:
                if (this.x(256, 256))
                {
                    this.createBuilding(40, 256, 256);
                }
                break;
            case 41:
                if (this.x(320, 320))
                {
                    this.createBuilding(28, 320, 320);
                }
                break;
            case 42:
                if (this.x(320, 256))
                {
                    this.createBuilding(41, 320, 256);
                    this.createBuilding(43, 0, 0);
                    this.createBuilding(44, 0, 0);
                }
                break;
            case 43:
                if (this.x(256, 320))
                {
                    this.createBuilding(42, 256, 320);
                }
                break;
            case 44:
                if (this.x(320, 320))
                {
                    this.createBuilding(27, 320, 320);
                }
                break;
        }

        this.initGui();
    }

    private void createBuilding(int id, int resource1, int resource2)
    {
        NetworkHandler.INSTANCE.sendToServer(new SPacketBuild((byte) id, false));

        ResourcesHandler.INSTANCE.decreasecobbleResource(resource1);
        ResourcesHandler.INSTANCE.decreasewoodResource(resource2);
    }

    private boolean x(int i, int j)
    {
        return ResourcesHandler.INSTANCE.getcobbleResource() >= i && ResourcesHandler.INSTANCE.getwoodResource() >= j;
    }

    private void addButton(int id, int posX, int posZ, String name)
    {
        this.buttonList.add(new GuiButton(id, posX, posZ, 120, 20, name));
    }

    private int getInventorySlotContainItem(Item item, ItemStack[] inv)
    {
        return IntStream.range(0, inv.length).filter(i -> inv[i] != null && inv[i].getItem() == item).findFirst().orElse(-1);
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(UltimateHelper.BACKGROUND);

        this.drawTexturedModalRect((this.width - 255) / 2, 0, 0, 0, 255, 255);

        super.drawScreen(i, j, f);

        if (!Buildings.INSTANCE.isTier2)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.build.stage_1", getPlayerProvider().getGoldTotal()), (this.width / 2) - fontRendererObj.getStringWidth(I18n.format("gui.build.stage_1", getPlayerProvider().getGoldTotal())) / 2, 15, 16763904);
        }

        if (Buildings.INSTANCE.isTier2 && !Buildings.INSTANCE.isTier3)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.build.stage_2", getPlayerProvider().getGoldTotal()), (this.width / 2) - fontRendererObj.getStringWidth(I18n.format("gui.build.stage_2", getPlayerProvider().getGoldTotal())) / 2, 15, 16763904);
        }

        if (Buildings.INSTANCE.isTier3)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.build.stage_3", getPlayerProvider().getGoldTotal()), (this.width / 2) - fontRendererObj.getStringWidth(I18n.format("gui.build.stage_3", getPlayerProvider().getGoldTotal())) / 2, 15, 16763904);
        }

        if (this.woodBar != null)
            this.woodBar.drawBar();

        if (this.cobblestoneBar != null)
            this.cobblestoneBar.drawBar();

        this.drawString(this.fontRendererObj, "0       160     320", this.width / 2 - 100, 30, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("gui.build.resources.logs"), this.width / 2, 40, 16763904);
        this.drawString(this.fontRendererObj, I18n.format("gui.build.resources.cobble"), this.width / 2, 60, 16763904);
    }
}
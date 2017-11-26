package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.server.container.ContainerSell;
import aginsun.kingdoms.server.entities.TileEntitySell;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public final class GuiSell extends GuiContainer
{
    public GuiSell(InventoryPlayer player_inventory, TileEntitySell sell)
    {
        super(new ContainerSell(sell, player_inventory));
    }

    @Override
    public void drawScreen(int w, int h, float partial)
    {
        this.fontRendererObj.drawString("Total Money: ", 25, 40, 4210752);
        this.fontRendererObj.drawString(EconomyHandler.INSTANCE.getGoldTotal() + " Gold Coins", 30, 50, 4210752);
        this.fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
        this.fontRendererObj.drawString("Sell Menu", 25, 20, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(UltimateHelper.BACKGROUND);
        this.drawTexturedModalRect((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void keyTyped(char character, int code)
    {
        if (character == 1 || code == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
    }
}
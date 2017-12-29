package kingdoms.client.gui;

import kingdoms.api.gui.GuiPriceBar;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public final class GuiReinforcementPool extends GuiScreenToK
{
    private float soldierNumber;
    private GuiPriceBar knightPool;

    public GuiReinforcementPool(EntityPlayer player, World world)
    {
        super(player, world);
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.knightPool = new GuiPriceBar(0, this.width / 2 - 100, 40, 90, 12, 1.0F, "red");
        this.knightPool.setBar(this.soldierNumber / 80.0F);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
      if (guibutton.id == 0) {
         ;
      }

      if (guibutton.id == 1) {
         ;
      }

      if (guibutton.id == 2) {
         this.mc.displayGuiScreen(null);
      }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(UltimateHelper.BACKGROUND);
        this.drawTexturedModalRect((this.width - 255) / 2, 0, 0, 0, 255, 255);
        super.drawScreen(i, j, f);

        this.drawString(this.fontRendererObj, "Reinforcement Pool", this.width / 2, 15, 16777215);
        this.knightPool.drawBar();
        super.drawScreen(i, j, f);
    }
}
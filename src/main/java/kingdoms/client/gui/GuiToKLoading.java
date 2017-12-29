package kingdoms.client.gui;

import kingdoms.api.gui.GuiPriceBar;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.schematic.SchematicHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class GuiToKLoading extends GuiScreenToK
{
    private GuiPriceBar bar;

    public GuiToKLoading(EntityPlayer player, World world)
    {
        super(player, world);
    }

    @Override
    public void initGui()
    {
        this.bar = new GuiPriceBar(1, this.width / 2 - 100, this.height / 2 - 10, 200, 20, 1.0F, "red");
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 60, this.height / 2 + 25, 120, 20, I18n.format("gui.exit")));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 0)
            this.mc.displayGuiScreen(null);
    }

    @Override
    public void drawScreen(int x, int y, float partial)
    {
        this.drawDefaultBackground();
        float d = SchematicHandler.INSTANCE.getProgressCurrentBuilding();

        if (!SchematicHandler.INSTANCE.getBuildingList().isEmpty())
        {
            this.bar.setBar(d / 100.0F);
            this.bar.drawBar();
            this.drawString(this.fontRendererObj, "Building the Guild...", this.width / 2 - fontRendererObj.getStringWidth("Building the Guild...") / 2, this.height / 2 + 15, Color.pink.getRGB());
        }
        else
            this.drawString(this.fontRendererObj, "Press exit to continue...", this.width / 2 - fontRendererObj.getStringWidth("Press exit to continue...") / 2, this.height / 2 + 15, Color.pink.getRGB());

        super.drawScreen(x, y, partial);
    }
}
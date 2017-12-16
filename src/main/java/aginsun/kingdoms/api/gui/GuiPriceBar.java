package aginsun.kingdoms.api.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public final class GuiPriceBar extends Gui
{
    public boolean border;
    public float barPosition;
    private int width, height;
    public int xPosition, borderColor, yPosition, id, colour;

    public GuiPriceBar(int i, int j, int k, int l, int i1, float f)
    {
        this.borderColor = -10592674;

        if (f >= 0.0F && f <= 1.0F)
        {
            this.barPosition = f;
        }
        else
        {
            this.barPosition = 0.0F;
        }

        this.id = i;
        this.colour = -2553077;
        this.xPosition = j;
        this.yPosition = k;
        this.width = l;
        this.height = i1;
        this.border = true;
    }

    public GuiPriceBar(int i, int j, int k, int l, int i1, float f, int j1)
    {
        this(i, j, k, l, i1, f);
        this.colour = j1;
    }

    public GuiPriceBar(int i, int j, int k, int l, int i1, float f, String s)
    {
        this(i, j, k, l, i1, f);

        if (s.equalsIgnoreCase("red"))
        {
            this.colour = -2553077;
        }
        else if (s.equalsIgnoreCase("green"))
        {
            this.colour = -16298223;
        }
        else if (s.equalsIgnoreCase("blue"))
        {
            this.colour = -15000608;
        }
        else
        {
            this.colour = -1;
        }
    }

    public void setBar(float percent)
    {
        if (percent >= 0.0F && percent <= 1.0F)
        {
            this.barPosition = percent;
        }
    }

    public void drawBar()
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (this.border)
        {
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.borderColor);
        }

        drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + 1 + this.width - 2, this.yPosition + 1 + this.height - 2, -16777216);
        drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + 1 + (int)(this.barPosition * (float)(this.width - 2)), this.yPosition + 1 + this.height - 2, this.colour);
    }
}
package kingdoms.api.gui;

import kingdoms.client.gui.GuiShopList;
import kingdoms.client.gui.GuiStockList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class GuiButtonShop extends GuiButton
{
    private GuiShopList gui;
    private GuiStockList gui2;
    private ItemStack stack;
    private int x, y;
    private String text;
    public int id;
    private boolean field_73742_g, enabled2;
    private final ResourceLocation resource = new ResourceLocation("taleofkingdoms", "textures/gui/gui.png");

    public GuiButtonShop(ItemStack stack, GuiShopList guishoplist, int id, int x, int y, int w, int h, String text)
    {
        super(id, x, y, 200, 20, text);
        this.gui = guishoplist;
        this.stack = stack;
        this.field_73742_g = true;
        this.enabled2 = true;
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.text = text;
    }

    public GuiButtonShop(ItemStack stack, GuiStockList guistocklist, int id, int x, int y, int w, int h, String text)
    {
        super(id, x, y, 200, 20, text);
        this.gui2 = guistocklist;
        this.stack = stack;
        this.field_73742_g = true;
        this.enabled2 = true;
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.text = text;
    }

    @Override
    public int getHoverState(boolean flag)
    {
        byte byte0 = 1;

        if (!this.field_73742_g)
        {
            byte0 = 0;
        }
        else if (flag)
        {
            byte0 = 2;
        }
        return byte0;
    }

    @Override
    public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (this.enabled2)
        {
            final FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.renderEngine.bindTexture(resource);
            boolean flag = i >= this.x && j >= this.y && i < this.x + this.width && j < this.y + this.height;
            int k = this.getHoverState(flag);

            if (this.gui != null)
            {
                k = this.gui.stackSelected == stack ? 2 : 1;
            }

            if (this.gui2 != null)
            {
                if (this.gui2.stackSelected == stack)
                {
                    k = 2;
                }
                else
                {
                    k = 1;
                }
            }

            this.drawTexturedModalRect(this.x + this.width / 2 + 55, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(minecraft, i, j);

            if (!flag)
            {
                this.drawString(fontrenderer, this.text, this.x + this.width / 2 - 20, this.y + (this.height - 8) / 2, 16777215);
            }
            else
            {
                this.drawString(fontrenderer, this.text, this.x + this.width / 2 - 20, this.y + (this.height - 8) / 2, '\ucc00');
            }
        }
    }
}
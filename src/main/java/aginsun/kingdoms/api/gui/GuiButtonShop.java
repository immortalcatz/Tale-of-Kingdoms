package aginsun.kingdoms.api.gui;

import aginsun.kingdoms.client.gui.GuiShopList;
import aginsun.kingdoms.client.gui.GuiStockList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public final class GuiButtonShop extends GuiButton
{
    private GuiShopList gui;
    private GuiStockList gui2;
    private Item item;
    private int field_73747_a, field_73745_b, x, y;
    private String text;
    public int id;
    private boolean field_73742_g, enabled2;
    private final ResourceLocation resource = new ResourceLocation("taleofkingdoms", "textures/gui/gui.png");

    public GuiButtonShop(Item item, GuiShopList guishoplist, int id, int x, int y, int l, int i1, String text)
    {
        super(id, x, y, 200, 20, text);
        this.gui = guishoplist;
        this.item = item;
        this.field_73747_a = 200;
        this.field_73745_b = 20;
        this.field_73742_g = true;
        this.enabled2 = true;
        this.id = id;
        this.x = x;
        this.y = y;
        this.field_73747_a = l;
        this.field_73745_b = i1;
        this.text = text;
    }

    public GuiButtonShop(Item item1, GuiStockList guistocklist, int id, int x, int y, int l, int i1, String text)
    {
        super(id, x, y, 200, 20, text);
        this.gui2 = guistocklist;
        this.item = item1;
        this.field_73747_a = 200;
        this.field_73745_b = 20;
        this.field_73742_g = true;
        this.enabled2 = true;
        this.id = id;
        this.x = x;
        this.y = y;
        this.field_73747_a = l;
        this.field_73745_b = i1;
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
            boolean flag = i >= this.x && j >= this.y && i < this.x + this.field_73747_a && j < this.y + this.field_73745_b;
            int k = this.getHoverState(flag);

            if (this.gui != null)
            {
                if(this.gui.itemSelected == item)
                {
                    k = 2;
                }
                else
                {
                    k = 1;
                }
            }

            if (this.gui2 != null)
            {
                if (this.gui2.itemSelected == this.item)
                {
                    k = 2;
                }
                else
                {
                    k = 1;
                }
            }

            this.drawTexturedModalRect(this.x + this.field_73747_a / 2, this.y, 200 - this.field_73747_a / 2, 46 + k * 20, this.field_73747_a / 2, this.field_73745_b);
            this.mouseDragged(minecraft, i, j);

            if (!flag)
            {
                this.drawString(fontrenderer, this.text, this.x + this.field_73747_a / 2 - 20, this.y + (this.field_73745_b - 8) / 2, 16777215);
            }
            else
            {
                this.drawString(fontrenderer, this.text, this.x + this.field_73747_a / 2 - 20, this.y + (this.field_73745_b - 8) / 2, '\ucc00');
            }
        }
    }
}
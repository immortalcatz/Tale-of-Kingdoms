package kingdoms.client.gui;

import kingdoms.api.gui.GuiButtonShop;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.server.SPacketBuy;
import kingdoms.server.handlers.packets.server.SPacketOpenGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.IntStream;

public class GuiShopList extends GuiScreenToK
{
    private GuiButton back;
    private int currentGui = 0, price, shopcounter = 20;
    private List<ItemStack> stacks;
    private boolean reachedend = false, goldchecker = false;
    public ItemStack stackSelected;
    private String stringoutput = "";

    public GuiShopList(EntityPlayer player, World world, List<ItemStack> stacks)
    {
        super(player, world);
        this.stacks = stacks;
        this.stackSelected = stacks.get(0);
        this.price = stacks.get(0).getTagCompound().getInteger("price");
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        back.enabled = currentGui != 0;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();

        for (int row = 0; row < 8; row++)
        {
            for (int column = 0; column < 2; column++)
            {
                int size = column + row * 2;

                if (size < stacks.size())
                    this.buttonList.add(new GuiButtonShop(stacks.get(size), this, size, (width / 2 - 110) + column * 100, 45 + row * 20, 50, 20, stacks.get(size).getDisplayName()));
            }
        }

        this.buttonList.add(back = new GuiButton(17, this.width / 2 - 120, 200, 70, 20, I18n.format("gui.shop.back")));
        this.buttonList.add(new GuiButton(16, this.width / 2 + 50, 200, 70, 20, I18n.format("gui.shop.next")));
        this.buttonList.add(new GuiButton(18, this.width / 2 - 35, 200, 70, 20, I18n.format("gui.shop.buy")));
        this.buttonList.add(new GuiButton(19, this.width / 2 - 35, 220, 70, 20, I18n.format("gui.exit")));
        GuiButton btn = new GuiButton(20, this.width / 2 - 120, 220, 70, 20, I18n.format("gui.shop.sell"));
        btn.enabled = false;
        this.buttonList.add(btn);
        this.buttonList.add(new GuiButton(21, this.width / 2 + 50, 220, 70, 20, I18n.format("gui.shop.buy.sixteen")));
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void onGuiClosed()
    {
        if (!this.world.isRemote)
            this.player.addChatMessage(new ChatComponentTranslation("gui.shop.bye"));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        IntStream.range(0, stacks.size()).filter(i -> guibutton.id == i).forEach(i -> {
            stackSelected = stacks.get(i);
            this.goldchecker = false;
            if (stackSelected != null)
                this.price = stacks.get(i).getTagCompound().getInteger("price");
        });

        if (guibutton.id == 16)
        {
            if (!this.reachedend)
                ++this.currentGui;

            this.goldchecker = false;

            if (stackSelected != null)
                this.price = stackSelected.getTagCompound().getInteger("price");
        }

        if(guibutton.id == 17)
        {
            if (this.currentGui != 0)
                --this.currentGui;
            
            this.goldchecker = false;

            if (stackSelected != null)
                this.price = stackSelected.getTagCompound().getInteger("price");
        }

        if (guibutton.id == 18)
        {
            int price = stackSelected.getTagCompound().getInteger("price");

            if (price <= playerProvider.getGoldTotal())
            {
                NetworkHandler.INSTANCE.sendToServer(new SPacketBuy(stackSelected, (byte) 1, price));
            }
            else {
                this.goldchecker = true;
            }
        }

        if (guibutton.id == 21 && this.shopcounter >= 16)
        {
            int price = stackSelected.getTagCompound().getInteger("price");

            if (price <= playerProvider.getGoldTotal())
            {
                NetworkHandler.INSTANCE.sendToServer(new SPacketBuy(stackSelected, (byte) 16, price));
                this.shopcounter = 0;
            }
            else {
                this.goldchecker = true;
            }
        }

        if (guibutton.id == 19)
        {
            this.mc.displayGuiScreen(null);
            this.goldchecker = false;
        }

        if (guibutton.id == 20)
        {
            this.mc.displayGuiScreen(null);
            NetworkHandler.INSTANCE.sendToServer(new SPacketOpenGui(1));
            this.goldchecker = false;
        }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        if (this.shopcounter < 16)
        {
            int price = stackSelected.getTagCompound().getInteger("price");

            if (price <= playerProvider.getGoldTotal())
                NetworkHandler.INSTANCE.sendToServer(new SPacketBuy(stackSelected, (byte) 1, price));

            ++this.shopcounter;
        }

        this.drawDefaultBackground();
        this.mc.renderEngine.bindTexture(new ResourceLocation("taleofkingdoms", "textures/gui/crafting.png"));
        this.drawTexturedModalRect((this.width - 255) / 2, 0, 0, 0, 255, 255);

        this.drawString(this.fontRendererObj, I18n.format("gui.shop.title", playerProvider.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.shop.title", playerProvider.getGoldTotal())) / 2, 5, 16763904);
        this.drawString(this.fontRendererObj, "Selected Item Cost: " + this.stringoutput + " - " + this.price + " Gold coins", this.width / 2 - fontRendererObj.getStringWidth("Selected Item Cost: " + this.stringoutput + " - " + this.price + " Gold coins") / 2, 20, 16763904);

        if (this.goldchecker)
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, 33, 16763904);

        super.drawScreen(i, j, f);
    }
}
package kingdoms.client.gui;

import kingdoms.api.gui.GuiPriceBar;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.resources.ResourcesHandler;
import kingdoms.server.handlers.resources.WorkersHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class GuiLumber extends GuiScreenToK
{
    private GuiPriceBar bar;
    private boolean goldchecker = false;

    public GuiLumber(EntityPlayer player, World world)
    {
        super(player, world);
    }

    @Override
    public void initGui()
    {
        this.bar = new GuiPriceBar(0, this.width / 2 - 45, 40, 90, 12, 1.0F, "red");
        this.bar.setBar((float) ResourcesHandler.INSTANCE.getWoodPool() / 320.0F);
        this.buttonList.add(new GuiButton(1, this.width / 2 - 45, 77, 90, 20, I18n.format("gui.foreman.takeStack")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 45, 100, 90, 20, I18n.format("gui.foreman.buyWorker")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 45, 123, 90, 20, I18n.format("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (ResourcesHandler.INSTANCE.getWoodPool() >= 64)
                {
                    this.world.spawnEntityInWorld(new EntityItem(this.world, this.player.posX, this.player.posY, this.player.posZ, new ItemStack(Item.getItemFromBlock(Blocks.log), 64)));
                    this.goldchecker = false;
                    ResourcesHandler.INSTANCE.decreaseWoodPool(64);
                }
                else
                {
                    this.player.addChatMessage(new ChatComponentTranslation("gui.foreman.notResources"));
                }
                break;
            case 2:
                if (playerProvider.getGoldTotal() >= 1500)
                {
                    if (WorkersHandler.INSTANCE.getLumberMembers() < 12)
                    {
                        WorkersHandler.INSTANCE.addLumberMember((byte) 1);
                        playerProvider.decreaseGold(1500);
                        this.player.addChatMessage(new ChatComponentTranslation("gui.foreman.boughtWorker"));
                    }
                    else
                    {
                        this.player.addChatMessage(new ChatComponentTranslation("gui.foreman.limitWorkers"));
                    }
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 3:
                this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void drawScreen(int x, int y, float partial)
    {
        super.drawScreen(x, y, partial);

        this.drawString(this.fontRendererObj, I18n.format("gui.foreman.title", playerProvider.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.foreman.title", playerProvider.getGoldTotal())) / 2, 15, 16777215);

        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, 27, 16777215);
        }

        this.bar.drawBar();
        this.drawString(this.fontRendererObj, I18n.format("gui.foreman.bar.label"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.foreman.bar.label")) / 2, 42, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("gui.foreman.note.workerPrice"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.foreman.note.workerPrice")) / 2, 60, 16777215);
    }
}
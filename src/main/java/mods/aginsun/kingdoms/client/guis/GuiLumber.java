package mods.aginsun.kingdoms.client.guis;

import mods.aginsun.kingdoms.handlers.GoldKeeper;
import mods.aginsun.kingdoms.handlers.ResourceHandler;
import mods.aginsun.kingdoms.handlers.WorkerHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class GuiLumber extends GuiScreenToK
{
    private World worldObj;
    public GoldKeeper gold;
    private GuiPriceBar bar;
    public EntityPlayer player;
    boolean goldchecker = false;

    public GuiLumber(EntityPlayer player, World world)
    {
        this.player = player;
        this.worldObj = world;
    }

    @Override
    public void initGui()
    {
        this.bar = new GuiPriceBar(0, this.width / 2 - 45, 40, 90, 12, 1.0F, "red");
        this.bar.setBar((float)ResourceHandler.getInstance().getWoodPool() / 320.0F);
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
                if (ResourceHandler.getInstance().getWoodPool() >= 64)
                {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.player.posX, this.player.posY, this.player.posZ, new ItemStack(Item.getItemFromBlock(Blocks.log), 64)));
                    this.goldchecker = false;
                    ResourceHandler.getInstance().decreaseWoodPool(64);
                }
                else
                {
                    this.player.addChatMessage(new ChatComponentText(I18n.format("gui.foreman.notResources")));
                }
                break;
            case 2:
                if(GoldKeeper.getGoldTotal() >= 1500)
                {
                    if(WorkerHandler.getInstance().getLumberMembers() < 12)
                    {
                        WorkerHandler.getInstance().addLumberMember();
                        GoldKeeper.decreaseGold(1500);
                        this.player.addChatMessage(new ChatComponentText(I18n.format("gui.foreman.boughtWorker")));
                    }
                    else
                    {
                        this.player.addChatMessage(new ChatComponentText(I18n.format("gui.foreman.limitWorkers")));
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
    public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);

        this.drawString(this.fontRendererObj, I18n.format("gui.foreman.title", GoldKeeper.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.foreman.title", GoldKeeper.getGoldTotal())) / 2, 15, 16777215);

        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, 27, 16777215);
        }

        this.bar.drawBar();
        this.drawString(this.fontRendererObj, I18n.format("gui.foreman.bar.label"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.foreman.bar.label")) / 2, 42, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("gui.foreman.note.workerPrice"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.foreman.note.workerPrice")) / 2, 60, 16777215);
    }
}
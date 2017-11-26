package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.api.gui.GuiScreenToK;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public final class GuiStableMaster extends GuiScreenToK
{
    private EntityPlayer player;
    private boolean goldchecker = false;

    public GuiStableMaster(EntityPlayer player, World world)
    {
        super(world);
        this.player = player;
        this.world = world;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 25, 120, 20, I18n.format("gui.stable.starter")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 60, this.height / 2, 120, 20, I18n.format("gui.stable.expert")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 60, this.height / 2 + 25, 120, 20, I18n.format("gui.exit")));
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (EconomyHandler.INSTANCE.getGoldTotal() >= 1500)
                {
                    this.player.dropItem(Items.saddle, 1);
                    this.player.dropItem(Items.lead, 1);
                    this.player.dropItem(Items.wheat, 15);
                    this.player.dropItem(Items.name_tag, 1);
                    EconomyHandler.INSTANCE.decreaseGold(1500);
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 2:
                if (EconomyHandler.INSTANCE.getGoldTotal() >= 7500)
                {
                    this.player.dropItem(Items.diamond_horse_armor, 1);
                    this.player.dropItem(Items.wheat, 64);
                    EntityHorse horse = new EntityHorse(world);
                    world.spawnEntityInWorld(horse);
                    EconomyHandler.INSTANCE.decreaseGold(7500);
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 3:
                this.mc.displayGuiScreen(null);
                this.goldchecker = false;
                break;
        }
    }

    @Override
    public void drawScreen(int x, int y, float partial)
    {
        this.drawDefaultBackground();
        super.drawScreen(x, y, partial);
        this.drawString(this.fontRendererObj, I18n.format("gui.stable.title", EconomyHandler.INSTANCE.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.title", EconomyHandler.INSTANCE.getGoldTotal())) / 2, this.height / 2 - 90, 16763904);
        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, this.height / 2 - 45, 16763904);
        }
        this.drawString(this.fontRendererObj, I18n.format("gui.stable.starter.desc"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.stable.starter.desc")) / 2, this.height / 2 - 75, 16763904);
        this.drawString(this.fontRendererObj, I18n.format("gui.stable.expert.desc"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.stable.expert.desc")) / 2, this.height / 2 - 60, 16763904);
    }
}
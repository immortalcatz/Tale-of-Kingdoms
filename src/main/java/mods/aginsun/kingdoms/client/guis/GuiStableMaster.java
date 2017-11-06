package mods.aginsun.kingdoms.client.guis;

import cpw.mods.fml.common.FMLCommonHandler;
import mods.aginsun.kingdoms.handlers.GoldKeeper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public final class GuiStableMaster extends GuiScreenToK
{
    public EntityPlayer player;
    private boolean goldchecker = false;
    private World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

    public GuiStableMaster(EntityPlayer player, World world)
    {
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
                if(GoldKeeper.getGoldTotal() >= 1500)
                {
                    this.player.dropItem(Items.saddle, 1);
                    this.player.dropItem(Items.lead, 1);
                    this.player.dropItem(Items.wheat, 15);
                    this.player.dropItem(Items.name_tag, 1);
                    GoldKeeper.decreaseGold(1500);
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 2:
                if(GoldKeeper.getGoldTotal() >= 7500)
                {
                    this.player.dropItem(Items.diamond_horse_armor, 1);
                    this.player.dropItem(Items.wheat, 64);
                    EntityHorse horse = new EntityHorse(world);
                    world.spawnEntityInWorld(horse);
                    GoldKeeper.decreaseGold(7500);
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
    public void drawScreen(int i, int j, float f)
    {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);
        this.drawString(this.fontRendererObj, I18n.format("gui.stable.title", GoldKeeper.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.title", GoldKeeper.getGoldTotal())) / 2, this.height / 2 - 90, 16763904);
        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, this.height / 2 - 45, 16763904);
        }
        this.drawString(this.fontRendererObj, I18n.format("gui.stable.starter.desc"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.stable.starter.desc")) / 2, this.height / 2 - 75, 16763904);
        this.drawString(this.fontRendererObj, I18n.format("gui.stable.expert.desc"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.stable.expert.desc")) / 2, this.height / 2 - 60, 16763904);
    }
}
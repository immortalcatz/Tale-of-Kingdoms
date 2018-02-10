package kingdoms.client.gui;

import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GuiPriest extends GuiScreenToK
{
    private boolean goldchecker = false;

    public GuiPriest(EntityPlayer player, World world)
    {
        super(player, world);
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 + 100, 180, 110, 20, "Recruit a Priestess"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 100, 200, 110, 20, "Rejuvinate"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 100, 220, 110, 20, "Exit"));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (2000 <= getPlayerProvider().getGoldTotal())
                {
                    EntityLiving entityliving = (EntityLiving) UltimateHelper.INSTANCE.getEntity("DefendPriest", getWorld());
                    entityliving.setLocationAndAngles(getPlayer().posX, getPlayer().posY, getPlayer().posZ, 0.0F, 0.0F);
                    this.getWorld().spawnEntityInWorld(entityliving);
                    getPlayerProvider().decreaseGold(2000);
                }
                else
                    this.goldchecker = true;
                break;
            case 2:
                if (!this.getWorld().isRemote)
                {
                    getPlayer().getFoodStats().setFoodLevel(20);
                    getPlayer().heal(20.0F);
                    getPlayer().addChatMessage(new ChatComponentText("Head Priest: You are now rejuvinated."));
                }
                break;
            case 3:
                this.mc.displayGuiScreen(null);
                this.goldchecker = false;
                break;
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (!this.getWorld().isRemote)
            getPlayer().addChatMessage(new ChatComponentText("Head Priest: May the light be with you."));
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);

        if (this.goldchecker)
            this.drawString(this.fontRendererObj, "The Chapel Total Money: " + getPlayerProvider().getGoldTotal() + " Gold Coins - NOT ENOUGH GOLD", this.width / 2, 20, 16763904);
        else
            this.drawString(this.fontRendererObj, "The Chapel Total Money: " + getPlayerProvider().getGoldTotal() + " Gold Coins", this.width / 2, 10, 16763904);

        this.drawString(this.fontRendererObj, "Note: Recruiting a priest cost 2000", this.width / 2, 20, 16763904);
    }
}
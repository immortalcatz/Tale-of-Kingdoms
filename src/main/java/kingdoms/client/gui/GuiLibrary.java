package kingdoms.client.gui;

import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.entities.EntityLibraryKeeper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GuiLibrary extends GuiScreenToK
{
    private EntityLibraryKeeper get;
    private boolean goldchecker = false;

    public GuiLibrary(EntityPlayer player, World world, EntityLibraryKeeper entitylibrarykeeper)
    {
        super(player, world);
        this.get = entitylibrarykeeper;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 70, 80, 140, 20, "Study in the library"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 70, 100, 140, 20, "Invest for the library"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 70, 120, 140, 20, "Research on Excalibur"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 70, 140, 140, 20, "Exit"));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 1)
        {
            if (this.get.studied)
            {
                if (!this.getWorld().isRemote)
                {
                    this.getWorld().spawnEntityInWorld(new EntityXPOrb(this.getWorld(), getPlayer().posX, getPlayer().posY, getPlayer().posZ, 150));
                    getPlayer().addChatMessage(new ChatComponentText("You have gained experience."));
                }

                this.get.studied = false;
            }
            else if (!this.getWorld().isRemote)
            {
                getPlayer().addChatMessage(new ChatComponentText("You have already studied for a while, go back in a few moments"));
            }
            this.goldchecker = false;
        }

        if (guibutton.id == 2)
        {
            if (500 + getPlayerProvider().libraryInvestment * 2 <= getPlayerProvider().getGoldTotal())
            {
                getPlayerProvider().libraryInvestment += 5;

                if (!this.getWorld().isRemote)
                {
                    getPlayer().addChatMessage(new ChatComponentText("Tax is now increased by " + getPlayerProvider().libraryInvestment + " gold per house."));
                }

                getPlayerProvider().decreaseGold(500 + getPlayerProvider().libraryInvestment * 2);
            }
            else
            {
                this.goldchecker = true;
            }
        }

        if (guibutton.id == 3)
        {
            this.mc.displayGuiScreen(null);
            this.goldchecker = false;
        }

        if (guibutton.id == 4)
        {
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (!this.getWorld().isRemote)
        {
            getPlayer().addChatMessage(new ChatComponentText("Librarian: I will see you again hero."));
        }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        super.drawScreen(i, j, f);

        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, "The Library Total Money: " + getPlayerProvider().getGoldTotal() + " Gold Coins - NOT ENOUGH GOLD", this.width / 2, 20, 16777215);
        }
        else
        {
            this.drawString(this.fontRendererObj, "The Library  Total Money: " + getPlayerProvider().getGoldTotal() + " Gold Coins", this.width / 2, 20, 16777215);
        }

        this.drawString(this.fontRendererObj, "Note: The more you invest, the more knowledge people gain to yield more tax.", this.width / 2, 170, 16777215);
    }
}
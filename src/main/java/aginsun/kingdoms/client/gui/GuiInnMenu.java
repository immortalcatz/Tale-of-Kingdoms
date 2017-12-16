package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.api.gui.GuiScreenToK;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GuiInnMenu extends GuiScreenToK
{
    private EntityPlayer player;
    private boolean screenpause = false, isResting = false;

    public GuiInnMenu(EntityPlayer player, World world)
    {
        super(world);
        this.player = player;
    }

    @Override
    public void initGui()
    {
        if (!this.isResting)
        {
            this.buttonList.clear();
            this.buttonList.add(new GuiButton(1, this.width / 2 + 110, 180, 100, 20, "Rest in a room."));
            this.buttonList.add(new GuiButton(2, this.width / 2 + 110, 200, 100, 20, "Wait for night time."));
            this.buttonList.add(new GuiButton(3, this.width / 2 + 110, 220, 100, 20, I18n.format("gui.exit")));
        }
        else
        {
            this.buttonList.clear();
            this.buttonList.add(new GuiButton(1, this.width / 2 + 110, 220, 70, 20, "Wake Up."));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (!this.isResting)
                {
                    this.screenpause = true;
                    this.isResting = true;
                    this.player.heal(20.0F);
                    long l = this.world.getWorldInfo().getWorldTime() + 24000L;
                    this.world.getWorldInfo().setWorldTime(l - l % 24000L);
                    this.initGui();
                }
                else
                {
                    this.screenpause = false;
                    this.isResting = false;
                    this.initGui();
                }
                break;
            case 2:
                if (!this.isResting)
                {
                    this.screenpause = true;
                    this.isResting = true;
                    this.player.heal(20.0F);
                    this.world.getWorldInfo().setWorldTime(14000L);
                    this.initGui();
                }
                else
                {
                    this.screenpause = false;
                    this.isResting = false;
                    this.initGui();
                }
                break;
            case 3:
                this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return this.screenpause;
    }

    @Override
    public void onGuiClosed()
    {
        if (!this.world.isRemote)
            this.player.addChatMessage(new ChatComponentText("House Keeper: Have a nice day."));
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        if (!this.isResting)
        {
            this.drawString(this.fontRendererObj, "Time flies when you rest..", this.width / 2, 10, 16772608);
            this.drawString(this.fontRendererObj, "Note: You could rest even in daylight but you will wake up the next day", this.width / 2, 20, 16772608);
        }
        else
        {
            this.drawDefaultBackground();
            this.drawString(this.fontRendererObj, "Resting...", this.width / 2, this.height / 2 - 20, 16772608);
        }
        super.drawScreen(i, j, f);
    }
}
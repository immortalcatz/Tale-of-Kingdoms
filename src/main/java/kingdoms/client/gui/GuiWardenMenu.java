package kingdoms.client.gui;

import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.server.SPacketWarden;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GuiWardenMenu extends GuiScreenToK
{
    private boolean goldchecker;

    public GuiWardenMenu(EntityPlayer player, World world)
    {
        super(player, world);
        this.goldchecker = false;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 + 110, 160, 100, 20, "Recruit a Knight."));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 110, 180, 100, 20, "Recruit an Archer."));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 110, 200, 100, 20, I18n.format("gui.exit")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (1000 <= playerProvider.getGoldTotal())
                {
                    NetworkHandler.INSTANCE.sendToServer(new SPacketWarden("DefendWarrior"));
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 2:
                if(1000 <= playerProvider.getGoldTotal())
                {
                    NetworkHandler.INSTANCE.sendToServer(new SPacketWarden("DefendArcher"));
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
   public void onGuiClosed() {
      this.player.addChatMessage(new ChatComponentText("Warden: Good Day."));
   }

   @Override
   public void drawScreen(int x, int y, float partial)
   {
       if (this.goldchecker)
           this.drawCenteredString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2, 45, 16777215);

       super.drawScreen(x, y, partial);

       this.drawString(this.fontRendererObj, "Note: The knights and archers will upgrade if they damage enough monsters!", this.width / 2 - fontRendererObj.getStringWidth("Note: The knights and archers will upgrade if they damage enough monsters!") / 2, 20, 16763904);
       this.drawString(this.fontRendererObj, "1000 gold per hire.", this.width / 2 - fontRendererObj.getStringWidth("1000 gold per hire.") / 2, 33, 16763904);
   }
}
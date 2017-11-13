package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.api.gui.GuiScreenToK;
import aginsun.kingdoms.server.handlers.UtilToK;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public final class GuiWardenMenu extends GuiScreenToK
{
    private EntityPlayer player;
    private boolean goldchecker;

    public GuiWardenMenu(EntityPlayer player, World world)
    {
        super(world);
        this.player = player;
        this.goldchecker = false;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 + 110, 160, 100, 20, "Recruit a Knight."));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 110, 180, 100, 20, "Recruit an Archer."));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 110, 200, 100, 20, "Recall defenders"));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 110, 220, 100, 20, "Exit"));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
      if(guibutton.id == 1) {
         if(1000 <= EconomyHandler.INSTANCE.getGoldTotal()) {
            UtilToK.INSTANCE.spawnEntity(this.world, "DefendWarrior", new ChunkCoordinates((int)this.player.posX, (int)this.player.posY, (int)this.player.posZ));
            EconomyHandler.INSTANCE.decreaseGold(1000);
         } else {
            this.goldchecker = true;
         }
      }

      if(guibutton.id == 2) {
         if(1000 <= EconomyHandler.INSTANCE.getGoldTotal()) {
            UtilToK.INSTANCE.spawnEntity(this.world, "DefendArcher", new ChunkCoordinates((int)this.player.posX, (int)this.player.posY, (int)this.player.posZ));
            EconomyHandler.INSTANCE.decreaseGold(1000);
         } else {
            this.goldchecker = true;
         }
      }

      if(guibutton.id == 3) {
         this.mc.displayGuiScreen(null);
         this.goldchecker = false;
      }

   }

   public void onGuiClosed() {
      this.player.addChatMessage(new ChatComponentText("Warden: Good Day."));
   }

   public void drawScreen(int i, int j, float f) {
      super.drawScreen(i, j, f);

      if(this.goldchecker) {
         this.drawString(this.fontRendererObj, "Barracks Total Money: " + EconomyHandler.INSTANCE.getGoldTotal() + " Gold Coins - NOT ENOUGH GOLD", this.width / 2, 20, 16763904);
      } else {
         this.drawString(this.fontRendererObj, "Barracks  Total Money: " + EconomyHandler.INSTANCE.getGoldTotal() + " Gold Coins", this.width / 2, 10, 16763904);
      }

      this.drawString(this.fontRendererObj, "Note: The knights and archers will upgrade if they damage enough monsters!", this.width / 2, 20, 16763904);
      this.drawString(this.fontRendererObj, " 1000 gold per hire.", this.width / 2, 30, 16763904);
   }
}

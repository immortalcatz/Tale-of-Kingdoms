package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.api.gui.GuiScreenToK;
import aginsun.kingdoms.server.handlers.NetworkHandler;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import aginsun.kingdoms.server.handlers.packets.server.SPacketSpawnEntity;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class GuiWardenMenu extends GuiScreenToK
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
        this.buttonList.add(new GuiButton(3, this.width / 2 + 110, 220, 100, 20, I18n.format("gui.exit")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (1000 <= EconomyHandler.INSTANCE.getGoldTotal())
                {
                    NetworkHandler.INSTANCE.sendToServer(new SPacketSpawnEntity("DefendWarrior"));
                    EconomyHandler.INSTANCE.decreaseGold(1000);
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 2:
                if(1000 <= EconomyHandler.INSTANCE.getGoldTotal())
                {
                    if (!world.isRemote)
                    {
                        EntityLivingBase entityliving = (EntityLivingBase) UltimateHelper.INSTANCE.getEntity("DefendArcher", world);
                        entityliving.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0F, 0.0F);
                        world.spawnEntityInWorld(entityliving);
                    }
                    EconomyHandler.INSTANCE.decreaseGold(1000);
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
       super.drawScreen(x, y, partial);

       if (this.goldchecker)
           this.drawString(this.fontRendererObj, "Barracks Total Money: " + EconomyHandler.INSTANCE.getGoldTotal() + " Gold Coins - NOT ENOUGH GOLD", this.width / 2, 20, 16763904);
       else
           this.drawString(this.fontRendererObj, "Barracks  Total Money: " + EconomyHandler.INSTANCE.getGoldTotal() + " Gold Coins", this.width / 2, 10, 16763904);

       this.drawString(this.fontRendererObj, "Note: The knights and archers will upgrade if they damage enough monsters!", this.width / 2, 20, 16763904);
       this.drawString(this.fontRendererObj, " 1000 gold per hire.", this.width / 2, 30, 16763904);
   }
}
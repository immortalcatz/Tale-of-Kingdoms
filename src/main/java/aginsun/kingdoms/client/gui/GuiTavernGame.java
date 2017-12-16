package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.api.gui.GuiScreenToK;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class GuiTavernGame extends GuiScreenToK {

   private EntityPlayer entityplayer;
   private Integer dealer;
   private Integer player;
   private int fRollp = 0;
   private int sRollp = 0;
   private int fRolld = 0;
   private int sRolld = 0;
   private boolean dealerR = false;
   private boolean playerR = false;
   private boolean game = false;
   private int wintype = 0;
   private boolean gameNotEnded = true;
   private boolean rolledDouble = true;
   private boolean notEnough = false;


   public GuiTavernGame(EntityPlayer entityplayer1, World world)
   {
      super(world);
      this.entityplayer = entityplayer1;
   }

   public boolean deal(int i) {
      Random random = new Random();
      if(i == 0) {
         EconomyHandler.INSTANCE.decreaseGold(50);
         this.fRollp = random.nextInt(6) + 1;
         this.fRolld = random.nextInt(6) + 1;
         this.sRollp = random.nextInt(6) + 1;
         this.sRolld = random.nextInt(6) + 1;
         this.player = this.fRollp + this.sRollp;
         this.dealer = this.fRolld + this.sRolld;
         if(this.fRollp == this.sRollp) {
            this.wintype = 4;
            this.gameNotEnded = false;
            this.rolledDouble = false;
            EconomyHandler.INSTANCE.addGold(100);
            this.initGui();
         }

         if(this.fRolld == this.sRolld) {
            this.wintype = 5;
            this.gameNotEnded = false;
            this.rolledDouble = false;
            this.initGui();
         }
      }

      if(i == 1) {
         if(this.dealer < this.player) {
            if(this.fRolld > this.sRolld) {
               this.sRolld = random.nextInt(6) + 1;
            }

            if(this.sRolld > this.fRolld) {
               this.fRolld = random.nextInt(6) + 1;
            }

            this.dealer = this.fRolld + this.sRolld;
            this.dealerR = true;
            if(this.fRolld == this.sRolld) {
               this.wintype = 5;
               this.gameNotEnded = false;
               this.rolledDouble = false;
               this.initGui();
            }
         }

         return false;
      } else if(i == 2 && this.player > this.dealer) {
         EconomyHandler.INSTANCE.addGold(75);
         return true;
      } else {
         if(i == 3) {
            if(this.player < this.dealer) {
               return true;
            }

            EconomyHandler.INSTANCE.decreaseGold(25);
         }

         if(i == 4) {
            this.fRollp = random.nextInt(6) + 1;
            this.player = this.fRollp + this.sRollp;
            if(this.fRollp == this.sRollp) {
               this.wintype = 4;
               EconomyHandler.INSTANCE.addGold(100);
               this.gameNotEnded = false;
               this.rolledDouble = false;
               this.initGui();
            }
         }

         if(i == 5) {
            this.sRollp = random.nextInt(6) + 1;
            this.player = this.fRollp + this.sRollp;
            if(this.fRollp == this.sRollp) {
               this.wintype = 4;
               EconomyHandler.INSTANCE.addGold(100);
               this.gameNotEnded = false;
               this.rolledDouble = false;
               this.initGui();
            }
         }

         return false;
      }
   }

   public void initGui() {
      this.buttonList.clear();
      if(!this.game) {
         this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height / 2, 100, 20, "Roll the Die!"));
         if(this.gameNotEnded) {
            this.buttonList.add(new GuiButton(6, this.width / 2 - 50, this.height / 2 + 20, 100, 20, "Exit"));
         }
      }

      if(this.game) {
         if(!this.playerR && this.rolledDouble) {
            this.buttonList.add(new GuiButton(2, this.width / 2 - 105, this.height / 2 + 80, 100, 20, "Re-roll Die 1"));
            this.buttonList.add(new GuiButton(3, this.width / 2 + 5, this.height / 2 + 80, 100, 20, "Re-roll Die 2"));
         }

         if(this.gameNotEnded) {
            this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height / 2 + 30, 100, 20, "Stay"));
         }

         if(!this.gameNotEnded) {
            this.buttonList.add(new GuiButton(5, this.width / 2 - 50, this.height / 2 + 30, 100, 20, "Exit"));
         }
      }

   }

   public void onGuiClosed() {
      this.entityplayer.addChatMessage(new ChatComponentText("One-Eyed Gambler: Just come by if your feeling a bit... lucky."));
   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 1) {
         if(EconomyHandler.INSTANCE.getGoldTotal() >= 50) {
            this.game = true;
            this.deal(0);
         } else {
            this.notEnough = true;
         }

         this.initGui();
      }

      if(guibutton.id == 2) {
         this.deal(4);
         this.playerR = true;
         this.initGui();
      }

      if(guibutton.id == 3) {
         this.deal(5);
         this.playerR = true;
         this.initGui();
      }

      if(guibutton.id == 4) {
         this.deal(1);
         if(this.deal(2)) {
            this.wintype = 2;
         }

         if(this.deal(3)) {
            this.wintype = 1;
         }

         if(this.dealer == this.player) {
            this.wintype = 3;
         }

         this.gameNotEnded = false;
         this.initGui();
      }

      if(guibutton.id == 5) {
         this.mc.displayGuiScreen(null);
      }

      if(guibutton.id == 6) {
         this.mc.displayGuiScreen(null);
      }

   }

   public void drawScreen(int i, int j, float f) {
      this.drawDefaultBackground();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ResourceLocation resource = new ResourceLocation("taleofkingdoms", "textures/gui/round.png");
      this.mc.renderEngine.bindTexture(resource);
      short c = 176;
      short c1 = 166;
      int l = (this.width - c) / 2;
      int i1 = (this.height - c1) / 2;
      this.drawTexturedModalRect(l, i1, 0, 0, c, c1);
      if(this.game) {
         this.drawString(this.fontRendererObj, "Player: " + this.player, this.width / 2 - 30, i1 + 40, 16777215);
         this.drawString(this.fontRendererObj, "Die one: " + this.fRollp, this.width / 2 - 30, i1 + 60, 16777215);
         this.drawString(this.fontRendererObj, "Die two: " + this.sRollp, this.width / 2 - 30, i1 + 80, 16777215);
         this.drawString(this.fontRendererObj, "Dealer: " + this.dealer, this.width / 2 + 30, i1 + 40, 16777215);
         this.drawString(this.fontRendererObj, "Die one: " + this.fRolld, this.width / 2 + 30, i1 + 60, 16777215);
         this.drawString(this.fontRendererObj, "Die two: " + this.sRolld, this.width / 2 + 30, i1 + 80, 16777215);
      }

      if(!this.game) {
         this.drawString(this.fontRendererObj, "Play Round Robin!", this.width / 2, i1 + 50, 16777215);
         this.drawString(this.fontRendererObj, "50 gold coins to play!", this.width / 2, i1 + 70, 16777215);
      }

      if(this.wintype == 1) {
         this.drawString(this.fontRendererObj, "You Lose!-Outnumbered the Player", this.width / 2, this.height / 2 - 95, 16777215);
      }

      if(this.wintype == 2) {
         this.drawString(this.fontRendererObj, "You Win!-Outnumbered the Dealer", this.width / 2, this.height / 2 - 95, 16777215);
      }

      if(this.wintype == 3) {
         this.drawString(this.fontRendererObj, "Draw!-Same number rolled!", this.width / 2, this.height / 2 - 80, 16777215);
      }

      if(this.wintype == 4) {
         this.drawString(this.fontRendererObj, "You win!-Player Rolled a Double!", this.width / 2, this.height / 2 - 95, 16777215);
      }

      if(this.wintype == 5) {
         this.drawString(this.fontRendererObj, "You Lose!-Dealer Rolled a Double!", this.width / 2, this.height / 2 - 95, 16777215);
      }

      if(this.playerR) {
         this.drawString(this.fontRendererObj, "Player Rerolled!", this.width / 2, this.height / 2 + 10, 16777215);
      }

      if(this.dealerR) {
         this.drawString(this.fontRendererObj, "Dealer Rerolled!", this.width / 2, this.height / 2 + 20, 16777215);
      }

      if(!this.notEnough) {
         this.drawString(this.fontRendererObj, "Round Robin - Total Money: " + EconomyHandler.INSTANCE.getGoldTotal() + " Gold Coins", this.width / 2, 15, 16777215);
      }

      if(this.notEnough) {
         this.drawString(this.fontRendererObj, "Round Robin - Total Money: " + EconomyHandler.INSTANCE.getGoldTotal() + " Gold Coins -NOT ENOUGH GOLD", this.width / 2, 15, 16777215);
      }

      super.drawScreen(i, j, f);
   }
}

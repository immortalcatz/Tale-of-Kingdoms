package mods.aginsun.kingdoms.client.guis;

import mods.aginsun.kingdoms.handlers.GoldKeeper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Items;
import net.minecraft.util.ChatComponentText;

import java.awt.*;

public final class GuiStableMaster extends GuiScreen {

   public void initGui() {
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 10, 120, 20, "Buy Starter Kit!"));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 60, this.height / 2 - 10, 120, 20, "Buy Expert Kit!"));
      this.buttonList.add(new GuiButton(3, this.width / 2 - 60, this.height / 2 + 10, 120, 20, "Exit!"));
   }

   public void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 1) {
         if(GoldKeeper.getGoldTotal() >= 1500) {
            this.mc.thePlayer.dropItem(Items.saddle, 1);
            this.mc.thePlayer.dropItem(Items.lead, 1);
            this.mc.thePlayer.dropItem(Items.wheat, 15);
            this.mc.thePlayer.dropItem(Items.name_tag, 1);
         } else {
            this.mc.thePlayer.addChatMessage(new ChatComponentText("You don\'t have enough money!"));
         }
      } else if(guibutton.id == 2) {
         if(GoldKeeper.getGoldTotal() >= 7500) {
            this.mc.thePlayer.dropItem(Items.diamond_horse_armor, 1);
            this.mc.thePlayer.dropItem(Items.wheat, 64);
            EntityHorse entity = new EntityHorse(this.mc.theWorld);
            entity.setHorseType(0);
            this.mc.theWorld.spawnEntityInWorld(entity);
         } else {
            this.mc.thePlayer.addChatMessage(new ChatComponentText("You don\'t have enough money!"));
         }
      } else if(guibutton.id == 3) {
         this.mc.setIngameFocus();
      }

   }

   public void drawScreen(int i, int j, float f) {
      String s = "Starter Kit: Saddle, lead, wheat and a name tag for the price of 1500 gold!";
      String s1 = "Expert Kit: Horse armor, a stack of wheat and a horse for the low price of 7500 gold!";
      this.drawString(this.fontRendererObj, s, this.width / 2 - fontRendererObj.getStringWidth(s) / 2, this.height / 2 - 30, Color.ORANGE.getRGB());
      this.drawString(this.fontRendererObj, s, this.width / 2 - fontRendererObj.getStringWidth(s1) / 2, this.height / 2 - 30, Color.ORANGE.getRGB());
   }
}

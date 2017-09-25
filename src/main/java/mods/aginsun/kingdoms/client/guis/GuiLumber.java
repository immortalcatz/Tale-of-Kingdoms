package mods.aginsun.kingdoms.client.guis;

import mods.aginsun.kingdoms.handlers.GoldKeeper;
import mods.aginsun.kingdoms.handlers.ResourceHandler;
import mods.aginsun.kingdoms.handlers.WorkerHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class GuiLumber extends GuiScreenToK {

   private World worldObj;
   public EntityPlayer player;
   public GoldKeeper gold;
   boolean goldchecker = false;
   private GuiPriceBar bar;


   public GuiLumber(EntityPlayer entityplayer1, World world) {
      this.player = entityplayer1;
      this.worldObj = world;
   }

   public void initGui() {
      this.bar = new GuiPriceBar(0, this.width / 2 - 100, 40, 90, 12, 1.0F, "red");
      this.bar.setBar((float)ResourceHandler.getInstance().getWoodPool() / 320.0F);
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, 75, 90, 20, "Collect 64"));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 100, 95, 90, 20, "Buy Worker."));
      this.buttonList.add(new GuiButton(3, this.width / 2 - 100, 115, 90, 20, "Cancel."));
   }

   protected void actionPerformed(GuiButton guibutton) {
      if(guibutton.id == 1 && ResourceHandler.getInstance().getWoodPool() >= 64) {
         ItemStack itemstack = new ItemStack(Item.getItemFromBlock(Blocks.log), 64, 0);
         EntityItem entityitem = new EntityItem(this.worldObj, this.player.posX, this.player.posY, this.player.posZ, itemstack);
         this.worldObj.spawnEntityInWorld(entityitem);
         this.goldchecker = false;
         ResourceHandler.getInstance().decreaseWoodPool(64);
      } else if(!this.worldObj.isRemote) {
         this.player.addChatMessage(new ChatComponentText("Foreman: Come back again later when we have the resources."));
      }

      if(guibutton.id == 2) {
         if(GoldKeeper.getGoldTotal() >= 1500) {
            if(WorkerHandler.getInstance().getLumberMembers() < 12) {
               WorkerHandler.getInstance().addLumberMember();
               GoldKeeper.decreaseGold(1500);
               if(!this.worldObj.isRemote) {
                  this.player.addChatMessage(new ChatComponentText("Foreman: He will begin to work immediately."));
               }
            } else if(!this.worldObj.isRemote) {
               this.player.addChatMessage(new ChatComponentText("Foreman: We have reached the capacity of men. Hire civilian workers instead."));
            }
         } else {
            this.goldchecker = true;
         }
      }

      if(guibutton.id == 3) {
         this.mc.displayGuiScreen(null);
      }

   }

   public void drawScreen(int i, int j, float f) {
      for(int k = 0; k < this.buttonList.size(); ++k) {
         if(this.buttonList.get(k) instanceof GuiButtonShop) {
            GuiButtonShop guibutton = (GuiButtonShop)this.buttonList.get(k);
            guibutton.drawButton(this.mc, i, j);
         }

         GuiButton var6 = (GuiButton)this.buttonList.get(k);
         var6.drawButton(this.mc, i, j);
      }

      if(!this.goldchecker) {
         this.drawString(this.fontRendererObj, "Foreman Menu - Total Money: " + GoldKeeper.getGoldTotal() + " Gold Coins", this.width / 2, 15, 16777215);
      } else {
         this.drawString(this.fontRendererObj, "Foreman Menu - Total Money: " + GoldKeeper.getGoldTotal() + " Gold Coins - NOT ENOUGH GOLD", this.width / 2, 15, 16777215);
      }

      this.drawString(this.fontRendererObj, "-Resources-    Note: Worker cost 1500 gold coins.", this.width / 2 - 87, 55, 16777215);
      this.bar.drawBar();
   }
}

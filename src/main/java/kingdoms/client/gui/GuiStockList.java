package kingdoms.client.gui;

import cpw.mods.fml.common.FMLCommonHandler;
import kingdoms.api.gui.GuiPriceBar;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.UltimateHelper;
import kingdoms.server.handlers.resources.GoldKeeper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiStockList extends GuiScreenToK
{
   int checkBounty = 0;
   private Item[] item = new Item[9];
   boolean reachedend = false;
   public ItemStack stackSelected;
   boolean goldchecker = false;
   private GuiPriceBar[] loadbar = new GuiPriceBar[8];
   public int shopcounter = 20;


   public GuiStockList(EntityPlayer player, World world)
   {
      super(player, world);
      this.setItemList();
      this.stackSelected = new ItemStack(Items.flint, 1, 0);
   }

   public void setItemList()
   {
      this.item[1] = (new ItemStack(Items.flint, 1, 0)).getItem();
      this.item[2] = (new ItemStack(Items.clay_ball, 1, 0)).getItem();
      this.item[3] = (new ItemStack(Items.iron_ingot, 1, 0)).getItem();
      this.item[4] = (new ItemStack(Items.diamond, 1, 0)).getItem();
      this.item[5] = (new ItemStack(Items.cooked_fished, 1, 0)).getItem();
      this.item[6] = (new ItemStack(Items.apple, 1, 0)).getItem();
      this.item[7] = (new ItemStack(Items.string, 1, 0)).getItem();
      this.item[8] = (new ItemStack(Items.feather, 1, 0)).getItem();
      this.initGui();
   }

   public void initGui() {
      this.buttonList.clear();
      this.loadbar[0] = new GuiPriceBar(0, this.width / 2 - 100, 43, 90, 12, 1.0F, "red");
      this.loadbar[1] = new GuiPriceBar(0, this.width / 2 - 100, 63, 90, 12, 1.0F, "red");
      this.loadbar[2] = new GuiPriceBar(0, this.width / 2 - 100, 83, 90, 12, 1.0F, "red");
      this.loadbar[3] = new GuiPriceBar(0, this.width / 2 - 100, 103, 90, 12, 1.0F, "red");
      this.loadbar[4] = new GuiPriceBar(0, this.width / 2 - 100, 123, 90, 12, 1.0F, "red");
      this.loadbar[5] = new GuiPriceBar(0, this.width / 2 - 100, 143, 90, 12, 1.0F, "red");
      this.loadbar[6] = new GuiPriceBar(0, this.width / 2 - 100, 163, 90, 12, 1.0F, "red");
      this.loadbar[7] = new GuiPriceBar(0, this.width / 2 - 100, 183, 90, 12, 1.0F, "red");
      /*this.loadbar[0].setBar(GoldKeeper.INSTANCE.flint / 200.0F);
      this.loadbar[1].setBar(GoldKeeper.INSTANCE.clay / 200.0F);
      this.loadbar[2].setBar(GoldKeeper.INSTANCE.iron / 200.0F);
      this.loadbar[3].setBar(GoldKeeper.INSTANCE.diamond / 200.0F);
      this.loadbar[4].setBar(GoldKeeper.INSTANCE.fish / 200.0F);
      this.loadbar[5].setBar(GoldKeeper.INSTANCE.apple / 200.0F);
      this.loadbar[6].setBar(GoldKeeper.INSTANCE.string / 200.0F);
      this.loadbar[7].setBar(GoldKeeper.INSTANCE.feather / 200.0F);*/
      String s7;
      /*if(this.item[1] != null) {
         s7 = this.item[1].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[1], this, 8, this.width / 2 + 20, 40, 90, 20, s7));
      }

      if(this.item[2] != null) {
         s7 = this.item[2].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[2], this, 9, this.width / 2 + 20, 60, 90, 20, s7));
      }

      if(this.item[3] != null) {
         s7 = this.item[3].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[3], this, 10, this.width / 2 + 20, 80, 90, 20, s7));
      }

      if(this.item[4] != null) {
         s7 = this.item[4].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[4], this, 11, this.width / 2 + 20, 100, 90, 20, s7));
      }

      if(this.item[5] != null) {
         s7 = this.item[5].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[5], this, 12, this.width / 2 + 20, 120, 90, 20, s7));
      }

      if(this.item[6] != null) {
         s7 = this.item[6].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[6], this, 13, this.width / 2 + 20, 140, 90, 20, s7));
      }

      if(this.item[7] != null) {
         s7 = this.item[7].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[7], this, 14, this.width / 2 + 20, 160, 90, 20, s7));
      }

      if(this.item[8] != null) {
         s7 = this.item[8].getUnlocalizedName() + ".name";
         s7 = this.st.translateKey(s7);
         this.buttonList.add(new GuiButtonShop(this.item[8], this, 15, this.width / 2 + 20, 180, 90, 20, s7));
      }*/

      this.buttonList.add(new GuiButton(18, this.width / 2 + 130, 160, 80, 20, "Buy Item"));
      this.buttonList.add(new GuiButton(19, this.width / 2 + 130, 220, 80, 20, "Exit"));
      this.buttonList.add(new GuiButton(21, this.width / 2 + 130, 200, 80, 20, "Buy 16 Items"));
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   public void onGuiClosed() {
      if(this.getWorld().isRemote) {
         this.getPlayer().addChatMessage(new ChatComponentText("Stock Keeper: Keep a look out on your stock supplies!"));
      }

   }

   protected void actionPerformed(GuiButton guibutton) {
      /*if(guibutton.id == 8) {
         this.itemSelected = this.item[1];
         this.goldchecker = false;
      }

      if(guibutton.id == 9) {
         this.itemSelected = this.item[2];
         this.goldchecker = false;
      }

      if(guibutton.id == 10) {
         this.itemSelected = this.item[3];
         this.goldchecker = false;
      }

      if(guibutton.id == 11) {
         this.itemSelected = this.item[4];
         this.goldchecker = false;
      }

      if(guibutton.id == 12) {
         this.itemSelected = this.item[5];
         this.goldchecker = false;
      }

      if(guibutton.id == 13) {
         this.itemSelected = this.item[6];
         this.goldchecker = false;
      }

      if(guibutton.id == 14) {
         this.itemSelected = this.item[7];
         this.goldchecker = false;
      }

      if(guibutton.id == 15) {
         this.itemSelected = this.item[8];
         this.goldchecker = false;
      }*/

      ItemStack itemstack1;
      Item item2;
      String s1;
      int j;
      float f1;
      if(guibutton.id == 18) {
         itemstack1 = stackSelected;
         item2 = itemstack1.getItem();
         j = GoldKeeper.INSTANCE.priceItem(item2);
         f1 = 0.0F;
         /*if(this.itemSelected == Items.flint) {
            f1 = GoldKeeper.INSTANCE.flint;
         }

         if(this.itemSelected == Items.clay_ball) {
            f1 = GoldKeeper.INSTANCE.clay;
         }

         if(this.itemSelected == Items.iron_ingot) {
            f1 = GoldKeeper.INSTANCE.iron;
         }

         if(this.itemSelected == Items.diamond) {
            f1 = GoldKeeper.INSTANCE.diamond;
         }

         if(this.itemSelected == Items.cooked_fished) {
            f1 = GoldKeeper.INSTANCE.fish;
         }

         if(this.itemSelected == Items.apple) {
            f1 = GoldKeeper.INSTANCE.apple;
         }

         if(this.itemSelected == Items.string) {
            f1 = GoldKeeper.INSTANCE.string;
         }

         if(this.itemSelected == Items.feather) {
            f1 = GoldKeeper.INSTANCE.feather;
         }*/

         f1 /= 100.0F;
         j = (int)((float)j + (float)j * f1);
         if(j <= getPlayerProvider().getGoldTotal()) {
            EntityItem entityitem = new EntityItem(this.getWorld(), this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, itemstack1);
            this.getPlayer().joinEntityItemWithWorld(entityitem);
            if(FMLCommonHandler.instance().getEffectiveSide().isServer()) {
               //provider.decreaseGold(j);
            }
         } else {
            this.goldchecker = true;
         }
      }

      if(guibutton.id == 21 && this.shopcounter >= 16) {
         itemstack1 = stackSelected;
         item2 = itemstack1.getItem();
         j = GoldKeeper.INSTANCE.priceItem(item2);
         f1 = 0.0F;
         /*if(this.itemSelected == Items.flint) {
            f1 = GoldKeeper.INSTANCE.flint;
         }

         if(this.itemSelected == Items.clay_ball) {
            f1 = GoldKeeper.INSTANCE.clay;
         }

         if(this.itemSelected == Items.iron_ingot) {
            f1 = GoldKeeper.INSTANCE.iron;
         }

         if(this.itemSelected == Items.diamond) {
            f1 = GoldKeeper.INSTANCE.diamond;
         }

         if(this.itemSelected == Items.cooked_fished) {
            f1 = GoldKeeper.INSTANCE.fish;
         }

         if(this.itemSelected == Items.apple) {
            f1 = GoldKeeper.INSTANCE.apple;
         }

         if(this.itemSelected == Items.string) {
            f1 = GoldKeeper.INSTANCE.string;
         }

         if(this.itemSelected == Items.feather) {
            f1 = GoldKeeper.INSTANCE.feather;
         }*/

         f1 /= 100.0F;
         j = (int)((float)j + (float)j * f1);
         if(j * 16 <= getPlayerProvider().getGoldTotal()) {
            this.shopcounter = 0;
         } else {
            this.goldchecker = true;
         }
      }

      if(guibutton.id == 19) {
         this.mc.displayGuiScreen(null);
         this.goldchecker = false;
      }

      if(guibutton.id == 20) {
         this.goldchecker = false;
      }

   }

   public void drawScreen(int i, int j, float f) {
      int f1;
      if(this.shopcounter < 16) {
         ItemStack s = stackSelected;
         Item s1 = s.getItem();
         f1 = GoldKeeper.INSTANCE.priceItem(s1);
         float l1 = 0.0F;
         /*if(this.itemSelected == Items.flint) {
            l1 = GoldKeeper.INSTANCE.flint;
         }

         if(this.itemSelected == Items.clay_ball) {
            l1 = GoldKeeper.INSTANCE.clay;
         }

         if(this.itemSelected == Items.iron_ingot) {
            l1 = GoldKeeper.INSTANCE.iron;
         }

         if(this.itemSelected == Items.diamond) {
            l1 = GoldKeeper.INSTANCE.diamond;
         }

         if(this.itemSelected == Items.cooked_fished) {
            l1 = GoldKeeper.INSTANCE.fish;
         }

         if(this.itemSelected == Items.apple) {
            l1 = GoldKeeper.INSTANCE.apple;
         }

         if(this.itemSelected == Items.string) {
            l1 = GoldKeeper.INSTANCE.string;
         }

         if(this.itemSelected == Items.feather) {
            l1 = GoldKeeper.INSTANCE.feather;
         }*/

         l1 /= 100.0F;
         f1 = (int)((float)f1 + (float)f1 * l1);
         if(f1 <= getPlayerProvider().getGoldTotal()) {
            EntityItem entityitem = new EntityItem(this.getWorld(), this.getPlayer().posX, this.getPlayer().posY, this.getPlayer().posZ, s);
            this.getPlayer().joinEntityItemWithWorld(entityitem);
            //provider.decreaseGold(f1);
         }

         ++this.shopcounter;
      }

      this.drawDefaultBackground();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      short var10 = 255;
      short var12 = 255;
      this.mc.renderEngine.bindTexture(UltimateHelper.BACKGROUND);
      f1 = (this.width - var10) / 2;
      this.drawTexturedModalRect(f1, 0, 0, 0, var10, var12);

      super.drawScreen(i, j, f);

      this.drawString(this.fontRendererObj, "Stock Menu - Total Money: " + getPlayerProvider().getGoldTotal() + " Gold Coins", this.width / 2, 15, 16763904);
      //String var13 = this.itemSelected.getUnlocalizedName() + ".name";
      //String var17 = this.st.translateKey(var13);
      //int var18 = GoldKeeper.INSTANCE.priceItem(this.itemSelected);
      float var19 = 0.0F;
      /*if(this.itemSelected == Items.flint) {
         var19 = GoldKeeper.INSTANCE.flint;
      }

      if(this.itemSelected == Items.clay_ball) {
         var19 = GoldKeeper.INSTANCE.clay;
      }

      if(this.itemSelected == Items.iron_ingot) {
         var19 = GoldKeeper.INSTANCE.iron;
      }

      if(this.itemSelected == Items.diamond) {
         var19 = GoldKeeper.INSTANCE.diamond;
      }

      if(this.itemSelected == Items.cooked_fished) {
         var19 = GoldKeeper.INSTANCE.fish;
      }

      if(this.itemSelected == Items.apple) {
         var19 = GoldKeeper.INSTANCE.apple;
      }

      if(this.itemSelected == Items.string) {
         var19 = GoldKeeper.INSTANCE.string;
      }

      if(this.itemSelected == Items.feather) {
         var19 = GoldKeeper.INSTANCE.feather;
      }*/

      var19 /= 100.0F;
      //var18 = (int)((float)var18 + (float)var18 * var19);
      if(this.goldchecker) {
         //this.drawString(this.fontRendererObj, "Selected Item Cost: " + var17 + " - NOT ENOUGH GOLD", this.width / 2, 30, 16763904);
      } else {
         //this.drawString(this.fontRendererObj, "Selected Item Cost: " + var17 + " - " + var18 + " Gold coins", this.width / 2, 30, 16763904);
      }

      this.drawString(this.fontRendererObj, "Note: Full bar means full cost!", this.width / 2, 200, 16763904);

      for(int var20 = 0; var20 < 8; ++var20) {
         this.loadbar[var20].drawBar();
      }

      super.drawScreen(i, j, f);
   }
}

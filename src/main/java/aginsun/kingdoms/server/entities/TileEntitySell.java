package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.server.handlers.resources.GoldKeeper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;

public final class TileEntitySell implements IInventory {

   private ItemStack[] inventory = new ItemStack[1];
   public GoldKeeper gold;


   public int getSizeInventory() {
      return this.inventory.length;
   }

   public ItemStack getStackInSlot(int i) {
      int j = 0;
      if(this.inventory[i] != null) {
         for(int k = 0; k < this.inventory[i].stackSize; ++k) {
            Item item = this.inventory[i].getItem();
            String s = item.getUnlocalizedName();
            GoldKeeper var10000 = this.gold;
            j = GoldKeeper.INSTANCE.priceItem(s);
            float f = 0.0F;
            if(item == Items.flint) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.flint;
            }

            if(item == Items.clay_ball) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.clay;
            }

            if(item == Items.iron_ingot) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.iron;
            }

            if(item == Items.diamond) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.diamond;
            }

            if(item == Items.cooked_fished) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.fish;
            }

            if(item == Items.apple) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.apple;
            }

            if(item == Items.string) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.string;
            }

            if(item == Items.feather) {
               var10000 = this.gold;
               f = GoldKeeper.INSTANCE.feather;
            }

            f /= 100.0F;
            j = (int)((float)j + (float)j * f);
         }

         if(j != 0) {
            this.inventory[i] = null;
         }
      }

      return this.inventory[i];
   }

   public void setInventorySlotContents(int i, ItemStack itemstack) {
      this.inventory[i] = itemstack;
      if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
         itemstack.stackSize = this.getInventoryStackLimit();
      }

   }

   public ItemStack decrStackSize(int i, int j) {
      if(this.inventory[i] != null) {
         ItemStack itemstack1;
         if(this.inventory[i].stackSize <= j) {
            itemstack1 = this.inventory[i];
            this.inventory[i] = null;
            return itemstack1;
         } else {
            itemstack1 = this.inventory[i].splitStack(j);
            if(this.inventory[i].stackSize == 0) {
               this.inventory[i] = null;
            }

            return itemstack1;
         }
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int slotIndex) {
      return null;
   }

   public int getInventoryStackLimit() {
      return 64;
   }

   public void openInventory() {}

   public void closeInventory() {
      WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);
      EntityClientPlayerMP entityplayer = FMLClientHandler.instance().getClient().thePlayer;
      if(!world.isRemote) {
         entityplayer.addChatMessage(new ChatComponentText("Shop Keeper: Thank you for selling your stuff here!"));
      }

   }

   @Override
   public String getInventoryName()
   {
      return "TeSell";
   }

   @Override
   public boolean hasCustomInventoryName()
   {
      return false;
   }

   public boolean isItemValidForSlot(int i, ItemStack itemstack) {
      return false;
   }

   public void markDirty() {}

   public boolean isUseableByPlayer(EntityPlayer entityplayer) {
      return true;
   }
}

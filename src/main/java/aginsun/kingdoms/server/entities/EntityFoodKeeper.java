package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.handlers.NetworkHandler;
import aginsun.kingdoms.server.handlers.packets.CPacketSyncShopItems;
import aginsun.kingdoms.server.handlers.resources.GoldKeeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_SHOPLIST;

public final class EntityFoodKeeper extends EntityNPC {

   private ItemStack[] stacks = new ItemStack[200];
   private GoldKeeper gold;
   private StringTranslate st = new StringTranslate();


   public EntityFoodKeeper(World world) {
      super(world, null, 100.0F);
      this.isImmuneToFire = false;
   }

   public boolean canBePushed() {
      return false;
   }

   public boolean interact(EntityPlayer player) {
      if(this.canInteractWith(player)) {
         this.heal(100.0F);
         int i = 0;
         int j = 0;
         String s = "";

         for(int k = 256; k < 32000; ++k) {
            boolean flag1 = false;
            if(Item.getItemById(k) != null) {
               ItemStack itemstack = new ItemStack(Item.getItemById(k), 1, 0);
               Item item = itemstack.getItem();
               if(item instanceof ItemFood) {
                  flag1 = true;
               }

               if(item != null) {
                  s = item.getUnlocalizedName();
               }

               if(item == Item.getItemById(354)) {
                  flag1 = true;
               }

               if(s != null) {
                  GoldKeeper var10000 = this.gold;
                  j = GoldKeeper.INSTANCE.priceItem(s);
               }

               String s1 = item.getUnlocalizedName() + ".name";
               String s2 = this.st.translateKey(s1);
               Item l = itemstack.getItem();

               if(l == Item.getItemById(26) || l == Item.getItemById(34) || l == Item.getItemById(36) ||
                       l == Item.getItemById(43) || l == Item.getItemById(51) || l == Item.getItemById(52) ||
                       l == Item.getItemById(55) || l == Item.getItemById(59) || l == Item.getItemById(60) ||
                       l == Item.getItemById(62) || l == Item.getItemById(63) || l == Item.getItemById(64) ||
                       l == Item.getItemById(68) || l == Item.getItemById(71) || l == Item.getItemById(74) ||
                       l == Item.getItemById(75) || l == Item.getItemById(78) || l == Item.getItemById(90) ||
                       l == Item.getItemById(93) || l == Item.getItemById(94) || l == Item.getItemById(97) ||
                       l == Item.getItemById(99) || l == Item.getItemById(100) || l == Item.getItemById(104) ||
                       l == Item.getItemById(105) || l == Item.getItemById(110) || l == Items.clay_ball ||
                       l == Items.iron_ingot || l == Items.diamond || l == Items.fish || l == Items.apple || l == Items.string ||
                       l == Items.feather || !flag1) {
                  j = 0;
               }

               if(j > 0 && !s1.equals("null.name") && !s1.equals(s2)) {
                  stacks[i] = itemstack;
                  ++i;
               }
            }
         }

         if (!worldObj.isRemote)
         {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncShopItems(stacks), (EntityPlayerMP) player);
         }
      }
      return true;
   }
}

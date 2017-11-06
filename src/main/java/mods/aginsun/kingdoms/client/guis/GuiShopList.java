package mods.aginsun.kingdoms.client.guis;

import cpw.mods.fml.common.FMLCommonHandler;
import mods.aginsun.kingdoms.TaleOfKingdoms;
import mods.aginsun.kingdoms.entities.EntityForgeKeeper;
import mods.aginsun.kingdoms.handlers.GoldKeeper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public final class GuiShopList extends GuiScreenToK
{
    public World worldObj = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);
    public EntityPlayer entityplayer;
    public GoldKeeper gold;
    public StringTranslate st = new StringTranslate();
    private int checkBounty = 0, currentGui = 0, price, shopcounter = 20;
    private Item[] item = new Item[200];
    private Item[] items = new Item[200];
    private boolean reachedend = false;
    public Item itemSelected;
    private boolean goldchecker = false;
    private String stringGet = "", stringoutput = "";
    public static TaleOfKingdoms taleofkingdoms;
    private final ResourceLocation texture = new ResourceLocation("taleofkingdoms", "textures/guis/crafting.png");

    public GuiShopList(EntityPlayer entityplayer1, World world, Item[] items)
    {
        this.items = items;
        this.entityplayer = entityplayer1;
        this.worldObj = world;
        this.setItemList();
        this.itemSelected = this.items[0];
    }

    public void setItemList()
    {
        byte i = 0;
        int var3 = i + this.currentGui * 16;

        for (int j = 0; j <= 16; ++j)
        {
            if(this.items[var3 + j] != null)
            {
                this.item[j + 1] = this.items[var3 + j];
            }
            else
            {
                this.item[j + 1] = null;
            }
        }

        if(this.items[var3 + 17] == null)
        {
            this.reachedend = true;
        }
        else
        {
            this.reachedend = false;
        }
        this.initGui();
    }

   public void initGui() {
      this.buttonList.clear();
      Item item16;
      String s15;
      if(this.item[1] != null) {
         item16 = (new ItemStack(this.item[1], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[1], this, 0, this.width / 2 - 110, 40, 90, 20, s15));
      }

      if(this.item[2] != null) {
         item16 = (new ItemStack(this.item[2], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[2], this, 1, this.width / 2 - 110, 60, 90, 20, s15));
      }

      if(this.item[3] != null) {
         item16 = (new ItemStack(this.item[3], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[3], this, 2, this.width / 2 - 110, 80, 90, 20, s15));
      }

      if(this.item[4] != null) {
         item16 = (new ItemStack(this.item[4], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[4], this, 3, this.width / 2 - 110, 100, 90, 20, s15));
      }

      if(this.item[5] != null) {
         item16 = (new ItemStack(this.item[5], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[5], this, 4, this.width / 2 - 110, 120, 90, 20, s15));
      }

      if(this.item[6] != null) {
         item16 = (new ItemStack(this.item[6], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[6], this, 5, this.width / 2 - 110, 140, 90, 20, s15));
      }

      if(this.item[7] != null) {
         item16 = (new ItemStack(this.item[7], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[7], this, 6, this.width / 2 - 110, 160, 90, 20, s15));
      }

      if(this.item[8] != null) {
         item16 = (new ItemStack(this.item[8], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[8], this, 7, this.width / 2 - 110, 180, 90, 20, s15));
      }

      if(this.item[9] != null) {
         item16 = (new ItemStack(this.item[9], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[9], this, 8, this.width / 2 - 10, 40, 90, 20, s15));
      }

      if(this.item[10] != null) {
         item16 = (new ItemStack(this.item[10], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[10], this, 9, this.width / 2 - 10, 60, 90, 20, s15));
      }

      if(this.item[11] != null) {
         item16 = (new ItemStack(this.item[11], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[11], this, 10, this.width / 2 - 10, 80, 90, 20, s15));
      }

      if(this.item[12] != null) {
         item16 = (new ItemStack(this.item[12], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[12], this, 11, this.width / 2 - 10, 100, 90, 20, s15));
      }

      if(this.item[13] != null) {
         item16 = (new ItemStack(this.item[13], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[13], this, 12, this.width / 2 - 10, 120, 90, 20, s15));
      }

      if(this.item[14] != null) {
         item16 = (new ItemStack(this.item[14], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[14], this, 13, this.width / 2 - 10, 140, 90, 20, s15));
      }

      if(this.item[15] != null) {
         item16 = (new ItemStack(this.item[15], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[15], this, 14, this.width / 2 - 10, 160, 90, 20, s15));
      }

      if(this.item[16] != null) {
         item16 = (new ItemStack(this.item[16], 1, 0)).getItem();
         s15 = item16.getUnlocalizedName() + ".name";
         s15 = this.st.translateKey(s15);
         this.buttonList.add(new GuiButtonShop(this.item[16], this, 15, this.width / 2 - 10, 180, 90, 20, s15));
      }

      this.buttonList.add(new GuiButton(17, this.width / 2 - 120, 200, 70, 20, I18n.format("gui.shop.back")));
      this.buttonList.add(new GuiButton(16, this.width / 2 + 50, 200, 70, 20, I18n.format("gui.shop.next")));
      this.buttonList.add(new GuiButton(18, this.width / 2 - 35, 200, 70, 20, I18n.format("gui.shop.buy")));
      this.buttonList.add(new GuiButton(19, this.width / 2 - 35, 220, 70, 20, I18n.format("gui.exit")));
      GuiButton btn = new GuiButton(20, this.width / 2 - 120, 220, 70, 20, I18n.format("gui.shop.sell"));
       btn.enabled = false;
       this.buttonList.add(btn);
      this.buttonList.add(new GuiButton(21, this.width / 2 + 50, 220, 70, 20, I18n.format("gui.shop.sell.sixteen")));
   }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void onGuiClosed()
    {
        if(!this.worldObj.isRemote)
        {
            this.entityplayer.addChatMessage(new ChatComponentText(I18n.format("gui.shop.bye")));
        }
    }

   protected void actionPerformed(GuiButton guibutton) {
      Item itemstack1;
      if(guibutton.id == 0) {
         this.itemSelected = this.item[1];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 1) {
         this.itemSelected = this.item[2];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 2) {
         this.itemSelected = this.item[3];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 3) {
         this.itemSelected = this.item[4];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 4) {
         this.itemSelected = this.item[5];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 5) {
         this.itemSelected = this.item[6];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 6) {
         this.itemSelected = this.item[7];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 7) {
         this.itemSelected = this.item[8];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 8) {
         this.itemSelected = this.item[9];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 9) {
         this.itemSelected = this.item[10];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 10) {
         this.itemSelected = this.item[11];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 11) {
         this.itemSelected = this.item[12];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 12) {
         this.itemSelected = this.item[13];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 13) {
         this.itemSelected = this.item[14];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 14) {
         this.itemSelected = this.item[15];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 15) {
         this.itemSelected = this.item[16];
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 16) {
         if(!this.reachedend) {
            ++this.currentGui;
         }

         this.setItemList();
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      if(guibutton.id == 17) {
         if(this.currentGui != 0) {
            --this.currentGui;
         }

         this.setItemList();
         this.goldchecker = false;
         if(this.itemSelected != null) {
            itemstack1 = (new ItemStack(this.itemSelected, 1, 0)).getItem();
            this.stringGet = itemstack1.getUnlocalizedName() + ".name";
            if(this.stringGet != null) {
               this.stringoutput = this.st.translateKey(this.stringGet);
            }

            this.price = GoldKeeper.priceItem(String.valueOf(itemstack1.getUnlocalizedName()));
            this.price = (int)((double)this.price + (double)this.price * 0.8D);
            if(itemstack1 instanceof ItemFood) {
               this.price = (int)((double)this.price + (double)this.price * 0.2D);
            }
         }
      }

      Item item20;
      String s1;
      int j;
      ItemStack var7;
      if(guibutton.id == 18) {
         var7 = new ItemStack(this.itemSelected, 1, 0);
         item20 = var7.getItem();
         s1 = item20.getUnlocalizedName();
         j = GoldKeeper.priceItem(s1);
         j = (int)((double)j + (double)j * 0.8D);
         if(item20 instanceof ItemFood) {
            j = (int)((double)j + (double)j * 0.2D);
         }

         if(j <= GoldKeeper.getGoldTotal()) {
            EntityItem entityitem = new EntityItem(this.worldObj, this.entityplayer.posX, this.entityplayer.posY, this.entityplayer.posZ, var7);
            this.worldObj.spawnEntityInWorld(entityitem);
            GoldKeeper.decreaseGold(j);
         } else {
            this.goldchecker = true;
         }
      }

      if(guibutton.id == 21 && this.shopcounter >= 16) {
         var7 = new ItemStack(this.itemSelected, 1, 0);
         item20 = var7.getItem();
         s1 = item20.getUnlocalizedName();
         j = GoldKeeper.priceItem(s1);
         j = (int)((double)j + (double)j * 0.8D);
         if(item20 instanceof ItemFood) {
            j = (int)((double)j + (double)j * 0.2D);
         }

         if(j * 16 <= GoldKeeper.getGoldTotal()) {
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
         this.mc.displayGuiScreen(null);
         this.entityplayer.openGui(TaleOfKingdoms.instance, 1, this.worldObj, (int)this.entityplayer.posX, (int)this.entityplayer.posY, (int)this.entityplayer.posZ);
         this.goldchecker = false;
      }
   }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        int j1;

        if(this.shopcounter < 16)
        {
            final Item guibutton = this.itemSelected;
            String resource = guibutton.getUnlocalizedName();
            j1 = GoldKeeper.priceItem(resource);
            j1 = (int)((double)j1 + (double)j1 * 0.8D);

            if(guibutton instanceof ItemFood)
            {
                j1 = (int)((double)j1 + (double)j1 * 0.2D);
            }

            if(j1 <= GoldKeeper.getGoldTotal())
            {
                final EntityItem entityitem = new EntityItem(this.worldObj, this.entityplayer.posX, this.entityplayer.posY, this.entityplayer.posZ, new ItemStack(guibutton));
                this.entityplayer.joinEntityItemWithWorld(entityitem);
                GoldKeeper.decreaseGold(j1);
            }
            ++this.shopcounter;
        }

        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(texture);
        j1 = (this.width - 255) / 2;
        this.drawTexturedModalRect(j1, 0, 0, 0, 255, 255);

        this.drawString(this.fontRendererObj, I18n.format("gui.shop.title", GoldKeeper.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.shop.title", GoldKeeper.getGoldTotal())) / 2, 5, 16763904);
        this.drawString(this.fontRendererObj, "Selected Item Cost: " + this.stringoutput + " - " + this.price + " Gold coins", this.width / 2 - fontRendererObj.getStringWidth("Selected Item Cost: " + this.stringoutput + " - " + this.price + " Gold coins") / 2, 20, 16763904);

        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, 33, 16763904);
        }

        super.drawScreen(i, j, f);
    }
}
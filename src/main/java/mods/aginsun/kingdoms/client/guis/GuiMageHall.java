package mods.aginsun.kingdoms.client.guis;

import cpw.mods.fml.common.FMLCommonHandler;
import mods.aginsun.kingdoms.handlers.GoldKeeper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class GuiMageHall extends GuiScreenToK
{
    public EntityPlayer entityplayer;
    private boolean goldchecker = false;
    private World worldObj = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

    public GuiMageHall(EntityPlayer entityplayer1, World world)
    {
        this.entityplayer = entityplayer1;
        this.worldObj = world;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 45, this.height / 2 - 32, 90, 20, I18n.format("gui.mage.enchant")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 45, this.height / 2 - 10, 90, 20, I18n.format("gui.mage.recruit")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 45, this.height / 2 + 12, 90, 20, I18n.format("gui.exit")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                ItemStack var9 = this.entityplayer.inventory.getCurrentItem();
                if (500 <= GoldKeeper.getGoldTotal() && var9 != null)
                {
                    final Random random = new Random();
                    int[] ai = new int[3];

                    int k;

                    for (k = 0; k < 3; ++k)
                    {
                        ai[k] = EnchantmentHelper.calcItemStackEnchantability(random, k, 30, var9);
                    }

                    k = random.nextInt(3);
                    List list1 = EnchantmentHelper.buildEnchantmentList(random, var9, ai[k]);

                    if (list1 != null)
                    {
                        for (Object aList1 : list1)
                        {
                            EnchantmentData enchantmentdata = (EnchantmentData) aList1;
                            var9.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                        }

                        GoldKeeper.decreaseGold(500);
                        this.entityplayer.addChatMessage(new ChatComponentText(I18n.format("gui.mage.enchanted", var9.getDisplayName())));
                    }
                    else if (!this.worldObj.isRemote)
                    {
                        this.entityplayer.addChatMessage(new ChatComponentText(I18n.format("gui.mage.dontEnchant")));
                    }
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 2:
                if(2000 <= GoldKeeper.getGoldTotal())
                {
                    EntityLiving itemstack = (EntityLiving)EntityList.createEntityByName("DefendMage", this.worldObj);
                    itemstack.setLocationAndAngles(this.entityplayer.posX, this.entityplayer.posY, this.entityplayer.posZ, 0.0F, 0.0F);
                    this.worldObj.spawnEntityInWorld(itemstack);
                    GoldKeeper.decreaseGold(2000);
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
    public void onGuiClosed()
    {
        if (!this.worldObj.isRemote)
        {
            this.entityplayer.addChatMessage(new ChatComponentText(I18n.format("gui.mage.bye")));
        }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);

        this.drawString(this.fontRendererObj, I18n.format("gui.mage.title", GoldKeeper.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.title", GoldKeeper.getGoldTotal())) / 2, this.height / 2 - 95, 16763904);
        this.drawString(this.fontRendererObj, I18n.format("gui.mage.enchant.selected"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.enchant.selected")) / 2, this.height / 2 - 80, 16763904);

        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, this.height / 2 - 50, 16763904);
        }
        this.drawString(this.fontRendererObj, I18n.format("gui.mage.recruit.price"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.recruit.price")) / 2, this.height / 2 - 65, 16763904);
    }
}
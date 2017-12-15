package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.api.gui.GuiScreenToK;
import aginsun.kingdoms.server.handlers.NetworkHandler;
import aginsun.kingdoms.server.handlers.packets.SPacketSpawnEntity;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public final class GuiMageHall extends GuiScreenToK
{
    public EntityPlayer entityplayer;
    private boolean goldchecker = false;

    public GuiMageHall(EntityPlayer entityplayer1, World world)
    {
        super(world);
        this.entityplayer = entityplayer1;
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
                if (500 <= EconomyHandler.INSTANCE.getGoldTotal() && var9 != null)
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

                        EconomyHandler.INSTANCE.decreaseGold(500);
                        this.entityplayer.addChatMessage(new ChatComponentTranslation("gui.mage.enchanted", var9.getDisplayName()));
                    }
                    else if (!this.world.isRemote)
                    {
                        this.entityplayer.addChatMessage(new ChatComponentTranslation("gui.mage.dontEnchant"));
                    }
                }
                else
                {
                    this.goldchecker = true;
                }
                break;
            case 2:
                if(2000 <= EconomyHandler.INSTANCE.getGoldTotal())
                {
                    NetworkHandler.INSTANCE.sendToServer(new SPacketSpawnEntity("DefendMage"));
                    EconomyHandler.INSTANCE.decreaseGold(2000);
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
        if (!this.world.isRemote)
        {
            this.entityplayer.addChatMessage(new ChatComponentTranslation("gui.mage.bye"));
        }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);

        this.drawString(this.fontRendererObj, I18n.format("gui.mage.title", EconomyHandler.INSTANCE.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.title", EconomyHandler.INSTANCE.getGoldTotal())) / 2, this.height / 2 - 95, 16763904);
        this.drawString(this.fontRendererObj, I18n.format("gui.mage.enchant.selected"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.enchant.selected")) / 2, this.height / 2 - 80, 16763904);

        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, this.height / 2 - 50, 16763904);
        }

        this.drawString(this.fontRendererObj, I18n.format("gui.mage.recruit.price"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.mage.recruit.price")) / 2, this.height / 2 - 65, 16763904);
    }
}
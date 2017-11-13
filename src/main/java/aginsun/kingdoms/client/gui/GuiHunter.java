package aginsun.kingdoms.client.gui;

import aginsun.kingdoms.api.gui.GuiPriceBar;
import aginsun.kingdoms.api.gui.GuiScreenToK;
import aginsun.kingdoms.server.entities.EntityHired;
import aginsun.kingdoms.server.handlers.UltimateHelper;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import aginsun.kingdoms.server.handlers.resources.GloryHandler;
import aginsun.kingdoms.server.handlers.resources.HunterHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public final class GuiHunter extends GuiScreenToK
{
    private int glory;
    public EntityPlayer player;
    private GuiPriceBar worthness;
    private boolean goldchecker = false;

    public GuiHunter(EntityPlayer player, World world)
    {
        super(world);
        this.player = player;
        this.glory = GloryHandler.INSTANCE.getGlory();
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();

        if (!HunterHandler.INSTANCE.getHunter())
        {
            this.buttonList.add(new GuiButton(1, this.width / 2 + 110, 140, 100, 20, I18n.format("gui.guildMaster.sigh")));
        }
        else
        {
            this.buttonList.add(new GuiButton(1, this.width / 2 + 110, 140, 100, 20, I18n.format("gui.guildMaster.discard")));
        }

        this.buttonList.add(new GuiButton(2, this.width / 2 + 110, 160, 100, 20, I18n.format("gui.guildMaster.hire")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 110, 180, 100, 20, I18n.format("gui.guildMaster.fix")));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 110, 200, 100, 20, I18n.format("gui.guildMaster.retire")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 110, 220, 100, 20, I18n.format("gui.exit")));

        this.worthness = new GuiPriceBar(0, this.width / 2 + 110, 120, 125, 12, 1.0F, "red");
        this.worthness.setBar(this.glory / 10000.0F);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (!this.world.isRemote)
                {
                    if (!HunterHandler.INSTANCE.getHunter())
                    {
                        this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.killMobs"));
                    }
                    else
                    {
                        this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.discarded"));
                    }
                    this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.await"));
                }
                this.initGui();
                break;
            case 2:
                if (1500 <= EconomyHandler.INSTANCE.getGoldTotal())
                {
                    EntityLiving entityLiving = (EntityLiving) UltimateHelper.INSTANCE.getEntity("Hired", world);
                    entityLiving.setLocationAndAngles(this.player.posX, this.player.posY, this.player.posZ, 0.0F, 0.0F);
                    this.world.spawnEntityInWorld(entityLiving);
                    EconomyHandler.INSTANCE.decreaseGold(1500);
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
            case 4:
                InventoryPlayer var6 = this.player.inventory;
                boolean entity = false;

                if (var6.hasItem(Item.getItemFromBlock(Blocks.log)))
                {
                    for (int entityhired = 0; entityhired < var6.mainInventory.length; ++entityhired)
                    {
                        if (var6.mainInventory[entityhired] != null && var6.mainInventory[entityhired].getItem() == Item.getItemFromBlock(Blocks.log))
                        {
                            ItemStack itemstack = var6.getStackInSlot(entityhired);

                            if (itemstack.stackSize == itemstack.getMaxStackSize() && !entity)
                            {
                                var6.setInventorySlotContents(entityhired, null);
                                entity = true;
                            }
                        }
                    }
                }

                if (!entity)
                {
                    if (!this.world.isRemote)
                    {
                        this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.needMore"));
                    }
                }
                else if (!this.world.isRemote)
                {
                    this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.fixed"));
                }
                break;
            case 5:
                for (int var7 = 0; var7 < this.world.loadedEntityList.size(); ++var7)
                {
                    Entity var8 = (Entity)this.world.loadedEntityList.get(var7);

                    if(var8 instanceof EntityHired)
                    {
                        EntityHired var9 = (EntityHired)var8;
                        var9.setDead();
                        EconomyHandler.INSTANCE.addGold(1000);
                    }
                }
                break;
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (!this.world.isRemote)
        {
            this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.bye"));
        }
    }

    @Override
    public void drawScreen(int x, int y, float partial)
    {
        super.drawScreen(x, y, partial);

        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.kingship"), this.width / 2 + 100, 110, 11158783);
        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.title", EconomyHandler.INSTANCE.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.guildMaster.title", EconomyHandler.INSTANCE.getGoldTotal())) / 2, 15, 16777215);

        if (this.goldchecker)
        {
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, 27, 16777215);
        }

        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.note"), this.width / 2, 10, 16772608);
        this.worthness.drawBar();
    }
}
package kingdoms.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kingdoms.api.gui.GuiPriceBar;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.entities.EntityHired;
import kingdoms.server.handlers.UltimateHelper;
import kingdoms.server.handlers.resources.HunterHandler;
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

import java.util.stream.IntStream;

@SideOnly(Side.CLIENT)
public class GuiHunter extends GuiScreenToK
{
    private GuiPriceBar worthness;
    private boolean goldchecker = false;

    public GuiHunter(EntityPlayer player, World world)
    {
        super(player, world);
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();

        this.buttonList.add(!HunterHandler.INSTANCE.getHunter() ? new GuiButton(1, this.width / 2 + 110, 140, 100, 20, I18n.format("gui.guildMaster.sigh")) : new GuiButton(1, this.width / 2 + 110, 140, 100, 20, I18n.format("gui.guildMaster.discard")));

        this.buttonList.add(new GuiButton(2, this.width / 2 + 110, 160, 100, 20, I18n.format("gui.guildMaster.hire")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 110, 180, 100, 20, I18n.format("gui.guildMaster.fix")));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 110, 200, 100, 20, I18n.format("gui.guildMaster.retire")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 110, 220, 100, 20, I18n.format("gui.exit")));

        this.worthness = new GuiPriceBar(0, this.width / 2 + 110, 120, 125, 12, 1.0F, "red");
        this.worthness.setBar(playerProvider.getGlory() / 10000.0F);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                this.player.addChatMessage(!HunterHandler.INSTANCE.getHunter() ? new ChatComponentTranslation("gui.guildMaster.killMobs") : new ChatComponentTranslation("gui.guildMaster.discarded"));
                this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.await"));
                this.initGui();
                break;
            case 2:
                if (1500 <= playerProvider.getGoldTotal())
                {
                    EntityLiving entityLiving = (EntityLiving) UltimateHelper.INSTANCE.getEntity("Hired", world);
                    entityLiving.setLocationAndAngles(this.player.posX, this.player.posY, this.player.posZ, 0.0F, 0.0F);
                    this.world.spawnEntityInWorld(entityLiving);
                    playerProvider.decreaseGold(1500);
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
                InventoryPlayer inventoryPlayer = this.player.inventory;
                boolean entity = false;

                if (inventoryPlayer.hasItem(Item.getItemFromBlock(Blocks.log)))
                {
                    for (ItemStack stack : inventoryPlayer.mainInventory)
                    {
                        if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.log))
                        {
                            if (stack.stackSize == stack.getMaxStackSize() && !entity)
                            {
                                inventoryPlayer.setItemStack(null);
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
                IntStream.range(0, this.world.loadedEntityList.size()).mapToObj(var7 -> (Entity) this.world.loadedEntityList.get(var7)).filter(entity1 -> entity1 instanceof EntityHired).map(entity1 -> (EntityHired) entity1).forEach(var9 -> {
                    var9.setDead();
                    playerProvider.addGold(1000);
                });
                break;
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (!this.world.isRemote)
            this.player.addChatMessage(new ChatComponentTranslation("gui.guildMaster.bye"));
    }

    @Override
    public void drawScreen(int x, int y, float partial)
    {
        super.drawScreen(x, y, partial);

        this.drawString(fontRendererObj, "Your glory: " + playerProvider.getGlory(), this.width / 2, height / 2, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.kingship"), this.width / 2 + 100, 110, 11158783);
        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.title", playerProvider.getGoldTotal()), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.guildMaster.title", playerProvider.getGoldTotal())) / 2, 15, 16777215);

        if (this.goldchecker)
            this.drawString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.notEnough")) / 2, 27, 16777215);

        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.note"), this.width / 2, 10, 16772608);
        this.worthness.drawBar();
    }
}
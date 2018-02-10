package kingdoms.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kingdoms.api.gui.GuiPriceBar;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.entities.EntityHired;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.UltimateHelper;
import kingdoms.server.handlers.packets.server.SPacketHunter;
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

        String contract;

        if (getPlayerProvider().hunter)
        {
            contract = I18n.format("gui.guildMaster.discard");
        }
        else
        {
            contract = I18n.format("gui.guildMaster.sigh");
        }

        this.buttonList.add(new GuiButton(1, this.width / 2 + 110, 140, 100, 20, contract));

        this.buttonList.add(new GuiButton(2, this.width / 2 + 110, 160, 100, 20, I18n.format("gui.guildMaster.hire")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 110, 180, 100, 20, I18n.format("gui.guildMaster.fix")));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 110, 200, 100, 20, I18n.format("gui.guildMaster.retire")));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 110, 220, 100, 20, I18n.format("gui.exit")));

        this.worthness = new GuiPriceBar(0, this.width / 2 + 110, 120, 125, 12, 1.0F, "red");
        this.worthness.setBar(getPlayerProvider().getGlory() / 10000.0F);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        String contract;

        if (getPlayerProvider().hunter)
        {
            contract = "gui.guildMaster.discarded";
        }
        else
        {
            contract = "gui.guildMaster.killMobs";
        }

        switch (button.id)
        {
            case 1:
                getPlayer().addChatMessage(new ChatComponentTranslation(contract));
                getPlayerProvider().hunter = !getPlayerProvider().hunter;
                getPlayer().addChatMessage(new ChatComponentTranslation("gui.guildMaster.await"));
                NetworkHandler.INSTANCE.sendToServer(new SPacketHunter(getPlayerProvider()));
                this.initGui();
                break;
            case 2:
                if (1500 <= getPlayerProvider().getGoldTotal())
                {
                    EntityLiving entityLiving = (EntityLiving) UltimateHelper.INSTANCE.getEntity("Hired", getWorld());
                    entityLiving.setLocationAndAngles(getPlayer().posX, getPlayer().posY, getPlayer().posZ, 0.0F, 0.0F);
                    this.getWorld().spawnEntityInWorld(entityLiving);
                    getPlayerProvider().decreaseGold(1500);
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
                InventoryPlayer inventoryPlayer = getPlayer().inventory;
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
                    if (!this.getWorld().isRemote)
                    {
                        getPlayer().addChatMessage(new ChatComponentTranslation("gui.guildMaster.needMore"));
                    }
                }
                else if (!this.getWorld().isRemote)
                {
                    getPlayer().addChatMessage(new ChatComponentTranslation("gui.guildMaster.fixed"));
                }
                break;
            case 5:
                int bound = this.getWorld().loadedEntityList.size();
                for (int var7 = 0; var7 < bound; var7++) {
                    Entity entity1 = (Entity) this.getWorld().loadedEntityList.get(var7);
                    if (entity1 instanceof EntityHired) {
                        EntityHired var9 = (EntityHired) entity1;
                        var9.setDead();
                        getPlayerProvider().addGold(1000);
                    }
                }
                break;
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (!this.getWorld().isRemote)
            getPlayer().addChatMessage(new ChatComponentTranslation("gui.guildMaster.bye"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.kingship"), this.width / 2 + 100, 110, 11158783);

        this.drawString(this.fontRendererObj, I18n.format("gui.guildMaster.note"), this.width / 2, 10, 16772608);
        this.worthness.drawBar();
    }
}
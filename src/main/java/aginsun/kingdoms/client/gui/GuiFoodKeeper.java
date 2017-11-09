package aginsun.kingdoms.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class GuiFoodKeeper extends GuiScreenToK
{
    private World worldObj;
    private EntityPlayer entityplayer;
    private boolean freebread = true;

    public GuiFoodKeeper(EntityPlayer entityplayer1, World world)
    {
        this.entityplayer = entityplayer1;
        this.worldObj = world;
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 60, this.height / 2 - 23, 120, 20, I18n.format("gui.farmer.giveBread")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 60, this.height / 2 + 3, 120, 20, I18n.format("gui.exit")));
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                if (this.freebread)
                {
                    if (!this.worldObj.isRemote)
                    {
                        this.entityplayer.addChatMessage(new ChatComponentText(I18n.format("gui.farmer.take")));
                    }

                    ItemStack itemstack = new ItemStack(Items.bread, 1, 0);
                    EntityItem entityitem = new EntityItem(this.worldObj, this.entityplayer.posX, this.entityplayer.posY, this.entityplayer.posZ, itemstack);
                    this.worldObj.spawnEntityInWorld(entityitem);
                    this.freebread = false;
                }
                else if (!this.worldObj.isRemote)
                {
                    this.entityplayer.addChatMessage(new ChatComponentText(I18n.format("gui.farmer.have")));
                }
                break;
            case 2:
                this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    protected void keyTyped(char character, int code)
    {
        if (code == 1 || code == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.thePlayer.closeScreen();
        }
    }
}
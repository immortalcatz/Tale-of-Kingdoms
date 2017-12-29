package kingdoms.client.gui;

import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.server.SPacketAdd;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public final class GuiFoodKeeper extends GuiScreenToK
{
    private boolean freeBread = true;

    public GuiFoodKeeper(EntityPlayer player, World world)
    {
        super(player, world);
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
                if (this.freeBread)
                {
                    this.mc.displayGuiScreen(null);
                    NetworkHandler.INSTANCE.sendToServer(new SPacketAdd(new ItemStack(Items.bread), 1, "gui.farmer.take"));
                    this.freeBread = false;
                }
                else if (!this.world.isRemote)
                {
                    this.player.addChatMessage(new ChatComponentTranslation("gui.farmer.have"));
                }
                break;
            case 2:
                this.mc.displayGuiScreen(null);
                break;
        }
    }
}
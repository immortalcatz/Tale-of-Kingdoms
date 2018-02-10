package kingdoms.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.server.SPacketAdd;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
class GuiFisher(player: EntityPlayer, world: World): GuiScreenToK(player, world)
{
    override fun initGui()
    {
        val wid = this.width / 2 - 60
        val hei = this.height / 2

        this.buttonList.clear();
        this.buttonList.add(GuiButton(1, wid, hei - 10, 120, 20, I18n.format("gui.fisher.fishingRod")));
        this.buttonList.add(GuiButton(2, wid, hei + 10, 120, 20, I18n.format("gui.exit")));
    }

    override fun actionPerformed(btn: GuiButton)
    {
        if (btn.id == 1)
        {
            this.mc.displayGuiScreen(null);
            NetworkHandler.sendToServer(SPacketAdd(ItemStack(Items.fishing_rod), 1, "npc.fisher.dialog.fishingRod"));
        }
        else if (btn.id == 2)
        {
            this.mc.displayGuiScreen(null);
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float)
    {
        this.drawString(this.fontRendererObj, I18n.format("gui.fisher.dialog.for"), this.width / 2 - this.fontRendererObj.getStringWidth(I18n.format("gui.fisher.dialog.for")) / 2, this.height / 2 - 30, 0xFFC800);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
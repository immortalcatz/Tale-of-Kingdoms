package kingdoms.client.gui

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import kingdoms.api.gui.GuiScreenToK
import kingdoms.server.handlers.NetworkHandler
import kingdoms.server.handlers.UltimateHelper
import kingdoms.server.handlers.packets.server.SPacketBank
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.world.World

@SideOnly(Side.CLIENT)
class GuiBank(player: EntityPlayer, world: World): GuiScreenToK(player, world)
{
    private var check: Boolean = false

    override fun initGui()
    {
        val center = width / 2
        val dep = center - 95
        val with = center + 5

        this.buttonList.clear()
        this.buttonList.add(GuiButton(1, dep, 55, 90, 20, I18n.format("gui.bank.deposit") + " 1 G"))
        this.buttonList.add(GuiButton(2, dep, 77, 90, 20, I18n.format("gui.bank.deposit") + " 10 G"))
        this.buttonList.add(GuiButton(3, dep, 99, 90, 20, I18n.format("gui.bank.deposit") + " 100 G"))
        this.buttonList.add(GuiButton(4, dep, 121, 90, 20, I18n.format("gui.bank.deposit") + " 1000 G"))
        this.buttonList.add(GuiButton(5, dep, 143, 90, 20, I18n.format("gui.bank.deposit") + " 10000 G"))
        this.buttonList.add(GuiButton(6, dep, 165, 90, 20, I18n.format("gui.bank.deposit.all")))
        this.buttonList.add(GuiButton(7, with, 55, 90, 20, I18n.format("gui.bank.withdraw") + " 1 G"))
        this.buttonList.add(GuiButton(8, with, 77, 90, 20, I18n.format("gui.bank.withdraw") + " 10 G"))
        this.buttonList.add(GuiButton(9, with, 99, 90, 20, I18n.format("gui.bank.withdraw") + " 100 G"))
        this.buttonList.add(GuiButton(10, with, 121, 90, 20, I18n.format("gui.bank.withdraw") + " 1000 G"))
        this.buttonList.add(GuiButton(11, with, 143, 90, 20, I18n.format("gui.bank.withdraw") + " 10000 G"))
        this.buttonList.add(GuiButton(12, with, 165, 90, 20, I18n.format("gui.bank.withdraw.all")))
        this.buttonList.add(GuiButton(13, center - 45, 197, 90, 20, I18n.format("gui.cancel")))
    }

    override fun actionPerformed(button: GuiButton)
    {
        when (button.id)
        {
            1 -> depositGold(1)
            2 -> depositGold(10)
            3 -> depositGold(100)
            4 -> depositGold(1000)
            5 -> depositGold(10000)
            6 -> depositGold(playerProvider.goldTotal.toShort())
            7 -> withdrawGold(1)
            8 -> withdrawGold(10)
            9 -> withdrawGold(100)
            10 -> withdrawGold(1000)
            11 -> withdrawGold(10000)
            12 -> withdrawGold(playerProvider.goldTotal.toShort())
            13 -> {
                if (!world.isRemote)
                {
                    player.addChatMessage(ChatComponentTranslation("npc.banker.dialog.bye"))
                }
                mc.displayGuiScreen(null)
            }
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float)
    {
        this.mc.renderEngine.bindTexture(UltimateHelper.BACKGROUND)
        this.drawTexturedModalRect((this.width - 255) / 2, 0, 0, 0, 255, 255)
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.bank.title"), this.width / 2, 15, 16777215)
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.bank.totalMoney.player", playerProvider.goldTotal), this.width / 2, 25, 16777215)
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.bank.totalMoney.bank", playerProvider.bankGold), this.width / 2, 35, 16777215)

        if (this.check)
            this.drawCenteredString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2, 45, 16777215)

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    private fun depositGold(gold: Short)
    {
        if (playerProvider.goldTotal >= gold)
        {
            NetworkHandler.sendToServer(SPacketBank(gold, true))
            this.check = false
        }
        else
            this.check = true
    }

    private fun withdrawGold(gold: Short)
    {
        if (playerProvider.bankGold >= gold)
        {
            NetworkHandler.sendToServer(SPacketBank(gold, false))
            this.check = false
        }
        else
            this.check = true
    }
}
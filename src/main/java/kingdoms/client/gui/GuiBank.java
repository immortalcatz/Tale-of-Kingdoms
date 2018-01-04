package kingdoms.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.UltimateHelper;
import kingdoms.server.handlers.packets.server.SPacketBank;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public final class GuiBank extends GuiScreenToK
{
    private boolean check = false;

    public GuiBank(EntityPlayer player, World world)
    {
        super(player, world);
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 95, 55, 90, 20, I18n.format("gui.bank.deposit") + " 1 G"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 95, 77, 90, 20, I18n.format("gui.bank.deposit") + " 10 G"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 95, 99, 90, 20, I18n.format("gui.bank.deposit") + " 100 G"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 95, 121, 90, 20, I18n.format("gui.bank.deposit") + " 1000 G"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 95, 143, 90, 20, I18n.format("gui.bank.deposit") + " 10000 G"));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 95, 165, 90, 20, I18n.format("gui.bank.deposit.all")));
        this.buttonList.add(new GuiButton(7, this.width / 2 + 5, 55, 90, 20, I18n.format("gui.bank.withdraw") + " 1 G"));
        this.buttonList.add(new GuiButton(8, this.width / 2 + 5, 77, 90, 20, I18n.format("gui.bank.withdraw") + " 10 G"));
        this.buttonList.add(new GuiButton(9, this.width / 2 + 5, 99, 90, 20, I18n.format("gui.bank.withdraw") + " 100 G"));
        this.buttonList.add(new GuiButton(10, this.width / 2 + 5, 121, 90, 20, I18n.format("gui.bank.withdraw") + " 1000 G"));
        this.buttonList.add(new GuiButton(11, this.width / 2 + 5, 143, 90, 20, I18n.format("gui.bank.withdraw") + " 10000 G"));
        this.buttonList.add(new GuiButton(12, this.width / 2 + 5, 165, 90, 20, I18n.format("gui.bank.withdraw.all")));
        this.buttonList.add(new GuiButton(13, this.width / 2 - 45, 197, 90, 20, I18n.format("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                depositGold((short) 1);
                break;
            case 2:
                depositGold((short) 10);
                break;
            case 3:
                depositGold((short) 100);
                break;
            case 4:
                depositGold((short) 1000);
                break;
            case 5:
                depositGold((short) 10000);
                break;
            case 6:
                depositGold((short) playerProvider.getGoldTotal());
                break;
            case 7:
                withdrawGold((short) 1);
                break;
            case 8:
                withdrawGold((short) 10);
                break;
            case 9:
                withdrawGold((short) 100);
                break;
            case 10:
                withdrawGold((short) 1000);
                break;
            case 11:
                withdrawGold((short) 10000);
                break;
            case 12:
                withdrawGold((short) playerProvider.getBankGold());
                break;
            case 13:
                if (!this.world.isRemote)
                    this.player.addChatMessage(new ChatComponentTranslation("npc.banker.dialog.bye"));

                this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        this.mc.renderEngine.bindTexture(UltimateHelper.BACKGROUND);
        this.drawTexturedModalRect((this.width - 255) / 2, 0, 0, 0, 255, 255);
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.bank.title"), this.width / 2, 15, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.bank.totalMoney.player", playerProvider.getGoldTotal()), this.width / 2, 25, 16777215);
        this.drawCenteredString(this.fontRendererObj, I18n.format("gui.bank.totalMoney.bank", playerProvider.getBankGold()), this.width / 2, 35, 16777215);

        if (this.check)
            this.drawCenteredString(this.fontRendererObj, I18n.format("gui.notEnough"), this.width / 2, 45, 16777215);

        super.drawScreen(i, j, f);
    }

    private void depositGold(short gold)
    {
        if (playerProvider.getGoldTotal() >= gold)
        {
            NetworkHandler.INSTANCE.sendToServer(new SPacketBank(gold, true));
            this.check = false;
        }
        else
            this.check = true;
    }

    private void withdrawGold(short gold)
    {
        if (playerProvider.getBankGold() >= gold)
        {
            NetworkHandler.INSTANCE.sendToServer(new SPacketBank(gold, false));
            this.check = false;
        }
        else
            this.check = true;
    }
}
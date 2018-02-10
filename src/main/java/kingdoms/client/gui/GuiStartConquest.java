package kingdoms.client.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.server.SPacketBuild;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiStartConquest extends GuiScreenToK
{
    private GuiButton start;

    public GuiStartConquest(EntityPlayer player, World world)
    {
        super(player, world);
    }

    @Override
    public void initGui()
    {
        this.buttonList.clear();
        start = new GuiButton(1, this.width / 2 - 70, this.height / 2 + 40, 140, 20, I18n.format("gui.conquest.start"));

        if (getWorldProvider().guild)
        {
            start.enabled = true;
            start.displayString = ChatFormatting.RED + I18n.format("gui.conquest.created");
        }

        this.buttonList.add(start);
        this.buttonList.add(new GuiButton(2, this.width / 2 - 70, this.height / 2 + 70, 140, 20, I18n.format("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 1)
        {
            //TODO: sendToPlayer for individual of the Kingdom
            NetworkHandler.INSTANCE.sendToServer(new SPacketBuild((byte) 0, true));
            this.mc.displayGuiScreen(null);
            this.mc.displayGuiScreen(new GuiToKLoading(getPlayer(), getWorld()));
        }
        else if (guibutton.id == 2)
        {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawString(this.fontRendererObj, ChatFormatting.GOLD + "The Tale of Kingdoms", this.width / 2 - fontRendererObj.getStringWidth("The Tale of Kingdoms") / 2, this.height / 2 - 80, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + I18n.format("gui.startConquest.line_1"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.startConquest.line_1")) / 2, this.height / 2 - 70, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + I18n.format("gui.startConquest.line_2"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.startConquest.line_2")) / 2, this.height / 2 - 60, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + I18n.format("gui.startConquest.line_3"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.startConquest.line_3")) / 2, this.height / 2 - 50, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + I18n.format("gui.startConquest.line_4"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.startConquest.line_4")) / 2, this.height / 2 - 40, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + I18n.format("gui.startConquest.line_5"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.startConquest.line_5")) / 2, this.height / 2, 16777215);
        this.drawString(this.fontRendererObj, ChatFormatting.GRAY + I18n.format("gui.startConquest.line_6"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.startConquest.line_6")) / 2, this.height / 2 + 10, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
package kingdoms.client.gui;

import kingdoms.api.gui.GuiScreenToK;
import kingdoms.server.entities.EntityWorkerMember;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class GuiWorker extends GuiScreenToK
{
    private EntityWorkerMember member;

    public GuiWorker(EntityPlayer player, World world, EntityWorkerMember entityworkermember)
    {
        super(player, world);
        this.member = entityworkermember;
    }

    @Override
    public void initGui()
    {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 45, 75, 90, 20, I18n.format("gui.worker.woodcut")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 45, 97, 90, 20, I18n.format("gui.worker.mine")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 45, 119, 90, 20, I18n.format("gui.cancel")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 1:
                getPlayer().addChatMessage(new ChatComponentTranslation("gui.worker.woodcut.go"));
                EntityWorkerMember.defaultHeldItem = new ItemStack(Items.iron_axe, 1);
                this.member.worktype = 1;
                this.member.follow = true;
                this.mc.displayGuiScreen(null);
                break;
            case 2:
                getPlayer().addChatMessage(new ChatComponentTranslation("gui.worker.mine.go"));
                EntityWorkerMember.defaultHeldItem = new ItemStack(Items.iron_pickaxe, 1);
                this.member.worktype = 2;
                this.member.follow = true;
                this.mc.displayGuiScreen(null);
                break;
            case 3:
                this.mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawString(this.fontRendererObj, I18n.format("gui.worker.title"), this.width / 2 - fontRendererObj.getStringWidth(I18n.format("gui.worker.title")) / 2, 60, 16777215);
    }
}
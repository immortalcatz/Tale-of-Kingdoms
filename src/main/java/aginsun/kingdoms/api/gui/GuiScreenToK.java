package aginsun.kingdoms.api.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class GuiScreenToK extends GuiScreen
{
    protected World world;

    public GuiScreenToK(World world)
    {
        this.world = world;
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    protected void keyTyped(char character, int code)
    {
        if (code == 1 || code == this.mc.gameSettings.keyBindInventory.getKeyCode())
            this.mc.thePlayer.closeScreen();
    }
}
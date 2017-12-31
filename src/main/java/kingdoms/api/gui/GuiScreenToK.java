package kingdoms.api.gui;

import kingdoms.server.PlayerProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public abstract class GuiScreenToK extends GuiScreen
{
    protected World world;
    protected EntityPlayer player;
    protected PlayerProvider provider;

    public GuiScreenToK(EntityPlayer player, World world)
    {
        this.player = player;
        this.world = world;
        this.provider = PlayerProvider.get(player);
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
            player.closeScreen();
    }
}
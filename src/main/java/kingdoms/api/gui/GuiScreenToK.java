package kingdoms.api.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kingdoms.server.PlayerProvider;
import kingdoms.server.WorldProvider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public abstract class GuiScreenToK extends GuiScreen
{
    protected World world;
    protected EntityPlayer player;
    protected PlayerProvider playerProvider;
    protected WorldProvider worldProvider;

    public GuiScreenToK(EntityPlayer player, World world)
    {
        this.player = player;
        this.world = world;
        this.playerProvider = PlayerProvider.get(player);
        this.worldProvider = WorldProvider.get(world);
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
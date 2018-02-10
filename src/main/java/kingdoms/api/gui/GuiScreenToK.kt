package kingdoms.api.gui

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import kingdoms.server.PlayerProvider
import kingdoms.server.WorldProvider
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

@SideOnly(Side.CLIENT)
abstract class GuiScreenToK(protected val player: EntityPlayer, protected val world: World): GuiScreen()
{
    protected val worldProvider: WorldProvider = WorldProvider.get(world)
    protected val playerProvider: PlayerProvider = PlayerProvider.get(player)

    override fun doesGuiPauseGame(): Boolean
    {
        return false
    }

    override fun keyTyped(character: Char, code: Int)
    {
        if (code == 1 || code == this.mc.gameSettings.keyBindInventory.keyCode)
            player.closeScreen()
    }
}
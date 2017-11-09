package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiInnMenu;
import aginsun.kingdoms.api.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class EntityInnKeeper extends EntityNPC
{
    public EntityInnKeeper(World world)
    {
        super(world, null, 100.0F);
        this.isImmuneToFire = false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if(!this.worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("House Keeper: Would you like to take a rest sir?"));
        }

        if(this.canInteractWith(entityplayer))
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.displayGuiScreen(new GuiInnMenu(entityplayer, this.worldObj));
        }
        return true;
    }
}
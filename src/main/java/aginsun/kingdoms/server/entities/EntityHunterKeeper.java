package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiHunter;
import aginsun.kingdoms.api.entities.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityHunterKeeper extends EntityNPC
{
    public EntityHunterKeeper(World world)
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
    protected boolean isMovementCeased()
    {
        return true;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if(this.canInteractWith(entityplayer))
        {
            this.heal(100.0F);
            final Minecraft minecraft = Minecraft.getMinecraft();

            if(!this.worldObj.isRemote)
            {
                entityplayer.addChatMessage(new ChatComponentText("Guild Master: Welcome to the order, hero."));
            }
            minecraft.displayGuiScreen(new GuiHunter(entityplayer, this.worldObj));
        }
        return true;
    }
}
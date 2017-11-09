package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.EntityNPC;
import aginsun.kingdoms.client.gui.GuiLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityLibraryKeeper extends EntityNPC
{
    private int counter = 0;
    public boolean studied = true;

    public EntityLibraryKeeper(World world)
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
    protected void updateEntityActionState()
    {
        super.updateEntityActionState();

        if(this.counter > 10000)
        {
            this.studied = true;
            this.counter = 0;
        }
        ++this.counter;
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
                entityplayer.addChatMessage(new ChatComponentText("Librarian: You picked a good day to visit the library, young one"));
            }
            minecraft.displayGuiScreen(new GuiLibrary(entityplayer, this.worldObj, this));
        }
        return true;
    }
}
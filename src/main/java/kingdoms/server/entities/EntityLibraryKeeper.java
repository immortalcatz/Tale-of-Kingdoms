package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.handlers.NetworkHandler;
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

        if (this.counter > 10000)
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
    public boolean interact(EntityPlayer player)
    {
        if (!this.worldObj.isRemote)
        {
            if (this.canInteractWith(player))
            {
                this.heal(100.0F);
                player.addChatMessage(new ChatComponentText("Librarian: You picked a good day to visit the library, young one"));
                NetworkHandler.INSTANCE.openGui(this.getEntityId(), player);
            }
        }
        return true;
    }
}
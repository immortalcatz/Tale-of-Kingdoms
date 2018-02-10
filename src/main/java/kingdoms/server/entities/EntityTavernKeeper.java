package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.handlers.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public final class EntityTavernKeeper extends EntityNPC
{
    public EntityTavernKeeper(World world)
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
    public boolean interact(EntityPlayer player)
    {
        if (!worldObj.isRemote)
        {
            if (this.canInteractWith(player))
            {
                player.addChatMessage(new ChatComponentTranslation("npc.tavernKeeper.dialog"));
                NetworkHandler.INSTANCE.openGui(this.getEntityId(), player);
            }
        }
        return true;
    }
}
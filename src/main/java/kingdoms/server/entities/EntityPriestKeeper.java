package kingdoms.server.entities;

import kingdoms.server.handlers.NetworkHandler;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public final class EntityPriestKeeper extends EntityCreature
{
    public EntityPriestKeeper(World world)
    {
        super(world);
        this.isImmuneToFire = false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return !this.isDead && entityplayer.getDistanceSqToEntity(this) <= 64.0D;
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
                this.heal(100.0F);
                player.addChatMessage(new ChatComponentTranslation("npc.headPriest.dialog"));
                NetworkHandler.INSTANCE.openGui(this.getEntityId(), player);
            }
        }
        return true;
    }
}
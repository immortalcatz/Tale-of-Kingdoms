package kingdoms.server.entities;

import kingdoms.server.handlers.NetworkHandler;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public final class EntityLumber extends EntityCreature
{
    public EntityLumber(World world)
    {
        super(world);
        this.isImmuneToFire = false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    public boolean canInteractWith(EntityPlayer player)
    {
        return !this.isDead || player.getDistanceSqToEntity(this) <= 64.0D;
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
        if (!this.worldObj.isRemote)
        {
            if (this.canInteractWith(player))
            {
                this.heal(100.0F);
                player.addChatMessage(new ChatComponentTranslation("npc.foreman.dialog"));
                NetworkHandler.INSTANCE.openGui(this.getEntityId(), player);
            }
        }
        return true;
    }
}
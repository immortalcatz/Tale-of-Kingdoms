package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.handlers.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public final class EntityMageKeeper extends EntityNPC
{
    public EntityMageKeeper(World world)
    {
        super(world, new ItemStack(Items.stick), 100.0F);
        this.isImmuneToFire = true;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
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
                player.addChatMessage(new ChatComponentTranslation("npc.headMage.dialog"));
                NetworkHandler.INSTANCE.openGui(this.getEntityId(), player);
            }
        }
        return true;
    }
}
package kingdoms.server.entities

import kingdoms.api.entities.EntityNPC
import kingdoms.server.handlers.NetworkHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class EntityFisher(world: World): EntityNPC(world, ItemStack(Items.fishing_rod), 40.0F)
{
    override fun canBePushed(): Boolean
    {
        return false
    }

    override fun isMovementCeased(): Boolean
    {
        return true
    }

    override fun interact(player: EntityPlayer): Boolean
    {
        if (!worldObj.isRemote)
        {
            if (this.canInteractWith(player))
            {
                NetworkHandler.openGui(this.entityId, player)
            }
        }
        return true
    }
}
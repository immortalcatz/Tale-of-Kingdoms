package kingdoms.server.entities

import kingdoms.api.entities.EntityNPC
import kingdoms.server.handlers.NetworkHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentText
import net.minecraft.world.World

class EntityHunterKeeper(world: World): EntityNPC(world, null, 100.0F)
{
    init
    {
        this.isImmuneToFire = false
    }

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
                this.heal(100.0F)
                player.addChatMessage(ChatComponentText("Guild Master: Welcome to the order, hero."))
                NetworkHandler.openGui(this.entityId, player)
            }
        }
        return true
    }
}
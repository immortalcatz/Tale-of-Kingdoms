package kingdoms.server.entities

import kingdoms.api.entities.EntityNPC
import kingdoms.server.handlers.NetworkHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.world.World

class EntityInnKeeper(world: World): EntityNPC(world, null, 100.0F)
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
                player.addChatMessage(ChatComponentTranslation("npc.inn.dialog"))
                NetworkHandler.openGui(this.entityId, player)
            }
        }
        return true
    }
}
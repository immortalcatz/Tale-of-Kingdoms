package kingdoms.server.entities

import kingdoms.api.entities.EntityNPC
import kingdoms.server.handlers.NetworkHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.world.World

class EntityBankerKeeper(world: World): EntityNPC(world, ItemStack(Items.book), 40.0F)
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
                this.heal(100.0F)
                player.addChatMessage(ChatComponentTranslation("npc.banker.dialog.welcome"))
                NetworkHandler.openGui(this.entityId, player)
            }
        }
        return false
    }
}
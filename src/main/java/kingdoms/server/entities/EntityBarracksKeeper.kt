package kingdoms.server.entities

import kingdoms.api.entities.EntityNPC
import kingdoms.server.handlers.NetworkHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentTranslation
import net.minecraft.world.World

class EntityBarracksKeeper(world: World): EntityNPC(world, ItemStack(Items.iron_sword), 20.0F)
{
    override fun canBePushed(): Boolean
    {
        return false
    }

    override fun interact(player: EntityPlayer): Boolean
    {
        if (!worldObj.isRemote)
        {
            if (this.canInteractWith(player))
            {
                player.addChatMessage(ChatComponentTranslation("npc.warden.dialog"))
                NetworkHandler.openGui(this.entityId, player)
            }
        }
        return true
    }
}
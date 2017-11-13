package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityBarracksKeeper extends EntityNPC
{
    public EntityBarracksKeeper(World world)
    {
        super(world, new ItemStack(Items.iron_sword), 20.0F);
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if(this.canInteractWith(player))
        {
            if(!this.worldObj.isRemote)
            {
                player.addChatMessage(new ChatComponentText(I18n.format("npc.warden.dialog")));
            }
            player.openGui(TaleOfKingdoms.instance, 2, worldObj, 0, 0, 0);
        }
        return true;
    }
}
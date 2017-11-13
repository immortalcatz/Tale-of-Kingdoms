package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public final class EntityBankerKeeper extends EntityNPC
{
    public EntityBankerKeeper(World world)
    {
        super(world, new ItemStack(Items.book), 40.0F);
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
    public boolean interact(EntityPlayer entityplayer)
    {
        if (this.canInteractWith(entityplayer))
        {
            if (!this.worldObj.isRemote)
            {
                this.heal(100.0F);
                entityplayer.addChatMessage(new ChatComponentTranslation("npc.banker.dialog.welcome"));
            }
            entityplayer.openGui(TaleOfKingdoms.instance, 0, worldObj, 0, 0, 0);
        }
        return false;
    }
}
package kingdoms.server.entities;

import kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import static kingdoms.server.handlers.GuiHandler.GUI_QUARRY;

public final class EntityQuarry extends EntityCreature
{
    public EntityQuarry(World world)
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
        if (this.canInteractWith(player))
        {
            if (!this.worldObj.isRemote)
            {
                this.heal(100.0F);
                player.addChatMessage(new ChatComponentTranslation("npc.foreman.dialog"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_QUARRY, worldObj, 0, 0, 0);
        }
        return true;
    }
}
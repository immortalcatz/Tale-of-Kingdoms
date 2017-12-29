package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import static kingdoms.server.handlers.GuiHandler.GUI_INN;

public class EntityInnKeeper extends EntityNPC
{
    public EntityInnKeeper(World world)
    {
        super(world, null, 100.0F);
        this.isImmuneToFire = false;
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
                player.addChatMessage(new ChatComponentTranslation("npc.inn.dialog"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_INN, worldObj, 0, 0, 0);
        }
        return true;
    }
}
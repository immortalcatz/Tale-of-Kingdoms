package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_HUNTER;

public final class EntityHunterKeeper extends EntityNPC
{
    public EntityHunterKeeper(World world)
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
                this.heal(100.0F);
                player.addChatMessage(new ChatComponentText("Guild Master: Welcome to the order, hero."));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_HUNTER, worldObj, 0, 0, 0);
        }
        return true;
    }
}
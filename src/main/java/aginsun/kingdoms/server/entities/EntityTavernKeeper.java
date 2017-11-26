package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_TAVERN;

public final class EntityTavernKeeper extends EntityNPC
{
    public EntityTavernKeeper(World world)
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
            if (!worldObj.isRemote)
            {
                player.addChatMessage(new ChatComponentTranslation("npc.tavernKeeper.dialog"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_TAVERN, worldObj, 0, 0, 0);
        }
        return true;
    }
}
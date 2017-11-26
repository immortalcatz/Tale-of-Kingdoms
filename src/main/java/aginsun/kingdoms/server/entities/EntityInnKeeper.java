package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_INN;

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
    public boolean interact(EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            if (!this.worldObj.isRemote)
            {
                player.addChatMessage(new ChatComponentText("House Keeper: Would you like to take a rest sir?"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_INN, worldObj, 0, 0, 0);
        }
        return true;
    }
}
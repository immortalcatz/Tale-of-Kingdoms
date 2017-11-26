package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_STABLE;

public final class EntityStableMaster extends EntityNPC
{
    public EntityStableMaster(World world)
    {
        super(world, new ItemStack(Items.lead), 40.0F);
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
    public boolean interact(final EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            player.openGui(TaleOfKingdoms.instance, GUI_STABLE, worldObj, 0, 0, 0);
        }
        return true;
    }
}
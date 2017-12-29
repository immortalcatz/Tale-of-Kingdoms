package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static kingdoms.server.handlers.GuiHandler.GUI_FARMER;

public final class EntityFarmerKeeper extends EntityNPC
{
    public EntityFarmerKeeper(World world)
    {
        super(world, new ItemStack(Items.iron_hoe), 100.0F);
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
            player.openGui(TaleOfKingdoms.instance, GUI_FARMER, worldObj, 0, 0, 0);
        }
        return true;
    }
}
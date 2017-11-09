package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.EntityNPC;
import aginsun.kingdoms.client.gui.GuiFoodKeeper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
    public boolean interact(EntityPlayer entityplayer)
    {
        if(this.canInteractWith(entityplayer))
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiFoodKeeper(entityplayer, this.worldObj));
        }
        return true;
    }
}
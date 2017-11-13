package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.client.gui.GuiFisher;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class EntityFisher extends EntityNPC
{
    public EntityFisher(World par1World)
    {
        super(par1World, new ItemStack(Items.fishing_rod), 40.0F);
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
            Minecraft.getMinecraft().displayGuiScreen(new GuiFisher(entityplayer));
        }
        return true;
    }
}
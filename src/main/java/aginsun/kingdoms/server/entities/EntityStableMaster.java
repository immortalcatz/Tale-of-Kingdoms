package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiStableMaster;
import aginsun.kingdoms.api.entities.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
    public boolean interact(final EntityPlayer entityplayer)
    {
        if(this.canInteractWith(entityplayer))
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiStableMaster(entityplayer, entityplayer.worldObj));
        }
        return true;
    }
}
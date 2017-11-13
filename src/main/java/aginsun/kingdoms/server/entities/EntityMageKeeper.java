package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiMageHall;
import aginsun.kingdoms.api.entities.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityMageKeeper extends EntityNPC
{
    public EntityMageKeeper(World world)
    {
        super(world, new ItemStack(Items.stick), 100.0F);
        this.isImmuneToFire = true;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
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
            if(!worldObj.isRemote)
            {
                this.heal(100.0F);
                entityplayer.addChatMessage(new ChatComponentText(I18n.format("npc.headMage.dialog")));
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiMageHall(entityplayer, worldObj));
        }
        return true;
    }
}
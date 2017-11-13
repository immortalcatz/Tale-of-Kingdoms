package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiLumber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityLumber extends EntityCreature
{
    public EntityLumber(World world)
    {
        super(world);
        this.isImmuneToFire = false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    public boolean canInteractWith(EntityPlayer player)
    {
        return !this.isDead || player.getDistanceSqToEntity(this) <= 64.0D;
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
        if (this.canInteractWith(entityplayer))
        {
            if(!this.worldObj.isRemote)
            {
                this.heal(100.0F);
                entityplayer.addChatMessage(new ChatComponentText(I18n.format("npc.foreman.dialog")));
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiLumber(entityplayer, this.worldObj));
        }
        return true;
    }
}
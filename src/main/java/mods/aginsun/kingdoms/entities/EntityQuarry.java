package mods.aginsun.kingdoms.entities;

import mods.aginsun.kingdoms.client.guis.GuiQuarry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityQuarry extends EntityCreature
{
    public EntityQuarry(World world)
    {
        super(world);
        this.isImmuneToFire = false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return !this.isDead && entityplayer.getDistanceSqToEntity(this) <= 64.0D;
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
            this.heal(100.0F);
            final Minecraft minecraft = Minecraft.getMinecraft();

            if(!this.worldObj.isRemote)
            {
                entityplayer.addChatMessage(new ChatComponentText(I18n.format("npc.foreman.dialog")));
            }
            minecraft.displayGuiScreen(new GuiQuarry(entityplayer, this.worldObj));
        }
        return true;
    }
}
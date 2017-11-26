package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiPriest;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_PRIEST;

public final class EntityPriestKeeper extends EntityCreature
{
    public EntityPriestKeeper(World world)
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
    public boolean interact(EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            if (!this.worldObj.isRemote)
            {
                this.heal(100.0F);
                player.addChatMessage(new ChatComponentText(I18n.format("npc.headPriest.dialog")));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_PRIEST, worldObj, 0, 0, 0);
        }
        return true;
    }
}
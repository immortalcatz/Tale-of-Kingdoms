package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import static kingdoms.server.handlers.GuiHandler.GUI_MAGEHALL;

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
    public boolean interact(EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            if (!worldObj.isRemote)
            {
                this.heal(100.0F);
                player.addChatMessage(new ChatComponentTranslation("npc.headMage.dialog"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_MAGEHALL, worldObj, 0, 0, 0);
        }
        return true;
    }
}
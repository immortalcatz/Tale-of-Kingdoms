package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_BANK;

public final class EntityBankerKeeper extends EntityNPC
{
    public EntityBankerKeeper(World world)
    {
        super(world, new ItemStack(Items.BOOK), 40.0F);
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected boolean isMovementBlocked()
    {
        return true;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.canInteractWith(player))
        {
            if (!this.world.isRemote)
            {
                this.heal(100.0F);
                player.sendMessage(new TextComponentString("npc.banker.dialog.welcome"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_BANK, world, 0, 0, 0);
        }
        return true;
    }
}
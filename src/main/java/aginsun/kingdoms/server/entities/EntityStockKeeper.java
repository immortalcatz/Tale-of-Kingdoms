package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_STOCK;

public final class EntityStockKeeper extends EntityNPC
{
    public EntityStockKeeper(World world)
    {
        super(world, null, 100.0F);
        this.isImmuneToFire = false;
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
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.canInteractWith(player))
        {
            if (!this.world.isRemote)
            {
                this.heal(100.0F);
                player.sendMessage(new TextComponentString("npc.stockKeeper.dialog"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_STOCK, world, 0, 0, 0);
        }
        return true;
    }
}
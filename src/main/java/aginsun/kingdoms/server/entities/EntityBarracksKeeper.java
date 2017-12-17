package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_WARDEN;

public final class EntityBarracksKeeper extends EntityNPC
{
    public EntityBarracksKeeper(World world)
    {
        super(world, new ItemStack(Items.IRON_SWORD), 20.0F);
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.canInteractWith(player))
        {
            if (!this.world.isRemote)
            {
                player.sendMessage(new TextComponentString("npc.warden.dialog"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_WARDEN, world, 0, 0, 0);
        }
        return true;
    }
}
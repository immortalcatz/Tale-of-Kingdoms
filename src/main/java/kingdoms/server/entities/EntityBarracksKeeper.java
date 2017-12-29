package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import static kingdoms.server.handlers.GuiHandler.GUI_WARDEN;

public final class EntityBarracksKeeper extends EntityNPC
{
    public EntityBarracksKeeper(World world)
    {
        super(world, new ItemStack(Items.iron_sword), 20.0F);
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            if (!this.worldObj.isRemote)
            {
                player.addChatMessage(new ChatComponentTranslation("npc.warden.dialog"));
            }
            player.openGui(TaleOfKingdoms.instance, GUI_WARDEN, worldObj, 0, 0, 0);
        }
        return true;
    }
}
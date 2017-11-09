package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiWardenMenu;
import aginsun.kingdoms.api.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

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
    public boolean interact(EntityPlayer entityplayer)
    {
        if(!this.world.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText(I18n.format("npc.warden.dialog")));
        }

        if(this.canInteractWith(entityplayer))
        {
            Minecraft.getMinecraft().displayGuiScreen(new GuiWardenMenu(entityplayer, this.worldObj));
        }
        return true;
    }
}
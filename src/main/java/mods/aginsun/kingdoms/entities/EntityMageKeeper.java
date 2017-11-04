package mods.aginsun.kingdoms.entities;

import mods.aginsun.kingdoms.client.guis.GuiMageHall;
import mods.aginsun.kingdoms.entities.api.EntityNPC;
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
            this.heal(100.0F);
            if(!this.world.isRemote)
            {
                entityplayer.addChatMessage(new ChatComponentText(I18n.format("npc.headMage.dialog")));
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiMageHall(entityplayer, this.world));
        }
        return true;
    }
}
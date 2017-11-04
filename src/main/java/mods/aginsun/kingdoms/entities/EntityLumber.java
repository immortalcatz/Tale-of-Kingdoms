package mods.aginsun.kingdoms.entities;

import cpw.mods.fml.common.FMLCommonHandler;
import mods.aginsun.kingdoms.client.guis.GuiLumber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityLumber extends EntityCreature
{
    private World field_70170_p = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

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
            this.heal(100.0F);
            if(!this.field_70170_p.isRemote)
            {
                entityplayer.addChatMessage(new ChatComponentText(I18n.format("npc.foreman.dialog")));
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiLumber(entityplayer, this.field_70170_p));
        }
        return true;
    }
}
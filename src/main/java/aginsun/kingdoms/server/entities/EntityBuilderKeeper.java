package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.handlers.Buildings;
import aginsun.kingdoms.server.handlers.resources.GloryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import static aginsun.kingdoms.server.handlers.GuiHandler.GUI_BUILD;

public final class EntityBuilderKeeper extends EntityNPC
{
    private boolean follow = true, setted = false;

    public EntityBuilderKeeper(World world)
    {
        super(world, new ItemStack(Items.stone_axe), 100.0F);
        this.isImmuneToFire = false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return this.follow;
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            if (GloryHandler.INSTANCE.getGlory() < 10000 && !Buildings.INSTANCE.kingdomCreated)
            {
                if (!worldObj.isRemote)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_1"));
                }
            }

            if (!this.follow || setted || GloryHandler.INSTANCE.getGlory() >= 10000.0F && Buildings.INSTANCE.kingdomCreated)
            {
                if (!worldObj.isRemote)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_2"));
                    this.follow = true;
                    this.setted = true;
                }
                player.openGui(TaleOfKingdoms.instance, GUI_BUILD, worldObj, 0, 0, 0);
            }
            else if (this.canInteractWith(player) && GloryHandler.INSTANCE.getGlory() >= 10000.0F && this.follow && !Buildings.INSTANCE.kingdomCreated && !setted)
            {
                if (!worldObj.isRemote)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_3"));
                    this.follow = false;
                }
            }
        }
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!this.follow && !Buildings.INSTANCE.kingdomCreated)
        {
            /*EntityPlayer player = Minecraft.getMinecraft().thePlayer;

            if (player != null)
            {
                PathEntity pathentity;

                if (player.getDistanceToEntity(this) > 3.5F && player.getDistanceToEntity(this) < 25.0F)
                {
                    pathentity = worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
                }
                else
                {
                    pathentity = null;
                }
                this.setPathToEntity(pathentity);
            }*/
        }
    }
}

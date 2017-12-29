package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.PlayerProvider;
import kingdoms.server.TaleOfKingdoms;
import kingdoms.server.handlers.Buildings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

import static kingdoms.server.handlers.GuiHandler.GUI_BUILD;

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
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("follow", follow);
        compound.setBoolean("setted", setted);
        nbtTagCompound.setTag("builderData", compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        NBTTagCompound compound = nbtTagCompound.getCompoundTag("builderData");
        follow = compound.getBoolean("follow");
        setted = compound.getBoolean("setted");
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            if (PlayerProvider.get(player).getGlory() < 10000 && !Buildings.INSTANCE.kingdomCreated)
            {
                if (!worldObj.isRemote)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_1"));
                }
            }

            if (!this.follow || setted || PlayerProvider.get(player).getGlory() >= 10000.0F)
            {
                if (!worldObj.isRemote)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_2"));
                    this.follow = true;
                    this.setted = true;
                }
                player.openGui(TaleOfKingdoms.instance, GUI_BUILD, worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
            }
            else if (this.canInteractWith(player) && PlayerProvider.get(player).getGlory() >= 10000.0F && this.follow && !Buildings.INSTANCE.kingdomCreated && !setted)
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
        if (!worldObj.isRemote && !this.follow && !Buildings.INSTANCE.kingdomCreated)
        {
            List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

            for (Entity entity : list)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;

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
                }
            }
        }
    }
}

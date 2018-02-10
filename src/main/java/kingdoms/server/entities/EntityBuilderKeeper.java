package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.PlayerProvider;
import kingdoms.server.handlers.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

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
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setBoolean("follow", follow);
        compound.setBoolean("setted", setted);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        follow = compound.getBoolean("follow");
        setted = compound.getBoolean("setted");
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (!worldObj.isRemote)
        {
            if (this.canInteractWith(player))
            {
                int glory = PlayerProvider.get(player).getGlory();

                if (glory < 10000 && !getWorldProvider().town)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_1"));
                }

                if (glory >= 10000.0F && follow && !setted)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_2"));
                    this.follow = true;
                    this.setted = true;
                    NetworkHandler.INSTANCE.openGui(this.getEntityId(), player);
                }
                else if (glory >= 10000.0F && follow && !getWorldProvider().town && !setted)
                {
                    player.addChatMessage(new ChatComponentTranslation("npc.cityBuilder.dialog_3"));
                    this.follow = false;
                }
            }
        }
        return true;
    }

    @Override
    protected void updateEntityActionState()
    {
        super.updateEntityActionState();

        if (!worldObj.isRemote)
        {
            if (!this.follow && !getWorldProvider().town && !setted)
            {
                this.setAIMoveSpeed(1.7F);
            }
        }
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!worldObj.isRemote && !this.follow && !getWorldProvider().town)
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

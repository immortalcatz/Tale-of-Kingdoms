package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public final class EntityLostVillager extends EntityNPC
{
    private boolean follow = true;

    public EntityLostVillager(World world)
    {
        super(world, null, 20.0F);
        this.isImmuneToFire = true;
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
            if (!this.worldObj.isRemote)
            {
                this.follow = false;
                player.addChatMessage(new ChatComponentText("Lost Villager: Thank the heavens! Our village is attacked by the reficuls! Please lead me back to the guild."));
            }
        }
        return true;
    }

    @Override
    protected void jump()
    {
        if (!this.follow)
        {
            this.motionY = 0.41999998688697815D;

            if (this.isSprinting())
            {
                float f = this.rotationYaw * 0.01745329F;
                this.motionX -= (double)(MathHelper.sin(f) * 0.2F);
                this.motionZ += (double)(MathHelper.cos(f) * 0.2F);
            }
            this.isAirBorne = true;
        }
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.follow)
        {
            List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

            for (Entity entity : list)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;

                    PathEntity pathentity;

                    if (player.getDistanceToEntity(this) > 5.0F && player.getDistanceToEntity(this) < 18.0F)
                    {
                        pathentity = this.worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
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
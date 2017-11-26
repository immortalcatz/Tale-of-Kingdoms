package aginsun.kingdoms.server.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public final class EntitySlash extends EntityBlaze
{
    private int counter = 0, speed = 200;
    public EntityPlayer entityplayer;
    private boolean explode = false, surround = false;
    private double range = 0.0D;

    public EntitySlash(World world)
    {
        super(world);
    }

    @Override
    public void onUpdate()
    {
        this.counter += this.speed;

        if (this.counter > 200)
        {
            this.setDead();
        }

        if (this.counter % 2 == 0 && this.explode && this.speed != 200)
        {
            this.worldObj.newExplosion(null, this.posX, this.posY + 1.0D, this.posZ, 2.0F, true, false);
        }

        if (this.counter % 2 == 0 && this.surround)
        {
            List list = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(this.range, this.range, this.range));

            if(!list.isEmpty())
            {
                boolean flag = true;
                EntityLivingBase entityliving = (EntityLivingBase)list.get(this.worldObj.rand.nextInt(list.size()));

                if(entityliving instanceof EntityPlayer || entityliving instanceof EntitySlash)
                {
                    flag = false;
                }

                if(this.entityplayer != null && flag)
                {
                    this.worldObj.newExplosion(this.entityplayer, entityliving.posX, entityliving.posY + 1.0D, entityliving.posZ, 1.0F, true, false);
                }
            }
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f) {}

    @Override
    protected void fall(float f) {}

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

    @Override
    protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.air);
    }

    @Override
    public boolean isBurning()
    {
        return false;
    }

    protected void dropFewItems(boolean flag, int i) {}
}
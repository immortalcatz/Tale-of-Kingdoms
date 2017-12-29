package kingdoms.server.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public final class EntityMarkerKeeper extends Entity
{
    private int treelife = 8;

    public EntityMarkerKeeper(World world1)
    {
        super(world1);
        this.setSize(5.0E-6F, 5.0E-6F);
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public void entityInit()
    {
        --this.treelife;
        this.onLivingUpdate();
    }

    public void onLivingUpdate()
    {
        int i = (int) this.posY, j = (int) this.posY - 1;

        if (this.treelife == 0)
        {
            List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

            for (Entity entity : list)
            {
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer) entity;

                    while (this.worldObj.getBlock((int) this.posX, i, (int) this.posZ) == Block.getBlockById(17) && player != null)
                    {
                        this.worldObj.setBlock((int) this.posX, i, (int) this.posZ, Blocks.air);
                        ItemStack k = new ItemStack(Item.getItemById(17), 1, 0);
                        EntityItem itemstack1 = new EntityItem(this.worldObj, player.posX, player.posY, player.posZ, k);
                        this.worldObj.spawnEntityInWorld(itemstack1);
                        k = new ItemStack(Item.getItemById(17), 1, 0);
                        itemstack1 = new EntityItem(this.worldObj, player.posX, player.posY, player.posZ, k);
                        this.worldObj.spawnEntityInWorld(itemstack1);
                        ++i;
                    }

                    for (int var6 = j; this.worldObj.getBlock((int) this.posX, var6, (int) this.posZ) == Block.getBlockById(17) && player != null; --var6)
                    {
                        this.worldObj.setBlock((int) this.posX, var6, (int) this.posZ, Blocks.air);
                        ItemStack var7 = new ItemStack(Item.getItemById(17), 1, 0);
                        EntityItem entityitem1 = new EntityItem(this.worldObj, player.posX, player.posY, player.posZ, var7);
                        this.worldObj.spawnEntityInWorld(entityitem1);
                        var7 = new ItemStack(Item.getItemById(17), 1, 0);
                        entityitem1 = new EntityItem(this.worldObj, player.posX, player.posY, player.posZ, var7);
                        this.worldObj.spawnEntityInWorld(entityitem1);
                    }
                }
            }
            this.setDead();
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {}
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {}
}
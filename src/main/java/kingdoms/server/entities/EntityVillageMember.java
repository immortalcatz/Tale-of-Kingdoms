package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public final class EntityVillageMember extends EntityNPC
{
    private boolean hasAxe = false, hasPick = false;

    public EntityVillageMember(World world)
    {
        super(world, null, 30.0F);
        this.isImmuneToFire = false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (itemstack != null && !this.hasAxe && (itemstack.getItem() == Items.wooden_axe || itemstack.getItem() == Items.stone_axe || itemstack.getItem() == Items.iron_axe))
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            this.hasAxe = true;
        }

        if (itemstack != null && !this.hasPick && (itemstack.getItem() == Items.iron_pickaxe || itemstack.getItem() == Items.wooden_pickaxe || itemstack.getItem() == Items.stone_pickaxe))
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            this.hasPick = true;
        }

        if (!this.hasPick && !this.hasAxe && !worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("Villager: My king! Do you need a worker? Give me an axe and a pickaxe and I will work for you."));
        }

        if (!this.hasPick && this.hasAxe && !worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("Villager: I still need a pickaxe sir."));
        }

        if (this.hasPick && !this.hasAxe && !worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("Villager: I still need an axe sir."));
        }

        if (this.hasPick && this.hasAxe && !worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("Worker: I am now a worker sir! Lead the way!"));
            EntityLiving entityliving = (EntityLiving) UltimateHelper.INSTANCE.getEntity("WorkerMember", this.worldObj);
            entityliving.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            worldObj.spawnEntityInWorld(entityliving);
            this.setDead();
        }
        return true;
    }

    @Override
    protected void jump()
    {
        final Random random = new Random();

        if (random.nextInt(15) == 0)
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
}
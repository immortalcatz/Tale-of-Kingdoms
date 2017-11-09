package aginsun.kingdoms.server.entities;

import java.util.List;

import aginsun.kingdoms.api.EntityNPC;
import aginsun.kingdoms.server.handlers.resources.WorthyKeeper;
import aginsun.kingdoms.server.handlers.UtilToK;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityLoneTraveller extends EntityNPC
{
    private static ItemStack defaultHeldItem = new ItemStack(Items.iron_sword, 1);

    public EntityLoneTraveller(World world)
    {
        super(world, defaultHeldItem, 20.0F);
        this.isImmuneToFire = true;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return true;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        boolean flag1 = false;
        List list = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(16.0D, 4.0D, 16.0D));

        if(!list.isEmpty())
        {
            for (Object aList : list)
            {
                Entity entity = (Entity) aList;
                if (this.canEntityBeSeen(entity) && entity instanceof EntityLostVillager)
                {
                    entity.setDead();
                    WorthyKeeper.getInstance().addWorthy(400.0F);
                    flag1 = true;
                }
            }
        }

        if(flag1 && !this.worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("Survivor: My king! Thank you for saving them! I will let the guild master know your efforts"));
        }
        else if(!this.worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("Survivor: I am gravely lost, my king. I survived the attack but many of our villages burned down. There are still survivors left, hurry and rescue them!"));
            entityplayer.addChatMessage(new ChatComponentText("Guild Master: Your quest has started, find the village and save them!"));
        }

        UtilToK.burningVillages = 1;
        return true;
    }
}
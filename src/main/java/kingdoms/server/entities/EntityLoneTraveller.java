package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.PlayerProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

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
    public boolean interact(EntityPlayer player)
    {
        boolean flag1 = false;
        List list = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(16.0D, 4.0D, 16.0D));

        if (!list.isEmpty())
        {
            for (Object aList : list)
            {
                Entity entity = (Entity) aList;

                if (this.canEntityBeSeen(entity) && entity instanceof EntityLostVillager)
                {
                    entity.setDead();
                    PlayerProvider.get(player).addGlory(400);
                    flag1 = true;
                }
            }
        }

        if (flag1 && !this.worldObj.isRemote)
        {
            player.addChatMessage(new ChatComponentText("Survivor: My king! Thank you for saving them! I will let the guild master know your efforts"));
        }
        else if (!this.worldObj.isRemote)
        {
            player.addChatMessage(new ChatComponentText("Survivor: I am gravely lost, my king. I survived the attack but many of our villages burned down. There are still survivors left, hurry and rescue them!"));
            player.addChatMessage(new ChatComponentText("Guild Master: Your quest has started, find the village and save them!"));
        }

        PlayerProvider.get(player).burningVillages = 1;
        return true;
    }
}
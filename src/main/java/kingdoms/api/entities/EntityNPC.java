package kingdoms.api.entities;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityNPC extends EntityCreature
{
    private ItemStack defaultHeldItem;

    public EntityNPC(World world, ItemStack defaultHeldItem, float i)
    {
        super(world);
        this.setHealth(i);
        this.defaultHeldItem = defaultHeldItem;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }

    @Override
    public ItemStack getHeldItem()
    {
        return this.defaultHeldItem;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public boolean canBePushed()
    {
        return true;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return false;
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return !this.isDead || entityplayer.getDistanceSqToEntity(this) <= 64.0D;
    }
}
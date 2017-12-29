package kingdoms.server.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public final class EntityDefendMarker extends Entity
{
    public EntityDefendMarker(World world)
    {
        super(world);
        this.setSize(5.0E-6F, 5.0E-6F);
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public void entityInit() {}

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {}

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {}
}
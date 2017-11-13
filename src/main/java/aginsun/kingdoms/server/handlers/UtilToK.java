package aginsun.kingdoms.server.handlers;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

@Deprecated
public final class UtilToK
{
    public static final UtilToK INSTANCE = new UtilToK();

    public static void spawnEntity(World world, String name, ChunkCoordinates position)
    {
        if (!world.isRemote)
        {
            EntityLivingBase entityliving = (EntityLivingBase)EntityList.createEntityByName("taleofkingdoms." + name, world);
            entityliving.setLocationAndAngles((double) position.posX, (double) position.posY, (double) position.posZ, 0.0F, 0.0F);
            world.spawnEntityInWorld(entityliving);
        }
    }
}
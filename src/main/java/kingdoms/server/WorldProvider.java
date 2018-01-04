package kingdoms.server;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public final class WorldProvider extends WorldSavedData
{
    public int guildPosX, guildPosY, guildPosZ;

    public WorldProvider(String name)
    {
        super(name);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound world)
    {
        NBTTagCompound compound = world.getCompoundTag("guild");
        guildPosX = compound.getInteger("guildPosX");
        guildPosY = compound.getInteger("guildPosY");
        guildPosZ = compound.getInteger("guildPosZ");
    }

    @Override
    public void writeToNBT(NBTTagCompound world)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("guildPosX", guildPosX);
        compound.setInteger("guildPosY", guildPosY);
        compound.setInteger("guildPosZ", guildPosZ);
        world.setTag("guild", compound);
    }

    public static WorldProvider get(World world)
    {
        MapStorage storage = world.mapStorage;
        WorldProvider instance = (WorldProvider) storage.loadData(WorldProvider.class, "tokWorld");

        if (instance == null)
        {
            instance = new WorldProvider("tokWorld");
            instance.markDirty();
            storage.setData("tokWorld", instance);
        }

        return instance;
    }
}
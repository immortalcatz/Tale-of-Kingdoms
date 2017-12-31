package kingdoms.server;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public final class WorldProvider extends WorldSavedData
{
    public WorldProvider()
    {
        super("tokWorld");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {

    }

    public static WorldProvider get(World world)
    {
        MapStorage storage = world.mapStorage;
        WorldProvider instance = (WorldProvider) storage.loadData(WorldProvider.class, "tokWorld");

        if (instance == null)
        {
            instance = new WorldProvider();
            storage.setData("tokWorld", instance);
        }

        return instance;
    }
}
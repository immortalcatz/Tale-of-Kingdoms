package kingdoms.server;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public final class WorldProvider extends WorldSavedData
{
    public int townPosX, townPosY, townPosZ;
    public boolean
            guild, town, smallhouse1, smallhouse2, largehouse1, well, itemshop, stockmarket, isTier2,
            smallhouse3, smallhouse4, largehouse2, builderhouse, barracks, foodshop, blockshop, isTier3, smallhouse5,
            smallhouse6, smallhouse7, largehouse3, tavern, chapel, library, magehall, isTier4, bridge, castle, colloseum,
            easternTower, fishHut, lightHouse, mill, observerPost, smallhouse8, smallhouse9, smallhouse10, smallhouse11,
            largehouse4, northernTower1, northernTower2, stables, zeppelin;

    public WorldProvider(String name)
    {
        super(name);
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound world)
    {
        NBTTagCompound compound = world.getCompoundTag("town");
        townPosX = compound.getInteger("townPosX");
        townPosY = compound.getInteger("townPosY");
        townPosZ = compound.getInteger("townPosZ");

        guild = compound.getBoolean("guild");
        town = compound.getBoolean("town");
        smallhouse1 = compound.getBoolean("smallhouse1");
        smallhouse2 = compound.getBoolean("smallhouse2");
        largehouse1 = compound.getBoolean("largehouse1");
        well = compound.getBoolean("well");
        itemshop = compound.getBoolean("itemshop");
        stockmarket = compound.getBoolean("stockmarket");
        isTier2 = compound.getBoolean("isTier2");
        smallhouse3 = compound.getBoolean("smallhouse3");
        smallhouse4 = compound.getBoolean("smallhouse4");
        largehouse2 = compound.getBoolean("largehouse2");
        builderhouse = compound.getBoolean("builderhouse");
        barracks = compound.getBoolean("barracks");
        foodshop = compound.getBoolean("foodshop");
        blockshop = compound.getBoolean("blockshop");
        isTier3 = compound.getBoolean("isTier3");
        smallhouse5 = compound.getBoolean("smallhouse5");
        smallhouse6 = compound.getBoolean("smallhouse6");
        smallhouse7 = compound.getBoolean("smallhouse7");
        largehouse3 = compound.getBoolean("largehouse3");
        tavern = compound.getBoolean("tavern");
        chapel = compound.getBoolean("chapel");
        library = compound.getBoolean("library");
        magehall = compound.getBoolean("magehall");
        isTier4 = compound.getBoolean("isTier4");
        bridge = compound.getBoolean("bridge");
        castle = compound.getBoolean("castle");
        colloseum = compound.getBoolean("colloseum");
        easternTower = compound.getBoolean("easternTower");
        fishHut = compound.getBoolean("fishHut");
        lightHouse = compound.getBoolean("lightHouse");
        mill = compound.getBoolean("mill");
        observerPost = compound.getBoolean("observerPost");
        smallhouse8 = compound.getBoolean("smallhouse8");
        smallhouse9 = compound.getBoolean("smallhouse9");
        smallhouse10 = compound.getBoolean("smallhouse10");
        smallhouse11 = compound.getBoolean("smallhouse11");
        largehouse4 = compound.getBoolean("largehouse4");
        northernTower1 = compound.getBoolean("northernTower1");
        northernTower2 = compound.getBoolean("northernTower2");
        stables = compound.getBoolean("stables");
        zeppelin = compound.getBoolean("zeppelin");
    }

    @Override
    public void writeToNBT(NBTTagCompound world)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("townPosX", townPosX);
        compound.setInteger("townPosY", townPosY);
        compound.setInteger("townPosZ", townPosZ);

        compound.setBoolean("guild", guild);
        compound.setBoolean("town", town);
        compound.setBoolean("smallhouse1", smallhouse1);
        compound.setBoolean("smallhouse2", smallhouse2);
        compound.setBoolean("largehouse1", largehouse1);
        compound.setBoolean("well", well);
        compound.setBoolean("itemshop", itemshop);
        compound.setBoolean("stockmarket", stockmarket);
        compound.setBoolean("isTier2", isTier2);
        compound.setBoolean("smallhouse3", smallhouse3);
        compound.setBoolean("smallhouse4", smallhouse4);
        compound.setBoolean("largehouse2", largehouse2);
        compound.setBoolean("builderhouse", builderhouse);
        compound.setBoolean("barracks", barracks);
        compound.setBoolean("foodshop", foodshop);
        compound.setBoolean("blockshop", blockshop);
        compound.setBoolean("isTier3", isTier3);
        compound.setBoolean("smallhouse5", smallhouse5);
        compound.setBoolean("smallhouse6", smallhouse6);
        compound.setBoolean("smallhouse7", smallhouse7);
        compound.setBoolean("largehouse3", largehouse3);
        compound.setBoolean("tavern", tavern);
        compound.setBoolean("chapel", chapel);
        compound.setBoolean("library", library);
        compound.setBoolean("magehall", magehall);
        compound.setBoolean("isTier4", isTier4);
        compound.setBoolean("bridge", bridge);
        compound.setBoolean("castle", castle);
        compound.setBoolean("colloseum", colloseum);
        compound.setBoolean("easternTower", easternTower);
        compound.setBoolean("fishHut", fishHut);
        compound.setBoolean("lightHouse", lightHouse);
        compound.setBoolean("mill", mill);
        compound.setBoolean("observerPost", observerPost);
        compound.setBoolean("smallhouse8", smallhouse8);
        compound.setBoolean("smallhouse9", smallhouse9);
        compound.setBoolean("smallhouse10", smallhouse10);
        compound.setBoolean("smallhouse11", smallhouse11);
        compound.setBoolean("largehouse4", largehouse4);
        compound.setBoolean("northernTower1", northernTower1);
        compound.setBoolean("northernTower2", northernTower2);
        compound.setBoolean("stables", stables);
        compound.setBoolean("zeppelin", zeppelin);

        world.setTag("town", compound);
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
package aginsun.kingdoms.server;

import aginsun.kingdoms.server.handlers.Buildings;
import aginsun.kingdoms.server.handlers.UtilToK;
import aginsun.kingdoms.server.handlers.resources.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public final class PlayerProvider implements IExtendedEntityProperties
{
    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound taleOfKingdoms = new NBTTagCompound();
        taleOfKingdoms.setFloat("worthy", WorthyKeeper.getInstance().getWorthy());
        taleOfKingdoms.setBoolean("hunter", HunterKeeper.getInstance().getHunter());
        taleOfKingdoms.setInteger("burningVillages", UtilToK.burningVillages);
        taleOfKingdoms.setInteger("reficulHoles", UtilToK.reficulHoles);
        taleOfKingdoms.setInteger("libraryInvestment", UtilToK.libraryInvestment);
        taleOfKingdoms.setInteger("bindLight", UtilToK.bindLight);

        NBTTagCompound eco = new NBTTagCompound();
        eco.setInteger("GoldTotal", GoldKeeper.getGoldTotal());
        eco.setInteger("bankGold", GoldKeeper.bankGold);
        taleOfKingdoms.setTag("economy", eco);

        NBTTagCompound town = new NBTTagCompound();
        town.setInteger("townX", UtilToK.townX);
        town.setInteger("townY", UtilToK.townY);
        town.setInteger("townZ", UtilToK.townZ);
        taleOfKingdoms.setTag("town", town);

        NBTTagCompound kingdom = new NBTTagCompound();
        kingdom.setBoolean("kingdomCreated", Buildings.getBuilding(1));
        kingdom.setBoolean("smallhouse1", Buildings.getBuilding(2));
        kingdom.setBoolean("smallhouse2", Buildings.getBuilding(3));
        kingdom.setBoolean("largehouse1", Buildings.getBuilding(4));
        kingdom.setBoolean("well", Buildings.getBuilding(5));
        kingdom.setBoolean("itemshop", Buildings.getBuilding(6));
        kingdom.setBoolean("stockmarket", Buildings.getBuilding(7));
        kingdom.setBoolean("isTier2", Buildings.getBuilding(8));
        kingdom.setBoolean("smallhouse3", Buildings.getBuilding(9));
        kingdom.setBoolean("largehouse2", Buildings.getBuilding(10));
        kingdom.setBoolean("builderhouse", Buildings.getBuilding(11));
        kingdom.setBoolean("smallhouse4", Buildings.getBuilding(12));
        kingdom.setBoolean("barracks", Buildings.getBuilding(13));
        kingdom.setBoolean("foodshop", Buildings.getBuilding(14));
        kingdom.setBoolean("blockshop", Buildings.getBuilding(15));
        kingdom.setBoolean("isTier3", Buildings.getBuilding(16));
        kingdom.setBoolean("smallhouse5", Buildings.getBuilding(17));
        kingdom.setBoolean("smallhouse6", Buildings.getBuilding(18));
        kingdom.setBoolean("smallhouse7", Buildings.getBuilding(19));
        kingdom.setBoolean("largehouse3", Buildings.getBuilding(20));
        kingdom.setBoolean("tavern", Buildings.getBuilding(21));
        kingdom.setBoolean("chapel", Buildings.getBuilding(22));
        kingdom.setBoolean("library", Buildings.getBuilding(23));
        kingdom.setBoolean("magehall", Buildings.getBuilding(24));
        kingdom.setBoolean("isTier4", Buildings.getBuilding(25));
        kingdom.setBoolean("bridge", Buildings.getBuilding(26));
        kingdom.setBoolean("castle", Buildings.getBuilding(27));
        kingdom.setBoolean("easternTower", Buildings.getBuilding(28));
        kingdom.setBoolean("fishHut", Buildings.getBuilding(29));
        kingdom.setBoolean("lightHouse", Buildings.getBuilding(30));
        kingdom.setBoolean("mill", Buildings.getBuilding(31));
        kingdom.setBoolean("observerPost", Buildings.getBuilding(32));
        kingdom.setBoolean("smallhouse8", Buildings.getBuilding(33));
        kingdom.setBoolean("smallhouse9", Buildings.getBuilding(34));
        kingdom.setBoolean("smallhouse10", Buildings.getBuilding(35));
        kingdom.setBoolean("smallhouse11", Buildings.getBuilding(36));
        kingdom.setBoolean("largehouse4", Buildings.getBuilding(37));
        kingdom.setBoolean("northernTower1", Buildings.getBuilding(38));
        kingdom.setBoolean("northernTower2", Buildings.getBuilding(39));
        kingdom.setBoolean("colloseum", Buildings.getBuilding(40));
        kingdom.setBoolean("stables", Buildings.getBuilding(41));
        kingdom.setBoolean("zeppelin", Buildings.getBuilding(42));
        taleOfKingdoms.setTag("kingdom", kingdom);

        NBTTagCompound guild = new NBTTagCompound();
        guild.setBoolean("GuildCreated", Buildings.getBuilding(0));
        guild.setBoolean("guildFightEnded", UtilToK.guildFightEnded);
        guild.setBoolean("guildFightStarted", UtilToK.guildFightStarted);
        taleOfKingdoms.setTag("guild", guild);

        NBTTagCompound resources = new NBTTagCompound();
        resources.setInteger("woodResource", ResourceHandler.getInstance().getwoodResource());
        resources.setInteger("cobblestoneResource", ResourceHandler.getInstance().getcobbleResource());
        resources.setInteger("woodPool", ResourceHandler.getInstance().getWoodPool());
        resources.setInteger("cobblePool", ResourceHandler.getInstance().getCobblePool());
        taleOfKingdoms.setTag("resources", resources);

        NBTTagCompound workers = new NBTTagCompound();
        workers.setInteger("quarryMembers", WorkerHandler.getInstance().getQuarryMembers());
        workers.setInteger("lumberMembers", WorkerHandler.getInstance().getLumberMembers());
        taleOfKingdoms.setTag("workers", workers);

        compound.setTag("PlayerPersisted", taleOfKingdoms);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound taleOfKingdoms = compound.getCompoundTag("PlayerPersisted");
        WorthyKeeper.getInstance().setWorthy(taleOfKingdoms.getFloat("worthy"));
        HunterKeeper.getInstance().setHunter(taleOfKingdoms.getBoolean("hunter"));
        UtilToK.burningVillages = taleOfKingdoms.getInteger("burningVillages");
        UtilToK.reficulHoles = taleOfKingdoms.getInteger("reficulHoles");
        UtilToK.libraryInvestment = taleOfKingdoms.getInteger("libraryInvestment");
        UtilToK.bindLight = taleOfKingdoms.getInteger("bindLight");

        NBTTagCompound eco = taleOfKingdoms.getCompoundTag("economy");
        GoldKeeper.goldTotal = eco.getInteger("GoldTotal");
        GoldKeeper.bankGold = eco.getInteger("bankGold");

        NBTTagCompound town = taleOfKingdoms.getCompoundTag("town");
        UtilToK.townX = town.getInteger("townX");
        UtilToK.townY = town.getInteger("townY");
        UtilToK.townZ = town.getInteger("townZ");

        NBTTagCompound kingdom = taleOfKingdoms.getCompoundTag("kingdom");
        Buildings.kingdomCreated = kingdom.getBoolean("kingdomCreated");
        Buildings.smallhouse1 = kingdom.getBoolean("smallhouse1");
        Buildings.smallhouse2 = kingdom.getBoolean("smallhouse2");
        Buildings.largehouse1 = kingdom.getBoolean("largehouse1");
        Buildings.well = kingdom.getBoolean("well");
        Buildings.itemshop = kingdom.getBoolean("itemshop");
        Buildings.stockmarket = kingdom.getBoolean("stockmarket");
        Buildings.isTier2 = kingdom.getBoolean("isTier2");
        Buildings.smallhouse3 = kingdom.getBoolean("smallhouse3");
        Buildings.largehouse2 = kingdom.getBoolean("largehouse2");
        Buildings.builderhouse = kingdom.getBoolean("builderhouse");
        Buildings.smallhouse4 = kingdom.getBoolean("smallhouse4");
        Buildings.barracks = kingdom.getBoolean("barracks");
        Buildings.foodshop = kingdom.getBoolean("foodshop");
        Buildings.blockshop = kingdom.getBoolean("blockshop");
        Buildings.isTier3 = kingdom.getBoolean("isTier3");
        Buildings.tavern = kingdom.getBoolean("tavern");
        Buildings.smallhouse5 = kingdom.getBoolean("smallhouse5");
        Buildings.smallhouse6 = kingdom.getBoolean("smallhouse6");
        Buildings.smallhouse7 = kingdom.getBoolean("smallhouse7");
        Buildings.chapel = kingdom.getBoolean("chapel");
        Buildings.largehouse3 = kingdom.getBoolean("largehouse3");
        Buildings.library = kingdom.getBoolean("library");
        Buildings.magehall = kingdom.getBoolean("magehall");
        Buildings.isTier4 = kingdom.getBoolean("isTier4");
        Buildings.bridge = kingdom.getBoolean("bridge");
        Buildings.castle = kingdom.getBoolean("castle");
        Buildings.easternTower =kingdom.getBoolean("easternTower");
        Buildings.fishHut = kingdom.getBoolean("fishHut");
        Buildings.lightHouse = kingdom.getBoolean("lightHouse");
        Buildings.mill = kingdom.getBoolean("mill");
        Buildings.observerPost = kingdom.getBoolean("observerPost");
        Buildings.smallhouse8 = kingdom.getBoolean("smallhouse8");
        Buildings.smallhouse9 = kingdom.getBoolean("smallhouse9");
        Buildings.smallhouse10 = kingdom.getBoolean("smallhouse10");
        Buildings.smallhouse11 = kingdom.getBoolean("smallhouse11");
        Buildings.largehouse4 = kingdom.getBoolean("largehouse4");
        Buildings.northernTower1 = kingdom.getBoolean("northernTower1");
        Buildings.northernTower2 = kingdom.getBoolean("northernTower2");
        Buildings.colloseum = kingdom.getBoolean("colloseum");
        Buildings.stables = kingdom.getBoolean("stables");
        Buildings.zeppelin = kingdom.getBoolean("zeppelin");

        NBTTagCompound guild = taleOfKingdoms.getCompoundTag("guild");
        Buildings.createGuild = guild.getBoolean("GuildCreated");
        UtilToK.guildFightEnded = guild.getBoolean("guildFightEnded");
        UtilToK.guildFightStarted = guild.getBoolean("guildFightStarted");

        NBTTagCompound resources = taleOfKingdoms.getCompoundTag("resources");
        ResourceHandler.getInstance().setWoodPool(resources.getInteger("woodPool"));
        ResourceHandler.getInstance().setCobblePool(resources.getInteger("cobblePool"));
        ResourceHandler.getInstance().setwoodResource(resources.getInteger("woodResource"));
        ResourceHandler.getInstance().setcobbleResource(resources.getInteger("cobblestoneResource"));

        NBTTagCompound workers = taleOfKingdoms.getCompoundTag("workers");
        WorkerHandler.getInstance().setQuarryMembers(workers.getInteger("quarryMembers"));
        WorkerHandler.getInstance().setLumberMembers(workers.getInteger("lumberMembers"));
    }

    @Override
    public void init(Entity entity, World world) {}

    public static PlayerProvider get(EntityPlayer player)
    {
        return (PlayerProvider) player.getExtendedProperties("tokPlayer");
    }
}
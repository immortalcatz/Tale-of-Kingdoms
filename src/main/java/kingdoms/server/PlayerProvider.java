package kingdoms.server;

import kingdoms.server.handlers.Buildings;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.client.CPacketSyncBank;
import kingdoms.server.handlers.packets.client.CPacketSyncDataPlayer;
import kingdoms.server.handlers.resources.HunterHandler;
import kingdoms.server.handlers.resources.ResourcesHandler;
import kingdoms.server.handlers.resources.WorkersHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.List;

public final class PlayerProvider implements IExtendedEntityProperties
{
    private int goldTotal, bankGold, glory, townX, townY, townZ;
    public int libraryInvestment, burningVillages, reficulHoles, bindLight;
    private boolean guildFightEnded = true, guildFightStarted = false;
    public boolean badKid;
    public List<ItemStack> stacks;

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound taleOfKingdoms = new NBTTagCompound();
        taleOfKingdoms.setInteger("glory", glory);
        taleOfKingdoms.setBoolean("hunter", HunterHandler.INSTANCE.getHunter());
        taleOfKingdoms.setInteger("burningVillages", burningVillages);
        taleOfKingdoms.setInteger("reficulHoles", reficulHoles);
        taleOfKingdoms.setInteger("libraryInvestment", libraryInvestment);
        taleOfKingdoms.setInteger("bindLight", bindLight);
        taleOfKingdoms.setBoolean("badKid", badKid);

        NBTTagCompound eco = new NBTTagCompound();
        eco.setInteger("GoldTotal", getGoldTotal());
        eco.setInteger("bankGold", getBankGold());
        taleOfKingdoms.setTag("economy", eco);

        NBTTagCompound town = new NBTTagCompound();
        town.setInteger("townX", getTownX());
        town.setInteger("townY", getTownY());
        town.setInteger("townZ", getTownZ());
        taleOfKingdoms.setTag("town", town);

        NBTTagCompound kingdom = new NBTTagCompound();
        kingdom.setBoolean("kingdomCreated", Buildings.INSTANCE.getBuilding(1));
        kingdom.setBoolean("smallhouse1", Buildings.INSTANCE.getBuilding(2));
        kingdom.setBoolean("smallhouse2", Buildings.INSTANCE.getBuilding(3));
        kingdom.setBoolean("largehouse1", Buildings.INSTANCE.getBuilding(4));
        kingdom.setBoolean("well", Buildings.INSTANCE.getBuilding(5));
        kingdom.setBoolean("itemshop", Buildings.INSTANCE.getBuilding(6));
        kingdom.setBoolean("stockmarket", Buildings.INSTANCE.getBuilding(7));
        kingdom.setBoolean("isTier2", Buildings.INSTANCE.getBuilding(8));
        kingdom.setBoolean("smallhouse3", Buildings.INSTANCE.getBuilding(9));
        kingdom.setBoolean("largehouse2", Buildings.INSTANCE.getBuilding(10));
        kingdom.setBoolean("builderhouse", Buildings.INSTANCE.getBuilding(11));
        kingdom.setBoolean("smallhouse4", Buildings.INSTANCE.getBuilding(12));
        kingdom.setBoolean("barracks", Buildings.INSTANCE.getBuilding(13));
        kingdom.setBoolean("foodshop", Buildings.INSTANCE.getBuilding(14));
        kingdom.setBoolean("blockshop", Buildings.INSTANCE.getBuilding(15));
        kingdom.setBoolean("isTier3", Buildings.INSTANCE.getBuilding(16));
        kingdom.setBoolean("smallhouse5", Buildings.INSTANCE.getBuilding(17));
        kingdom.setBoolean("smallhouse6", Buildings.INSTANCE.getBuilding(18));
        kingdom.setBoolean("smallhouse7", Buildings.INSTANCE.getBuilding(19));
        kingdom.setBoolean("largehouse3", Buildings.INSTANCE.getBuilding(20));
        kingdom.setBoolean("tavern", Buildings.INSTANCE.getBuilding(21));
        kingdom.setBoolean("chapel", Buildings.INSTANCE.getBuilding(22));
        kingdom.setBoolean("library", Buildings.INSTANCE.getBuilding(23));
        kingdom.setBoolean("magehall", Buildings.INSTANCE.getBuilding(24));
        kingdom.setBoolean("isTier4", Buildings.INSTANCE.getBuilding(25));
        kingdom.setBoolean("bridge", Buildings.INSTANCE.getBuilding(26));
        kingdom.setBoolean("castle", Buildings.INSTANCE.getBuilding(27));
        kingdom.setBoolean("easternTower", Buildings.INSTANCE.getBuilding(28));
        kingdom.setBoolean("fishHut", Buildings.INSTANCE.getBuilding(29));
        kingdom.setBoolean("lightHouse", Buildings.INSTANCE.getBuilding(30));
        kingdom.setBoolean("mill", Buildings.INSTANCE.getBuilding(31));
        kingdom.setBoolean("observerPost", Buildings.INSTANCE.getBuilding(32));
        kingdom.setBoolean("smallhouse8", Buildings.INSTANCE.getBuilding(33));
        kingdom.setBoolean("smallhouse9", Buildings.INSTANCE.getBuilding(34));
        kingdom.setBoolean("smallhouse10", Buildings.INSTANCE.getBuilding(35));
        kingdom.setBoolean("smallhouse11", Buildings.INSTANCE.getBuilding(36));
        kingdom.setBoolean("largehouse4", Buildings.INSTANCE.getBuilding(37));
        kingdom.setBoolean("northernTower1", Buildings.INSTANCE.getBuilding(38));
        kingdom.setBoolean("northernTower2", Buildings.INSTANCE.getBuilding(39));
        kingdom.setBoolean("colloseum", Buildings.INSTANCE.getBuilding(40));
        kingdom.setBoolean("stables", Buildings.INSTANCE.getBuilding(41));
        kingdom.setBoolean("zeppelin", Buildings.INSTANCE.getBuilding(42));
        taleOfKingdoms.setTag("kingdom", kingdom);

        NBTTagCompound guild = new NBTTagCompound();
        guild.setBoolean("GuildCreated", Buildings.INSTANCE.getBuilding(0));
        guild.setBoolean("guildFightEnded", getGuildFightEnded());
        guild.setBoolean("guildFightStarted", getGuildFightStarted());
        taleOfKingdoms.setTag("guild", guild);

        NBTTagCompound resources = new NBTTagCompound();
        resources.setInteger("woodResource", ResourcesHandler.INSTANCE.getwoodResource());
        resources.setInteger("cobblestoneResource", ResourcesHandler.INSTANCE.getcobbleResource());
        resources.setInteger("woodPool", ResourcesHandler.INSTANCE.getWoodPool());
        resources.setInteger("cobblePool", ResourcesHandler.INSTANCE.getCobblePool());
        taleOfKingdoms.setTag("resources", resources);

        NBTTagCompound workers = new NBTTagCompound();
        workers.setByte("quarryMembers", WorkersHandler.INSTANCE.getQuarryMembers());
        workers.setByte("lumberMembers", WorkersHandler.INSTANCE.getLumberMembers());
        taleOfKingdoms.setTag("workers", workers);

        compound.setTag("PlayerPersisted", taleOfKingdoms);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        NBTTagCompound taleOfKingdoms = compound.getCompoundTag("PlayerPersisted");
        setGlory(taleOfKingdoms.getInteger("glory"));
        HunterHandler.INSTANCE.setHunter(taleOfKingdoms.getBoolean("hunter"));
        burningVillages = taleOfKingdoms.getInteger("burningVillages");
        reficulHoles = taleOfKingdoms.getInteger("reficulHoles");
        libraryInvestment = taleOfKingdoms.getInteger("libraryInvestment");
        bindLight = taleOfKingdoms.getInteger("bindLight");
        badKid = taleOfKingdoms.getBoolean("badKid");

        NBTTagCompound eco = taleOfKingdoms.getCompoundTag("economy");
        setGoldTotal(eco.getInteger("GoldTotal"));
        setBankGold(eco.getInteger("bankGold"));

        NBTTagCompound town = taleOfKingdoms.getCompoundTag("town");
        setTownX(town.getInteger("townX"));
        setTownY(town.getInteger("townY"));
        setTownZ(town.getInteger("townZ"));

        NBTTagCompound kingdom = taleOfKingdoms.getCompoundTag("kingdom");
        Buildings.INSTANCE.kingdomCreated = kingdom.getBoolean("kingdomCreated");
        Buildings.INSTANCE.smallhouse1 = kingdom.getBoolean("smallhouse1");
        Buildings.INSTANCE.smallhouse2 = kingdom.getBoolean("smallhouse2");
        Buildings.INSTANCE.largehouse1 = kingdom.getBoolean("largehouse1");
        Buildings.INSTANCE.well = kingdom.getBoolean("well");
        Buildings.INSTANCE.itemshop = kingdom.getBoolean("itemshop");
        Buildings.INSTANCE.stockmarket = kingdom.getBoolean("stockmarket");
        Buildings.INSTANCE.isTier2 = kingdom.getBoolean("isTier2");
        Buildings.INSTANCE.smallhouse3 = kingdom.getBoolean("smallhouse3");
        Buildings.INSTANCE.largehouse2 = kingdom.getBoolean("largehouse2");
        Buildings.INSTANCE.builderhouse = kingdom.getBoolean("builderhouse");
        Buildings.INSTANCE.smallhouse4 = kingdom.getBoolean("smallhouse4");
        Buildings.INSTANCE.barracks = kingdom.getBoolean("barracks");
        Buildings.INSTANCE.foodshop = kingdom.getBoolean("foodshop");
        Buildings.INSTANCE.blockshop = kingdom.getBoolean("blockshop");
        Buildings.INSTANCE.isTier3 = kingdom.getBoolean("isTier3");
        Buildings.INSTANCE.tavern = kingdom.getBoolean("tavern");
        Buildings.INSTANCE.smallhouse5 = kingdom.getBoolean("smallhouse5");
        Buildings.INSTANCE.smallhouse6 = kingdom.getBoolean("smallhouse6");
        Buildings.INSTANCE.smallhouse7 = kingdom.getBoolean("smallhouse7");
        Buildings.INSTANCE.chapel = kingdom.getBoolean("chapel");
        Buildings.INSTANCE.largehouse3 = kingdom.getBoolean("largehouse3");
        Buildings.INSTANCE.library = kingdom.getBoolean("library");
        Buildings.INSTANCE.magehall = kingdom.getBoolean("magehall");
        Buildings.INSTANCE.isTier4 = kingdom.getBoolean("isTier4");
        Buildings.INSTANCE.bridge = kingdom.getBoolean("bridge");
        Buildings.INSTANCE.castle = kingdom.getBoolean("castle");
        Buildings.INSTANCE.easternTower =kingdom.getBoolean("easternTower");
        Buildings.INSTANCE.fishHut = kingdom.getBoolean("fishHut");
        Buildings.INSTANCE.lightHouse = kingdom.getBoolean("lightHouse");
        Buildings.INSTANCE.mill = kingdom.getBoolean("mill");
        Buildings.INSTANCE.observerPost = kingdom.getBoolean("observerPost");
        Buildings.INSTANCE.smallhouse8 = kingdom.getBoolean("smallhouse8");
        Buildings.INSTANCE.smallhouse9 = kingdom.getBoolean("smallhouse9");
        Buildings.INSTANCE.smallhouse10 = kingdom.getBoolean("smallhouse10");
        Buildings.INSTANCE.smallhouse11 = kingdom.getBoolean("smallhouse11");
        Buildings.INSTANCE.largehouse4 = kingdom.getBoolean("largehouse4");
        Buildings.INSTANCE.northernTower1 = kingdom.getBoolean("northernTower1");
        Buildings.INSTANCE.northernTower2 = kingdom.getBoolean("northernTower2");
        Buildings.INSTANCE.colloseum = kingdom.getBoolean("colloseum");
        Buildings.INSTANCE.stables = kingdom.getBoolean("stables");
        Buildings.INSTANCE.zeppelin = kingdom.getBoolean("zeppelin");

        NBTTagCompound guild = taleOfKingdoms.getCompoundTag("guild");
        Buildings.INSTANCE.createGuild = guild.getBoolean("GuildCreated");
        setGuildFightEnded(guild.getBoolean("guildFightEnded"));
        setGuildFightEnded(guild.getBoolean("guildFightStarted"));

        NBTTagCompound resources = taleOfKingdoms.getCompoundTag("resources");
        ResourcesHandler.INSTANCE.setWoodPool(resources.getInteger("woodPool"));
        ResourcesHandler.INSTANCE.setCobblePool(resources.getInteger("cobblePool"));
        ResourcesHandler.INSTANCE.setwoodResource(resources.getInteger("woodResource"));
        ResourcesHandler.INSTANCE.setcobbleResource(resources.getInteger("cobblestoneResource"));

        NBTTagCompound workers = taleOfKingdoms.getCompoundTag("workers");
        WorkersHandler.INSTANCE.setQuarryMembers(workers.getByte("quarryMembers"));
        WorkersHandler.INSTANCE.setLumberMembers(workers.getByte("lumberMembers"));
    }

    @Override
    public void init(Entity entity, World world) {}

    public int getGlory()
    {
        return glory;
    }

    public void setGlory(int glory)
    {
        this.glory = glory;
    }

    public void addGlory(int glory, EntityPlayer player)
    {
        this.glory += glory;
        sync(player);
    }

    public void decreaseGlory(int glory, EntityPlayer player)
    {
        this.glory -= glory;
        sync(player);
    }

    public int getGoldTotal()
    {
        return goldTotal;
    }
    public int getBankGold()
    {
        return bankGold;
    }

    public void setGoldTotal(int count)
    {
        goldTotal = count;
    }
    public void setBankGold(int count)
    {
        bankGold = count;
    }

    public void addGold(int count, EntityPlayer player)
    {
        goldTotal += count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(goldTotal, false), (EntityPlayerMP) player);
        }
    }
    public void addBankGold(int count, EntityPlayer player)
    {
        bankGold += count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(bankGold, true), (EntityPlayerMP) player);
        }
    }

    public void decreaseGold(int count, EntityPlayer player)
    {
        goldTotal -= count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(goldTotal, false), (EntityPlayerMP) player);
        }
    }
    public void decreaseBankGold(int count, EntityPlayer player)
    {
        bankGold -= count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(bankGold, true), (EntityPlayerMP) player);
        }
    }

    public int getTownX()
    {
        return townX;
    }
    public int getTownY()
    {
        return townY;
    }
    public int getTownZ()
    {
        return townZ;
    }

    public boolean getGuildFightEnded()
    {
        return guildFightEnded;
    }
    public boolean getGuildFightStarted()
    {
        return guildFightStarted;
    }

    public void setTownX(int townX)
    {
        this.townX = townX;
    }
    public void setTownY(int townY)
    {
        this.townY = townY;
    }
    public void setTownZ(int townZ)
    {
        this.townZ = townZ;
    }

    public void setGuildFightEnded(boolean guildFightEnded)
    {
        this.guildFightEnded = guildFightEnded;
    }
    public void setGuildFightStarted(boolean guildFightStarted)
    {
        this.guildFightStarted = guildFightStarted;
    }

    public void sync(EntityPlayer player)
    {
        if (!player.worldObj.isRemote)
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncDataPlayer(this), (EntityPlayerMP) player);
    }

    public static PlayerProvider get(EntityPlayer player)
    {
        return (PlayerProvider) player.getExtendedProperties("tokPlayer");
    }
}
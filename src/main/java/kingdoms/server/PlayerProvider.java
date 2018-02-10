package kingdoms.server;

import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.client.CPacketSyncBank;
import kingdoms.server.handlers.packets.client.CPacketSyncDataPlayer;
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
    private final EntityPlayer player;

    private int goldTotal, bankGold, glory;
    public int libraryInvestment, burningVillages, reficulHoles, bindLight;
    public boolean badKid, hunter;
    public List<ItemStack> stacks;

    public PlayerProvider(EntityPlayer player)
    {
        this.player = player;
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        NBTTagCompound taleOfKingdoms = new NBTTagCompound();
        taleOfKingdoms.setInteger("glory", glory);
        taleOfKingdoms.setBoolean("hunter", hunter);
        taleOfKingdoms.setInteger("burningVillages", burningVillages);
        taleOfKingdoms.setInteger("reficulHoles", reficulHoles);
        taleOfKingdoms.setInteger("libraryInvestment", libraryInvestment);
        taleOfKingdoms.setInteger("bindLight", bindLight);
        taleOfKingdoms.setBoolean("badKid", badKid);

        NBTTagCompound eco = new NBTTagCompound();
        eco.setInteger("GoldTotal", getGoldTotal());
        eco.setInteger("bankGold", getBankGold());
        taleOfKingdoms.setTag("economy", eco);

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
        hunter = taleOfKingdoms.getBoolean("hunter");
        burningVillages = taleOfKingdoms.getInteger("burningVillages");
        reficulHoles = taleOfKingdoms.getInteger("reficulHoles");
        libraryInvestment = taleOfKingdoms.getInteger("libraryInvestment");
        bindLight = taleOfKingdoms.getInteger("bindLight");
        badKid = taleOfKingdoms.getBoolean("badKid");

        NBTTagCompound eco = taleOfKingdoms.getCompoundTag("economy");
        setGoldTotal(eco.getInteger("GoldTotal"));
        setBankGold(eco.getInteger("bankGold"));

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

    public void addGlory(int glory)
    {
        this.glory += glory;
        sync();
    }

    public void decreaseGlory(int glory)
    {
        this.glory -= glory;
        sync();
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

    public void addGold(int count)
    {
        goldTotal += count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(goldTotal, false), (EntityPlayerMP) player);
        }
    }
    public void addBankGold(int count)
    {
        bankGold += count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(bankGold, true), (EntityPlayerMP) player);
        }
    }

    public void decreaseGold(int count)
    {
        goldTotal -= count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(goldTotal, false), (EntityPlayerMP) player);
        }
    }
    public void decreaseBankGold(int count)
    {
        bankGold -= count;

        if (!player.worldObj.isRemote)
        {
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncBank(bankGold, true), (EntityPlayerMP) player);
        }
    }

    public void sync()
    {
        if (!player.worldObj.isRemote)
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncDataPlayer(this), (EntityPlayerMP) player);
    }

    public static PlayerProvider get(EntityPlayer player)
    {
        return (PlayerProvider) player.getExtendedProperties("tokPlayer");
    }
}
package kingdoms.server.handlers;

import kingdoms.server.PlayerProvider;
import kingdoms.server.TaleOfKingdoms;
import kingdoms.server.handlers.packets.client.CPacketSyncDataPlayer;
import kingdoms.server.handlers.schematic.SchematicHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.Random;

public final class ServerEvents
{
    public ServerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new MF());
        FMLCommonHandler.instance().bus().register(new FML());
    }

    public class MF
    {
        @SubscribeEvent
        public void onConstructing(EntityEvent.EntityConstructing e)
        {
            if (e.entity instanceof EntityPlayer)
            {
                if (e.entity.getExtendedProperties("tokPlayer") == null)
                    e.entity.registerExtendedProperties("tokPlayer", new PlayerProvider());
            }
        }

        @SubscribeEvent
        public void entityJoinWorld(EntityJoinWorldEvent e)
        {
            if (e.entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) e.entity;
                PlayerProvider provider = PlayerProvider.get(player);

                if (provider != null)
                {
                    if (!e.world.isRemote)
                    {
                        EntityTracker tracker = ((WorldServer) e.world).getEntityTracker();
                        CPacketSyncDataPlayer packet = new CPacketSyncDataPlayer(provider);

                        tracker.getTrackingPlayers(player).forEach(entityPlayer -> NetworkHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) entityPlayer));
                    }
                }
            }
        }

        @SubscribeEvent
        public void playerTracking(PlayerEvent.StartTracking e)
        {
            if (e.entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) e.entity;
                PlayerProvider provider = PlayerProvider.get(player);

                if (provider != null)
                    NetworkHandler.INSTANCE.sendTo(new CPacketSyncDataPlayer(provider), (EntityPlayerMP) e.entityPlayer);
            }
        }

        @SubscribeEvent
        public void onClone(PlayerEvent.Clone e)
        {
            if (e.wasDeath)
            {
                NBTTagCompound tag = new NBTTagCompound();
                PlayerProvider.get(e.original).saveNBTData(tag);
                PlayerProvider.get(e.entityPlayer).loadNBTData(tag);
            }
        }

        @SubscribeEvent
        public void onDeath(LivingDeathEvent e)
        {
            if (e.source.getDamageType().equals("player") || e.source.getDamageType().equals("arrow"))
                ItemDropHelper.dropCoins(e.entityLiving);
        }
    }

    public class FML
    {
        @SubscribeEvent
        public void onTicks(TickEvent.ServerTickEvent e)
        {
            if (e.phase == TickEvent.Phase.END)
            {
                if (e.type == TickEvent.Type.SERVER)
                {
                    WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

                    if (world != null)
                        SchematicHandler.INSTANCE.update(world);
                }
            }
        }

        @SubscribeEvent
        public void onPickUp(cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent e)
        {
            final Random random = new Random();
            final Item item = e.pickedUp.getEntityItem().getItem();

            if (item == TaleOfKingdoms.proxy.coins)
            {
                e.player.inventory.consumeInventoryItem(item);
                PlayerProvider.get(e.player).addGold(2, e.player);
                PlayerProvider.get(e.player).addGlory(random.nextInt(15), e.player);
            }
        }

        @SubscribeEvent
        public void onPlayerLogin(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e)
        {
            Buildings.INSTANCE.registerBuildings();
            //NetworkHandler.INSTANCE.sendTo(new CPacketSyncBuildings(Buildings.INSTANCE.getBuildingList()), (EntityPlayerMP) e.player);
        }
    }
}
package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.server.PlayerProvider;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.handlers.packets.client.CPacketSyncDataPlayer;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import aginsun.kingdoms.server.handlers.schematic.SchematicHandler;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Random;

@Mod.EventBusSubscriber(Side.SERVER)
public final class ServerEvents
{
    public ServerEvents()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onConstructing(EntityEvent.EntityConstructing e)
    {
        if (e.getEntity() instanceof EntityPlayer)
        {
            //if (e.getEntity().getExtendedProperties("tokPlayer") == null)
                //e.getEntity().registerExtendedProperties("tokPlayer", new PlayerProvider());
        }
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent e)
    {
        if (e.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) e.getEntity();
            PlayerProvider provider = PlayerProvider.get(player);

            if (provider != null)
            {
                if (!e.getWorld().isRemote)
                {
                    EntityTracker tracker = ((WorldServer) e.getWorld()).getEntityTracker();
                    CPacketSyncDataPlayer packet = new CPacketSyncDataPlayer(provider);

                    tracker.getTrackingPlayers(player).forEach(entityPlayer -> NetworkHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) entityPlayer));
                }
            }
        }
    }

    @SubscribeEvent
    public void playerTracking(PlayerEvent.StartTracking e)
    {
        PlayerProvider provider = PlayerProvider.get(e.getEntityPlayer());

        if (provider != null)
            NetworkHandler.INSTANCE.sendTo(new CPacketSyncDataPlayer(provider), (EntityPlayerMP) e.getEntityPlayer());
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone e)
    {
        if (e.isWasDeath())
        {
            NBTTagCompound tag = new NBTTagCompound();
            PlayerProvider.get(e.getOriginal()).saveNBTData(tag);
            PlayerProvider.get(e.getEntityPlayer()).loadNBTData(tag);
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent e)
    {
        if (e.getSource().getDamageType().equals("player"))
            ItemDropHelper.dropCoins(e.getEntityLiving());
    }

    @SubscribeEvent
    public void onTicks(TickEvent.ServerTickEvent e)
    {
        if (e.phase == TickEvent.Phase.END)
        {
            if (e.type == TickEvent.Type.SERVER)
            {
                WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().worlds[0];

                if (world != null)
                    SchematicHandler.INSTANCE.update(world);
            }
        }
    }

    @SubscribeEvent
    public void onPickUp(net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent e)
    {
        final Random random = new Random();

        if (e.getStack().getItem() == TaleOfKingdoms.proxy.coins)
        {
            e.player.inventory.deleteStack(e.getStack());
            EconomyHandler.INSTANCE.addGold(2);
            PlayerProvider.get(e.player).addGlory(random.nextInt(15), e.player);
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e)
    {
        Buildings.INSTANCE.registerBuildings();
        //NetworkHandler.INSTANCE.sendTo(new CPacketSyncBuildings(Buildings.INSTANCE.getBuildingList()), (EntityPlayerMP) e.player);
    }
}
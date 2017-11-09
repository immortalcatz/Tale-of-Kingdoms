package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.server.handlers.resources.ItemDropHelper;
import aginsun.kingdoms.server.TaleOfKingdoms;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import aginsun.kingdoms.server.handlers.resources.GoldKeeper;
import aginsun.kingdoms.server.handlers.schematic.SchematicHandler;
import aginsun.kingdoms.server.handlers.resources.WorthyKeeper;
import aginsun.kingdoms.server.PlayerProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
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
            if (e.source.getDamageType().equals("player"))
            {
                ItemDropHelper.dropCoins(e.entityLiving);
            }
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
                    {
                        SchematicHandler.getInstance().update(world);
                    }
                }
            }
        }

        @SubscribeEvent
        public void onPickUp(cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent e)
        {
            final Random random = new Random();
            final Item item = e.pickedUp.getEntityItem().getItem();

            if (item == TaleOfKingdoms.coins)
            {
                e.player.inventory.consumeInventoryItem(item);
                GoldKeeper.addGold(2);
                WorthyKeeper.getInstance().addWorthy(random.nextInt(15));
            }
        }

        @SubscribeEvent
        public void onPlayerLogin(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e)
        {
            Buildings.registerBuildings();
        }
    }
}
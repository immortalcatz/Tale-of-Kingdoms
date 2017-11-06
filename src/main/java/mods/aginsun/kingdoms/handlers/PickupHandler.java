package mods.aginsun.kingdoms.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import mods.aginsun.kingdoms.TaleOfKingdoms;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

public final class PickupHandler
{
    @SubscribeEvent
    public void onPickUp(PlayerEvent.ItemPickupEvent e)
    {
        final Random random = new Random();
        Item item = e.pickedUp.getEntityItem().getItem();

        if (item == TaleOfKingdoms.coins)
        {
            e.player.inventory.consumeInventoryItem(item);
            GoldKeeper.addGold(2);
            WorthyKeeper.getInstance().addWorthy(random.nextInt(15));
        }
    }
}
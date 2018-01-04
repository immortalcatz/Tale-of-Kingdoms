package kingdoms.server.handlers.packets.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import kingdoms.api.network.AbstractPacket;
import kingdoms.server.PlayerProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public final class SPacketBuy extends AbstractPacket
{
    public SPacketBuy(){}
    public SPacketBuy(ItemStack stack, byte count, int price)
    {
        ByteBufUtils.writeItemStack(buf(), stack);
        buf().writeByte(count);
        buf().writeInt(price);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        ItemStack stack = ByteBufUtils.readItemStack(buf());

        stack.stackSize = buf().readByte();
        PlayerProvider.get(player).decreaseGold(buf().readInt());

        if (!player.inventory.addItemStackToInventory(stack))
            player.entityDropItem(stack, 0);
    }
}
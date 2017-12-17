package aginsun.kingdoms.server.handlers.packets.server;

import aginsun.kingdoms.api.network.AbstractPacket;
import aginsun.kingdoms.server.handlers.resources.EconomyHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public final class SPacketBuy extends AbstractPacket<SPacketBuy>
{
    private static int price;
    private static byte count;
    private static ItemStack stack;

    public SPacketBuy(){}
    public SPacketBuy(ItemStack stack, byte count, int price)
    {
        this.stack = stack;
        this.count = count;
        this.price = price;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayerMP player)
    {
        stack.setCount(count);
        EconomyHandler.INSTANCE.decreaseGold(price);

        if (!player.inventory.addItemStackToInventory(stack))
            player.entityDropItem(stack, 0);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        stack = ByteBufUtils.readItemStack(buf);
        count = buf.readByte();
        price = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeByte(count);
        buf.writeInt(price);
    }
}
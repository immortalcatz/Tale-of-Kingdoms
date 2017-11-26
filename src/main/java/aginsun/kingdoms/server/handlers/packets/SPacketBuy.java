package aginsun.kingdoms.server.handlers.packets;

import aginsun.kingdoms.api.network.AbstractPacket;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class SPacketBuy extends AbstractPacket<SPacketBuy>
{
    private static ItemStack stack;

    public SPacketBuy(){}
    public SPacketBuy(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {}

    @Override
    public void handleServerSide(EntityPlayerMP player)
    {
        player.inventory.addItemStackToInventory(stack);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeItemStack(buf, stack);
    }
}
package kingdoms.server.handlers.packets.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import kingdoms.api.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

public final class SPacketAdd extends AbstractPacket
{
    public SPacketAdd(){}
    public SPacketAdd(ItemStack stack, int count, String message)
    {
        ByteBufUtils.writeItemStack(buf(), stack);
        buf().writeInt(count);
        ByteBufUtils.writeUTF8String(buf(), message);
    }

    @Override
    public void server(EntityPlayerMP player)
    {
        ItemStack stack = ByteBufUtils.readItemStack(buf());

        stack.stackSize = buf().readInt();

        if (!player.inventory.addItemStackToInventory(stack))
            player.entityDropItem(stack, 0);

        player.addChatMessage(new ChatComponentTranslation(ByteBufUtils.readUTF8String(buf())));
    }
}
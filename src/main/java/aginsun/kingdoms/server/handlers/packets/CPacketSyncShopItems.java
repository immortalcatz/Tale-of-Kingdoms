package aginsun.kingdoms.server.handlers.packets;

import aginsun.kingdoms.api.network.AbstractPacket;
import aginsun.kingdoms.server.PlayerProvider;
import aginsun.kingdoms.server.TaleOfKingdoms;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public final class CPacketSyncShopItems extends AbstractPacket<CPacketSyncShopItems>
{
    private static ItemStack[] stacks;

    public CPacketSyncShopItems(){}
    public CPacketSyncShopItems(ItemStack... stacks)
    {
        this.stacks = stacks;
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        PlayerProvider.get(player).stacks = stacks;
        player.openGui(TaleOfKingdoms.instance, 6, player.worldObj, 0, 0, 0);
    }

    @Override
    public void handleServerSide(EntityPlayerMP player) {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
        stacks = new ItemStack[200];

        for (int i = 0; i < stacks.length; i++)
        {
            stacks[i] = ByteBufUtils.readItemStack(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        for (ItemStack stack : stacks)
        {
            ByteBufUtils.writeItemStack(buf, stack);
        }
    }
}
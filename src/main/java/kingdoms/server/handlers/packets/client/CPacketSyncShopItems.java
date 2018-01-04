package kingdoms.server.handlers.packets.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import kingdoms.api.network.AbstractPacket;
import kingdoms.server.PlayerProvider;
import kingdoms.server.TaleOfKingdoms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class CPacketSyncShopItems extends AbstractPacket
{
    public CPacketSyncShopItems(){}
    public CPacketSyncShopItems(List<ItemStack> stacks)
    {
        buf().writeInt(stacks.size());
        stacks.forEach(stack -> ByteBufUtils.writeItemStack(buf(), stack));
    }

    @Override
    public void client(EntityPlayer player)
    {
        List<ItemStack> stacks = new ArrayList<>();

        for (int i = 0; i < buf().readInt(); i++)
            stacks.add(ByteBufUtils.readItemStack(buf()));

        PlayerProvider.get(player).stacks = stacks;
        player.openGui(TaleOfKingdoms.instance, 6, player.worldObj, 0, 0, 0);
    }
}
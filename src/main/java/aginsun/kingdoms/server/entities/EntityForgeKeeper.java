package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.handlers.Config;
import aginsun.kingdoms.server.handlers.NetworkHandler;
import aginsun.kingdoms.server.handlers.packets.CPacketSyncShopItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public final class EntityForgeKeeper extends EntityNPC
{
    private List<ItemStack> stacks = new ArrayList<>();

    public EntityForgeKeeper(World world)
    {
        super(world, null, 100.0F);
        this.isImmuneToFire = false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (this.canInteractWith(player))
        {
            if (!worldObj.isRemote)
            {
                Config cfg = TaleOfKingdoms.proxy.getConfig();

                this.heal(100.0F);
                stacks.clear();

                for (String itemName : cfg.getNames())
                {
                    ItemStack stack = new ItemStack((Item) Item.itemRegistry.getObject(itemName));

                    if (stack.getTagCompound() == null)
                    {
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setInteger("price", cfg.getPrice(itemName));

                    stacks.add(stack);
                }

                NetworkHandler.INSTANCE.sendTo(new CPacketSyncShopItems(stacks), (EntityPlayerMP) player);
            }
        }
        return true;
    }
}
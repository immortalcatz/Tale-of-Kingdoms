package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import kingdoms.server.TaleOfKingdoms;
import kingdoms.server.handlers.Config;
import kingdoms.server.handlers.NetworkHandler;
import kingdoms.server.handlers.packets.client.CPacketSyncShopItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EntityWeaponKeeper extends EntityNPC
{
    private List<ItemStack> stacks = new ArrayList<>();

    public EntityWeaponKeeper(World world)
    {
        super(world, null, 100.0F);
        this.isImmuneToFire = false;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return true;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (!worldObj.isRemote)
        {
            if (this.canInteractWith(player))
            {
                Config cfg = TaleOfKingdoms.proxy.getConfig();

                this.heal(100.0F);
                stacks.clear();

                Objects.requireNonNull(cfg.getNames()).forEach(itemName -> {
                    ItemStack stack = new ItemStack((Item) Item.itemRegistry.getObject(itemName));

                    if (stack.getTagCompound() == null)
                        stack.setTagCompound(new NBTTagCompound());

                    stack.getTagCompound().setInteger("price", cfg.getPrice(itemName));
                    stacks.add(stack);
                });

                NetworkHandler.INSTANCE.sendTo(new CPacketSyncShopItems(this.getEntityId(), stacks), (EntityPlayerMP) player);
            }
        }
        return true;
    }
}
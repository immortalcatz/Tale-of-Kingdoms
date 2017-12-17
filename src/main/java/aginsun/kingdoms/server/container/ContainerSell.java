package aginsun.kingdoms.server.container;

import aginsun.kingdoms.server.entities.TileEntitySell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public final class ContainerSell extends Container
{
    private TileEntitySell entitySell;

    public ContainerSell(TileEntitySell entitySell, InventoryPlayer inventoryPlayer)
    {
        this.entitySell = entitySell;
        this.addSlotToContainer(new Slot(entitySell, 0, 116, 35));
        this.bindPlayerInventory(inventoryPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.entitySell.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i)
    {
        ItemStack stack = null;
        Slot slot = this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack())
        {
            ItemStack stackInslot = slot.getStack();
            stack = stackInslot.copy();

            if (i == 0)
            {
                if (!this.mergeItemStack(stackInslot, 1, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stackInslot, 0, 1, false))
            {
                return null;
            }

            if (stackInslot.getCount() == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }
}
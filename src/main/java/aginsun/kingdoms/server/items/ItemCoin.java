package aginsun.kingdoms.server.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public final class ItemCoin extends Item
{
    public ItemCoin()
    {
        this.maxStackSize = 1;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("taleofkingdoms:coin");
    }
}
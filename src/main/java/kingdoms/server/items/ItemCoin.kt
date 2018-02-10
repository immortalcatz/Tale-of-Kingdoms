package kingdoms.server.items

import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.Item

class ItemCoin: Item()
{
    init
    {
        this.maxStackSize = 1
    }

    override fun registerIcons(iconRegister: IIconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("tok:coin")
    }
}
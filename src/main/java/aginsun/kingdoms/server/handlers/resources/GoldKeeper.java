package aginsun.kingdoms.server.handlers.resources;

import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.item.Item;

@Deprecated
public final class GoldKeeper
{
    public static final GoldKeeper INSTANCE = new GoldKeeper();

    public int priceItem(Item item)
    {
        String name = Item.itemRegistry.getNameForObject(item);

        name = name.substring(10);

        int price = TaleOfKingdoms.proxy.getConfig().getPrice(name);

        if (price == 0)
        {
            return 0;
        }
        return price;
    }
}
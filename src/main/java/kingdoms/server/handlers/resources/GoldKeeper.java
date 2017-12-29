package kingdoms.server.handlers.resources;

import kingdoms.server.TaleOfKingdoms;
import net.minecraft.item.Item;

@Deprecated
public final class GoldKeeper
{
    public static final GoldKeeper INSTANCE = new GoldKeeper();

    public int priceItem(Item item)
    {
        String name = Item.itemRegistry.getNameForObject(item);
        return TaleOfKingdoms.proxy.getConfig().getPrice(name.split(":")[1]);
    }
}
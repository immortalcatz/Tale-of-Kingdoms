package aginsun.kingdoms.server.handlers.resources;

import aginsun.kingdoms.server.TaleOfKingdoms;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

@Deprecated
public final class GoldKeeper
{
    public static final GoldKeeper INSTANCE = new GoldKeeper();

    public int priceItem(Item item)
    {
        ResourceLocation name = Item.REGISTRY.getNameForObject(item);
        return TaleOfKingdoms.proxy.getConfig().getPrice(name.getResourcePath());
    }
}
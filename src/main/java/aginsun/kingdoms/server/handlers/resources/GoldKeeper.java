package aginsun.kingdoms.server.handlers.resources;

@Deprecated
public final class GoldKeeper
{
    public static final GoldKeeper INSTANCE = new GoldKeeper();
    public float flint, clay, iron, diamond, fish, apple, string, feather;

    public int priceItem(String s)
    {
        return 1;
    }
}
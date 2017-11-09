package aginsun.kingdoms.server.handlers.resources;

public final class GoldKeeper
{
    public static int goldTotal, bankGold, hunter = 0;
    public static float flint, clay, iron, diamond, fish, apple, string, feather;

    public static int getGoldTotal()
    {
        return goldTotal;
    }

    public static void addGold(int count)
    {
        goldTotal += count;
    }

    public static void setGoldTotal(int count)
    {
        goldTotal = count;
    }

    public static void decreaseGold(int count)
    {
        goldTotal -= count;
    }

    public static int getBankGold()
    {
        return bankGold;
    }

    public static void addBankGold(int count)
    {
        bankGold += count;
    }

    public static void setBankGold(int count)
    {
        bankGold = count;
    }

    public static void decreaseBankGold(int count)
    {
        bankGold -= count;
    }

    public static int priceItem(String s)
    {
        return 1;
    }
}
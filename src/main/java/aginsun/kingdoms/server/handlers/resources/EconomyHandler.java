package aginsun.kingdoms.server.handlers.resources;

public final class EconomyHandler
{
    private int goldTotal, bankGold;
    public static final EconomyHandler INSTANCE = new EconomyHandler();

    public int getGoldTotal()
    {
        return goldTotal;
    }
    public int getBankGold()
    {
        return bankGold;
    }

    public void setGoldTotal(int count)
    {
        goldTotal = count;

    }
    public void setBankGold(int count)
    {
        bankGold = count;
    }

    public void addGold(int count)
    {
        goldTotal += count;
    }
    public void addBankGold(int count)
    {
        bankGold += count;
    }

    public void decreaseGold(int count)
    {
        goldTotal -= count;
    }
    public void decreaseBankGold(int count)
    {
        bankGold -= count;
    }
}
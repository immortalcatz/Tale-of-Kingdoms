package aginsun.kingdoms.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ConfigTemplate
{
    private HashMap<String, Integer> prices = new HashMap<>(), sales = new HashMap<>();

    public void setPrices(String name, int price)
    {
        prices.put(name, price);
    }

    public void setSales(String name, int price)
    {
        sales.put(name, price);
    }

    public int getPrices(String name)
    {
        return prices.get(name) != null ? prices.get(name) : 0;
    }

    public int getSales(String name)
    {
        return sales.get(name) != null ? prices.get(name) : 0;
    }

    public List<String> getNames()
    {
        return new ArrayList<>(prices.keySet());
    }

    public List<String> getSalesName()
    {
        return new ArrayList<>(sales.keySet());
    }
}
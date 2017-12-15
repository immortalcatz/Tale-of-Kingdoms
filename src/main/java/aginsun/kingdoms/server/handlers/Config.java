package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.server.ConfigTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.*;
import java.util.List;

public final class Config
{
    private final Gson gson;
    private final File config;

    public Config(FMLPreInitializationEvent e)
    {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.config = new File(e.getModConfigurationDirectory(), "TileOfKingdoms.json");

        if (config.length() == 0)
        {
            try
            {
                generate();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public List<String> getNames()
    {
        try
        {
            return read().getNames();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int getPrice(String name)
    {
        try
        {
            return read().getPrices(name);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    private void generate() throws IOException
    {
        ConfigTemplate cfg = new ConfigTemplate();
        cfg.setPrices("apple", 100);
        cfg.setSales("coal", 50);

        write(cfg);
    }

    private void write(ConfigTemplate cfg) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(config)))
        {
            writer.write(gson.toJson(cfg));
        }
    }

    private ConfigTemplate read() throws IOException
    {
        try (JsonReader reader = new JsonReader(new FileReader(config)))
        {
            return gson.fromJson(reader, new TypeToken<ConfigTemplate>() {}.getType());
        }
    }
}
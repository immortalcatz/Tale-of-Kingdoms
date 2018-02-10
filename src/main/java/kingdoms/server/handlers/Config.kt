package kingdoms.server.handlers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import kingdoms.server.ConfigTemplate

import java.io.*

class Config(e: FMLPreInitializationEvent)
{
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val config: File = File(e.modConfigurationDirectory, "TileOfKingdoms.json")

    init
    {
        if (config.length() == 0L)
        {
            try
            {
                generate()
            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }
        }
    }

    fun getNames(): List<String>?
    {
        try
        {
            return read().names
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        return null
    }

    fun getPrice(name: String): Int?
    {
        try
        {
            return read().getPrices(name)
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        return 0
    }

    fun isOp(): Boolean
    {
        try
        {
            return read().isOp
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        return false
    }

    @Throws(IOException::class)
    private fun generate()
    {
        val cfg = ConfigTemplate()
        cfg.setPrices("apple", 5)
        cfg.setPrices("coal", 2)
        cfg.setPrices("compass", 500)
        cfg.setPrices("flint", 50)
        cfg.setPrices("arrow", 48)
        cfg.setPrices("saddle", 250)
        cfg.setPrices("bucket", 87)
        cfg.setPrices("diamond", 1000)
        cfg.setPrices("iron_pickaxe", 850)
        cfg.setPrices("iron_axe", 850)
        cfg.setPrices("iron_sword", 750)
        cfg.setPrices("diamond_sword", 2175)
        cfg.setPrices("golden_apple", 1235)
        cfg.setPrices("diamond_horse_armor", 5000)
        cfg.isOp = false

        write(cfg)
    }

    @Throws(IOException::class)
    private fun write(cfg: ConfigTemplate)
    {
        BufferedWriter(FileWriter(config)).use { writer -> writer.write(gson.toJson(cfg)) }
    }

    @Throws(IOException::class)
    private fun read(): ConfigTemplate
    {
        JsonReader(FileReader(config)).use { reader ->
            return gson.fromJson(reader, object : TypeToken<ConfigTemplate>() {}.type)
        }
    }
}
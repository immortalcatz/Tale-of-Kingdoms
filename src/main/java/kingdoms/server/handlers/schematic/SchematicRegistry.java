package kingdoms.server.handlers.schematic;

import java.util.ArrayList;
import java.util.List;

public final class SchematicRegistry
{
    public static final SchematicRegistry INSTANCE = new SchematicRegistry();

    private boolean registered = false;
    private List<Schematic> schematics = new ArrayList<>(45);

    public void registerAllBuildings(int x, int y, int z)
    {
        schematics.clear();

        addSchematic(new Schematic("schematics/GuildCastle").setPosition(x, y, z));

        addSchematic(new Schematic("schematics/tier1/Tier1").setPosition(x, y, z));
        addSchematic(new Schematic("schematics/tier1/Tier1SmallHouse1").setPosition(x + 6, y + 1, z + 37));
        addSchematic(new Schematic("schematics/tier1/Tier1SmallHouse2").setPosition(x + 6, y + 1, z + 15));
        addSchematic(new Schematic("schematics/tier1/Tier1Blacksmith").setPosition(x + 18, y + 1, z + 4));
        addSchematic(new Schematic("schematics/tier1/Tier1LargeHouse1").setPosition(x + 19, y + 1, z + 35));
        addSchematic(new Schematic("schematics/tier1/Tier1Well").setPosition(x + 23, y + 1, z + 26));
        addSchematic(new Schematic("schematics/tier1/Tier1StockMarket").setPosition(x + 35, y + 1, z + 16));

        addSchematic(new Schematic("schematics/tier2/Tier2").setPosition(x, y, z));
        addSchematic(new Schematic("schematics/tier2/Tier2SmallHouse1").setPosition(x + 59, y + 1, z + 42));
        addSchematic(new Schematic("schematics/tier2/Tier2SmallHouse2").setPosition(x + 14, y + 1, z + 74));
        addSchematic(new Schematic("schematics/tier2/Tier2LargeHouse1").setPosition(x + 59, y + 1, z + 61));
        addSchematic(new Schematic("schematics/tier2/Tier2BuilderHouse").setPosition(x + 14, y + 1, z + 58));
        addSchematic(new Schematic("schematics/tier2/Tier2Barracks").setPosition(x + 57, y + 1, z + 14));
        addSchematic(new Schematic("schematics/tier2/Tier2FoodShop").setPosition(x + 40, y + 1, z + 65));
        addSchematic(new Schematic("schematics/tier2/Tier2BlockShop").setPosition(x + 41, y + 1, z + 42));

        int i1 = x - 10, k1 = z - 5;

        addSchematic(new Schematic("schematics/tier3/Tier3").setPosition(i1, y, k1));
        addSchematic(new Schematic("schematics/tier3/Tier3SmallHouse1").setPosition(i1 + 81, y + 1, k1 + 123));
        addSchematic(new Schematic("schematics/tier3/Tier3SmallHouse2").setPosition(i1 + 81, y + 1, k1 + 47));
        addSchematic(new Schematic("schematics/tier3/Tier3SmallHouse3").setPosition(i1 + 83, y + 1, k1 + 65));
        addSchematic(new Schematic("schematics/tier3/Tier3LargeHouse1").setPosition(i1 + 103, y + 1, k1 + 72));
        addSchematic(new Schematic("schematics/tier3/Tier3Tavern").setPosition(i1 + 35, y + 1, k1 + 123));
        addSchematic(new Schematic("schematics/tier3/Tier3Chapel").setPosition(i1 + 93, y, k1 + 100));
        addSchematic(new Schematic("schematics/tier3/Tier3Library").setPosition(i1 + 103, y + 1, k1 + 20));
        addSchematic(new Schematic("schematics/tier3/Tier3MageHall").setPosition(i1 + 105, y + 1, k1 + 46));

        int j1 = y - 6;

        addSchematic(new Schematic("schematics/tier4/Tier4").setPosition(i1, j1, k1));
        addSchematic(new Schematic("schematics/tier4/Tier4Bridge").setPosition(i1 + 130, y + 1, k1 + 90));
        addSchematic(new Schematic("schematics/tier4/Tier4Castle").setPosition(i1 + 51, j1 - 5, k1 + 151));
        addSchematic(new Schematic("schematics/tier4/Tier4Colloseum").setPosition(i1 + 142, y + 1, k1 + 36));
        addSchematic(new Schematic("schematics/tier4/Tier4EasternTower").setPosition(i1 + 103, y + 1, k1 + 133));
        addSchematic(new Schematic("schematics/tier4/Tier4FishHut").setPosition(i1 + 110, y + 1, k1 + 151));
        addSchematic(new Schematic("schematics/tier4/Tier4LightHouse").setPosition(i1 + 114, y - 1, k1 + 186));
        addSchematic(new Schematic("schematics/tier4/Tier4Mill").setPosition(i1 + 20, y + 1, k1 + 126));
        addSchematic(new Schematic("schematics/tier4/Tier4ObserverPost").setPosition(i1 + 32, y + 1, k1 + 90));
        addSchematic(new Schematic("schematics/tier4/Tier4SmallHouse1").setPosition(i1 + 145, y + 1, k1 + 108));
        addSchematic(new Schematic("schematics/tier4/Tier4SmallHouse2").setPosition(i1 + 145, y + 1, k1 + 122));
        addSchematic(new Schematic("schematics/tier4/Tier4SmallHouse3").setPosition(i1 + 170, y + 1, k1 + 80));
        addSchematic(new Schematic("schematics/tier4/Tier4SmallHouse4").setPosition(i1 + 170, y + 1, k1 + 95));
        addSchematic(new Schematic("schematics/tier4/Tier4LargeHouse").setPosition(i1 + 170, y, k1 + 109));
        addSchematic(new Schematic("schematics/tier4/Tier4NorthernTower1").setPosition(i1 + 144, y + 1, k1 + 77));
        addSchematic(new Schematic("schematics/tier4/Tier4NorthernTower2").setPosition(i1 + 181, y + 1, k1 + 68));
        addSchematic(new Schematic("schematics/tier4/Tier4Stables").setPosition(i1 + 131, y + 1, k1 + 142));
        addSchematic(new Schematic("schematics/tier4/Tier4Zeppelin").setPosition(i1 + 171, y + 1, k1 + 138));
        addSchematic(new Schematic("schematics/tier4/Tier4Stables1").setPosition(i1 + 169, y, k1 + 163));
        addSchematic(new Schematic("schematics/tier4/Tier4Stables2").setPosition(i1 + 131, y, k1 + 164));

        registered = true;
    }

    private void addSchematic(Schematic schematic)
    {
        schematics.add(schematic);
    }

    public boolean isRegistered()
    {
        return registered;
    }

    public Schematic getSchematic(int id)
    {
        return schematics.get(id);
    }
}
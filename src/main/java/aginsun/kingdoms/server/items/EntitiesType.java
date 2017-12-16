package aginsun.kingdoms.server.items;

import java.util.Arrays;

public enum EntitiesType
{
    BANKER("Banker", 5719879, 10722965),
    BARRACKSKEEPER("BarracksKeeper", 4013373, 10592673),
    BUILDERKEEPER("BuilderKeeper", 6700863, 14403840),
    DEFENDARCHER("DefendArcher", 3355443, 0),
    DEFENDBANDIT("DefendBandit", 6915928, 7687745),
    DEFENDKNIGHT("DefendKnight", 6184542, 6915928),
    DEFENDMAGE("DefendMage", 10750207, 14935011),
    DEFENDPALADIN("DefendPaladin", 14935011, 16249925),
    DEFENDPRIEST("DefendPriest", 15786465, 16249925),
    DEFENDWARRIOR("DefendWarrior", 11382189, 16754856),
    FARMER("Farmer", 9533268, 15112501),
    FISHER("Fisher", 6269766, 7687745),
    FOODKEEPER("FoodKeeper", 15452507, 3714306),
    FORGEKEEPER("ForgeKeeper", 9058103, 13399156),
    GUILDKEEPER("GuildKeeper", 10592673, 16711680),
    GUILDMASTER("GuildMaster", 13948116, 12237498),
    GUILDMEMBER("GuildMember", 14079702, 16711680),
    HEADCOMMANDER("HeadCommander", 4864038, 14725412),
    HIRED("Hired", 14079702, 16711680),
    INNKEEPER("InnKeeper", 9533268, 15112501),
    KINGDOMWORKER("KingdomWorker", 15452507, 4473924),
    LIBRARYKEEPER("LibraryKeeper", 8553090, 3881787),
    LONETRAVELLER("LoneTraveller", 3881787, 9731430),
    LOSTVILLAGER("LostVillager", 7687745, 16777215),
    LUMBER("Lumber", 11048278, 12893361),
    MAGEKEEPER("MageKeeper", 12394014, 7566195),
    PRIESTKEEPER("PriestKeeper", 16748060, 16249925),
    QUARRY("Quarry", 4644352, 5520701),
    REFICULGUARDIAN("ReficulGuardian", 3684408, 16711680),
    REFICULMAGE("ReficulMage", 0, 16711680),
    REFICULSOLDIER("ReficulSoldier", 6052956, 16711680),
    SANTACLAUS("SantaClaus", 15991298, 16777215),
    SHOPKEEPER("ShopKeeper", 9058103, 13399156),
    STABLEMASTER("StableMaster", 12434877, 13721182),
    STOCKKEEPER("StockKeeper", 14725553, 10921124),
    TAVERNKEEPER("TavernKeeper", 7687745, 4473924),
    VILLAGEMEMBER("VillageMember", 6033938, 4473924),
    WEAPONKEEPER("WeaponKeeper", 5520701, 0),
    WORKERMEMBER("WorkerMember", 15452507, 4473924);

    private final String name;
    private final int base, spots;

    /**
     * EntitiesType
     * @param name - entity name for spawn
     * @param base - base colour for spawn egg
     * @param spots - spots colour for spawn egg
     */
    EntitiesType(String name, int base, int spots)
    {
        this.name = name;
        this.base = base;
        this.spots = spots;
    }

    public static EntitiesType getByID(int id)
    {
        return Arrays.stream(values()).filter(type -> type.ordinal() == id).findFirst().orElse(STABLEMASTER);
    }

    public String getName()
    {
        return name;
    }

    public int getBase()
    {
        return base;
    }

    public int getSpots()
    {
        return spots;
    }
}
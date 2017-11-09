package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.server.TaleOfKingdoms;
import cpw.mods.fml.common.registry.EntityRegistry;
import java.awt.Color;

public final class EntityRegistryToK
{
    private int id;

    public void registerEntities()
    {
        registerEntity(EntityStableMaster.class, "StableMaster");
        registerEntity(EntityFisher.class, "Fisher");
        registerEntity(EntityLumber.class, "Lumber");
        registerEntity(EntityWorkerMember.class, "WorkerMember");
        registerEntity(EntityDefendMarker.class, "DefendMark");
        registerEntity(EntityMageKeeper.class, "MageKeeper");
        registerEntity(EntityGuildMaster.class, "GuildMaster");
        registerEntity(EntityInnKeeper.class, "InnKeeper");
        registerEntity(EntityHunterKeeper.class, "GuildKeeper");
        registerEntity(EntityBuilderKeeper.class, "BuilderKeeper");
        registerEntity(EntityBankerKeeper.class, "Banker");
        registerEntity(EntityBarracksKeeper.class, "BarracksKeeper");
        registerEntity(EntityDefendArcher.class, "DefendArcher");
        registerEntity(EntityDefendBandit.class, "DefendBandit");
        registerEntity(EntityDefendKnight.class, "DefendKnight");
        registerEntity(EntityDefendMage.class, "DefendMage");
        registerEntity(EntityDefendPaladin.class, "DefendPaladin");
        registerEntity(EntityDefendPriest.class, "DefendPriest");
        registerEntity(EntityDefendWarrior.class, "DefendWarrior");
        registerEntity(EntityFarmerKeeper.class, "Farmer");
        registerEntity(EntityFoodKeeper.class, "FoodKeeper");
        registerEntity(EntityQuarry.class, "Quarry");
        registerEntity(EntityWeaponKeeper.class, "WeaponKeeper");
        registerEntity(EntityGuildMember.class, "GuildMember");
        registerEntity(EntityHired.class, "Hired");
        registerEntity(EntityForgeKeeper.class, "ForgeKeeper");
        registerEntity(EntityHeadCommander.class, "HeadCommander");
        registerEntity(EntityKingdomWorker.class, "KingdomWorker");
        registerEntity(EntityLibraryKeeper.class, "LibraryKeeper");
        registerEntity(EntityLoneTraveller.class, "LoneTraveller");
        registerEntity(EntityLostVillager.class, "LostVillager");
        registerEntity(EntityMarkerKeeper.class, "Marker");
        registerEntity(EntityMarker2Keeper.class, "Marker2");
        registerEntity(EntityPriestKeeper.class, "PriestKeeper");
        registerEntity(EntityReficulGuardian.class, "ReficulGuardian");
        registerEntity(EntityReficulMage.class, "ReficulMage");
        registerEntity(EntityReficulSoldier.class, "ReficulSoldier");
        registerEntity(EntityShopKeeper.class, "ShopKeeper");
        registerEntity(EntityStockKeeper.class, "StockKeeper");
        registerEntity(EntityTavernKeeper.class, "TavernKeeper");
        registerEntity(EntityVillageMember.class, "VillageMember");
    }

    private void registerEntity(Class entityClass, String name)
    {
        id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, name, id++, Color.red.getRGB(), Color.green.getRGB());
        EntityRegistry.registerModEntity(entityClass, name, id++, TaleOfKingdoms.instance, 128, 1, true);
    }
}
package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.server.TaleOfKingdoms;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

@Deprecated
public final class EntityRegistryToK
{
    private int id = 0;

    public EntityRegistryToK()
    {
        register(EntityStableMaster.class, "StableMaster");
        register(EntityFisher.class, "Fisher");
        register(EntityLumber.class, "Lumber");
        register(EntityWorkerMember.class, "WorkerMember");
        register(EntityDefendMarker.class, "DefendMark");
        register(EntityMageKeeper.class, "MageKeeper");
        register(EntityGuildMaster.class, "GuildMaster");
        register(EntityInnKeeper.class, "InnKeeper");
        register(EntityHunterKeeper.class, "GuildKeeper");
        register(EntityBuilderKeeper.class, "BuilderKeeper");
        register(EntityBankerKeeper.class, "Banker");
        register(EntityBarracksKeeper.class, "BarracksKeeper");
        register(EntityDefendArcher.class, "DefendArcher");
        register(EntityDefendBandit.class, "DefendBandit");
        register(EntityDefendKnight.class, "DefendKnight");
        register(EntityDefendMage.class, "DefendMage");
        register(EntityDefendPaladin.class, "DefendPaladin");
        register(EntityDefendPriest.class, "DefendPriest");
        register(EntityDefendWarrior.class, "DefendWarrior");
        register(EntityFarmerKeeper.class, "Farmer");
        register(EntityFoodKeeper.class, "FoodKeeper");
        register(EntityQuarry.class, "Quarry");
        register(EntityWeaponKeeper.class, "WeaponKeeper");
        register(EntityGuildMember.class, "GuildMember");
        register(EntityHired.class, "Hired");
        register(EntityForgeKeeper.class, "ForgeKeeper");
        register(EntityHeadCommander.class, "HeadCommander");
        register(EntityKingdomWorker.class, "KingdomWorker");
        register(EntityLibraryKeeper.class, "LibraryKeeper");
        register(EntityLoneTraveller.class, "LoneTraveller");
        register(EntityLostVillager.class, "LostVillager");
        register(EntityMarkerKeeper.class, "Marker");
        register(EntityMarker2Keeper.class, "Marker2");
        register(EntityPriestKeeper.class, "PriestKeeper");
        register(EntityReficulGuardian.class, "ReficulGuardian");
        register(EntityReficulMage.class, "ReficulMage");
        register(EntityReficulSoldier.class, "ReficulSoldier");
        register(EntityShopKeeper.class, "ShopKeeper");
        register(EntityStockKeeper.class, "StockKeeper");
        register(EntityTavernKeeper.class, "TavernKeeper");
        register(EntityVillageMember.class, "VillageMember");
    }

    private void register(Class<? extends Entity> entity, String name)
    {
        EntityRegistry.registerModEntity(entity, name, id++, TaleOfKingdoms.instance, 64, 1, false);
    }
}
package aginsun.kingdoms.server.handlers;

import aginsun.kingdoms.client.render.RenderBipedToK;
import aginsun.kingdoms.server.TaleOfKingdoms;
import aginsun.kingdoms.server.entities.*;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public final class EntitiesRegister
{
    private byte id = 0;
    public static final EntitiesRegister INSTANCE = new EntitiesRegister();

    public void register()
    {
        setRegister(EntityStableMaster.class, "StableMaster");
        setRegister(EntityFisher.class, "Fisher");
        setRegister(EntityLumber.class, "Lumber");
        setRegister(EntityWorkerMember.class, "WorkerMember");
        setRegister(EntityDefendMarker.class, "DefendMark");
        setRegister(EntityMageKeeper.class, "MageKeeper");
        setRegister(EntityGuildMaster.class, "GuildMaster");
        setRegister(EntityInnKeeper.class, "InnKeeper");
        setRegister(EntityHunterKeeper.class, "GuildKeeper");
        setRegister(EntityBuilderKeeper.class, "BuilderKeeper");
        setRegister(EntityBankerKeeper.class, "Banker");
        setRegister(EntityBarracksKeeper.class, "BarracksKeeper");
        setRegister(EntityDefendArcher.class, "DefendArcher");
        setRegister(EntityDefendBandit.class, "DefendBandit");
        setRegister(EntityDefendKnight.class, "DefendKnight");
        setRegister(EntityDefendMage.class, "DefendMage");
        setRegister(EntityDefendPaladin.class, "DefendPaladin");
        setRegister(EntityDefendPriest.class, "DefendPriest");
        setRegister(EntityDefendWarrior.class, "DefendWarrior");
        setRegister(EntityFarmerKeeper.class, "Farmer");
        setRegister(EntityFoodKeeper.class, "FoodKeeper");
        setRegister(EntityQuarry.class, "Quarry");
        setRegister(EntityWeaponKeeper.class, "WeaponKeeper");
        setRegister(EntityGuildMember.class, "GuildMember");
        setRegister(EntityHired.class, "Hired");
        setRegister(EntityForgeKeeper.class, "ForgeKeeper");
        setRegister(EntityHeadCommander.class, "HeadCommander");
        setRegister(EntityKingdomWorker.class, "KingdomWorker");
        setRegister(EntityLibraryKeeper.class, "LibraryKeeper");
        setRegister(EntityLoneTraveller.class, "LoneTraveller");
        setRegister(EntityLostVillager.class, "LostVillager");
        setRegister(EntityMarkerKeeper.class, "Marker");
        setRegister(EntityMarker2Keeper.class, "Marker2");
        setRegister(EntityPriestKeeper.class, "PriestKeeper");
        setRegister(EntityReficulGuardian.class, "ReficulGuardian");
        setRegister(EntityReficulMage.class, "ReficulMage");
        setRegister(EntityReficulSoldier.class, "ReficulSoldier");
        setRegister(EntityShopKeeper.class, "ShopKeeper");
        setRegister(EntityStockKeeper.class, "StockKeeper");
        setRegister(EntityTavernKeeper.class, "TavernKeeper");
        setRegister(EntityVillageMember.class, "VillageMember");
    }

    public void render()
    {
        setRender(EntityGuildMaster.class, "head");
        setRender(EntityInnKeeper.class, "inn");
        setRender(EntityBuilderKeeper.class, "builder");
        setRender(EntityHunterKeeper.class, "head");
        setRender(EntityBankerKeeper.class, "banker");
        setRender(EntityBarracksKeeper.class, "guardelite");
        setRender(EntityDefendArcher.class, "hunter");
        setRender(EntityDefendBandit.class, "bandit");
        setRender(EntityDefendKnight.class, "knight");
        setRender(EntityDefendMage.class, "wizard");
        setRender(EntityDefendMarker.class, "");
        setRender(EntityDefendPaladin.class, "paladin");
        setRender(EntityDefendPriest.class, "priest");
        setRender(EntityDefendWarrior.class, "warrior");
        setRender(EntityFarmerKeeper.class, "inn");
        setRender(EntityFoodKeeper.class, "food");
        setRender(EntityLumber.class, "foremanlumber");
        setRender(EntityWeaponKeeper.class, "smith");
        setRender(EntityGuildMember.class, "member");
        setRender(EntityHired.class, "guild");
        setRender(EntityForgeKeeper.class, "forge");
        setRender(EntityHeadCommander.class, "headcommander");
        setRender(EntityKingdomWorker.class, "worker");
        setRender(EntityLibraryKeeper.class, "librarian");
        setRender(EntityLoneTraveller.class, "lone");
        setRender(EntityLostVillager.class, "ac");
        setRender(EntityMarkerKeeper.class, "");
        setRender(EntityMarker2Keeper.class, "");
        setRender(EntityPriestKeeper.class, "headpriest");
        setRender(EntityReficulGuardian.class, "reficulGuardian");
        setRender(EntityReficulMage.class, "reficulMage");
        setRender(EntityReficulSoldier.class, "reficulSoldier");
        setRender(EntityShopKeeper.class, "forge");
        setRender(EntityStockKeeper.class, "stock");
        setRender(EntityTavernKeeper.class, "tavern");
        setRender(EntityVillageMember.class, "man1");
        setRender(EntityWorkerMember.class, "worker");
        setRender(EntityMageKeeper.class, "headmage");
        setRender(EntityQuarry.class, "foremanquarry");
        setRender(EntityStableMaster.class, "stable");
        setRender(EntityFisher.class, "fisher");
    }

    private void setRegister(Class<? extends Entity> entity, String name)
    {
        EntityRegistry.registerModEntity(entity, name, id++, TaleOfKingdoms.instance, 64, 1, false);
    }

    @SideOnly(Side.CLIENT)
    private void setRender(Class<? extends Entity> entity, String location)
    {
        RenderingRegistry.registerEntityRenderingHandler(entity, new RenderBipedToK(new ModelBiped(), 0.4F, location));
    }
}
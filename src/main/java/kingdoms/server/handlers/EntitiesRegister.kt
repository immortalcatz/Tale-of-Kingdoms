package kingdoms.server.handlers

import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.registry.EntityRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import kingdoms.client.render.RenderBipedToK
import kingdoms.server.TaleOfKingdoms
import kingdoms.server.entities.*
import net.minecraft.client.model.ModelBiped
import net.minecraft.entity.Entity
import java.util.*

object EntitiesRegister
{
    private var id: Int = 0
    private val isNewYear: Boolean
    
    init
    {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DATE)
        isNewYear = (month == 12 || month == 1) && (day in 1..31)
    }

    fun register()
    {
        setRegister(EntityFisher::class.java, "Fisher")
        setRegister(EntityLumber::class.java, "Lumber")
        setRegister(EntityWorkerMember::class.java, "WorkerMember")
        setRegister(EntityDefendMarker::class.java, "DefendMark")
        setRegister(EntityMageKeeper::class.java, "MageKeeper")
        setRegister(EntityGuildMaster::class.java, "GuildMaster")
        setRegister(EntityInnKeeper::class.java, "InnKeeper")
        setRegister(EntityHunterKeeper::class.java, "GuildKeeper")
        setRegister(EntityBuilderKeeper::class.java, "BuilderKeeper")
        setRegister(EntityBankerKeeper::class.java, "Banker")
        setRegister(EntityBarracksKeeper::class.java, "BarracksKeeper")
        setRegister(EntityDefendArcher::class.java, "DefendArcher")
        setRegister(EntityDefendBandit::class.java, "DefendBandit")
        setRegister(EntityDefendKnight::class.java, "DefendKnight")
        setRegister(EntityDefendMage::class.java, "DefendMage")
        setRegister(EntityDefendPaladin::class.java, "DefendPaladin")
        setRegister(EntityDefendPriest::class.java, "DefendPriest")
        setRegister(EntityDefendWarrior::class.java, "DefendWarrior")
        setRegister(EntityFarmerKeeper::class.java, "Farmer")
        setRegister(EntityFoodKeeper::class.java, "FoodKeeper")
        setRegister(EntityQuarry::class.java, "Quarry")
        setRegister(EntityWeaponKeeper::class.java, "WeaponKeeper")
        setRegister(EntityGuildMember::class.java, "GuildMember")
        setRegister(EntityHired::class.java, "Hired")
        setRegister(EntityForgeKeeper::class.java, "ForgeKeeper")
        setRegister(EntityHeadCommander::class.java, "HeadCommander")
        setRegister(EntityKingdomWorker::class.java, "KingdomWorker")
        setRegister(EntityLibraryKeeper::class.java, "LibraryKeeper")
        setRegister(EntityLoneTraveller::class.java, "LoneTraveller")
        setRegister(EntityLostVillager::class.java, "LostVillager")
        setRegister(EntityMarkerKeeper::class.java, "Marker")
        setRegister(EntityMarker2Keeper::class.java, "Marker2")
        setRegister(EntityPriestKeeper::class.java, "PriestKeeper")
        setRegister(EntityReficulGuardian::class.java, "ReficulGuardian")
        setRegister(EntityReficulMage::class.java, "ReficulMage")
        setRegister(EntityReficulSoldier::class.java, "ReficulSoldier")
        setRegister(EntityShopKeeper::class.java, "ShopKeeper")
        setRegister(EntityStockKeeper::class.java, "StockKeeper")
        setRegister(EntityTavernKeeper::class.java, "TavernKeeper")
        setRegister(EntityVillageMember::class.java, "VillageMember")

        if (isNewYear)
            setRegister(EntitySantaClaus::class.java, "SantaClaus")
    }

    fun render()
    {
        setRender(EntityGuildMaster::class.java, "head")
        setRender(EntityInnKeeper::class.java, "inn")
        setRender(EntityBuilderKeeper::class.java, "builder")
        setRender(EntityHunterKeeper::class.java, "head")
        setRender(EntityBankerKeeper::class.java, "banker")
        setRender(EntityBarracksKeeper::class.java, "guardelite")
        setRender(EntityDefendArcher::class.java, "hunter")
        setRender(EntityDefendBandit::class.java, "bandit")
        setRender(EntityDefendKnight::class.java, "knight")
        setRender(EntityDefendMage::class.java, "wizard")
        setRender(EntityDefendMarker::class.java, "")
        setRender(EntityDefendPaladin::class.java, "paladin")
        setRender(EntityDefendPriest::class.java, "priest")
        setRender(EntityDefendWarrior::class.java, "warrior")
        setRender(EntityFarmerKeeper::class.java, "inn")
        setRender(EntityFoodKeeper::class.java, "food")
        setRender(EntityLumber::class.java, "foremanlumber")
        setRender(EntityWeaponKeeper::class.java, "smith")
        setRender(EntityGuildMember::class.java, "member")
        setRender(EntityHired::class.java, "guild")
        setRender(EntityForgeKeeper::class.java, "forge")
        setRender(EntityHeadCommander::class.java, "headcommander")
        setRender(EntityKingdomWorker::class.java, "worker")
        setRender(EntityLibraryKeeper::class.java, "librarian")
        setRender(EntityLoneTraveller::class.java, "lone")
        setRender(EntityLostVillager::class.java, "ac")
        setRender(EntityMarkerKeeper::class.java, "")
        setRender(EntityMarker2Keeper::class.java, "")
        setRender(EntityPriestKeeper::class.java, "headpriest")
        setRender(EntityReficulGuardian::class.java, "reficulGuardian")
        setRender(EntityReficulMage::class.java, "reficulMage")
        setRender(EntityReficulSoldier::class.java, "reficulSoldier")
        setRender(EntityShopKeeper::class.java, "forge")
        setRender(EntityStockKeeper::class.java, "stock")
        setRender(EntityTavernKeeper::class.java, "tavern")
        setRender(EntityVillageMember::class.java, "man1")
        setRender(EntityWorkerMember::class.java, "worker")
        setRender(EntityMageKeeper::class.java, "headmage")
        setRender(EntityQuarry::class.java, "foremanquarry")
        setRender(EntityFisher::class.java, "fisher")

        if (isNewYear)
            setRender(EntitySantaClaus::class.java, "santaClaus")
    }

    fun isNewYear(): Boolean
    {
        return isNewYear
    }

    private fun setRegister(entity: Class<out Entity>, name: String)
    {
        EntityRegistry.registerModEntity(entity, name, id++, TaleOfKingdoms.instance, 64, 1, false)
    }

    @SideOnly(Side.CLIENT)
    private fun setRender(entity: Class<out Entity>, location: String)
    {
        RenderingRegistry.registerEntityRenderingHandler(entity, RenderBipedToK(ModelBiped(), 0.4F, location))
    }
}
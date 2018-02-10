package kingdoms.client.render

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.model.ModelBiped
import net.minecraft.client.renderer.entity.RenderBiped
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation

@SideOnly(Side.CLIENT)
class RenderBipedToK(model: ModelBiped, shadow: Float, private val location: String): RenderBiped(model, shadow)
{
    override fun getEntityTexture(entity: Entity): ResourceLocation
    {
        return ResourceLocation("tok", "textures/entities/$location.png")
    }
}
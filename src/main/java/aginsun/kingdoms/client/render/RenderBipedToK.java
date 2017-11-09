package aginsun.kingdoms.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public final class RenderBipedToK extends RenderBiped
{
    private final String location;

    public RenderBipedToK(ModelBiped par1ModelBiped, float par2, String location)
    {
        super(par1ModelBiped, par2);
        this.location = "textures/entities/" + location + ".png";
    }

    @Override
    protected ResourceLocation getEntityTexture(final Entity entity)
    {
        return new ResourceLocation("taleofkingdoms", location);
    }
}
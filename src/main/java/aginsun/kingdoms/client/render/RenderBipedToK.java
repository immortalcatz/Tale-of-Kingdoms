package aginsun.kingdoms.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class RenderBipedToK extends RenderBiped
{
    private final String location;

    public RenderBipedToK(ModelBiped model, float par2, String location)
    {
        super(model, par2);
        this.location = location;
    }

    @Override
    protected ResourceLocation getEntityTexture(final Entity entity)
    {
        return new ResourceLocation("taleofkingdoms", "textures/entities/" + location + ".png");
    }
}
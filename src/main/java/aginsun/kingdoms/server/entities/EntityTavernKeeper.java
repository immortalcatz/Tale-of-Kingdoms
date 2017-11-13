package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.client.gui.GuiTavernGame;
import aginsun.kingdoms.api.entities.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class EntityTavernKeeper extends EntityNPC
{
    private World worldObj;

    public EntityTavernKeeper(World world)
    {
        super(world, null, 100.0F);
        this.worldObj = world;
        this.isImmuneToFire = false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return true;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if(this.canInteractWith(entityplayer))
        {
            if(!worldObj.isRemote)
            {
                entityplayer.addChatMessage(new ChatComponentText("One-Eyed Gambler: Feeling a bit lucky eh?"));
            }
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.displayGuiScreen(new GuiTavernGame(entityplayer, worldObj));
        }
        return true;
    }
}
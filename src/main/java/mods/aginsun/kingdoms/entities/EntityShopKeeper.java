package mods.aginsun.kingdoms.entities;

import mods.aginsun.kingdoms.client.guis.GuiShopList;
import mods.aginsun.kingdoms.entities.api.EntityNPC;
import mods.aginsun.kingdoms.handlers.GoldKeeper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;

public final class EntityShopKeeper extends EntityNPC
{
    private World world;
    private Item[] items = new Item[200];
    private StringTranslate st = new StringTranslate();

    public EntityShopKeeper(World world)
    {
        super(world, null, 100.0F);
        this.world = world;
        this.isImmuneToFire = false;
    }

    @Override
    protected boolean canDespawn()
    {
        return false;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return !this.isDead && entityplayer.getDistanceSqToEntity(this) <= 64.0D;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return true;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (this.canInteractWith(entityplayer))
        {
            this.heal(100.0F);
            final Minecraft minecraft = Minecraft.getMinecraft();
            int i = 0, j = 0;
            String s = "";

            for (int k = 256; k < 32000; ++k)
            {
                boolean flag2 = false;
                boolean flag3 = false;
                if(Item.getItemById(k) != null)
                {
                    Item item = Item.getItemById(k);
                    if(item instanceof ItemFood)
                    {
                        flag2 = true;
                    }

                    if(item instanceof ItemArmor)
                    {
                        flag3 = true;
                    }

                    if(item instanceof ItemSword)
                    {
                        flag3 = true;
                    }

                    if(item instanceof ItemTool)
                    {
                        flag3 = true;
                    }

                    if(item != null)
                    {
                        s = item.getUnlocalizedName();
                    }

                    if(s != null)
                    {
                        j = GoldKeeper.priceItem(s);
                    }

                    String s1 = item.getUnlocalizedName() + ".name";
                    String s2 = this.st.translateKey(s1);
                    Item l = Item.getItemById(k);
                    if(l == Items.flint_and_steel || l == Items.coal || l == Items.stick ||
                            l == Items.gunpowder || l == Items.wheat || l == Items.painting ||
                            l == Items.sign || l == Items.wooden_door || l == Items.bucket ||
                            l == Items.water_bucket || l == Items.lava_bucket || l == Items.minecart ||
                            l == Items.saddle || l == Items.iron_door || l == Items.redstone ||
                            l == Items.boat || l == Items.leather || l == Items.milk_bucket ||
                            l == Items.reeds || l == Items.paper || l == Items.book ||
                            l == Items.egg || l == Items.compass || l == Items.fishing_rod ||
                            l == Items.clock || l == Items.glowstone_dust || l == Items.bone ||
                            l == Items.sugar || l == Items.bed || l == Items.repeater ||
                            l == Items.map || l == Items.shears || l == Items.pumpkin_seeds ||
                            l == Items.melon_seeds || l == Items.ender_pearl || l == Items.emerald || flag2 || flag3)
                    {
                        j = 0;
                    }

                    if(j > 0 && !s1.equals("null.name") && !s1.equals(s2))
                    {
                        this.items[i] = Item.getItemById(k);
                        ++i;
                    }
                }
            }
            minecraft.displayGuiScreen(new GuiShopList(entityplayer, this.world, this.items));
        }
        return true;
    }
}
package aginsun.kingdoms.server.entities;

import aginsun.kingdoms.api.entities.EntityNPC;
import aginsun.kingdoms.server.PlayerProvider;
import aginsun.kingdoms.server.handlers.resources.GuildHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public final class EntityGuildMember extends EntityNPC
{
    private EntityPlayer player;
    private boolean fight = false, isSwinging;
    private int counter = 0, field_110158_av, attackStrength;
    private static ItemStack defaultHeldItem = new ItemStack(Items.iron_sword, 1);

    public EntityGuildMember(World world)
    {
        super(world, defaultHeldItem, 40.0F);
        this.isImmuneToFire = true;
        this.attackStrength = 15;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        this.player = entityplayer;

        if (GuildHandler.INSTANCE.getGuildFightEnded())
        {
            ItemStack itemstack = entityplayer.getHeldItem();

            if (itemstack != null)
            {
                if (itemstack.getItem() == Items.wooden_sword)
                {
                    defaultHeldItem = new ItemStack(Items.wooden_sword, 1);
                    //entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);

                    if (!worldObj.isRemote)
                    {
                        entityplayer.addChatMessage(new ChatComponentText("Guild Member: Get Ready."));
                    }
                    this.fight = true;
                }
                else if (!worldObj.isRemote)
                {
                    entityplayer.addChatMessage(new ChatComponentText("Guild Member: Greetings. You seem like a tough fighter. Give me a wooden sword and lets have a sparing match!"));
                }
            }
            else if (!worldObj.isRemote)
            {
                entityplayer.addChatMessage(new ChatComponentText("Guild Member: Greetings. You seem like a tough fighter. Give me a wooden sword and lets have a sparing match!"));
            }
        }
        else if (!worldObj.isRemote)
        {
            entityplayer.addChatMessage(new ChatComponentText("Guild Member: Damn this Reficules"));
        }
        return true;
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        if (this.fight)
        {
            PlayerProvider.get(player).addGlory(50, player);

            if(this.player != null && !worldObj.isRemote)
            {
                this.player.addChatMessage(new ChatComponentText("Guild Member: Your a good fighter my friend, I will let the guild master know of your strength."));
            }
        }
    }

   protected void updateEntityActionState() {
      super.updateEntityActionState();
      if(this.fight) {
         ++this.counter;
         if(this.counter == 10 && !worldObj.isRemote) {
            this.player.addChatMessage(new ChatComponentText("Guild Member: 3"));
         }

         if(this.counter == 20 && !worldObj.isRemote) {
            this.player.addChatMessage(new ChatComponentText("Guild Member: 2"));
         }

         if(this.counter == 30 && !worldObj.isRemote) {
            this.player.addChatMessage(new ChatComponentText("Guild Member: 1"));
         }

         if(this.counter == 40 && !worldObj.isRemote) {
            this.player.addChatMessage(new ChatComponentText("Guild Member: Begin!"));
            this.entityToAttack = this.player;
         }
      }

      byte i = 6;
      if(this.isSwinging) {
         ++this.field_110158_av;
         if(this.field_110158_av >= i) {
            this.field_110158_av = 0;
            this.isSwinging = false;
         }
      } else {
         this.field_110158_av = 0;
      }

      this.swingProgress = (float)this.field_110158_av / (float)i;
      if(this.entityToAttack == null && !this.hasPath()) {
         List list = worldObj.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(20.0D, 4.0D, 20.0D));
         if(!list.isEmpty()) {
            Entity entity = (Entity)list.get(worldObj.rand.nextInt(list.size()));
            if(entity instanceof EntityCreeper) {
               entity.setDead();
            } else if(entity instanceof EntityMob || entity instanceof EntityReficulSoldier || entity instanceof EntityReficulGuardian || entity instanceof EntityReficulMage) {
               defaultHeldItem = new ItemStack(Items.iron_sword, 1);
               this.entityToAttack = entity;
            }
         }
      }

   }

   protected void jump() {
      Random random = new Random();
      if(random.nextInt(15) == 0) {
         this.motionY = 0.41999998688697815D;
         if(this.isSprinting()) {
            float f = this.rotationYaw * 0.01745329F;
            this.motionX -= (double)(MathHelper.sin(f) * 0.2F);
            this.motionZ += (double)(MathHelper.cos(f) * 0.2F);
         }

         this.isAirBorne = true;
      }

   }

   protected void attackEntity(Entity entity, float f) {
      if(this.attackTime <= 0 && f < 2.0F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
         this.attackTime = 20;
         this.swingItem();
         this.attackEntityAsMob(entity);
      }

   }

   public boolean attackEntityAsMob(Entity entity) {
      int i = this.attackStrength;
      if(this.isPotionActive(Potion.damageBoost)) {
         i += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
      }

      if(this.isPotionActive(Potion.weakness)) {
         i -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
      }

      return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)i);
   }

   public void swingItem() {
      if(!this.isSwinging || this.field_110158_av < 0) {
         this.field_110158_av = -1;
         this.isSwinging = true;
      }

   }

}

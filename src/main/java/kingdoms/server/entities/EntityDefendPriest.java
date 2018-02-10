package kingdoms.server.entities;

import kingdoms.api.entities.EntityNPC;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public final class EntityDefendPriest extends EntityNPC
{
   private static ItemStack defaultHeldItem = new ItemStack(Items.stick, 1);
   private boolean follow;
   private boolean checkPlayer;
   private int healCounter;
   private int whenHealing;
   protected int attackStrength;
   public boolean isSwinging;
   public int field_110158_av;


   public EntityDefendPriest(World world) {
      super(world, defaultHeldItem, 40.0F);
      this.follow = false;
      this.checkPlayer = true;
      this.healCounter = 0;
      this.whenHealing = 0;
      this.isImmuneToFire = false;
      this.attackStrength = 10;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(this.whenHealing < 50) {
         for(int minecraft = 0; minecraft < 2; ++minecraft) {
            this.worldObj.spawnParticle("heart", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
         }
      }

      ++this.whenHealing;
      if(this.whenHealing > 500) {
         this.whenHealing = 100;
      }

      if(this.follow)
      {
          List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

          for (Entity entity : list)
          {
              if (entity instanceof EntityPlayer)
              {
                  EntityPlayer player = (EntityPlayer) entity;

                  PathEntity pathentity;
                  if (player.getDistanceToEntity(this) > 5.0F && player.getDistanceToEntity(this) < 18.0F)
                  {
                      pathentity = this.worldObj.getPathEntityToEntity(this, player, 16.0F, true, false, false, true);
                  }
                  else
                  {
                      pathentity = null;
                  }

                  this.setPathToEntity(pathentity);
              }
          }
      }
   }

   public boolean interact(EntityPlayer entityplayer) {
      if(!this.follow) {
         this.follow = true;
         if(!this.worldObj.isRemote) {
            entityplayer.addChatMessage(new ChatComponentText("Priest: I will follow you."));
         }
      } else {
         this.follow = false;
         if(!this.worldObj.isRemote) {
            entityplayer.addChatMessage(new ChatComponentText("Priest: I will guard this area."));
         }
      }

      return true;
   }

   protected void updateEntityActionState() {
      super.updateEntityActionState();
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

      if (this.checkPlayer)
      {
          List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30, 30, 30));

          for (Entity entity : list)
          {
              if (entity instanceof EntityPlayer)
              {
                  if (entity.getDistanceSqToEntity(this) <= 64.0D)
                  {
                      this.follow = true;
                  }
              }
          }
      }

      this.checkPlayer = false;
      boolean var5 = false;
      List var6 = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(16.0D, 4.0D, 16.0D));
      if(!var6.isEmpty()) {
         EntityLivingBase entityliving = (EntityLivingBase)var6.get(this.worldObj.rand.nextInt(var6.size()));
         if(this.healCounter > 20 && (entityliving instanceof EntityDefendBandit || entityliving instanceof EntityDefendKnight || entityliving instanceof EntityDefendMage || entityliving instanceof EntityDefendPaladin || entityliving instanceof EntityDefendWarrior || entityliving instanceof EntityDefendArcher || entityliving instanceof EntityHired || entityliving instanceof EntityPlayer || entityliving instanceof EntityPlayerSP) && entityliving.getHealth() < 15.0F) {
            entityliving.heal(2.0F);
            var5 = true;
            this.whenHealing = 0;
         }
      }

      if(var5) {
         this.healCounter = 0;
      }

      ++this.healCounter;
   }

   public ItemStack getHeldItem() {
      return defaultHeldItem;
   }

   public boolean attackEntityFrom(DamageSource damagesource, int i) {
      boolean flag = true;
      Entity entity = damagesource.getSourceOfDamage();
      if(entity instanceof EntityDefendBandit || entity instanceof EntityDefendKnight || entity instanceof EntityDefendPaladin || entity instanceof EntityDefendWarrior || entity instanceof EntityDefendArcher || entity instanceof EntityHired || entity instanceof EntityDefendMage || entity instanceof EntityPlayer) {
         flag = false;
      }

      if(flag) {
         super.attackEntityFrom(damagesource, (float)i);
      }

      return true;
   }

   public void onDeath(DamageSource damagesource) {}

   public void swingItem() {
      if(!this.isSwinging || this.field_110158_av < 0) {
         this.field_110158_av = -1;
         this.isSwinging = true;
      }

   }

}

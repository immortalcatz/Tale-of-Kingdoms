package aginsun.kingdoms.server.entities;

import cpw.mods.fml.client.FMLClientHandler;
import aginsun.kingdoms.api.EntityNPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public final class EntityDefendMage extends EntityNPC {

   private EntityPlayer player;
   private boolean follow;
   private boolean checkPlayer;
   protected int attackStrength;
   public boolean isSwinging;
   public int field_110158_av;


   public EntityDefendMage(World world) {
      super(world, new ItemStack(Items.stick), 40.0F);
      this.player = FMLClientHandler.instance().getClient().thePlayer;
      this.follow = false;
      this.checkPlayer = true;
      this.worldObj = world;
      this.isImmuneToFire = false;
      this.attackStrength = 10;
   }

   public void onLivingUpdate() {
      super.onLivingUpdate();
      if(this.follow) {
         Minecraft minecraft = Minecraft.getMinecraft();
         EntityClientPlayerMP entityplayersp = minecraft.thePlayer;
         if(entityplayersp != null) {
            float f = entityplayersp.getDistanceToEntity(this);
            PathEntity pathentity;
            if(f > 5.0F && f < 18.0F) {
               pathentity = this.worldObj.getPathEntityToEntity(this, entityplayersp, 16.0F, true, false, false, true);
            } else {
               pathentity = null;
            }

            this.setPathToEntity(pathentity);
         }
      }

   }

   public boolean interact(EntityPlayer entityplayer) {
      this.player = entityplayer;
      if(!this.follow) {
         this.follow = true;
         if(!this.world.isRemote) {
            entityplayer.addChatMessage(new ChatComponentText("Mage: I will follow you."));
         }
      } else {
         this.follow = false;
         if(!this.world.isRemote) {
            entityplayer.addChatMessage(new ChatComponentText("Mage: I will guard this area."));
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
      Entity entity1;
      if(this.checkPlayer) {
         for(int list = 0; list < this.worldObj.loadedEntityList.size(); ++list) {
            entity1 = (Entity)this.worldObj.loadedEntityList.get(list);
            if(entity1 instanceof EntityPlayer) {
               this.player = (EntityPlayer)entity1;
            }
         }

         if(this.player != null && this.player.getDistanceSqToEntity(this) <= 64.0D) {
            this.follow = true;
         }
      }

      this.checkPlayer = false;
      if(this.entityToAttack == null && !this.hasPath()) {
         List var4 = this.worldObj.getEntitiesWithinAABB(EntityCreature.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(16.0D, 4.0D, 16.0D));
         if(!var4.isEmpty()) {
            entity1 = (Entity)var4.get(this.worldObj.rand.nextInt(var4.size()));
            if(this.canEntityBeSeen(entity1) && (entity1 instanceof EntityMob || entity1 instanceof EntityReficulSoldier || entity1 instanceof EntityReficulGuardian || entity1 instanceof EntityReficulMage)) {
               this.entityToAttack = entity1;
            }
         }
      }

   }

   protected void attackEntity(Entity entity, float f) {
      if(f < 10.0F) {
         if(this.attackTime == 0) {
            this.attackTime = 80;
            this.swingItem();
            this.attackEntityAsMob(entity);
            this.faceEntity(entity, 30.0F, 30.0F);
         }

         this.hasAttacked = true;
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

   public boolean attackEntityFrom(DamageSource damagesource, int i) {
      boolean flag = true;
      Entity entity = damagesource.getSourceOfDamage();
      if(entity instanceof EntityDefendBandit || entity instanceof EntityDefendKnight || entity instanceof EntityDefendPaladin || entity instanceof EntityDefendWarrior || entity instanceof EntityDefendArcher || entity instanceof EntityHired || entity instanceof EntityDefendMage || entity instanceof EntityPlayer || entity instanceof EntityPlayerSP) {
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

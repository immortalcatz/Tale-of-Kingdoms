package kingdoms.server.handlers.schematic;

import kingdoms.api.blocks.FakeBlock;
import kingdoms.api.entities.FakeEntity;
import kingdoms.server.WorldProvider;
import kingdoms.server.handlers.Buildings;
import kingdoms.server.handlers.UltimateHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public final class SchematicHandler
{
    private int index;
    private List<FakeBlock> torchList = new ArrayList<>();
    private List<Schematic> buildingList = new ArrayList<>();

    public static final SchematicHandler INSTANCE = new SchematicHandler();

    public void addBuilding(Schematic schematic)
    {
        this.buildingList.add(schematic);
    }

    public List<Schematic> getBuildingList()
    {
        return this.buildingList;
    }

    public void update(World world)
    {
        if (!this.buildingList.isEmpty())
        {
            Schematic first = this.buildingList.get(0);

            if (first != null)
            {
                List<FakeBlock> blocks = first.getBlockList();
                List<FakeEntity> entities = first.getEntityList();

                for (int i = 0; i < first.speed; ++i)
                {
                    if (index < blocks.size())
                    {
                        FakeBlock fakeBlock = blocks.get(index);

                        if (fakeBlock != null && world.getBlock(first.x + fakeBlock.posX, first.y + fakeBlock.posY, first.z + fakeBlock.posZ) != fakeBlock.block)
                        {
                            if(fakeBlock.block == Blocks.air)
                                world.setBlockToAir(first.x + fakeBlock.posX, first.y + fakeBlock.posY, first.z + fakeBlock.posZ);

                            if (fakeBlock.block != Blocks.torch && fakeBlock.block != Blocks.wooden_door && fakeBlock.block != Blocks.ladder && fakeBlock.block != Blocks.trapdoor)
                            {
                                world.setBlock(first.x + fakeBlock.posX, first.y + fakeBlock.posY, first.z + fakeBlock.posZ, fakeBlock.block, fakeBlock.metadata, 3);
                            }
                            else
                                this.torchList.add(fakeBlock);
                        }

                        ++index;
                    }
                    else if (index < blocks.size() + entities.size())
                    {
                        FakeEntity entity = entities.get(index - blocks.size());

                        WorldProvider worldProvider = WorldProvider.get(world);
                        EntityLiving entity1 = (EntityLiving) UltimateHelper.INSTANCE.getEntity(entity.entityName, world);

                        if (entity1 != null)
                        {
                            if (Buildings.INSTANCE.getBuilding(1))
                            {
                                entity1.setPosition((double) worldProvider.guildPosX + entity.posX, (double) worldProvider.guildPosY + entity.posY, (double) worldProvider.guildPosZ + entity.posZ);
                            }
                            else
                            {
                                entity1.setPosition((double) first.x + entity.posX, (double) first.y + entity.posY + 1.5D, (double) first.z + entity.posZ);
                            }

                            world.spawnEntityInWorld(entity1);
                        }

                        ++index;
                    }
                    else
                    {
                        this.torchList.forEach(block -> world.setBlock(first.x + block.posX, first.y + block.posY, first.z + block.posZ, block.block, block.metadata, 3));

                        index = 0;

                        if (!this.buildingList.isEmpty())
                            this.buildingList.remove(0);

                        this.torchList.clear();
                    }
                }
            }
        }
    }

    public float getProgressCurrentBuilding()
    {
        if (this.buildingList.isEmpty())
        {
            return 0.0F;
        }
        else
        {
            List<FakeBlock> blocks = this.buildingList.get(0).getBlockList();
            return index / (float) blocks.size() * 100.0F;
        }
    }
}
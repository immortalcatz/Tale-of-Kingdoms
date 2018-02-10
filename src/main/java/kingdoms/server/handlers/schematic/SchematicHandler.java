package kingdoms.server.handlers.schematic;

import kingdoms.api.blocks.FakeBlock;
import kingdoms.api.entities.FakeEntity;
import kingdoms.server.WorldProvider;
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

                        if (fakeBlock != null && world.getBlock(first.x + fakeBlock.getPosX(), first.y + fakeBlock.getPosY(), first.z + fakeBlock.getPosZ()) != fakeBlock.getBlock())
                        {
                            if(fakeBlock.getBlock() == Blocks.air)
                                world.setBlockToAir(first.x + fakeBlock.getPosX(), first.y + fakeBlock.getPosY(), first.z + fakeBlock.getPosZ());

                            if (fakeBlock.getBlock() != Blocks.torch && fakeBlock.getBlock() != Blocks.wooden_door && fakeBlock.getBlock() != Blocks.ladder && fakeBlock.getBlock() != Blocks.trapdoor)
                            {
                                world.setBlock(first.x + fakeBlock.getPosX(), first.y + fakeBlock.getPosY(), first.z + fakeBlock.getPosZ(), fakeBlock.getBlock(), fakeBlock.getMetadata(), 3);
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
                        EntityLiving entity1 = (EntityLiving) UltimateHelper.INSTANCE.getEntity(entity.getEntityName(), world);

                        if (entity1 != null)
                        {
                            if (worldProvider.town)
                            {
                                entity1.setPosition((double) worldProvider.townPosX + entity.getPosX(), (double) worldProvider.townPosY + entity.getPosY(), (double) worldProvider.townPosZ + entity.getPosZ());
                            }
                            else
                            {
                                entity1.setPosition((double) first.x + entity.getPosX(), (double) first.y + entity.getPosY() + 1.5D, (double) first.z + entity.getPosZ());
                            }

                            world.spawnEntityInWorld(entity1);
                        }

                        ++index;
                    }
                    else
                    {
                        this.torchList.forEach(block -> world.setBlock(first.x + block.getPosX(), first.y + block.getPosY(), first.z + block.getPosZ(), block.getBlock(), block.getMetadata(), 3));

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
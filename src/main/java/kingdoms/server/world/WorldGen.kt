package kingdoms.server.world

import cpw.mods.fml.common.IWorldGenerator
import net.minecraft.world.World
import net.minecraft.world.chunk.IChunkProvider
import java.util.*

class WorldGen: IWorldGenerator
{
    override fun generate(random: Random?, x: Int, z: Int, world: World, generator: IChunkProvider?, provider: IChunkProvider?)
    {
        when (world.provider.dimensionId)
        {
            0 -> generateSurface(world, random, x, z)
        }
    }

    private fun generateSurface(world: World?, random: Random?, x: Int, z: Int)
    {

    }
}
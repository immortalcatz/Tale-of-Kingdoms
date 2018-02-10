package kingdoms.server.world

import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenerator
import java.util.*

class ReficulTower: WorldGenerator()
{
    override fun generate(world: World?, rand: Random?, x: Int, y: Int, z: Int): Boolean
    {
        return false
    }
}
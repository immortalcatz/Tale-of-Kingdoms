package kingdoms.api.blocks

import net.minecraft.block.Block

class FakeBlock(block: Int, val metadata: Int, val posX: Int, val posY: Int, val posZ: Int)
{
    val block: Block = Block.getBlockById(block)
}
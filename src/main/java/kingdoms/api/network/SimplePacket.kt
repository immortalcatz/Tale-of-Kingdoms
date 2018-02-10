package kingdoms.api.network

import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler
import cpw.mods.fml.common.network.simpleimpl.MessageContext
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP

open class SimplePacket: IMessage, IMessageHandler<SimplePacket, SimplePacket>
{
    private var buf: ByteBuf? = null

    override fun onMessage(msg: SimplePacket, ctx: MessageContext): SimplePacket?
    {
        if (ctx.side.isServer)
            msg.server(ctx.serverHandler.playerEntity)
        else
            msg.client(clientPlayer())
        return null
    }

    protected fun buf(): ByteBuf
    {
        if(buf == null) buf = Unpooled.buffer()
        return buf!!
    }

    open fun client(player: EntityPlayer) {}
    open fun server(player: EntityPlayerMP) {}

    override fun fromBytes(buf: ByteBuf)
    {
        this.buf = buf
    }

    override fun toBytes(buf: ByteBuf)
    {
        buf.writeBytes(this.buf)
    }

    @SideOnly(Side.CLIENT)
    private fun clientPlayer(): EntityPlayer
    {
        return Minecraft.getMinecraft().thePlayer
    }
}
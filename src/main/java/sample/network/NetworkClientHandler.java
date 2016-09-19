package sample.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NetworkClientHandler extends ChannelInboundHandlerAdapter {

    NetworkMessage networkMessage = new NetworkMessage();

    public NetworkClientHandler() {
        networkMessage.setType(NetworkMessage.HANDSHAKE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send the first message if this handler is a client-side handler.
        ctx.writeAndFlush(networkMessage);
        System.out.println("msg was sent to server");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("msg received from server");
        NetworkMessage networkMessage = (NetworkMessage) msg;
        System.out.println(networkMessage.getType());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
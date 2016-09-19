package sample.network;

import io.netty.channel.*;
import sample.game.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * Created by ruslan.babich on 16.08.2016.
 */
public class NetworkClient {
    private static final int TCP_PORT = 27015;
    private static final boolean SSL = System.getProperty("ssl") != null;
    private static Channel channel;
    private static ChannelFuture channelFuture;
    private static EventLoopGroup group = new NioEventLoopGroup();

    public static void sendChatMessage(String chatMessage) {
        NetworkMessage message = new NetworkMessage();
        message.setType(NetworkMessage.SEND_CHAT_MESSAGE);
        message.setMessage(chatMessage);
        message.setPlayer(GameAttributes.getPlayer());

//        client.sendTCP(message);
    }

    public static void sendMessage(String type) {
        NetworkMessage message = new NetworkMessage();
        message.setType(type);
        message.setPlayer(GameAttributes.getPlayer());
//        client.sendTCP(message);
    }

    public static NetworkClient start(final String hostIP) throws SSLException, InterruptedException {
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(sslCtx.newHandler(ch.alloc(), hostIP, TCP_PORT));
                        }
                        p.addLast(
                                new ObjectEncoder(),
                                new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                new NetworkClientHandler());
                    }
                });

        // Start the connection attempt.
        channel = b.connect(hostIP, TCP_PORT).sync().channel();

        return null;
    }

    public static void stop() throws InterruptedException {
        channel.closeFuture().sync();
        if (channelFuture != null)
            channelFuture.sync();
        group.shutdownGracefully();
    }
}

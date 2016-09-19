package sample.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javafx.application.Platform;
import sample.Main;
import sample.game.GameAttributes;
import sample.game.Player;

import java.io.IOException;

public class NetworkClientHandler extends ChannelInboundHandlerAdapter {

    public NetworkClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof NetworkMessage) {
            final NetworkMessage message = (NetworkMessage) msg;

            //NEW PLAYER
            if (message.getType().equals(NetworkMessage.NEW_PLAYER)) {
                System.out.println(NetworkMessage.NEW_PLAYER + " received from server");
                //if we need to modify smth from not-this-application-thread (for example another client) - we need to use runLater()
                Platform.runLater(() -> {
                    GameAttributes.setPlayers(message.getPlayers());
                    GameAttributes.setPlayersNames();
                    for (Player player : message.getPlayers()) {
                        //TODO: change this to ID from database in future
                        if (player.getName().equals(GameAttributes.getPlayer().getName()))
                            GameAttributes.setPlayer(player);
                    }
//                    try {
//                        Main.showSceneFromFXML(Main.LOBBY_FXML);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                });
            }
        }
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
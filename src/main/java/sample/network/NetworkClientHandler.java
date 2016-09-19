package sample.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javafx.application.Platform;
import sample.Main;
import sample.controllers.TabletopController;
import sample.game.Card;
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
            //NEW PLAYER
            //NEW PLAYER
            if (message.getType().equals(NetworkMessage.NEW_PLAYER)) {
                System.out.println(message.getType() + " received from server");
                //if we need to modify smth from not-this-application-thread (for example another client) - we need to use runLater()
                Platform.runLater(() -> {
                    GameAttributes.setPlayers(message.getPlayers());
                    GameAttributes.setPlayersNames();
                    for (Player player : message.getPlayers()) {
                        //TODO: change this to ID from database in future
                        if (player.getName().equals(GameAttributes.getPlayer().getName()))
                            GameAttributes.setPlayer(player);
                    }
                });
            }

            //SEND CHAT MESSAGE
            //SEND CHAT MESSAGE
            //SEND CHAT MESSAGE
            if (message.getType().equals(NetworkMessage.SEND_CHAT_MESSAGE)) {
                System.out.println(message.getType()+ " received from server");
                //if we need to modify smth from not-this-application-thread (for example another client) - we need to use runLater()
                Platform.runLater(() -> GameAttributes.setLobbyChat(message.getMessage()));
            }

            //START GAME
            //START GAME
            //START GAME
            if (message.getType().equals(NetworkMessage.START_GAME)) {
                System.out.println(message.getType()+ " received from server");
                Platform.runLater(() -> {
                    GameAttributes.setPlayers(message.getPlayers());
                    GameAttributes.setAlivePlayers(message.getAlivePlayers());
                    GameAttributes.setNowMovingPlayerName(message.getNowMovingPlayerName());
                    Main.showTableTop();
                });
            }

            //GET CARD FROM DECK
            //GET CARD FROM DECK
            //GET CARD FROM DECK
            if (message.getType().equals(NetworkMessage.GET_CARD_FROM_DECK)) {
                System.out.println(message.getType()+ " received from server");

                if (message.getCard().getType().equals(Card.CARD_TYPE_EVENT)) {
                    GameAttributes.getPlayer().getHandCards().add(message.getCard());
                } else {
                    GameAttributes.setNowPlayingCard(message.getCard());
                    TabletopController.drawNowPlayingCard();
                }
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
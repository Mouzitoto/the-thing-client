package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.game.GameAttributes;
import sample.Main;
import sample.network.NetworkClient;
import sample.network.NetworkMessage;

import java.io.IOException;

/**
 * Created by ruslan.babich on 26.07.2016.
 */
public class LobbyController {

    @FXML
    public void initialize() {
        taPlayers.textProperty().bind(Bindings.convert(GameAttributes.playersNamesProperty()));
        taChatWindow.textProperty().bind(Bindings.convert(GameAttributes.lobbyChatProperty()));
        btnStartGame.setVisible(GameAttributes.getPlayer().isGameOwner());
        btnStartGame.visibleProperty().bind(GameAttributes.isGameOwnerProperty());
    }

    @FXML
    public TextArea taPlayers;

    @FXML
    public TextArea taGameOptions;

    @FXML
    public TextArea taChatWindow;

    @FXML
    public TextField tfPlayersInput;

    @FXML
    public Button btnSay;

    @FXML
    public Button btnStartGame;

    @FXML
    public void sendMessageToLobbyChat() {
        String chatMessage = "[" + GameAttributes.getPlayer().getName() + "]: " + tfPlayersInput.getText();
        NetworkClient.sendChatMessage(chatMessage);
        tfPlayersInput.setText("");
    }

    @FXML
    public void startGame() throws IOException, InterruptedException {
        NetworkMessage message = new NetworkMessage();
        message.setType(NetworkMessage.START_GAME);
        NetworkClient.sendMessage(message);
    }

}

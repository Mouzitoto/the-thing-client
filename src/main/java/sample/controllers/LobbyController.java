package sample.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Main;

import java.io.IOException;

/**
 * Created by ruslan.babich on 26.07.2016.
 */
public class LobbyController {

    @FXML
    public void initialize() {
        taPlayers.textProperty().bind(Bindings.convert(Main.gameAttributes.playersNamesProperty()));
        taChatWindow.textProperty().bind(Bindings.convert(Main.gameAttributes.lobbyChatProperty()));
        btnStartGame.setVisible(Main.player.isGameOwner());
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
        String chatMessage = "[" + Main.player.getName() + "]: " + tfPlayersInput.getText();
        Main.sendChatMessage(chatMessage);
        tfPlayersInput.setText("");
    }

    @FXML
    public void startGame() throws IOException {
        Main.startGame();
    }

}

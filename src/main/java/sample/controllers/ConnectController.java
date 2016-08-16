package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.game.GameAttributes;
import sample.network.NetworkClient;

import java.io.IOException;

public class ConnectController {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfHostIP;

    @FXML
    private Button btnConnect;


    public void connect() throws IOException {
        GameAttributes.getPlayer().setName(tfName.getText());
        NetworkClient.connectToServer(tfHostIP.getText(), tfName.getText());
    }

}

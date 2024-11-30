package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.MainPanel;

import java.io.IOException;

public class LoginController {

    @FXML
    private void switchRegister(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPanel.class.getResource("RegisterView.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onLogin(ActionEvent event){

    }

}

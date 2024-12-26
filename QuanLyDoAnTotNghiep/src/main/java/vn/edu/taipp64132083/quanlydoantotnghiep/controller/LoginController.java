package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.MainApplication;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.LoginHandler;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Identifiable;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Service service = new Service();
        try {
            service.getGiangVienList();
            service.getSinhVienList();
            service.getDoAnList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void switchRegister(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("RegisterView.fxml"));
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
    private void onLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Thông báo", "Tên đăng nhập hoặc mật khẩu không được để trống!", Alert.AlertType.WARNING);
            return;
        }

        LoginHandler loginHandler = new LoginHandler();
        if (loginHandler.login(username, password)) {
            // Nếu đăng nhập thành công, chuyển sang Dashboard
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/dashboard.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                showAlert("Lỗi", "Không thể tải giao diện Dashboard!", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            showAlert("Thông báo", "Tên đăng nhập hoặc mật khẩu không chính xác!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

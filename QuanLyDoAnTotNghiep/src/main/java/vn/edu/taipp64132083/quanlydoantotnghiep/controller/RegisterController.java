package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.MainApplication;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.UserRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

  @FXML
  private TextField username;

  @FXML
  private PasswordField password;

  @FXML
  private PasswordField confirmPassword;
  @FXML
  private ChoiceBox<GiangVien> cbTenGiangVien;
  @FXML
  private void switchLogin(MouseEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginView.fxml"));
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
  private void onRegister(ActionEvent event) {
    // Kiểm tra username và mật khẩu không rỗng
    String enteredUsername = username.getText();
    String enteredPassword = password.getText();
    String enteredConfirmPassword = confirmPassword.getText();
    GiangVien selectedGiangVien = cbTenGiangVien.getValue();

    if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredConfirmPassword.isEmpty()) {
      showAlert(Alert.AlertType.ERROR, "Lỗi đăng ký", "Vui lòng nhập đầy đủ thông tin!");
      return;
    }

    // Kiểm tra mật khẩu khớp nhau
    if (!enteredPassword.equals(enteredConfirmPassword)) {
      showAlert(Alert.AlertType.ERROR, "Lỗi mật khẩu", "Mật khẩu xác nhận không khớp!");
      return;
    }

    // Kiểm tra giảng viên được chọn
    if (selectedGiangVien == null) {
      showAlert(Alert.AlertType.ERROR, "Lỗi đăng ký", "Vui lòng chọn giảng viên!");
      return;
    }

    // Xác định RoleID dựa vào CanBoKhoa
    int roleId = (selectedGiangVien.getCanBoKhoa() == 0) ? 2 : 4;

    // Tạo đối tượng UserRequest
    UserRequest newUser = new UserRequest();
    newUser.setMaNguoiDung(selectedGiangVien.getMaGiangVien());
    newUser.setUsername(enteredUsername);
    newUser.setPasswordHash(enteredPassword); // Hàm băm mật khẩu
    newUser.setRoleID(roleId);

    try {
      // Gửi thông tin đăng ký tới Service
      UserRequest userRequest = APIClient.post("Users",newUser);
      boolean isRegistered = true;
      if (userRequest==null){
        isRegistered = false;
      }
      if (isRegistered) {
        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đăng ký tài khoản thành công!");
         // Quay lại màn hình đăng nhập
      } else {
        showAlert(Alert.AlertType.ERROR, "Thất bại", "Đăng ký tài khoản thất bại!");
      }
    } catch (Exception e) {
      showAlert(Alert.AlertType.ERROR, "Lỗi hệ thống", "Có lỗi xảy ra: " + e.getMessage());
    }
  }

  // Hàm hiển thị thông báo
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    try {
      cbTenGiangVien.getItems().addAll(Service.getGiangVienList());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}

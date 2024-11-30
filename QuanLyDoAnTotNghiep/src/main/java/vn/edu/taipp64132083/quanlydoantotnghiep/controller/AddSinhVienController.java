package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVienRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;

public class AddSinhVienController {

  @FXML
  private TextField txtHoTen, txtNgaySinh, txtEmail, txtSDT, txtMaLop;
  @FXML
  private ComboBox<String> cmbGioiTinh, cmbTrangThai;
  @FXML
  private Button btnAdd, btnCancel;

  @FXML
  public void initialize() {
    // Sự kiện nút "Thêm"
    btnAdd.setOnAction(event -> handleAddSinhVien());

    // Sự kiện nút "Hủy"
    btnCancel.setOnAction(event -> handleCancel());
    // Cung cấp dữ liệu cho ComboBox
    ObservableList<String> gioiTinhList = FXCollections.observableArrayList("Nam", "Nữ");
    cmbGioiTinh.setItems(gioiTinhList);

    ObservableList<String> trangThaiList = FXCollections.observableArrayList("Hoạt động", "Không hoạt động");
    cmbTrangThai.setItems(trangThaiList);
  }

  private void handleAddSinhVien() {
    try {
      // Lấy dữ liệu từ giao diện
      String hoTen = txtHoTen.getText();
      String ngaySinh = txtNgaySinh.getText();
      int gioiTinh = cmbGioiTinh.getValue().equals("Nam") ? 1 : 0;
      String email = txtEmail.getText();
      String sdt = txtSDT.getText();
      int trangThai = cmbTrangThai.getValue().equals("Hoạt động") ? 1 : 0;
      int maLop = Integer.parseInt(txtMaLop.getText());

      // Tạo đối tượng yêu cầu
      SinhVienRequest sinhVienRequest = new SinhVienRequest(hoTen, ngaySinh, gioiTinh, email, sdt, trangThai, maLop);

      // Gửi yêu cầu thêm sinh viên qua API
      SinhVienRequest response = APIClient.post("SinhVien", sinhVienRequest);

      // Hiển thị thông báo thành công
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Thành công");
      alert.setHeaderText("Đã thêm sinh viên thành công!");
      alert.showAndWait();

      // Đóng form
      Stage stage = (Stage) btnAdd.getScene().getWindow();
      stage.close();

    } catch (Exception e) {
      // Hiển thị thông báo lỗi
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText("Không thể thêm sinh viên!");
      alert.setContentText(e.getMessage());
      alert.show();
    }
  }

  private void handleCancel() {
    // Đóng form
    Stage stage = (Stage) btnCancel.getScene().getWindow();
    stage.close();
  }
}

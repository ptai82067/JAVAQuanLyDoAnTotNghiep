package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVienRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;

public class EditSinhVienController {

  @FXML
  private TextField txtHoTen, txtNgaySinh, txtEmail, txtSDT, txtMaLop;
  @FXML
  private ComboBox<String> cmbGioiTinh, cmbTrangThai;
  @FXML
  private Button btnSave, btnCancel;

  private SinhVien sinhVien;  // Đối tượng sinh viên được chỉnh sửa

  @FXML
  public void initialize() {
    // Sự kiện nút "Lưu"
    btnSave.setOnAction(event -> handleSave());

    // Sự kiện nút "Hủy"
    btnCancel.setOnAction(event -> handleCancel());
    // Cung cấp dữ liệu cho ComboBox
    ObservableList<String> gioiTinhList = FXCollections.observableArrayList("Nam", "Nữ");
    cmbGioiTinh.setItems(gioiTinhList);

    ObservableList<String> trangThaiList = FXCollections.observableArrayList("Hoạt động", "Không hoạt động");
    cmbTrangThai.setItems(trangThaiList);
  }

  // Hàm nhận đối tượng SinhVien từ controller gọi form này
  public void setSinhVien(SinhVien sinhVien) {
    this.sinhVien = sinhVien;

    // Hiển thị dữ liệu của sinh viên lên form
    txtHoTen.setText(sinhVien.getHoTen());
    txtNgaySinh.setText(sinhVien.getNgaySinh());
    cmbGioiTinh.setValue(sinhVien.getGioiTinh() == 1 ? "Nam" : "Nữ");
    txtEmail.setText(sinhVien.getEmail());
    txtSDT.setText(sinhVien.getSDT());
    cmbTrangThai.setValue(sinhVien.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động");
    txtMaLop.setText(String.valueOf(sinhVien.getLop().getMaLop()));
  }

  private void handleSave() {
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

      // Gửi yêu cầu cập nhật sinh viên qua API
      APIClient.put("SinhVien/" + sinhVien.getId(), sinhVienRequest);

      // Hiển thị thông báo thành công
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Thành công");
      alert.setHeaderText("Đã cập nhật sinh viên thành công!");
      alert.showAndWait();

      // Đóng form
      Stage stage = (Stage) btnSave.getScene().getWindow();
      stage.close();

    } catch (Exception e) {
      // Hiển thị thông báo lỗi
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText("Không thể cập nhật sinh viên!");
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

package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;

public class XemChiTietSinhVienController {

  @FXML private Label lblHoTen, lblNgaySinh, lblGioiTinh, lblEmail, lblSDT, lblTrangThai, lblMaLop;
  @FXML private Button btnClose;

  private SinhVien sinhVien;

  @FXML
  public void initialize() {
    // Sự kiện khi đóng hộp thoại
    btnClose.setOnAction(event -> handleClose());
  }

  // Hàm nhận đối tượng SinhVien từ controller gọi form này
  public void setSinhVien(SinhVien sinhVien) {
    this.sinhVien = sinhVien;

    // Hiển thị dữ liệu của sinh viên lên form
    lblHoTen.setText(sinhVien.getHoTen());
    lblNgaySinh.setText(sinhVien.getNgaySinh());
    lblGioiTinh.setText(sinhVien.getGioiTinh() == 1 ? "Nam" : "Nữ");
    lblEmail.setText(sinhVien.getEmail());
    lblSDT.setText(sinhVien.getSDT());
    lblTrangThai.setText(sinhVien.getTrangThai() == 1 ? "Hoạt động" : "Không hoạt động");
    lblMaLop.setText(String.valueOf(sinhVien.getLop().getMaLop()));
  }

  private void handleClose() {
    // Đóng hộp thoại
    Stage stage = (Stage) btnClose.getScene().getWindow();
    stage.close();
  }
}

package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVien;

public class XemChiTietGiangVienController {

  @FXML private Label lblHoTen, lblEmail, lblSDT, lblCanBoKhoa, lblHanMucHD, lblSoLuongHuongDan, lblBoMon;
  @FXML private Button btnClose;

  private GiangVien giangVien;

  @FXML
  public void initialize() {
    // Sự kiện khi đóng hộp thoại
    btnClose.setOnAction(event -> handleClose());
  }

  // Hàm nhận đối tượng GiangVien từ controller gọi form này
  public void setGiangVien(GiangVien giangVien) {
    this.giangVien = giangVien;

    // Hiển thị dữ liệu của giảng viên lên form
    lblHoTen.setText(giangVien.getHoTen());
    lblEmail.setText(giangVien.getEmail());
    lblSDT.setText(giangVien.getSDT());
    if(giangVien.getCanBoKhoa() == 1) lblCanBoKhoa.setText( "Cán bộ khoa");
    else lblCanBoKhoa.setText( "Bình thường");
    lblHanMucHD.setText(String.valueOf(giangVien.getHanMucHD()));
    lblSoLuongHuongDan.setText(String.valueOf(giangVien.getSoLuongHuongDan()));
    lblBoMon.setText(giangVien.getBoMon().getTenBoMon());
  }

  private void handleClose() {
    // Đóng hộp thoại
    Stage stage = (Stage) btnClose.getScene().getWindow();
    stage.close();
  }
}

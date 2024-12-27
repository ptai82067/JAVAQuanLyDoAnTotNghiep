package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;

public class XemChiTietDoAnController {

  @FXML private Label  lblTenDoAn, lblMoTa, lblDoKho, lblNgayTao, lblNgayDuyet, lblHanMucDK, lblSoLuongDK, lblGiangVienTao, lblGiangVienDuyet, lblGiangVienKhoaDoAn;
  @FXML private Button btnClose;

  private DoAn doAn;

  @FXML
  public void initialize() {
    // Sự kiện khi đóng hộp thoại
    btnClose.setOnAction(event -> handleClose());
  }

  // Hàm nhận đối tượng DoAn từ controller gọi form này
  public void setDoAn(DoAn doAn) {
    this.doAn = doAn;

    // Hiển thị dữ liệu của đồ án lên form
    lblTenDoAn.setText(doAn.getTenDoAn());
    lblMoTa.setText(doAn.getMoTa());
    lblDoKho.setText(String.valueOf(doAn.getDoKho()));
    lblNgayTao.setText(doAn.getNgayTao());
    lblNgayDuyet.setText(doAn.getNgayDuyet());
    lblHanMucDK.setText(String.valueOf(doAn.getHanMucDK()));
    lblSoLuongDK.setText(String.valueOf(doAn.getSoLuongDK()));

    if (doAn.getGiang_vien_tao() != null) {
      lblGiangVienTao.setText(doAn.getGiang_vien_tao().getHoTen());
    }
    if (doAn.getGiang_vien_duyet() != null) {
      lblGiangVienDuyet.setText(doAn.getGiang_vien_duyet().getHoTen());
    }
    if (doAn.getGiang_vien_khoa_do_an() != null) {
      lblGiangVienKhoaDoAn.setText(doAn.getGiang_vien_khoa_do_an().getHoTen());
    }
  }

  private void handleClose() {
    // Đóng hộp thoại
    Stage stage = (Stage) btnClose.getScene().getWindow();
    stage.close();
  }
}

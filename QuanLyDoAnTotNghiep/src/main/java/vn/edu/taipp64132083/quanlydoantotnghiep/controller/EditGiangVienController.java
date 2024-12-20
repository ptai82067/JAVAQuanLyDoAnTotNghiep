package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.BoMon;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVienRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

public class EditGiangVienController {

  @FXML
  private TextField txtHoTen, txtEmail, txtSDT, txtHanMucHD, txtSoLuongHuongDan;
  @FXML
  private ComboBox<String> cmbCanBoKhoa;
  @FXML
  private ChoiceBox<BoMon> cbMaBoMon;
  @FXML
  private Button btnSave, btnCancel;

  private GiangVien currentGiangVien;

  @FXML
  public void initialize() throws Exception {
    // Sự kiện nút "Lưu"
    btnSave.setOnAction(event -> handleSaveGiangVien());

    // Sự kiện nút "Hủy"
    btnCancel.setOnAction(event -> handleCancel());

    // Cung cấp dữ liệu cho ComboBox Cán bộ khoa
    ObservableList<String> canBoKhoaList = FXCollections.observableArrayList("Bình thường", "Cán bộ khoa");
    cbMaBoMon.getItems().addAll(Service.getBoMon());
    cmbCanBoKhoa.setItems(canBoKhoaList);
  }

  public void setGiangVien(GiangVien giangVien) throws Exception {
    // Gán giảng viên hiện tại
    this.currentGiangVien = giangVien;

    // Điền dữ liệu vào các trường
    txtHoTen.setText(giangVien.getHoTen());
    txtEmail.setText(giangVien.getEmail());
    txtSDT.setText(giangVien.getSDT());
    cbMaBoMon.setValue(Service.getBoMon().stream()
            .filter(bm -> bm.getId() == giangVien.getBoMon().getId())
            .findFirst()
            .orElse(null));
    cmbCanBoKhoa.setValue(giangVien.getCanBoKhoa() == 0 ? "Bình thường" : "Cán bộ khoa");
    txtHanMucHD.setText(String.valueOf(giangVien.getHanMucHD()));
    txtSoLuongHuongDan.setText(String.valueOf(giangVien.getSoLuongHuongDan()));
  }

  private void handleSaveGiangVien() {
    try {
      // Lấy dữ liệu từ giao diện
      String hoTen = txtHoTen.getText();
      String email = txtEmail.getText();
      String sdt = txtSDT.getText();
      int maBoMon = cbMaBoMon.getValue().getId();
      int canBoKhoa = cmbCanBoKhoa.getValue().equals("Bình thường") ? 0 : 1;
      int hanMucHD = Integer.parseInt(txtHanMucHD.getText());
      int soLuongHuongDan = Integer.parseInt(txtSoLuongHuongDan.getText());

      // Tạo đối tượng yêu cầu chỉnh sửa
      GiangVienRequest giangVienRequest = new GiangVienRequest(
              hoTen, email, sdt, maBoMon, canBoKhoa, hanMucHD, soLuongHuongDan
      );

      // Gửi yêu cầu chỉnh sửa giảng viên qua API
      APIClient.put(Service.GIANGVIEN_TABLE_NAME+"/"+currentGiangVien.getId(), giangVienRequest);

      // Hiển thị thông báo thành công

      // Đóng form
      Stage stage = (Stage) btnSave.getScene().getWindow();
      stage.close();

    } catch (Exception e) {
      // Hiển thị thông báo lỗi
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText("Không thể chỉnh sửa giảng viên!");
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

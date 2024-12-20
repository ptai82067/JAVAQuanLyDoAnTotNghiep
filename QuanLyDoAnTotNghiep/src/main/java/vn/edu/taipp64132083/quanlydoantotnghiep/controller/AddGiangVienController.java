package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.BoMon;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVienRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

public class AddGiangVienController {

  @FXML
  private TextField txtHoTen, txtEmail, txtSDT, txtHanMucHD, txtSoLuongHuongDan;
  @FXML
  private ComboBox<String> cmbCanBoKhoa;
  @FXML
  private ChoiceBox<BoMon> cbMaBoMon;
  @FXML
  private Button btnAdd, btnCancel;

  @FXML
  public void initialize() throws Exception {
    // Sự kiện nút "Thêm"
    btnAdd.setOnAction(event -> handleAddGiangVien());

    // Sự kiện nút "Hủy"
    btnCancel.setOnAction(event -> handleCancel());

    // Cung cấp dữ liệu cho ComboBox Cán bộ khoa
    ObservableList<String> canBoKhoaList = FXCollections.observableArrayList("Bình thường","Cán bộ khoa");
    cbMaBoMon.getItems().addAll(Service.getBoMon());
    cbMaBoMon.setValue(Service.getBoMon().get(0));
    cmbCanBoKhoa.setItems(canBoKhoaList);
  }

  private void handleAddGiangVien() {
    try {
      // Lấy dữ liệu từ giao diện
      String hoTen = txtHoTen.getText();
      String email = txtEmail.getText();
      String sdt = txtSDT.getText();
      int maBoMon = cbMaBoMon.getValue().getId();

      int canBoKhoa = cmbCanBoKhoa.getValue().equals("Bình thường") ? 0 : 1;
      int hanMucHD = Integer.parseInt(txtHanMucHD.getText());
      int soLuongHuongDan = Integer.parseInt(txtSoLuongHuongDan.getText());

      // Tạo đối tượng yêu cầu
      GiangVienRequest giangVienRequest = new GiangVienRequest(
              hoTen, email, sdt, maBoMon, canBoKhoa, hanMucHD, soLuongHuongDan
      );

      // Gửi yêu cầu thêm giảng viên qua API
      GiangVienRequest response = APIClient.post(Service.GIANGVIEN_TABLE_NAME, giangVienRequest);

      // Hiển thị thông báo thành công


      // Đóng form
      Stage stage = (Stage) btnAdd.getScene().getWindow();
      stage.close();

    } catch (Exception e) {
      // Hiển thị thông báo lỗi
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText("Không thể thêm giảng viên!");
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

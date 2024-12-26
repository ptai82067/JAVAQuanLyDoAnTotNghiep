package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAnRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

public class EditDoAnController {

  @FXML
  private TextField  txtTenDoAn, txtNgayDuyet, txtHanMucDK;
  @FXML
  private TextArea txtMoTa;
  @FXML
  private ChoiceBox<String> cbDoKho;
  @FXML
  private Button btnSave, btnCancel;

  private DoAn currentDoAn;

  @FXML
  public void initialize() throws Exception {
    // Sự kiện nút "Lưu"
    btnSave.setOnAction(event -> handleSaveDoAn());

    // Sự kiện nút "Hủy"
    btnCancel.setOnAction(event -> handleCancel());

    // Cung cấp dữ liệu cho các ComboBox
    ObservableList<String> doKhoList = FXCollections.observableArrayList("Dễ", "Vừa", "Khó", "Rất khó");
    cbDoKho.setItems(doKhoList);

  }

  public void setDoAn(DoAn doAn) throws Exception {
    // Gán đồ án hiện tại
    this.currentDoAn = doAn;

    // Điền dữ liệu vào các trường
    txtTenDoAn.setText(doAn.getTenDoAn());
    txtMoTa.setText(doAn.getMoTa());
    cbDoKho.setValue(convertDoKhoToString(currentDoAn.getDoKho()));
    txtNgayDuyet.setText(doAn.getNgayDuyet());
    txtHanMucDK.setText(String.valueOf(doAn.getHanMucDK()));
  }

  private void handleSaveDoAn() {
    try {
      // Lấy dữ liệu từ giao diện
      int maDoAn = currentDoAn.getId();
      String tenDoAn = txtTenDoAn.getText();
      String moTa = txtMoTa.getText();
      String doKhoString = cbDoKho.getValue();  // Lấy giá trị từ ChoiceBox
      int doKho = convertDoKhoToInt(doKhoString);  // Chuyển đổi thành giá trị int
      String ngayTao = currentDoAn.getNgayTao();
      String ngayDuyet = txtNgayDuyet.getText();
      int hanMucDK = Integer.parseInt(txtHanMucDK.getText());
      int soLuongDK = currentDoAn.getSoLuongDK();
      int giangVienTao = currentDoAn.getGiang_vien_tao().getId();
      int giangVienDuyet = currentDoAn.getGiang_vien_duyet().getId();
      int giangVienKhoa = currentDoAn.getGiang_vien_khoa_do_an().getId();

      // Tạo đối tượng yêu cầu chỉnh sửa đồ án
      DoAnRequest doAnRequest = new DoAnRequest(
              tenDoAn, moTa, doKho, giangVienTao,
              giangVienDuyet, giangVienKhoa, ngayTao,
              ngayDuyet, hanMucDK, soLuongDK
      );

      // Gửi yêu cầu chỉnh sửa đồ án qua API
      APIClient.put(Service.DOAN_TABLE_NAME + "/" + currentDoAn.getMaDoAn(), doAnRequest);

      // Hiển thị thông báo thành công

      // Đóng form
      Stage stage = (Stage) btnSave.getScene().getWindow();
      stage.close();

    } catch (Exception e) {
      // Hiển thị thông báo lỗi
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText("Không thể chỉnh sửa đồ án!");
      alert.setContentText(e.getMessage());
      alert.show();
    }
  }

  private void handleCancel() {
    // Đóng form
    Stage stage = (Stage) btnCancel.getScene().getWindow();
    stage.close();
  }
  private String convertDoKhoToString(int doKho) {
    switch (doKho) {
      case 1:
        return "Dễ";
      case 2:
        return "Vừa";
      case 3:
        return "Khó";
      case 4:
        return "Rất khó";
      default:
        return "Dễ";  // Mặc định là "Dễ"
    }
  }
  private int convertDoKhoToInt(String doKho) {
    switch (doKho) {
      case "Dễ":
        return 1;
      case "Vừa":
        return 2;
      case "Khó":
        return 3;
      case "Rất khó":
        return 4;
      default:
        return 1;  // Mặc định là "Dễ"
    }
  }
}

package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.APIDataCache;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAnRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.BoMon;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SessionData;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

public class AddDoAnController {

  @FXML
  private TextField txtTenDoAn, txtMoTa, txtMaNguoiTao, txtMaNguoiDuyet, txtMaNguoiKhoaDoAn,
          txtNgayTao, txtNgayDuyet, txtHanMucDK, txtSoLuongDK;
  @FXML
  private ChoiceBox<String> cbDoKho;
  @FXML
  private Button btnAdd, btnCancel;

  @FXML
  public void initialize() throws Exception {
    ObservableList<String> doKhoList = FXCollections.observableArrayList("Dễ", "Vừa", "Khó", "Rất khó");
    cbDoKho.setItems(doKhoList);
    cbDoKho.setValue("Dễ");  // Mặc định là "Dễ"
    // Sự kiện nút "Thêm"
    btnAdd.setOnAction(event -> handleAddDoAn());

    // Sự kiện nút "Hủy"
    btnCancel.setOnAction(event -> handleCancel());
  }

  private void handleAddDoAn() {
    try {
      // Lấy dữ liệu từ giao diện
      String tenDoAn = txtTenDoAn.getText();
      String moTa = txtMoTa.getText();
      String doKhoString = cbDoKho.getValue();  // Lấy giá trị từ ChoiceBox
      int doKho = convertDoKhoToInt(doKhoString);  // Chuyển đổi thành giá trị int
      // Truy cập thông tin session từ APIDataCache
      ObservableList<SessionData> sessions = APIDataCache.getInstance()
              .getListFromCache("LoginSession", SessionData.class);
      SessionData session = sessions.get(0);
      int maNguoiTao = session.getId();
      Integer maNguoiDuyet = txtMaNguoiDuyet.getText().isEmpty() ? null : Integer.parseInt(txtMaNguoiDuyet.getText());
      Integer maNguoiKhoaDoAn = txtMaNguoiKhoaDoAn.getText().isEmpty() ? null : Integer.parseInt(txtMaNguoiKhoaDoAn.getText());
      String ngayTao = txtNgayTao.getText();
      String ngayDuyet = txtNgayDuyet.getText().isEmpty() ? null : txtNgayDuyet.getText();
      int hanMucDK = Integer.parseInt(txtHanMucDK.getText());
      int soLuongDK = Integer.parseInt(txtSoLuongDK.getText());
      // Tạo đối tượng yêu cầu
      DoAnRequest doAnRequest = new DoAnRequest(
              tenDoAn, moTa, doKho, maNguoiTao, maNguoiDuyet, maNguoiKhoaDoAn, ngayTao, ngayDuyet, hanMucDK, soLuongDK
      );

      // Gửi yêu cầu thêm đồ án qua API
      DoAnRequest response = APIClient.post(Service.DOAN_TABLE_NAME, doAnRequest);


      // Đóng form
      Stage stage = (Stage) btnAdd.getScene().getWindow();
      stage.close();

    } catch (Exception e) {
      // Hiển thị thông báo lỗi
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText("Không thể thêm đồ án!");
      alert.setContentText(e.getMessage());
      alert.show();
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

  private void handleCancel() {
    // Đóng form
    Stage stage = (Stage) btnCancel.getScene().getWindow();
    stage.close();
  }
}

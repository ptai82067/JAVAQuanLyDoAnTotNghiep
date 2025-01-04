package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.APIDataCache;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.*;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class PhanCongController implements Initializable {
  @FXML
  private ChoiceBox<DoAn> cbMaDoAn;
  @FXML
  private ChoiceBox<SinhVien> cbMaSinhVien;

  @FXML
  private TextField txtNgayPhanCong;

  @FXML
  private TextField txtGhiChu;
  ObservableList<SessionData> sessions = APIDataCache.getInstance()
          .getListFromCache("LoginSession", SessionData.class);
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Service service = new Service();
    try {
      List<DoAn> filteredDoAnList = service.getDoAnList().stream()
              .filter(item -> item.getHanMucDK() > item.getSoLuongDK())
              .toList();

      List<GiangVien> filteredGiangVienList = service.getGiangVienList().stream()
              .filter(item -> item.getHanMucHD() > item.getSoLuongHuongDan())
              .toList();

      cbMaDoAn.getItems().addAll(filteredDoAnList);

      cbMaSinhVien.getItems().addAll(service.getSinhVienList());

      // Đặt giá trị mặc định
      cbMaDoAn.setValue(filteredDoAnList.get(0));
      cbMaSinhVien.setValue(service.getSinhVienList().get(0));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @FXML
  public void onSave() throws Exception {
    Integer maDoAn = cbMaDoAn.getValue().getId();
    Integer maSinhVien = cbMaSinhVien.getValue().getId();
    SessionData session = sessions.get(0);
    Integer maGiangVien = session.getUserDetails().getId();
    String ngayPhanCong = txtNgayPhanCong.getText();
    String ghiChu = txtGhiChu.getText();
    if(!ngayPhanCong.isEmpty()){
      APIClient.post(Service.PHANCONG_TABLE_NAME,new PhanCongDoAnRequest(maDoAn,
              maSinhVien,maGiangVien,ngayPhanCong,ghiChu));

    }else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("");
      alert.setContentText("Chưa nhập ngày phân công!");
      // Hiển thị hộp thoại
      alert.showAndWait();
    }
  }
  @FXML
  public void onCancel() {

  }

}

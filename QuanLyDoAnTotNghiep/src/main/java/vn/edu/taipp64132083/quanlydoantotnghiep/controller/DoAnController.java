package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.APIDataCache;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.*;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.PaginationService;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoAnController implements Initializable {

  @FXML
  private TableView<DoAn> doAnTable;  // TableView để hiển thị danh sách sinh viên
  @FXML
  private TableColumn<DoAn, String> tenDoAn, ngayTao, ngayDuyet;
  @FXML
  private TableColumn<DoAn, Integer> doKho, hanMucDK, soLuongDK;
  @FXML
  private TableColumn<DoAn, GiangVien> giangVienDuyet;
  @FXML
  private Button btnAdd, btnEdit, btnDelete, btnDetail, btnDuyet, btnKhoa,btnLamMoi;


  @FXML
  private Button btnNextPage, btnPrevPage, btnFirstPage, btnLastPage;  // Nút phân trang
  @FXML
  private Label lblPageInfo;  // Hiển thị thông tin trang hiện tại

  private ObservableList<DoAn> allDoAn;  // Toàn bộ danh sách sinh viên
  private PaginationService<DoAn> paginationService;  // Dịch vụ phân trang


  public DoAnController()
  {

  }
  ObservableList<SessionData> sessions = APIDataCache.getInstance()
          .getListFromCache("LoginSession", SessionData.class);
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    SessionData session = sessions.get(0);
   if(session.getUserDetails().getCanBoKhoa()==0)  {
     btnDuyet.setVisible(false);
     btnDuyet.setDisable(false);
     btnKhoa.setVisible(false);
     btnKhoa.setDisable(false);
    }
    tenDoAn.setCellValueFactory(new PropertyValueFactory<>("tenDoAn"));
    ngayTao.setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
    ngayDuyet.setCellValueFactory(new PropertyValueFactory<>("ngayDuyet"));
    doKho.setCellValueFactory(new PropertyValueFactory<>("doKho"));
    hanMucDK.setCellValueFactory(new PropertyValueFactory<>("hanMucDK"));
    soLuongDK.setCellValueFactory(new PropertyValueFactory<>("soLuongDK"));
    giangVienDuyet.setCellValueFactory(new PropertyValueFactory<>("giang_vien_duyet"));


    // Lấy danh sách DoAn từ cache hoặc API
    Service service = new Service();
    try {
      allDoAn = service.getDoAnList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    allDoAn.addListener((ListChangeListener<DoAn>) change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          // Nếu có phần tử mới được thêm vào
          for (DoAn doAn : change.getAddedSubList()) {
            // Cập nhật UI khi thêm đồ án

          }
        }
        if (change.wasRemoved()) {
          // Nếu có phần tử bị xóa
          for (DoAn doAn : change.getRemoved()) {
            // Cập nhật UI khi xóa đồ án

          }
        }
        if (change.wasUpdated()) {
          // Nếu có phần tử bị cập nhật
          System.out.println("Đã cập nhật sinh viên.");
        }
      }});
    doAnTable.setItems(allDoAn);
    // Xử lý sự kiện nút
    btnAdd.setOnAction(this::handleAdd);
    btnEdit.setOnAction(this::handleEdit);
    btnDelete.setOnAction(this::handleDelete);
    btnDetail.setOnAction(this::handleDetail);
    btnDuyet.setOnAction(this::handleDuyet);
    btnKhoa.setOnAction(this::handleKhoa);
    btnLamMoi.setOnAction(this::handleLamMoi);
    // Khởi tạo dịch vụ phân trang
    paginationService = new PaginationService<>(allDoAn, 5);

    // Cập nhật TableView với dữ liệu của trang đầu tiên
    updateTableView();

    // Cập nhật các nút phân trang
    btnNextPage.setOnAction(event -> nextPage());
    btnPrevPage.setOnAction(event -> prevPage());
    btnFirstPage.setOnAction(event -> firstPage());
    btnLastPage.setOnAction(event -> lastPage());

  }

  // Cập nhật dữ liệu trong TableView
  public void updateTableView() {
    doAnTable.setItems(paginationService.getCurrentPageData());
    lblPageInfo.setText("Trang " + (paginationService.getCurrentPage() + 1) + " / " + paginationService.getTotalPages());
  }
  private void refreshTableView() {// Làm mới phân trang
    updateTableView();           // Cập nhật lại TableView
  }

  // Chuyển đến trang tiếp theo
  private void nextPage() {
    paginationService.nextPage();
    updateTableView();
  }

  // Chuyển đến trang trước
  private void prevPage() {
    paginationService.prevPage();
    updateTableView();
  }

  // Chuyển đến trang đầu tiên
  private void firstPage() {
    paginationService.firstPage();
    updateTableView();
  }

  // Chuyển đến trang cuối cùng
  private void lastPage() {
    paginationService.lastPage();
    updateTableView();
  }
  @FXML
  private void handleDuyet(ActionEvent event){
    DoAn selectedDoAn = doAnTable.getSelectionModel().getSelectedItem();
    if (selectedDoAn == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Cảnh báo");
      alert.setHeaderText("Chưa chọn sinh viên!");
      alert.setContentText("Vui lòng chọn một đồ án để duyệt.");
      alert.show();
      return;
    }
    if(selectedDoAn.getGiang_vien_duyet()==null){
      SessionData sessionData = sessions.get(0);
      selectedDoAn.setGiang_vien_duyet(sessionData.getUserDetails());
    }
  }
  @FXML
  private void handleKhoa(ActionEvent event){
    DoAn selectedDoAn = doAnTable.getSelectionModel().getSelectedItem();
    if (selectedDoAn == null) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Cảnh báo");
      alert.setHeaderText("Chưa chọn sinh viên!");
      alert.setContentText("Vui lòng chọn một đồ án để khóa.");
      alert.show();
      return;
    }
    if(selectedDoAn.getGiang_vien_khoa_do_an()==null){
      SessionData sessionData = sessions.get(0);
      selectedDoAn.setGiang_vien_khoa_do_an(sessionData.getUserDetails());
    }
  }
  @FXML
  private void handleLamMoi(ActionEvent event) {
    try {

      allDoAn.add(Service.refeshDoAn());
      doAnTable.refresh();
      updateTableView();
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Làm Mới");
      alert.setHeaderText("");
      alert.setContentText("Làm mới thành công!");
      // Hiển thị hộp thoại
      alert.showAndWait();
    }catch (Exception e){

    }

  }
  @FXML
  private void handleAdd(ActionEvent event) {
    try {
      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/AddDoAnView.fxml"));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Thêm Sinh Viên");
      stage.showAndWait();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @FXML
  private void handleEdit(ActionEvent event) {
//    try {
//      // Lấy sinh viên được chọn trong bảng
//      DoAn selectedDoAn = doAnTable.getSelectionModel().getSelectedItem();
//      if (selectedDoAn == null) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Cảnh báo");
//        alert.setHeaderText("Chưa chọn sinh viên!");
//        alert.setContentText("Vui lòng chọn một sinh viên để chỉnh sửa.");
//        alert.show();
//        return;
//      }
//
//      // Tải form chỉnh sửa
//      Stage stage = new Stage();
//      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/EditDoAnView.fxml"));
//      Parent root = fxmlLoader.load();
//
//      Scene scene = new Scene(root);
//      stage.setScene(scene);
//
//      // Truyền đối tượng sinh viên vào controller
//      EditDoAnController controller = fxmlLoader.getController();
//      controller.setDoAn(selectedDoAn);
//
//      stage.setTitle("Sửa Sinh Viên");
//      stage.showAndWait();
//
//      // Sau khi chỉnh sửa, cập nhật lại danh sách (nếu cần)
//      updateTableView();
//
//    } catch (IOException e) {
//      e.printStackTrace();
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
  }
  @FXML
  private void handleDetail(ActionEvent event) {
    try {
      // Lấy sinh viên được chọn trong bảng
      DoAn selectedDoAn = doAnTable.getSelectionModel().getSelectedItem();
      if (selectedDoAn == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText("Chưa chọn sinh viên!");
        alert.setContentText("Vui lòng chọn một đồ án để xem chi tiết.");
        alert.show();
        return;
      }

      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/XemChiTietDoAnView.fxml"));
      Parent root = fxmlLoader.load();

      Scene scene = new Scene(root);
      stage.setScene(scene);

      // Truyền đối tượng sinh viên vào controller
      XemChiTietDoAnController controller = fxmlLoader.getController();
      controller.setDoAn(selectedDoAn);

      stage.setTitle("Thông Tin Chi Tiết Đồ Án");
      stage.showAndWait();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleDelete(ActionEvent event) {
    // Lấy sinh viên được chọn trong TableView
    DoAn selectedDoAn = doAnTable.getSelectionModel().getSelectedItem();

    if (selectedDoAn != null) {
      // Hiển thị hộp thoại xác nhận xóa
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Xác nhận xóa");
      alert.setHeaderText("Bạn có chắc chắn muốn xóa đồ án này?");
      alert.setContentText("Tên đồ án: " + selectedDoAn.getTenDoAn());

      // Hiển thị hộp thoại và chờ người dùng phản hồi
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          // Xử lý xóa sinh viên

          try {
            APIClient.delete("DoAn/"+selectedDoAn.getId(),selectedDoAn);
          } catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Lỗi");
            alert1.setHeaderText("Không thể xóa sinh viên");
            alert1.setContentText("Đối tượng có liên quan tới bảng khác, không thể xóa.");
            alert1.showAndWait();
          }
        }
      });
    } else {
      // Nếu không có sinh viên nào được chọn, hiển thị thông báo
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Cảnh báo");
      alert.setHeaderText("Chưa chọn sinh viên");
      alert.setContentText("Vui lòng chọn sinh viên muốn xóa.");
      alert.showAndWait();
    }
  }

}
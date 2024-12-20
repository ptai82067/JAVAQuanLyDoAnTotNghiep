package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

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
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.BoMon;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Lop;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVienRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.PaginationService;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GiangVienController implements Initializable {

  @FXML
  private TableView<GiangVien> giangVienTable;  // TableView để hiển thị danh sách sinh viên
  @FXML
  private TableColumn<GiangVien, String> hoTen, email, SDT;
  @FXML
  private TableColumn<GiangVien, Integer> hanMucHD,soLuongHuongDan;
  @FXML
  private TableColumn<GiangVien, BoMon> boMon;
  @FXML
  private Button btnAdd, btnEdit, btnDelete, btnDetail;


  @FXML
  private Button btnNextPage, btnPrevPage, btnFirstPage, btnLastPage;  // Nút phân trang
  @FXML
  private Label lblPageInfo;  // Hiển thị thông tin trang hiện tại

  private ObservableList<GiangVien> allGiangVien;  // Toàn bộ danh sách sinh viên
  private PaginationService<GiangVien> paginationService;  // Dịch vụ phân trang


  public GiangVienController()
  {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    hoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
    SDT.setCellValueFactory(new PropertyValueFactory<>("SDT"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    boMon.setCellValueFactory(new PropertyValueFactory<>("boMon"));
    hanMucHD.setCellValueFactory(new PropertyValueFactory<>("hanMucHD"));
    soLuongHuongDan.setCellValueFactory(new PropertyValueFactory<>("soLuongHuongDan"));
    // Lấy danh sách GiangVien từ cache hoặc API
    Service service = new Service();
    try {
      allGiangVien = service.getGiangVienList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    allGiangVien.addListener((ListChangeListener<GiangVien>) change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          // Nếu có phần tử mới được thêm vào
          for (GiangVien giangVien : change.getAddedSubList()) {
            // Cập nhật UI khi thêm sinh viên
            System.out.println("Đã thêm sinh viên: " + giangVien.getHoTen());
          }
        }
        if (change.wasRemoved()) {
          // Nếu có phần tử bị xóa
          for (GiangVien giangVien : change.getRemoved()) {
            // Cập nhật UI khi xóa
            System.out.println("Đã xóa sinh viên: " + giangVien.getHoTen());
          }
        }
        if (change.wasUpdated()) {
          // Nếu có phần tử bị cập nhật
          System.out.println("Đã cập nhật sinh viên.");
        }
      }});
    giangVienTable.setItems(allGiangVien);
    // Xử lý sự kiện nút
    btnAdd.setOnAction(this::handleAdd);
    btnEdit.setOnAction(this::handleEdit);
    btnDelete.setOnAction(this::handleDelete);
    btnDetail.setOnAction(this::handleDetail);
    // Khởi tạo dịch vụ phân trang
    paginationService = new PaginationService<>(allGiangVien, 5);

    // Cập nhật TableView với dữ liệu của trang đầu tiên
    updateTableView();

    // Cập nhật các nút phân trang
    btnNextPage.setOnAction(event -> nextPage());
    btnPrevPage.setOnAction(event -> prevPage());
    btnFirstPage.setOnAction(event -> firstPage());
    btnLastPage.setOnAction(event -> lastPage());

  }

  // Cập nhật dữ liệu trong TableView
  private void updateTableView() {
    giangVienTable.setItems(paginationService.getCurrentPageData());
    lblPageInfo.setText("Trang " + (paginationService.getCurrentPage() + 1) + " / " + paginationService.getTotalPages());
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
  private void handleAdd(ActionEvent event) {
    try {
      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/AddGiangVienView.fxml"));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Thêm Giảng Viên");
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @FXML
  private void handleEdit(ActionEvent event) {
    try {
      // Lấy sinh viên được chọn trong bảng
      GiangVien selectedGiangVien = giangVienTable.getSelectionModel().getSelectedItem();
      if (selectedGiangVien == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText("Chưa chọn sinh viên!");
        alert.setContentText("Vui lòng chọn một sinh viên để chỉnh sửa.");
        alert.show();
        return;
      }

      // Tải form chỉnh sửa
      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/EditGiangVienView.fxml"));
      Parent root = fxmlLoader.load();

      Scene scene = new Scene(root);
      stage.setScene(scene);

      // Truyền đối tượng sinh viên vào controller
      EditGiangVienController controller = fxmlLoader.getController();
      controller.setGiangVien(selectedGiangVien);

      stage.setTitle("Sửa Sinh Viên");
      stage.showAndWait();

      // Sau khi chỉnh sửa, cập nhật lại danh sách (nếu cần)
      updateTableView();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  @FXML
  private void handleDetail(ActionEvent event) {
    try {
      // Lấy sinh viên được chọn trong bảng
      GiangVien selectedGiangVien = giangVienTable.getSelectionModel().getSelectedItem();
      if (selectedGiangVien == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText("Chưa chọn sinh viên!");
        alert.setContentText("Vui lòng chọn một sinh viên để xem chi tiết.");
        alert.show();
        return;
      }

      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/XemChiTietGiangVienView.fxml"));
      Parent root = fxmlLoader.load();

      Scene scene = new Scene(root);
      stage.setScene(scene);

      // Truyền đối tượng sinh viên vào controller
      XemChiTietGiangVienController controller = fxmlLoader.getController();
      controller.setGiangVien(selectedGiangVien);

      stage.setTitle("Thông Tin Chi Tiết Sinh Viên");
      stage.showAndWait();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleDelete(ActionEvent event) {
    // Lấy sinh viên được chọn trong TableView
    GiangVien selectedGiangVien = giangVienTable.getSelectionModel().getSelectedItem();

    if (selectedGiangVien != null) {
      // Hiển thị hộp thoại xác nhận xóa
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Xác nhận xóa");
      alert.setHeaderText("Bạn có chắc chắn muốn xóa sinh viên này?");
      alert.setContentText("Tên sinh viên: " + selectedGiangVien.getHoTen());

      // Hiển thị hộp thoại và chờ người dùng phản hồi
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          // Xử lý xóa sinh viên

          try {
            APIClient.delete("GiangVien/"+selectedGiangVien.getId(),selectedGiangVien);
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
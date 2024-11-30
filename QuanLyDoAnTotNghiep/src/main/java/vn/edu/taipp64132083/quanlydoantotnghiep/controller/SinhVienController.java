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
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Lop;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVienRequest;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.APIClient;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.PaginationService;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SinhVienController implements Initializable {

  @FXML
  private TableView<SinhVien> sinhVienTable;  // TableView để hiển thị danh sách sinh viên
  @FXML
  private TableColumn<SinhVien, String> hoTen, ngaySinh, email;
  @FXML
  private TableColumn<SinhVien, Integer> gioiTinh;
  @FXML
  private TableColumn<SinhVien, Lop> lop;
  @FXML
  private Button btnAdd, btnEdit, btnDelete, btnDetail;


  @FXML
  private Button btnNextPage, btnPrevPage, btnFirstPage, btnLastPage;  // Nút phân trang
  @FXML
  private Label lblPageInfo;  // Hiển thị thông tin trang hiện tại

  private ObservableList<SinhVien> allSinhVien;  // Toàn bộ danh sách sinh viên
  private PaginationService<SinhVien> paginationService;  // Dịch vụ phân trang


  public SinhVienController()
  {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    hoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
    ngaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    gioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
    lop.setCellValueFactory(new PropertyValueFactory<>("lop"));
    // Lấy danh sách SinhVien từ cache hoặc API
    Service service = new Service();
    try {
      allSinhVien = service.getSinhVienList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    allSinhVien.addListener((ListChangeListener<SinhVien>) change -> {
      while (change.next()) {
        if (change.wasAdded()) {
          // Nếu có phần tử mới được thêm vào
          for (SinhVien sinhVien : change.getAddedSubList()) {
            // Cập nhật UI khi thêm sinh viên
            System.out.println("Đã thêm sinh viên: " + sinhVien.getHoTen());
          }
        }
        if (change.wasRemoved()) {
          // Nếu có phần tử bị xóa
          for (SinhVien sinhVien : change.getRemoved()) {
            // Cập nhật UI khi xóa sinh viên
            System.out.println("Đã xóa sinh viên: " + sinhVien.getHoTen());
          }
        }
        if (change.wasUpdated()) {
          // Nếu có phần tử bị cập nhật
          System.out.println("Đã cập nhật sinh viên.");
        }
      }});
    sinhVienTable.setItems(allSinhVien);
    // Xử lý sự kiện nút
    btnAdd.setOnAction(this::handleAdd);
    btnEdit.setOnAction(this::handleEdit);
    btnDelete.setOnAction(this::handleDelete);
    btnDetail.setOnAction(this::handleDetail);
    // Khởi tạo dịch vụ phân trang
    paginationService = new PaginationService<>(allSinhVien, 5);

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
    sinhVienTable.setItems(paginationService.getCurrentPageData());
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
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/AddSinhVienView.fxml"));
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Thêm Sinh Viên");
      stage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @FXML
  private void handleEdit(ActionEvent event) {
    try {
      // Lấy sinh viên được chọn trong bảng
      SinhVien selectedSinhVien = sinhVienTable.getSelectionModel().getSelectedItem();
      if (selectedSinhVien == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText("Chưa chọn sinh viên!");
        alert.setContentText("Vui lòng chọn một sinh viên để chỉnh sửa.");
        alert.show();
        return;
      }

      // Tải form chỉnh sửa
      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/EditSinhVienView.fxml"));
      Parent root = fxmlLoader.load();

      Scene scene = new Scene(root);
      stage.setScene(scene);

      // Truyền đối tượng sinh viên vào controller
      EditSinhVienController controller = fxmlLoader.getController();
      controller.setSinhVien(selectedSinhVien);

      stage.setTitle("Sửa Sinh Viên");
      stage.showAndWait();

      // Sau khi chỉnh sửa, cập nhật lại danh sách (nếu cần)
      updateTableView();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @FXML
  private void handleDetail(ActionEvent event) {
    try {
      // Lấy sinh viên được chọn trong bảng
      SinhVien selectedSinhVien = sinhVienTable.getSelectionModel().getSelectedItem();
      if (selectedSinhVien == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Cảnh báo");
        alert.setHeaderText("Chưa chọn sinh viên!");
        alert.setContentText("Vui lòng chọn một sinh viên để xem chi tiết.");
        alert.show();
        return;
      }

      Stage stage = new Stage();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vn/edu/taipp64132083/quanlydoantotnghiep/XemChiTietSinhVienView.fxml"));
      Parent root = fxmlLoader.load();

      Scene scene = new Scene(root);
      stage.setScene(scene);

      // Truyền đối tượng sinh viên vào controller
      XemChiTietSinhVienController controller = fxmlLoader.getController();
      controller.setSinhVien(selectedSinhVien);

      stage.setTitle("Thông Tin Chi Tiết Sinh Viên");
      stage.showAndWait();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleDelete(ActionEvent event) {
    // Lấy sinh viên được chọn trong TableView
    SinhVien selectedSinhVien = sinhVienTable.getSelectionModel().getSelectedItem();

    if (selectedSinhVien != null) {
      // Hiển thị hộp thoại xác nhận xóa
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Xác nhận xóa");
      alert.setHeaderText("Bạn có chắc chắn muốn xóa sinh viên này?");
      alert.setContentText("Tên sinh viên: " + selectedSinhVien.getHoTen());

      // Hiển thị hộp thoại và chờ người dùng phản hồi
      alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
          // Xử lý xóa sinh viên

          try {
            APIClient.delete("SinhVien/"+selectedSinhVien.getId(),selectedSinhVien);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Thành công");
            alert1.setHeaderText("Sinh viên đã được xóa");
            alert1.showAndWait();
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
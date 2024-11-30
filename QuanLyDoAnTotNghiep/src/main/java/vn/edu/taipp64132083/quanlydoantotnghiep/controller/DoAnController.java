package vn.edu.taipp64132083.quanlydoantotnghiep.controller;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Lop;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.PaginationService;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;


import java.net.URL;
import java.util.ResourceBundle;
public class DoAnController implements Initializable {
  @FXML
  private TableView<SinhVien> sinhVienTable;  // TableView để hiển thị danh sách sinh viên
  @FXML
  private TableColumn<SinhVien, String> hoTen, ngaySinh, email;
  @FXML
  private TableColumn<SinhVien, Integer> gioiTinh;
  @FXML
  private TableColumn<SinhVien, Lop> lop;

  @FXML
  private Button btnNextPage, btnPrevPage, btnFirstPage, btnLastPage;  // Nút phân trang
  @FXML
  private Label lblPageInfo;  // Hiển thị thông tin trang hiện tại

  private ObservableList<SinhVien> allSinhVien;  // Toàn bộ danh sách sinh viên
  private PaginationService<SinhVien> paginationService;  // Dịch vụ phân trang


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
//    sinhVienTable.setItems(allSinhVien);
    // Khởi tạo dịch vụ phân trang
    paginationService = new PaginationService<>(allSinhVien, 3);

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
}

package vn.edu.taipp64132083.quanlydoantotnghiep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.PhanCongDoAn;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.PhanCongDoAnDoAn;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.PaginationService;
import vn.edu.taipp64132083.quanlydoantotnghiep.utils.Service;

public class PhanCongDoAnDoAnController {

  @FXML
  private Button btnAdd;

  @FXML
  private Button btnEdit;

  @FXML
  private Button btnDelete;

  @FXML
  private Button btnDetail;

  @FXML
  private Button btnLamMoi;

  @FXML
  private Button btnNextPage, btnPrevPage, btnFirstPage, btnLastPage;  // Nút phân trang
  @FXML
  private Label lblPageInfo;  // Hiển thị thông tin trang hiện tại

  private ObservableList<DoAn> allDoAn;  // Toàn bộ danh sách sinh viên
  private PaginationService<DoAn> paginationService;  // Dịch vụ phân trang

  @FXML
  private TableView<PhanCongDoAn> phanCongTable;

  @FXML
  private TableColumn<PhanCongDoAn, String> ngayPhanCongDoAn;

  @FXML
  private TableColumn<PhanCongDoAn, String> ghiChu;

  @FXML
  private TableColumn<PhanCongDoAn, String> doAn;

  @FXML
  private TableColumn<PhanCongDoAn, String> sinhVien;

  @FXML
  private TableColumn<PhanCongDoAn, String> giangVien;

  private ObservableList<PhanCongDoAn> phanCongList;

  @FXML
  public void initialize() {
    // Cấu hình các cột trong bảng
    ngayPhanCongDoAn.setCellValueFactory(new PropertyValueFactory<>("ngayPhanCongDoAn"));
    ghiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));
    doAn.setCellValueFactory(new PropertyValueFactory<>("doAn"));
    sinhVien.setCellValueFactory(new PropertyValueFactory<>("sinhVien"));
    giangVien.setCellValueFactory(new PropertyValueFactory<>("giangVien"));

    try {
      phanCongList = Service.getPhanCongList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // Gắn dữ liệu vào bảng
    phanCongTable.setItems(phanCongList);

    // Cài đặt các sự kiện nút
//    btnAdd.setOnAction(event -> handleAdd());
//    btnEdit.setOnAction(event -> handleEdit());
//    btnDelete.setOnAction(event -> handleDelete());
//    btnDetail.setOnAction(event -> handleDetail());
//    btnLamMoi.setOnAction(event -> handleLamMoi());

    paginationService = new PaginationService<>(phanCongList  , 5);

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
    phanCongTable.setItems(paginationService.getCurrentPageData());
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

}

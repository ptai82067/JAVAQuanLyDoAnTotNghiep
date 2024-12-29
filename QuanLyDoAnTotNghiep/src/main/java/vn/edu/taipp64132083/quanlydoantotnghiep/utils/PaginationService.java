package vn.edu.taipp64132083.quanlydoantotnghiep.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Identifiable;

public class PaginationService<T extends Identifiable> {

  private ObservableList<T> allItems;  // Toàn bộ dữ liệu
  private ObservableList<T> currentPageData;  // Dữ liệu của trang hiện tại
  private int itemsPerPage;  // Số phần tử trên mỗi trang
  private int currentPage;  // Trang hiện tại

  public PaginationService(ObservableList<T> allItems, int itemsPerPage) {
    this.allItems = allItems;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 0;
    updateCurrentPageData();  // Cập nhật dữ liệu của trang đầu tiên
  }

  // Cập nhật dữ liệu của trang hiện tại
  public void updateCurrentPageData() {
    int startIndex = currentPage * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, allItems.size());
    currentPageData = FXCollections.observableArrayList(allItems.subList(startIndex, endIndex));
  }

  // Lấy dữ liệu của trang hiện tại
  public ObservableList<T> getCurrentPageData() {
    return currentPageData;
  }

  // Chuyển đến trang tiếp theo
  public void nextPage() {
    if ((currentPage + 1) * itemsPerPage < allItems.size()) {
      currentPage++;
      updateCurrentPageData();
    }
  }

  // Chuyển đến trang trước
  public void prevPage() {
    if (currentPage > 0) {
      currentPage--;
      updateCurrentPageData();
    }
  }

  // Chuyển đến trang đầu tiên
  public void firstPage() {
    currentPage = 0;
    updateCurrentPageData();
  }

  // Chuyển đến trang cuối
  public void lastPage() {
    currentPage = (allItems.size() / itemsPerPage);
    if (allItems.size() % itemsPerPage == 0) {
      currentPage--;
    }
    updateCurrentPageData();
  }

  // Lấy số trang hiện tại
  public int getCurrentPage() {
    return currentPage;
  }

  // Lấy tổng số trang
  public int getTotalPages() {
    return (int) Math.ceil((double) allItems.size() / itemsPerPage);
  }
  // Làm mới dữ liệu và cập nhật trang hiện tại
  public void refresh(ObservableList<T> updatedItems) {
    // Cập nhật toàn bộ danh sách
    this.allItems = updatedItems;

    // Kiểm tra nếu số lượng dữ liệu giảm làm trang hiện tại bị vượt quá giới hạn
    if (currentPage * itemsPerPage >= allItems.size()) {
      currentPage = (int) Math.ceil((double) allItems.size() / itemsPerPage) - 1;
      if (currentPage < 0) {
        currentPage = 0;  // Nếu danh sách trống, đảm bảo không bị lỗi
      }
    }

    // Cập nhật lại dữ liệu trang hiện tại
    updateCurrentPageData();
  }

}
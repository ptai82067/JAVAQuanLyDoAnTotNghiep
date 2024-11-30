package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Identifiable;

import java.util.HashMap;
import java.util.Map;

public class APIDataCache {

  private static APIDataCache instance;
  private final Map<String, ObservableList<Identifiable>> cache = new HashMap<>();

  // Lấy instance của APIDataCache
  public static APIDataCache getInstance() {
    if (instance == null) {
      instance = new APIDataCache();
    }
    return instance;
  }

  // Phương thức thêm mới đối tượng vào cache (không cần getId())
  public <T extends Identifiable> void addInCache(String tableName, T newItem) {
    ObservableList<T> list = (ObservableList<T>) cache.computeIfAbsent(tableName, k -> FXCollections.observableArrayList());
    list.add(newItem); // Thêm đối tượng mới vào danh sách
  }

  // Phương thức cập nhật đối tượng trong cache
  public <T extends Identifiable> void updateInCache(String tableName, T updatedItem) {
    ObservableList<T> list = (ObservableList<T>) cache.get(tableName);
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        T item = list.get(i);
        if (item.getId() == updatedItem.getId()) {
          list.set(i, updatedItem); // Cập nhật đối tượng nếu ID trùng khớp
          return;
        }
      }
    } else {
      throw new IllegalArgumentException("Table not found: " + tableName);
    }
  }

  // Phương thức xóa đối tượng trong cache theo ID
  public <T extends Identifiable> void deleteInCache(String tableName, T itemToDelete) {
    ObservableList<T> list = (ObservableList<T>) cache.get(tableName);
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        T item = list.get(i);
        if (item.getId() == itemToDelete.getId()) {
          list.remove(i); // Xóa đối tượng nếu ID trùng khớp
          return;
        }
      }
    } else {
      throw new IllegalArgumentException("Table not found: " + tableName);
    }
  }

  // Hàm lấy danh sách từ cache theo tên bảng
  // Hàm lấy danh sách từ cache theo tên bảng và kiểu của đối tượng
  public <T extends Identifiable> ObservableList<T> getListFromCache(String tableName, Class<T> type) {
    ObservableList<Identifiable> list = cache.get(tableName);
    if (list != null) {
      // Tạo danh sách ObservableList của kiểu T
      ObservableList<T> typedList = FXCollections.observableArrayList();
      for (Identifiable item : list) {
        if (type.isInstance(item)) {
          typedList.add(type.cast(item)); // Ép kiểu an toàn
        }
      }
      return typedList;
    }
    return FXCollections.observableArrayList(); // Trả về danh sách rỗng nếu không tìm thấy
  }

}
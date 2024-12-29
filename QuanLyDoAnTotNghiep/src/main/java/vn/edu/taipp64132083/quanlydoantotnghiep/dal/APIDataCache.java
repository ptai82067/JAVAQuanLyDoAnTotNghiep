package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.Identifiable;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SessionData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIDataCache {

  private static APIDataCache instance;
  //Lưu trữ cache, mỗi String (tên bảng) ánh xạ đến một danh sách các đối tượng thực thi Identifiable
  private final Map<String, ObservableList<Identifiable>> cache = new HashMap<>();
  // Lấy instance của APIDataCache
  public static APIDataCache getInstance() {
    if (instance == null) {
      instance = new APIDataCache();
    }
    return instance;
  }

  // Phương thức thêm mới danh sách (đối tượng) vào cache
  public <T extends Identifiable> void addInCache(String tableName, T newItem) {
    //Kiểm tra xem tên bảng (tableName) đã tồn tại trong cache chưa.
    //Nếu chưa, tạo một danh sách trống (ObservableList) cho bảng.
    ObservableList<T> list = (ObservableList<T>) cache.computeIfAbsent(tableName,k -> FXCollections.observableArrayList());
    list.add(newItem); // Thêm đối tượng mới vào danh sách
  }

  // Phương thức cập nhật đối tượng trong cache
  public <T extends Identifiable> void updateInCache(String tableName, T updatedItem) {
    //Lấy danh sách từ cache dựa trên tableName.
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
  public void saveLoginSession(int userId, String role,  GiangVien userDetails, List<String> permissions) {
    SessionData sessionData = new SessionData(userId, role, userDetails, permissions);
    ObservableList<Identifiable> sessionList = FXCollections.observableArrayList(sessionData);
    cache.put("LoginSession", sessionList);
  }

}
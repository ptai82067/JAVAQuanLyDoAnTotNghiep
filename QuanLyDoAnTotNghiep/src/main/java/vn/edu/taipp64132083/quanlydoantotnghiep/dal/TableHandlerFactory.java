package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TableHandlerFactory {
  // Map ánh xạ từ tableName đến Factory
  private static final Map<String, Function<String, EventHandler<?>>> handlerMap = new HashMap<>();

  static {
    // Thêm ánh xạ cho SinhVien
    handlerMap.put("SinhVien", eventType -> SinhVienHandlerFactory.getHandler(eventType));

    // Thêm ánh xạ cho DoAn
    handlerMap.put("DoAn", eventType -> DoAnHandlerFactory.getHandler(eventType));

    // Thêm ánh xạ cho các bảng khác tại đây
    // handlerMap.put("Khoa", eventType -> KhoaHandlerFactory.getHandler(eventType));
    // handlerMap.put("Lop", eventType -> LopHandlerFactory.getHandler(eventType));
  }

  // Phương thức lấy handler dựa trên tableName và eventType
  public static EventHandler<?> getHandler(String tableName, String eventType) {
    Function<String, EventHandler<?>> handlerFactory = handlerMap.get(tableName);

    if (handlerFactory == null) {
      throw new IllegalArgumentException("No handler found for table: " + tableName);
    }

    return handlerFactory.apply(eventType);
  }
}
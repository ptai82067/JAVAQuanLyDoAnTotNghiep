package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;

public class SinhVienHandlerFactory {
  public static EventHandler<SinhVien> getHandler(String eventType) {
    switch (eventType) {
      case "show":
        return new AddSinhVienHandler();
      case "update":
        return new UpdateSinhVienHandler();
      default:
        throw new IllegalArgumentException("Unknown event type for SinhVien: " + eventType);
    }
  }
}
package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;

public class DoAnHandlerFactory {
  public static EventHandler<DoAn> getHandler(String eventType) {
    switch (eventType) {
      case "show":
        return new AddDoAnHandler();
      case "update":
        return new UpdateDoAnHandler();
      default:
        throw new IllegalArgumentException("Unknown event type for DoAn: " + eventType);
    }
  }
}
package vn.edu.taipp64132083.quanlydoantotnghiep.utils;
import com.google.gson.Gson;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.EventHandler;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.TableHandlerFactory;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.DoAn;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.EventMessage;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SinhVien;

import java.net.Socket;
import java.net.SocketImplFactory;


public class MessageListener {

  public void onMessage(String message) {
    System.out.println("Received message: " + message);

    try {
      // 1. Parse JSON message thành đối tượng EventMessage
      Gson gson = new Gson();
      EventMessage eventMessage = gson.fromJson(message, EventMessage.class);

      // 2. Lấy thông tin tableName và eventType từ message
      String tableName = eventMessage.getData().getTableName(); // Lấy tên bảng
      String eventType = eventMessage.getData().getEventType(); // Lấy loại sự kiện (Add, Update)

      // 3. Lấy handler tương ứng từ TableHandlerFactory
      EventHandler<?> handler = TableHandlerFactory.getHandler(tableName, eventType);

      // 4. Deserialize record dựa trên tableName và xử lý
      if ("SinhVien".equals(tableName)) {
        // Nếu là bảng SinhVien, deserialize thành đối tượng SinhVien
        SinhVien sinhVien = gson.fromJson(eventMessage.getData().getRecord(), SinhVien.class);
        ((EventHandler<SinhVien>) handler).handle(sinhVien); // Gọi handler xử lý
      } else if ("DoAn".equals(tableName)) {
        // Nếu là bảng DoAn, deserialize thành đối tượng DoAn
        DoAn doAn = gson.fromJson(eventMessage.getData().getRecord(), DoAn.class);
        ((EventHandler<DoAn>) handler).handle(doAn); // Gọi handler xử lý
      }
    } catch (Exception e) {
      // 5. Xử lý lỗi nếu quá trình parse hoặc xử lý gặp vấn đề
      System.err.println("Error processing message:");
      e.printStackTrace();
    }
  }
}

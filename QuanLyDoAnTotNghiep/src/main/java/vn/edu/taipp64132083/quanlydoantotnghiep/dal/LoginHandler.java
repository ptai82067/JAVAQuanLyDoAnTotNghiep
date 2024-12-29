package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vn.edu.taipp64132083.quanlydoantotnghiep.dal.APIDataCache;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.GiangVien;
import vn.edu.taipp64132083.quanlydoantotnghiep.model_dto.SessionData;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class LoginHandler {
  private static final String LOGIN_API_URL = "https://quanlyduan-production.up.railway.app/api/dangnhap";

  public boolean login(String username, String password) {
    try {
      // Tạo payload JSON
      String payload = new Gson().toJson(Map.of("username", username, "password", password));

      // Thiết lập kết nối HTTP
      URL url = new URL(LOGIN_API_URL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);

      // Gửi yêu cầu POST
      try (OutputStream os = connection.getOutputStream()) {
        os.write(payload.getBytes(StandardCharsets.UTF_8));
      }

      // Kiểm tra mã phản hồi HTTP
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // Đọc phản hồi JSON
        String jsonResponse = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, Object> response = new Gson().fromJson(jsonResponse, new TypeToken<Map<String, Object>>() {}.getType());

        // Kiểm tra trạng thái phản hồi
        if ("success".equals(response.get("status"))) {
          // Lưu thông tin session
          saveSession(response);
          return true;
        } else {
          System.err.println("Login failed: " + response.get("message"));
        }
      } else {
        System.err.println("HTTP error code: " + responseCode);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  private void saveSession(Map<String, Object> response) {
    try {
      // Trích xuất thông tin từ phản hồi
      int userId = ((Double) response.get("userId")).intValue();
      String role = (String) response.get("role");
      Gson gson = new Gson();
      String userDetailsJson = gson.toJson(response.get("userDetails"));
      GiangVien userDetails = gson.fromJson(userDetailsJson, GiangVien.class);

      // Chuyển Iterable<String> sang List<String> rồi sang ObservableList<String>
      Iterable<String> permissionIterable = (Iterable<String>) response.get("permissions");
      ObservableList<String> permissions = FXCollections.observableArrayList();
      permissionIterable.forEach(permissions::add);

      // Tạo đối tượng SessionData
      SessionData sessionData = new SessionData(userId, role, userDetails, permissions);

      // Lưu vào APIDataCache
      APIDataCache.getInstance().addInCache("LoginSession", sessionData);
    } catch (Exception e) {
      System.err.println("Error saving session: " + e.getMessage());
      e.printStackTrace();
    }
  }

}

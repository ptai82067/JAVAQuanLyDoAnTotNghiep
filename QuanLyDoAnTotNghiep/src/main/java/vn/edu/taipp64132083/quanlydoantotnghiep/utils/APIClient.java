package vn.edu.taipp64132083.quanlydoantotnghiep.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class APIClient {
//  private static final String BASE_URL = "http://localhost:8000/api/";
  private static final String BASE_URL = "https://quanlyduan-production.up.railway.app/api/"; // URL gốc của API
  private static final Gson gson = new Gson();
  private static final HttpClient client = HttpClient.newHttpClient();

  // Phương thức GET trả về một đối tượng đơn (T)
  public static <T> T show(String endpoint, Class<T> clazz) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + endpoint))
            .header("Content-Type", "application/json")
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      return gson.fromJson(response.body(), clazz); // Chuyển đổi thành đối tượng kiểu T
    } else {
      throw new Exception("GET request failed with status: " + response.statusCode());
    }
  }

  // Phương thức GET trả về một danh sách các đối tượng (List<T>)
  public static <T> List<T> get(String endpoint, Class<T> clazz) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + endpoint))
            .header("Content-Type", "application/json")
            .GET()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      Type listType = TypeToken.getParameterized(List.class, clazz).getType();
      return gson.fromJson(response.body(), listType); // Chuyển đổi thành List<T>
    } else {
      throw new Exception("GET request failed with status: " + response.statusCode());
    }
  }

  // Phương thức POST để gửi dữ liệu và nhận kết quả trả về (T)
  public static <T> T post(String endpoint, T object) throws Exception {
    String json = gson.toJson(object);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + endpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 201) {
      return gson.fromJson(response.body(), (Class<T>) object.getClass()); // Chuyển đổi dữ liệu JSON thành đối tượng
    } else {
      throw new Exception("POST request failed with status: " + response.statusCode());
    }
  }

  // Phương thức PUT để cập nhật dữ liệu (T)
  public static <T> T put(String endpoint, T object) throws Exception {
    String json = gson.toJson(object);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + endpoint))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      return gson.fromJson(response.body(), (Class<T>) object.getClass()); // Chuyển đổi dữ liệu JSON thành đối tượng
    } else {
      throw new Exception("PUT request failed with status: " + response.statusCode());
    }
  }

  // Phương thức DELETE để xóa dữ liệu
  public static <T> T delete(String endpoint, T object) throws Exception {
    String json = gson.toJson(object);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + endpoint))
            .header("Content-Type", "application/json")
            .DELETE()
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      return gson.fromJson(response.body(), (Class<T>) object.getClass()); // Chuyển đổi dữ liệu JSON thành đối tượng
    } else {
      throw new Exception("DELETE request failed with status: " + response.statusCode());
    }
  }
}

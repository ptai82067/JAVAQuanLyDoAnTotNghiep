package vn.edu.taipp64132083.quanlydoantotnghiep.model_dto;


import java.util.List;
import java.util.Map;

public class SessionData implements Identifiable {
  private int userId;
  private String role;
  private Map<String, Object> userDetails;
  private List<String> permissions;

  public SessionData(int userId, String role, Map<String, Object> userDetails, List<String> permissions) {
    this.userId = userId;
    this.role = role;
    this.userDetails = userDetails;
    this.permissions = permissions;
  }

  @Override
  public int getId() {
    return userId; // Trả về ID của user làm định danh
  }

  // Getter và Setter
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Map<String, Object> getUserDetails() {
    return userDetails;
  }

  public void setUserDetails(Map<String, Object> userDetails) {
    this.userDetails = userDetails;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }
}

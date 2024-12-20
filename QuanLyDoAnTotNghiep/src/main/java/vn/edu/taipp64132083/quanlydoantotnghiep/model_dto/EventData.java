package vn.edu.taipp64132083.quanlydoantotnghiep.model_dto;

import com.google.gson.JsonObject;

// Lớp chứa dữ liệu chính của sự kiện
public class EventData {
  private String tableName;
  private String eventType;
  private JsonObject record; // Để linh hoạt lưu bất kỳ dữ liệu nào, dùng JsonObject

  // Getters và Setters

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public JsonObject getRecord() {
    return record;
  }

  public void setRecord(JsonObject record) {
    this.record = record;
  }
}
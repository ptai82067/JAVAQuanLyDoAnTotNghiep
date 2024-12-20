package vn.edu.taipp64132083.quanlydoantotnghiep.model_dto;

// Lớp gốc chứa thông tin về sự kiện
public class EventMessage {
  private String event;
  private String channel;
  private EventData data;

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public EventData getData() {
    return data;
  }

  public void setData(EventData data) {
    this.data = data;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }
}
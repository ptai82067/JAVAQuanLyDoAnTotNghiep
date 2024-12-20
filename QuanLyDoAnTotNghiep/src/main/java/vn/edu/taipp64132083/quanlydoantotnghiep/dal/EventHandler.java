package vn.edu.taipp64132083.quanlydoantotnghiep.dal;

public interface EventHandler<T> {
  void handle(T record);
}
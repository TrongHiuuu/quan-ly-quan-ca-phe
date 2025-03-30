package DAO;

import java.util.List;

/**
 * T: Kiểu của DTO chi tiết (ví dụ: CTHoaDonDTO)
 * K1: Kiểu của khóa ngoại thứ nhất (ví dụ: Integer cho idHD)
 * K2: Kiểu của khóa ngoại thứ hai (ví dụ: Integer cho idSP)
 */
public interface DetailDAO<T, K1, K2> {
    List<T> getAllByKey1(K1 key1);    // Lấy tất cả chi tiết theo khóa ngoại thứ nhất
    List<T> getAllByKey2(K2 key2);    // Lấy tất cả chi tiết theo khóa ngoại thứ hai
    boolean add(T entity);            // Thêm một chi tiết mới
    void closeConnection();           // Đóng kết nối
}
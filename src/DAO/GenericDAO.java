package DAO;

import java.util.List;

/**
 * T: Kiểu của DTO
 * K: kiểu của khóa chính
 */

//Interface sẽ định nghĩa các phương thức mà các DAO cần phải implement
public interface GenericDAO<T, K> {
    List<T> getAll();           // Lấy tất cả bản ghi
    T findById(K id);           // Tìm theo ID
    boolean isExist(K id);      // Kiểm tra tồn tại
    boolean add(T entity);      // Thêm mới
    void closeConnection();     // Đóng kết nối
}

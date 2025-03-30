package DAO;

import lib.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDetailDAO<T, K1, K2> implements DetailDAO<T, K1, K2> {
    protected final Database db = new Database();

    @Override
    public List<T> getAllByKey1(K1 key1) {
        List<T> list = new ArrayList<>();
        String sql = getQueryByKey1(key1);
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                T entity = mapResultSetToDTO(rs);
                list.add(entity);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> getAllByKey2(K2 key2) {
        List<T> list = new ArrayList<>();
        String sql = getQueryByKey2(key2);
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                T entity = mapResultSetToDTO(rs);
                list.add(entity);
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public abstract boolean add(T entity);

    @Override
    public void closeConnection() {
        db.close();
    }

    // Các phương thức trừu tượng mà lớp con phải triển khai
    protected abstract String getQueryByKey1(K1 key1);    // Truy vấn theo khóa ngoại 1
    protected abstract String getQueryByKey2(K2 key2);    // Truy vấn theo khóa ngoại 2
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException; // Ánh xạ ResultSet thành DTO
}
package DAO;

import lib.Database;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseDAO<T> implements IBaseDAO<T> {
    protected final Database db = new Database();

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        ResultSet rs = db.getAll(sql);
        try {
            while (rs != null && rs.next()) {
                T entity = mapResultSetToDTO(rs);
                list.add(entity);
                return list;
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public T findById(String column, int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + column + " = " + id;
        ResultSet rs = db.getOne(sql);
        try {
            if (rs != null && rs.next()) {
                T entity = mapResultSetToDTO(rs);
                rs.close();
                return entity;
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isExist(String column, int id) {
        String sql = "SELECT COUNT(*) FROM " + getTableName() + " WHERE " + column + " = " + id;
        ResultSet rs = db.getOne(sql);
        try {
            if (rs != null && rs.next()) {
                boolean exists = rs.getInt(1) > 0;
                rs.close();
                return exists;
            }
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(List<Object> tuple) {
        String sql = "INSERT INTO "+ getTableName() + " "+
                "(" + String.join(",",getTableColumns() + ") " +
                "VALUES (" +
                String.join(",", Collections.nCopies(tuple.size(), "?")) + ")";

        return db.execute(sql, tuple);
    }

    @Override
    public void closeConnection() {
        db.close();
    }

    // *** Các phương thức trừu tượng mà lớp con phải triển khai
    protected abstract String getTableName();
    protected abstract List<String> getTableColumns();
    protected abstract T mapResultSetToDTO(ResultSet rs) throws SQLException;
    // ***
}

package lib;

import java.sql.*;
import java.util.List;

import static config.Config.DB_HOST;
import static config.Config.DB_USER;
import static config.Config.DB_PASS;
import static config.Config.DB_NAME;

public class Database {
    private String host = DB_HOST;
    private String user = DB_USER;
    private String pass = DB_PASS;
    private String dbname = DB_NAME;

    private Connection link;
    private String error;

    public Database() {
        this.connectDB();
    }

    private void connectDB() {
        try {
            String url = "jdbc:mysql://" + this.host + "/" + this.dbname;
            this.link = DriverManager.getConnection(url, this.user, this.pass);
        } catch (SQLException e) {
            this.error = "Connection fail: " + e.getMessage();
            this.link = null;
        }
    }

    public Connection getLink() {
        return this.link;
    }
    

    public boolean execute(String sql, List<Object> params) {
        try (PreparedStatement pstmt = this.link.prepareStatement(sql)) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i));
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // Trả về tất cả các dòng dưới dạng ResultSet
    public ResultSet getAll(String sql) {
        try (PreparedStatement pstmt = this.link.prepareStatement(sql);) {
            if (this.link != null) {
                return pstmt.executeQuery();
                // Lưu ý: Người dùng cần đóng ResultSet sau khi sử dụng
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    // Trả về một dòng dưới dạng ResultSet
    public ResultSet getOne(String sql) {
        try {
            if (this.link != null) {
                try (PreparedStatement pstmt = this.link.prepareStatement(sql)) {
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        return rs;
                    }
                    return null;
                }
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int getNewAutoIncrementNumber(String tableName) {
        String sql = "SELECT AUTO_INCREMENT as newID " +
                "FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = ? " +
                "AND TABLE_NAME = ?";

        try {
            if (this.link != null) {
                try (PreparedStatement pstmt = this.link.prepareStatement(sql)) {
                    pstmt.setString(1, DB_NAME);
                    pstmt.setString(2, tableName);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int newID = rs.getInt("newID");
                        rs.close();
                        return newID;
                    }
                    return -1;
                }
            }
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    // Method để đóng kết nối
    public void close() {
        try {
            if (this.link != null && !this.link.isClosed()) {
                this.link.close();
            }
        } catch (SQLException e) {
            this.error = "Error closing connection: " + e.getMessage();
        }
    }
}
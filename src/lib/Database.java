package lib;

import java.sql.*;

import static config.Config.DB_HOST;
import static config.Config.DB_USER;
import static config.Config.DB_PASS;
import static config.Config.DB_NAME;

public class Database {
    private String host = DB_HOST;      // Thay bằng host thực tế hoặc sử dụng config
    private String user = DB_USER;      // Thay bằng username thực tế hoặc sử dụng config
    private String pass = DB_PASS;      // Thay bằng password thực tế hoặc sử dụng config
    private String dbname = DB_NAME;    // Thay bằng database name thực tế hoặc sử dụng config

    private Connection link;
    private String error;

    public Database() {
        this.connectDB();
    }

    private void connectDB() {
        try {
            // Tạo URL kết nối
            String url = "jdbc:mysql://" + this.host + "/" + this.dbname;
            // Thiết lập kết nối
            this.link = DriverManager.getConnection(url, this.user, this.pass);
            // Thiết lập để trả về kiểu INT và FLOAT native (tương tự MYSQLI_OPT_INT_AND_FLOAT_NATIVE)
            // Trong Java, điều này được xử lý tự động bởi driver

        } catch (SQLException e) {
            this.error = "Connection fail: " + e.getMessage();
            this.link = null;
        }
    }

    public Connection getLink() {
        return this.link;
    }

    public boolean execute(String sql) {
        try {
            if (this.link != null) {
                Statement stmt = this.link.createStatement();
                stmt.execute(sql);
                stmt.close();
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    // Trả về tất cả các dòng dưới dạng ResultSet
    //TaiKhoanDTO{id=1,tenTK=... ...}
    //TaiKhoanDTO{id=2, tenTK= ...}
    //....
    public ResultSet getAll(String sql) {
        try {
            if (this.link != null) {
                Statement stmt = this.link.createStatement();
                return stmt.executeQuery(sql);
                // Lưu ý: Người dùng cần đóng ResultSet và Statement sau khi sử dụng
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
                Statement stmt = this.link.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    return rs;
                }
                rs.close();
                stmt.close();
            }
            return null;
        } catch (SQLException e) {
            return null;
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
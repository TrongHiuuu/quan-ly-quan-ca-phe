package DTO;

public class TaiKhoanDTO {
    private int idTK;
    private String tenTK;
    private String matkhau;
    private String hoten;
    private String email;
    private String dienthoai;
    private boolean trangthai;
    private String idNQ;

    public TaiKhoanDTO() {
        this.idTK = 0;
        this.tenTK = "";
        this.matkhau = "";
        this.hoten = "";
        this.email = "";
        this.dienthoai = "";
        this.trangthai = false;
        this.idNQ = "";
    }

    public TaiKhoanDTO(int idTK, String tenTK, String matkhau, String hoten, String email, String dienthoai, boolean trangthai, String idNQ) {
        this.idTK = idTK;
        this.tenTK = tenTK;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.email = email;
        this.dienthoai = dienthoai;
        this.trangthai = trangthai;
        this.idNQ = idNQ;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public boolean getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public String getIdNQ() {
        return idNQ;
    }

    public void setIdNQ(String idNQ) {
        this.idNQ = idNQ;
    }

    /*
     *Trong java, truy vấn trả về đối tượng của mysqli sẽ trả về một ResultSet
     *Nếu trong database có dữ liệu:
     *idTK=1, tenTK="user1", matkhau="pass123", hoten="Nguyen Van A",
     *email="a@gmail.com", dienthoai="0123456789",
     *trangthai=1, idNQ="NQ001"
     *Thì kết quả truy vấn sẽ trả về ResultSet
     * TaiKhoanDTO{
     *           idTK=1,
     *           tenTK='user1',
     *           matKhau='pass123',
     *           hoTen='Nguyen Van A',
     *           email='a@gmail.com',
     *           dienThoai='0123456789',
     *           trangThai=true,
     *           idNQ='NQ001'
     * }
     */
    @Override
    public String toString() {
        return "TaiKhoanDTO{" +
                "idTK=" + idTK +
                ", tenTK='" + tenTK + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", hoTen='" + hoten + '\'' +
                ", email='" + email + '\'' +
                ", dienThoai='" + dienthoai + '\'' +
                ", trangThai=" + trangthai +
                ", idNQ='" + idNQ + '\'' +
                '}';
    }
}

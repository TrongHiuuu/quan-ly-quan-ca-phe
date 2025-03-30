package DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDTO {
    private int idHD;
    private LocalDate ngaytao;
    private int idTK;
    private List<DTO.CTHoaDonDTO> chiTietHoaDon;

    public HoaDonDTO() {
        this.idHD = 0;
        this.ngaytao = LocalDate.now();
        this.idTK = 0;
        this.chiTietHoaDon = new ArrayList<>();
    }

    public HoaDonDTO(int idHD, LocalDate ngaytao, int idTK){
        this.idHD = idHD;
        this.ngaytao = ngaytao;
        this.idTK = idTK;
        this.chiTietHoaDon = new ArrayList<>();
    }

    public HoaDonDTO(LocalDate ngaytao, int idTK){
        this.idHD = 0;
        this.ngaytao = ngaytao;
        this.idTK = idTK;
        this.chiTietHoaDon = new ArrayList<>();
    }


    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public LocalDate getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(LocalDate ngaytao) {
        this.ngaytao = ngaytao;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public List<DTO.CTHoaDonDTO> getCTHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(List<DTO.CTHoaDonDTO> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }

    @Override
    public String toString() {
        return "HoaDonDTO{" +
                "idHD=" + idHD +
                ", ngaytao=" + ngaytao +
                ", idTK=" + idTK +
                ", chiTietHoaDon=" + chiTietHoaDon +
                '}';
    }
}

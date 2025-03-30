package DTO;

public class CTHoaDonDTO {
    private int idSP;
    private int idHD;
    private int soluong;
    private int gialucdat;

    public CTHoaDonDTO(int idSP, int idHD, int soluong, int gialucdat) {
        this.idSP = idSP;
        this.idHD = idHD;
        this.soluong = soluong;
        this.gialucdat = gialucdat;
    }

    // Getters, setters
    public int getIdSP() {
        return idSP;
    }
    public int getIdHD() {
        return idHD;
    }
    public int getSoluong() {
        return soluong;
    }
    public int getGialucdat() {
        return gialucdat;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setGialucdat(int gialucdat) {
        this.gialucdat = gialucdat;
    }
}
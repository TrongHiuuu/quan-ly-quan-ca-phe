CREATE TABLE `sanpham` (
  `idSP` int(11) PRIMARY KEY,
  `tenSP` nvarchar(255),
  `giaban` int(11),
  `hinhanh` varchar(255),
  `trangthai` bool,
  `idDM` int(11)
);

CREATE TABLE `danhmuc` (
  `idDM` int(11) PRIMARY KEY,
  `tenDM` nvarchar(255),
  `trangthai` bool,
  `idDMCha` int(11)
);

CREATE TABLE `CT_hoadon` (
  `idSP` int(11),
  `idHD` int(11),
  `soluong` int(11),
  `gialucdat` int(11),
  PRIMARY KEY (`idSP`, `idHD`)
);

CREATE TABLE `hoadon` (
  `idHD` int(11) PRIMARY KEY,
  `ngaytao` date,
  `idTK` int(11)
);

CREATE TABLE `congthuc` (
  `idCT` int(11) PRIMARY KEY,
  `mota` nvarchar(255),
  `trangthai` bool,
  `idSP` int(11)
);

CREATE TABLE `CT_congthuc` (
  `idCT` int(11),
  `idNL` int(11),
  `soluong` int(11),
  PRIMARY KEY (`idCT`, `idNL`)
);

CREATE TABLE `taikhoan` (
  `idTK` int(11) PRIMARY KEY,
  `tenTK` nvarchar(255),
  `matkhau` varchar(255),
  `email` varchar(255),
  `trangthai` bool,
  `idNQ` int(11)
);

CREATE TABLE `nhomquyen` (
  `idNQ` int(11) PRIMARY KEY,
  `tenNQ` nvarchar(255)
);

CREATE TABLE `nguyenlieu` (
  `idNL` int(11) PRIMARY KEY,
  `donvi` varchar(255),
  `tenNL` varchar(255)
);

CREATE TABLE `lo_nguyenlieu` (
  `idNL` int(11),
  `idPN` int(11),
  `soluongnhap` float,
  `tonkho` float,
  `dongia` int(11),
  `hsd` date,
  PRIMARY KEY (`idNL`, `idPN`)
);

CREATE TABLE `phieunhap` (
  `idPN` int(11) PRIMARY KEY,
  `ngaytao` date,
  `ngaycapnhat` date,
  `idTK` int(11),
  `idNCC` int(11),
  `idTT` int(11)
);

CREATE TABLE `trangthai_PN` (
  `idTT` int(11),
  `tenTT` nvarchar(255)
);

CREATE TABLE `nhacungcap` (
  `idNCC` int(11),
  `tenNCC` nvarchar(255),
  `diachi` nvarchar(255),
  `sdt` varchar(10),
  `email` nvarchar(255),
  `trangthai` bool
);

ALTER TABLE `danhmuc` ADD FOREIGN KEY (`idDM`) REFERENCES `danhmuc` (`idDMCha`);

ALTER TABLE `sanpham` ADD FOREIGN KEY (`idDM`) REFERENCES `danhmuc` (`idDM`);

ALTER TABLE `CT_hoadon` ADD FOREIGN KEY (`idSP`) REFERENCES `sanpham` (`idSP`);

ALTER TABLE `CT_hoadon` ADD FOREIGN KEY (`idHD`) REFERENCES `hoadon` (`idHD`);

ALTER TABLE `congthuc` ADD FOREIGN KEY (`idSP`) REFERENCES `sanpham` (`idSP`);

ALTER TABLE `CT_congthuc` ADD FOREIGN KEY (`idCT`) REFERENCES `congthuc` (`idCT`);

ALTER TABLE `hoadon` ADD FOREIGN KEY (`idTK`) REFERENCES `taikhoan` (`idTK`);

ALTER TABLE `taikhoan` ADD FOREIGN KEY (`idNQ`) REFERENCES `nhomquyen` (`idNQ`);

ALTER TABLE `CT_congthuc` ADD FOREIGN KEY (`idNL`) REFERENCES `nguyenlieu` (`idNL`);

ALTER TABLE `lo_nguyenlieu` ADD FOREIGN KEY (`idNL`) REFERENCES `nguyenlieu` (`idNL`);

ALTER TABLE `lo_nguyenlieu` ADD FOREIGN KEY (`idPN`) REFERENCES `phieunhap` (`idPN`);

ALTER TABLE `phieunhap` ADD FOREIGN KEY (`idTK`) REFERENCES `taikhoan` (`idTK`);

ALTER TABLE `phieunhap` ADD FOREIGN KEY (`idTT`) REFERENCES `trangthai_PN` (`idTT`);

ALTER TABLE `phieunhap` ADD FOREIGN KEY (`idNCC`) REFERENCES `nhacungcap` (`idNCC`);

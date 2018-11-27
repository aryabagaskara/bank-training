package com.tabeldata.bootcamp.Model;

import java.math.BigDecimal;
import java.util.Date;

public class TransaksiTabungan extends Tabungan{
    private Integer id;
    private Integer jenisTransksi;
    private Date tanggalTransaksi;
    private BigDecimal kredit;
    private BigDecimal debet;
    private BigDecimal saldo= super.getSaldo();

}

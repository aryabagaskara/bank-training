package com.tabeldata.bootcamp;

import com.tabeldata.bootcamp.Model.Nasabah;
import com.tabeldata.bootcamp.Model.Tabungan;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    static String url = "jdbc:postgresql://127.0.0.1:5432/saya";
    static String username = "saya";
    static String password = "saya";
    static Date sqlDate = new Date(Calendar.getInstance().getTimeInMillis());

    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            List<Nasabah> nasabah = new ArrayList<Nasabah>();
            String selectQuery = null;
            PreparedStatement selectStatement = null;
            ResultSet resultSet = null;


            String query = "INSERT INTO nasabah (nasabah_name,nasabah_address) VALUES (?,?)";
            PreparedStatement statement = conn.prepareStatement(query);

            //inster data nasabah
            statement.setString(1, "Johanes");
            statement.setString(2, "Jl. Godong");
            statement.executeUpdate();


            //insert data tabungan nasabah
            query = "INSERT INTO mst_tabungan (nasabah_id, saldo, tabungan_name) values (?,?,?)";
            statement = conn.prepareStatement(query);
            statement.setInt(1, 3);
            statement.setBigDecimal(2, BigDecimal.valueOf(0));
            statement.setString(3, "Tabungan Masa Depan");
            statement.executeUpdate();
            System.out.println("sukses memasukkan data tabungan baru");

            //select data tabungan dan insert transaksi
            Integer jenisTransaksi = 0;
            selectQuery = "SELECT saldo FROM mst_tabungan where tabungan_id=?";
            switch (jenisTransaksi) {
                case 0:
                    selectStatement = conn.prepareStatement(selectQuery);
                    selectStatement.setInt(1, 1);
                    resultSet = selectStatement.executeQuery();
                    if (resultSet.next()) {
                        BigDecimal adder = null;
                        Tabungan tabungan = new Tabungan();
                        tabungan.setSaldo(resultSet.getBigDecimal(1));
                        query = "INSERT INTO trx_transaksi_tabungan (tabungan_id,transaksi_debet,tanggal_transaksi) values (?,?,?)";
                        selectQuery = "UPDATE mst_tabungan SET saldo=? WHERE tabungan_id=1";
                        statement = conn.prepareStatement(query);
                        statement.setInt(1, 1);
                        statement.setBigDecimal(2, BigDecimal.valueOf(200000));
                        statement.setDate(3, sqlDate);
                        tabungan.setSaldo(tabungan.getSaldo().add(BigDecimal.valueOf(200000)));
                        adder = tabungan.getSaldo();
                        statement.executeUpdate();
                        statement = conn.prepareStatement(selectQuery);
                        statement.setBigDecimal(1, adder);
                        statement.executeUpdate();
                    }
                    break;
                case 1:
                    selectStatement = conn.prepareStatement(selectQuery);
                    selectStatement.setInt(1, 1);
                    resultSet = selectStatement.executeQuery();
                    if (resultSet.next()) {
                        BigDecimal adder = null;
                        Tabungan tabungan = new Tabungan();
                        tabungan.setSaldo(resultSet.getBigDecimal(1));
                        query = "INSERT INTO trx_transaksi_tabungan (tabungan_id,transaksi_debet, tanggal_transaksi) values (?,?,?)";
                        selectQuery = "UPDATE mst_tabungan SET saldo=? WHERE tabungan_id=1";
                        statement = conn.prepareStatement(query);
                        statement.setInt(1, 1);
                        statement.setBigDecimal(2, BigDecimal.valueOf(100000));
                        tabungan.setSaldo(tabungan.getSaldo().subtract(BigDecimal.valueOf(100000)));
                        adder = tabungan.getSaldo();
                        statement.executeUpdate();
                        statement = conn.prepareStatement(selectQuery);
                        statement.setBigDecimal(1, adder);
                        statement.executeUpdate();
                    }
                    break;
                case 2:
                    selectStatement = conn.prepareStatement(selectQuery);
                    selectStatement.setInt(1, 1);
                    resultSet = selectStatement.executeQuery();
                    if (resultSet.next()) {
                        BigDecimal adder = null;
                        Tabungan tabungan = new Tabungan();
                        tabungan.setSaldo(resultSet.getBigDecimal(1));
                        query = "INSERT INTO trx_transaksi_tabungan (tabungan_id,transaksi_kredit,tanggal_transaksi) values (?,?,?)";
                        selectQuery = "UPDATE mst_tabungan SET saldo=? WHERE tabungan_id=1";
                        statement = conn.prepareStatement(query);
                        statement.setInt(1, 1);
                        statement.setBigDecimal(2, BigDecimal.valueOf(2500));
                        tabungan.setSaldo(tabungan.getSaldo().add(BigDecimal.valueOf(2500)));
                        adder = tabungan.getSaldo();
                        statement.executeUpdate();
                        statement = conn.prepareStatement(selectQuery);
                        statement.setBigDecimal(1, adder);
                        statement.executeUpdate();
                    }
                    break;
                default:
                    System.out.println("jenis transaksi tidak ada");


            }


            conn.commit();
            statement.close();
            selectStatement.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Tidak bisa konek ke database");
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

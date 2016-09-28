/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author Nick
 */
public class MySqlDbStrategy implements DbStrategy {

    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url,
            String userName, String password) throws ClassNotFoundException, Exception {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @Override
    public void createRecord(String tableName, List<String> colNames,
            List<Object> colVals) throws SQLException {

        String sql = "INSERT INTO " + tableName;
        StringJoiner sj = new StringJoiner(", ", " (", ")");
        StringJoiner sj2 = new StringJoiner("', '", " ('", "')");

        for (String colName : colNames) {
            sj.add(colName);
        }
        sql += sj.toString() + " VALUES ";
        List<String> vals = new LinkedList<>();
        for (Object obj : colVals) {
            vals.add(obj.toString());
        }
        for (String colVal : vals) {
            sj2.add(colVal);
        }
        sql += sj2.toString();

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
    }
    
    @Override
    public void updateRecord(String tableName, List<String> colNames,
            List<Object> colVals, String primaryKeyName,
            Object primaryKey) throws SQLException {

        String sql = "UPDATE " + tableName + " SET ";
        List<String> vals = new ArrayList<>();
        for (Object obj : colVals) {
            vals.add(obj.toString());
        }
        //StringJoiner sj = new StringJoiner(", ", " (", ")");
        //StringJoiner sj2 = new StringJoiner("', '", " ('", "')");

        for (String colName : colNames) {
            int i = 0;
            sql += colName + "='"+ vals.get(i) +"', ";
        }

        sql = sql.replaceAll(", $", ""); //strip last comma
        sql += " WHERE ? = '?'";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKeyName);
        stmt.setObject(2, primaryKey);
        stmt.executeUpdate();
    }

    @Override
    public Map<String, Object> findRecordByPk(String tableName, String pkName,
            Object primaryKey) throws SQLException {

        String sql = "SELECT * FROM " + tableName + " WHERE " + pkName + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKey);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String, Object> record = new LinkedHashMap<>();
        while (rs.next()) {        
            for (int i = 0; i < colCount; i++) {
                String colName = rsmd.getColumnName(i + 1);
                Object colData = rs.getObject(colName);
                record.put(colName, colData);
            }
        }
        return record;
    }

    @Override
    public List<Map<String, Object>> findAllRecords(String tableName,
            int maxRecords) throws SQLException {

        String sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Map<String, Object>> records = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        while (rs.next()) {
            Map<String, Object> record = new LinkedHashMap<>();
            for (int i = 0; i < colCount; i++) {
                String colName = rsmd.getColumnName(i + 1);
                Object colData = rs.getObject(colName);
                record.put(colName, colData);
            }
            records.add(record);
        }

        return records;
    }

    @Override
    public void deleteRecord(String tableName, String primaryKeyName,
            Object primaryKey) throws SQLException {

        PreparedStatement stmt = buildDeleteStatement(tableName, primaryKeyName, primaryKey);
        stmt.executeUpdate();

    }

    private PreparedStatement buildDeleteStatement(String tableName,
            String primaryKeyName, Object primaryKey) throws SQLException {

        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyName + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKey);
        return stmt;
    }

    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book?useSSL=false", "root", "admin");

        //List<Map<String, Object>> records = db.findAllRecords("author", 100);
        Map<String, Object> rec = db.findRecordByPk("author", "author_id", "5");
        System.out.println(rec.toString());
        db.closeConnection();
    }
}

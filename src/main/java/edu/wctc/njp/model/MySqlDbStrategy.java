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
import java.util.Iterator;
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
    public void openConnection(String driverClassName, String url, String username, String password)
            throws IllegalArgumentException, ClassNotFoundException, SQLException {
        String msg = "Error: url is null or zero length";
        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException(msg);
        }
        username = (username == null) ? "" : username;
        password = (password == null) ? "" : password;
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @Override
    public void createRecord(String tableName, List<String> colNames,
            List<Object> colVals) throws SQLException {

        PreparedStatement stmt = buildCreateStatement(tableName, colNames);

        final Iterator i = colVals.iterator();
        int index = 1;

        while (i.hasNext()) {
            stmt.setObject(index++, i.next());
        }

        stmt.executeUpdate();
    }

    private PreparedStatement buildCreateStatement(String tableName, List colNames)
            throws SQLException {

        String sql = "INSERT INTO " + tableName + " (";

        final Iterator i = colNames.iterator();
        while (i.hasNext()) {
            sql += (String) i.next() + ", ";
        }
        sql = sql.replaceAll(", $", "");
        sql += ") VALUES (";

        for( int j = 0; j < colNames.size(); j++ ) {
            sql += " ?, ";
        }
        sql = sql.replaceAll(", $", "");
        sql += " )";
        PreparedStatement stmt = conn.prepareStatement(sql);
        System.out.println(stmt);
        return stmt;
    }

    @Override
    public void updateRecord(String tableName, List<String> colNames,
            List<Object> colVals, String whereField,
            Object primaryKey) throws SQLException {

 
        int index = 1;
        PreparedStatement stmt = buildUpdateStatement(tableName, colNames, whereField);
        System.out.println(stmt);
//        List<String> vals = new ArrayList<>();
//        for (Object obj : colVals) {
//            vals.add(obj.toString());
//        }

        for (Object colVal : colVals) {
            stmt.setObject(index++, colVal);
        }

        if (primaryKey != null) {
            stmt.setObject(index, primaryKey);
        }

        System.out.println(stmt);
        stmt.executeUpdate();
    }

    private PreparedStatement buildUpdateStatement(String tableName, List colNames,
            String whereField) throws SQLException {

        String sql = "UPDATE " + tableName + " SET ";

        final Iterator i = colNames.iterator();
        while (i.hasNext()) {
            sql += (String) i.next() + " = ?, ";
        }
        sql = sql.replaceAll(", $", "");
        sql += " WHERE " + whereField + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        return stmt;
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

        PreparedStatement stmt = buildDeleteStatement(tableName, primaryKeyName);
        stmt.setObject(1, primaryKey);
        stmt.executeUpdate();

    }

    private PreparedStatement buildDeleteStatement(String tableName,
            String whereField) throws SQLException {

        String sql = "DELETE FROM " + tableName;
        if (whereField != null) {
            sql += " WHERE ";
            sql += whereField + " = ?";
        }
        PreparedStatement stmt = conn.prepareStatement(sql);

        return stmt;
    }

    
    
    
    
    //
    //
    //
    //
    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book?useSSL=false", "root", "admin");

        //List<Map<String, Object>> records = db.findAllRecords("author", 100);
        List<String> s = new ArrayList<>();
        List<Object> o = new ArrayList<>();
        o.add("update");
        s.add("author_name");
//
        //Map<String, Object> rec = db.findRecordByPk("author", "author_id", "5");
        db.updateRecord("author", s, o, "author_id", 5);
//        System.out.println(rec.toString());

        db.closeConnection();
    }
}

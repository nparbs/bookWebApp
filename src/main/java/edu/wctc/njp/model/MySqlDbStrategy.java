/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.model;

import java.io.Serializable;
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
import javax.sql.DataSource;

/**
 *
 * @author Nick
 */
public class MySqlDbStrategy implements DbStrategy, Serializable {

    private static final long serialVersionUID = 6987962562913661056L;

    private Connection conn;

    private String UrlErrorMsg = "Error: url is null or zero length";

    /**
     * Opens the SQL database connection.
     *
     * @param driverClassName Driver class name for connection. 
     *                        Must not be null.
     * @param url URL used for connection. Must not be null.
     * @param username Username used for connection. Must not be null.
     * @param password Password used for connection. Must not be null.
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public final void openConnection(String driverClassName, String url, String username, String password)
            throws IllegalArgumentException, ClassNotFoundException, SQLException {

        if (url == null || url.length() == 0) {
            throw new IllegalArgumentException(UrlErrorMsg);
        }
        username = (username == null) ? "" : username;
        password = (password == null) ? "" : password;
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, password);
    }

    @Override
    public final void openConnection(DataSource ds) throws SQLException {
        try {
            conn = ds.getConnection();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Closes the SQL database connection.
     *
     * @throws SQLException
     */
    @Override
    public final void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * Creates a database record.
     *
     * @param tableName The database table where the record is being created.
     * @param colNames The tables column names used for creating the record.
     * @param colVals The values for each of the tables columns.
     * @throws SQLException
     */
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

        for (Object c : colNames) {
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
        //System.out.println(stmt);

        for (Object colVal : colVals) {
            stmt.setObject(index++, colVal);
        }

        if (primaryKey != null) {
            stmt.setObject(index, primaryKey);
        }

        //System.out.println(stmt);
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

    /**
     *
     * @param tableName
     * @param pkName
     * @param primaryKey
     * @return
     * @throws SQLException
     */
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

    /**
     * Finds all records and returns a list.
     *
     * @param tableName Name of the desired table
     * @param maxRecords Max limit of records returned.
     * @return List of records.
     * @throws SQLException
     */
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

    /**
     *
     * @param tableName
     * @param primaryKeyName
     * @param primaryKey
     * @throws SQLException
     */
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

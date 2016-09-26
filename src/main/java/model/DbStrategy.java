/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nick
 */
public interface DbStrategy {

    void closeConnection() throws SQLException;

    List<Map<String,Object>> findAllRecords(String tableName, int maxRecords) throws SQLException;
    
    void createRecord(String tableName, List<String> colNames, List<Object> colVals) throws SQLException;
    
    void deleteRecord(String tableName, String primaryKeyName, int primaryKey) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, Exception;
    
    
}

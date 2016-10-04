/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author Nick
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable {
    
    @Inject
    private DbStrategy db;
    
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    public AuthorDao() {
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public void initDao(String driverClass, String url, String userName, String password){
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }

    @Override
    public void updateAuthor(String id, String name) throws Exception {
        db.openConnection(driverClass, url, userName, password);

        List<String> colNames = new ArrayList<>();
        //colNames.add("author_id");
        colNames.add("author_name");
        //colNames.add("date_added");
        
        List<Object> colVals = new ArrayList<>();
        colVals.add(name);
        
        db.updateRecord("author", colNames, colVals, "author_id", id);
        db.closeConnection();

    }

    @Override
    public void createAuthor(String name) throws ClassNotFoundException, SQLException, Exception {
        db.openConnection(driverClass, url, userName, password);

        List<String> colNames = new ArrayList<>();
        //colNames.add("author_id");
        colNames.add("author_name");
        colNames.add("date_added");
        List<Object> colVals = new ArrayList<>();
        //colVals.add(null);
        colVals.add(name);
        Date d = new Date();
        java.sql.Date sqlDate = new java.sql.Date(d.getTime());
        colVals.add(sqlDate);

        db.createRecord("author", colNames, colVals);
        db.closeConnection();
    }

    @Override
    public void deleteAuthorById(String primaryKey) throws Exception {
        Integer id = Integer.parseInt(primaryKey);
        db.openConnection(driverClass, url, userName, password);
        db.deleteRecord("author", "author_id", id);
        db.closeConnection();

    }

    @Override
    public Author findAuthorByPk(String primaryKey) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        Map<String, Object> rec = db.findRecordByPk("author", "author_id", primaryKey);
        Author author = new Author();
        int id = Integer.parseInt(rec.get("author_id").toString());
        author.setAuthorId(id);
        String name = rec.get("author_name").toString();
        author.setAuthorName(name != null ? name : "");
        Date date = (Date) rec.get("date_added");
        author.setDateAdded(date);

        db.closeConnection();

        return author;
    }

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException, Exception {

        db.openConnection(driverClass, url, userName, password);

        List<Map<String, Object>> records = db.findAllRecords("author", 500);
        List<Author> authors = new ArrayList<>();
        for (Map<String, Object> rec : records) {
            Author author = new Author();
            int id = Integer.parseInt(rec.get("author_id").toString());
            author.setAuthorId(id);
            String name = rec.get("author_name").toString();
            author.setAuthorName(name != null ? name : "");
            Date date = (Date) rec.get("date_added");
            author.setDateAdded(date);
            authors.add(author);
        }
        db.closeConnection();
        return authors;
    }

    public final DbStrategy getDb() {
        return db;
    }

    public final void setDb(DbStrategy db) {
        this.db = db;
    }

//    public static void main(String[] args) throws Exception {
//        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book?useSSL=false", "root", "admin");
//        //dao.createAuthor("jeff");
//        //List<Author> authors = dao.getAuthorList();
//        //Author a = dao.findAuthorByPk("10");
//        //system.out.println(a);
//        
//        dao.updateAuthor("25", "jaz aids");
//    }
}

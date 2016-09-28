/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.model;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nick
 */
public class AuthorService {
    private AuthorDaoStrategy dao;

    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }

    public final List<Author> getAuthorList() throws SQLException, Exception{   
        return dao.getAuthorList();
    }
    
    public final void deleteAuthor(String id) throws SQLException, Exception{
        dao.deleteAuthorById(id);
    }
    
    public final void createAuthor(String name) throws SQLException, Exception{
        dao.createAuthor(name);
    }
    
    public final void updateAuthor(String id, String name) throws Exception {
        dao.updateAuthor(id, name);
    }
    
    public final Author findAuthorById(String id) throws Exception{
        return dao.findAuthorByPk(id);
    }
    
    public static void main(String[] args) throws Exception {
        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", 
                "jdbc:mysql://localhost:3306/book?useSSL=false", "root", "admin");
        
        AuthorService service = new AuthorService(dao);
        dao.createAuthor("jeff");
        List<Author> authors = service.getAuthorList();
        System.out.println(authors);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nick
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException, Exception;
    
    void deleteAuthorById(String primaryKey) throws ClassNotFoundException, SQLException, Exception;
    
    void createAuthor(String name) throws ClassNotFoundException, SQLException, Exception;
    
    void updateAuthor(String id, String name) throws Exception;
    
    Author findAuthorByPk(String primaryKey) throws Exception;
    
    
    
}

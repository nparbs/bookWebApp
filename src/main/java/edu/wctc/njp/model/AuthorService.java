/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Nick
 */


@SessionScoped
public class AuthorService implements Serializable{
    
    @Inject
    private AuthorDaoStrategy dao;

    /**
     * Default Constructor
     */
    public AuthorService() {
    }

    public List<Author> getAuthorList() throws SQLException, Exception{   
        return dao.getAuthorList();
    }
    
    public void deleteAuthor(String id) throws SQLException, Exception{
        dao.deleteAuthorById(id);
    }
    
    public void createAuthor(String name) throws SQLException, Exception{
        dao.createAuthor(name);
    }
    
    public void updateAuthor(String id, String name) throws Exception {
        dao.updateAuthor(id, name);
    }
    
    public Author findAuthorById(String id) throws Exception{
        return dao.findAuthorByPk(id);
    }

    public AuthorDaoStrategy getDao() {
        return dao;
    }
    //
    //
    //



    
}

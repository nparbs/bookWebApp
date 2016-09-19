/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nick
 */
public class AuthorService {
    
    private List<Author> authorList;
    
    public AuthorService() {
    }
    
    public final List<Author> getAuthorList() {
        
        authorList = new ArrayList<>();
        
        Author a1 = new Author(001);
        a1.setAuthorName("Antonio Goncalves");
        a1.setDateAdded(Date.valueOf(LocalDate.now()));
        authorList.add(a1);
        
        Author a2 = new Author(002);
        a2.setAuthorName("John Adams");
        a2.setDateAdded(Date.valueOf(LocalDate.now()));
        authorList.add(a2);
        
        Author a3 = new Author(003);
        a3.setAuthorName("Joe Smith");
        a3.setDateAdded(Date.valueOf(LocalDate.now()));
        authorList.add(a3);
        
        return authorList;
    }
    
    

    @Override
    public String toString() {
        return "AuthorService{" + "authorList=" + authorList + '}';
    }
    
    
}

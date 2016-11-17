/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.ejb;

import edu.wctc.njp.model.Author;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nick
 */
@Stateless
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "book_pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }
    
    public List<Author> findById(String id) {
        List<Author> authorList = null;

        TypedQuery<Author> q = getEntityManager().createNamedQuery("Author.findByAuthorId", Author.class);

        q.setParameter("authorId", new Integer(id));

        authorList = q.getResultList();

        return authorList;
    }

    public List<Author> findByName(String name) {
        List<Author> authorList = null;

        //String jpql = "select a from Author where a.authorName = ?1";
        TypedQuery<Author> q = getEntityManager().createNamedQuery("Author.findByAuthorName", Author.class);

        q.setParameter("authorName", name);

        authorList = q.getResultList();

        return authorList;
    }

    public List<Author> findByDate(String date) {
        List<Author> authorList = null;

        //String jpql = "select a from Author where a.dateAdded = ?1";
        TypedQuery<Author> q = getEntityManager().createNamedQuery("Author.findByDateAdded", Author.class);

        q.setParameter("dateAdded", date);

        authorList = q.getResultList();

        return authorList;
    }

    public int deleteById(String id) {
        //String jpql = "select a from Author where a.authorId = ?1";

        TypedQuery<Author> q = getEntityManager().createNamedQuery("Author.deleteAuthorById", Author.class);

        q.setParameter("authorId", Integer.parseInt(id));

        int recordsDeleted = q.executeUpdate();

        return recordsDeleted;
    }

    public void updateAuthorName(String id, String name) {

        TypedQuery<Author> q = getEntityManager().createNamedQuery("Author.updateAuthorName", Author.class);

        q.setParameter("authorName", name);
        q.setParameter("authorId", Integer.parseInt(id));

        q.executeUpdate();

    }
    
    //public List<Author> getFullAuthorList(){}
}

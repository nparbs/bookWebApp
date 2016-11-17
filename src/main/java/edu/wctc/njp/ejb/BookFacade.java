/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.ejb;

//import edu.wctc.njp.model.Author;
import edu.wctc.njp.model.Book;
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
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "book_pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

    public List<Book> findById(String id) {

        TypedQuery<Book> q = getEntityManager().createNamedQuery("Book.findByBookId", Book.class);

        q.setParameter("bookId", Integer.parseInt(id));

        return q.getResultList();

    }

    public List<Book> findByTitle(String title) {
        //String jpql = "select a from Book where a.authorName = ?1";
        TypedQuery<Book> q = getEntityManager().createNamedQuery("Book.findByTitle", Book.class);

        q.setParameter("title", title);

        return q.getResultList();

    }

    public List<Book> findByISBN(String isbn) {
        //String jpql = "select a from Book where a.dateAdded = ?1";
        TypedQuery<Book> q = getEntityManager().createNamedQuery("Book.findByIsbn", Book.class);

        q.setParameter("isbn", isbn);

        return q.getResultList();
    }

    public int deleteById(String id) {
        //String jpql = "select a from Book where a.authorId = ?1";

        TypedQuery<Book> q = getEntityManager().createNamedQuery("Book.deleteBookById", Book.class);

        q.setParameter("bookId", Integer.parseInt(id));

        int recordsDeleted = q.executeUpdate();

        return recordsDeleted;
    }

    public void updateBookTitle(String id, String title) {

        TypedQuery<Book> q = getEntityManager().createNamedQuery("Book.updateBookTitle", Book.class);

        q.setParameter("title", title);
        q.setParameter("bookId", Integer.parseInt(id));

        q.executeUpdate();

    }

}

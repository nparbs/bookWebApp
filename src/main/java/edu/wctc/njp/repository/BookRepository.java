package edu.wctc.njp.repository;


import edu.wctc.njp.model.Book;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jlombardo
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
    @Query("SELECT b FROM Book b WHERE b.authorId.authorId = :authorId")
    public List <Book> findByAuthorId(@Param("authorId") String authorId);
    
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")//LIKE CONCAT('%', :title, '%')")
    public List <Book> findByLikeTitle(@Param("title") String title);
    
    @Query("SELECT b FROM Book b WHERE b.authorId.authorName LIKE %:authorName%")//LIKE CONCAT('%', :authorName, '%')")
    public List <Book> findByLikeAuthorName(@Param("authorName") String authorName);
    
    @Query("SELECT b FROM Book b WHERE b.authorId.authorName LIKE %:authorName% AND b.title LIKE %:title%")//LIKE CONCAT('%', :authorName, '%')")
    public List <Book> findByLikeAuthorNameAndLikeTitle(@Param("authorName") String authorName, @Param("title") String title);
    
}

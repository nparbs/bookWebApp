package edu.wctc.njp.repository;


import edu.wctc.njp.model.Author;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author jlombardo
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
    //@Query("SELECT a FROM Author a JOIN FETCH a.bookSet WHERE a.authorId = (:id)")
    //public Author findByIdAndFetchBooksEagerly(@Param("id") Integer id);
//    
    @Query("SELECT a.authorName FROM Author a")
    public Object[] findAllWithNameOnly();
    
    @Query("SELECT a FROM Author a WHERE a.authorName = :name")
    public Author findByName(@Param("name") String name);
    
    //@Query("SELECT a FROM Author a WHERE a.authorName LIKE CONCAT('%', :name, '%')")
    @Query("SELECT a FROM Author a WHERE a.authorName LIKE %:name%")
    public List<Author> findByLikeName(@Param("name") String name);
    
    @Query("SELECT a FROM Author a WHERE a.dateAdded BETWEEN :startDate AND :endDate")
    public List<Author> findBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    
}

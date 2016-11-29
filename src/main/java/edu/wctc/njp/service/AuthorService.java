package edu.wctc.njp.service;

import edu.wctc.njp.model.Author;
import edu.wctc.njp.repository.AuthorRepository;
import edu.wctc.njp.repository.BookRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to various
 * Spring managed repository objects using special spring annotations to perform
 * dependency injection, and special annotations for transactions. It also uses
 * SLF4j to provide logging features.
 *
 * Don't confuse the Spring @Respository annotation with the repository classes
 * (AuthorRepository, BookRespository). The annotation here is used solely to
 * tell Spring to translate any exception messages into more user friendly text.
 * Any class annotated that way will do that.
 *
 */
@Repository
@Transactional(readOnly = true)
public class AuthorService {

    private transient final Logger LOG = LoggerFactory.getLogger(AuthorService.class);

    @Inject
    private AuthorRepository authorRepo;

    @Inject
    private BookRepository bookRepo;

    public AuthorService() {
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }

    /**
     * This custom method is necessary because we are using Hibernate which does
     * not load lazy relationships (in this case Books).
     *
     * @param id
     * @return
     
    public Author findByIdAndFetchBooksEagerly(String id) { //NEED
        Integer authorId = new Integer(id);

        //You could do this to eagerly load the books, but it's slow
        //return authorRepo.findByIdAndFetchBooksEagerly(authorId);
        // Instead do this, it's faster
        Author author = authorRepo.findOne(authorId);
        author.getBookCollection().size();
        return author;
    }*/

    public Author findById(String id) {
        return authorRepo.findOne(new Integer(id));
    }
        public Author findByName(String name) {
        return authorRepo.findByName(name);
    }
    
    public List<Author> findByLikeName(String name) {
        return authorRepo.findByLikeName(name);
    }
    
    public List<Author> findBetweenDates(String startDate, String endDate) {
        return authorRepo.findBetweenDates(startDate, endDate);
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     *
     * @param author
     */
    @Transactional
    public void remove(Author author) {
        LOG.debug("Deleting author: " + author.getAuthorName());
        authorRepo.delete(author);
    }

    /**
     * Spring performs a transaction with readonly=false. This guarantees a
     * rollback if something goes wrong.
     *
     * @param author
     */
    @Transactional
    public Author edit(Author author) {
        return authorRepo.saveAndFlush(author);
        //return authorRepo.
    }
    
 //   public Author 
}
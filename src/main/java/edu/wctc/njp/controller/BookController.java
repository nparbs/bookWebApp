/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.controller;

import edu.wctc.njp.ejb.AuthorFacade;
import edu.wctc.njp.ejb.BookFacade;
import edu.wctc.njp.model.Author;
import edu.wctc.njp.model.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nick
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    private static final String VIEW_ALL_AUTHORS = "viewAuthors.jsp";
    private static final String VIEW_BOOKS = "viewBooks.jsp";
    private static final String EDIT_PAGE = "editBook.jsp";

    private static final String TASK = "task";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String ISBN = "isbn";
    private static final String AUTHOR_ID = "authorId";
    private static final String AUTHOR_NAME = "authorName";
    private static final String BOOK = "book";
    private static final String BOOK_LIST = "bookList";

    private static final String CREATE = "Create";
    private static final String VIEW = "View";
    private static final String FIND = "Find";
    private static final String EDIT = "Edit";
    private static final String DELETE = "Delete";

    @Inject
    private AuthorFacade authSvc;
    @Inject
    private BookFacade bookSvc;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sess = request.getSession();

        ServletContext ctx = request.getServletContext();

        String task = request.getParameter(TASK);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

        synchronized (ctx) {
            ctx.setAttribute("time", dateFormat.format(Calendar.getInstance().getTime()));
        }

        try {

            if (task == null) {
                task = VIEW;
            }

            switch (task) {
                case CREATE: {
                    try {

                        String title = request.getParameter(TITLE);
                        String isbn = request.getParameter(ISBN);
                        String authorName = request.getParameter(AUTHOR_NAME);

                        if (title != null && isbn != null && authorName != null) {

                            //Author a = authSvc.find(new Integer(authorId));
                            Author auth = null;

                            if (authSvc.findByName(authorName).isEmpty()) {

//                                Book b = new Book();
//                                b.setTitle("it worked");
//                                b.setIsbn(isbn);
//                                b.setAuthorId(auth);
//                                bookSvc.create(b);
                                Author a = new Author();

                                a.setAuthorName(authorName);
                                a.setDateAdded(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                                authSvc.create(a);

                            } else {
                                //auth = authSvc.findByName(authorName).get(0);

                            }

                            auth = authSvc.findByName(authorName).get(0);

                            Book b = new Book();

                            b.setTitle(title);
                            b.setIsbn(isbn);
                            b.setAuthorId(auth);

                            bookSvc.create(b);

                            //Set<Book> bookSet = (Set<Book>) auth.getBookCollection();
                            //bookSet.add(b);
                            //auth.setBookCollection(bookSet);
                        }

                        request.setAttribute(BOOK_LIST, bookSvc.findAll());

                        RequestDispatcher view = request.getRequestDispatcher(VIEW_BOOKS);
                        view.forward(request, response);
                    } catch (Exception e) {
                    }
                    break;
                }

                case EDIT: {
                    try {
                        String id = request.getParameter(ID);
                        String title = request.getParameter(TITLE);
                        String isbn = request.getParameter(ISBN);
                        String authorId = request.getParameter(AUTHOR_ID);
                        //String authorName = request.getParameter(AUTHOR_NAME);

                        Book book = bookSvc.find(new Integer(id));

                        request.setAttribute(BOOK, book);

                        if (title != null && isbn != null && authorId != null) {
                            Author author = authSvc.find(new Integer(authorId));

                            book.setTitle(title);
                            book.setIsbn(isbn);
                            book.setAuthorId(author);

                            bookSvc.edit(book);

                            //TEST
                            //Set<Book> bookSet = (Set<Book>) author.getBookCollection();
                            //bookSet.add(book);
                            //author.setBookCollection(bookSet);

                            RequestDispatcher view = request.getRequestDispatcher(VIEW_BOOKS);
                            view.forward(request, response);
                            //} else if () {

                        } else {
                            request.setAttribute("editError", "Title, ISBN, and Author must be at least 1 character");
                            RequestDispatcher view = request.getRequestDispatcher(EDIT_PAGE);
                            view.forward(request, response);
                        }
                    } catch (Exception e) {
                    }

                    break;
                }
                case DELETE: {
                    try {
                        String id = request.getParameter(ID);

                        bookSvc.deleteById(id);

                        request.setAttribute(BOOK_LIST, bookSvc.findAll());
                        RequestDispatcher view = request.getRequestDispatcher(VIEW_BOOKS);
                        view.forward(request, response);
                    } catch (Exception e) {
                    }
                    break;
                }
                case FIND: {
                    String id = null;
                    try {

                        id = request.getParameter(ID);
                        List<Book> bookList = new ArrayList();
                        bookList.add(bookSvc.find(new Integer(id)));
                        request.setAttribute(BOOK_LIST, bookList);

                        RequestDispatcher view = request.getRequestDispatcher(VIEW_BOOKS);
                        view.forward(request, response);
                    } catch (Exception e) {
                        if (id != null) {
                            request.setAttribute("failed", e);
                            RequestDispatcher view = request.getRequestDispatcher(VIEW_BOOKS);
                            view.forward(request, response);
                        }
                    }
                    break;
                }
                case VIEW: {
                    try {
                        List<Book> bookList = bookSvc.findAll();
                        request.setAttribute(BOOK_LIST, bookList);
                        RequestDispatcher view = request.getRequestDispatcher(VIEW_BOOKS);
                        view.forward(request, response);
                    } catch (Exception e) {
                    }
                    break;
                }
                default:
                    RequestDispatcher view = request.getRequestDispatcher(VIEW_BOOKS);
                    view.forward(request, response);
                    break;
            }
        } catch (Exception e) {
            //request.setAttribute("errorMsg", e.getCause().getMessage());
            //sess.setAttribute("errorMsg", e.getCause().getMessage());
        }

        //response.sendRedirect(response.encodeURL(dest));
        //RequestDispatcher view = request.getServletContext().getRequestDispatcher(response.encodeURL(dest));
        //RequestDispatcher view = request.getRequestDispatcher(response.encodeURL(dest));
        //view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

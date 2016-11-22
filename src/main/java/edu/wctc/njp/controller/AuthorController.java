/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.njp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.wctc.njp.model.Author;
import edu.wctc.njp.service.AuthorService;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The controller for all author methods.
 *
 * @author Nick
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String VIEW_ALL_PAGE = "viewAuthors.jsp";
    private static final String EDIT_PAGE = "editAuthor.jsp";
    private static final String FIND_PAGE = "findAuthor.jsp";

    private static final String TASK = "task";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String AUTHOR_LIST = "authorList";

    private static final String CREATE = "Create";
    private static final String VIEW = "View";
    private static final String FIND = "Find";
    private static final String EDIT = "Edit";
    private static final String DELETE = "Delete";

    private String email;

    
    private AuthorService authSvc;

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sess = request.getSession();

        ServletContext ctx = request.getServletContext();

        String task = request.getParameter(TASK);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

        synchronized (ctx) {
            ctx.setAttribute("time", dateFormat.format(Calendar.getInstance().getTime()));
            ctx.setAttribute("webmaster", email);
        }

        try {

            if (task == null) {
                task = VIEW;
            }
            //sess.removeAttribute(AUTHOR);
            //sess.removeAttribute(AUTHOR_LIST);

            switch (task) {
                case CREATE: {
                    try {
                        String name = request.getParameter(NAME);

                        Author a = new Author();
                        a.setAuthorName(name);
                        a.setDateAdded(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

                        authSvc.edit(a);

                        RequestDispatcher view = request.getRequestDispatcher(VIEW_ALL_PAGE);
                        view.forward(request, response);
                    } catch (Exception e) {
                    }
                    break;
                }
                case EDIT: {
                    try {
                        String id = request.getParameter(ID);
                        String name = request.getParameter(NAME);

                        Author author = authSvc.findById(id);
                        request.setAttribute(AUTHOR, author);
                        if (name != null) {
                            //authSvc.updateAuthorName(id, name);
                            author.setAuthorName(name);
                            authSvc.edit(author);
                            RequestDispatcher view = request.getRequestDispatcher(VIEW_ALL_PAGE);
                            view.forward(request, response);
                        } else if (name == null) {
                            //sess.setAttribute("editError", "Name must be at least 1 char");
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

                        authSvc.remove(authSvc.findById(id));

                        List<Author> authorList = authSvc.findAll();
                        request.setAttribute(AUTHOR_LIST, authorList);
                        RequestDispatcher view = request.getRequestDispatcher(VIEW_ALL_PAGE);
                        view.forward(request, response);
                    } catch (Exception e) {
                    }
                    break;
                }
                case FIND: {
                    String id = null;
                    try {

                        id = request.getParameter(ID);
                        Author author = authSvc.findById(id);
                        request.setAttribute(AUTHOR, author);

                        RequestDispatcher view = request.getRequestDispatcher(FIND_PAGE);
                        view.forward(request, response);
                    } catch (Exception e) {
                        if (id != null) {
                            request.setAttribute("failed", e);
                        }
                    }
                    break;
                }
                case VIEW: {
                    try {
                        List<Author> authorList = authSvc.findAll();
                        request.setAttribute(AUTHOR_LIST, authorList);
                        RequestDispatcher view = request.getRequestDispatcher(VIEW_ALL_PAGE);
                        view.forward(request, response);
                    } catch (Exception e) {
                    }
                    break;
                }
                default:
                    RequestDispatcher view = request.getRequestDispatcher(VIEW_ALL_PAGE);
                    view.forward(request, response);
                    //response.sendRedirect(dest);
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
        try {
            processRequest(request, response);

        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);

        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initiates the database based on parameters set in the ServletContext
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
                ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        authSvc = (AuthorService) ctx.getBean("authorService");

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

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
import edu.wctc.njp.model.AuthorDao;
import edu.wctc.njp.model.AuthorDaoStrategy;
import edu.wctc.njp.model.AuthorService;
import edu.wctc.njp.model.DbStrategy;
import edu.wctc.njp.model.MySqlDbStrategy;
import java.util.ArrayList;
import javax.inject.Inject;

/**
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

    private String driverClass;
    private String url;
    private String userName;
    private String password;

    @Inject
    private AuthorService authSvc;

    private void configDbConnection() {
        authSvc.getDao().initDao(driverClass, url, userName, password);
    }

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

        String task = request.getParameter(TASK);

        String dest = VIEW_ALL_PAGE;

        try {
            configDbConnection();
            //add enum
            switch (task) {
                case CREATE: {
                    String name = request.getParameter(NAME);
                    authSvc.createAuthor(name);
                    dest = VIEW_ALL_PAGE;
                    break;
                }
                case EDIT: {
                    String id = request.getParameter(ID);
                    String name = request.getParameter(NAME);
                    try {
                        if (name != null) {

                            authSvc.updateAuthor(id, name);

                            break;
                        }
                        Author author = authSvc.findAuthorById(id);
                        request.setAttribute(AUTHOR, author);
                    } catch (Exception e) {
                    }
                    if (name == null) {
                        dest = EDIT_PAGE;
                    }
                    break;
                }
                case DELETE: {
                    String id = request.getParameter(ID);
                    authSvc.deleteAuthor(id);
                    break;
                }
                case FIND: {
                    try {
                        String id = request.getParameter(ID);
                        Author author = authSvc.findAuthorById(id);
                        request.setAttribute(AUTHOR, author);
                    } catch (Exception e) {
                        request.setAttribute("failed", e);
                    }
                    dest = FIND_PAGE;
                    break;
                }
                case VIEW: {
                    List<Author> authorList = authSvc.getAuthorList();
                    request.setAttribute(AUTHOR_LIST, authorList);
                }
                default:
                    dest = VIEW_ALL_PAGE;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errorMsg", e.getCause().getMessage());
        }

        RequestDispatcher view = request.getRequestDispatcher(dest);

        view.forward(request, response);
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        driverClass = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/book?useSSL=false";
        userName = "root";
        password = "admin";
    }

}

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

    private DbStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
    @Inject
    private AuthorService authSvc;
    
    private void configDbConnection() {
        authSvc.getDao().initDao(driverClass,url,userName,password);
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



        String task = request.getParameter("task");

        String dest = VIEW_ALL_PAGE;

        try {
            configDbConnection();
            //add enum
            switch (task) {
                case "Create": {
                    String name = request.getParameter("name");
                    authSvc.createAuthor(name);
                    dest = VIEW_ALL_PAGE;
                    break;
                }
                case "Edit": {
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    try {
                        if (name != null) {

                            authSvc.updateAuthor(id, name);

                            break;
                        }
                        Author author = authSvc.findAuthorById(id);
                        request.setAttribute("author", author);
                    } catch (Exception e) {
                    }
                    if (name == null) {
                        dest = EDIT_PAGE;
                    }
                    break;
                }
                case "Delete": {
                    String id = request.getParameter("id");
                    authSvc.deleteAuthor(id);
                    break;
                }
                case "Find": {
                    try {
                        String id = request.getParameter("id");
                        Author author = authSvc.findAuthorById(id);
                        request.setAttribute("author", author);
                    } catch (Exception e) {
                        request.setAttribute("failed", e);
                    }
                    dest = FIND_PAGE;
                    break;
                }
                case "View": {
                    List<Author> authorList = authSvc.getAuthorList();
                    request.setAttribute("authorList", authorList);
                }
                default:
                    dest = VIEW_ALL_PAGE;
                    break;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
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

    @Override
    public void init() throws ServletException {
        driverClass = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/book?useSSL=false";
        userName = "root";
        password = "admin";
    }

}

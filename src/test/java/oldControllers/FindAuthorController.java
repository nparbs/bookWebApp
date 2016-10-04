/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oldControllers;

import edu.wctc.njp.model.Author;
import edu.wctc.njp.model.AuthorDao;
import edu.wctc.njp.model.AuthorDaoStrategy;
import edu.wctc.njp.model.AuthorService;
import edu.wctc.njp.model.MySqlDbStrategy;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nick
 */
@WebServlet(name = "FindAuthorController", urlPatterns = {"/FindAuthor"})
public class FindAuthorController extends HttpServlet {

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

        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book?useSSL=false", "root", "admin");

        AuthorService svc = new AuthorService(dao);
        
        

        try {
            String id = request.getParameter("id");
            Author author = svc.findAuthorById(id);
            request.setAttribute("author", author);
        } catch (Exception e) {
            request.setAttribute("failed", e);
        }
        
        RequestDispatcher view = request.getRequestDispatcher("findAuthor.jsp");
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

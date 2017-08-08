/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.estandar;


import gob.gbc.estandar.Usuarios;
import gob.gbc.util.Utilerias;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vhernandez
 */
public class addUser extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession ses = request.getSession(true);
        Usuarios usuarioBenef = null;
        int iOpcion = 0;
        ResultSQLEstandar sql = null;
        try {
            if (ses.getAttribute("datosUsuario") == null) {
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.sendRedirect("logout.jsp?expired=1");
            }
            usuarioBenef = (Usuarios) ses.getAttribute("datosUsuario");
            /*if (Utilerias.existeParametro("usuario", request)) {
                usuarioBenef.setStrUsuario(((String) request.getParameter("usuario")).toUpperCase());
            }
            if (Utilerias.existeParametro("desc", request)) {
                usuarioBenef.setStrDescripcion((String) request.getParameter("desc"));
            }
            if (Utilerias.existeParametro("email", request)) {
                usuarioBenef.setStrEmail((String) request.getParameter("email"));
            }
            if (Utilerias.existeParametro("pass", request)) {
                usuarioBenef.setStrPassword((String) request.getParameter("pass"));
            }*/
            if (Utilerias.existeParametro("hfOpcion", request)) {
                iOpcion = Integer.parseInt(request.getParameter("hfOpcion"));
            }
            sql = new ResultSQLEstandar();
            sql.setStrServer(request.getHeader("host"));
            sql.setStrUbicacion(getServletContext().getRealPath("").toString());
            sql.resultSQLConecta("CFD");
            //out.write(sql.mantenimientoUsuario(usuarioBenef, iOpcion).toString());
        } catch (Exception ex) {
        } finally {
            if (sql != null) {
                sql.resultSQLDesconecta();
            }
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

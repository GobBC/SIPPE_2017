/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.estandar;

import gob.gbc.estandar.Usuarios;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Utilerias;
import gob.gbc.ws.client.email.Resultado;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author vhernandez
 */
public class nuevoPassword extends HttpServlet {

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
        String tipoDependencia = new String();
        HttpSession session = request.getSession();
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
        PrintWriter out = response.getWriter();
        ResultSQL sql = null;
        String strhomo = "", strMail = "";
        JSONObject json = null;
        try {
            String strClave = "";
            String strUsuario = "";
            if (Utilerias.existeParametro("hfClave", request)) {
                strClave = (String) request.getParameter("hfClave");
            }
            if (Utilerias.existeParametro("sCveUsuario", request)) {
                strUsuario = (String) request.getParameter("sCveUsuario");
                strUsuario = strUsuario.toUpperCase();
            }


            //sql = new ResultSQL();
            sql = new ResultSQL(tipoDependencia);
            sql.setStrServer(request.getHeader("host"));
            sql.setStrUbicacion(getServletContext().getRealPath("").toString());
            sql.resultSQLConecta(sql.getsConexionBD());           

            json = new JSONObject();
            //Usuarios benef = ;            
            Usuarios usuario=null;
            usuario = sql.getExisteUsuario(strUsuario);
            if (usuario != null) {
                if (!sql.actualizaPasswordEmail(strUsuario, strClave)) {
                    json.put("resultado", "-1");
                    json.put("mensaje", "El Usuario No existe");
                } else {
                    /*emision = new Emision();
                    emision.setStrServer(request.getHeader("host"));
                    emision.setStrUbicacion(getServletContext().getRealPath("").toString());
                    emision.setConectaBD(sql.getConectaBD());*/
                    Resultado resMail = null;
                    resMail = sql.envioMailPassword(strUsuario, strClave, usuario.getsCorreo());
                    if (resMail != null) {
                        if (resMail.getCodigo() == 0) {
                            json.put("resultado", "0");
                            json.put("mensaje", "El nuevo password ha sido enviado a su correo");
                            sql.getConectaBD().transaccionCommit();//revisado
                        } else {
                            json.put("resultado", "-1");
                            json.put("mensaje", resMail.getMensaje());
                            sql.getConectaBD().transaccionCommit();//revisado
                        }

                    } else {
                        json.put("resultado", "-1");
                        json.put("mensaje", "Ocurrio un error al enviar su nuevo password");
                        sql.getConectaBD().transaccionCommit();//revisado
                    }

                }
            } else {
                json.put("resultado", "-1");
                json.put("mensaje", "El Usuario no esta registrado");
            }         
        } catch (Exception e) {
            e.getMessage();
        } finally {
            out.println(json.toString());
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

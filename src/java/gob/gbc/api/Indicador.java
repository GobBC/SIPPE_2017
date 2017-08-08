/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.api;

import com.google.gson.Gson;
import gob.gbc.Framework.DataSourceResult;
import gob.gbc.entidades.GraficaMir;
import gob.gbc.entidades.UsuarioSession;
import gob.gbc.sql.ResultSQLCatalogos;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Utilerias;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author ugarcia
 */
public class Indicador extends HttpServlet {
    private Gson _gson;
    
    public Indicador(){
        super();            
        // initialize the Gson library
        _gson = new Gson();
    }

    // <editor-fold defaultstate="collapsed" desc="doGET">
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
        response.setContentType("application/json;charset=UTF-8");
        HttpSession httpsession = request.getSession(true);
        ResultSQLCatalogos rs = null;
        JSONObject strResultado = null;
        
        String tipoDependencia = (String)httpsession.getAttribute("tipoDependencia");
        String action = request.getPathInfo();
        String indicadorSEI = new String();

        try {
            rs = new ResultSQLCatalogos(tipoDependencia);                         


            rs.setStrServer(request.getHeader("host"));
            rs.setStrUbicacion(getServletContext().getRealPath("").toString());
            try {
                rs.resultSQLConecta(tipoDependencia);                    
            } catch (Exception ex) {
                Logger.getLogger(MIR.class.getName()).log(Level.SEVERE, null, ex);
            }
            UsuarioSession session = new UsuarioSession();
            session.setTipoDependencia(tipoDependencia);  
            session.setYear((String)httpsession.getAttribute("year"));
            session.setStrUsuario((String)httpsession.getAttribute("strUsuario"));     
            session.setRol( (String) httpsession.getAttribute("strRol"));
            session.setNormativo(session.getRol().equals(rs.getResultSQLGetRolesPrg()));
            String ramo = request.getParameter("ramo");
            String programa = request.getParameter("programa");            
            action = action != null ? action : new String();
            gob.gbc.entidades.Indicador indicador = new gob.gbc.entidades.Indicador();
            if(action.equals("/ObtenerFinProposito")){
                // create a new DataSourceResult to send back
                DataSourceResult result = new DataSourceResult();

                // set the total property
                result.setTotal(2);

                // set the data
                result.setData(rs.consultaFinProposito(new gob.gbc.entidades.MIR(session.getYear(),ramo, programa), session.isNormativo())); 

                // convert the DataSourceReslt to JSON and write it to the response
                response.getWriter().print(_gson.toJson(result));
            }else if(action.equals("/ConsultarIndicadores")){
                DataSourceResult result = new DataSourceResult();
                indicador.setYear(session.getYear());
                indicador.setRamo(ramo);
                indicador.setPrograma(programa);
                indicador.setDimension(Integer.parseInt(request.getParameter("dimension")));
                // set the data
                result.setData(rs.getIndicadorList(indicador)); 

                // convert the DataSourceReslt to JSON and write it to the response
                response.getWriter().print(_gson.toJson(result));
            }else if(action.equals("/GetInfomacionIndicador")){
                indicadorSEI = Utilerias.existeParametro("indicadorSEI", request) ? request.getParameter("indicadorSEI") : new String();
                if(!indicadorSEI.isEmpty()){
                    indicador = rs.getInformacionIndicador(new gob.gbc.entidades.Indicador(session.getYear(),ramo, programa, indicadorSEI)); 
                } else {
                    indicador.setIndicadores("");
                    indicador.setMedios("");
                }
                response.getWriter().print(_gson.toJson(indicador));
                // convert the DataSourceReslt to JSON and write it to the response
            }
        }catch (Exception ex) {
            rs.transaccionRollback();
            Bitacora bitacora = new Bitacora();
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(Bitacora.TIPO_ERROR);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.grabaBitacora();
            strResultado = new JSONObject()
                    .put("exito", false)
                    .put("mensaje", "Ocurrio un error, favor de intentarlo mas tarde.");
            response.getWriter().print(_gson.toJson(strResultado));
        } finally {
            if (rs != null) {
                rs.resultSQLDesconecta();
            }
        }
    }
    // </editor-fold >
    
    // <editor-fold defaultstate="collapsed" desc="doPOST">
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
        //processRequest(request, response);
        response.setContentType("application/json;charset=UTF-8");
        String action = request.getPathInfo();
        JSONObject strResultado = null;
        boolean res = false;
        String errorMsg = "";
        String accion = "guardado"; 
        String entidad = "";
        
        gob.gbc.entidades.Indicador entity = new gob.gbc.entidades.Indicador();
        
        HttpSession httpsession = request.getSession(true);
        String tipoDependencia = (String)httpsession.getAttribute("tipoDependencia");

        ResultSQLCatalogos rs = null;
        
        try {
            rs = new ResultSQLCatalogos(tipoDependencia);        
                
            rs.setStrServer(request.getHeader("host"));
            rs.setStrUbicacion(getServletContext().getRealPath("").toString());     
            rs.resultSQLConecta(tipoDependencia);   
        
            UsuarioSession session = new UsuarioSession();
            session.setTipoDependencia(tipoDependencia);  
            session.setYear((String)httpsession.getAttribute("year"));
            session.setStrUsuario((String)httpsession.getAttribute("strUsuario"));
            session.setRol((String)httpsession.getAttribute("strRol"));
            
            if(action.equals("/EditRecordsFinProposito")){                                
                entity = new Gson().fromJson(request.getParameter("options"), gob.gbc.entidades.Indicador.class);     
                if(rs.existeIndicadorFinProposito(entity))
                    res = rs.updateIndicadorFinProposito(entity);
                else
                    res = rs.insertMIRIndicadorFinProposito(entity);
                if (!res) {
                    errorMsg = rs.getStrError();
                }  else if (!rs.guardar()) {
                    errorMsg = "No se pudo Guardar el cambio. ";
                }
                accion = "editado";
            }
            strResultado = new JSONObject()
                    .put("exito", res)
                    .put("mensaje", !errorMsg.isEmpty() ? errorMsg : "Se ha " + accion + " el registro correctamente.");
        } catch (Exception ex) {
            rs.transaccionRollback();
            Bitacora bitacora = new Bitacora();
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(Bitacora.TIPO_ERROR);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.grabaBitacora();
            strResultado = new JSONObject()
                    .put("exito", false)
                    .put("mensaje", "Ocurrio un error, favor de intentarlo mas tarde.");
            response.getWriter().print(_gson.toJson(strResultado));
        } finally {
            if (rs != null) {
                rs.resultSQLDesconecta();
            }
            response.getWriter().print(_gson.toJson(strResultado));
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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

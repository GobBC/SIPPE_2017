/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.api;

import com.google.gson.Gson;
import gob.gbc.Framework.DataSourceRequest;
import gob.gbc.Framework.DataSourceResult;
import gob.gbc.entidades.UsuarioSession;
import gob.gbc.sql.ResultSQL;
import gob.gbc.sql.ResultSQLCatalogos;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Utilerias;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
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
 * @author ealonso
 */
public class Actividad extends HttpServlet{
    private static final long serialVersionUID = 1L;
	private Gson _gson;
        
        public Actividad(){
            super();
            
            // initialize the Gson library
            _gson = new Gson();
        }
        
        @Override
        public void init() throws ServletException{
            super.init();
        }
        
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set the content type we are sending back as JSON
		response.setContentType("application/json;charset=UTF-8");	
                HttpSession httpsession = request.getSession(true);
                String tipoDependencia = (String)httpsession.getAttribute("tipoDependencia");
                String action = request.getPathInfo() == null ? new String() : request.getPathInfo();
                String errorMsg = request.getPathInfo();
                ResultSQLCatalogos rs = null;
                JSONObject strResultado = null;
                
                List<String> statusAutorizados = Arrays.asList("2", "4", "5", "7");
                try {
                    rs = new ResultSQLCatalogos(tipoDependencia);                         
                } catch (SQLException ex) {
                    Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                rs.setStrServer(request.getHeader("host"));
                rs.setStrUbicacion(getServletContext().getRealPath(""));
                try {
                    rs.resultSQLConecta(tipoDependencia);                    
                } catch (Exception ex) {
                    Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		// get the take and skip parameters
                
		try {		
                    UsuarioSession session = new UsuarioSession();
                    session.setTipoDependencia(tipoDependencia);  
                    session.setYear((String)httpsession.getAttribute("year"));
                    session.setStrUsuario((String)httpsession.getAttribute("strUsuario"));
                    session.setRol((String)httpsession.getAttribute("strRol"));
                    session.setNormativo(session.getRol().equals(rs.getResultSQLGetRolesPrg()));
                    String ramo = request.getParameter("ramo");
                    String programa = request.getParameter("programa");
                    if(action.equals("/validaRequisitosBorrar")){
                        boolean isMIRValida = false;
                        if(statusAutorizados
                                .contains(rs.getEstatusMir(new gob.gbc.entidades.MIR(
                                                session.getYear(),
                                                ramo,
                                                programa)))){
                            if(session.isNormativo()){
                                 isMIRValida = rs.validaComponenteActividadMIR(session.getYear(),ramo,programa);
                                 if(isMIRValida)
                                     errorMsg = "Al borrar la actividad puede dejar inválida la MIR. ¿Desea continuar?";
                            }else{
                                errorMsg = "Sólo los usuarios normativos pueden borrar";
                            }
                        }
                        strResultado = new JSONObject()
                            .put("exito", isMIRValida)
                            .put("isNormativo", session.isNormativo())
                            .put("mensaje", !errorMsg.isEmpty() ? errorMsg : "");
                        response.getWriter().print(_gson.toJson(strResultado));
                    } else {                   
                        DataSourceRequest options = new Gson().fromJson(request.getParameter("options"), DataSourceRequest.class);	
                        int componente = Integer.parseInt(options.getMasterId());
			// create a new DataSourceResult to send back
			DataSourceResult result = new DataSourceResult();

			// set the total property
                        result.setTotal(rs.getActividadCount(session, ramo, programa, componente, options, true));			

                        if(options.getTake() == 0)
                            options.setTake(result.getTotal());

			// set the data
                       result.setData(rs.getActividadFiltros(session, ramo, programa, componente, options, false)); 			

			// convert the DataSourceReslt to JSON and write it to the response
			response.getWriter().print(_gson.toJson(result));
		}
		}
		catch (Exception e) {
			response.sendError(500);
		}
        }
        /**
     * @param request
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("application/json;charset=UTF-8");
        String action = request.getPathInfo();
        JSONObject strResultado = null;
        boolean res = false;
        String errorMsg = "";
        String accion = "guardado"; 
        Utilerias utileria = null;
        String entidad = "";

        gob.gbc.entidades.Actividad data = new gob.gbc.entidades.Actividad();
        
        HttpSession httpsession = request.getSession(true);
        String tipoDependencia = (String)httpsession.getAttribute("tipoDependencia");

        ResultSQLCatalogos rs = null;
        try {
            rs = new ResultSQLCatalogos(tipoDependencia);        
                
            rs.setStrServer(request.getHeader("host"));
            rs.setStrUbicacion(getServletContext().getRealPath("").toString());          
        } catch (SQLException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            utileria = new Utilerias();
            rs.resultSQLConecta(tipoDependencia);                    
        } catch (Exception ex) {
            Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        // get the take and skip parameters
        UsuarioSession session = new UsuarioSession();
        session.setTipoDependencia(tipoDependencia);  
        session.setYear((String)httpsession.getAttribute("year"));
        session.setStrUsuario((String)httpsession.getAttribute("strUsuario"));
        session.setRol((String)httpsession.getAttribute("strRol"));
        String ramo = request.getParameter("ramo");
        String programa = request.getParameter("programa");
        //DataSourceRequest options = new Gson().fromJson(request.getParameter("options"), DataSourceRequest.class);

        try {   
                if ("/AddRecords".equals(action)) {
                    int componente = Integer.parseInt(request.getParameter("componente"));
                    
                    String descr = request.getParameter("descr");
                    data.setStrYear(session.getYear());
                    data.setRamo(ramo);
                    data.setPrg(programa);
                    data.setComponente(componente);
                    data.setDescr(descr);
                    res = rs.insertaActividad(data);
                    if (!res) {
                        errorMsg = rs.getStrError();
                    }  else if (!rs.guardar()) {
                        errorMsg = "No se pudo Guardar el cambio. ";
                    }
                    accion = "agregado";
                }else{
                    entidad = request.getParameter("entidad");
                    data = new Gson().fromJson(entidad, gob.gbc.entidades.Actividad.class);
                    ResultSQL resultSQL = new ResultSQL(tipoDependencia);
                    resultSQL.setStrServer(request.getHeader("host"));
                    resultSQL.setStrUbicacion(getServletContext().getRealPath(""));
                    resultSQL.resultSQLConecta(tipoDependencia); 
                    String rolNormativo = resultSQL.getResultSQLGetRolesPrg();
                    session.setRolNormativo(rolNormativo);
                }
                
                if ("/EditRecords".equals(action)) {
                    if(rs.permiteEditarActividad(session, data)){
                        res = rs.actualizaActividad(data);
                        if (!res) {
                            errorMsg = rs.getStrError();
                        }  else if (!rs.guardar()) {
                            errorMsg = "No se pudo Guardar el cambio. ";
                        }
                        accion = "editado";
                    } else {
                        errorMsg = "No se puede editar la Actividad";
                    }
                }

                if ("/DeleteRecords".equals(action)) {
                    if(rs.permiteBorrarActividad(session, data)){
                        res = rs.borraActividad(data);
                        if (!res) {
                            errorMsg = rs.getStrError();
                        }  else if (!rs.guardar()) {
                            errorMsg = "No se pudo Guardar el cambio. ";
                        }
                        accion = "eliminado";
                    } else {
                        errorMsg = "No se puede borrar la Actividad, revise si esta relacionada a una Acción con Requerimientos";
                    }
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
        } finally {
            if (rs != null) {
                rs.resultSQLDesconecta();
            }
            response.getWriter().print(_gson.toJson(strResultado));
        }
    }
    

}

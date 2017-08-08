
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.api;

import com.google.gson.Gson;
import gob.gbc.Framework.DataSourceRequest;
import gob.gbc.Framework.DataSourceResult;
import gob.gbc.entidades.GraficaMir;
import gob.gbc.entidades.UsuarioSession;
import gob.gbc.sql.ResultSQLCatalogos;
import gob.gbc.util.Bitacora;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ealonso
 */
public class MIR extends HttpServlet{
    private static final long serialVersionUID = 1L;
	private Gson _gson;
        
        public MIR(){
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
                String action = request.getPathInfo();
                ResultSQLCatalogos rs = null;
                
                GraficaMir grafica;
                
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
                    if(action.equals("/GetGraficaMir")){
                        try {
                            grafica = rs.ConsultaGraficaMir(session,ramo, programa);                        
                            request.setAttribute("grafica", grafica);
                            response.setContentType("text/html;charset=utf-8");
                            request.getRequestDispatcher("/subVista/graficaMir.jsp").forward(request, response);
                        } catch (SQLException ex) {
                            Bitacora bitacora = new Bitacora();
                            bitacora.setStrServer(request.getHeader("host"));
                            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
                            bitacora.setITipoBitacora(Bitacora.TIPO_ERROR);
                            bitacora.setStrInformacion(ex, request.getServletPath());
                            bitacora.grabaBitacora();
                        }
                    }else{
                        // get the take and skip parameters

                        DataSourceRequest options = new Gson().fromJson(request.getParameter("options"), DataSourceRequest.class);
                        try {

                                // create a new DataSourceResult to send back
                                DataSourceResult result = new DataSourceResult();

                                // set the total property
                                result.setTotal(rs.getMIRCount(session, ramo, programa, options, true));

                                if(options.getTake() == 0)
                                    options.setTake(result.getTotal());

                                // set the data
                                result.setData(rs.getMIRFiltros(session, ramo, programa, options, false)); 

                                // convert the DataSourceReslt to JSON and write it to the response
                                response.getWriter().print(_gson.toJson(result));
                        }
                        catch (Exception e) {
                                response.sendError(500);
                        }
                    }
            } catch (SQLException ex) {
                Logger.getLogger(MIR.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MIR.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(MIR.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(MIR.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MIR.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
}

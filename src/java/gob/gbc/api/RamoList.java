/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import gob.gbc.Framework.DataSourceRequest;
import gob.gbc.Framework.DataSourceResult;
import gob.gbc.sql.ResultSQLCatalogos;
import gob.gbc.entidades.UsuarioSession;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ealonso
 */
public class RamoList extends HttpServlet{
    private static final long serialVersionUID = 1L;
	private Gson _gson;
        
        public RamoList(){
            super();
            
            // initialize the Gson library
            _gson = new Gson();
        }
        
        public void init() throws ServletException{
            super.init();
        }
        
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set the content type we are sending back as JSON
		response.setContentType("application/json;charset=UTF-8");	
                HttpSession httpsession = request.getSession(true);
                String tipoDependencia = (String)httpsession.getAttribute("tipoDependencia");

                ResultSQLCatalogos rs = null;
                try {
                    rs = new ResultSQLCatalogos(tipoDependencia);                         
                } catch (SQLException ex) {
                    Logger.getLogger(RamoList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RamoList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(RamoList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(RamoList.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RamoList.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                rs.setStrServer(request.getHeader("host"));
                rs.setStrUbicacion(getServletContext().getRealPath("").toString());
                try {
                    rs.resultSQLConecta(tipoDependencia);                    
                } catch (Exception ex) {
                    Logger.getLogger(RamoList.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		// get the take and skip parameters
                UsuarioSession session = new UsuarioSession();
                session.setTipoDependencia(tipoDependencia);  
                session.setYear((String)httpsession.getAttribute("year"));
                session.setStrUsuario((String)httpsession.getAttribute("strUsuario"));
                String ramo = request.getParameter("ramo");
                String programa = request.getParameter("programa");
		DataSourceRequest options = new Gson().fromJson(request.getParameter("options"), DataSourceRequest.class);
		try {
			
			// create a new DataSourceResult to send back
			DataSourceResult result = new DataSourceResult();

			// set the total property
			result.setTotal(rs.getRamoListCount(session, ramo, programa, options, true));
						
                        if(options.getTake() == 0)
                            options.setTake(result.getTotal());
                        
			// set the data
			result.setData(rs.getRamoListFiltros(session, ramo, programa, options, false)); 
						
			// convert the DataSourceReslt to JSON and write it to the response
			response.getWriter().print(_gson.toJson(result));
		}
		catch (Exception e) {
			response.sendError(500);
		}
        }
    
}

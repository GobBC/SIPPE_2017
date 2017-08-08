/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gbc.servlet;

import gob.gbc.aplicacion.CargaProyeccionBean;
import gob.gbc.entidades.Archivo;
import gob.gbc.entidades.Proyeccion;
import gob.gbc.util.Bitacora;
import gob.gbc.util.DatosUpload;
import gob.gbc.util.UtilUploads;
import gob.gbc.util.Utilerias;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

/**
 *
 * @author ugarcia
 */
public class cargarExcel extends HttpServlet {

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
        int row = 0;
        int year = 0;
        boolean bandera = true;
        boolean validaRamo = false;
        JSONObject json = null;
        Proyeccion proyeccion;
        String selRamo = new String();
        String tipoDependencia = new String();
        String usuario = new String();
        String strRutaFile = new String();
        Bitacora bitacora = new Bitacora();
        CargaProyeccionBean cargaBean = null;
        XSSFWorkbook libroXLS;
        Sheet hojaXLS;
        Row rows;
        PrintWriter out = response.getWriter();
        List<Archivo> archivoList = new ArrayList<Archivo>();
        List<Proyeccion> proyeccionList = new ArrayList<Proyeccion>();
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = request.getRequestDispatcher("cargaProyeccion.jsp");
        try {
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }
            if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                usuario = (String) session.getAttribute("strUsuario");
            }
            DatosUpload datos = null;
            UtilUploads upload = new UtilUploads();
            cargaBean = new CargaProyeccionBean(tipoDependencia);
            cargaBean.setStrServer(( request.getHeader("Host")));
            cargaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            cargaBean.resultSQLConecta(tipoDependencia);
            upload.setStrServer(request.getHeader("host"));
            upload.setStrUbicacion(getServletContext().getRealPath("").toString());
            upload.setStrDirectorioTemp(getServletContext().getRealPath("Documentos"));
            datos = upload.getDatos(request);
            selRamo = (String) datos.getParametros().get("selRamo");
            archivoList = datos.getArchivos();
            for (int it = 0; it < archivoList.size(); it++) {
                strRutaFile = getServletContext().getRealPath("").toString()
                        + File.separatorChar + "temp" + File.separatorChar;
                File fTemp = new File(strRutaFile);
                if (!fTemp.isDirectory()) {
                    fTemp.mkdirs();
                }
                strRutaFile += "excel-" + usuario + ".xlsx";
                FileOutputStream outFile = new FileOutputStream(strRutaFile);
                outFile.write(archivoList.get(it).getContenido());
                outFile.close();
            }
            File excel = new File(strRutaFile);
            /*Procesamiento de archivo XLSX*/
            libroXLS = new XSSFWorkbook(new FileInputStream(excel));
            validaRamo = cargaBean.validaRamoPartida(libroXLS, year - 1);
            if (validaRamo) {
                validaRamo = false;
                validaRamo = cargaBean.validaRamoParaestatal(libroXLS, selRamo);
                if (validaRamo) {
                    hojaXLS = libroXLS.getSheetAt(0);
                    row = hojaXLS.getPhysicalNumberOfRows();
                    for (int it = 0; it < row; it++) {
                        proyeccion = new Proyeccion();
                        rows = hojaXLS.getRow(it);
                        try {
                            if (rows != null && !Utilerias.getStringValueProyeccion(rows.getCell(0),false).isEmpty()) {
                                proyeccion = new Proyeccion();
                                //proyeccion = cargaBean.getResultSQLgetProyeccion(ramo, programa, partida, Integer.parseInt(yearList.get(1)));
                                proyeccion.setRamo(Utilerias.getStringValueProyeccion(rows.getCell(0),false));
                                proyeccion.setPrograma(Utilerias.getStringValueProyeccion(rows.getCell(1),false));
                                proyeccion.setPartida(Utilerias.getStringValueProyeccion(rows.getCell(2),false));
                                proyeccion.setRelLaboral(Utilerias.getStringValueProyeccion(rows.getCell(3),false));
                                if(Double.parseDouble(Utilerias.getStringValueProyeccion(rows.getCell(4),true)) >= 0)
                                    proyeccion.setProyectado(Double.parseDouble(Utilerias.getStringValueProyeccion(rows.getCell(4),true)));
                                else{
                                    bandera = false;
                                    break;
                                }     
                                proyeccionList.add(proyeccion);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else {
                    json = new JSONObject();
                    json.put("resultado", "-1");
                    json.put("mensaje", "El ramo seleccionado no coincide con el capturado en el archivo Excel");
                }
            } else {
                json = new JSONObject();
                json.put("resultado", "-1");
                if (!cargaBean.partida.isEmpty()) {
                    json.put("mensaje", "El/La "+cargaBean.partida.split(",")[0]+" " + cargaBean.partida.split(",")[1] + " capturada en el archivo no existe para el año " + (year - 1) + " ");
                } else {
                    json.put("mensaje", "El formato del archivo excel no es correcto.");
                }
            }
            if(!bandera){
                 json = new JSONObject();
                    json.put("resultado", "-1");
                    json.put("mensaje", "La proyección no puede contener valores negativos");
                    proyeccionList = null;
            }
        } catch (IllegalStateException e) {
            bitacora.setStrUbicacion(cargaBean.getStrUbicacion());
            bitacora.setStrServer(cargaBean.getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();
            json = new JSONObject();
            json.put("resultado", "-1");
            json.put("mensaje", "Uno de los valores en el archivo no corresponde a número o caracter");
        } catch (Exception e) {
            bitacora.setStrUbicacion(cargaBean.getStrUbicacion());
            bitacora.setStrServer(cargaBean.getStrServer());
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, new Throwable());
            bitacora.grabaBitacora();
            json = new JSONObject();
            json.put("resultado", "-1");
            json.put("mensaje", "Ocurrió un error inseperado al cargar el archivo");
        } finally {
            session.setAttribute("jason", json);
            session.setAttribute("resultadoCarga", proyeccionList);
            session.setAttribute("selRamo", selRamo);
            //response.sendRedirect("cargaProyeccion.jsp");       
            dispatcher.forward(request, response);
            cargaBean.resultSQLDesconecta();
            out.close();
        }
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
        return "Obtiene los datos de un archivo xlsx para procesamiento y guardado en base de datos ";
    }// </editor-fold>

}

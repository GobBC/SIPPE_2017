/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gob.gbc.servlet;

import gob.gbc.bd.ConectaBD;
import gob.gbc.sql.ResultSQL;
import gob.gbc.util.Archivo;
import gob.gbc.util.Bitacora;
import gob.gbc.util.Utilerias;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * @author ugarcia
 */
public class PaqueteReportesReprogramado extends HttpServlet {

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
        HttpSession session = null;
        Map<String, Object> jasperParameter;
        
        int year = 0;   
        int radioBusqueda = -1;
        
        long caratula = 0;
        byte reporte[];
        
        File arrayArchivos[];
        
        String proyIni = new String();
        String proyFin = new String();
        String programaI = new String();
        String programaF = new String();
        String reportType = new String();
        String ramo = new String();
        String filename = new String();
        String tipoDependencia = new String();
        String nombreArchivo = new String();
        String nombresReporte = new String();
        String arregloReportes[] = new String[3];
        String idUsuario = new String();
        String query = new String();
        String oficioList = new String();
        String rol = new String();
        String usuario = new String();
        
        String imgpath = getServletContext().getRealPath("/reportes/imagenes") + File.separatorChar;
        String rptPath = getServletContext().getRealPath("/reportes") + File.separatorChar;
        String tempPath = getServletContext().getRealPath("/temp") + File.separatorChar;
        
        ConectaBD conectaBD = null;
        FileOutputStream paqueteZip = null;
        ZipOutputStream zip = null;
        JRXlsxExporter export = null;
        ByteArrayOutputStream  xlsReport;
        
        RequestDispatcher dispatcher;
        
        try {
            File file = new File(tempPath + File.separatorChar + "reportesR");
            if(!file.exists()){
                file.mkdir();
            }
            arrayArchivos = file.listFiles(new FilenameFilter(){
                @Override
                public boolean accept(File file, String name){
                    return name.toLowerCase().endsWith(".zip");
                }
            });
            if(arrayArchivos != null)
                for(File zipt : arrayArchivos){
                    if(!DateUtils.isSameDay(new Date(), new Date(zipt.lastModified())) &&
                            new Date(zipt.lastModified()).before(new Date()) ){
                        zipt.delete();
                    }
                }
            System.setProperty("java.awt.headless", "true");
            session = request.getSession();
            if (session.getAttribute("strUsuario") == null
                    || session.getAttribute("strUsuario").equals("")) {
                response.sendRedirect("logout.jsp");
            }
            
            if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                usuario = (String) session.getAttribute("strUsuario");
            }
            
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }
            
            if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
                rol = (String) session.getAttribute("strRol");
            }
            
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
            
            if(Utilerias.existeParametro("selRamo", request)){
                ramo = request.getParameter("selRamo");
            }
            
            if(Utilerias.existeParametro("reporttype", request)){
                reportType = request.getParameter("reporttype");
            }
            
            if(Utilerias.existeParametro("oficioList", request)){
                oficioList = request.getParameter("oficioList");
            }
            
            if (Utilerias.existeParametro("filename", request)) {
                filename = request.getParameter("filename");
            }
            
            if (Utilerias.existeParametro("nombresReporte", request)) {
                nombresReporte = request.getParameter("nombresReporte");
                arregloReportes = nombresReporte.split(",");
            }
            
            if (Utilerias.existeParametro("rptPath", request)) {
                rptPath += request.getParameter("rptPath") + File.separatorChar;
                filename = File.separatorChar + filename;
            }
            
            if (Utilerias.existeParametro("selCaratula", request)) {
                caratula = Long.parseLong(request.getParameter("selCaratula"));
            }
            
            if (Utilerias.existeParametro("radio-busca", request)) {
                radioBusqueda = Integer.parseInt(request.getParameter("radio-busca"));
            }
            
            if (Utilerias.existeParametro("selProgramaI", request)) {
                programaI = request.getParameter("selProgramaI");
            }

            if (Utilerias.existeParametro("selProgramaF", request)) {
                programaF = request.getParameter("selProgramaF");
            }
            
            idUsuario = session.getId().substring(session.getId().length() - 5, session.getId().length());
            
            ResultSQL resultSQL = new ResultSQL(tipoDependencia);
            resultSQL.setStrServer(request.getHeader("host"));
            resultSQL.setStrUbicacion(getServletContext().getRealPath(""));
            resultSQL.resultSQLConecta(tipoDependencia);//cambiar 
            
            
            if (radioBusqueda == 1) {
            //getResultSQLgetFoliosByCaratula(int year, String ramo, String appLogin, boolean isNormativo, long caratula)
                if (caratula != -2 && caratula != 0) {
                    oficioList = resultSQL.getResultSQLgetFoliosByCaratula(year, ramo, usuario, rol.equals(resultSQL.getResultSQLGetRolesPrg()), caratula);
                    if (oficioList.isEmpty()) {
                        query = "TR.OFICIO IN (0)";
                    } else {
                        for (int cont = 0; cont < oficioList.split(",").length; cont++) {
                            query += "TR.OFICIO IN (" + oficioList.split(",")[cont] + ") ";
                            if (cont < oficioList.split(",").length - 1) {
                                query += " OR \n";
                            }
                        }
                    }
                } else {
                    query += "TR.RAMO IN (" + ramo + ") \n";
                    if (!programaI.equals("-1") && !programaF.equals("-1")) {
                        if (!programaI.equals(programaF)) {
                            query += " AND TR.PRG BETWEEN '" + programaI + "' AND '" + programaF + "' \n";
                        } else {
                            query += " AND TR.PRG = '" + programaI + "' \n";
                            if (!proyIni.equals("-1") && !proyFin.equals("-1")) {
                                query += " AND TR.PROY||','||TR.TIPO_PROY BETWEEN '" + proyIni + "' AND '" + proyFin + "' \n";
                            }

                        }
                    }
                    if(caratula != 0){
                        query += "AND (";
                        oficioList = resultSQL.getResultSQLgetFoliosByCaratula(year, ramo, usuario, rol.equals(resultSQL.getResultSQLGetRolesPrg()), caratula);
                        if (oficioList.isEmpty()) {
                            query += " TR.OFICIO IN (0)";
                        } else {
                            for (int cont = 0; cont < oficioList.split(",").length; cont++) {
                                query += "TR.OFICIO IN (" + oficioList.split(",")[cont] + ") ";
                                if (cont < oficioList.split(",").length - 1) {
                                    query += " OR \n";
                                }
                            }
                        }
                        query += ")";
                    }
                }
            }else if (radioBusqueda == 0) {
                query = "TR.OFICIO = " + oficioList + " ";
            }
            
            jasperParameter = new HashMap<String, Object>();
            
            jasperParameter.put("YEAR", year);
            jasperParameter.put("ID_CARATULA", caratula);
            jasperParameter.put("RPT_DIR", rptPath);
            jasperParameter.put("RAMO", ramo);
            jasperParameter.put("QUERY", query);
            jasperParameter.put("imgDir" ,imgpath);
            conectaBD = resultSQL.getConectaBD();
            
            paqueteZip = new FileOutputStream(tempPath + File.separatorChar + "reportesR"  + File.separatorChar  + "ModificacionesProgramatica"+ idUsuario +".zip");
            zip = new ZipOutputStream(paqueteZip);
            for(int it= 0; it < arregloReportes.length ; it++){
                switch(it){
                    case 0 :
                        nombreArchivo = "reportesR" + File.separatorChar + "MetasNuevasMPCR";
                        break;
                    case 1 :
                        nombreArchivo = "reportesR" + File.separatorChar + "RequerimientosMPCR";
                        break;
                    case 2 :
                        nombreArchivo = "reportesR" + File.separatorChar + "MetasAccionMPMD01";
                        break;
                    default :
                        nombreArchivo = "reportesR" + File.separatorChar + "MetasNuevasMPCR";
                }
                JasperPrint jasperPrint = JasperFillManager.fillReport(rptPath + arregloReportes[it], jasperParameter,
                        conectaBD.getConConexion());
                //outputStream = response.getOutputStream();
                
                response.setContentType("application/force-download");
                response.setHeader("Content-Transfer-Encoding", "binary");
                response.setHeader("Content-Disposition","attachment; filename=\"formatoModificacionesPresupuestales"+ idUsuario +".zip\"");
                if(reportType.equals("pdf")){                    
                    JasperExportManager.exportReportToPdfFile(jasperPrint, tempPath + File.separatorChar +  nombreArchivo +""+ idUsuario +".pdf");
                }else{
                    export = new JRXlsxExporter();
                    xlsReport = new ByteArrayOutputStream();
                    export.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    export.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
                    export.exportReport();
                    reporte = xlsReport.toByteArray();
                    xlsReport.close();
                    FileOutputStream  ouputStream = new FileOutputStream(tempPath + File.separatorChar + nombreArchivo +""+ idUsuario +".xlsx");
                    ouputStream.write(reporte);
                    ouputStream.close();
                    reportType = "xlsx";
                }
                Archivo.addToZipFile(tempPath + File.separatorChar,  nombreArchivo +""+ idUsuario +"."+ reportType,zip);
                File fTemp = new File(tempPath + File.separatorChar + nombreArchivo +""+ idUsuario +"."+ reportType);
                fTemp.delete();
            }
            /*
            if ("pdf".equalsIgnoreCase(reportType)) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            }else if ("xls".equalsIgnoreCase(reportType)) {
                response.setContentType("application/xlsx");
                response.setHeader("Content-Disposition", "inline; filename=\"report.xlsx\"");
                //exporter = new JRXlsExporter();
                export = new JRXlsxExporter();
                export.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                export.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);
            }
            if (!"xls".equalsIgnoreCase(reportType)) {
                exporter.exportReport();
            } else {
                export.exportReport();
            }
            */
        } catch (JRException e) {
            Bitacora bitacora = new Bitacora();
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(e, request.getServletPath());
            bitacora.grabaBitacora();
            throw new ServletException(e);
        }catch(Exception ex){
            Bitacora bitacora = new Bitacora();
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.grabaBitacora();
            throw new ServletException(ex);
        }finally {
            try {
                zip.close();
                paqueteZip.close();
                conectaBD.desconecta();
                dispatcher = request.getRequestDispatcher("/temp/reportesR/ModificacionesProgramatica"+ idUsuario +".zip");                
                dispatcher.forward(request, response);
            } catch (Exception ex) {
                Bitacora bitacora = new Bitacora();
                bitacora.setStrServer(request.getHeader("host"));
                bitacora.setStrUbicacion(getServletContext().getRealPath(""));
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, request.getServletPath());
                bitacora.grabaBitacora();
            }
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
        return "Short description";
    }// </editor-fold>

}

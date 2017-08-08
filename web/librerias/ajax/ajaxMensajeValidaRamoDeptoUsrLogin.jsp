

<%@page import="java.util.Vector"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.Writer"%>
<%@page import="net.sf.jasperreports.engine.fill.JRTemplatePrintText"%>
<%@page import="net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporterParameter"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.sf.jasperreports.engine.base.JRBasePrintPage"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.jasperreports.engine.JRPrintPage"%>
<%@page import="gob.gbc.aplicacion.ParametroBean"%>
<%@page import="gob.gbc.bd.ConectaBD"%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.estandar.DatosGeneral"%>
<%@page import="gob.gbc.bd.DatosBdConfig"%>
<%@page import="gob.gbc.sql.QuerysBD"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="net.sf.jasperreports.engine.export.JRCsvExporter"%>
<%@page import="net.sf.jasperreports.engine.JRException"%>
<%@page import="java.io.IOException"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRRtfExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporter"%>
<%@page import="java.io.OutputStream"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%
    String tipoDependencia = new String();
    String strRespuesta = new String();
    String strUsuario = new String();
    String strContrasena = new String();
    String strUsuRamoTMP = new String();
    String strUsuarioTMP = new String();
    String strUsuDeptoTMP= new String();
    
    
    if (request.getParameter("tipoDependencia") != null) {
        tipoDependencia = (String) request.getParameter("tipoDependencia");
    }
     if (request.getParameter("strUsuario") != null) {
        strUsuario = (String) request.getParameter("strUsuario");
    }
    if (request.getParameter("strEncrypt") != null) {
        strContrasena = (String) request.getParameter("strEncrypt");
    }
 
    try{
            ResultSQL resultSQL = new ResultSQL(tipoDependencia);
            resultSQL.setStrServer(((String) request.getHeader("Host")));
            resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
            resultSQL.resultSQLConecta(tipoDependencia);
            Vector vecResultado = resultSQL.getResultSQLConsultaEstatusUsuario(strUsuario, strContrasena);
            resultSQL.resultSQLDesconecta();


            strUsuarioTMP=(String) vecResultado.get(1);
            strUsuRamoTMP=(String) vecResultado.get(6);
            strUsuDeptoTMP=(String) vecResultado.get(7);
             if((strUsuRamoTMP==null) && (strUsuDeptoTMP==null)){
                   strRespuesta="Falta capturar Ramo y Departamento para este usuario: " + strUsuario ;
             }else if((strUsuRamoTMP==null) && (strUsuDeptoTMP != null )  ){
                   strRespuesta="Falta capturar Ramo para este usuario: " + strUsuario;
             }else if((strUsuRamoTMP != null) && (strUsuDeptoTMP == null )){
                   strRespuesta="Falta capturar Departamento para este usuario: " + strUsuario;
             }else{
                 strRespuesta="1";
             }
            
          } catch (Exception ex) {
               
                Bitacora bitacora = new Bitacora();
                bitacora.setStrServer(request.getHeader("host"));
                bitacora.setStrUbicacion(getServletContext().getRealPath(""));
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, request.getServletPath());
                bitacora.grabaBitacora();
            } finally {
//                conectaBD.desconecta();
                out.print(strRespuesta);
            }
        %>

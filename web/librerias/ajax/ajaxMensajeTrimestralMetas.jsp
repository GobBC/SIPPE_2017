

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
            if (session.getAttribute("strUsuario") == null
                    || session.getAttribute("strUsuario").equals("")) {
                response.sendRedirect("logout.jsp");
                return;
            }
            System.setProperty("java.awt.headless", "true");
            ConectaBD conectaBD;
            String tipoDependencia = new String();
            if (request.isRequestedSessionIdValid()) {

                if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {

                    tipoDependencia = (String) session.getAttribute("tipoDependencia");
                }
            }
            if (Utilerias.existeParametro("tipoDep", request)) {
                tipoDependencia = request.getParameter("tipoDep");
            }
            ResultSQL resultSQL = new ResultSQL(tipoDependencia);
            resultSQL.setStrServer(request.getHeader("host"));
            resultSQL.setStrUbicacion(getServletContext().getRealPath(""));
            resultSQL.resultSQLConecta(tipoDependencia);//cambiar 

            int intYear = 0;
            String queryPartidas = new String();

            QuerysBD querysBD = new QuerysBD();
            String programaI = new String();
            String programaF = new String();
            int  iCandadoMTvsCIM = 0;
            String ramoInList = "";
            String sTipoOficio = "";
            String  strParamValidaCIM = new String();
            String strDocValido = "S";
            conectaBD = resultSQL.getConectaBD();
            String query = "";
            int periodo=0;
            int mes = 0;
            
            try {
                if (request.isRequestedSessionIdValid()) {
                    if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                        intYear = Integer.parseInt((String) session.getAttribute("year"));
                    }
                }
                if (Utilerias.existeParametro("yearT", request)) {
                    intYear = Integer.parseInt(request.getParameter("yearT"));
                }

             

                if (Utilerias.existeParametro("tipoOficio", request)) {
                    sTipoOficio = request.getParameter("tipoOficio");
                    if (sTipoOficio.equals("null") || sTipoOficio.isEmpty() || sTipoOficio == null) {
                        sTipoOficio = new String();
                    }
                }
                if (Utilerias.existeParametro("selProgramaI", request)) {
                    programaI = request.getParameter("selProgramaI");
                }

                if (Utilerias.existeParametro("selProgramaF", request)) {
                    programaF = request.getParameter("selProgramaF");
                }


                if (Utilerias.existeParametro("ramoInList", request)) {
                    ramoInList = request.getParameter("ramoInList");
                }
                if (Utilerias.existeParametro("periodo", request)) {
                    periodo = Integer.parseInt(request.getParameter("periodo"));
                    mes = periodo * 3;
                }
              
                    iCandadoMTvsCIM = resultSQL.getResultSQLvalidaCapturaCIM(periodo,intYear,ramoInList,programaI,programaF);
                   strParamValidaCIM=resultSQL.getResultSQLParamvalidaCIM();
                  if(iCandadoMTvsCIM<=0 && strParamValidaCIM.equals("S")){
                    strDocValido ="N";
                    }else{
                    strDocValido ="S";
                  }
           } catch (Exception ex) {
                strDocValido = "-1";
                Bitacora bitacora = new Bitacora();
                bitacora.setStrServer(request.getHeader("host"));
                bitacora.setStrUbicacion(getServletContext().getRealPath(""));
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, request.getServletPath());
                bitacora.grabaBitacora();
            } finally {
                conectaBD.desconecta();
                out.print(strDocValido);
            }
        %>

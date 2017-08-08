<%-- 
    Document   : ajaxBorrarSubconceptoCapturaIngreso
    Created on : Abr 22, 2016, 2:00:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.entidades.CapturaPresupuestoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaIngresoBean"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    CapturaIngresoBean capturaIngresoBean = null;

    String ramoId = new String();
    String concepto = new String();
    String selTipoMov = new String();
    String strResultado = new String();
    String tipoDependencia = new String();

    long selCaratula = -1;

    int year = 0;
    int subConcepto = -1;

    boolean resultado = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        if (request.getParameter("yearPres") != null && request.getParameter("yearPres") != "") {
            year = Integer.parseInt(request.getParameter("yearPres"));
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = request.getParameter("ramo");
        }
        if (request.getParameter("concepto") != null && !request.getParameter("concepto").equals("")) {
            concepto = request.getParameter("concepto");
        }
        if (request.getParameter("selTipoMov") != null && !request.getParameter("selTipoMov").equals("")) {
            selTipoMov = request.getParameter("selTipoMov");
        }
        if (request.getParameter("subConcepto") != null && !request.getParameter("subConcepto").equals("")) {
            subConcepto = Integer.parseInt(String.valueOf(request.getParameter("subConcepto")));
        }

        capturaIngresoBean = new CapturaIngresoBean(tipoDependencia);
        capturaIngresoBean.setStrServer((request.getHeader("Host")));
        capturaIngresoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaIngresoBean.resultSQLConecta(tipoDependencia);

        resultado = capturaIngresoBean.deleteSubConceptoCapturaIngreso(selCaratula, year, ramoId, concepto, subConcepto);

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (capturaIngresoBean != null) {
            capturaIngresoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

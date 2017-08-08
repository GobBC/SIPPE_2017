<%-- 
    Document   : ajaxActualizaStatusIngresoModificado
    Created on : Ago 04 , 2016, 4:50:46 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="gob.gbc.entidades.ModificacionIngreso"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.CapturaPresupuestoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaIngresoBean"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.PresupuestoIngreso"%>
<%@page import="gob.gbc.aplicacion.CapturaPresupuestoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    response.setCharacterEncoding("UTF-8");

    CapturaIngresoBean capturaIngresoBean = null;

    String status = "A";
    String ramoId = new String();
    String concepto = new String();
    String strResultado = new String();
    String tipoDependencia = new String();

    long selCaratula = -1;

    int year = 0;

    boolean resultado = false;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
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
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        if (request.getParameter("status") != null && !request.getParameter("status").equals("")) {
            status = (String) request.getParameter("status");
        }

        capturaIngresoBean = new CapturaIngresoBean(tipoDependencia);
        capturaIngresoBean.setStrServer(((String) request.getHeader("Host")));
        capturaIngresoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        capturaIngresoBean.resultSQLConecta(tipoDependencia);

        if (capturaIngresoBean.existeStatusModificacionIngreso(selCaratula, year, ramoId, concepto)) {
            resultado = capturaIngresoBean.updateStatusIngresoModificado(selCaratula, year, ramoId, concepto, status);
        } else {
            resultado = capturaIngresoBean.insertStatusIngresoModificado(selCaratula, year, ramoId, concepto, status);
        }

        if (!resultado) {
            capturaIngresoBean.transaccionRollback();
            strResultado = "-1";
        } else {
            capturaIngresoBean.transaccionCommit();
        }

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

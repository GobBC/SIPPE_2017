<%-- 
    Document   : ajaxGuardarReprogramacion
    Created on : Dec 23, 2015, 2:11:21 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.aplicacion.ReprogramacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ReprogramacionBean reproBean = null;
    MovimientosReprogramacion movReprog = new MovimientosReprogramacion();
    String tipoDependencia = new String();
    String strResultado = new String();
    String appLogin = new String();
    String justificacion = new String();
    String estatus = new String();
    String tipoMov = new String();
    String mensaje = new String();
    String date = new String();
    String ramoSession = new String();
    long selCaratula = -1;
    int folio = 0;
    int year = 0;
    boolean isActual = true;
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        if (request.getParameter("justificacion") != null && !request.getParameter("justificacion").equals("")) {
            justificacion = (String) request.getParameter("justificacion");
            justificacion = justificacion.replaceAll("'","''");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = (String) request.getParameter("estatus");
        }
        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = (String) request.getParameter("tipoMov");
        }
        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt((String) request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("isActual", request)) {
            isActual = Boolean.parseBoolean((String) request.getParameter("isActual"));
        }
        if (Utilerias.existeParametro("dateFor", request)) {
            date = (String) request.getParameter("dateFor");
        }
        if (session.getAttribute("reprogramacion") != null && session.getAttribute("reprogramacion") != "") {
            movReprog = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            movReprog.setAppLogin(appLogin);
            movReprog.setJutificacion(justificacion);
            movReprog.setStatus(estatus);
            movReprog.setTipoMovimiento(tipoMov);
        }
        if (movReprog.getMovOficioAccionList().size() > 0 || movReprog.getMovOficioMetaList().size() > 0) {
            reproBean = new ReprogramacionBean(tipoDependencia);
            reproBean.setStrServer(request.getHeader("host"));
            reproBean.setStrUbicacion(getServletContext().getRealPath(""));
            reproBean.resultSQLConecta(tipoDependencia);
            if (movReprog.getRamo() == null) {
                mensaje = reproBean.saveMovimientoReprogramacion(movReprog, folio, isActual, year, date, ramoSession, selCaratula, justificacion);
            } else {
                mensaje = reproBean.saveMovimientoReprogramacion(movReprog, folio, isActual, year, date, movReprog.getRamo(), selCaratula, justificacion);
            }
            if (mensaje.startsWith("$")) {
                strResultado = "1|Los registros se grabaron satisfactoriamente|" + mensaje.replace("$", "");
            } else {
                strResultado = "2|" + mensaje;
            }
        } else {
            strResultado += "6|Debe de realizar al menos un moviemiento antes de guardar";
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
        if (reproBean != null) {
            reproBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
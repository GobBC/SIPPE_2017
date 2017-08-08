<%-- 
    Document   : ajaxGetFoliosByRamoCaratulaTipoMovStatusMov
    Created on : Jun 09, 2017, 10:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Movimiento"%>
<%@page import="gob.gbc.aplicacion.MovimientosBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Fechas"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    MovimientosBean movimientoBean = null;
    Movimiento movto = new Movimiento();

    List<Movimiento> movimientoList = new ArrayList<Movimiento>();

    Fechas objFechas = new Fechas();

    String allDisabled = "";
    String rol = new String();
    String ramoId = new String();
    String tipoMov = new String();
    String btnJust = new String();
    String appLogin = new String();
    String strResult = new String();
    String statusMov = new String();
    String tipoDependencia = new String();
    String btnAsignarCaratula = new String();
    String btnDesAsignarCaratula = new String();

    long caratula = -1;

    int year = 0;
    int folio = 0;

    try {

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("caratula") != null && !request.getParameter("caratula").equals("")) {
            caratula = Long.parseLong(request.getParameter("caratula"));
        }
        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = request.getParameter("tipoMov");
        }
        if (request.getParameter("statusMov") != null && !request.getParameter("statusMov").equals("")) {
            statusMov = request.getParameter("statusMov");
        }

        movimientoBean = new MovimientosBean(tipoDependencia);
        movimientoBean.setStrServer(request.getHeader("host"));
        movimientoBean.setStrUbicacion(getServletContext().getRealPath(""));
        movimientoBean.resultSQLConecta(tipoDependencia);

        movimientoList = movimientoBean.getFoliosByYearRamoCaratulaTipoMovStatusMov(year, ramoId, caratula, tipoMov, statusMov, appLogin, rol.equals(movimientoBean.getRolNormativo()));
        
        strResult += "               <option value='-1' Selected > -- Seleccione un folio -- </option> ";
        
        if (caratula != -1) {
            for (Movimiento solicitudMovimiento : movimientoList) {
                strResult += "               <option value=" + solicitudMovimiento.getOficio() + "  > " + solicitudMovimiento.getOficio() + " </option>";
            }
        }

    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (movimientoBean != null) {
            movimientoBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>

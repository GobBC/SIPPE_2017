<%-- 
    Document   : ajaxGetLineaSectorialOnChange
    Created on : Dec 28, 2015, 11:52:27 AM
    Author     : Jarguelles
--%>

<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.LineaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    LineaBean lineaBean = null;
    List<Linea> sectorialList = new ArrayList<Linea>();
    String strResultado = new String();
    String ramoId = new String();
    String programaId = new String();
    String estrategia = new String();
    String selected = new String();
    String tipoDependencia = new String();
    int year = 0;
    int proyecto = 0;
    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        if (request.getParameter("programaId") != null
                && !request.getParameter("programaId").equals("")) {
            programaId = (String) request.getParameter("programaId");
        }
        if (request.getParameter("estrategia") != null
                && !request.getParameter("estrategia").equals("")) {
            estrategia = (String) request.getParameter("estrategia");
        }
        if (request.getParameter("proyectoId") != null
                && !request.getParameter("proyectoId").equals("")) {
            proyecto = Integer.parseInt((String) request.getParameter("proyectoId"));
        }
        lineaBean = new LineaBean(tipoDependencia);
        lineaBean.setStrServer(request.getHeader("host"));
        lineaBean.setStrUbicacion(getServletContext().getRealPath(""));
        lineaBean.resultSQLConecta(tipoDependencia);
        if (!estrategia.equals("-1")) {
            sectorialList = lineaBean.getLineaSectorialByLineaAccion(estrategia, year);
            strResultado += "<option value='-1'> -- Seleccione una l&iacute;nea sectorial -- </option>";
            if (sectorialList.size() > 0) {

                for (Linea lineaSect : sectorialList) {
                    if (estrategia.equals(lineaSect.getLineaId())) {
                        selected = "selected";
                    }
                    strResultado += "<option value='" + lineaSect.getLineaId() + "' " + selected + ">"
                            + lineaSect.getLineaId() + "-" + lineaSect.getLinea() + "</option>";
                    selected = "";
                }
            }
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
        if (lineaBean != null) {
            lineaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

<%-- 
    Document   : ajaxGetRamosByYear
    Created on : Jun 08, 2015, 4:08:07 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    int year = 0;

    String usuario = new String();
    RamoBean ramoBean = null;
    List<Ramo> ramoList = new ArrayList<Ramo>();
    String tipoDependencia = new String();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        
        if (request.getParameter("year") != null && !request.getParameter("year").equals("")) {
            year = Integer.parseInt((String) request.getParameter("year"));
        }

        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }

        ramoBean = new RamoBean(tipoDependencia);
        ramoBean.setStrServer(request.getHeader("host"));
        ramoBean.setStrUbicacion(getServletContext().getRealPath(""));
        ramoBean.resultSQLConecta(tipoDependencia);

        ramoList = ramoBean.getResultRamoByYear(year, usuario);
        ramoBean.resultSQLDesconecta();

        strResultado += "   <option value='-1'>";
        strResultado += "       -- Seleccione un ramo --";
        strResultado += "   </option>";

        for (Ramo ramo : ramoList) {
            strResultado += "<option value='" + ramo.getRamo() + "'>";
            strResultado += ramo.getRamo() + "-" + ramo.getRamoDescr();
            strResultado += "</option>";
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
        if (ramoBean != null) {
            ramoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
<%-- 
    Document   : ajaxDesligarIndicadorSectorRamo
    Created on : Nov 08, 2016, 4:54:00 PM
    Author     : jarguelles
--%>

<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.IndicadorSectorRamo"%>
<%@page import="gob.gbc.aplicacion.IndicadorSectorRamoBean"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Expires", "0");
    response.setDateHeader("Expires", -1);
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");

    IndicadorSectorRamoBean indicadorSectorRamoBean = null;

    String strResultado = "-1";
    String year = new String();
    String selRamo = new String();    
    String selPrograma = new String();
    String selIndicador = new String();
    String tipoDependencia = new String();

    int countRow = 0;

    boolean resultado = false;

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = (String) session.getAttribute("year");
        }
        if (request.getParameter("selIndicador") != null && !request.getParameter("selIndicador").equals("")) {
            selIndicador = request.getParameter("selIndicador");
        }
        if (request.getParameter("selRamo") != null && !request.getParameter("selRamo").equals("")) {
            selRamo = request.getParameter("selRamo");
        }
                if (request.getParameter("selPrograma") != null && !request.getParameter("selPrograma").equals("")) {
            selPrograma = request.getParameter("selPrograma");
        }

        indicadorSectorRamoBean = new IndicadorSectorRamoBean(tipoDependencia);
        indicadorSectorRamoBean.setStrServer((request.getHeader("Host")));
        indicadorSectorRamoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        indicadorSectorRamoBean.resultSQLConecta(tipoDependencia);

        resultado = indicadorSectorRamoBean.getDesligarIndicadorRamoSector(year, selIndicador, selRamo, selPrograma);

        if(resultado)
        strResultado += "1";

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (indicadorSectorRamoBean != null) {
            indicadorSectorRamoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

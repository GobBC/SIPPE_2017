<%-- 
    Document   : ajaxGetIndicadorSectorRamos
    Created on : Nov 07, 2016, 2:33:00 PM
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
    List<IndicadorSectorRamo> arrIndicadoresSectorRamos = null;

    String year = new String();
    String selRamo = new String();
    String strResultado = new String();
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

        indicadorSectorRamoBean = new IndicadorSectorRamoBean(tipoDependencia);
        indicadorSectorRamoBean.setStrServer((request.getHeader("Host")));
        indicadorSectorRamoBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        indicadorSectorRamoBean.resultSQLConecta(tipoDependencia);

        arrIndicadoresSectorRamos = indicadorSectorRamoBean.getObtieneIndicadorSectorRamos(year, selIndicador, selRamo);

        strResultado += " <table id='tblPresupuesto'>";
        strResultado += "   <thead> ";
        strResultado += "       <tr> ";
        strResultado += "           <th> Ramo </th>";
        strResultado += "           <th style='width: 30%;'> Descripci&oacute;n </th>";
        strResultado += "           <th> Programa </th>";
        strResultado += "           <th style='width: 30%;'> Descripci&oacute;n</th>";
        strResultado += "           <th></th> ";
        strResultado += "       </tr> ";
        strResultado += "   </thead> ";
        strResultado += "   <tbody> ";
        strResultado += "       <tr> ";

        for (IndicadorSectorRamo indicadorSectorRamo : arrIndicadoresSectorRamos) {

            countRow++;

            if (countRow % 2 == 0) {
                strResultado += "<tr class='rowPar'>";
            } else {
                strResultado += "<tr>";
            }

            strResultado += "<td>" + indicadorSectorRamo.getRamo() + "</td>";
            strResultado += "<td> " + indicadorSectorRamo.getRamoDescr() + "</td> ";
            strResultado += "<td>" + indicadorSectorRamo.getPrg() + "</td>";
            strResultado += "<td> " + indicadorSectorRamo.getPrgDescr() + "</td> ";
            strResultado += "<td><input  type='button' class='btnbootstrap-drop btn-borrar' onclick=' desligarIndicadorSectorRamo(\"" + indicadorSectorRamo.getClaveIndicador() + "\",\"" + indicadorSectorRamo.getRamo() +"\",\"" + indicadorSectorRamo.getPrg()+ "\"); '/></td>";
            strResultado += "</tr>";

        }

        strResultado += " </tbody>";
        strResultado += "</table>";
        strResultado += "<script type=\"text/javascript\">";
        strResultado += "  $(\"#tblPresupuesto tbody tr\").click(function(){";
        strResultado += "  $(this).addClass('selected').siblings().removeClass('selected');";
        strResultado += "});";
        strResultado += "</script>";

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

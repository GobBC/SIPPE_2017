<%-- 
    Document   : ajaxGetRamosAsociadosOficioByOficio
    Created on : Jun 23 , 2016, 1:50:46 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    RamoBean ramoBean = null;
    List<Ramo> ramoList = new ArrayList<Ramo>();

    String strResultado = new String();
    String tipoDependencia = new String();

    int year = 0;
    int oficio = 0;

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (Utilerias.existeParametro("oficio", request)) {
            oficio = Integer.parseInt(request.getParameter("oficio"));
        }

        ramoBean = new RamoBean(tipoDependencia);
        ramoBean.setStrServer(request.getHeader("host"));
        ramoBean.setStrUbicacion(getServletContext().getRealPath(""));
        ramoBean.resultSQLConecta(tipoDependencia);

        ramoList = ramoBean.getResultRamosAsociadosOficioByOficio(oficio, year);
        ramoBean.resultSQLDesconecta();

        strResultado += "    <table Style='width: 390px;' > ";
        strResultado += "      <thead> ";
        strResultado += "        <tr Style='background-color: #21ADE4' > ";
        strResultado += "          <th>Ramo</th> ";
        strResultado += "          <th style='text-align:center;' >Descripción</th> ";
        strResultado += "        </tr> ";
        strResultado += "      </thead> ";
        strResultado += "      <tbody  > ";

        for (Ramo ramo : ramoList) {
            strResultado += "        <tr Style = 'background-color: #f2f2f2'> ";
            strResultado += "          <td>" + ramo.getRamo() + "</td> ";
            strResultado += "          <td style='text-align:center;' >" + ramo.getRamoDescr() + "</td> ";
            strResultado += "        </tr> ";
        }

        strResultado += "      </tbody> ";
        strResultado += "    </table> ";

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResultado);
    }
%>

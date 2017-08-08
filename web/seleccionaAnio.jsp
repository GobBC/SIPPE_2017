<%-- 
    Document   : seleccionaAnio
    Created on : May 25, 2015, 3:46:05 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.YearBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        List<String> yearList = new ArrayList<String>();
        int arrayLength = 0;
        String selected = new String();
        String tipoDependencia = new String();
        String aviso = new String();
        String avisoArray[];
        YearBean yearBean = null;
        try {
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
            yearBean = new YearBean(tipoDependencia);
            yearBean.setStrServer(((String) request.getHeader("Host")));
            yearBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            yearBean.resultSQLConecta(tipoDependencia);
            yearList = yearBean.getSelectYear();
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Selecci&oacute;n de a&ntilde;o</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <center>
        <form id="frmYear" action="menu.jsp" method="POST">
            <label> Seleccione el ejercicio que se programar&aacute; </label>
            <div id="selYear">
                <%
                    selected = "checked";
                    for (String year : yearList) {
                        if (year.equals("0")) {
                            continue;
                        }
                        out.write("<input id='rad" + year + "' type='radio' name='year' value='" + year + "' " + selected + "><label for='rad" + year + "'>" + year + "</label></input>");
                        selected = "";
                    }
                %>
            </div>
            <input type="submit" value="Aceptar"/>
        </form>
        <div id="infoIncial">
            <div>
                <%
                    aviso = yearBean.getAvisoInicial();
                    if (aviso != null) {
                        arrayLength = aviso.split("\n").length;
                        avisoArray = new String[arrayLength];
                        avisoArray = aviso.split("\n");
                        for (int it = 0; it < arrayLength; it++) {
                            out.write(avisoArray[it]);
                            out.write("</br>");
                        }
                    } else {
                        out.write("");
                        out.write("</br>");
                    }
                %>
            </div>
        </div>
    </center>
    <%} catch (Exception ex) {
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (yearBean != null) {
            yearBean.resultSQLDesconecta();
        }
    }%>
        
    <jsp:include page="template/piePagina.jsp" />
</html>

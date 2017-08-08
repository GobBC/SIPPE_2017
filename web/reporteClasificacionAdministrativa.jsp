<%-- 
    Document   : reporteClasificacionAdministrativa
    Created on : Dic 04, 2015, 04:00:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Calendarizado Mensual</title>
    </head>
    <%
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
    %>

    <jsp:include page="template/encabezado.jsp" />

    <body>
        <div Id="TitProcess"><label >Reporte Calendarizado Mensual<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarReporteCalendarizadoPorMes()">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <div class="center-div"> 
            <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <input type="hidden" id="filename" name="filename" value="" /> 
                <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
                <div class="radioDiv">
                    <%--
                    <table>
                        <tr>
                            <td><input type="radio" Id="calendarizado" name="selRpt" value="calendarizado" checked></td>
                            <td>Calendarizado</td>
                        </tr>
                        <tr>
                            <td><input type="radio" Id="avance" name="selRpt" value="avance"></td>
                            <td>Calendarizado / Avance</td>
                        </tr>
                    </table>
                    --%>
                </div>
                <div>
                    <input id="allChecks" name="allChecks" onclick="allChecksRptRamPrgGrupPob()" checked="true" type="checkbox">
                </div>
                <table>
                    <tr>
                        <td> A&#241;o: </td>
                        <td>
                            <input min="0" id="yearCaptura" name="yearCaptura" value="2016" tabindex="1" onchange="cargaComboRamosbyYear()" type="number">
                        </td>
                    </tr>
                    <tr>
                        <td> Ramo: </td>

                        <td>
                            <select id="selRamo" name="selRamo" tabindex="2"  style="width: 460px;">
                                <option value="-1">
                                    -- Seleccione un ramo --
                                </option>
                            </select>
                        </td>
                    </tr>
                </table>
                <script>
                    cargaComboRamosbyYear();
                </script>
                <br>

            </form>
        </div>
        <div id="mensaje"></div>
        <jsp:include page="template/piePagina.jsp" />
    </body> 
</html>

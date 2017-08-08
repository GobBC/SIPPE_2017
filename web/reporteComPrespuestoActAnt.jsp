<%-- 
    Document   : reporteIngreosEgresos
    Created on : Jul 17, 2015, 1:55:03 PM
    Author     : ugarcia
--%>

<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comparativo de Presupuesto Actual con Anterior</title>
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
        List<Ramo> ramoList = new ArrayList<Ramo>();

        int year = 0;
        int contReg = 0;
        int contRegSelect = 0;
        String msjSelect = "Seleccionados";
        String tipoDependencia = new String();
        String usuario = new String();
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        ResultSQL resultSQL = new ResultSQL(tipoDependencia);
        resultSQL.setStrServer(((String) request.getHeader("Host")));
        resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSQL.resultSQLConecta(tipoDependencia);
        ramoList = resultSQL.getResultRamoByYear(year, usuario);
        resultSQL.resultSQLDesconecta();

        contReg = ramoList.size();
        contRegSelect = contReg;

        if (contReg == 1) {
            msjSelect = "Seleccionado";
        }

    %>
    <jsp:include page="template/encabezado.jsp" />

    <body>
        <div Id="TitProcess"><label >Comparativo de Presupuesto Actual con Anterior<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReporteIngresoEgreso('XLS')">
                    <br/> <small> Excel </small>
                </button>
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarReporteIngresoEgreso('PDF')">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <div class="center-div"> 
            <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <input type="hidden" id="filename" name="filename" value="<%="ComparativoIngresoEgreso" + File.separatorChar + "RptComparativoIngresoEgresoActualSiguiente.jasper"%>"/> 
                <input type="hidden" id="reporttype" name="reporttype" value="PDF"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
                <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
                <div id="divCombo" class="comboRamos">
                    <label> Ramo:</label>
                    <ul>
                        <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=contReg + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                            <ul>
                                <li>
                                    <div id="divRamos" name="divRamos" >
                                        <input type="checkbox" id="allChecks" name="allChecks" onclick="allChecksRptRamPrgGrupPob()" checked="true"/>Todos/Ninguno
                                        <table>
                                            <%
                                                int RegTemp = 0;
                                                for (Ramo ramo : ramoList) {
                                                    RegTemp++;
                                                    out.print("<tr >");
                                                    out.print("<td align='left' >");
                                                    out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrgGrupPob(" + RegTemp + ")' checked='true' />");
                                                    out.print("<input id='ramoCheck" + RegTemp + "' name='ramoCheck" + RegTemp + "' type='hidden'  value='" + ramo.getRamo() + "' />");
                                                    out.print("</td>");
                                                    out.print("<td align='left'  >");
                                                    out.print(ramo.getRamo() + "-" + ramo.getRamoDescr() + " ");
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                }
                                            %> 
                                        </table>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <div id="divInfoPlantilla" style="width: 1200px" > 
                    <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                    <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
                </div>
            </form>
        </div>
        <div id="mensaje"></div>
        <jsp:include page="template/piePagina.jsp" />
</html>

<%-- 
    Document   : reporteComparativoPresupuesto
    Created on : Julio 13 , 2015, 08:25:00 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.ReporteExcelBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
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
        <title>Reporte Comparativo de Presupuesto Actual Vs Anterior </title>
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
        AccionBean accionBean = null;
        try {
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
            accionBean = new AccionBean(tipoDependencia);
            accionBean.setStrServer(request.getHeader("host"));
            accionBean.setStrUbicacion(getServletContext().getRealPath(""));
            accionBean.resultSQLConecta(tipoDependencia);
            List<Partida> partidaList = new ArrayList<Partida>();

    %>
    <jsp:include page="template/encabezado.jsp" />

    <body>
        <div Id="TitProcess"><label >Reporte Comparativo de Presupuesto Actual Vs Anterior<label/></div>
        <div class="col-md-8 col-md-offset-2">
            <div class="botones-menu_bootstrap">
                <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReporteComparativoPresupuesto('XLS')">
                    <br/> <small> Excel </small>
                </button>
                <button type="button" class="btnbootstrap btn-reporte" onclick="generarReporteComparativoPresupuesto('PDF')">
                    <br/> <small> Reporte </small>
                </button>
                <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                    <br/> <small> Inicio </small>
                </button>
            </div>
        </div>
        <div class="center-div"> 
            <form id='frmActInfoRpt' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
                <input type="hidden" id="filename" name="filename" value="rptComparativoPresupuestoActVsSig.jasper"/> 
                <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
                <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
                <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
                <input type="hidden" id="selPartIni" name="selPartIni" value="0"/>
                <input type="hidden" id="selPartFin" name="selPartFin" value="0"/>
                <input type="hidden" id="queryPartidas" name="queryPartidas" value="0"/>

                <div class="radioDivComparativo">
                    <table>
                        <tr>
                            <td><input type="radio"  name="selRpt"  value="rptComparativoPptoActVsSigRamo.jasper" checked></td>
                            <td>Concentrado por Ramo</td>
                        </tr>
                        <tr>
                            <td><input type="radio"  name="selRpt"  value="rptComparativoPptoActVsSigPartida.jasper"></td>
                            <td>Concentrado por Partida</td>
                        </tr>
                        <tr>
                            <td><input type="radio"  name="selRpt"  value="rptComparativoPresupuestoActVsSig.jasper"></td>
                            <td>Concentrado por Ramo - Partida</td>
                        </tr>
                        <tr>
                            <td><input type="radio"  name="selRpt"  value="rptComparativoPresupuestoActVsSigRelLaboral.jasper"></td>
                            <td>Concentrado por Ramo - Partida - Relación Laboral</td>
                        </tr>
                    </table>
                </div>
                <br>
                <br>
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
                <table>
                    <tr><td><label>Partida inicial: </label></td>
                        <td>
                            <select id="selPartidaComp1"  name="selPartidaComp1" class="selPartidaComp">
                                <option value="-1">
                                    -- Seleccione una partida --
                                </option>
                                <%

                                    ReporteExcelBean reporteExcelBean = new ReporteExcelBean(tipoDependencia);
                                    reporteExcelBean.setStrServer(((String) request.getHeader("Host")));
                                    reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
                                    reporteExcelBean.resultSQLConecta(tipoDependencia);
                                    partidaList = reporteExcelBean.getPartidaList(year, tipoDependencia);

                                    for (Partida partida : partidaList) {
                                        out.print("<option value=\"" + partida.getPartidaId() + "\">");
                                        out.print(partida.getPartidaId() + "-" + partida.getPartida());
                                        out.print("</option>");
                                    }
                                %>
                            </select>           

                        </td>
                    </tr>
                </table>
                <br>
                <table>
                    <tr><td><label>Partida final: </label></td>
                        <td>
                            <select id="selPartidaComp2"  name="selPartidaComp2" class="selPartidaComp2">
                                <option value="-1">
                                    -- Seleccione una partida --
                                </option>
                                <%

                                    partidaList = accionBean.getPartidaList(year, tipoDependencia);

                                    for (Partida partida : partidaList) {
                                        out.print("<option value=\"" + partida.getPartidaId() + "\">");
                                        out.print(partida.getPartidaId() + "-" + partida.getPartida());
                                        out.print("</option>");
                                    }
                                %>
                            </select>           

                        </td>
                    </tr>
                </table>



                <div id="divInfoPlantilla" style="width: 1200px" > 
                    <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                    <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
                </div>
            </form>
        </div>
        <div id="mensaje"></div>
        <jsp:include page="template/piePagina.jsp" />
        <%} catch (Exception ex) {
                Bitacora bitacora = new Bitacora();
                bitacora.setStrServer(request.getHeader("host"));
                bitacora.setStrUbicacion(getServletContext().getRealPath(""));
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, request.getServletPath());
                bitacora.grabaBitacora();
            } finally {
                if (accionBean != null) {
                    accionBean.resultSQLDesconecta();
                }
            }%>
    </body>     
</html>

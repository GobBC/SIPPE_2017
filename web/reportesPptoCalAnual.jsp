<%-- 
    Document   : reportesPptoCalAnual
    Created on : Sep 3, 2015, 1:31:30 PM
    Author     : Jarguelles
--%>

<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.aplicacion.ReporteExcelBean"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<Partida> partidaList = new ArrayList<Partida>();
        int contReg = 0;
        int contRegSelect = 0;
        String msjSelect = "Seleccionados";
        int year = 0;
        String usuario = new String();
        String tipoDependencia = new String();
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        ReporteExcelBean reporteExcelBean = new ReporteExcelBean(tipoDependencia);
        reporteExcelBean.setStrServer(((String) request.getHeader("Host")));
        reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        reporteExcelBean.resultSQLConecta(tipoDependencia);
        ramoList = reporteExcelBean.getResultRamoByYear(year, usuario);
        partidaList = reporteExcelBean.getPartidaList(year, tipoDependencia);
        reporteExcelBean.resultSQLDesconecta();
        contReg = ramoList.size();
        contRegSelect = contReg;

        if (contReg == 1) {
            msjSelect = "Seleccionado";
        }
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >PRESUPUESTO ASIGNADO<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-reporte" onclick="generarReportePpptoCalAnual()">
                <br/> <small> Reporte </small>
            </button>
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReportePpptoCalAnualExcel()">
                <br/> <small> Excel </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>    
    <div class="center-div" style="width: 62%;"> 

        <form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
            <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
            <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
            <div id="btnSeleccionRpt">
                <label id='lbCalAnual' >Agrupado por</label>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoRam" value="rptPptoRam.jasper" checked>
                    <label for="chkRptPptoRam" >Ramo</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoRamPart" value="rptPptoRamPart.jasper" >
                    <label for="chkRptPptoRamPart" >Ramo - Partida</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoRamPrgPart" value="rptPptoRamPrgPart.jasper" />
                    <label for="chkRptPptoRamPrgPart">Ramo - Programa - Partida</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoPart" value="rptPptoPart.jasper" />
                    <label for="chkRptPptoPart">Partida</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoRamDepto" value="rptPptoRamDepto.jasper" />
                    <label for="chkRptPptoRamDepto">Ramo - Departamento</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoRamFuenteFinanciamiento" value="rptPptoRamFuenteFinanciamiento.jasper" />
                    <label for="chkRptPptoRamFuenteFinanciamiento">Ramo - Fuente Financiamiento</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoCodigos" value="rptPptoCodigos.jasper"/>
                    <label for="chkRptPptoCodigos">C&oacute;digo</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoGpo" value="rptPptoRamGpo.jasper" >
                    <label for="chkRptPptoGpo">Ramo - Cap&iacute;tulo </label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoRamPrgGpo" value="rptPptoRamPrgGpo.jasper" />
                    <label for="chkRptPptoRamPrgGpo">Ramo - Programa - Capítulo</label>
                </div>                         
                <label id='lbCalAnual' >Tipo de reporte</label>
                <div>
                    <input type="radio" name='tipoGrupo' id="chkTipoRptCalendarizado" value="calendarizado" checked />
                    <label for="chkTipoRptCalendarizado">Calendarizado</label>
                </div> 
                <div>
                    <input type="radio" name='tipoGrupo' id="chkTipoRptAnual" value="anual" />
                    <label for="chkTipoRptAnual">Anual</label>
                </div> 
                <div>
                    <input type="checkbox" name='pptoModAut' id="chkPptoModAut" value="ACT" checked />
                    <label for="chkPptoModAut">Incluir presupuesto modificado autorizado</label>
                </div> 
            </div>
            </br>
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
                                                out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrg(" + RegTemp + ")' checked='true' />");
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
            <table id="filtrosRpt">
                <tr>
                    <td><label> Programa inicial </label></td>
                    <td>
                        <select id="selProgramaI" name="selProgramaI" class='selRangoPrograma' onchange="getDeptoByProgramaI();">
                            <option value="-1">-- Selecciona un programa --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label> Programa final   </label></td>
                    <td>
                        <select id="selProgramaF" name="selProgramaF" class='selRangoPrograma' onchange="getDeptoByProgramaF();">
                            <option value="-1">-- Selecciona un programa --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label> Depto. inicial </label></td>
                    <td>
                        <select id="selDeptoI" name="selDeptoI" class='selRangoDepto' onchange="validaMayorInicial()">
                            <option value="-1">-- Selecciona un departamento --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label> Depto. final </label></td>
                    <td>
                        <select id="selDeptoF" name="selDeptoF" class='selRangoDepto' onchange="validaMayorInicial()">
                            <option value="-1">-- Selecciona un departamento --</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Partida inicial: </label></td>
                    <td>
                        <select id="selPartIni"  name="selPartIni" class="selPartidaComp" onchange="validaPartidaMenor()">
                            <option value="-1">
                                -- Seleccione una partida --
                            </option>
                            <%
                                for (Partida partida : partidaList) {
                                    out.print("<option value=\"" + partida.getPartidaId() + "\">");
                                    out.print(partida.getPartidaId() + "-" + partida.getPartida());
                                    out.print("</option>");
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label>Partida final: </label></td>
                    <td>
                        <select id="selPartFin"  name="selPartFin" class="selPartidaComp" onchange="validaPartidaMayor()">
                            <option value="-1">
                                -- Seleccione una partida --
                            </option>
                            <%
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
    <jsp:include page="template/piePagina.jsp" />
</html>

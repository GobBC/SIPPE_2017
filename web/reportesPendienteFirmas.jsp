<%-- 
    Document   : reportesMonitoreoPresupuestal
    Created on : Abr 14, 2016, 3:01:06 PM
    Author     : jarguelles
--%>


<%@page import="gob.gbc.aplicacion.ParametroBean"%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.entidades.EstatusMovReporte" %>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.ReporteExcelBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Programa Operativo Anual</title>
    </head>
    <%
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }

        ParametroBean parametroBean = null;

        int isNormativo = 0;

        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<EstatusMovReporte> estatusMovList = new ArrayList<EstatusMovReporte>();

        String usuario = new String();
        String disabled = new String();
        String reportePOA = new String();
        String msjSelect = "Seleccionados";
        String tipoDependencia = new String();
        String checado = new String();

        int rolUsuario = 0;
        int year = 0;
        int contRegSelect1 = 0;
        int contReg = 0;
        int contRegSelect = 0;
        int contReg1 = 0;
        String ramoSession = new String();
        String checadoNivel = new String();
        boolean cerrado = true;
        boolean parametro = true;

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        ReporteExcelBean reporteExcelBean = new ReporteExcelBean(tipoDependencia);
        reporteExcelBean.setStrServer((request.getHeader("Host")));
        reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        reporteExcelBean.resultSQLConecta(tipoDependencia);

        ramoList = reporteExcelBean.getResultRamoByYear(year, usuario);
        estatusMovList = reporteExcelBean.getResultSQLGetEstatusMovReporte();

        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rolUsuario = Integer.parseInt((String) session.getAttribute("strRol"));
        }

        isNormativo = reporteExcelBean.getRolNormativo(rolUsuario);

        reporteExcelBean.resultSQLDesconecta();

        contReg = ramoList.size();
        contRegSelect = contReg;

        contReg1 = estatusMovList.size();
        contRegSelect1 = contReg1;

        if (isNormativo == 1) {
            checado = " checked ";
            checadoNivel = "";
        } else {
            checado = " disabled ";
            checadoNivel = " checked ";
        }

    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label>REPORTES PENDIENTES POR RAMO <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button id="botonPdf" type="submit" class="btnbootstrap btn-Excel" onclick="generarReportePendientesxDepPdf(2)" >
                <br/> <small> Excel </small>
            </button>
            <button id="botonPdf" type="submit" class="btnbootstrap btn-reporte" onclick="generarReportePendientesxDepPdf(1)" >
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div"> 
        <form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">

            <input type="hidden" id='reporttype' name='reporttype' value="pdf" />
            <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
            <input type="hidden" id="NivelesInList" name="NivelesInList" value="0"/>
            <input type="hidden" id="OpcionRepDep" name="OpcionRepDep" value="0"/>
            <input type="hidden" id="tipoReporte" name="tipoReporte" value="0"/>
            <input type="hidden" id="periodoIni" name="periodoIni" value="1"/>
            <input type="hidden" id="periodoFin" name="periodoFin" value="12"/>
            <input type="hidden" id="rptPath" name="rptPath" value="repPendientesDependencia"/>
            <input type="hidden" id="filename" name="filename" value="<%="repPendientesDependencia" + File.separatorChar + "rptPendientesDependencia.jasper"%>"/>

            <div id="btnSeleccionReporte">
                <div id="xRamo">
                    <input name="radioReporte" type="radio" id="radioReportePxDep" 
                           onChange="cambiaTipoRep('1')" <%=checado%>/>
                    <label for="radioReportePxDep">Por Ramo </label>
                </div>
                <div id="xNivelAut" >
                    <input name="radioReporte" type="radio" id="radioReportePxDepFirma" 
                           onChange="cambiaTipoRep('2')" <%=checadoNivel%>/>
                    <label for="radioReportePxDepFirma">Por Nivel de Autorizacion </label>
                </div>
            </div>  

            <br> 
            <br> 

            <div id="divCombo" class="comboRamos">
                <label> Ramo:</label>
                <ul>
                    <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=contReg + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                        <ul>
                            <li>
                                <div id="divRamos" name="divRamos"  >
                                    <input type="checkbox" id="allChecks" name="allChecks" onclick="allChecksRptRamPrgGrupPob()" checked="true"/>Todos/Ninguno
                                    <table>
                                        <%
                                            int RegTemp = 0;
                                            for (Ramo ramo : ramoList) {
                                                RegTemp++;

                                                out.print("<tr >");
                                                out.print("<td align='left' >");
                                                out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRptRamPrgMonitoreo(" + RegTemp + ")' checked='true' />");
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


            <div id="divCombo1" class="comboRamos">
               <label> Nivel:</label>
                <ul>
                    <li><div id="comboSelectEstatus" name="comboSelectEstatus" ><input id="labelCont1" name="labelCont1" type="Text" value="<%=contReg1 + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                        <ul>
                            <li>
                                <div id="divNiveles" name="divNiveles"  >
                                    <input type="checkbox" id="allChecks1" name="allChecks1" onclick="allChecksRptEstatusNivel()" checked="true"/>Todos/Ninguno
                                    <table>
                                        <%
                                            int RegTemp1 = 1;
                                            for (EstatusMovReporte estatus : estatusMovList) {
                                                RegTemp1++;
                                                out.print("<tr >");
                                                out.print("<td align='left' >");
                                                out.print("<input id='checks" + RegTemp1 + "' name='checks" + RegTemp1 + "' type='checkbox' onclick='contCheckNiveles(" + RegTemp1 + ")' checked='true' />");
                                                out.print("<input id='NivelCheck1" + RegTemp1 + "' name='NivelCheck1" + RegTemp1 + "' type='hidden'  value='" + estatus.getOrden() + "' />");
                                                out.print("</td>");
                                                out.print("<td align='left'  >");
                                                out.print(estatus.getEstatusMovId() + "-" + estatus.getEstatusMov() + " ");
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

            <br/>                        
            <br/> 
            <br/> 
            <br/> 


            <div id="divInfoPlantilla" style="width: 1200px" > 
                <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
                <input id='contReg1' name='contReg' type='hidden' value='<%=contReg1%>'/>
                <input id='contRegSelect1' name='contRegSelect' type='hidden' value='<%=contRegSelect1%>' />
                <input id='esNormativo' name='esNormativo' type='hidden' value='<%=isNormativo%>' />

            </div>

        </form>
    </div>
    <%
        if (isNormativo == 1) {
    %>
    <script>
        cambiaTipoRep(1);
    </script>
    <%
    } else {
    %>
    <script>
        cambiaTipoRep(2);
    </script><%
        }
    %>
    <jsp:include page="template/piePagina.jsp"/>
</html>

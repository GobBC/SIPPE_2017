<%-- 
    Document   : reportesExcel
    Created on : Jun 9, 2015, 1:31:30 PM
    Author     : SYSTEM
--%>

<%@page import="gob.gbc.aplicacion.ReporteExcelBean"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Expires", "0");

        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }

        List<Ramo> ramoList = new ArrayList<Ramo>();
        int contReg = 0;
        int contRegSelect = 0;
        String msjSelect = "Seleccionados";
        int contRegProgramas = 0;
        int contRegSelectProgramas = 0;
        String msjSelectProgramas = "Seleccionados";
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
        reporteExcelBean.resultSQLDesconecta();
        contReg = ramoList.size();
        contRegSelect = contReg;

        if (contReg == 1) {
            msjSelect = "Seleccionado";
        }

        ReporteExcelBean reporteExcelBeanP = new ReporteExcelBean(tipoDependencia);
        reporteExcelBean.setStrServer(((String) request.getHeader("Host")));
        reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        reporteExcelBean.resultSQLConecta(tipoDependencia);
        ramoList = reporteExcelBean.getResultRamoByYear(year, usuario);
        reporteExcelBean.resultSQLDesconecta();
        contRegProgramas = ramoList.size();
        contRegSelectProgramas = contRegProgramas;

        if (contRegProgramas == 1) {
            msjSelectProgramas = "Seleccionado";
        }
    %>
    <head>    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <meta http-equiv="Pragma" content="no-cache"> 
        <meta http-equiv="Expires" content="-1">
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >Reportes en excel <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReporteExcel()">
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>    
    <div class="center-div"> 
        <form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
            <input type="hidden" name='reporttype' value="xls" />
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
            <input type="hidden" id="ramoInList" name="ramoInList" value=""/>
            <input type="hidden" id="programaInList" name="programaInList" value=""/>
            <input type="hidden" id="proyectoInList" name="proyectoInList" value=""/>
            <input type="hidden" id="reportesExcel" name="reportesExcel" value="1"/>
            <div id="btnSeleccionRpt">
                <div>
                    <input type="radio" name='filename' id="chkCodigo" value='reportesExcel/repPptoXCodigoExce.jasper' onclick="ocultarCheckMovimientos();ocultarCheckMovimientosMetas()" checked>
                    <label for="chkCodigo">Presupuesto por c&oacute;digo program&aacute;tico</label>
                </div>
                <!--Inicia original sin modificado-->
                <div>
                    <input type="radio" name='filename' id="chkConByMetaSM" value='reportesExcel/rptConsolidadoPorMetaSM.jasper' onclick="ocultarCheckMovimientos();ocultarCheckMovimientosMetas()">
                    <label for="chkConByMetaSM">Presupuesto Consolidado por Meta</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkConByAccionSM" value='reportesExcel/rptConsolidadoPorAccionSM.jasper' onclick="ocultarCheckMovimientos();ocultarCheckMovimientosMetas()">
                    <label for="chkConByAccionSM">Presupuesto Consolidado por Acci&oacute;n</label>
                </div>
                <!--Termina original sin modificado-->
                <div>
                    <input type="radio" name='filename' id="chkCodModExce" value='reportesExcel/repPptoXCodigoModifExce.jasper' onclick="ocultarCheckMovimientos();ocultarCheckMovimientosMetas()" >
                    <label for="chkCodModExce">Presupuesto Modificado por c&oacute;digo program&aacute;tico</label>
                </div>

                <div>
                    <input type="radio" name='filename' id="chkCodByMeta" value='reportesExcel/rptConsolidadoPorMeta.jasper' onclick="ocultarCheckMovimientos();ocultarCheckMovimientosMetas()">
                    <label for="chkCodByMeta">Presupuesto Modificado Consolidado por Meta</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkCodByAccion" value='reportesExcel/rptConsolidadoPorAccion.jasper' onclick="ocultarCheckMovimientos();ocultarCheckMovimientosMetas()">
                    <label for="chkCodByAccion">Presupuesto Modificado Consolidado por Acci&oacute;n</label>
                </div>
                <%--
                <div>
                    <input type="radio" name='filename' id="chkDetalleAccion" value="reportesExcel/rptPresupuestoPorDetalleAccion.jasper" />
                    <label for="chkDetalleAccion">Presupuesto por detalle de acci&oacute;n</label>
                </div>
                --%>
                <div>
                    <input type="radio" name='filename' id="chkDetalleMetas" value="reportesExcel/rptDetalleMetas.jasper" onclick="ocultarCheckMovimientos();mostrarCheckMovimientosMetas()" />
                    <label for="chkDetalleMetas">Detalle de metas</label>
                     <div id="div-MovtosPresMetas" style="display: none" >
                        <label for="inpMovtosPresMetas"> Considerar movimientos presupuestales </label>
                        <input id="inpMovtosPresMetas" name="movtosPrespMetas" type="checkbox" value="1" />
                    </div>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkDetalleAcciones" value="reportesExcel/rptDetalleAcciones.jasper" onclick="mostrarCheckMovimientos();ocultarCheckMovimientosMetas()" />
                    <label for="chkDetalleAcciones">Detalle de acciones</label>
                    <div id="div-MovtosPres" style="display: none" >
                        <label for="inpMovtosPres"> Considerar movimientos presupuestales </label>
                        <input id="inpMovtosPres" name="movtosPresp" type="checkbox" value="1" />
                    </div>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkDetalleRequerimientos" value="reportesExcel/rptPptoXDetalleRequerimientos.jasper" onclick="ocultarCheckMovimientos();ocultarCheckMovimientosMetas()"/>
                    <label for="chkDetalleRequerimientos">Detalle de requerimientos</label>
                </div>
            </div>
            </br>
            <input type="hidden" id="filename" name="filename" value="reportes\rptRamoProgramaGrupoPoblacional.jasper"/> 
            <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>

            <div id="divCombo" class="comboRamos">
                <label> Ramo:</label>

                <ul>
                    <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=0 + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                        <ul>
                            <li>
                                <div id="divRamos" name="divRamos" >
                                    <input type="checkbox" id="allChecks" name="allChecks" onclick="allChecksRamosRptExcel()" />Todos/Ninguno
                                    <table>
                                        <%
                                            int RegTemp = 0;
                                            for (Ramo ramo : ramoList) {
                                                RegTemp++;
                                                out.print("<tr >");
                                                out.print("<td align='left' >");
                                                out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='contCheckRamosRptExcel(" + RegTemp + ")'  />");
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

            <div id="divComboProgramas" class="comboProgramas">

            </div>      

            <div id="divComboProyectos" class="comboProyectos">

            </div>    

            <div id="divInfoPlantilla" style="width: 1200px" > 
                <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='0' />
            </div>            
        </form>
        <div id="mensaje"></div>
    </div>
    <jsp:include page="template/piePagina.jsp" />
    <script type="text/javascript">
        $(document).ready(function() {
            $.ajaxSetup({ cache: false }); 
        });
    </script>
</html>

<%-- 
    Document   : reportesPOA
    Created on : Jun 11, 2015, 3:19:06 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.ParametroBean"%>
<%@page import="java.io.File"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.ReporteExcelBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }
        List<Ramo> ramoList = new ArrayList<Ramo>();
        int contReg = 0;
        int contRegSelect = 0;
        String msjSelect = "Seleccionados";
        String tipoDependencia = new String();
        String reportePOA = new String();
        int year = 0;
        int congelado = 0;
        String usuario = new String();
        String disabled = new String();
        ParametroBean parametroBean = null;
        Boolean cerrado = true;
        boolean parametro = true;


        if (session.getAttribute(
                "year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (session.getAttribute(
                "strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }

        if (session.getAttribute(
                "tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        ReporteExcelBean reporteExcelBean = new ReporteExcelBean(tipoDependencia);

        reporteExcelBean.setStrServer(
                ((String) request.getHeader("Host")));
        reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        reporteExcelBean.resultSQLConecta(tipoDependencia);
        ramoList = reporteExcelBean.getResultRamoByYear(year, usuario);
        parametro = reporteExcelBean.getParametroIndicadores();
        congelado = reporteExcelBean.getCountPresupuestoCongelado(year);
        reporteExcelBean.resultSQLDesconecta();
        contReg = ramoList.size();
        contRegSelect = contReg;
        if(parametro){
            reportePOA = "RptPOAweb.jasper";
        }else{
            reportePOA = "RptPOANoIndicadores.jasper";
        }
        if (contReg
                == 1) {
            msjSelect = "Seleccionado";
        }
        
        parametroBean = new ParametroBean(tipoDependencia);
        parametroBean.setStrServer(request.getHeader("host"));
        parametroBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        parametroBean.resultSQLConecta(tipoDependencia);
        cerrado = parametroBean.validarCierreEjercicio(year);
        parametroBean.resultSQLDesconecta();

        if (!cerrado) {
            disabled = "disabled";
        } else {
            disabled = "";
        }
        
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label >Reportes POA <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReportePOAExcel()">
                <br/> <small> Excel </small>
            </button>
            <button type="submit" class="btnbootstrap btn-reporte" onclick="generarReportePOA()">
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
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
            <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
            <input type="hidden" id="tipoReporte" name="tipoReporte" value="0"/>
            <input type="hidden" id="costoMeta" name="costoMeta" value="0"/>
            <input type="hidden" id="periodo" name="periodo" value="0"/>
            <div id="btnSeleccionReporte">
                <div>
                    <input type="radio" name='filename' id="chkCodigo" value='<%=reportePOA%>' 
                           onclick="chkReporteSinPeriodo()" checked>
                    <label for="chkCodigo">Programado</label>
                    <div id="div-costo" >
                        <label> Costo: </label>
                        <input id="inp-costo" name="costo" type="checkbox" value="1" />
                    </div>
                </div>
                <%if(congelado > 0){%>
                </div>
                    <input type="radio" name='filename' id="chkProgramadoAutorizado" value="<%="rptProgramadoCongelado" + File.separatorChar + "RptPOAweb.jasper"%>" 
                           onclick="chkReporteSinPeriodo()"/>
                    <label for="chkProgramadoAutorizado">Programado Autorizado</label>
                <div>
                <%}%>
                <div>
                    <input type="radio" name='filename' id="chkDetalleAccion" value="<%="reporteMasivo" + File.separatorChar + "rptProgOperativoAnualMasivo.jasper"%>" 
                           onclick="chkReporteSinPeriodo()"/>
                    <label for="chkDetalleAccion">Detalle de requerimientos</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkDetalleMetas" value="<%="reporteMasivo" + File.separatorChar + "rptDetalleMetas.jasper"%>" 
                           onclick="chkReporteSinPeriodo()"/>
                    <label for="chkDetalleMetas">Detalle de metas</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkAvanceAccion" value="<%="reporteMasivo" + File.separatorChar + "rptAvanceAccionMasivo.jasper"%>" 
                           onclick="chkReporteSinPeriodo()"/>
                    <label for="chkAvanceAccion">Avance acción masivo</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkAvanceTrimestral" value="<%="MasivoTrimestre" + File.separatorChar + "rptAvancePoaTrimestre.jasper"%>" 
                           onclick="seleccionaMasivoTrimestre()"/>
                    <label for="chkAvanceTrimestral">Avance masivo por trimestre</label>
                    <div id="div-periodo" style="display: none">
                        <label> Periodo: </label>
                        <input id="inp-periodo" name="periodo" type="text" value="1" disabled="true"/>
                        <input id="btn-decr" type="button" value="-" onclick="decrPer()"/>
                        <input id="btn-incr" type="button" value="+" onclick="incrPer()"/>
                    </div>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkAvanceAnual" value="<%="avanceAnual" + File.separatorChar + "rptAvanceAnual.jasper"%>" 
                           onclick="seleccionaAvanceAnual()"  <%=disabled%>      />
                    <label for="chkAvanceAnual">Cierre de ejercicio</label>                
                </div>
            </div>

            </br> 
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
            <div id="selPrgIF">
                <div id="divPrgI">
                    <label> Programa inicial </label>
                    <select id="selProgI" name="selProgramaI" class='selRangoPrograma' onchange="validaMenorFinal()">
                        <option value="-1">-- Selecciona un programa inicial --</option>
                    </select>
                </div> 
                <div id="divPrgF">
                    <label> Programa final &nbsp;  </label>
                    <select id="selProgF" name="selProgramaF" class='selRangoPrograma' onchange="validaMayorInicial()">
                        <option value="-1">-- Selecciona un programa final --</option>
                    </select>
                </div>
            </div>
            <div id="divInfoPlantilla" style="width: 1200px" > 
                <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
            </div>
        </form>
    </div>
    <jsp:include page="template/piePagina.jsp"/>
</html>

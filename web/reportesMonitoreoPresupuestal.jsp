<%-- 
    Document   : reportesMonitoreoPresupuestal
    Created on : Abr 14, 2016, 3:01:06 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.FolioMonitoreoPresup"%>
<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="gob.gbc.entidades.Programa"%>
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

        ParametroBean parametroBean = null;

        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<Partida> partidaList = new ArrayList<Partida>();
        List<FolioMonitoreoPresup> folioList = new ArrayList<FolioMonitoreoPresup>();

        String usuario = new String();
        String disabled = new String();
        String reportePOA = new String();
        String msjSelect = "Seleccionados";
        String tipoDependencia = new String();

        int year = 0;
        int contReg = 0;
        int contRegSelect = 0;

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
        reporteExcelBean.setStrServer(( request.getHeader("Host")));
        reporteExcelBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        reporteExcelBean.resultSQLConecta(tipoDependencia);

        ramoList = reporteExcelBean.getResultRamoByYear(year, usuario);
        partidaList = reporteExcelBean.getResultSQLGetPartidasGeneral(year);
        
        reporteExcelBean.resultSQLDesconecta();

        contReg = ramoList.size();
        contRegSelect = contReg;

        

    %>
    
    <jsp:include page="template/encabezado.jsp" />
    
    <script langauge="javascript">
        //Inicializar valores del filtro por rango de folios
        $( document ).ready(function() {
    validaConsultaxRangoFolios(0,'');
        });
        
    </script>
    
    <div Id="TitProcess"><label>REPORTES MONITOREO PRESUPUESTAL<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReporteMonitoreoPresupuestalExcel()">
                <br/> <small> Excel </small>
            </button>
           <!-- <button id="botonPdf" type="submit" class="btnbootstrap btn-reporte" onclick="generarReporteMonitoreoPresupuestalPdf()" disabled="true">
                <br/> <small> Reporte </small>
            </button>-->
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div"> 
        <form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">

            <input type="hidden" id='reporttype' name='reporttype' value="pdf" />
            <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
            <input type="hidden" id="folioInList" name="folioInList" value="0"/>
            <input type="hidden" id="tipoReporte" name="tipoReporte" value="5"/>
            <input type="hidden" id="periodoIni" name="periodoIni" value="1"/>
            <input type="hidden" id="periodoFin" name="periodoFin" value="12"/>
            <input type="hidden" id="rptPath" name="rptPath" value=""/>
            <input type="hidden" id="isConsultaxRangoFolios" name="isConsultaxRangoFolios" value=""/>

            <div id="btnSeleccionReporte">
              
                    <div>
                        <input type="radio" name='filename' id="radioReporteRecalendarizacionesConsolidado" 
                               value="<%="ConsolidadoControlRecalendarizaciones" + File.separatorChar + "rptConsolidadoControlRecalendarizaciones.jasper"%>"
                               onChange="cambiaDirectorioReporte('hiddenPdf','1')"  onclick="LimpiaFoliosFiltroRep();validaConsultaxRangoFolios(2,'')" checked />
                        <label for="radioReporteRecalendarizacionesConsolidado">Reporte Consolidado de Recalendarizaciones</label>
                    </div>
                    <div>
                        <input type="radio" name='filename' id="radioReporteRecalendarizacionesConsolidadoConcentrado" 
                               value="<%="ConsolidadoControlRecalendarizaciones" + File.separatorChar + "rptConsolidadoControlRecalConcentrado.jasper"%>"
                               onChange="cambiaDirectorioReporte('hiddenPdf','1')"  onclick="LimpiaFoliosFiltroRep();validaConsultaxRangoFolios(1,'');" />
                        <label for="radioReporteRecalendarizacionesConsolidadoConcentrado">Reporte Concentrado de Recalendarizaciones</label>
                    </div>
                               <div id="opcionConcentradoRecal" >
                                   &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="opCon" id="R" value="rptConsolidadoControlRecalConcentradoRamo.jasper" checked>Ramo   
                                   &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="opCon" id="P" value="rptConsolidadoControlRecalConcentradoRamoPrg.jasper">Ramo-Programa
                                   &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="opCon" id="D" value="rptConsolidadoControlRecalConcentrado.jasper">Ramo-Prg-Depto
                                   
                               </div>
                    <div>
                        <input type="radio" name='filename' id="radioReporteTransferencias" 
                               value="<%="MonitoreoPresupuestalTransferencias" + File.separatorChar + "rptMonitoreoPresupuestalTransferenciasExcel.jasper"%>"
                               onChange="cambiaDirectorioReporte('hiddenPdf','1')" onclick="LimpiaFoliosFiltroRep();validaConsultaxRangoFolios(2,'')"/>
                        <label for="radioReporteTransferencias">Reporte de Transferencias</label>
                    </div>
                    <div>
                        <input type="radio" name='filename' id="radioReporteAmpliaciones" 
                               value="<%="MonitoreoPresupuestalAmpliaciones" + File.separatorChar + "rptMonitoreoPresupuestalAmpliacionesExcel.jasper"%>"
                               onChange="cambiaDirectorioReporte('hiddenPdf', '1')" onclick="LimpiaFoliosFiltroRep();validaConsultaxRangoFolios(2,'')"/>
                        <label for="radioReporteAmpliaciones">Reporte de Ampliaciones\Reducciones</label>
                    </div>
              
           
           </div>  
           
          <br> 

            <div id="periodoIF" >
                <label> Periodo Inicial: </label>
                <input id="periodoIniV" name="periodoIniV" type="text" value="1" disabled="true" />
                <input id="btn-decr" type="button" value="-" onclick="disminuirPeriodoIniMonitoreo()"/>
                <input id="btn-incr" type="button" value="+" onclick="aumentarPeriodoIniMonitoreo()"/>
                <br>
                <label> Periodo Final: </label>
                <input id="periodoFinV" name="periodoFinV" type="text" value="12" disabled="true" />
                <input id="btn-decr" type="button" value="-" onclick="disminuirPeriodoFinMonitoreo()"/>
                <input id="btn-incr" type="button" value="+" onclick="aumentarPeriodoFinMonitoreo()"/>
            </div>

            <br> 

            <div id="divCombo" class="comboRamos">
                <label> Ramo:</label>
                <ul>
                    <li><div id="comboSelect" name="comboSelect" ><input id="labelCont" name="labelCont" type="Text" value="<%=contReg + " " + msjSelect%>" disabled="true"/><img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" ></div>
                        <ul>
                            <li>
                                <div id="divRamos" name="divRamos"  >
                                    <input type="checkbox" id="allChecks" name="allChecks" onclick="LimpiaFoliosFiltroRep();allChecksRptRamPrgGrupPob()" checked="true"/>Todos/Ninguno
                                    <table>
                                        <%
                                            int RegTemp = 0;
                                            for (Ramo ramo : ramoList) {
                                                RegTemp++;
                                                out.print("<tr >");
                                                out.print("<td align='left' >");
                                                out.print("<input id='check" + RegTemp + "' name='check" + RegTemp + "' type='checkbox' onclick='LimpiaFoliosFiltroRep();contCheckRptRamPrgMonitoreo(" + RegTemp + ")' checked='true' />");
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
            <div id="mensajeBuscaFolios"></div>
             <!--
            <div style="/* padding:10px; width:300px;border-top-style: solid;border-right-style: solid; border-bottom-style: solid; border-left-style: solid;*/ ">
             <input type="checkbox" value="xFolios" id="chkXfolios" name="chkXfolios" onclick="validaConsultaxRangoFolios(3,'')">&nbsp;
             <label id="tituloFiltroxRangFolios">Consultar por rango de folios</label><br>
             <label>Folio del &nbsp;</label>
             <input type="text" name="folioIni" id="folioIni" maxlength="10"  onkeypress="return validaConsultaxRangoFolios(4,event)" style=" width:90px;" > 
             <label>al</label>&nbsp; 
             <input type="text" name="folioFin" id="folioFin" maxlength="10" onkeypress="return validaConsultaxRangoFolios(4,event)" style=" width:90px;">
            </div>
              -->  <br>
              <input type="checkbox" value="xFolios" id="chkXfolios" name="chkXfolios" onclick="validaConsultaxRangoFolios(3,'')">&nbsp;
             <label id="tituloFiltroxRangFolios">Consultar por rango de folios</label><br>
             <div id="resultFolios" name="resultFolios"></div>              
              
              <br>
                 <div id="selPrgIF">
                <div id="divPrgI">
                    <label> Programa inicial </label>
                    <select id="selProgI" name="selProgramaI" class='selRangoPrograma' onchange="validaProgramaMenorFinalMonitoreo()">
                        <option value="-1">-- Selecciona un programa inicial --</option>
                    </select>
                </div> 
                <br> 
                <div id="divPrgF">
                    <label> Programa final &nbsp;  </label>
                    <select id="selProgF" name="selProgramaF" class='selRangoPrograma' onchange="validaProgramaMayorInicialMonitoreo()">
                        <option value="-1">-- Selecciona un programa final --</option>
                    </select>
                </div>
            </div>

            <br>                        

            <div id="selDeptoIF">
                <div id="divDeptoI">
                    <label> Departamento inicial </label>
                    <select id="selDeptoI" name="selDeptoI" class='selRangoDepartamento' onchange="validaDepartamentoMenorFinalMonitoreo()">
                        <option value="-1">-- Selecciona un departamento inicial --</option>
                    </select>
                </div> 
                <br> 
                <div id="divDeptoF">
                    <label> Departamento final  </label>
                    <select id="selDeptoF" name="selDeptoF" class='selRangoDepartamento' onchange="validaDepartamentoMayorInicialMonitoreo()">
                        <option value="-1">-- Selecciona un departamento final --</option>
                    </select>
                </div>
            </div> 
            <br> 
            <div id="selPartidaIF">
                <div id="divPartidaI">
                    <label> Partida inicial</label>
                    <select id="selPartidaI" name="selPartidaI" class='selRangoPartida' style="width: 400px" onchange="validaPartidaMenorFinalMonitoreo()">
                        <option value="-1">-- Selecciona una partida inicial --</option>
                        <%
                            for(Partida partida : partidaList){
                                out.write("<option value=\""+partida.getPartidaId()+"\" >"+partida.getPartidaId()+"-"+partida.getPartida()+"</option>");
                            }
                        %>
                    </select>
                </div> 
                <br> 
                <div id="divPartidaF">
                    <label> Partida final  </label>
                    <select id="selPartidaF" name="selPartidaF" class='selRangoPartida' style="width: 400px" onchange="validaPartidaMayorInicialMonitoreo()">
                        <option value="-1">-- Selecciona una partida final --</option>
                        <%
                            for(Partida partida : partidaList){
                                out.write("<option value=\""+partida.getPartidaId()+"\" >"+partida.getPartidaId()+"-"+partida.getPartida()+"</option>");
                            }
                        %>
                    </select>
                </div>
            </div> 

            <div id="divInfoPlantilla" style="width: 1200px" > 
                <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
               
            </div>
            <script>
                $("#opcionConcentradoRecal").css("display","none");
            </script>
        </form>
    </div>
    <jsp:include page="template/piePagina.jsp"/>
</html>

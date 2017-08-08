<%-- 
    Document   : reportesPOA
    Created on : Jun 11, 2015, 3:19:06 PM
    Author     : ugarcia
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.entidades.EstatusMovReporte"%>
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
        List<EstatusMovReporte> estatusMovList = new ArrayList<EstatusMovReporte>();

        int contReg = 0;
        int contRegSelect = 0;
        int contReg1 = 0;
        int contRegSelect1 = 0;
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
       String fechaSession = new String();
       SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
       SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yy");
       fechaSession= "";//df.format(formatter.parse("01/01/2009"));

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
          estatusMovList = reporteExcelBean.getResultSQLGetEstatusMovReporte();
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
        
         contReg1 = estatusMovList.size()+1 ;
        contRegSelect1 = contReg1;
    %>
  
    <jsp:include page="template/encabezado.jsp" />
      
    <div Id="TitProcess"><label >Reporte Estad&iacute;stica de Firma de folios <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReporteEstadisticaFirmaFoliosExcel()">
                <br/> <small> Excel </small>
            </button>
            <!--<button type="submit" class="btnbootstrap btn-reporte" onclick="generarReportePOA()">
                <br/> <small> Reporte </small>
            </button>-->
            
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
     <div class="center-div"> 
        <form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
            <input type="hidden" id='reporttype' name='reporttype' value="xls" />
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="1"/>
            <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
            <input type="hidden" id="NivelesInList" name="NivelesInList" value="0"/>
            <input type="hidden" id="tipoReporte" name="tipoReporte" value="0"/>
            <input type="hidden" id="costoMeta" name="costoMeta" value="0"/>
            <input type="hidden" id="periodo" name="periodo" value="0"/>
            <div id="btnSeleccionReporte">
           <br/>
                <div>
                    <input type="hidden" name='filename' id="filename" value="<%="EstadisticaFirmasFolio" + File.separatorChar + "rptEstadisticaFirmasFolio.jasper"%>"  />
                   <!-- <label for="chkEstadisticaAutFolios">ESTAD&Iacute;STICA DE AUTORIZACI&Oacute;N DE FOLIOS, DE TODAS LAS FIRMAS</label>-->
                </div>
        
            </div>

            
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
     <div id="divCombo" class="comboRamos">
         <label> Nivel:</label>
                 <ul>
                    <li><div id="comboSelectEstatus" name="comboSelectEstatus" >
                            <input id="labelCont1" name="labelCont1" type="Text" value="<%=contReg1 + " " + msjSelect%>" disabled="true"/>
                            <img id="dropDown" name="dropDown" src="imagenes/OpenArrow.png" >
                        </div>
                        <ul>
                            <li>
                                <div id="divNiveles" name="divNiveles"  >
                                    <input type="checkbox" id="allChecks1" name="allChecks1" onclick="allChecksRptEstatusNivelRepEst()" checked="true"/>Todos/Ninguno
                                    <table>
                                        <%
                                            int RegTemp1 = 1;
                                            for (EstatusMovReporte estatus : estatusMovList) {
                                                RegTemp1++;
                                                out.print("<tr >");
                                                out.print("<td align='left' >");
                                                out.print("<input id='checks" + RegTemp1 + "' name='checks" + RegTemp1 + "' type='checkbox' onclick='contCheckNiveles(" + RegTemp1 + ")' checked='true' />");
                                                out.print("<input id='NivelCheck1" + RegTemp1 + "' name='NivelCheck1" + RegTemp1 + "' type='hidden'  value='" + estatus.getEstatusMovId() + "' />");
                                                out.print("</td>");
                                                out.print("<td align='left'  >");
                                                out.print(estatus.getEstatusMov()+ " ");
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
        <div>
            <label>Fecha de envio:</label><br/><br/>
            <label>Desde:</label><input readonly='readonly' type='text' id='fecI' name='fecI' maxlength='10' size='10' class='datepicker input-fecha' value=''>
            <label>hasta:</label><input readonly='readonly' type='text' id='fecF' name='fecF' maxlength='10' size='10' class='datepicker input-fecha' value=''>
         <script type='text/javascript'>
     $('.datepicker').datepicker();
    $('.datepicker' ).datepicker('option','dateFormat','dd/mm/yy');
    </script>
        </div>

    <input id='contReg1' name='contReg1' type='hidden' value='<%=contReg1%>'/>
                <input id='contRegSelect1' name='contRegSelect1' type='hidden' value='<%=contRegSelect1%>' />
                 <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
         
        </form>
                
                
                
                </div>
        <jsp:include page="template/piePagina.jsp"/>
</html>

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
<%@page import="gob.gbc.entidades.Finalidad"%>
<%@page import="gob.gbc.entidades.Funcion"%>
<%@page import="gob.gbc.entidades.EntePublico"%>
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
        List<Finalidad> finalidadList = new ArrayList<Finalidad>();
        List<Funcion> funcionList = new ArrayList<Funcion>();
        List<EntePublico> entepublicoList = new ArrayList<EntePublico>();
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
        finalidadList=reporteExcelBean.getResultFinalidadByYear(year);
        entepublicoList=reporteExcelBean.getEntePublico();
        reporteExcelBean.resultSQLDesconecta();
        contReg = ramoList.size();
        contRegSelect = contReg;

        if (contReg == 1) {
            msjSelect = "Seleccionado";
        }
    %>
    <script src="librerias/funciones.js" type="text/javascript"></script>
    <script>
       function inicializa(){
        <% if (tipoDependencia.equals("SPPD")){%>
        $("#tipoConcentrado").show();
        $("#tipoAnual").hide();
        $("#filtroClasifAdmin").show();
        $("#divCombo").hide();
        $("#filtrosRpt").hide();
        $("#filtroClasifFunc").hide();
        <%} else if (tipoDependencia.equals("PROGD")){%>
        $("#tipoConcentrado").show();
        $("#tipoAnual").hide();
        $("#filtroClasifAdmin").hide();
        $("#divCombo").show();
        $("#filtrosRpt").show();
        $("#filtroClasifFunc").hide();
            <%}%>
         };     
        </script>
        
        <body onload="inicializa()">
    
     <jsp:include page="template/encabezado.jsp" />
     
     <div Id="TitProcess" ><label >REPORTES PARA TRANSPARENCIA<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-reporte" onclick="generaReporteTransparencia()">
                <br/> <small> Reporte </small>
            </button>
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generaReporteTransparenciaExcel()">
                <br/> <small> Excel </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>    
    <div class="center-div" style="width: 62%;"> 

        <form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST" onSubmit="return validaRepsTransparencia('<%=tipoDependencia%>')">
            <input type="hidden" id="opcionReporte" name="opcionReporte" value="Transparencia"/>
            <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
            <input type="hidden" id="reporttype" name="reporttype" value="pdf"/> 
            <input type="hidden" id="MilesPesos" name="MilesPesos" value="S"/> 
            
            <div id="btnSeleccionRpt">
                <label id='lbCalAnual' >PRESUPUESTO DE EGRESOS </label>
                <div>
                    <input type="radio" name='filename' id="chkRptClasifAdmin" value="ClasifAdmin" checked onClick="filtrarReportesTransparecia(1,'<%=tipoDependencia%>')">
                    <label for="chkRptClasifAdmin" >POR CLASIFICACI&Oacute;N ADMINISTRATIVA</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptClasifEcon" onClick="filtrarReportesTransparecia(2,'<%=tipoDependencia%>')" value="rptClasificacionEconomicaTipoGasto.jasper">
                    <label for="chkRptClasifEcon" >POR CLASIFICACI&Oacute;N ECON&Oacute;MICA POR TIPO DE GASTO</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptPptoClasifFunc" onClick="filtrarReportesTransparecia(3,'<%=tipoDependencia%>')" value="ClasificacionFuncional" />
                    <label for="chkRptPptoClasifFunc">POR CLASIFICACI&Oacute;N FUNCIONAL DEL GASTO</label>
                </div>
                <div>
                    <input type="radio" name='filename' id="chkRptClasifObjGto"  onClick="filtrarReportesTransparecia(4,'<%=tipoDependencia%>')" value="objetoGasto"/>
                    <label for="chkRptClasifObjGto">POR CLASIFICACI&Oacute;N POR OBJETO DEL GASTO</label>
                </div>
               <div>
                    <input type="radio" name='filename' id="chkCalendario" onClick="filtrarReportesTransparecia(5,'<%=tipoDependencia%>')" value="Calendario" />
                    <label for="chkCalendario">CALENDARIZADO DEL PRESUPUESTO DE EGRESOS</label>
                </div>
   
               
            </div>
            </br>
            <div id="tipoConcentrado">
               <label>OPCI&Oacute;N DE REPORTE</label>
               <div>
                    <input type="radio" name='chkTipoConcentrado' id="chkTipoConcentrado" value="C" checked/>
                    <label for="chkConcentrado">CONCENTRADO</label>
                </div>
                <div>
                    <input type="radio" name='chkTipoConcentrado' id="chkTipoConcentrado"  value="D" />
                    <label for="chkDetallado">DETALLADO</label>
                </div>
            </div>
            </br>
          <div id="tipoAnual">
              <label>OPCI&Oacute;N DE REPORTE</label>
               <div>
                    <input type="radio" name='chkTipoanual' id="chkTipoanual" value="1" checked/>
                    <label for="chkCalendarizado">CALENDARIZADO</label>
                </div>
                <div>
                    <input type="radio" name='chkTipoanual' id="chkTipoanual" value="2"  />
                    <label for="chkAnual">ANUAL</label>
                </div>
            </div>
            </br>

            <div id="filtroClasifAdmin">
              <label>FILTROS DE REPORTE</label>
              <br/>
              <table>
                <tr>
                    <td><label> TIPO ENTE</label><BR/><BR/><label>DE:</label></td>
                    <td><BR/>
                        <select id="selTEI" name="selTEI" class='selRangoPrograma' onchange="validaClasifAdmin()">
                            <option value="-1">-- Selecciona un tipo ente --</option>
                            <% for (EntePublico entepublico : entepublicoList) {
                                   out.print("<option value='"+String.valueOf(entepublico.getSectorConac())+String.valueOf(entepublico.getSectorEconomico())+String.valueOf(entepublico.getSectorTipo())+String.valueOf(entepublico.getSubsector())+entepublico.getTipoEntePublico()+entepublico.getEntePublico()+"'>"+String.valueOf(entepublico.getSectorConac())+"-"+String.valueOf(entepublico.getSectorEconomico())+"-"+String.valueOf(entepublico.getSectorTipo())+"-"+String.valueOf(entepublico.getSubsector())+"-"+entepublico.getTipoEntePublico()+"-"+entepublico.getEntePublico()+" "+entepublico.getDescripcion()+"</option>");
                                 
                            }
                            
                            %> 
                        </select>
                    </td>
                </tr>
                 <tr>
                    <td><label>A:</label></td>
                    <td>
                        <select id="selTEF" name="selTEF" class='selRangoPrograma' onchange="validaClasifAdmin()">
                            <option value="-1">-- Selecciona un tipo ente --</option>
                               <% for (EntePublico entepublico : entepublicoList) {
                                   out.print("<option value='"+String.valueOf(entepublico.getSectorConac())+String.valueOf(entepublico.getSectorEconomico())+String.valueOf(entepublico.getSectorTipo())+String.valueOf(entepublico.getSubsector())+entepublico.getTipoEntePublico()+entepublico.getEntePublico()+"'>"+String.valueOf(entepublico.getSectorConac())+"-"+String.valueOf(entepublico.getSectorEconomico())+"-"+String.valueOf(entepublico.getSectorTipo())+"-"+String.valueOf(entepublico.getSubsector())+"-"+entepublico.getTipoEntePublico()+"-"+entepublico.getEntePublico()+" "+entepublico.getDescripcion()+"</option>");
                            }
                            
                            %> 
                               
                        </select>
                    </td>
                </tr>
           
            </table>
            </div>
            </br>
               <div id="filtroClasifFunc">
              <label>FILTROS DE REPORTE</label>
              <table>
              <tr>
                 <td>
                     <label>Finalidad inicial&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <select id="selFinalidad1" name="selFinalidad1" class='selRangoPrograma' onchange="cargarFuncionesReportesTransparencia();">
                        <option value="-1">-- Selecciona una Finalidad --</option>
                       <%              for (Finalidad finalidad : finalidadList) {
                                             out.print("<option value='"+finalidad.getFinalidad()+"'>"+finalidad.getDescr()+"</option>");
                                            }
                                        %> 
                        </select>
                 </td></tr>
                 <tr><td>
                      <label>Finalidad Final&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <select id="selFinalidad2" name="selFinalidad2" class='selRangoPrograma' onchange="cargarFuncionesReportesTransparencia();">
                        <option value="-1">-- Selecciona una Finalidad --</option>
                            <%              for (Finalidad finalidad : finalidadList) {
                                               // RegTempf++;
                                                  out.print("<option value='"+finalidad.getFinalidad()+"'>"+finalidad.getDescr()+"</option>");
                                            }
                                        %> 
                        </select>
                 </td>
                </tr>
   
              <tr>
                 <td>
                     <label>Funcion inicial&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <select id="selFuncion1" name="selFuncion1" class='selRangoPrograma' onchange="cargarSubfuncionesReportesTransparencia()">
                        <option value="-1">-- Selecciona una Funci&oacute;n --</option>
                        </select>
                 </td></tr>
                 <tr><td>
                      <label>Funcion Final&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                    <select id="selFuncion2" name="selFuncion2" class='selRangoPrograma' onchange="cargarSubfuncionesReportesTransparencia()">
                        <option value="-1">-- Selecciona una Funci&oacute;n --</option>
                        </select>
                 </td>
                </tr>
                 <tr>
                          <td>
                     <label>SubFuncion inicial&nbsp;</label>
                    <select id="selSubFuncion1" name="selSubFuncion1" class='selRangoPrograma' onchange="">
                        <option value="-1">-- Selecciona una SubFunci&oacute;n --</option>
                        </select>
                          </td></tr><tr><td>
                      <label>SubFuncion Final&nbsp;&nbsp;&nbsp;</label>
                    <select id="selSubFuncion2" name="selSubFuncion2" class='selRangoPrograma' onchange="">
                        <option value="-1">-- Selecciona una SubFunci&oacute;n --</option>
                        </select>
                 </td>
                     </tr>
            </table>
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
                        <div>
                            <input type="checkbox" id="Miles" name="Miles" onclick="validaMilesPesos()"> Mostrar en Miles de Pesos
                        </div>
            
            <div id="divInfoPlantilla" style="width: 1200px" > 
                <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
            </div>
        </form>
    </div>
    <jsp:include page="template/piePagina.jsp" />
        </body>
</html>

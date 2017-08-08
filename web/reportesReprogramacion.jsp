<%-- 
    Document   : reportesReprogramación
    Created on : Abr 14, 2016, 3:01:06 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
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
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<Proyecto> proyectoList = new ArrayList<Proyecto>();
        
        CaratulaBean caratulaBean = null;
        List<Caratula> caratulaList = null;

        String usuario = new String();
        String tipoDependencia = new String();
        String ramoSession = new String();
        String msjSelect = "Seleccionados";
        String rol = new String();

        int year = 0;
        int contReg = 0;
        int contRegSelect = 0;

        boolean isParaestatal = false;
        boolean isNormativo = false;
        try{
            if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
                response.sendRedirect("logout.jsp");
                return;
            }
            if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
                rol = (String) session.getAttribute("strRol");
            }
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }
            if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                usuario = (String) session.getAttribute("strUsuario");
            }
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }        
            if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
                ramoSession = (String) session.getAttribute("ramoAsignado");
            }

            caratulaBean = new CaratulaBean(tipoDependencia);
            caratulaBean.setStrServer(( request.getHeader("Host")));
            caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            caratulaBean.resultSQLConecta(tipoDependencia);
            isParaestatal = caratulaBean.getResultSQLisParaestatal();
            ramoList = caratulaBean.getResultRamoByYear(year, usuario);
            if(rol.equals(caratulaBean.getResultSQLGetRolesPrg()))
                isNormativo = true;
            if(isParaestatal)
                caratulaList = caratulaBean.getCaratulas(String.valueOf(year), ramoSession, false,1,isNormativo);
            
            contReg = ramoList.size();
            contRegSelect = contReg;

    %>
    <jsp:include page="template/encabezado.jsp" />
    
    <div Id="TitProcess"><label >REPORTES REPROGRAMCI&Oacute;N <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReporteReprogramacion('xls')">
                <br/> <small> Excel </small>
            </button>
            <button id="botonPdf" type="submit" class="btnbootstrap btn-reporte" onclick="generarReporteReprogramacion('pdf')">
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div"> 
        <!--<form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">-->
        <form id='frmRptExcel' action="PaqueteReportesReprogramado" target="_blank" method="POST">

            <input type="hidden" id='reporttype' name='reporttype' value="" />
            <input type="hidden" id="tipoReporte" name="tipoReporte" value="6"/>
            <input type="hidden" id="ramoInList" name="ramoInList" value="0"/>
            <input type="hidden" id="rptPath" name="rptPath" value="reprogramadoRevision"/>
            <input type="hidden" id="isParaestatal" name="isParaestatal" value="<%=isParaestatal%>"/>
            <input type="hidden" id="nombresReporte" name="nombresReporte" value=""/>

            
           <div id="div-inpt-folio">
                <section class="col-md-12">
                    <div class="col-md-12">
                        <div class="col-md-3">
                            <input type="radio" id="radioCaratula" name="radio-busca" value="1" onclick="cambioBusquedaReporteReprogramacion('1')" checked > 
                            <%if(isParaestatal){%>
                                <label for='radioCaratula'>Ramo / Car&aacute;tula:</label>
                            <%}else{%>
                                <label for='radioCaratula'>Ramo:</label>
                            <%}%>
                        </div>
                        <div class="col-md-9">
                            <%
                                if(isParaestatal){
                            %>
                            <input type="hidden" name='radioTipoCaratula' id="tipoCaratula"  value="1" />
                            <%--
                            <div class="col-md-12">
                                <div class="col-md-12">
                                    <input type="radio" name='radioTipoCaratula' id="tipoCaratula"  onchange="getCaratulasByRamoReprogramacion(<%=isParaestatal%>)"  value="1"  checked />
                                    <label for="tipoCaratula">N&uacute;mero sesi&oacute;n</label>
                                </div>
                                <div class="col-md-12">
                                    <input type="radio" name='radioTipoCaratula' id="caratulaPresupuestal" onchange="getCaratulasByRamoReprogramacion(<%=isParaestatal%>)" value="2"  />
                                    <label for="caratulaPresupuestal">N&uacute;mero de Modificación Program&aacute;tica</label>
                                </div>
                            </div> 
                            --%>
                            <%}%>
                            <div class="col-md-12">
                                <%if(isParaestatal){%>
                                <label> Ramo: </label>
                                <%}%>
                                <select id="selRamo" name="selRamo" onchange="getCaratulasByRamoReprogramacion(<%=isParaestatal%>);cargarProgramasReprogramacion()">
                                    <option value="-1">-- Seleccione un ramo </option>
                                    <%
                                        for(Ramo ramo : ramoList){
                                            out.print("<option value='"+ramo.getRamo()+"'>"+ramo.getRamo()+"-"+ramo.getRamoDescr()+"</option>");
                                        }
                                    %>
                                </select> 
                            </div>
                            
                            <br/>
                            <div class="col-md-12">                                 
                                <%
                                    if(isParaestatal){
                                        %>
                                        <label> Car&aacute;tula: </label>
                                <%
                                        out.print("<select id='selCaratula' name='selCaratula' onChange='mostrarRangoSeleccion()'>");
                                        out.print("<option value='-1'> -- Seleccione una car&aacute;tula -- </option>");
                                        out.print("<option value='-2' > No aplica </option>");
                                        out.print("</select>");
                                    }
                                %>
                            </div>
                            <br/><br/><br/>
                            <div id="rangos-param" style="padding-left: 20px; display:none;" class="col-md-12">
                                <div id="btnSeleccionReporte" style="display: none">                
            
                                    <div>
                                        <input class="chkModProgramatica" type="radio" name='filename' id="radioReporteMetaNueva" 
                                               value="<%="rptReproMetasNuevas.jasper"%>" 
                                               onChange="cambiaDirectorioReprogReporte('showPdf','2')" checked/>
                                        <label for="radioReporteMetaNueva">Metas Nuevas MP-CR<!--Reprogramación metas nuevas--></label>
                                    </div>
                                    <div>
                                        <input class="chkModProgramatica" type="radio" name='filename' id="radioReporteDesgAccion" 
                                               value="<%="rptDesglosePorAccion.jasper"%>" 
                                               onChange="cambiaDirectorioReprogReporte('showPdf','2')"/>
                                        <label for="radioReporteDesgAccion">Requerimientos MP-CR<!--Desglose de acciones--></label>
                                    </div>
                                    <div>
                                        <input class="chkModProgramatica" type="radio" name='filename' id="radioReporteCompRep" 
                                               value="<%="rptReproCompMetaAccion.jasper"%>" 
                                               onChange="cambiaDirectorioReprogReporte('showPdf', '2')"/>
                                        <label for="radioReporteCompRep">Metas-Accion MP-MD-01<!--Comparativo reprogramación--></label>
                                    </div>

                                </div>  
                                <br/>
                                <br/>
                                <div id="selPrgIF">
                                    <div id="divPrgI">
                                        <label> Programa inicial </label>
                                        <select id="selProgI" name="selProgramaI" class='selRangoPrograma comboReporte' onchange="validaProgramaMenorFinalMonitoreoReprogramacion()">
                                            <option value="-1">-- Selecciona un programa inicial --</option>
                                        </select>
                                    </div> 
                                    <div id="divPrgF">
                                        <label> Programa final &nbsp;  </label>
                                        <select id="selProgF" name="selProgramaF" class='selRangoPrograma comboReporte' onchange="validaProgramaMayorInicialReprogramacion()">
                                            <option value="-1">-- Selecciona un programa final --</option>
                                        </select>
                                    </div>
                                </div>
                                <div id="selproyActIF">
                                    <div id="divProActI">
                                        <label> Proy/Act inicial</label>
                                        <select id="selProActI" name="selProActI" class='selRangoProyecto comboReporte' style="width: 400px" onchange="validaProyectoMenorFinalMonitoreo()">
                                            <option value="-1">-- Selecciona un proyecto/actividad inicial --</option>
                                        </select>
                                    </div> 
                                    <div id="divProActF">
                                        <label> Proy/Act final  </label>
                                        <select id="selProActF" name="selProActF" class='selRangoProyecto comboReporte' style="width: 400px" onchange="validaProyectoMayorInicialMonitoreo()">
                                            <option value="-1">-- Selecciona un proyecto/actividad final --</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="col-md-3">
                            <input type="radio" id="radioFolio" name="radio-busca" value="0" onclick="cambioBusquedaReporteReprogramacion('0')">
                            <label for='radioFolio'>Folio:</label>
                        </div>
                        <div class="col-md-9">
                            <input type="text" id="inpt-folio" name='oficioList' value="0"/>
                        </div>
                    </div>
                </section>
            </div>
        </form>    
    </div>
    <jsp:include page="template/piePagina.jsp"/>
    
    <script type="text/javascript">
        $(document).ready(function(){         
            $(".center-div").css("margin","5%");
        });
    </script>
    <%
        }catch(Exception ex){
            Bitacora bitacora = new Bitacora();
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.grabaBitacora();
        }finally{
            if(caratulaBean != null)
                caratulaBean.resultSQLDesconecta();
        }
            
    %>

<%-- 
    Document   : reportesCaratulas
    Created on : Jun 29, 2016, 3:01:06 PM
    Author     : jarguelles
--%>

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
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }

        ParametroBean parametroBean = null;
        CaratulaBean caratulaBean = null;

        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<Partida> partidaList = new ArrayList<Partida>();
        ArrayList<Caratula> arrCaratulas = new ArrayList<Caratula>();

        String rol = new String();
        String ramoId = new String();
        String usuario = new String();
        String disabled = new String();
        String reportePOA = new String();
        String ramoSession = new String();
        String strSelected = new String();
        String msjSelect = "Seleccionados";
        String tipoDependencia = new String();
        String caratulaDisable = new String();

        int year = 0;
        int contReg = 0;
        int contRegSelect = 0;

        boolean cerrado = true;
        boolean parametro = true;

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
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
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        ramoList = caratulaBean.getResultRamoByYear(year, usuario);

        contReg = ramoList.size();
        contRegSelect = contReg;

        if (!rol.equals(caratulaBean.getRolNormativo())) {
            ramoId = ramoSession;
        }


    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label>REPORTES CARATULAS<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="submit" class="btnbootstrap btn-Excel" onclick="generarReporteCaratula('xls')">
                <br/> <small> Excel </small>
            </button>
            <button id="botonPdf" type="submit" class="btnbootstrap btn-reporte" onclick="generarReporteCaratula('pdf')" >
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div"> 
        <!--<form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">-->
        <form id='frmRptExcel' action="PaqueteReporte" target="_blank" method="POST">

            <input type="hidden" id='reporttype' name='reporttype' value="pdf" />
            <input type="hidden" id="tipoReporte" name="tipoReporte" value="0"/>
            <input type="hidden" id="rptPath" name="rptPath" value="Caratulas"/>
            <input type="hidden" id="nombresReporte" name="nombresReporte" value=""/>

            <div id="btnSeleccionReporte" style="display: none">   
                <div>
                    <input type="radio" class='paqueteReportes' name='filename' id="radioFormatoModificacionPresupuestal" value="<%="rptModificacionPPTOCaratula.jasper"%>"  onChange="cambiarReporteCaratula('')" checked />
                    <label for="radioFormatoModificacionPresupuestal">Formato De Modificación Presupuestal</label>
                </div>
                <div>
                    <input type="radio" class='paqueteReportes' name='filename' id="radioFormatoModificacionPresupuestalDetallado" value="<%="rptModificacionPPTOCaratulaDetallado.jasper"%>"  onChange="cambiaDirectorioReporte('', '1')" />
                    <label for="radioFormatoModificacionPresupuestalDetallado">Formato De Modificación Presupuestal Detallado</label>
                </div>
                <div>
                    <input type="radio" class='paqueteReportes' name='filename' id="radioFormatoConsolidadoModificacionesPresupuestales" value="<%="repModificacionPPTOConsolidado.jasper"%>"  onChange="cambiaDirectorioReporte('', '1')" />
                    <label for="radioFormatoConsolidadoModificacionesPresupuestales">Formato De Consolidación De Modificaciones Presupuestales</label>
                </div>
                <div>
                    <input type="radio" class='paqueteReportes' name='filename' id="radioFormatoModificacionPPTODetalleRequerimientos" value="<%="rptModificacionPPTODetalleRequerimientos.jasper"%>"  onChange="cambiaDirectorioReporte('', '1')" />
                    <label for="radioFormatoModificacionPPTODetalleRequerimientos">Formato De Modificaciones Presupuestales Detalle De Requerimientos</label>
                </div>
            </div>  
            <div>
                <fieldset style="display:inline">
                    <legend>
                        Ramo
                    </legend>
                    <div class='caratula'>     
                        <table id="tblComboCaratula">
                            <tr>
                                <td>
                                    <div id='txtCaratula' >
                                        Ramo:
                                    </div> 
                                </td>
                                <td>
                                    <select  id="selRamo" name="selRamo" onchange='getCaratulasByRamo()'>
                                        <%
                                            if (rol.equals(caratulaBean.getRolNormativo())) {
                                                out.print("<option value='-1'> -- Seleccione un ramo -- </option>");
                                            }

                                            if (ramoList.size() > 0) {
                                                for (Ramo ramoTemp : ramoList) {
                                                    if (ramoId.equals(ramoTemp.getRamo())) {
                                                        out.print("<option selected value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");
                                                    } else {
                                                        out.print("<option value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");
                                                    }
                                                }
                                            }
                                        %>
                                    </select>
                                </td>
                            </tr>
                        </table>                      
                    </div>
                </fieldset>
                <br>                   

                <fieldset style="display:inline">
                    <legend>
                        Caratula de Revisi&oacute;n
                    </legend>
                    <div class='caratula'>
                        <input type="hidden" name='radioTipoCaratula' id="tipoCaratula"  value="1" />
                        <%--
                        <div>
                            <div>
                                <input type="radio" name='radioTipoCaratula' id="tipoCaratula"  onchange="getCaratulasByRamo()"  value="1"  checked />
                                <label for="tipoCaratula">Numero sesión</label>
                            </div>
                            <div>
                                <input type="radio" name='radioTipoCaratula' id="caratulaPresupuestal" onchange="getCaratulasByRamo()" value="2"  />
                                <label for="caratulaPresupuestal">Numero de Modificación Presupuestal</label>
                            </div>
                        </div>  
                        --%>
                        <table id="tblComboCaratula">
                            <tr>
                                <td>
                                    <div id='txtCaratula' >
                                        Caratula:
                                    </div> 
                                </td>
                                <td>
                                    <%
                                        out.print("<select style='width: 400px;' id='selCaratula' name='selCaratula' >");
                                        out.print("<option value='-1'> -- Seleccione un caratula -- </option>");
                                        if (arrCaratulas.size() > 0) {
                                            for (Caratula objCaratula : arrCaratulas) {
                                                caratulaDisable = "";
                                                if (year != objCaratula.getiYear()) {
                                                    caratulaDisable = " class='disabled' disabled ";
                                                } else {
                                                    caratulaDisable = " class='enabled' ";
                                                }
                                                strSelected = "";
                                                if (year != objCaratula.getiYearSesion()) {
                                                    out.print("<option " + caratulaDisable + "  title='Sesi&oacute;n " + objCaratula.getiYearSesion() + "' value=" + objCaratula.getsIdCaratula() + "   >" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>");
                                                } else {
                                                    out.print("<option " + caratulaDisable + "  value=" + objCaratula.getsIdCaratula() + "   >" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>");
                                                }
                                            }
                                        }
                                        out.print("</select>");
                                    %>
                                </td>
                            </tr>
                        </table> 
                    </div>
                </fieldset>

            </div>


            <div id="divInfoPlantilla" style="width: 1200px" > 
                <input id='contReg' name='contReg' type='hidden' value='<%=contReg%>'/>
                <input id='contRegSelect' name='contRegSelect' type='hidden' value='<%=contRegSelect%>' />
            </div>

        </form>
    </div>
    <script>
        getCaratulasByRamo();
    </script>  
    <%
        if (caratulaBean != null) {
            caratulaBean.resultSQLDesconecta();
        }
    %>
    <jsp:include page="template/piePagina.jsp"/>
</html>

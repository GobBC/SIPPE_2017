<%-- 
    Document   : reporteTransAmp
    Created on : Dec 11, 2015, 9:44:10 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.entidades.EstatusMov"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="gob.gbc.entidades.TipoFlujo"%>
<%@page import="gob.gbc.entidades.TipoMovimiento"%>
<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.entidades.Programa"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>REPORTE IMPRESIÓN DE FOLIOS MOVTOS PTALES</title>

    </head>
    <%
        if (session.getAttribute("strUsuario") == null
                || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
            return;
        }
        if (session.getAttribute("tipoDependencia") == null
                || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
            return;
        }
        //CaratulaBean caratulaBean = new CaratulaBean();
        ArrayList<Caratula> arrCaratulas = null;
        List<EstatusMov> estatusMovimientosList = new ArrayList<EstatusMov>();
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<TipoMovimiento>();
        String rol = new String();
        String tipoMov = new String();
        String estatus = new String();
        String strSelected = new String();
        String ramoSession = new String();
        String selectTipoMov = new String();
        String selectEstatus = new String();
        String caratulaDisable = new String();
        String tipoDependencia = new String();
        String usuario = new String();
        int year = 0;
        int folio = 0;
        boolean isNormativo = false;
        boolean isParaestatal = false;
        boolean bFiltraEstatusAbiertas = false;

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = request.getParameter("tipoMov");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = request.getParameter("estatus");
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }

        AutorizacionBean autorizacionBean = new AutorizacionBean(tipoDependencia);
        autorizacionBean.setStrServer((request.getHeader("Host")));
        autorizacionBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        autorizacionBean.resultSQLConecta(tipoDependencia);

        tipoMovimientoList = autorizacionBean.getResultTipoMovimientoFiltrado();
        estatusMovimientosList = autorizacionBean.getResultEstatusMovimientos();

        isParaestatal = autorizacionBean.isParaestatal();
        /*caratulaBean.setStrServer(request.getHeader("host"));
         caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
         caratulaBean.resultSQLConecta(tipoDependencia);*/
        if (autorizacionBean.getRolNormativo(Integer.parseInt(rol)) == 1) {
            isNormativo = true;
        }
        arrCaratulas = autorizacionBean.getResultSQLObtieneCaratulas(String.valueOf(year), ramoSession, bFiltraEstatusAbiertas, 1, isNormativo);
        /*if (caratulaBean != null) {
         caratulaBean.resultSQLDesconecta();
         }*/
    %>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label>REPORTE IMPRESI&Oacute;N DE FOLIOS MOVTOS PTALES<label/></div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div> 
    </div>
    <br>
    <br>
    <br>
    <br> 

    <div class="center-div" style="margin: 5px 2%;">
        <form id="frmConsulta" action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">        
            <input type="hidden" id="opcionReporte" name="opcionReporte" value=""/>
            <input type="hidden" id="reporttype" name="reporttype" value=""/> 
            <input type="hidden" id="rptPath" name="rptPath" value=""/> 
            <input type="hidden" id="oficio" name="oficio" value=""/> 
            <input type="hidden" id="tipoOficio" name="tipoOficio" value=""/> 
            <input type="hidden" id="tipoReporteAmpTran" name="tipoReporteAmpTran" value=""/> 
            <input type="hidden" id="tipoMovimiento" name="tipoMovimiento" value=""/> 
            <input type="hidden" id='filename' name='filename'  value="rptAmpliacionesConcentrado.jasper" />
            <input type="hidden" id='banderaPage' name='banderaPage'  value="true" />
            <input type="hidden" id='estatusMovimientoId' name='estatusMovimientoId'  value="" />
            <input type="hidden" id='codCompleto' name='codCompleto'  value="" />

            <%if (!isParaestatal || autorizacionBean.getResultSqlGetIsAyuntamiento()) {%>
            <div id="div-inpt-folio" class="col-md-12">                
                <div class="col-md-3">                 
                    <input Style="display:none" type="radio" id="radioFolio" name="radio-busca" value="folio" onclick="cambioBusqueda()" checked > 
                    Folio:  
                    <input type="text" id="inpt-folio" value="<%=folio%>" style="margin-left:15px;" /><br/>  
                </div>
                <div class="col-md-9">
                    <input type="button" id="btnFolio" name="btnFolio" value="Buscar" onclick="getTablaReporteSolicitudesMovimientosByFolio()"/> <br/>         
                </div>                      

            </div>
            <%} else {%>

            <div id="div-inpt-folio">
                <input type="radio" id="radioFolio" name="radio-busca" value="folio" onclick="cambioBusquedaReporteByFolioCaratula()" checked /> 
                Folio:
                <input type="text" id="inpt-folio" value="<%=folio%>" style="margin-left:15px;" /><br/>                
                <input type="button" id="btnFolio" style="margin-left: 200px;" name="btnFolio" value="Buscar" onclick="getTablaReporteSolicitudesMovimientosByFolio()"/> <br/>         

                <table id="tblComboCaratula">
                    <tr>
                        <td>
                            <input type="radio" id="radioCaratula" name="radio-busca" value="caratula" onclick="cambioBusquedaReporteByFolioCaratula()"  > 
                        </td>
                        <td><div id='txtCaratula' >Caratula:</div> </td>
                        <td>
                            <%
                                out.print("<select id='selCaratula' name='selCaratula' >");
                                out.print("<option value='-1' selected > -- Seleccione un caratula -- </option>");
                                out.print("<option value='-2' > No aplica </option>");

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
                        <td>
                            <input type="button" id="btnCaratula" name="btnCaratula" value="Buscar" onclick="getTablaReporteSolicitudesMovimientosByCaratula()"/>
                        </td>
                    </tr>
                </table> 
                <br/>    

            </div>


            <%}%>

            <input Style="display: none" type="radio" id="radioCompleto" name="radio-busca" onclick="cambioBusqueda()" value="folio"/>  



            <table Style="display: none" id="tblSolicitudSelects">
                <tr>
                    <td>
                        <label id="lbTipoSolicitud" >Tipo de Solicitud</label>
                    </td>
                    <td>
                        <select id='selTipoSolicitud' name="selTipoSolicitud" onchange='getTablaSolicitudesMovimientosReporte()' >
                            <option value='-1'>-- Selecciona un tipo de solicitud --</option>
                            <%
                                for (TipoMovimiento tipoMovimiento : tipoMovimientoList) {
                                    selectTipoMov = "";
                                    if (tipoMovimiento.getTipoMovId().equalsIgnoreCase(tipoMov)) {
                                        selectTipoMov = "selected";
                                    }

                                    out.println("<option value='" + tipoMovimiento.getTipoMovId() + "' " + selectTipoMov + "  >"
                                            + tipoMovimiento.getTipoMov() + " </option>");
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <label>Estatus</label>
                    </td>
                    <td>
                        <select id="selEstatusMovimiento"  onchange='getTablaSolicitudesMovimientosReporte()' >
                            <option value="-1">-- Selecciona un estatus --</option>
                            <%
                                for (EstatusMov estatusMovimiento : estatusMovimientosList) {
                                    selectEstatus = "";
                                    if (estatusMovimiento.getEstatusMovId().equalsIgnoreCase(estatus)) {
                                        selectEstatus = "selected";
                                    }

                                    out.println("<option value='" + estatusMovimiento.getEstatusMovId() + "' " + selectEstatus + " >"
                                            + estatusMovimiento.getEstatusMov() + " </option>");
                                }
                            %>
                        </select>

                    </td>
                </tr>
            </table> 
            <script>
                getTablaSolicitudesMovimientosReporte();
            </script>                         

            <div style="margin-left: 100px">


                <div style="display:none; margin-left: 50px;">
                    <input type="radio" name="rdTipoRpt"  id="rdBtnAmpliacion" onchange="prepararReporteMovtos('', 'repAmpTrans')" value="3" checked/> <label for="rdBtnAmpliacion">Reporte Ampliaciones</label><br/>
                    <input type="radio" name="rdTipoRpt"  id="rdBtnTransferencia" onchange="prepararReporteMovtos('', 'repAmpTrans')" value="4" /> <label for="rdBtnTransferencia">Reporte Transferencias</label><br/>
                    <input type="radio" name="rdTipoRpt"  id="rdBtnRepro"  onchange="prepararReporteMovtos('rptOficialReprogramacion.jasper', 'reprogramado')" value="1"/><label for="rdBtnRepro">Reporte Reprogramado</label><br/>
                    <input type="radio" name="rdTipoRpt"  id="rdBtnRecal"  onchange="prepararReporteMovtos('rptRecalendarizacionFormatoRevision.jasper', 'RecalendarizacionFormatoRevision')" value="2"/><label for="rdBtnRecal">Reporte Recalendarizado</label>
                </div>
                <div id="divRptGral" style="margin-left: 100px;">    
                    <input type="radio" name="rdGeneral"  id="rdGeneralCon"  value="1" onchange="cambiaRadiosReporteMovtos('', 'repAmpTrans')" checked /> 
                    <label for="rdGeneralCon"> Concentrado</label>
                    <br/>
                    <input type="radio" name="rdGeneral"  id="rdGeneralDet" value="2" onchange="cambiaRadiosReporteMovtos('', 'repAmpTrans')" /> 
                    <label for="rdGeneralDet"> Detallado</label>
                </div>
                <div id="divSelCodigo" name="divSelCodigo" style="display:none;   margin-left: 100px;">
                    <input type="checkbox"  id="chkCodigoCompleto" name="chkCodigoCompleto">Codigo Completo  
                </div>
            </div>
            <br>
            <br>
            <div id="mensaje" style="margin-left: 344px !important; margin-top: -29px !important; position: fixed; " ></div> 
            <br>
            <div id="TableZone">
                <table id="tbl-solMovs">
                    <thead>
                        <tr>
                            <th align="right" >Oficio</th>
                            <th align="right" >Fecha<br>Elaboración</th>
                            <th align="right" >Fecha Env&iacute;o<br>Autorizaci&oacute;n</th>
                            <th align="right" >Tipo Movimiento</th>
                            <th align="right" >Tipo Oficio</th>
                            <th align="right" >Usuario</th>
                            <th align="right" style='width: 126px;display: block'></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </form>

    </div>
    <jsp:include page="template/piePagina.jsp" />
</html>

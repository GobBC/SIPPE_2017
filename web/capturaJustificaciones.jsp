<%-- 
    Document   : capturaJustificaciones
    Created on : 23/05/2016, 09:56:20 AM
    Author     : jarguelles
--%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="template/encabezado.jsp" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captura de justificaci&oacute;n por movimientos</title>
        <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
        <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
        <link type="text/css" rel="stylesheet" href="librerias/js/jquery.qtip.custom/jquery.qtip.css" />
        <script type="text/javascript" src="librerias/js/jquery.qtip.custom/jquery.qtip.js"></script>
    </head>
    <script src="librerias/_justificaciones.js" type="text/javascript"></script>
    <div Id="TitProcess"><label>CAPTURA DE JUSTIFICACI&Oacute;N POR MOVIMIENTOS</label></div>

    <%
        request.setCharacterEncoding("UTF-8");

        CaratulaBean caratulaBean = null;
        List<Ramo> ramoList = new ArrayList<Ramo>();
        ArrayList<Caratula> arrCaratulas = new ArrayList<Caratula>();
        //List<RevisionCaratula> revisionesCaratulaList = null;
        String rol = new String();
        String ramoId = new String();
        String appLogin = new String();
        String disabled = new String();
        String urlRegresa = new String();
        String ramoSession = new String();
        //String fechaSession = new String();
        //String numeroSession = new String();
        String tipoDependencia = new String();
        long caratula = -2;
        long justificacion = -1;
        int year = 0;
        boolean bFiltraEstatusAbiertas = false;

        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (session.getAttribute("strUsuario") == null || session.getAttribute("strUsuario").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") == null || session.getAttribute("tipoDependencia").equals("")) {
            response.sendRedirect("logout.jsp");
        }
        if (request.getParameter("urlRegresa") != null && !request.getParameter("urlRegresa").equals("")) {
            urlRegresa = request.getParameter("urlRegresa");
        }
        if (request.getParameter("caratula") != null && !request.getParameter("caratula").equals("")) {
            caratula = Long.parseLong(request.getParameter("caratula"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (urlRegresa.equals("")) {
            urlRegresa = "menu.jsp";
        }

        caratulaBean = new CaratulaBean(tipoDependencia);
        caratulaBean.setStrServer((request.getHeader("Host")));
        caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        caratulaBean.resultSQLConecta(tipoDependencia);

        ramoList = caratulaBean.getResultRamoByYear(year, appLogin);

        if (!rol.equals(caratulaBean.getRolNormativo())) {
            disabled = "disabled";
            ramoId = ramoSession;
        }

        arrCaratulas = caratulaBean.getCaratulas(String.valueOf(year), ramoId, bFiltraEstatusAbiertas, 1, false);

    %>

    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-atras" onclick="regresarAutorizacion()">
                <br/> <small> Regresar </small>
            </button>
            <button type="button" class="btnbootstrap btn-reporte" onclick="capturaJustificacionesGenerarReportePDF()">
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-Excel" onclick="capturaJustificacionesGenerarReporteExcel()"> 
                <br/> <small> Reporte </small>
            </button>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <form id='frmRptExcel' action="ejecutaReporte/ejecutarReporte.jsp" target="_blank" method="POST">
        <input type="hidden" id='reporttype' name='reporttype' value="pdf" />
        <input type="hidden" id="filename" name='filename' value="rptJustificacionesMovimientos.jasper" />
        <input type="hidden" id="rptPath" name="rptPath" value='JustificacionesMovimientos'/>
        <input type="hidden" id="oficio" name="oficio" value="-1"/>
        <div>
            <fieldset style="display:inline">
                <legend>
                    Justificaciones
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
                                <select <%= disabled%> id="selRamo" name="selRamo" onchange='capturaJustificacionesGetCaratulas()'>
                                    <option value="-1"> -- Seleccione un ramo -- </option>
                                    <%
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
                <br>
                <div class='caratula'>     
                    <table id="tblComboCaratula">
                        <tr>
                            <td>
                                <div id='txtCaratula' >
                                    Caratula:
                                </div> 
                            </td>
                            <td>
                                <%
                                    out.print("<select style='width: 373px;' id='selCaratula' name='selCaratula' onchange='capturaJustificacionesGetFolios();'>");
                                    out.print("<option value='-1'> -- Seleccione un caratula -- </option>");
                                    out.print("<option value='-2'> No aplica </option>");

                                    if (arrCaratulas.size() > 0) {
                                        for (Caratula objCaratula : arrCaratulas) {
                                            if (caratula == objCaratula.getsIdCaratula()) {
                                                out.print("<option value=" + objCaratula.getsIdCaratula() + " selected   >" + objCaratula.getsDescr() + " </option>");
                                            } else {
                                                out.print("<option value=" + objCaratula.getsIdCaratula() + " >" + objCaratula.getsDescr() + " </option>");
                                            }
                                        }
                                    }
                                    
                                    out.print("</select>");

                                %>
                            </td>
                        </tr>
                    </table>         
                    <%                    out.print("<br>");
                        out.print("<div id='infoCaratula' class='info-usuario'>");
                        out.print("</div>");

                    %> 
                </div>
                <br>
                <div class='caratula'>    
                    <table>
                        <tr>
                            <td>
                                <label>Tipo de Solicitud:</label> 
                            </td>
                            <td>
                                <select id="selTipoMovto" onchange="capturaJustificacionesGetFolios()">
                                    <option value="-1">-- Seleccione un tipo de movimiento --</option>
                                    <option value="A">Ampliación/Reducción </option>
                                    <option value="R">Reprogramación </option>
                                    <option value="T">Transferencia </option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="display: none">
                    <select id="selEstatusMovimiento" onchange="capturaJustificacionesGetFolios()">
                        <option value="-1">-- Selecciona un estatus --</option>
                        <option value="X" selected="" >En Captura </option>
                    </select>
                </div>            
                <br>
                <div class='caratula'> 
                    <table>
                        <tr>
                            <td>
                                <label>Folios:</label> 
                            </td>
                            <td>
                                <select id="selFolios" onchange="capturaJustificacionesGetJustificaciones()">
                                    <option value="-1">-- Seleccione un folio --</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
                <br>
                <div class='caratula'> 
                    <table>
                        <tr>
                            <td>
                                <label>Justificaciones:</label> 
                            </td>
                            <td>
                                <select id="selJustificaciones" onchange="capturaJustificacionesGetTablaMovimientos()">
                                    <option value="-1">-- Seleccione una justificacion --</option>
                                </select>
                            </td>
                            <td>
                                <%if (justificacion != -1) {%> 
                                <input type="button" class="btnbootstrap-drop btn-add" id="btn-add" style="display:none" onclick="capturaJustificacionesGetPopUpJustificacion()"/>
                                <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit" style="" onclick="capturaJustificacionesGetPopUpJustificacion()"/>
                                <input id="tlpJustificacion" class="btnbootstrap-drop btn-infoGlobe" title="" type="button">
                                <%} else {%>
                                <input type="button" class="btnbootstrap-drop btn-add" id="btn-add" style="" onclick="capturaJustificacionesGetPopUpJustificacion()"/>
                                <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit" style="display:none" onclick="capturaJustificacionesGetPopUpJustificacion()"/>
                                <input type="button" class="btnbootstrap-drop btn-infoGlobe" id="tlpJustificacion" style="display:none" title="">
                                <%}%>
                            </td>

                        </tr>
                    </table>
                </div>
            </fieldset>

            <fieldset style="">
                <legend>
                    Movimientos Ligados Al Folio
                </legend>
                <div id="dvAllChecks"  style="display: none;  position: relative; left: 604px;">
                    <label>Seleccionar Todos/Ninguno </label>
                    <input id="allChecks" name="allChecks" onclick="capturaJustificacionesActAsignacionTodosMovimientos();"  type="checkbox" />
                </div>
                <div id="dataTableZone">
                    <table id="tbl-Justificaciones" class="display" cellspacing="0">
                        <thead>
                            <tr>
                                <th  >Num Mov</th>
                                <th  >Ramo</th>                            
                                <th  >Programa</th>
                                <th  >Proy. / Act.</th>
                                <th  >Meta</th>
                                <th  >Accion</th>
                                <th  >Depto</th>
                                <th  >Partida</th>
                                <th  >F.F.R</th>
                                <th  >Importe</th>
                                <th  >Seleccionar</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                <input type="hidden" id="actAllChecks" name="actAllChecks" value="0" />
            </fieldset>

            <div id="PopUpZone"></div>

            <div id="dialog-tbl-justificacion" title="Justificacion">
                <p id="errorComentario" style="color:red"  ></p>
                <div> 
                    <div id="justificacionZone" class="col-md-12">
                        <textarea  maxlength="350" type="text" class='no-enter' name="justificacionAuto" style="max-width: 100%; width: 100%" id="justificacionAuto" class="textarea-comentario text col-md-12" ></textarea>  
                        <input type="hidden" id="idJustificacion" name="idJustificacion" value="-1" />
                    </div>                
                </div> 
            </div>
            <script type="text/javascript">
                $(function() {
                    $("#dialog-tbl-justificacion").dialog({
                        autoOpen: false,
                        height: 500,
                        width: 600,
                        draggable: false,
                        modal: true,
                        buttons: {
                            "Aceptar": function() {
                                if ($("#justificacionAuto").val().trim().length > 0) {
                                    capturaJustificacionesSaveJustificacion();
                                    $("#justificacionAuto").val("");
                                    $("#idJustificacion").val("-1");
                                    $("#errorComentario").html("");
                                    $("#cancelEdit").addClass("hidden");
                                    $(this).dialog("close");
                                } else {
                                    $("#justificacionAuto").val("");
                                    $("#idJustificacion").val("-1");
                                    $("#errorComentario").html("La justificacion es requerida");
                                }

                            },
                            "Cancelar": function() {
                                $("#errorComentario").html("");
                                $("#cancelEdit").addClass("hidden");
                                $(this).dialog("close");
                            }
                        }, close: function() {
                            $("#justificacionAuto").val("");
                            $("#idJustificacion").val("-1");
                            $("#errorComentario").html("");
                            $("#cancelEdit").addClass("hidden");
                            $(this).dialog("close");
                        }
                    });
                });
            </script>       
            <div id="mensaje" style="position: relative"></div>
    </form>
    <form id='frmRegresa' method='POST' action="<%=urlRegresa%>">
        <input type="hidden" id="ramoId" name="ramoId" value="<%=ramoId%>" />
    </form>



</div>
<jsp:include page="template/piePagina.jsp" />
<script>
    capturaJustificacionesInicializarDataTable("tbl-Justificaciones");
</script>
<%
    if (caratulaBean != null) {
        caratulaBean.resultSQLDesconecta();
    }
%>

</html>

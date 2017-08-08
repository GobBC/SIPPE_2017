<%-- 
    Document   : ampliacionReduccion
    Created on : Jan 5, 2016, 10:38:28 AM
    Author     : ugarcia
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Usuario"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
    <title>Ampliación/Reducción</title>    
</head>

<html>
    <jsp:include page="template/encabezado.jsp" />

    <%
        if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }
        request.setCharacterEncoding("UTF-8");
        Ramo ramoTemp = null;
        Caratula caratulaStatus = null;
        String ramoSession = new String();
        //ResultSQL sql = new ResultSQL();
        ArrayList<Caratula> arrCaratulas = null;
        boolean bFiltraEstatusAbiertas = true;
        String sDisabledCaratula = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat mf = new SimpleDateFormat("MM");
        NumberFormat dFormat = NumberFormat.getInstance(Locale.US);
        BigDecimal totalAmp = new BigDecimal(0);
        AmpliacionReduccionBean ampRedBean = null;
        CaratulaBean caratulaBean = null;
        Bitacora bitacora = new Bitacora();
        Usuario usuario = new Usuario();
        MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<AmpliacionReduccionMeta> ampReduccionMetaList = new ArrayList<AmpliacionReduccionMeta>();
        List<AmpliacionReduccionAccion> ampReduccionAccionList = new ArrayList<AmpliacionReduccionAccion>();
        List<AmpliacionReduccionAccionReq> ampReduccionAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();

        String rol = new String();
        String tipoDependencia = new String();
        String appLogin = new String();
        String dateFormat = new String();
        String urlRegresa = new String();
        String displayMov = new String();
        String displayBotones = new String();
        String displayEnvio = new String();
        String displayCancela = new String();
        String estatus = "X";
        String impacto = new String();
        String displaybuttos = new String();
        String tipoOficio = new String();
        String displayFolioRel = new String();
        String ramoCons = new String();
        String sDisabledComboCaratula = "";
        String caratulaEstatus = "A";
        String sMuestraUsuario = "";
        String ramoInList = "";
        String caratulaDisable = new String();
        String strSelected = new String();

        boolean isSameUser = false;
        boolean isParaestatal = false;
        boolean isObraPublica = false;
        boolean autorizar = false;
        boolean isActual = true;
        boolean fechaContable = false;

        int folio = 0;
        int monthAct = 0;
        int year = 0;
        int mesActual = 0;
        int tipoFlujo = 0;
        int opcion = 0;
        int nuevo = 0;
        try {
            if (session.getAttribute("reprogramacion") != null) {
                session.removeAttribute("reprogramacion");
            }
            if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
                session.removeAttribute("ampliacionReduccion");
            }
            if (session.getAttribute("transferencia") != null) {
                session.removeAttribute("transferencia");
            }
            if (session.getAttribute("accionReqIdNegativo") != null && session.getAttribute("accionReqIdNegativo") != "") {
                session.removeAttribute("accionReqIdNegativo");
            }
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }

            if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                appLogin = (String) session.getAttribute("strUsuario");
            }
            if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
                rol = (String) session.getAttribute("strRol");
            }
            if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
            if (Utilerias.existeParametro("folio", request)) {
                folio = Integer.parseInt(request.getParameter("folio"));
            }
            if (Utilerias.existeParametro("urlRegresa", request)) {
                urlRegresa = request.getParameter("urlRegresa");
            }
            if (Utilerias.existeParametro("tipoOficio", request)) {
                tipoOficio = request.getParameter("tipoOficio");
            }
            if (request.getParameter("autorizacion") != null && !request.getParameter("autorizacion").equals("")) {
                autorizar = Boolean.valueOf(request.getParameter("autorizacion"));
            }
            if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
                estatus = request.getParameter("estatus");
            }
            if (request.getParameter("ramoInList") != null && !request.getParameter("ramoInList").equals("")) {
                ramoInList = request.getParameter("ramoInList");
            }
            if (Utilerias.existeParametro("tipoFlujo", request)) {
                tipoFlujo = Integer.parseInt(request.getParameter("tipoFlujo"));
            }
            if (Utilerias.existeParametro("ramoCons", request)) {
                ramoCons = request.getParameter("ramoCons");
            }
            if (Utilerias.existeParametro("opcion", request)) {
                opcion = Integer.parseInt(request.getParameter("opcion"));
            }
            if (Utilerias.existeParametro("nuevo", request)) {
                nuevo = Integer.parseInt(request.getParameter("nuevo"));
            }
            if (estatus.isEmpty()) {
                estatus = "X";
            }
            if (session.getAttribute("accionIdNegativo") != null && !session.getAttribute("accionIdNegativo").equals("")) {
                session.removeAttribute("accionIdNegativo");
            }
            if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
                ramoSession = (String) session.getAttribute("ramoAsignado");
            }
            /*sql.setStrServer(request.getHeader("host"));
             sql.setStrUbicacion(getServletContext().getRealPath("").toString());
             sql.resultSQLConecta(sql.getsConexionBD());
             */

            //autorizar = "S";  //TEST
            ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
            ampRedBean.setStrServer((request.getHeader("Host")));
            ampRedBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            ampRedBean.resultSQLConecta(tipoDependencia);

            caratulaBean = new CaratulaBean(tipoDependencia);
            caratulaBean.setStrServer((request.getHeader("Host")));
            caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            caratulaBean.resultSQLConecta(tipoDependencia);

            ramoList = ampRedBean.getRamosByUsuario(year, appLogin);
            date = ampRedBean.getResultSQLgetServerDate();
            cal.setTime(date);
            if (year < cal.get(cal.YEAR)) {
                cal.set(cal.DATE, 31);
                cal.set(cal.MONTH, 11);
                cal.set(cal.YEAR, year);
                date = cal.getTime();
                isActual = false;
            }
            mesActual = Integer.parseInt(mf.format(date));
            dateFormat = df.format(date);
            movimiento.setJutificacion(new String());
            movimiento.setObsRechazo(new String());
            isParaestatal = ampRedBean.getResultSQLisParaestatal();

            if (folio != 0) {
                if (!Utileria.isTipoOficioRequerido(estatus)) {
                    movimiento = ampRedBean.getMovimientosAmpliacionReduccion(folio, year, mesActual, ramoSession);
                } else {
                    if (estatus.equals("K")) {
                        movimiento = ampRedBean.getMovimientosAmpliacionReduccionRechazado(folio, year, tipoOficio, mesActual, ramoSession);
                    } else {
                        movimiento = ampRedBean.getMovimientosAmpliacionReduccionByTipoOficio(folio, year, tipoOficio, mesActual, ramoSession);
                    }
                }

                //ampRedBean.resultSQLDesconecta();
                //ampRedBean.resultSQLConecta(tipoDependencia);                
                
                if (isParaestatal && !ampRedBean.getResultSqlGetIsAyuntamiento()) {
                    if (movimiento.getMovCaratula().getsIdCaratula() == 0) {
                        movimiento.getMovCaratula().setsIdCaratula(-2);
                    } else {
                        caratulaStatus = caratulaBean.getResultSQLCaratulaByYearIdRamoIdCaratula(String.valueOf(movimiento.getMovCaratula().getiYear()), movimiento.getMovCaratula().getsRamo(), movimiento.getMovCaratula().getsIdCaratula());

                        if (caratulaStatus != null) {
                            caratulaEstatus = caratulaStatus.getiStatus();
                        }

                        if (caratulaEstatus.equals("C")) {
                            sDisabledComboCaratula = "disabled";
                            bFiltraEstatusAbiertas = false;
                        }
                    }
                }
                usuario = ampRedBean.getInfoUsuario(movimiento.getAppLogin(), year);
                if (usuario.getAppLogin() == null) {
                    usuario.setAppLogin(movimiento.getAppLogin().trim());
                }
                session.setAttribute("ampliacionReduccion", movimiento);
            } else {
                displayMov = "style='display:none;'";
                movimiento.setObsRechazo(new String());
                usuario = ampRedBean.getInfoUsuario(appLogin, year);
                usuario.setAppLogin(appLogin);
            }
            if (appLogin.equals(usuario.getAppLogin()) || autorizar || rol.equals(ampRedBean.getResultSQLGetRolesPrg())) {
                isSameUser = true;
            }
            if (isParaestatal && !ampRedBean.getResultSqlGetIsAyuntamiento()) {
                if (folio != 0) {
                    if (movimiento.getMovCaratula().getsRamo() == null) {
                        arrCaratulas = caratulaBean.getCaratulas(String.valueOf(year), ramoSession, bFiltraEstatusAbiertas, 1, false);
                    } else {
                        arrCaratulas = caratulaBean.getCaratulas(String.valueOf(movimiento.getMovCaratula().getiYear()), movimiento.getMovCaratula().getsRamo(), bFiltraEstatusAbiertas, 1, false);
                    }
                } else {
                    arrCaratulas = caratulaBean.getCaratulas(String.valueOf(year), ramoSession, bFiltraEstatusAbiertas, 1, false);
                }
            }
            if (autorizar || ((estatus.equals("X") || estatus.equals("R")) && folio != 0)) {
                displayCancela = "display: inline;";
            } else {
                displayCancela = "display: none;";
            }
            if (movimiento.getFolioRelacionado() == 0) {
                displayFolioRel = "display:none;";
            } else {
                displayFolioRel = "display:grid;";
            }
            if (urlRegresa.isEmpty()) {
                urlRegresa = "menu.jsp";
            }
            if (movimiento.getCapturaEspecial() != null && movimiento.getCapturaEspecial().equals("S")) {
                fechaContable = true;
            } else {
                fechaContable = false;
            }
            ampReduccionMetaList = movimiento.getAmpReducMetaList();
            ampReduccionAccionList = movimiento.getAmpReducAccionList();
            ampReduccionAccionReqList = movimiento.getAmpReducAccionReqList();

            if ((folio != 0) && (!isParaestatal)) {
                isObraPublica = ampRedBean.getResultSQLValidaOficioSINVP(folio);
            } else {
                isObraPublica = false;
            }
            if (ampReduccionMetaList.size() > 0 || ampReduccionAccionList.size() > 0) {
                if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientosAmpliacionRed()' checked/>";
                    displayBotones = "display: block;";
                    displaybuttos = "<script>var selMeta = $('#selMeta').val(); "
                            + "if ($('#chk-imp-prog').is(':checked') && selMeta === '-1') {"
                            + "     $('#btn-add-meta').css('display', 'block');"
                            + "    $('#btn-edit-meta').css('display', 'none');"
                            + "    $('#btn-add-accion').css('display', 'block');"
                            + "    $('#btn-edit-accion').css('display', 'none');"
                            + " } else if ($('#chk-imp-prog').is(':checked') && selMeta !== '-1') {"
                            + "     $('#btn-add-meta').css('display', 'none');"
                            + "     $('#btn-edit-meta').css('display','block');"
                            + "     $('#btn-add-accion').css('display', 'block');"
                            + "     $('#btn-edit-accion').css('display', 'none');"
                            + "  } </script>";
                } else {
                    impacto = "<input type='checkbox' id='chk-imp-prog'  checked disabled/>";
                    displayBotones = "display: block;";
                }
            } else {
                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientosAmpliacionRed()' />";
                    displayBotones = "display: none;";
                } else {
                    impacto = "<input type='checkbox' id='chk-imp-prog'  disabled/>";
                    displayBotones = "display: none;";
                }
            }
            if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("Z")) && folio != 0) {
                displayEnvio = "width: 55px;";
            } else {
                displayEnvio = "width: 55px;display: none;";
            }


    %>
    <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
    <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
    <script src="librerias/_justificaciones.js" type="text/javascript"></script>
    <div Id="TitProcess"><label >Ampliaci&oacute;n / Reducci&oacute;n<label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <%                if (autorizar) {
            %>
            <button type="button" id="btnAceptar" class="btnbootstrap btn-aceptar" onclick="validaMetasAccionesHabilitadas(false);">
                <br/> <small> Aceptar </small>
            </button>
            <%
                if (!isObraPublica) {%>
            <button type="button" id="btnRechazar" class="btnbootstrap btn-rechazar" onclick="getRechazaMov(8)">
                <br/> <small> Rechazar </small>
            </button>
            <%}
                }
                if (isSameUser) {%>
            <button type="button" id="btnCancelar" style="<%=displayCancela%>" class="btnbootstrap btn-cancelar" onclick="getCancelaMov(7)">
                <br/> <small> Cancelar </small>
            </button>
            <%}
                if (!autorizar && isSameUser) {
                    //enviarAmpliacionReduccion() Para enviar a autorizar
%>
            <button type="button" id="btnEnviarAutorizar" style="<%=displayEnvio%>" class="btnbootstrap btn-enviar" onclick="movimientosJustificacionesValMovsAsig('A')">
                <br/> <small style="padding-top: 5px;display: block;line-height: 1;"> Enviar a autorizaci&oacute;n </small>
            </button>
            <%
                }
                /**
                 * Para la cancelación por el usuario *
                 */
                //if(folio != 0 && estatus.equals("X")){
%>
            <%--
                <button type="button" style="<%=displayEnvio%>" class="btnbootstrap btn-cancelar" onclick="cancelarMovimiento('A','ampliacionReduccion')">
                    <br/> <small> Cancelar folio </small>
                </button>
            <%
                }
            %>
            --%>
            <button type="button" id="btnRegresar" class="btnbootstrap btn-atras" onclick="regresarAutorizacion()">
                <br/> <small> Regresar </small>
            </button>
            <button type="button" id="btnInicio" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div" style="width: 60%">

        <form id="frmRegresa" method="POST" action="<%=urlRegresa%>">            
            <input type="hidden" id="estatus" name="estatus" value="<%=estatus%>" />
            <input type="hidden" id="ramoInList" name="ramoInList" value="<%=ramoInList%>" />
            <input type="hidden" id="tipoMov" name="tipoMov" value="A" />
            <input type="hidden" id="dateFor" name="dateFor" value="<%=dateFormat%>" />
            <input type="hidden" id="isActual" name="isActual" value="<%=isActual%>" />
            <input type="hidden" id="mesPPTO" name="mesPPTO" value="<%=mesActual%>" />
            <input type="hidden" id="tipoOficio" name="tipoOficio" value="<%=tipoOficio%>" />
            <input type="hidden" id="fechaContabilidad" name="fechaContabilidad" value="<%=fechaContable%>" /> 
            <input type="hidden" id="opcion" name="opcion" value="<%=opcion%>" />
            <input type="hidden" id="folio" name="folio" value="<%=folio%>" />
            <input type="hidden" id="nuevo" name="nuevo" value="<%=nuevo%>" />
            <input type="hidden" id="errorGrabar" name="errorGrabar" value="" />
            <input type="hidden" id="autorizacion" name="autorizacion" value="<%=autorizar%>" />
            <input type="hidden" id="ramoCons" name="ramoCons" value="<%=ramoCons%>" />
            <input type="hidden" id="comentario" name="comentario" value="" />
        </form>
        <br/>
        <%
            if (isParaestatal && !ampRedBean.getResultSqlGetIsAyuntamiento()) {
        %>

        <div class='caratula'>  

            <table id="tblComboCaratula">
                <tr>
                    <td><div id='txtCaratula' >Caratula:</div> </td>
                    <td>
                        <%
                            sDisabledCaratula = "style='Display:none'";
                            if (movimiento.getMovCaratula().getsIdCaratula() != 0) {
                                if (estatus.equals("X") || estatus.equals("R")) {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' onchange='evaluarCaratulaSelected()'>");
                                } else {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' disabled onchange='evaluarCaratulaSelected()'>");
                                }
                                sDisabledCaratula = "";
                            } else {
                                if (estatus.equals("X") || estatus.equals("R")) {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' onchange='evaluarCaratulaSelected()'>");
                                } else {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' disabled onchange='evaluarCaratulaSelected()'>");
                                }
                            }
                            out.print("<option " + sDisabledComboCaratula + " value='-1'> -- Seleccione un caratula -- </option>");
                            if (movimiento.getMovCaratula().getsIdCaratula() == -2) {
                                out.print("<option " + sDisabledComboCaratula + " value='-2'  selected > No aplica </option>");
                            } else {
                                out.print("<option " + sDisabledComboCaratula + " value='-2'> -- No aplica -- </option>");
                            }
                            if (arrCaratulas.size() > 0) {
                                for (Caratula objCaratula : arrCaratulas) {
                                    caratulaDisable = "";
                                    if (year != objCaratula.getiYear()) {
                                        caratulaDisable = " class='disabled' disabled ";
                                    } else {
                                        caratulaDisable = " class='enabled' ";
                                    }
                                    strSelected = "";
                                    if (objCaratula.getsIdCaratula() == movimiento.getMovCaratula().getsIdCaratula()) {
                                        movimiento.setMovCaratula(objCaratula);
                                        strSelected = " selected ";
                                    }
                                    if (year != objCaratula.getiYearSesion()) {
                                        out.print("<option " + caratulaDisable + sDisabledComboCaratula + strSelected + "  title='Sesi&oacute;n " + objCaratula.getiYearSesion() + "' value=" + objCaratula.getsIdCaratula() + "   >" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>");
                                    } else {
                                        out.print("<option " + caratulaDisable + sDisabledComboCaratula + strSelected + "  value=" + objCaratula.getsIdCaratula() + "   >" + objCaratula.getStrTipoModificacionDescr() + " " + objCaratula.getsDescr() + " </option>");
                                    }
                                }
                            }
                            out.print("</select>");

                        %>
                    </td>
                </tr>
            </table>          


            <%                out.print("<br>");
                out.print("<div id='infoCaratula' class='info-usuario'>");
                if (movimiento.getMovCaratula().getsIdCaratula() != 0 && movimiento.getMovCaratula().getsIdCaratula() != -2) {

                    out.print(" <table>");
                    out.print("     <tr>");
                    out.print("         <td>");
                    out.print("             <label>Fecha Registro:</label>");
                    out.print("         </td>");
                    out.print("         <td>");
                    if (movimiento.getMovCaratula().getsFechaRegistro() != null) {
                        out.print("             <input id='fechaRegistro' class='info-usuario-der'  value='" + df.format(formatter.parse(movimiento.getMovCaratula().getsFechaRegistro())) + "' disabled/>");
                    } else {
                        out.print("             <input id='fechaRegistro' class='info-usuario-der'  value='" + "" + "' disabled/>");
                    }
                    out.print("         </td>");
                    out.print("         <td>");
                    out.print("             <label>Numero Sesi&oacute;n:</label>");
                    out.print("         </td>");
                    out.print("         <td>");
                    out.print("             <input id='numeroSesion' class='info-usuario-der'  value='" + movimiento.getMovCaratula().getsNumSesionDescr() + "' disabled/>");
                    out.print("         </td>");
                    out.print("     </tr>");
                    out.print("     <tr>");
                    out.print("         <td>");
                    out.print("             <label>Fecha Sesi&oacute;n:</label>");
                    out.print("         </td>");
                    out.print("         <td>");
                    if (movimiento.getMovCaratula().getsFechaRevision() != null) {
                        //out.print("             <input id='fechaRevision' class='info-usuario-der'  value='" + df.format(formatter.parse(movimiento.getMovCaratula().getsFechaRevision())) + "' disabled/>");
                        out.print("             <input id='fechaRevision' class='info-usuario-der'  value='" + movimiento.getMovCaratula().getsFechaRevision() + "' disabled/>");
                    } else {
                        out.print("             <input id='fechaRevision' class='info-usuario-der'  value='" + "" + "' disabled/>");
                    }
                    out.print("         </td>");
                    out.print("         <td>");
                    out.print("             <label>Tipo Sesi&oacute;n:</label>");
                    out.print("         </td>");
                    out.print("         <td>");
                    out.print("             <input id='tipoSesion' class='info-usuario-der'  value='" + movimiento.getMovCaratula().getsTipoSesionDescr() + "' disabled/>");
                    out.print("         </td>");
                    out.print("     </tr>");
                    out.print(" </table>");

                }

                out.print("</div>");

            %>        

        </div>
        <%            }
        %>
        <br>
        <div class='info-usuario'>
            <table id="tbl-info-cods">
                <tr>
                    <td>
                        <label>Fecha:</label>                        
                    </td>
                    <td>
                        <input class="info-usuario-der" id="inp-date-usr"  value="<%=dateFormat%>" disabled/>
                    </td>
                    <td>
                        <label>Ramo:</label>                        
                    </td>
                    <td>
                        <%
                            if (folio != 0) {
                                ramoTemp = new Ramo();
                                ramoTemp = ampRedBean.getResultGetRamoByRamoIdYear(movimiento.getRamo(), year);
                                if (ramoTemp != null) {
                        %>
                        <input class="info-usuario-izq" id="inp-ramo-usr" value="<%=ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr()%>" disabled/>
                        <%
                        } else {
                        %>
                        <input class="info-usuario-izq" id="inp-ramo-usr" value="<%=usuario.getRamo() + "-" + usuario.getRamoDescr()%>" disabled/>
                        <%
                            }
                        %>
                        <%
                        } else {
                        %>
                        <input class="info-usuario-izq" id="inp-ramo-usr" value="<%=usuario.getRamo() + "-" + usuario.getRamoDescr()%>" disabled/>
                        <%
                            }
                        %>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Folio:</label>                        
                    </td>
                    <td>
                        <input class="info-usuario-der" id="inp-folio-usr" disabled value="<%=folio%>"/>
                    </td>
                    <td>
                        <label>Depto:</label>                        
                    </td>
                    <td>
                        <input class="info-usuario-izq" id="inp-depto-usr" value="<%=usuario.getDepto() + "-" + usuario.getDeptoDescr()%>" disabled/>
                    </td>
                </tr>
                <tr id="tr-folio-rel" style="<%=displayFolioRel%>">
                    <td>
                        <label>Folio relacionado:</label>                        
                    </td>
                    <td>
                        <input class="info-usuario-der" id="inp-folio-rel-usr" disabled value="<%=movimiento.getFolioRelacionado()%>"/>
                    </td>
                </tr>
            </table>
        </div>
        <br/>
        <div style=" text-align: right; width: 104%;">
            <%
                if (movimiento.getAppLogin() != null) {
                    sMuestraUsuario = (String) movimiento.getAppLogin();
                } else {
                    sMuestraUsuario = (String) usuario.getAppLogin();
                }
            %>
            <label>Usuario que gener&oacute; oficio:&nbsp;</label><input id="applogingenero" value="<%=sMuestraUsuario%>" style="width:110px;" disabled/>
        </div>
        <br/>
        <div id='disabledCaratula' <%=sDisabledCaratula%> > 
            <%
                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
            %>
            <div class="info-codigo">              
                <table id="tbl-info-movs">
                    <tr>
                        <td>
                            <label> Impacto program&aacute;tico</label>
                        </td>
                        <td>
                            <%
                                out.print(impacto);
                            %>
                        </td>
                    </tr>
                    <tr>
                        <%if (!estatus.equals("X") && !estatus.equals("R")) {%>
                        <td>Comentario(s): <a onclick="getComentarios()"><span class="fa fa-eye botones-img"></span></a></td>
                        <td>
                            <%
                                if (movimiento.getComentarioAutorizacion() == null) {
                                    movimiento.setComentarioAutorizacion("");
                                    }%>
                            <textarea id='txt-area-coment' class='no-enter' style='text-transform:uppercase;'  disabled ><%=movimiento.getComentarioAutorizacion()%></textarea>
                        </td>

                        <% } %>
                        <td></td>
                    </tr> 
                    <tr>
                        <%if ((estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("T") || estatus.equals("A")) && !isParaestatal) {%>
                        <td>P&aacute;rrafo oficio: </td>
                        <td>
                            <%
                                if (movimiento.getComentarioPlaneacion() == null) {
                                    movimiento.setComentarioPlaneacion("");
                                    }%>
                            <%
                                    if (!estatus.equals("T") && !estatus.equals("A")) {%>
                            <textarea id='txt-area-coment-plan' class='no-enter' maxlength="600" style='text-transform:uppercase;'  ><%=movimiento.getComentarioPlaneacion()%></textarea>
                            <% } else {%>
                            <textarea id='txt-area-coment-plan' class='no-enter' maxlength="600" style='text-transform:uppercase;'  disabled ><%=movimiento.getComentarioPlaneacion()%></textarea>
                            <% } %>
                        </td>

                        <% } %>
                        <td></td>
                    </tr>
                    <tr>
                        <td>Justificaci&oacute;n:</td>
                        <td>
                            <%if (folio > 0) {
                                    if (movimiento.getJutificacion() == null) {
                                        movimiento.setJutificacion("");
                                    }
                                    if ((!estatus.equals("A") && !estatus.equals("C") || autorizar) && isSameUser) {
                                        out.print("<textarea id='txt-area-justif' class='no-enter' style='text-transform:uppercase;' >" + movimiento.getJutificacion() + "</textarea>");
                                    } /*else {
                                     out.print("<textarea id='txt-area-justif' style='text-transform:uppercase;'  disabled >" + movimiento.getJutificacion() + "</textarea>");
                                     }*/

                                } else {
                                    if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {%>
                            <textarea id="txt-area-justif" class='no-enter' style='text-transform:uppercase;' ></textarea>
                            <%} else {%>
                            <textarea id="txt-area-justif" class='no-enter' style='text-transform:uppercase;' disabled ></textarea>
                            <%}%>
                            <%}%>
                        </td>
                        <td></td>
                    </tr>                
                    <%
                        if (!movimiento.getObsRechazo().trim().isEmpty()) {
                    %>
                    <tr>
                        <td>Observaci&oacute;n de rechazo:</td>
                        <td>
                            <textarea id="txt-area-obs-rechazo" class='no-enter' style='text-transform:uppercase;' disabled><%out.print(movimiento.getObsRechazo().trim());%></textarea>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td> Ramo: </td>
                        <td>
                            <select id="selRamo" name="selRamo" onchange='getProgramasByRamoUsuario();
                                    mostrarEdicionMeta();
                                    LimpiaImptAmpRed();'>
                                <option value="-1"> -- Seleccione un ramo -- </option>
                                <%
                                    if (ramoList.size() > 0) {
                                        for (Ramo ramo : ramoList) {
                                            out.print("<option value=" + ramo.getRamo() + ">" + ramo.getRamo() + "-" + ramo.getRamoDescr() + " </option>");
                                        }
                                    }
                                %>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Programa: </td>
                        <td>
                            <select id="selPrg" name="selPrg" onchange='getProyectosByProgramaUsuario();
                                    mostrarEdicionMeta();
                                    LimpiaImptAmpRed();'>
                                <option value="-1"> -- Seleccione un programa -- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Proyecto/Actividad: </td>
                        <td>
                            <select id="selProy" name="selProys" onchange='getMetasByProyectoUsuarioAmpliacionReduccion();
                                    mostrarEdicionMeta();
                                    LimpiaImptAmpRed();'>
                                <option value="-1"> -- Seleccione un proyecto/actividad -- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Meta: </td>
                        <td>
                            <select id="selMeta" name="selMeta" onchange='getAccionByMetaUsuario();
                                    mostrarEdicionMeta();
                                    LimpiaImptAmpRed();'>
                                <option value="V"> -- Seleccione o cree una meta -- </option>
                            </select>
                        </td>
                        <td>                        
                            <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {%>
                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-meta" style="<%=displayBotones%>" onclick="getInfoMetaAmpliacionReduccion('-1', '<%=estatus%>')"/>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-meta" style="<%=displayBotones%>" onclick="getInfoMetaAmpliacionReduccionRecalendarizacion('-1', 'X')"/>
                            <%}%>
                        </td>
                    </tr>
                    <tr>
                        <td> Acci&oacute;n: </td>
                        <td>
                            <select id="selAccion" name="selAccion" onchange='getPartidaByAccionAmplRed();
                                    mostrarEdicionAccion();
                                    LimpiaImptAmpRed();'>
                                <option value="V"> -- Seleccione o cree una acci&oacute;n -- </option>
                            </select>
                        </td>
                        <td>
                            <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {%>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-accion" style="<%=displayBotones%>" onclick="getInfoAccionAmpliacionReduccionRecalendarizacion('-1', 'X')"/>
                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-accion" style="<%=displayBotones%>" onclick="getInfoAccionAmpliacionReduccion('-1', '<%=estatus%>')"/>
                            <%}%>
                        </td> 
                    </tr>
                    <tr>
                        <td> Partida: </td>
                        <td>
                            <select id="selPartida" name="selPartida" onchange='getRelLaboralByPartidaAmpRed();
                                    LimpiaImptAmpRed();'>
                                <option value="-1"> -- Seleccione una partida-- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr id="tr-rel-laboral" style="display: none;"> <!--diplay:grid para cuando se tenga que mostrar grid-->
                        <td> Relalci&oacute;n laboral: </td>
                        <td>
                            <select id="selRelLaboral" name="selRelLaboral" onchange='getFuenteFinanciamientoByRelLaboralAmpRed();
                                    LimpiaImptAmpRed();'>
                                <option value="-1"> -- Seleccione una relalci&oacute;n laboral-- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Fuente de financiamiento: </td>
                        <td>
                            <select id="selFuente" name="selFuente" onchange='getAsignadoPPTOByMes();
                                    LimpiaImptAmpRed();'>
                                <option value="-1"> -- Seleccione una fuente de financiamiento-- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>     
                </table>                    
                <div id="div-cantidades-cod">
                    <div>Disponible:</div>
                    <input id="inp-txt-disp-ampred" type="text" value="0.0" disabled/>
                    <div>Importe:</div>
                    <input id="inp-txt-impor-ampred" type="text" value="0.0" onblur="agregarFormato('inp-txt-impor-ampred');
                            getBotonesAmpliacionReduccion();" 
                           onkeyup="validaMascara('inp-txt-impor-ampred')" />
                    <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {%>
                    <input type="button" class="btnbootstrap-drop btn-add" id="btn-reduccion" style="<%=displayBotones%>" onclick="validaCodigoReduccion()"/>
                    <%}%>
                </div>
                <div id="tbl-req-ampred" style="display:none;">
                    <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {%>
                    <input type="button" value="Insertar requerimiento" id="btn-ampliacion"  onclick="getAmplRedMovimientoAccionrReq()"/>
                    <%}%>
                </div> 
            </div>
            <%
                out.print(displaybuttos);
            } else {
            %>
            <div class="info-codigo">              
                <table id="tbl-info-movs">
                    <tr>
                        <%if (!estatus.equals("X") && !estatus.equals("R")) {%>
                        <td><label>Comentario(s): &nbsp;</label><a onclick="getComentarios()"><span class="fa fa-eye botones-img"></span></a></td>
                        <td>
                            <%
                                if (movimiento.getComentarioAutorizacion() == null) {
                                    movimiento.setComentarioAutorizacion("");
                                    }%>
                            <textarea id='txt-area-coment' class='no-enter' style='text-transform:uppercase;'  disabled ><%=movimiento.getComentarioAutorizacion()%></textarea>
                        </td>

                        <% } %>
                        <td></td>
                    </tr> 
                    <tr>
                        <%if ((estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("T") || estatus.equals("A")) && !isParaestatal) {%>
                        <td><label>P&aacute;rrafo oficio:</label></td>
                        <td>
                            <%
                                if (movimiento.getComentarioPlaneacion() == null) {
                                    movimiento.setComentarioPlaneacion("");
                                    }%>
                            <%
                                    if (!estatus.equals("T") && !estatus.equals("A")) {%>
                            <textarea id='txt-area-coment-plan' class='no-enter' maxlength="600" style='text-transform:uppercase;'  ><%=movimiento.getComentarioPlaneacion()%></textarea>
                            <% } else {%>
                            <textarea id='txt-area-coment-plan' class='no-enter' maxlength="600" style='text-transform:uppercase;'  disabled ><%=movimiento.getComentarioPlaneacion()%></textarea>
                            <% } %>
                        </td>

                        <% } %>
                        <td></td>
                    </tr>
                    <tr>
                        <td>
                            <%--Para autorización --%>
                            <label>Justificaci&oacute;n</label>
                        </td>
                        <td>
                            <%if (folio > 0) {
                                    if (movimiento.getJutificacion() == null) {
                                        movimiento.setJutificacion("");
                                    }
                                    if ((!estatus.equals("A") && !estatus.equals("C") && autorizar) && isSameUser) {
                                        out.print("<textarea class='no-enter' id='txt-area-justif' >" + movimiento.getJutificacion() + "</textarea>");
                                    } else {
                                        out.print("<textarea class='no-enter' id='txt-area-justif' disabled >" + movimiento.getJutificacion() + "</textarea>");
                                    }
                                } else {
                                    if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {%>
                            <textarea id="txt-area-justif" class='no-enter' style='text-transform:uppercase;' ></textarea>
                            <%} else {%>
                            <   textarea id="txt-area-justif" class='no-enter' style='text-transform:uppercase;' disabled ></textarea>
                            <%}%>
                            <%}%>
                        </td>
                    </tr>
                </table>
                <%
                    }
                %>
                <div id="mensaje"></div>
                <fieldset id="fldst-ampred">
                    <legend> Ampliaci&oacute;n / Reducci&oacute;n: </legend>
                    <table id="tbl-ampred-reqs">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Ramo</th>
                                <th>Programa</th>
                                <th>Proy./Act.</th>
                                <th>Meta</th>
                                <th>Acci&oacute;n</th>
                                <th>Depto.</th>
                                <th>Partida</th>
                                <th>F.F.R.</th>
                                <th>Rel. Laboral</th>
                                <th>15%</th>
                                <th>Acumulado Movtos-Pptales</th>
                                <th>Disponible Acumulado</th>
                                <th>Importe Anual</th>
                                <th>Importe</th>
                                    <%if ((autorizar && estatus.equals("T")) && !isParaestatal) {%>
                                <th>Ingreso propio</th>
                                    <%}%>
                                    <%if ((autorizar || estatus.equals("A")) && (estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("T") || estatus.equals("A")) && ampRedBean.getRolNormativo(Integer.parseInt(rol)) == 1) {%>
                                <th>Considerar </th>
                                    <%} else {%>
                                <th></th>
                                    <%}%>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (AmpliacionReduccionAccionReq ampRedRequer : ampReduccionAccionReqList) {
                                    out.print("<tr>");
                                    out.print("<td></td>");
                                    out.print("<td>" + ampRedRequer.getRamo() + "</td>");
                                    out.print("<td>" + ampRedRequer.getPrograma() + "</td>");
                                    out.print("<td>" + ampRedRequer.getTipoProy() + "-" + ampRedRequer.getProy() + "</td>");
                                    out.print("<td>" + ampRedRequer.getMeta() + "</td>");
                                    out.print("<td>" + ampRedRequer.getAccion() + "</td>");
                                    out.print("<td>" + ampRedRequer.getDepto() + "</td>");
                                    out.print("<td>" + ampRedRequer.getPartida() + "</td>");
                                    out.print("<td>" + ampRedRequer.getFuente() + "."
                                            + ampRedRequer.getFondo() + "."
                                            + ampRedRequer.getRecurso() + "</td>");
                                    out.print("<td>" + ampRedRequer.getRelLaboral() + "</td>");
                                    out.print("<td>" + dFormat.format(ampRedRequer.getQuincePor()) + "</td>");
                                    out.print("<td>" + dFormat.format(ampRedRequer.getAcumulado()) + "</td>");
                                    out.print("<td>" + dFormat.format(ampRedRequer.getDisponible()) + "</td>");
                                    out.print("<td>" + dFormat.format(ampRedRequer.getDisponibleAnual()) + "</td>");
                                    out.print("<td>" + dFormat.format(ampRedRequer.getImporte()) + "</td>");
                                    if ((autorizar && estatus.equals("T")) && !isParaestatal && (ampRedRequer.getTipoMovAmpRed().equals("A") || ampRedRequer.getTipoMovAmpRed().equals("C"))) {
                                        if (ampRedRequer.getIsIngresoPropio().equals("S")) {
                                            out.print("<td><input type='checkbox' id='ingPropio' name='ingPropio'  " + "onchange='changeIngresoPropio(\"" + ampRedRequer.getConsecutivo() + "\")' checked ></td>");
                                        } else {
                                            out.print("<td><input type='checkbox' id='ingPropio' name='ingPropio'  " + "onchange='changeIngresoPropio(\"" + ampRedRequer.getConsecutivo() + "\")' ></td>");
                                        }
                                    }
                                    if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {
                                        if (ampRedRequer.getTipoMovAmpRed().equals("R")) {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                    + "onclick='getInfoEdicionAccionRequerimientoAmpReq(\"" + ampRedRequer.getIdentidicador() + "\",\"" + estatus + "\",\""
                                                    + ampRedRequer.getRamo() + "\",\"" + ampRedRequer.getPrograma() + "\",\"" + ampRedRequer.getMeta() + "\","
                                                    + "\"" + ampRedRequer.getAccion() + "\",\"" + ampRedRequer.getConsecutivo() + "\",\"R\")' />");
                                        } else {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                    + "onclick='getInfoEdicionAccionRequerimientoAmpReq(\"" + ampRedRequer.getIdentidicador() + "\",\"" + estatus + "\",\""
                                                    + ampRedRequer.getRamo() + "\",\"" + ampRedRequer.getPrograma() + "\",\"" + ampRedRequer.getMeta() + "\","
                                                    + "\"" + ampRedRequer.getAccion() + "\",\"" + ampRedRequer.getConsecutivo() + "\",\"A\")' />");
                                        }
                                        out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarAmpliacionReduccion(\"" + ampRedRequer.getIdentidicador() + "\",\"3\");' /></td>");
                                    } else {
                                        if (ampRedRequer.getTipoMovAmpRed().equals("R")) {
                                            if ((autorizar || estatus.equals("A")) && (estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("T") || estatus.equals("A")) && ampRedBean.getRolNormativo(Integer.parseInt(rol)) == 1) {
                                                if (ampRedRequer.getConsiderar().equals("S")) {
                                                    out.print("<td><input type='checkbox' id='considerar' name='considerar'  " + "onchange='changeConsiderarTransAmp(\"" + ampRedRequer.getConsecutivo() + "\",\"0\")' checked ></td>");
                                                } else {
                                                    out.print("<td><input type='checkbox' id='considerar' name='considerar'  " + "onchange='changeConsiderarTransAmp(\"" + ampRedRequer.getConsecutivo() + "\",\"0\")' ></td>");
                                                }
                                            }
                                            out.print("<td><a onClick='mostrarProgramacion(\"" + ampRedRequer.getRamo() + "\"," + ampRedRequer.getMeta() + "," + ampRedRequer.getAccion() + ")'><span class='fa fa-calendar botones-img'></span></a>"
                                                    + "<input type='button' class='btnbootstrap-drop btn-ver' "
                                                    + "onclick='getInfoEdicionAccionRequerimientoAmpReq(\"" + ampRedRequer.getIdentidicador() + "\",\"" + estatus + "\",\""
                                                    + ampRedRequer.getRamo() + "\",\"" + ampRedRequer.getPrograma() + "\",\"" + ampRedRequer.getMeta() + "\","
                                                    + "\"" + ampRedRequer.getAccion() + "\",\"" + ampRedRequer.getConsecutivo() + "\",\"R\")' />");
                                        } else {
                                            if (((estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("T") || estatus.equals("A") || estatus.equals("A")) && ampRedBean.getRolNormativo(Integer.parseInt(rol)) == 1) && isSameUser) {
                                                if (ampRedRequer.getConsiderar().equals("S")) {
                                                    out.print("<td><input type='checkbox' id='considerar' name='considerar'  " + "onchange='changeConsiderarTransAmp(\"" + ampRedRequer.getConsecutivo() + "\",\"0\")' checked ></td>");
                                                } else {
                                                    out.print("<td><input type='checkbox' id='considerar' name='considerar'  " + "onchange='changeConsiderarTransAmp(\"" + ampRedRequer.getConsecutivo() + "\",\"0\")' ></td>");
                                                }
                                            }
                                            out.print("<td> <a onClick='mostrarProgramacion(\"" + ampRedRequer.getRamo() + "\"," + ampRedRequer.getMeta() + "," + ampRedRequer.getAccion() + ")'><span class='fa fa-calendar botones-img'></span></a>"
                                                    + "<input type='button' class='btnbootstrap-drop btn-ver' "
                                                    + "onclick='getInfoEdicionAccionRequerimientoAmpReq(\"" + ampRedRequer.getIdentidicador() + "\",\"" + estatus + "\",\""
                                                    + ampRedRequer.getRamo() + "\",\"" + ampRedRequer.getPrograma() + "\",\"" + ampRedRequer.getMeta() + "\","
                                                    + "\"" + ampRedRequer.getAccion() + "\",\"" + ampRedRequer.getConsecutivo() + "\",\"A\")' />");
                                        }
                                    }
                                    out.print("</tr>");
                                    totalAmp = totalAmp.add(new BigDecimal(ampRedRequer.getImporte()));
                                }
                            %>
                            <tr>
                                <td></td> 
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>Total:</td> 
                                <td><%=dFormat.format(totalAmp.setScale(2, RoundingMode.HALF_UP))%></td>
                            </tr>
                        </tbody>                        
                    </table>
                </fieldset>
                <br/>
                <%
                    out.print("<fieldset id='fldst-movs' " + displayMov + " >");
                %>
                <legend> Movimientos program&aacute;ticos: </legend>
                <table id="tbl-movs" style="margin: 0px 0%;">
                    <thead>
                        <tr>
                            <th align="center" >Ramo</th>
                            <th align="center" >Meta</th>
                            <th align="center" >Acci&oacute;n</th>
                            <th align="center" >Estimaci&oacute;n original</th>
                            <th align="center" >Propuesta</th>
                            <th align="center" ></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (AmpliacionReduccionMeta movMeta : ampReduccionMetaList) {
                                out.print("<tr>");
                                out.print("<td>" + movMeta.getMovOficioMeta().getRamoId() + "</td>");
                                out.print("<td>" + movMeta.getMovOficioMeta().getMetaId() + "</td>");
                                out.print("<td></td>");
                                out.print("<td>" + dFormat.format(ampRedBean.getResultSQLGetSumaEstimacion(year, movMeta.getMovOficioMeta().getRamoId(), movMeta.getMovOficioMeta().getMetaId(), folio, estatus)) + "</td>");
                                out.print("<td>" + dFormat.format(movMeta.getPropuestaEstimacion()) + "</td>");
                                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                                    if (movMeta.getMovOficioMeta().getNva_creacion().equals("S")) {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaAmpliacionReduccionTabla"
                                                + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' />");
                                    } else {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaAmpliacionReduccionRecalendarizacionTabla"
                                                + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' />");
                                    }
                                    out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarAmpliacionReduccion(\"" + movMeta.getIdentificador() + "\",\"1\");' /></td>");
                                } else {
                                    if (movMeta.getMovOficioMeta().getNva_creacion().equals("S")) {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' onclick='getInfoMetaAmpliacionReduccionTabla"
                                                + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' />");
                                    } else {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' onclick='getInfoMetaAmpliacionReduccionRecalendarizacionTabla"
                                                + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                                + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' />");
                                    }
                                }
                                out.print("</tr>");
                            }

                            for (AmpliacionReduccionAccion movAccion : ampReduccionAccionList) {
                                out.print("<tr>");
                                out.print("<td>" + movAccion.getMovOficioAccion().getRamoId() + "</td>");
                                out.print("<td>" + movAccion.getMovOficioAccion().getMetaId() + "</td>");
                                out.print("<td>" + movAccion.getMovOficioAccion().getAccionId() + "</td>");
                                out.print("<td>" + dFormat.format(ampRedBean.getResultSQLGetSumaAccionEstimacion(year,
                                        movAccion.getMovOficioAccion().getRamoId(), movAccion.getMovOficioAccion().getMetaId(),
                                        movAccion.getMovOficioAccion().getAccionId(), folio, estatus)) + "</td>");
                                out.print("<td>" + dFormat.format(movAccion.getPropuestaEstimacion()) + "</td>");
                                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                                    if (movAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                + "onclick='getInfoAccionAmpliacionReduccionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' />");
                                    } else {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                + "onclick='getInfoAccionAmpliacionReduccionRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' />");
                                    }
                                    out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarAmpliacionReduccion(\"" + movAccion.getIdentificador() + "\",\"2\");' /></td>");

                                } else {
                                    if (movAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                                + "onclick='getInfoAccionAmpliacionReduccionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' />");
                                    } else {
                                        out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                                + "onclick='getInfoAccionAmpliacionReduccionRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                                + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' />");
                                    }
                                }
                                out.print("</tr>");
                            }
                        %>
                    </tbody>
                </table>
                </fieldset>
                <div id="PopUpZone"></div>
                <br/>
                <div>
                    <input type="hidden" value="<%=estatus%>" id="inpEstatus" />
                    <input type="hidden" value="V" id="inpEstatus" />
                    <input type="hidden" value="<%=tipoFlujo%>" id="tipoFlujo" />
                    <%if (autorizar || estatus.equals("R") || estatus.equals("X")) {%>
                    <input type="button" value="Guardar" id="btn-save-movs" onclick="enviarAutorizacionAmpliacionReduccion(false)"/>
                    <%}%>
                </div>        
            </div>
        </div>

        <%
            if ((!isParaestatal || ampRedBean.getResultSqlGetIsAyuntamiento()) && ampRedBean.getValidaContabilidad(year) && ampRedBean.isUsuarioCapturaEspecial(appLogin) && folio == 0) {

        %>
        <script type="text/javascript">
            $(function() {
                $("#dialog-confirm").dialog({
                    height: 170,
                    width: 400,
                    draggable: false,
                    modal: true,
                    buttons: {
                        "Si": function() {
                            $("#fechaContabilidad").val(true);
                            $(this).dialog("close");
                        },
                        "No": function() {
                            $("#fechaContabilidad").val(false);
                            $(this).dialog("close");
                        }
                    }
                });
            });
        </script>
        <div id="dialog-confirm" title="Fecha contable">
            <p><span class="ui-icon ui-icon-info" style="float:left; margin:0 7px 20px 0;"></span>¿Desea que el presupuesto se registre en a&ntilde;o contable?</p>
        </div>
        <%                }
            } catch (Exception ex) {
                bitacora.setStrServer(request.getHeader("host"));
                bitacora.setStrUbicacion(getServletContext().getRealPath(""));
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, request.getServletPath());
                bitacora.grabaBitacora();
            } finally {
                if (ampRedBean != null) {
                    ampRedBean.resultSQLDesconecta();
                }
                if (caratulaBean != null) {
                    caratulaBean.resultSQLDesconecta();
                }
            }
        %>
        <div id="dialog-tbl-comentario" title="Comentarios anteriores">
            <div style="max-height: 249px; overflow-y: auto; overflow-x:visible ">
                <table class="table table-striped" id="tbl-comentario">
                    <thead>
                    <th>
                        Usuario 
                    </th>
                    <th>
                        Descripci&oacute;n del comentario
                    </th>
                    <th>
                        Fecha
                    </th>
                    <th>
                    </th>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <p id="errorComentario" style="color:red"  ></p>
            <div> 
                <div class="col-md-12">                    
                    <label for="name">Comentario</label>
                </div>
                <div class="col-md-12">
                    <textarea  maxlength="600" type="text" class='no-enter' name="comentarioAuto" style="max-width: 100%; width: 100%" id="comentarioAuto" class="textarea-comentario text col-md-12" ></textarea>  
                </div>                
            </div>        
            <div class="col-md-12">
                <a onclick="cancelEdicion();" id="cancelEdit" class="fa fa-times-circle h3 hidden" style="cursor: pointer;">
                    <span>                        
                        Cancelar edici&oacute;n
                    </span>                    
                </a>
            </div>
            <input type="hidden" id="numComentario" value="0"/>
        </div>
        <div id="programacion" title="Programaci&oacute;n"></div>
        <script type="text/javascript">
            $(function() {
                $("#dialog-tbl-comentario").dialog({
                    autoOpen: false,
                    height: 500,
                    width: 600,
                    draggable: false,
                    modal: true,
                    buttons: {
                        "Aceptar": function() {
                            if ($("#comentarioAuto").val().trim().length > 0) {
                                $("#errorComentario").html("");
                                $("#comentario").val($("#comentarioAuto").val().toUpperCase());
                                updateComentarioAut();
                            } else {
                                $("#comentarioAuto").val("");
                                $("#errorComentario").html("El comentario es requerido");
                            }

                        },
                        "Cancelar": function() {
                            $("#errorComentario").html("");
                            $("#numComentario").val(0);
                            $("#cancelEdit").addClass("hidden");
                            $(this).dialog("close");
                        }
                    }, close: function() {
                        $("#comentarioAuto").val("");
                        $("#errorComentario").html("");
                        $("#tbl-comentario tbody").html("");
                        $("#numComentario").val(0);
                        $("#cancelEdit").addClass("hidden");
                        $(this).dialog("close");
                    }
                });
            });
        </script>
        <script src="librerias/js/movimientoJS/_movimiento.js" type="text/javascript"></script>
        <script src="librerias/_comentarios.js" type="text/javascript"></script>
        <jsp:include page="template/piePagina.jsp" />
</html>


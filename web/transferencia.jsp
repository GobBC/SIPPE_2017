<%-- 
    Document   : transferencia
    Created on : Jan 15, 2016, 11:14:43 AM
    Author     : jarguelles
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Usuario"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
    <title>Transferencia</title>    
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
        Usuario usuario = new Usuario();
        Caratula caratulaStatus = null;
        //ResultSQL sql = new ResultSQL();        
        CaratulaBean caratulaBean = null;
        TransferenciaBean transBean = null;
        Bitacora bitacora = new Bitacora();
        MovimientosTransferencia movimiento = new MovimientosTransferencia();
        ArrayList<Caratula> arrCaratulas = null;
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        List<Transferencia> transfDeleteList = new ArrayList<Transferencia>();
        List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
        List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
        Date date;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat dFormat = NumberFormat.getInstance(Locale.US);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String estatus = "X";
        String ramoInList = "";
        String impacto = new String();
        String sDisabledCaratula = "";
        String appLogin = new String();
        String dateFormat = new String();
        String urlRegresa = new String();
        String displayMov = new String();
        String tipoOficio = new String();
        String ramoSession = new String();
        String displayEnvio = new String();
        String displayCancela = new String();
        String justificacion = new String();
        String displayBotones = new String();
        String tipoDependencia = new String();
        String displayFolioRel = new String();
        String ramo = new String();
        String programa = new String();
        String partida = new String();
        String relLaboral = new String();
        String fuenteFin = new String();
        String proyecto = new String();
        String ramoCons = new String();
        String rol = new String();
        String sDisabledComboCaratula = "";
        String caratulaEstatus = "A";
        String sMuestraUsuario = new String();
        String caratulaDisable = new String();
        String strSelected = new String();

        BigDecimal totalImporte = new BigDecimal(0.0);
        boolean isParas = true;
        boolean inicio = true;
        boolean isActual = true;
        boolean isObraPublica = false;
        boolean autorizar = false;
        boolean transfWork = false;
        boolean contabilidad = false;
        boolean isParaestatal = false;
        boolean fechaContable = false;
        boolean bFiltraEstatusAbiertas = true;
        boolean isRedirected = false;
        boolean isSameUser = false;
        int meta = 0;
        int accion = 0;
        int year = 0;
        int folio = 0;
        int nuevo = 0;
        int opcion = 0;
        int mesActual = 0;
        int tipoFlujo = 0;
        long idCaratula = 0;

        try {
            if (Utilerias.existeParametro("trans-work", request)) {
                transfWork = Boolean.parseBoolean(request.getParameter("trans-work"));
            }
            if (session.getAttribute("reprogramacion") != null) {
                session.removeAttribute("reprogramacion");
            }
            if (session.getAttribute("ampliacionReduccion") != null) {
                session.removeAttribute("ampliacionReduccion");
            }
            if (session.getAttribute("transferencia") != null && !transfWork) {
                session.removeAttribute("transferencia");
            } else {
                if (session.getAttribute("transferencia") != null) {
                    movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
                }
            }
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }

            if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
                rol = (String) session.getAttribute("strRol");
            }

            if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                appLogin = (String) session.getAttribute("strUsuario");
            }
            if (Utilerias.existeParametro("isRedirected", request)) {
                isRedirected = true;
                if (Utilerias.existeParametro("ramo", request)) {
                    ramo = request.getParameter("ramo");
                }
                if (Utilerias.existeParametro("programa", request)) {
                    programa = request.getParameter("programa");
                }
                if (Utilerias.existeParametro("proyectoR", request)) {
                    proyecto = request.getParameter("proyectoR");
                }
                if (Utilerias.existeParametro("meta", request)) {
                    meta = Integer.parseInt(request.getParameter("meta"));
                }
                if (Utilerias.existeParametro("accion", request)) {
                    accion = Integer.parseInt(request.getParameter("accion"));
                }
                if (Utilerias.existeParametro("partida", request)) {
                    partida = request.getParameter("partida");
                }
                if (Utilerias.existeParametro("relLaboral", request)) {
                    relLaboral = request.getParameter("relLaboral");
                }
                if (Utilerias.existeParametro("fuente", request)) {
                    fuenteFin = request.getParameter("fuente");
                }
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
            if (Utilerias.existeParametro("autorizacion", request)) {
                autorizar = Boolean.valueOf(request.getParameter("autorizacion"));
            }
            if (Utilerias.existeParametro("inicio", request)) {
                inicio = Boolean.valueOf(request.getParameter("inicio"));
            }
            if (Utilerias.existeParametro("sameUser", request)) {
                isSameUser = Boolean.valueOf(request.getParameter("sameUser"));
            }
            if (Utilerias.existeParametro("estatus", request)) {
                estatus = request.getParameter("estatus");
            }
            if (Utilerias.existeParametro("ramoInList", request)) {
                ramoInList = request.getParameter("ramoInList");
            }
            if (Utilerias.existeParametro("tipoOficio", request)) {
                tipoOficio = request.getParameter("tipoOficio");
            }
            if (Utilerias.existeParametro("tipoFlujo", request)) {
                tipoFlujo = Integer.parseInt(request.getParameter("tipoFlujo"));
            }
            if (Utilerias.existeParametro("idCaratula", request)) {
                idCaratula = Integer.parseInt(request.getParameter("idCaratula"));
            }
            if (Utilerias.existeParametro("opcion", request)) {
                opcion = Integer.parseInt(request.getParameter("opcion"));
            }
            if (Utilerias.existeParametro("nuevo", request)) {
                nuevo = Integer.parseInt(request.getParameter("nuevo"));
            }
            if (Utilerias.existeParametro("justificacionTrans", request)) {
                justificacion = request.getParameter("justificacionTrans");
            }
            if (Utilerias.existeParametro("ramoCons", request)) {
                ramoCons = request.getParameter("ramoCons");
            }
            if (estatus.isEmpty()) {
                estatus = "X";
            }
            if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
                ramoSession = (String) session.getAttribute("ramoAsignado");
            }
            transBean = new TransferenciaBean(tipoDependencia);
            transBean.setStrServer((request.getHeader("Host")));
            transBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            transBean.resultSQLConecta(tipoDependencia);

            caratulaBean = new CaratulaBean(tipoDependencia);
            caratulaBean.setStrServer((request.getHeader("Host")));
            caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            caratulaBean.resultSQLConecta(tipoDependencia);

            isParaestatal = transBean.isParaestatal();
            if (!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()) {
                contabilidad = transBean.getValidaContabilidad(year);
                isParas = false;
            }

            ramoList = transBean.getRamosByUsuario(year, appLogin);
            date = transBean.getResultSQLgetServerDate();
            cal.setTime(date);
            if (year < cal.get(cal.YEAR)) {
                cal.set(cal.DATE, 31);
                cal.set(cal.MONTH, 11);
                cal.set(cal.YEAR, year);
                date = cal.getTime();
                isActual = false;
            }
            dateFormat = df.format(date);
            mesActual = Integer.parseInt(dateFormat.split("/")[1]);
            if (folio != 0 && !transfWork) {
                if (!Utileria.isTipoOficioRequerido(estatus)) {
                    movimiento = transBean.getMovimientosTransferencia(folio, year, mesActual, ramoSession);
                } else {
                    if (!estatus.equals("K")) {
                        movimiento = transBean.getMovimientosTransferenciaByTipoOficio(folio, year, mesActual, tipoOficio, ramoSession);
                    } else {
                        movimiento = transBean.getMovimientosTransfRechazadaByTipoOficio(folio, year, mesActual, tipoOficio, ramoSession);
                    }
                }
                if (transBean.isParaestatal() && !transBean.getResultSqlGetIsAyuntamiento()) {
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
                usuario = transBean.getInfoUsuario(movimiento.getAppLogin(), year);
                if (usuario.getAppLogin() == null) {
                    usuario.setAppLogin(movimiento.getAppLogin().trim());
                }
            } else {
                displayMov = "style='display:none;'";
                if (!estatus.equals("K") || !estatus.equals("R")) {
                    movimiento.setObsRechazo(new String());
                }
                usuario = transBean.getInfoUsuario(appLogin, year);
                usuario.setAppLogin(appLogin);
            }
            if (appLogin.equals(usuario.getAppLogin()) || autorizar || rol.equals(transBean.getResultSQLGetRolesPrg())) {
                isSameUser = true;
            }
            if (folio != 0 && transfWork && transBean.isParaestatal() && !transBean.getResultSqlGetIsAyuntamiento()) {
                movimiento.getMovCaratula().setsIdCaratula(idCaratula);
            }
            if (transBean.isParaestatal() && !transBean.getResultSqlGetIsAyuntamiento()) {
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
            if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("Z")) && movimiento.getOficio() != 0) {
                displayEnvio = "width: 55px;";
            } else {
                displayEnvio = "width: 55px;display: none;";
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
            if (movimiento != null) {
                transMetaList = movimiento.getTransferenciaMetaList();
                transAccionList = movimiento.getTransferenciaAccionList();
                transferenciaList = movimiento.getTransferenciaList();
            }
            session.setAttribute("transferencia", movimiento);
            if (transMetaList.size() > 0 || transMetaList.size() > 0) {
                if ((estatus.equals("X") || estatus.equals("R") || (!estatus.equals("T") && autorizar)) && isSameUser) {

                    displayMov = "style='display:block;'";
                    if (!autorizar) {
                        impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' checked/>";
                    } else {
                        impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' />";
                    }
                    displayBotones = "display: block;";
                } else {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' checked disabled/>";
                    displayBotones = "display: block;";
                }
            } else {
                if ((estatus.equals("X") || estatus.equals("R") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' />";
                    displayBotones = "display: none;";
                } else {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' disabled/>";
                    displayBotones = "display: none;";
                }
            }
            if (urlRegresa.isEmpty()) {
                urlRegresa = "menu.jsp";
            }
            if (movimiento.getCapturaEspecial() != null && movimiento.getCapturaEspecial().equals("S")) {
                fechaContable = true;
            } else {
                fechaContable = false;
            }
            if ((movimiento.getOficio() != 0) && (!isParaestatal)) {
                isObraPublica = transBean.getResultSQLValidaOficioSINVP(movimiento.getOficio());
            } else {
                isObraPublica = false;
            }
    %>
    <div Id="TitProcess"><label >Transferencias <label/> </div>
    <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
    <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
    <script src="librerias/_justificaciones.js" type="text/javascript"></script>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <%
                if (autorizar) {
            %>
            <button type="button" id="btnAceptar" class="btnbootstrap btn-aceptar" onclick="validaMetasAccionesHabilitadasTransferencias(false);">
                <br/> <small> Aceptar </small>
            </button>
            <%if (!isObraPublica) {%>
            <button type="button" id="btnRechazar" class="btnbootstrap btn-rechazar" onclick="getRechazaMov(10)">
                <br/> <small> Rechazar </small>
            </button>
            <%}
                }
                if (isSameUser) {
            %>
            <button id="btnCancelar" type="button" style="<%=displayCancela%>" class="btnbootstrap btn-cancelar" onclick="getCancelaMov(9)">
                <br/> <small> Cancelar </small>
            </button>
            <%
                }
                if (!autorizar) {
            %>
            <button id="btnEnviarAutorizar" type="button" style="<%=displayEnvio%>" class="btnbootstrap btn-enviar" onclick="movimientosJustificacionesValMovsAsig('T')">
                <br/> <small style="padding-top: 5px;display: block;line-height: 1;"> Enviar a autorizaci&oacute;n </small>
            </button>
            <%
                }
            %>
            <button id="btnRegresar" type="button" class="btnbootstrap btn-atras" onclick="regresarAutorizacion()">
                <br/> <small> Regresar </small>
            </button>
            <button id="btnInicio" type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div" style="width: 60%">
        <input type="hidden" id="identificador" value="" />
        <form id="frmRegresa" method="POST" action="<%=urlRegresa%>" accept-charset="UTF-8">            
            <input type="hidden" id="estatus" name="estatus" value="<%=estatus%>" />
            <input type="hidden" id="ramoInList" name="ramoInList" value="<%=ramoInList%>" />
            <input type="hidden" id="tipoMov" name="tipoMov" value="T" />
            <input type="hidden" id="tipoOficio" name="tipoOficio" value="<%=tipoOficio%>" />            
            <input type="hidden" id="dateFor" name="dateFor" value="<%=dateFormat%>" />
            <input type="hidden" id="mesPPTO" name="mesPPTO" value="<%=mesActual%>" />
            <input type="hidden" id="isActual" name="isActual" value="<%=isActual%>" />
            <input type="hidden" id="tipoFlujo" name="tipoFlujo" value="<%=tipoFlujo%>" />
            <input type="hidden" id="opcion" name="opcion" value="<%=opcion%>" />
            <input type="hidden" id="folio" name="folio" value="<%=movimiento.getOficio()%>" />
            <input type="hidden" id="nuevo" name="nuevo" value="<%=nuevo%>" />
            <input type="hidden" id="errorGrabar" name="errorGrabar" value="" />
            <input type="hidden" id="ramoCons" name="ramoCons" value="<%=ramoCons%>" />
            <input type="hidden" id="comentario" name="comentario" value="" />
        </form>
        <br/>
        <%
            if (transBean.isParaestatal() && !transBean.getResultSqlGetIsAyuntamiento()) {
        %>

        <div class='caratula'>  

            <table id="tblComboCaratula">
                <tr>
                    <td><div id='txtCaratula' >Caratula:</div> </td>
                    <td>
                        <%
                            sDisabledCaratula = "style='Display:none'";
                            if (movimiento.getMovCaratula().getsIdCaratula() != 0) {
                                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' onchange='evaluarCaratulaSelected()'>");
                                } else {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' disabled onchange='evaluarCaratulaSelected()'>");
                                }
                                sDisabledCaratula = "";
                            } else {
                                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' onchange='evaluarCaratulaSelected()'>");
                                } else {
                                    out.print("<select " + sDisabledComboCaratula + " id='selCaratula' name='selCaratula' disabled onchange='evaluarCaratulaSelected()'>");
                                }
                            }
                            out.print("<option " + sDisabledComboCaratula + " value='-1'> -- Seleccione un caratula -- </option>");
                            if (movimiento.getMovCaratula().getsIdCaratula() == -2) {
                                out.print("<option " + sDisabledComboCaratula + " value='-2'  selected > No aplica </option>");
                            } else {
                                out.print("<option " + sDisabledComboCaratula + " value='-2'>  No aplica  </option>");
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
                                ramoTemp = transBean.getResultGetRamoByRamoIdYear(movimiento.getRamo(), year);
                                if (ramoTemp != null) {
                        %>
                        <input class="info-usuario-izq" id="inp-ramo-usr" value="<%=ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr()%>" disabled/>
                        <%
                        } else {
                        %>
                        <input class="info-usuario-izq" id="inp-ramo-usr" value="<%=usuario.getRamo() + "-" + usuario.getRamoDescr()%>" disabled/>
                        <%
                            }
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
                        <input class="info-usuario-der" id="inp-folio-usr" disabled value="<%=movimiento.getOficio()%>"/>
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
                <form id='form-transferencia' action='recepcionTransferencia.jsp' method="POST" accept-charset="UTF-8">
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
                            <td>P&aacute;rrafo oficio:</td>
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
                                <%
                                    if (movimiento.getJutificacion() == null || movimiento.getJutificacion().isEmpty()) {
                                        movimiento.setJutificacion(justificacion);
                                    }
                                    if (movimiento.getOficio() > 0 || transfWork) {

                                        if ((!estatus.equals("A") && !estatus.equals("C") || autorizar) && isSameUser) {
                                            out.print("<textarea id='txt-area-justif' class='no-enter' style='text-transform:uppercase;' >" + movimiento.getJutificacion() + "</textarea>");
                                        }/* else {
                                         out.print("<textarea id='txt-area-justif' style='text-transform:uppercase;'  disabled >" + movimiento.getJutificacion() + "</textarea>");
                                         }*/

                                    } else {
                                        if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {%>
                                <textarea id="txt-area-justif"  class='no-enter' style='text-transform:uppercase;' ></textarea>
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
                                <textarea id="txt-area-obs-rechazo" class='no-enter' style='text-transform:uppercase;'  disabled><%out.print(movimiento.getObsRechazo().trim());%></textarea>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td> Ramo: </td>
                            <td>
                                <select id="selRamo" name="selRamo" onchange='getProgramasByRamoUsuario()'>
                                    <option value="-1"> -- Seleccione un ramo -- </option>
                                    <%
                                        if (ramoList.size() > 0) {
                                            for (Ramo ramoT : ramoList) {
                                                out.print("<option value=" + ramoT.getRamo() + ">" + ramoT.getRamo() + "-" + ramoT.getRamoDescr() + " </option>");
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
                                <select id="selPrg" name="selPrg" onchange='getProyectosByProgramaUsuario()'>
                                    <option value="-1"> -- Seleccione un programa -- </option>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td> Proyecto/Actividad: </td>
                            <td>
                                <select id="selProy" name="selProy" onchange='getMetasByProyectoUsuario()'>
                                    <option value="-1"> -- Seleccione un proyecto/actividad -- </option>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td> Meta: </td>
                            <td>
                                <select id="selMeta" name="selMeta" onchange='getAccionByMetaUsuario()'>
                                    <option value="-1"> -- Seleccione una meta -- </option>
                                </select>
                            </td>
                            <td>                        
                                <%if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) {%>
                                <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-meta" style="<%=displayBotones%>" onclick="getInfoMetaTransferenciaRecalendarizacion('-1', 'X', 'T')"/>
                                <%}%>
                            </td>
                        </tr>
                        <tr>
                            <td> Acci&oacute;n: </td>
                            <td>
                                <select id="selAccion" name="selAccion" onchange='getPartidaByAccionUsuario()'>
                                    <option value="-1"> -- Seleccione una acci&oacute;n -- </option>
                                </select>
                            </td>
                            <td>
                                <%if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) {%>
                                <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-accion" style="<%=displayBotones%>" onclick="getInfoAccionTransferenciaRecalendarizacion('-1', 'X', 'T')"/>
                                <%}%>
                            </td> 
                        </tr> 
                        <tr>
                            <td> Partida: </td>
                            <td>
                                <select id="selPartida" name="selPartida" onchange='getRelLaboralByPartidaUsuario()'>
                                    <option value="-1"> -- Seleccione una partida-- </option>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr id="tr-rel-laboral" style="display: none;"> <!--diplay:grid para cuando se tenga que mostrar grid-->
                            <td> Relalci&oacute;n laboral: </td>
                            <td>
                                <select id="selRelLaboral" name="selRelLaboral" onchange='getFuenteFinanciamientoByRelLaboralUsuario()'>
                                    <option value="-1"> -- Seleccione una relalci&oacute;n laboral-- </option>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td> Fuente de financiamiento: </td>
                            <td>
                                <select id="selFuente" name="selFuente" onchange='getAsignadoPPTOByMes()'>
                                    <option value="-1"> -- Seleccione una fuente de financiamiento-- </option>
                                </select>
                            </td>
                            <td></td>
                        </tr>
                    </table>                
                    <div id="div-cantidades-cod">
                        <div>Disponible:</div>
                        <input id="inp-txt-disp-trans" type="text" value="0.0" disabled/>
                        <div>Importe:</div>
                        <input id="inp-txt-impor-trans" name='importeTrans' type="text" value="0.0" onblur="agregarFormato('inp-txt-impor-trans');" 
                               onkeyup="validaMascara('inp-txt-impor-trans')" />
                    </div>  
                    <div id="tbl-req-ampred">
                        <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {%>
                        <input type="button" value="Transferir importe" id="btn-tranferir"  onclick="transferirCodigo('-1')"/>
                        <%}%>
                    </div> 
                    <input  type="hidden" id="edRamo" name="edRamo" value="" />
                    <input  type="hidden" id="edPrg" name="edPrg" value="" />
                    <input  type="hidden" id="edProy" name="edProy" value="" />
                    <input  type="hidden" id="edMeta" name="edMeta" value="" />
                    <input  type="hidden" id="edAccion" name="edAccion" value="" />
                    <input  type="hidden" id="edPartida" name="edPartida" value="" />
                    <input  type="hidden" id="edRelLaboral" name="edRelLaboral" value="" />
                    <input  type="hidden" id="edFuente" name="edFuente" value="" />
                    <input  type="hidden" id="ramoDescr" name="ramoDescr" value="" />
                    <input  type="hidden" id="programaDescr" name="programaDescr" value="" />
                    <input  type="hidden" id="proyDescr" name="proyDescr" value="" />
                    <input  type="hidden" id="metaDescr" name="metaDescr" value="" />
                    <input  type="hidden" id="accionDescr" name="accionDescr" value="" />
                    <input  type="hidden" id="partidaDescr" name="partidaDescr" value="" />
                    <input  type="hidden" id="relLaboralDescr" name="relLaboralDescr" value="" />
                    <input  type="hidden" id="fuenteDescr" name="fuenteDescr" value="" />
                    <input  type="hidden" id="transferenciaId" name="transferenciaId" value="" />
                    <input  type="hidden" id="edicionTrans" name="edicionTrans" value="" />
                    <input type="hidden" id="tipoOficio" name="tipoOficio" value="<%=tipoOficio%>" />
                    <input  type="hidden" id="mesActual" name="mesActual" value="<%=mesActual%>" />
                    <input type="hidden" id="dateFor" name="dateFor" value="<%=dateFormat%>" />
                    <input type="hidden" id="fechaContabilidad" name="fechaContabilidad" value="<%=fechaContable%>" /> 
                    <input type="hidden" value="<%=estatus%>" id="inpEstatus" name="estatus"  />
                    <input type="hidden" value="<%=movimiento.getOficio()%>" id="folio" name="folio"  />
                    <input type="hidden" value="<%=urlRegresa%>" id="urlRegresa" name="urlRegresa"  />
                    <input type="hidden" id="nuevoT" name="nuevoT" value="<%=nuevo%>" />
                    <input type="hidden" id="opcionT" name="opcionT" value="<%=opcion%>" />
                    <input type="hidden" id="justificacionTrans" name="justificacionTrans" value="" />
                    <input type="hidden" id="idCaratula" name="idCaratula" value="" />
                    <input type="hidden" value="<%=inicio%>" id="inicio" name="inicio"  />
                    <input type="hidden" id="autorizacion" name="autorizacion" value="<%=autorizar%>" />
                    <input type="hidden" id="ramoCons" name="ramoCons" value="<%=ramoCons%>" />
                    <input type="hidden" value="<%=isSameUser%>" id="sameUser" name="sameUser"  />
                    <input type="hidden" id="tipoFlujo" name="tipoFlujo" value="<%=tipoFlujo%>" />
                </form>
            </div>
            <%
            } else {
            %>
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
                        <%if (movimiento.getOficio() > 0) {
                                if (movimiento.getJutificacion() == null) {
                                    movimiento.setJutificacion("");
                                }
                                if ((!estatus.equals("A") && !estatus.equals("C") || autorizar) && isSameUser) {
                                    out.print("<textarea id='txt-area-justif' class='no-enter' style='text-transform:uppercase;'  >" + movimiento.getJutificacion() + "</textarea>");
                                } else {
                                    out.print("<textarea id='txt-area-justif' class='no-enter' style='text-transform:uppercase;'  disabled >" + movimiento.getJutificacion() + "</textarea>");
                                }
                            } else {
                                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {%>
                        <textarea id="txt-area-justif" class='no-enter'  style='text-transform:uppercase;'></textarea>
                        <%} else {%>
                        <textarea id="txt-area-justif" class='no-enter' style='text-transform:uppercase;' disabled ></textarea>
                        <%}%>
                        <%}%>
                    </td>
                </tr>
            </table>
            <form id='form-transferencia' action='recepcionTransferencia.jsp' method="POST" accept-charset="UTF-8">
                <input  type="hidden" id="edRamo" name="edRamo" value="" />
                <input  type="hidden" id="edPrg" name="edPrg" value="" />
                <input  type="hidden" id="edProy" name="edProy" value="" />
                <input  type="hidden" id="edMeta" name="edMeta" value="" />
                <input  type="hidden" id="edAccion" name="edAccion" value="" />
                <input  type="hidden" id="edPartida" name="edPartida" value="" />
                <input  type="hidden" id="edRelLaboral" name="edRelLaboral" value="" />
                <input  type="hidden" id="edFuente" name="edFuente" value="" />
                <input  type="hidden" id="ramoDescr" name="ramoDescr" value="" />
                <input  type="hidden" id="programaDescr" name="programaDescr" value="" />
                <input  type="hidden" id="proyDescr" name="proyDescr" value="" />
                <input  type="hidden" id="metaDescr" name="metaDescr" value="" />
                <input  type="hidden" id="accionDescr" name="accionDescr" value="" />
                <input  type="hidden" id="partidaDescr" name="partidaDescr" value="" />
                <input  type="hidden" id="relLaboralDescr" name="relLaboralDescr" value="" />
                <input  type="hidden" id="fuenteDescr" name="fuenteDescr" value="" />
                <input  type="hidden" id="transferenciaId" name="transferenciaId" value="" />
                <input  type="hidden" id="edicionTrans" name="edicionTrans" value="" />
                <input  type="hidden" id="mesActual" name="mesActual" value="<%=mesActual%>" />
                <input type="hidden" id="dateFor" name="dateFor" value="<%=dateFormat%>" />
                <input type="hidden" id="fechaContabilidad" name="fechaContabilidad" value="<%=fechaContable%>" /> 
                <input type="hidden" value="<%=estatus%>" id="inpEstatus" name="estatus"  />
                <input type="hidden" value="<%=movimiento.getOficio()%>" id="folio" name="folio"  />
                <input type="hidden" value="<%=urlRegresa%>" id="urlRegresa" name="urlRegresa"  />
                <input type="hidden" id="nuevoT" name="nuevoT" value="<%=nuevo%>" />
                <input type="hidden" id="opcionT" name="opcionT" value="<%=opcion%>" />
                <input type="hidden" id="justificacionTrans" name="justificacionTrans" value="" />
                <input type="hidden" id="idCaratula" name="idCaratula" value="" />
                <input type="hidden" id="autorizacion" name="autorizacion" value="<%=autorizar%>" />
                <input type="hidden" id="ramoCons" name="ramoCons" value="<%=ramoCons%>" />
                <input type="hidden" value="<%=inicio%>" id="inicio" name="inicio"  />
                <input type="hidden" value="<%=isSameUser%>" id="sameUser" name="sameUser"  />
            </form>
            <%}%>
            <div id="mensaje"></div>
            <fieldset id="fldst-ampred">
                <legend> Transferencias: </legend>
                <table id="tbl-ampred-reqs">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Ramo</th>
                            <th>Depto.</th>
                            <th>Programa</th>
                            <th>Proy./Act.</th>
                            <th>Meta</th>
                            <th>Acci&oacute;n</th>
                            <th>Partida</th>
                            <th>F.F.R.</th>
                            <th>Rel. Laboral</th>
                            <th>15%</th>
                            <th>Acumulado Movtos-Pptales</th>
                            <th>Importe</th>
                            <th>Importe a Transferir</th>
                                <%if (!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()) {%>
                            <th>Disponible Acumulado</th>
                            <th>Importe Anual</th>
                                <%}
                                if ((autorizar || estatus.equals("A")) && (estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("T") || estatus.equals("A")) && transBean.getRolNormativo(Integer.parseInt(rol)) == 1) {%>
                            <th>Considerar </th>
                                <%} else {%>
                            <th></th>
                                <%}%>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Transferencia trasnferencia : transferenciaList) {
                                if (trasnferencia.getRamo() != null) {
                                    totalImporte = totalImporte.add(new BigDecimal(trasnferencia.getImporte()));
                                    out.print("<tr>");
                                    out.print("<td></td>");
                                    out.print("<td>" + trasnferencia.getRamo() + "</td>");
                                    out.print("<td>" + trasnferencia.getDepto() + "</td>");
                                    out.print("<td>" + trasnferencia.getPrograma() + "</td>");
                                    out.print("<td>" + trasnferencia.getTipoProy() + "-" + trasnferencia.getProyecto() + "</td>");
                                    out.print("<td>" + trasnferencia.getMeta() + "</td>");
                                    out.print("<td>" + trasnferencia.getAccion() + "</td>");
                                    out.print("<td>" + trasnferencia.getPartida() + "</td>");
                                    out.print("<td>" + trasnferencia.getFuente() + "."
                                            + trasnferencia.getFondo() + "."
                                            + trasnferencia.getRecurso() + "</td>");
                                    out.print("<td>" + trasnferencia.getRelLaboral() + "</td>");
                                    out.print("<td>" + dFormat.format(new BigDecimal(trasnferencia.getQuincePor()).setScale(2, RoundingMode.HALF_UP).doubleValue()) + "</td>");
                                    out.print("<td>" + dFormat.format(trasnferencia.getAcumulado()) + "</td>");
                                    out.print("<td>" + dFormat.format(trasnferencia.getImporte()) + "<input type='button' class='btnbootstrap-drop btn-money'"
                                            + "onClick='editarImporteTrans(\"" + trasnferencia.getConsec() + "\"," + isParas + ")' /></td>");
                                    //out.print("<td>" + dFormat.format(transBean.getImporteDisponible(trasnferencia.getImporte(), trasnferencia)) + "</td>");
                                    String strDisponible = dFormat.format(transBean.getImporteDisponible(trasnferencia.getImporte(), trasnferencia));
                                    if (!strDisponible.equals("-0")) {
                                        out.print("<td>" + strDisponible + "</td>");
                                    } else {
                                        out.print("<td>" + "0" + "</td>");
                                    }
                                    if (!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()) {
                                        out.print("<td>" + dFormat.format(trasnferencia.getDisponible()) + "</td>");
                                        out.print("<td>" + dFormat.format(trasnferencia.getDisponibleAnual()) + "</td>");
                                    }
                                    if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {
                                        out.print("<td><a onClick='mostrarProgramacion(\"" + trasnferencia.getRamo() + "\"," + trasnferencia.getMeta() + "," + trasnferencia.getAccion() + ")'><span class='fa fa-calendar botones-img'></span></a>"
                                                + "<input type='button' class='btnbootstrap-drop btn-edicion' "
                                                + "onclick='getInfoEdicionTransferencia(\"" + trasnferencia.getRamo() + "\",\"" + trasnferencia.getPrograma() + "\","
                                                + "\"" + trasnferencia.getProyecto() + "," + trasnferencia.getTipoProy() + "\",\"" + trasnferencia.getMeta() + "\","
                                                + "\"" + trasnferencia.getAccion() + "\",\"" + trasnferencia.getPartida() + "\","
                                                + "\"" + trasnferencia.getFuente() + "." + trasnferencia.getFondo() + "." + trasnferencia.getRecurso() + "\",\"" + trasnferencia.getRelLaboral() + "\","
                                                + "\"" + trasnferencia.getConsec() + "\")' />");
                                        out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarTransferencia(\"" + trasnferencia.getConsec() + "\",\"3\",\"-1\");' /></td>");
                                    } else {
                                        if ((autorizar || estatus.equals("A")) && (estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("T") || estatus.equals("A")) && transBean.getRolNormativo(Integer.parseInt(rol)) == 1) {
                                            if (trasnferencia.getConsiderar().equals("S")) {
                                                out.print("<td><input type='checkbox' id='considerar' name='considerar'  " + "onchange='changeConsiderarTransAmp(\"" + trasnferencia.getConsec() + "\",\"1\")' checked ></td>");
                                            } else {
                                                out.print("<td><input type='checkbox' id='considerar' name='considerar'  " + "onchange='changeConsiderarTransAmp(\"" + trasnferencia.getConsec() + "\",\"1\")' ></td>");
                                            }
                                        }
                                        out.print("<td><a onClick='mostrarProgramacion(\"" + trasnferencia.getRamo() + "\"," + trasnferencia.getMeta() + "," + trasnferencia.getAccion() + ")'><span class='fa fa-calendar botones-img'></span></a>"
                                                + "<input type='button' class='btnbootstrap-drop btn-ver' "
                                                + "onclick='getInfoEdicionTransferencia(\"" + trasnferencia.getRamo() + "\",\"" + trasnferencia.getPrograma() + "\","
                                                + "\"" + trasnferencia.getProyecto() + "," + trasnferencia.getTipoProy() + "\",\"" + trasnferencia.getMeta() + "\","
                                                + "\"" + trasnferencia.getAccion() + "\",\"" + trasnferencia.getPartida() + "\","
                                                + "\"" + trasnferencia.getFuente() + "." + trasnferencia.getFondo() + "." + trasnferencia.getRecurso() + "\",\"" + trasnferencia.getRelLaboral() + "\","
                                                + "\"" + trasnferencia.getConsec() + "\")' />");
                                    }
                                    out.print("</tr>");
                                } else {
                                    transfDeleteList.add(trasnferencia);
                                }
                            }
                            out.print("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><label>TOTAL:</label></td><td>" + dFormat.format(totalImporte.doubleValue()) + "</td></tr>");
                            for (Transferencia transferencia : transfDeleteList) {
                                transferenciaList.remove(transferencia);
                            }
                        %>
                    </tbody>
                </table>
            </fieldset>
            <br/>
            <%
                out.print("<fieldset id='fldst-movs' " + displayMov + " >");
            %>
            <legend> Propuesta de estimaci&oacute;n: </legend>
            <table id="tbl-movs">
                <thead>
                    <tr>
                        <th align="right" >Ramo</th>
                        <th align="right" >Programa</th>
                        <th align="right" >Meta</th>
                        <th align="right" >Acci&oacute;n</th>
                        <th align="right" >Estimaci&oacute;n original</th>
                        <th align="right" >Propuesta</th>
                        <th align="right" ></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (TransferenciaMeta transMeta : transMetaList) {
                            if (transMeta.getMovOficioMeta().getNva_creacion().equals("N")) {
                                out.print("<tr>");
                                out.print("<td>" + transMeta.getMovOficioEstimacion().get(0).getRamo() + "</td>");
                                out.print("<td>" + transMeta.getMovOficioEstimacion().get(0).getPrograma() + "</td>");
                                //out.print("<td>" + transMeta.getMovOficioEstimacion().get(0).get)+transMeta.getMetaInfo().getProyId() + "</td>";
                                out.print("<td>" + transMeta.getMovOficioEstimacion().get(0).getMeta() + "</td>");
                                out.print("<td> - </td>");
                                out.print("<td> " + dFormat.format(transBean.getResultSQLgetEstimacionOrigiginal(estatus,
                                        movimiento.getOficio(), year, transMeta.getMovOficioEstimacion().get(0).getRamo(),
                                        transMeta.getMovOficioEstimacion().get(0).getMeta())) + " </td>");
                                out.print("<td> " + dFormat.format(transMeta.getPropuestaEstimacion()) + " </td>");
                                //out.print("<td> - </td>)";
                                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                            + "onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                                            + "(\"" + transMeta.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + transMeta.getMovOficioEstimacion().get(0).getRamo() + "\","
                                            + "\"" + transMeta.getMovOficioEstimacion().get(0).getMeta() + "\",\"T\")' />");
                                    out.print("<input type='button' class='btnbootstrap-drop btn-borrar' "
                                            + "onclick='borrarTransferencia(\"" + transMeta.getIdentificador() + "\",\"1\",\"-1\");' /></td>");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                            + "onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                                            + "(\"" + transMeta.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + transMeta.getMovOficioEstimacion().get(0).getRamo() + "\","
                                            + "\"" + transMeta.getMovOficioEstimacion().get(0).getMeta() + "\",\"T\")' />");
                                }
                                out.print("</tr>");
                            }
                        }
                        for (TransferenciaAccion reprogAccion : transAccionList) {
                            if (reprogAccion.getMovOficioAccion().getNvaCreacion().equals("N")) {
                                out.print("<tr>");
                                out.print("<td>" + reprogAccion.getMovOficioAccionEstList().get(0).getRamo() + "</td>");
                                out.print("<td>" + reprogAccion.getMovOficioAccionEstList().get(0).getPrograma() + "</td>");
                                //out.print("<td> - </td>");
                                out.print("<td>" + reprogAccion.getMovOficioAccionEstList().get(0).getMeta() + "</td>");
                                out.print("<td> " + reprogAccion.getMovOficioAccionEstList().get(0).getAccion() + " </td>");
                                out.print("<td> " + dFormat.format(transBean.getResultSQLgetAccionEstimacionOriginal(estatus,
                                        movimiento.getOficio(), year, reprogAccion.getMovOficioAccionEstList().get(0).getRamo(),
                                        reprogAccion.getMovOficioAccionEstList().get(0).getMeta(),
                                        reprogAccion.getMovOficioAccionEstList().get(0).getAccion())) + " </td>");
                                out.print("<td> " + dFormat.format(reprogAccion.getPropuestaEstimacion()) + " </td>");
                                if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                            + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + reprogAccion.getMovOficioAccionEstList().get(0).getRamo() + "\","
                                            + "\"" + reprogAccion.getMovOficioAccionEstList().get(0).getMeta() + "\","
                                            + "\"" + reprogAccion.getMovOficioAccionEstList().get(0).getAccion() + "\",\"T\" )' />");
                                    out.print("<input type='button' class='btnbootstrap-drop btn-borrar' "
                                            + "onclick='borrarTransferencia(\"" + reprogAccion.getIdentificador() + "\",\"2\",\"-1\");' /></td>");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                            + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + reprogAccion.getMovOficioAccionEstList().get(0).getRamo() + "\","
                                            + "\"" + reprogAccion.getMovOficioAccionEstList().get(0).getMeta() + "\","
                                            + "\"" + reprogAccion.getMovOficioAccionEstList().get(0).getAccion() + "\",\"T\" )' />");
                                }
                                out.print("</tr>");
                            }
                        }
                    %>
                </tbody>
            </table>
            </fieldset>
            <div id="PopUpZone"></div>
            <br/>
            <div><%if (autorizar || estatus.equals("R") || estatus.equals("X")) {%>
                <input type="button" value="Guardar" id="btn-save-movs" onclick="guardarTransferencia(false)"/>
                <%}%>
            </div>
        </div>
    </div>
    <%if (isRedirected && !autorizar) {%>
    <script type="text/javascript">
        cargarProgramaTransferencia('<%=ramo%>');
    </script>

    <script type="text/javascript">
        cargarProyectoTransferencia('<%=programa%>');
    </script>

    <script type="text/javascript">
        cargarMetaTransferencia('<%=proyecto%>');
    </script>

    <script type="text/javascript">
        cargarAccionTransferencia(<%=meta%>);
    </script>

    <script type="text/javascript">
        cargarPartidaTransferencia(<%=accion%>);
    </script>

    <script type="text/javascript">
        cargarRelLaboralTransferencia('<%=partida%>');
    </script>

    <script type="text/javascript">
        cargarFuenteFinTransferencia('<%=relLaboral%>');
    </script>

    <script type="text/javascript">
        cargarDisponibleTransferencia('<%=fuenteFin%>');
    </script>
    <%}%>
    <%
        if (contabilidad && transBean.isUsuarioCapturaEspecial(appLogin) && movimiento.getOficio() == 0 && !transfWork && (!isParaestatal || transBean.getResultSqlGetIsAyuntamiento())) {
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
    <%                        }
        } catch (Exception ex) {
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.grabaBitacora();
        } finally {
            if (transBean != null) {
                transBean.resultSQLDesconecta();
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

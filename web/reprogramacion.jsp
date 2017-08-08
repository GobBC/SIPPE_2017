<%-- 
    Document   : reprogramacion
    Created on : Dec 21, 2015, 11:14:43 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.CaratulaBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Caratula"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.aplicacion.ReprogramacionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Usuario"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
    <title>Reprogramaci&oacute;n</title>    
</head>

<html>
    <jsp:include page="template/encabezado.jsp" />
    <%
        request.setCharacterEncoding("UTF-8");
        //ResultSQL sql = new ResultSQL();
        Caratula caratulaStatus = null;

        Ramo ramoTemp = null;
        Usuario usuario = new Usuario();
        CaratulaBean caratulaBean = null;
        Bitacora bitacora = new Bitacora();
        ReprogramacionBean reproBean = null;
        MovimientosReprogramacion movimiento = new MovimientosReprogramacion();

        ArrayList<Caratula> arrCaratulas = null;
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<ReprogramacionMeta> reprogramacionMetaList = new ArrayList<ReprogramacionMeta>();
        List<ReprogramacionAccion> reprogramacionAccionList = new ArrayList<ReprogramacionAccion>();

        Date date;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String estatus = "X";
        String ramoInList = "";
        String caratulaEstatus = "A";
        String sDisabledCaratula = "";
        String ramoCons = new String();
        String appLogin = new String();
        String dateFormat = new String();
        String urlRegresa = new String();
        String ramoSession = new String();
        String displayEnvio = new String();
        String sDisabledComboCaratula = "";
        String displayCancela = new String();
        String displayBotones = new String();
        String tipoDependencia = new String();
        String rol = new String();
        String sMuestraUsuario = new String();
        String caratulaDisable = new String();
        String strSelected = new String();

        int year = 0;
        int folio = 0;
        int nuevo = 0;
        int opcion = 0;
        int tipoFlujo = 0;

        boolean isActual = true;
        boolean isObraPublica = true;
        boolean autorizar = false;
        boolean bFiltraEstatusAbiertas = true;
        boolean isSameUser = false;

        try {

            if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
                response.sendRedirect("logout.jsp");
                return;
            }
            if (session.getAttribute("reprogramacion") != null) {
                session.removeAttribute("reprogramacion");
            }
            if (session.getAttribute("ampliacionReduccion") != null) {
                session.removeAttribute("ampliacionReduccion");
            }
            if (session.getAttribute("transferencia") != null) {
                session.removeAttribute("transferencia");
            }

            if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
                rol = (String) session.getAttribute("strRol");
            }

            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }
            if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
                ramoSession = (String) session.getAttribute("ramoAsignado");
            }
            if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
                appLogin = (String) session.getAttribute("strUsuario");
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
            if ((estatus.equals("X") || estatus.equals("Z") || estatus.equals("R")) && folio != 0) {
                displayEnvio = "width: 55px;";
            } else {
                displayEnvio = "width: 55px;display: none;";
            }
            if (autorizar || (estatus.equals("X")) || (estatus.equals("K") && folio != 0)) {
                displayCancela = "display: inline;";
            } else {
                displayCancela = "display: none;";
            }

            reproBean = new ReprogramacionBean(tipoDependencia);
            reproBean.setStrServer((request.getHeader("Host")));
            reproBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            reproBean.resultSQLConecta(tipoDependencia);

            caratulaBean = new CaratulaBean(tipoDependencia);
            caratulaBean.setStrServer((request.getHeader("Host")));
            caratulaBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            caratulaBean.resultSQLConecta(tipoDependencia);

            ramoList = reproBean.getRamosByUsuario(year, appLogin);
            date = reproBean.getResultSQLgetServerDate();
            cal.setTime(date);
            if (year < cal.get(cal.YEAR)) {
                cal.set(cal.DATE, 31);
                cal.set(cal.MONTH, 11);
                cal.set(cal.YEAR, year);
                date = cal.getTime();
                isActual = false;
            }
            dateFormat = df.format(date);
            if (folio != 0) {
                movimiento = reproBean.getMovimientosReprogramacion(year, ramoSession, folio);
                if (reproBean.isParaestatal() && !reproBean.getResultSqlGetIsAyuntamiento()) {
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
                reprogramacionMetaList = movimiento.getMovOficioMetaList();
                reprogramacionAccionList = movimiento.getMovOficioAccionList();
                session.setAttribute("reprogramacion", movimiento);
                usuario = reproBean.getInfoUsuario(movimiento.getAppLogin(), year);
                if (usuario.getAppLogin() == null) {
                    usuario.setAppLogin(movimiento.getAppLogin().trim());
                }
            } else {
                movimiento.setObsRechazo(new String());
                usuario = reproBean.getInfoUsuario(appLogin, year);
                usuario.setAppLogin(appLogin);
            }
            if (appLogin.equals(usuario.getAppLogin()) || autorizar || rol.equals(reproBean.getResultSQLGetRolesPrg())) {
                isSameUser = true;
            }
            if (reproBean.isParaestatal() && !reproBean.getResultSqlGetIsAyuntamiento()) {
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
            if (urlRegresa.isEmpty()) {
                urlRegresa = "menu.jsp";
            }
            if ((folio != 0) && (!reproBean.isParaestatal())) {
                isObraPublica = reproBean.getResultSQLValidaOficioSINVP(folio);
            } else {
                isObraPublica = false;
            }
    %>
    <script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
    <link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
    <script src="librerias/_justificaciones.js" type="text/javascript"></script>
    <div Id="TitProcess"><label >Reprogramaci&oacute;n <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <%
                if (autorizar) {
            %>
            <button type="button" id="btnAceptar" class="btnbootstrap btn-aceptar" onclick="validaMetasAccionesHabilitadasReprogramacion(false)">
                <br/> <small> Aceptar </small>
            </button>
            <%if (!isObraPublica) {%>
            <button type="button" id="btnRechazar" class="btnbootstrap btn-rechazar" onclick="getRechazaMov(2)">
                <br/> <small> Rechazar </small>
            </button>
            <%                    }
                }
                if (isSameUser) {
            %>
            <button type="button" id="btnCancelar" style="<%=displayCancela%>" class="btnbootstrap btn-cancelar" onclick="getCancelaMov(1)">
                <br/> <small> Cancelar </small>
            </button>
            <%}
                if (!autorizar && isSameUser) {
            %>
            <button type="button" id="btnEnviarAutorizar" style="<%=displayEnvio%>" class="btnbootstrap btn-enviar" onclick="movimientosJustificacionesValMovsAsig('R')">
                <br/> <small style="padding-top: 5px;display: block;line-height: 1;"> Enviar a autorizaci&oacute;n </small>
            </button>
            <%
                }
            %>
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
            <input type="hidden" id="tipoMov" name="tipoMov" value="R" />
            <input type="hidden" id="dateFor" name="dateFor" value="<%=dateFormat%>" />
            <input type="hidden" id="isActual" name="isActual" value="<%=isActual%>" />
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
            if (reproBean.isParaestatal() && !reproBean.getResultSqlGetIsAyuntamiento()) {
        %>
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

            <%                out.print("<div id='infoCaratula' class='info-usuario'>");
                out.print("<br>");
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
        <br>
        <%            }
        %>
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
                                ramoTemp = reproBean.getResultGetRamoByRamoIdYear(movimiento.getRamo(), year);
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
                if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {
            %>
            <div class="info-codigo">              
                <table id="tbl-info-movs">
                    <tr>
                        <%if (!estatus.equals("X") && !estatus.equals("R")) {%>
                        <td>Comentario(s): <a onclick="getComentarios()"><span class="fa fa-eye botones-img"></span></a></td>
                        <td>
                            <%
                                if (movimiento.getComentarioAutorizacion() == null) {
                                    movimiento.setComentarioAutorizacion("");
                                    }%>
                            <textarea id='txt-area-coment' class='no-enter' class='no-enter' style='text-transform:uppercase;'  disabled ><%=movimiento.getComentarioAutorizacion()%></textarea>
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
                                        out.print("<textarea id='txt-area-justif' class='no-enter' style='text-transform:uppercase;'  >" + movimiento.getJutificacion() + "</textarea>");
                                    } else {
                                        out.print("<textarea id='txt-area-justif' class='no-enter' style='text-transform:uppercase;'   disabled >" + movimiento.getJutificacion() + "</textarea>");
                                    }
                                } else {
                                    if (estatus.equals("X") || estatus.equals("R")) {%>
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
                                    mostrarEdicionMetaReprogramacion()'>
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
                                    mostrarEdicionMetaReprogramacion()'>
                                <option value="-1"> -- Seleccione un programa -- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Proyecto/Actividad: </td>
                        <td>
                            <select id="selProy" name="selProys" onchange='getMetasByProyecto();
                                    mostrarEdicionMetaReprogramacion()'>
                                <option value="-1"> -- Seleccione un proyecto/actividad -- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Meta: </td>
                        <td>
                            <select id="selMeta" name="selMeta" onchange='getAccionByMeta();
                                    mostrarEdicionMetaReprogramacion()'>
                                <option value="-1"> -- Seleccione una meta -- </option>
                            </select>
                        </td>
                        <td>                        
                            <%if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {%>
                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-meta" style="<%=displayBotones%>" onclick="getInfoMetaReprogramacion('-1', '<%=estatus%>')"/>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-meta" style="<%=displayBotones%>" onclick="getInfoMetaReprogramacionRecalendarizacion('-1', 'X')"/>
                            <%}%>
                        </td>
                    </tr>
                    <tr>
                        <td> Acci&oacute;n: </td>
                        <td>
                            <select id="selAccion" name="selAccion" onchange="mostrarEdicionAccionReprogramacion()">
                                <option value="-1"> -- Seleccione una acci&oacute;n -- </option>
                            </select>
                        </td>
                        <td>
                            <%if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {%>
                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-accion" style="<%=displayBotones%>" onclick="getInfoAccionReprogramacion('-1', '<%=estatus%>')"/>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-accion" style="<%=displayBotones%>" onclick="getInfoAccionReprogramacionRecalendarizacion('-1', 'X')"/>
                            <%}%>
                        </td> 
                    </tr>                
                </table>
            </div>
            <%
            } else {
            %>
            <div class="info-codigo">              
                <table id="tbl-info-movs">
                    <tr>
                        <td>
                            <label>Justificaci&oacute;n</label>
                        </td>
                        <td>
                            <%if (folio > 0) {
                                    if (movimiento.getJutificacion() == null) {
                                        movimiento.setJutificacion("");
                                    }
                                    if ((!estatus.equals("A") && !estatus.equals("C") || autorizar) && isSameUser) {
                                        out.print("<textarea id='txt-area-justif' class='no-enter' maxlength='100' style='text-transform:uppercase;'>" + movimiento.getJutificacion() + "</textarea>");
                                    } else {
                                        out.print("<textarea id='txt-area-justif' class='no-enter' maxlength='100' style='text-transform:uppercase;' disabled >" + movimiento.getJutificacion() + "</textarea>");
                                    }
                                } else {
                                    if ((estatus.equals("X") || estatus.equals("R")) && isSameUser) {%>
                            <textarea id="txt-area-justif" class='no-enter' maxlength="100"  style='text-transform:uppercase;'></textarea>
                            <%} else {%>
                            <textarea id="txt-area-justif" class='no-enter' maxlength="100" style='text-transform:uppercase;' disabled ></textarea>
                            <%}%>
                            <%}%>
                        </td>
                    </tr>
                </table>
                <%
                    }

                %>
                <div id="mensaje"></div>
                <fieldset id="fldst-recal">
                    <legend> Reprogramaciones: </legend>
                    <table id="tbl-movto-reprog">
                        <thead>
                            <tr>
                                <th>Ramo</th>
                                <th>Programa</th>
                                <th>Proyecto/Actividad</th>
                                <th>Meta</th>
                                <th>Acci&oacute;n</th>
                                <th>Departamento</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%                                for (ReprogramacionMeta reprogMeta : reprogramacionMetaList) {
                                    out.print("<tr>");
                                    out.print("<td> " + reprogMeta.getMetaInfo().getRamoId() + "</td>");
                                    out.print("<td> " + reprogMeta.getMetaInfo().getPrgId() + "</td>");
                                    out.print("<td> " + reprogMeta.getMetaInfo().getTipoProy() + reprogMeta.getMetaInfo().getProyId() + "</td>");
                                    out.print("<td> " + reprogMeta.getMetaInfo().getMetaId() + "</td>");
                                    out.print("<td> - </td>");
                                    out.print("<td> - </td>");
                                    if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {
                                        if (reprogMeta.getMetaInfo().getNvaCreacion().equals("S")) {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                    + "onclick='getInfoMetaReprogramacionTabla"
                                                    + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\")' />");
                                        } else {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                    + "onclick='getInfoMetaReprogramacionRecalendarizacionTabla"
                                                    + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\","
                                                    + "\"" + folio + "\")' />");
                                        }
                                        out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarReprogramacion(\"" + reprogMeta.getIdentificador() + "\",\"1\");' /></td>");
                                    } else {
                                        if (reprogMeta.getMetaInfo().getNvaCreacion().equals("S")) {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                                    + "onclick='getInfoMetaReprogramacionTabla"
                                                    + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\")' />");
                                        } else {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                                    + "onclick='getInfoMetaReprogramacionRecalendarizacionTabla"
                                                    + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                                                    + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\","
                                                    + "\"" + folio + "\")' />");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                                for (ReprogramacionAccion reprogAccion : reprogramacionAccionList) {
                                    out.print("<tr>");
                                    out.print("<td> " + reprogAccion.getMovOficioAccion().getRamoId() + "</td>");
                                    out.print("<td> " + reprogAccion.getMovOficioAccion().getProgramaId() + " </td>");
                                    out.print("<td> - </td>");
                                    out.print("<td> " + reprogAccion.getMovOficioAccion().getMetaId() + "</td>");
                                    out.print("<td> " + reprogAccion.getMovOficioAccion().getAccionId() + " </td>");
                                    out.print("<td> " + reprogAccion.getMovOficioAccion().getDeptoId() + " </td>");
                                    if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {
                                        if (reprogAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                    + "onclick='getInfoAccionReprogramacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\" )' />");

                                        } else {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                                    + "onclick='getInfoAccionReprogramacionRecalendarizacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\","
                                                    + "\"" + folio + "\")' />");
                                        }
                                        out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarReprogramacion(\"" + reprogAccion.getIdentificador() + "\",\"2\");' /></td>");
                                    } else {
                                        if (reprogAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                                    + "onclick='getInfoAccionReprogramacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\" )' />");
                                        } else {
                                            out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                                    + "onclick='getInfoAccionReprogramacionRecalendarizacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                                                    + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\","
                                                    + "\"" + folio + "\")' />");
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
                    <script>
                        acomodoBotones();
                    </script>
                    <input type="button" value="Guardar" id="btn-save-movs" onclick="guardarReprogramacion(false)"/>
                    <%}%>
                </div>
            </div>
        </div>
        <%
            } catch (Exception ex) {
                bitacora.setStrServer(request.getHeader("host"));
                bitacora.setStrUbicacion(getServletContext().getRealPath(""));
                bitacora.setITipoBitacora(1);
                bitacora.setStrInformacion(ex, request.getServletPath());
                bitacora.grabaBitacora();
            } finally {
                if (reproBean != null) {
                    reproBean.resultSQLDesconecta();
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
        <script src="librerias/_comentarios.js" type="text/javascript"></script>
        <jsp:include page="template/piePagina.jsp" />
</html>

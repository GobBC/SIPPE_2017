<%-- 
    Document   : recalendarizacion
    Created on : Dec 7, 2015, 8:36:20 AM
    Author     : ugarcia
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.Usuario"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
    <title>Recalendarización</title>    
</head>
<html>
    <jsp:include page="template/encabezado.jsp" />
    <%
        request.setCharacterEncoding("UTF-8");
        Date date;
        Ramo ramoTemp = null;
        Usuario usuario = new Usuario();
        Bitacora bitacora = new Bitacora();
        Calendar cal = Calendar.getInstance();
        RecalendarizacionBean recalBean = null;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat dFormat = NumberFormat.getInstance(Locale.US);
        BigDecimal sumaReq = new BigDecimal(0.0);
        BigDecimal sumaRecal = new BigDecimal(0.0);

        MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        List<RecalendarizacionMeta> recalMetaList = new ArrayList<RecalendarizacionMeta>();
        List<RecalendarizacionAccion> recalAccionList = new ArrayList<RecalendarizacionAccion>();
        List<RecalendarizacionAccionReq> recalAccionReqList = new ArrayList<RecalendarizacionAccionReq>();
        String estatus = "X";
        String ramoInList = "";
        String rol = new String();
        String impacto = new String();
        String appLogin = new String();
        String dateFormat = new String();
        String urlRegresa = new String();
        String displayMov = new String();
        String displayEnvio = new String();
        String displayCancela = new String();
        String displayBotones = new String();
        String tipoDependencia = new String();
        String ramoCons = new String();
        String sMuestraUsuario = new String();

        boolean isActual = true;
        boolean autorizar = false;
        boolean isObraPublica = false;
        boolean isSameUser = true;
        int year = 0;
        int cont = 0;
        int folio = 0;
        int tipoFlujo = 0;
        int opcion = 0;
        int nuevo = 0;

        try {
            if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
                rol = (String) session.getAttribute("strRol");
            }
            if (session.getAttribute("strUsuario") == null || session.getAttribute("tipoDependencia") == null) {
                response.sendRedirect("logout.jsp");
                return;
            }
            if (session.getAttribute("recalendarizacion") != null) {
                session.removeAttribute("recalendarizacion");
            }
            if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
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

            //autorizar = "S";  //TEST
            recalBean = new RecalendarizacionBean(tipoDependencia);
            recalBean.setStrServer((request.getHeader("Host")));
            recalBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            recalBean.resultSQLConecta(tipoDependencia);
            ramoList = recalBean.getRamosByUsuario(year, appLogin);
            date = recalBean.getResultSQLgetServerDate();
            cal.setTime(date);
            if (year < cal.get(cal.YEAR)) {
                cal.set(cal.DATE, 31);
                cal.set(cal.MONTH, 11);
                cal.set(cal.YEAR, year);
                date = cal.getTime();
                isActual = false;
            }
            dateFormat = df.format(date);
            movimiento.setJutificacion(new String());
            movimiento.setObsRechazo(new String());
            if (folio != 0) {
                movimiento = recalBean.getMovimientosRecalendarizacion(folio);
                session.setAttribute("recalendarizacion", movimiento);
                usuario = recalBean.getInfoUsuario(movimiento.getAppLogin(), year);
                if (usuario.getAppLogin() == null) {
                    usuario.setAppLogin(movimiento.getAppLogin().trim());
                }
            } else {
                displayMov = "style='display:none;'";
                movimiento.setObsRechazo(new String());
                usuario = recalBean.getInfoUsuario(appLogin, year);
                usuario.setAppLogin(appLogin);
            }
            if (appLogin.equals(usuario.getAppLogin()) || autorizar || rol.equals(recalBean.getResultSQLGetRolesPrg())) {
                isSameUser = true;
            } else {
                isSameUser = false;
            }

            if (urlRegresa.isEmpty()) {
                urlRegresa = "menu.jsp";
            }
            recalMetaList = movimiento.getMovEstimacionList();
            recalAccionList = movimiento.getMovAccionEstimacionList();
            recalAccionReqList = movimiento.getMovOficiosAccionReq();
            if (recalAccionList.size() > 0 || recalMetaList.size() > 0) {
                if (estatus.equals("X") || estatus.equals("R") || autorizar) {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' checked/>";
                    displayBotones = "display: block;";
                } else {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' checked disabled/>";
                    displayBotones = "display: block;";
                }
            } else {
                if (estatus.equals("X") || estatus.equals("R") || autorizar) {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' />";
                    displayBotones = "display: none;";
                } else {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' disabled/>";
                    displayBotones = "display: none;";
                }
            }
            if ((estatus.equals("X") || estatus.equals("Z") || estatus.equals("R")) && folio != 0) {
                displayEnvio = "width: 55px;";
            } else {
                displayEnvio = "width: 55px;display: none;";
            }
            if (autorizar || ((estatus.equals("X") || estatus.equals("R")) && folio != 0)) {
                displayCancela = "display: inline;";
            } else {
                displayCancela = "display: none;";
            }
            if ((folio != 0) && (!recalBean.isParaestatal())) {
                isObraPublica = recalBean.getResultSQLValidaOficioSINVP(folio);
            } else {
                isObraPublica = false;
            }
    %>
    <div Id="TitProcess"><label >Recalendarizaci&oacute;n <label/> </div>
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <%
                if (autorizar) {
            %>
            <button type="button" id="btnAceptar" class="btnbootstrap btn-aceptar" onclick="getAutorizaMovRecalendarizacion()">
                <br/> <small> Aceptar </small>
            </button>
            <%if (!isObraPublica) {%>
            <button type="button" id="btnRechazar" class="btnbootstrap btn-rechazar" onclick="getRechazaMov(2)">
                <br/> <small> Rechazar </small>
            </button>
            <%}
                }
                if (isSameUser) {
            %>
            <button type="button" id="btnCancelar" style="<%=displayCancela%>" class="btnbootstrap btn-cancelar" onclick="getCancelaMov(1)">
                <br/> <small> Cancelar </small>
            </button>
            <%
                }
                if (!autorizar && isSameUser) {
            %>
            <button type="button" id="btnEnviarAutorizar" style="<%=displayEnvio%>" class="btnbootstrap btn-enviar" onclick="enviarRecalendarizacion()">
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
            <input type="hidden" id="tipoMov" name="tipoMov" value="C" />
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
                                ramoTemp = recalBean.getResultGetRamoByRamoIdYear(movimiento.getRamo(), year);
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
        <%
            if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {
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
                    <td>Comentario(s): <a onclick="getComentarios()"><span class="fa fa-pencil botones-img"></span></a></td>
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
                        <select id="selRamo" name="selRamo" onchange='getProgramasByRamoUsuario()'>
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
                        <select id="selPrg" name="selPrg" onchange='getProyectosByProgramaUsuario()'>
                            <option value="-1"> -- Seleccione un programa -- </option>
                        </select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td> Proyecto/Actividad: </td>
                    <td>
                        <select id="selProy" name="selProys" onchange='getMetasByProyectoUsuario()'>
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
                        <%if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {%>
                        <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-meta" style="<%=displayBotones%>" onclick="getInfoMetaEstimacion('-1', 'X')"/>
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
                        <%if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {%>
                        <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-accion" style="<%=displayBotones%>" onclick="getInfoAccionEstimacion('-1', 'X')"/>
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
                        <select id="selFuente" name="selFuente" onchange='getRequerimientosByFuenteFinanciamientoUsuario()'>
                            <option value="-1"> -- Seleccione una fuente de financiamiento-- </option>
                        </select>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td> Requerimiento: </td>
                    <td>
                        <select id="selReq" name="selReq" onchange=''>
                            <option value="-1"> -- Seleccione un requerimiento-- </option>
                        </select>
                    </td>
                    <td>
                        <%if ((estatus.equals("X") || estatus.equals("R")) && isSameUser) {%>
                        <input type="button" class="btnbootstrap-drop btn-add" id="btn-edit-req"  onclick="getInfoAccionRequerimiento('-1', '<%=estatus%>')"/>
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
                    <%if (!estatus.equals("X") && !estatus.equals("R")) {%>
                    <td>Comentario(s): <a onclick="getComentarios()"><span class="fa fa-pencil botones-img"></span></a></td>
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
                    <td>
                        <label>Justificaci&oacute;n</label>
                    </td>
                    <td>
                        <%if (folio > 0) {
                                if (movimiento.getJutificacion() == null) {
                                    movimiento.setJutificacion("");
                                }
                                if ((!estatus.equals("A") && !estatus.equals("C") && autorizar) && isSameUser) {
                                    out.print("<textarea class='no-enter' id='txt-area-justif' maxlength='100'>" + movimiento.getJutificacion() + "</textarea>");
                                } else {
                                    out.print("<textarea class='no-enter' id='txt-area-justif' maxlength='100' disabled >" + movimiento.getJutificacion() + "</textarea>");
                                }
                            } else {
                                if ((estatus.equals("X") || estatus.equals("R")) && isSameUser) {%>
                        <textarea id="txt-area-justif" class='no-enter' maxlength="100" style='text-transform:uppercase;' ></textarea>
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
                    <legend> Recalendarizaciones: </legend>
                    <table id="tbl-reqs">
                        <thead>
                            <tr>
                                <th>Ramo</th>
                                <th>Programa</th>
                                <th>Meta</th>
                                <th>Acci&oacute;n</th>
                                <th>Depto.</th>
                                <th>Partida</th>
                                <th>F.F.R.</th>
                                <th>Rel. Laboral</th>
                                <th>Requerimiento </th>
                                <th>Importe </th>
                                <th>Importe <br/> recalendarizado</th>
                                    <%if ((autorizar || estatus.equals("A")) && (estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("A")) && recalBean.getRolNormativo(Integer.parseInt(rol)) == 1) {%>
                                <th>Considerar </th>
                                    <%} else {%>
                                <th></th>
                                    <%}%>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (RecalendarizacionAccionReq recalRec : recalAccionReqList) {
                                    out.print("<tr>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getRamo() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getPrograma() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getMeta() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getAccion() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getDepto() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getPartida() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getFuente() + "." + recalRec.getMovAccionReq().getFondo() + "." + recalRec.getMovAccionReq().getRecurso() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getRelLaboral() + "</td>");
                                    out.print("<td>" + recalRec.getMovAccionReq().getRequerimiento() + "</td>");
                                    out.print("<td class='tdCosto' >" + dFormat.format(recalRec.getMovAccionReq().getCostoAnual()) + "</td>");
                                    out.print("<td class='tdCosto' >" + dFormat.format(recalRec.getMovAccionReq().getRecalendarizado()) + "</td>");
                                    if ((estatus.equals("X") || estatus.equals("R")) && isSameUser) {
                                        out.print("<td><a onClick='mostrarProgramacion(\"" + recalRec.getMovAccionReq().getRamo() + "\"," + recalRec.getMovAccionReq().getMeta() + "," + recalRec.getMovAccionReq().getAccion() + ")'><span class='fa fa-calendar botones-img'></span></a>"
                                                + "<input type='button' class='btnbootstrap-drop btn-edicion' " + "onclick='getInfoEdicionAccionRequerimiento(\"" + recalRec.getIdentificador() + "\",\"" + estatus + "\",\"" + recalRec.getMovAccionReq().getRamo() + "\",\"" + recalRec.getMovAccionReq().getPrograma() + "\",\"" + recalRec.getMovAccionReq().getMeta() + "\"," + "\"" + recalRec.getMovAccionReq().getAccion() + "\",\"" + recalRec.getMovAccionReq().getRequerimiento() + "\",\"" + recalRec.getMovAccionReq().getFuente() + "." + recalRec.getMovAccionReq().getFondo() + "." + recalRec.getMovAccionReq().getRecurso() + "\",\"" + recalRec.getMovAccionReq().getPartida() + "\",\"" + recalRec.getMovAccionReq().getRelLaboral() + "\")' />");
                                        out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarRecalendarizacion(\"" + recalRec.getIdentificador() + "\",\"3\");' /></td>");
                                    } else {
                                        if ((autorizar || estatus.equals("A")) && (estatus.equals("W") || estatus.equals("Y") || estatus.equals("V") || estatus.equals("A")) && recalBean.getRolNormativo(Integer.parseInt(rol)) == 1) {
                                            if (recalRec.getMovAccionReq().getConsiderar().equalsIgnoreCase("S")) {
                                                out.print("<td><input type='checkbox' id='considerar" + recalRec.getIdentificador() + "' name='considerar" + recalRec.getIdentificador() + "'  " + "onchange='changeConsiderarRecalendarizacion(\"" + recalRec.getIdentificador() + "\")'   checked ></td>");
                                            } else {
                                                out.print("<td><input type='checkbox' id='considerar" + recalRec.getIdentificador() + "' name='considerar" + recalRec.getIdentificador() + "'  " + "onchange='changeConsiderarRecalendarizacion(\"" + recalRec.getIdentificador() + "\")' ></td>");
                                            }
                                        }
                                        out.print("<td><a onClick='mostrarProgramacion(\"" + recalRec.getMovAccionReq().getRamo() + "\"," + recalRec.getMovAccionReq().getMeta() + "," + recalRec.getMovAccionReq().getAccion() + ")'><span class='fa fa-calendar botones-img'></span></a><input type='button' class='btnbootstrap-drop btn-ver' " + "onclick='getInfoEdicionAccionRequerimiento(\"" + recalRec.getIdentificador() + "\",\"" + estatus + "\",\"" + recalRec.getMovAccionReq().getRamo() + "\",\"" + recalRec.getMovAccionReq().getPrograma() + "\",\"" + recalRec.getMovAccionReq().getMeta() + "\"," + "\"" + recalRec.getMovAccionReq().getAccion() + "\",\"" + recalRec.getMovAccionReq().getRequerimiento() + "\",\"" + recalRec.getMovAccionReq().getFuente() + "." + recalRec.getMovAccionReq().getFondo() + "." + recalRec.getMovAccionReq().getRecurso() + "\",\"" + recalRec.getMovAccionReq().getPartida() + "\",\"" + recalRec.getMovAccionReq().getRelLaboral() + "\")' />");
                                    }
                                    out.print("</tr>");
                                    sumaReq = sumaReq.add(new BigDecimal(recalRec.getMovAccionReq().getCostoAnual()));
                                    sumaRecal = sumaRecal.add(new BigDecimal(recalRec.getMovAccionReq().getRecalendarizado()));
                                }
                                sumaReq = sumaReq.setScale(2, RoundingMode.HALF_UP);
                                out.print("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><b>Total:</b></td><td class='tdCosto'><b>" + dFormat.format(sumaReq.doubleValue()) + "<b></td><td class='tdCosto'><b>" + dFormat.format(sumaRecal.doubleValue()) + "<b></td><td></td></tr>");
                            %>
                        </tbody>
                    </table>
                </fieldset>
                <br/>
                <%
                    out.print("<fieldset id='fldst-movs' " + displayMov + " >");
                %>
                <legend> Movimientos program&aacute;ticos </legend>
                <table id="tbl-movs">
                    <thead>
                        <tr>
                            <th align="right" >Ramo</th>
                            <th align="right" >Programa</th>
                            <th align="right" >Meta</th>
                            <th align="right" >Acci&oacute;n</th>
                            <th align="right" ></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (RecalendarizacionMeta movMeta : recalMetaList) {
                                out.print("<tr>");
                                out.print("<td>" + movMeta.getMovEstimacionList().get(cont).getRamo() + "</td>");
                                out.print("<td>" + movMeta.getMovEstimacionList().get(cont).getPrograma() + "</td>");
                                out.print("<td>" + movMeta.getMovEstimacionList().get(cont).getMeta() + "</td>");
                                out.print("<td></td>");
                                if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaEstimacionTabla" + "(\""
                                            + movMeta.getIdentificado() + "\",\"" + estatus + "\"," + "\""
                                            + movMeta.getMovEstimacionList().get(cont).getRamo() + "\"," + "\""
                                            + movMeta.getMovEstimacionList().get(cont).getMeta() + "\")' />");
                                    out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarRecalendarizacion(\""
                                            + movMeta.getIdentificado() + "\",\"1\");' /></td>");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' onclick='getInfoMetaEstimacionTabla" + "(\""
                                            + movMeta.getIdentificado() + "\",\"" + estatus + "\"," + "\""
                                            + movMeta.getMovEstimacionList().get(cont).getRamo() + "\"," + "\""
                                            + movMeta.getMovEstimacionList().get(cont).getMeta() + "\")' />");
                                }
                                out.print("</tr>");
                                cont++;
                            }
                            cont = 0;
                            for (RecalendarizacionAccion movAccion : recalAccionList) {
                                out.print("<tr>");
                                out.print("<td>" + movAccion.getMovEstimacionList().get(cont).getRamo() + "</td>");
                                out.print("<td>" + movAccion.getMovEstimacionList().get(cont).getPrograma() + "</td>");
                                out.print("<td>" + movAccion.getMovEstimacionList().get(cont).getMeta() + "</td>");
                                out.print("<td>" + movAccion.getMovEstimacionList().get(cont).getAccion() + "</td>");
                                if ((estatus.equals("X") || estatus.equals("R") || autorizar) && isSameUser) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                            + "onclick='getInfoAccionEstimacionTabla(\"" + movAccion.getIdentificado() + "\",\"" + estatus + "\","
                                            + "\"" + movAccion.getMovEstimacionList().get(cont).getRamo() + "\","
                                            + "\"" + movAccion.getMovEstimacionList().get(cont).getMeta() + "\","
                                            + "\"" + movAccion.getMovEstimacionList().get(cont).getAccion() + "\" )' />");
                                    out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarRecalendarizacion(\""
                                            + movAccion.getIdentificado() + "\",\"2\");' /></td>");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                            + "onclick='getInfoAccionEstimacionTabla(\"" + movAccion.getIdentificado() + "\",\"" + estatus + "\","
                                            + "\"" + movAccion.getMovEstimacionList().get(cont).getRamo() + "\","
                                            + "\"" + movAccion.getMovEstimacionList().get(cont).getMeta() + "\","
                                            + "\"" + movAccion.getMovEstimacionList().get(cont).getAccion() + "\" )' />");
                                }
                                out.print("</tr>");
                                cont++;
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
                    <input type="button" value="Guardar" id="btn-save-movs" onclick="enviarAutorizacion(false)"/>
                    <%}%>
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
                    if (recalBean != null) {
                        recalBean.resultSQLDesconecta();
                    }
                }
            %>
            <%--
            <div id="dialog-comentario" title="Comentario de autorizaci&oacute;n">
                <p id="errorComentario" style="color:red"  ></p>
                <label for="name">Comentario</label>
                <textarea maxlength="350" type="text" name="comentarioAuto" id="comentarioAuto" class="textarea-comentario text" ></textarea>
            </div>
            --%>
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
                            <textarea  maxlength="600" class='no-enter' type="text" name="comentarioAuto" style="max-width: 100%; width: 100%" id="comentarioAuto" class="textarea-comentario text col-md-12" ></textarea>  
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
            <script src="librerias/_comentarios.js" type="text/javascript"></script>
            <script src="librerias/js/movimientoJS/_movimiento.js" type="text/javascript"></script>
            <jsp:include page="template/piePagina.jsp" />
    </html>

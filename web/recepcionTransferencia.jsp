<%-- 
    Document   : recepcionTransferencia
    Created on : Feb 8, 2016, 3:51:17 PM
    Author     : ugarcia
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Usuario"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="template/encabezado.jsp" />

    <%
        MovimientosTransferencia movimiento = new MovimientosTransferencia();
        NumberFormat dFormat = NumberFormat.getInstance(Locale.US);
        List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
        List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
        List<TransferenciaAccionReq> transAccionReqList = new ArrayList<TransferenciaAccionReq>();
        Transferencia transferencia = new Transferencia();
        Transferencia transferenciaTemp = new Transferencia();
        List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
        Usuario usuario = new Usuario();
        List<Ramo> ramoList = new ArrayList<Ramo>();
        TransferenciaBean transBean = null;
        BigDecimal totalImporte = new BigDecimal(0.0);
        response.setContentType("text/html;charset=UTF-8");
        int year = 0;
        int meta = 0;
        int accion = 0;
        int proyecto = 0;
        int transferenciaId = 0;
        int mesActual = 0;
        int edicionTrans = 0;
        int nuevo = 0;
        int opcion = 0;
        int contReq = 0;

        double importeTrans = 0.0;
        double importeOriginal = 0.0;
        double importe = 0.0;
        boolean isParaestatal = false;
        boolean inicio = false;
        boolean autorizar = false;
        boolean isSameUser = false;
        
        String infoReq[] = new String[19];
        String appLogin = new String();
        String tipoOficio = new String();
        String impacto = new String();
        String displaybuttos = new String();
        String displayBotones = new String();
        String tipoDependencia = new String();
        String fecha = new String();
        String estatus = new String();
        String ramoDescr = new String();
        String ramo = new String();
        String programaDescr = new String();
        String programa = new String();
        String proyDescr = new String();
        String proy = new String();
        String tipoProyecto = new String();
        String metaDescr = new String();
        String accionDescr = new String();
        String partidaDescr = new String();
        String partida = new String();
        String relLaboralDescr = new String();
        String relLaboral = new String();
        String fuenteDescr = new String();
        String fuente = new String();
        String transitorio = new String();
        String urlRegresa = new String();
        String justificacionTrans = new String();
        String ramoCons = new String();
        Date date = new Date();
        long idCaratula = 0;
        int tipoFlujo = 0;

        try {
            if (session.getAttribute(
                    "year") != null && session.getAttribute("year") != "") {
                year = Integer.parseInt((String) session.getAttribute("year"));
            }

            if (session.getAttribute(
                    "strUsuario") != null && session.getAttribute("strUsuario") != "") {
                appLogin = (String) session.getAttribute("strUsuario");
            }

            if (session.getAttribute(
                    "tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
                tipoDependencia = (String) session.getAttribute("tipoDependencia");
            }
            if (Utilerias.existeParametro("transferenciaId", request)) {
                transferenciaId = Integer.parseInt(request.getParameter("transferenciaId"));
            }

            if (Utilerias.existeParametro("selRamo", request)) {
                ramo = request.getParameter("selRamo");
            }
            if (Utilerias.existeParametro("ramoDescr", request)) {
                ramoDescr = Utilerias.getParametroString("ramoDescr",request);
            }
            if (Utilerias.existeParametro("selPrg", request)) {
                programa = request.getParameter("selPrg");
            }
            if (Utilerias.existeParametro("programaDescr", request)) {
                programaDescr = Utilerias.getParametroString("programaDescr",request);
            }
            if (Utilerias.existeParametro("selProy", request)) {
                proy = request.getParameter("selProy");
            }
            if (Utilerias.existeParametro("tipoOficio", request)) {
                tipoOficio = request.getParameter("tipoOficio");
            }
            if (Utilerias.existeParametro("proyDescr", request)) {
                proyDescr = Utilerias.getParametroString("proyDescr",request);
            }
            if (Utilerias.existeParametro("selMeta", request)) {
                meta = Integer.parseInt(request.getParameter("selMeta"));
            }
            if (Utilerias.existeParametro("metaDescr", request)) {
                metaDescr = Utilerias.getParametroString("metaDescr",request);
            }
            if (Utilerias.existeParametro("selAccion", request)) {
                accion = Integer.parseInt(request.getParameter("selAccion"));
            }
            if (Utilerias.existeParametro("accionDescr", request)) {
                accionDescr = Utilerias.getParametroString("accionDescr",request);
            }
            if (Utilerias.existeParametro("selPartida", request)) {
                partida = request.getParameter("selPartida");
            }
            if (Utilerias.existeParametro("partidaDescr", request)) {
                partidaDescr = Utilerias.getParametroString("partidaDescr",request);
            }
            if (Utilerias.existeParametro("urlRegresa", request)) {
                urlRegresa = request.getParameter("urlRegresa");
            }
            if (Utilerias.existeParametro("ramoCons", request)) {
                ramoCons = request.getParameter("ramoCons");
            }
            if (Utilerias.existeParametro("selRelLaboral", request)) {
                relLaboral = request.getParameter("selRelLaboral");
                if (relLaboral.equals("-1")) {
                    relLaboral = "0";
                }
            }
            if (Utilerias.existeParametro("relLaboralDescr", request)) {
                relLaboralDescr = Utilerias.getParametroString("relLaboralDescr",request);
            }
            if (Utilerias.existeParametro("selFuente", request)) {
                fuente = request.getParameter("selFuente");
            }
            if (Utilerias.existeParametro("fuenteDescr", request)) {
                fuenteDescr = Utilerias.getParametroString("fuenteDescr",request);
            }
            if (Utilerias.existeParametro("mesActual", request)) {
                mesActual = Integer.parseInt(request.getParameter("mesActual"));
            }
            if (Utilerias.existeParametro("dateFor", request)) {
                fecha = request.getParameter("dateFor");
            }
            if (Utilerias.existeParametro("estatus", request)) {
                estatus = request.getParameter("estatus");
            }
            if (Utilerias.existeParametro("justificacionTrans", request)) {
                justificacionTrans = request.getParameter("justificacionTrans");
            }
            if (Utilerias.existeParametro("importeTrans", request)) {
                if (importe == 0.0) {
                    importeTrans = new Double((request.getParameter("importeTrans")).replaceAll(",", ""));
                    importeOriginal = new Double((request.getParameter("importeTrans")).replaceAll(",", ""));
                } else {
                    importeTrans = importe;
                }
            } else {
                importeTrans = importe;
            }
            if (Utilerias.existeParametro("edicionTrans", request)) {
                edicionTrans = Integer.parseInt((request.getParameter("edicionTrans")).replaceAll(",", ""));
            }
            if (edicionTrans > 0) {
                if (Utilerias.existeParametro("edRamo", request)) {
                    ramo = request.getParameter("edRamo");
                }
                if (Utilerias.existeParametro("edPrg", request)) {
                    programa = request.getParameter("edPrg");
                }
                if (Utilerias.existeParametro("edProy", request)) {
                    proy = request.getParameter("edProy");
                }
                if (Utilerias.existeParametro("edMeta", request)) {
                    meta = Integer.parseInt(request.getParameter("edMeta"));
                }
                if (Utilerias.existeParametro("edAccion", request)) {
                    accion = Integer.parseInt(request.getParameter("edAccion"));
                }
                if (Utilerias.existeParametro("edPartida", request)) {
                    partida = request.getParameter("edPartida");
                }
                if (Utilerias.existeParametro("edRelLaboral", request)) {
                    relLaboral = request.getParameter("edRelLaboral");
                }
                if (Utilerias.existeParametro("edFuente", request)) {
                    fuente = request.getParameter("edFuente");
                }
            }
            if (Utilerias.existeParametro("opcion", request)) {
                opcion = Integer.parseInt(request.getParameter("opcion"));
            }
            if (Utilerias.existeParametro("nuevo", request)) {
                nuevo = Integer.parseInt(request.getParameter("nuevo"));
            }
            if (Utilerias.existeParametro("idCaratula", request)) {
                idCaratula = Long.parseLong(request.getParameter("idCaratula"));
            }
            if (Utilerias.existeParametro("inicio", request)) {
                inicio = Boolean.valueOf(request.getParameter("inicio"));
            }
            if (Utilerias.existeParametro("sameUser", request)) {
                isSameUser = Boolean.valueOf(request.getParameter("sameUser"));
            }
            if (Utilerias.existeParametro("autorizacion", request)) {
                autorizar = Boolean.valueOf(request.getParameter("autorizacion"));
            }
            if (estatus.isEmpty()) {
                estatus = "X";
            }
            if (Utilerias.existeParametro("tipoFlujo", request)) {
                tipoFlujo = Integer.parseInt(request.getParameter("tipoFlujo"));
            }
            
            transBean = new TransferenciaBean(tipoDependencia);
            transBean.setStrServer(
                    (request.getHeader("Host")));
            transBean.setStrUbicacion(getServletContext().getRealPath("").toString());
            transBean.resultSQLConecta(tipoDependencia);
            if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
                movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
                transferenciaList = movimiento.getTransferenciaList();
                if (transferenciaId == -1) {
                    if (transferenciaList.size() > 0) {
                        if (inicio) {
                            contReq = transBean.getResultSQLGetMaxConsecTransferencia(movimiento.getOficio());
                            if (contReq > 1) {
                                transferencia.setConsec(contReq);
                            } else {
                                transferencia.setConsec(transferenciaList.get(transferenciaList.size() - 1).getConsec() + 1);
                            }
                            inicio = false;
                        } else {
                            transferencia.setConsec(transferenciaList.get(transferenciaList.size() - 1).getConsec() + 1);
                        }
                    } else {
                        transferencia.setConsec(1);
                    }
                    transferenciaList.add(transferencia);
                    movimiento.setTransferenciaList(transferenciaList);
                    session.setAttribute("transferencia", movimiento);
                } else {
                    for (Transferencia transTemp : transferenciaList) {
                        if (transTemp.getConsec() == transferenciaId) {
                            transferencia = transTemp;
                        }
                    }
                }
                transferenciaTemp = transferencia.clone();
                transAccionList = movimiento.getTransferenciaAccionList();
                transMetaList = movimiento.getTransferenciaMetaList();
                transAccionReqList = transferencia.getTransferenciaAccionReqList();
                /*transBean = new TransferenciaBean(tipoDependencia);
                 transBean.setStrServer(
                 ( request.getHeader("Host")));
                 transBean.setStrUbicacion(getServletContext().getRealPath("").toString());
                 transBean.resultSQLConecta(tipoDependencia);*/
                importe = transferencia.getImporte();
                if (edicionTrans > 0) {
                    importeTrans = transferencia.getImporte();
                }
                session.setAttribute("transferenciaTemp", transferenciaTemp);
            }
            isParaestatal = transBean.isParaestatal();
            if (edicionTrans == 1) {
                infoReq = transBean.getRequerimientoByDatosMovtos(year, ramo, programa,
                        meta, accion, partida, relLaboral,
                        fuente.split("\\.")[0], fuente.split("\\.")[1], fuente.split("\\.")[2]);
                ramo = infoReq[0];
                ramoDescr = infoReq[1];
                programa = infoReq[2];
                programaDescr = infoReq[3];
                proyDescr = infoReq[6];
                meta = Integer.parseInt(infoReq[7]);
                metaDescr = infoReq[8];
                accion = Integer.parseInt(infoReq[9]);
                accionDescr = infoReq[10];
                partida = infoReq[11];
                partidaDescr = infoReq[12];
                relLaboral = infoReq[13];
                relLaboralDescr = infoReq[14];
                fuente = infoReq[15];
                fuenteDescr = infoReq[16];
            }
            usuario = transBean.getInfoUsuario(appLogin, year);
            transitorio = transBean.getResultSQLisRamoTransotorio(year, ramo);
            ramoList = transBean.getRamosTransitorioByUsuario(year, appLogin, transitorio);
            date = transBean.getResultSQLgetServerDate();
            if (transMetaList.size() > 0 || transAccionList.size() > 0) {
                if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientos()' checked/>";
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
                if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) {
                    impacto = "<input type='checkbox' id='chk-imp-prog' onclick='mostarMovimientosAmpliacionRed()' />";
                    displayBotones = "display: none;";
                } else {
                    impacto = "<input type='checkbox' id='chk-imp-prog'  disabled/>";
                    displayBotones = "display: none;";
                }
            }
    %>
    <div Id="TitProcess"><label>Transferencia </label> </div>

    <div class="col-md-8 col-md-offset-2" style="z-index: 2">
        <div class="botones-menu_bootstrap">       
            <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || estatus.equals("V")) && isSameUser) {%>
            <button type="button" class="btnbootstrap btn-atras" onclick="regresarTransferencia()">
                <br/> <small> Regresar </small>
            </button>
            <%} else {%>
            <button type="button" class="btnbootstrap btn-atras" onclick="window.history.back()()">
                <br/> <small> Regresar </small>
            </button>
            <%}%>
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div center-req" style="display: block;">
        <fieldset>
            <legend>C&oacute;digo que transfiere</legend>
            <%
                out.print("	<div> ");
                out.print("		<table id='infoMetaRC' cellspacing='10'> ");
                out.print("			<tbody> ");
                out.print("				<tr>  ");
                out.print("					<td>Ramo:</td>  ");
                out.print("					<td> " + ramoDescr + " </td> ");
                out.print("				</tr> ");
                out.print("				<tr>  ");
                out.print("					<td>Programa:</td> ");
                out.print("					<td> " + programaDescr + " </td> ");
                out.print("				</tr>  ");
                out.print("				<tr>  ");
                out.print("					<td> Proyecto/Actividad: </td>  ");
                out.print("					<td> " + proyDescr + " </td>  ");
                out.print("				</tr> ");
                out.print("				<tr>  ");
                out.print("					<td>Meta:</td>  ");
                out.print("					<td> " + metaDescr + " </td>  ");
                out.print("				</tr> ");
                out.print("				<tr>  ");
                out.print("					<td>Acci&oacute;n:</td>  ");
                out.print("					<td> " + accionDescr + " </td>  ");
                out.print("				</tr> ");
                out.print("				<tr>  ");
                out.print("					<td>Partida</td>  ");
                out.print("					<td> " + partidaDescr + " </td>  ");
                out.print("				</tr> ");
                if (!relLaboral.equals("-1") && !relLaboral.equals("0")) {
                    out.print("				<tr>  ");
                    out.print("					<td>Relaci&oacute;n laboral: </td>  ");
                    out.print("					<td> " + relLaboralDescr + " </td>  ");
                    out.print("				</tr> ");
                }
                out.print("				<tr>  ");
                out.print("					<td>Fuente de financiamiento: </td>  ");
                out.print("					<td> " + fuenteDescr + " </td>  ");
                out.print("				</tr> ");
                out.print("			</tbody> ");
                out.print("		</table>  ");
                out.print("		<br> ");
                out.print("<div> ");
            %>
        </fieldset>
        <fieldset>
            <legend>Seleccione los c&oacute;digos a transferir</legend>
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
                        <td>
                            <label> Importe </label>
                        </td>
                        <td>
                            <input id='inp-txt-impor-ampred' value='<%=dFormat.format(importeTrans)%>' disabled/>
                        </td>
                    </tr>
                    <tr>
                        <td> Ramo: </td>
                        <td>
                            <select id="selRamo" name="selRamo" onchange='getProgramasByRamoUsuario();
                                mostrarEdicionMeta()'>
                                <option value="-1"> -- Seleccione un ramo -- </option>
                                <%
                                    if (ramoList.size() > 0) {
                                        for (Ramo ramoTemp : ramoList) {
                                            out.print("<option value=" + ramoTemp.getRamo() + ">" + ramoTemp.getRamo() + "-" + ramoTemp.getRamoDescr() + " </option>");
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
                                mostrarEdicionMeta()'>
                                <option value="-1"> -- Seleccione un programa -- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Proyecto/Actividad: </td>
                        <td>
                            <select id="selProy" name="selProys" onchange='getMetasByProyectoUsuarioAmpliacionReduccion();
                                mostrarEdicionMeta();'>
                                <option value="-1"> -- Seleccione un proyecto/actividad -- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Meta: </td>
                        <td>
                            <select id="selMeta" name="selMeta" onchange='getAccionByMetaUsuarioAmpliacionReduccion();
                                mostrarEdicionMeta();'>
                                <option value="-1"> -- Seleccione o cree una meta -- </option>
                            </select>
                        </td>
                        <td>                        
                            <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {%>
                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-meta" style="<%=displayBotones%>" 
                                   onclick="getInfoMetaTransferencia('-1','<%=estatus%>','R');"/>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-meta" style="<%=displayBotones%>" 
                                   onclick="getInfoMetaTransferenciaRecalendarizacion('-1','X','R');"/>
                            <%}%>
                        </td>
                    </tr>
                    <tr>
                        <td> Acci&oacute;n: </td>
                        <td>
                            <select id="selAccion" name="selAccion" onchange='getPartidaByAccionAmplRed();
                                mostrarEdicionAccion();'>
                                <option value="-1"> -- Seleccione o cree una acci&oacute;n -- </option>
                            </select>
                        </td>
                        <td>
                            <%if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {%>
                            <input type="button" class="btnbootstrap-drop btn-edicion" id="btn-edit-accion" style="<%=displayBotones%>" 
                                   onclick="getInfoAccionTransferenciaRecalendarizacion('-1','X','R');"/>
                            <input type="button" class="btnbootstrap-drop btn-add" id="btn-add-accion" style="<%=displayBotones%>" 
                                   onclick="getInfoAccionTransferencia('-1','<%=estatus%>','R');"/>
                            <%}%>
                        </td> 
                    </tr>
                    <tr>
                        <td> Partida: </td>
                        <td>
                            <select id="selPartida" name="selPartida" onchange='getRelLaboralByPartidaAmpRed()'>
                                <option value="-1"> -- Seleccione una partida-- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr id="tr-rel-laboral" style="display: none;"> <!--diplay:grid para cuando se tenga que mostrar grid-->
                        <td> Relalci&oacute;n laboral: </td>
                        <td>
                            <%--<select id="selRelLaboral" name="selRelLaboral" onchange='getFuenteFinanciamientoByRelLaboralUsuario()'>--%>
                            <select id="selRelLaboral" name="selRelLaboral" >
                                <option value="-1"> -- Seleccione una relalci&oacute;n laboral-- </option>
                            </select>
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td> Fuente de financiamiento: </td>
                        <td>
                            <input type='text' id="selFuenteD" style="width: 350px;" name="selFuenteD" value="<%=fuenteDescr%>" disabled>
                            <input type='hidden' id="selFuente" name="selFuente" value="<%=fuente%>"> 
                        </td>
                        <td></td>
                    </tr>     
                </table>     
                <div id="tbl-req-ampred">
                    <%if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {%>
                    <input type="button" value="Insertar requerimiento" id="btn-ampliacion"  onclick="getTransferenciaAccionrReq()"/>
                    <%}%>
                </div> 
            </div>
        </fieldset>
        <input type="hidden" id="inp-txt-impor-ampred" value="<%=importeTrans%>" />
        <input type="hidden" id="transferenciaId" value="<%=transferencia.getConsec()%>" />
        <input type="hidden" id="estatus" value="<%=estatus%>" />
        <input type='hidden' id="codigoDisminuye" name="codigoDisminuye" value="<%=ramo + "-" + programa + "-" + proy + "-" + meta + "-" + accion + "-" + partida + "-" + fuente + "-" + relLaboral + "-" + fuente%>" style ="width: 500px;">
        <div id="PopUpZone"></div>
        <fieldset id="fldst-ampred">
            <legend> C&oacute;digos que reciben </legend>
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
                        <%if (!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()) {%>
                        <th>Disponible Acumulado</th>
                        <%}%>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (TransferenciaAccionReq trasnferencia : transAccionReqList) {
                            totalImporte = totalImporte.add(new BigDecimal(trasnferencia.getImporte()));
                            out.print("<tr>");
                            out.print("<td></td>");
                            out.print("<td>" + trasnferencia.getRamo() + "</td>");
                            out.print("<td>" + trasnferencia.getDepto() + "</td>");
                            out.print("<td>" + trasnferencia.getPrograma() + "</td>");
                            out.print("<td>" + trasnferencia.getTipoProy() + "-" + trasnferencia.getProy() + "</td>");
                            out.print("<td>" + trasnferencia.getMeta() + "</td>");
                            out.print("<td>" + trasnferencia.getAccion() + "</td>");
                            out.print("<td>" + trasnferencia.getPartida() + "</td>");
                            out.print("<td>" + trasnferencia.getFuente() + "."
                                    + trasnferencia.getFondo() + "."
                                    + trasnferencia.getRecurso() + "</td>");
                            out.print("<td>" + trasnferencia.getRelLaboral() + "</td>");
                            out.print("<td>" + dFormat.format(trasnferencia.getQuincePor()) + "</td>");
                            out.print("<td>" + dFormat.format(trasnferencia.getAcumulado()) + "</td>");
                            out.print("<td>" + dFormat.format(trasnferencia.getImporte()) + "</td>");
                            if (!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()) {
                                out.print("<td>" + dFormat.format(trasnferencia.getDisponible()) + "</td>");
                            }
                            if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) && isSameUser) {
                                out.print("<td><a onClick='mostrarProgramacion(\""+trasnferencia.getRamo()+"\","+trasnferencia.getMeta()+","+trasnferencia.getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                                        + "<input type='button' class='btnbootstrap-drop btn-edicion' "
                                        + "onclick='getInfoEdicionAccionRequerimientoTransferencia(\"" + trasnferencia.getIdentidicador() + "\",\"" + estatus + "\",\""
                                        + trasnferencia.getRamo() + "\",\"" + trasnferencia.getPrograma() + "\",\"" + trasnferencia.getMeta() + "\","
                                        + "\"" + trasnferencia.getAccion() + "\",\"" + trasnferencia.getConsecutivo() + "\",\"" + trasnferencia.getImporte() + "\")' />");
                                out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarTransferencia(\"" + trasnferencia.getIdentidicador() + "\",\"6\",\"" + transferenciaId + "\");' /></td>");
                            } else {
                                out.print("<td><a onClick='mostrarProgramacion(\""+trasnferencia.getRamo()+"\","+trasnferencia.getMeta()+","+trasnferencia.getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                                        + "<input type='button' class='btnbootstrap-drop btn-ver' "
                                        + "onclick='getInfoEdicionAccionRequerimientoTransferencia(\"" + trasnferencia.getIdentidicador() + "\",\"" + estatus + "\",\""
                                        + trasnferencia.getRamo() + "\",\"" + trasnferencia.getPrograma() + "\",\"" + trasnferencia.getMeta() + "\","
                                        + "\"" + trasnferencia.getAccion() + "\",\"" + trasnferencia.getConsecutivo() + "\")' />");
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><label>TOTAL:</label></td><td>" + dFormat.format(totalImporte.doubleValue()) + "</td></tr>");
                    %>
                </tbody>
            </table>
        </fieldset>
        <br/>
        <fieldset id='fldst-movs' >
            <legend> Movimientos program&aacute;ticos: </legend>
            <table id="tbl-movs">
                <thead>
                    <tr>
                        <th align="right" >Ramo</th>
                        <%--<th align="right" >Programa</th>--%>
                        <th align="right" >Meta</th>
                        <th align="right" >Acci&oacute;n</th>
                        <th align="right" >Estimaci&oacute;n original</th>
                        <th align="right" >Propuesta</th>
                        <th align="right" ></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (TransferenciaMeta movMeta : transMetaList) {
                            out.print("<tr>");
                            out.print("<td>" + movMeta.getMovOficioMeta().getRamoId() + "</td>");
                            out.print("<td>" + movMeta.getMovOficioMeta().getMetaId() + "</td>");
                            out.print("<td> - </td>");
                            out.print("<td> " + dFormat.format(transBean.getResultSQLgetEstimacionOrigiginal(estatus,
                                    movimiento.getOficio(), year, movMeta.getMovOficioMeta().getRamoId(),
                                    movMeta.getMovOficioMeta().getMetaId())) + " </td>");
                            out.print("<td> " + dFormat.format(movMeta.getPropuestaEstimacion()) + " </td>");
                            if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                                if (movMeta.getMovOficioMeta().getNva_creacion().equals("S")) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaTransferenciaTabla"
                                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' />");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' />");
                                }
                                out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarTransferencia(\"" + movMeta.getIdentificador() + "\",\"1\",\"" + transferenciaId + "\");' /></td>");
                            } else {
                                if (movMeta.getMovOficioMeta().getNva_creacion().equals("S")) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' onclick='getInfoMetaTransferenciaTabla"
                                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' />");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' />");
                                }
                            }
                            out.print("</tr>");
                        }

                        for (TransferenciaAccion movAccion : transAccionList) {
                            out.print("<tr>");
                            out.print("<td>" + movAccion.getMovOficioAccion().getRamoId() + "</td>");
                            out.print("<td>" + movAccion.getMovOficioAccion().getMetaId() + "</td>");
                            out.print("<td>" + movAccion.getMovOficioAccion().getAccionId() + "</td>");
                            out.print("<td> " + dFormat.format(transBean.getResultSQLgetAccionEstimacionOriginal(estatus,
                                    movimiento.getOficio(), year, movAccion.getMovOficioAccion().getRamoId(),
                                    movAccion.getMovOficioAccion().getMetaId(),
                                    movAccion.getMovOficioAccion().getAccionId())) + " </td>");
                            out.print("<td> " + dFormat.format(movAccion.getPropuestaEstimacion()) + " </td>");
                            //out.print("<td>" + movAccion.getMovOficioAccion().getDeptoId() + "</td>");
                            if ((estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) && isSameUser) {
                                if (movAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                            + "onclick='getInfoAccionTransferenciaTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\" )' />");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                                            + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\" )' />");
                                }
                                out.print("<input type='button' class='btnbootstrap-drop btn-borrar' "
                                        + "onclick='borrarTransferencia(\"" + movAccion.getIdentificador() + "\",\"2\",\"" + transferenciaId + "\");' /></td>");
                            } else {
                                if (movAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                            + "onclick='getInfoAccionTransferenciaTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\" )' />");
                                } else {
                                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                                            + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\" )' />");
                                }
                            }
                            out.print("</tr>");
                        }
                    %>
                </tbody>
            </table>
        </fieldset>
        <form id="form-trans-rec" method="POST" action="transferencia.jsp">
            <input type="hidden" id="isRedirected" name="isRedirected" value="true"/>
            <input type="hidden" id="transfRecibe" name="transfRecibe" value="true"/>
            <input type="hidden" id="trans-work" name="trans-work" value="true" />
            <input  type="hidden" id="ramo" name="ramo" value="<%=ramo%>" />
            <input  type="hidden" id="programa" name="programa" value="<%=programa%>" />
            <input  type="hidden" id="proyecto" name="proyecto" value="<%=proy.split(",")[0]%>" />
            <input  type="hidden" id="tipoProy" name="tipoProy" value="<%=proy.split(",")[1]%>" />
            <input  type="hidden" id="proyecto" name="proyectoR" value="<%=proy%>" />
            <input  type="hidden" id="meta" name="meta" value="<%=meta%>" />
            <input  type="hidden" id="accion" name="accion" value="<%=accion%>" />
            <input  type="hidden" id="partida" name="partida" value="<%=partida%>" />
            <input  type="hidden" id="fuente" name="fuente" value="<%=fuente%>" />
            <input  type="hidden" id="relLaboral" name="relLaboral" value="<%=relLaboral%>" />
            <input  type="hidden" id="transferenciaId" name="transferenciaId" value="<%=transferencia.getConsec()%>" />
            <input  type="hidden" id="mesActual" name="mesActual" value="<%=mesActual%>" />
            <input  type="hidden" id="importeTrans" name="importeTrans" value="<%=importeTrans%>" />
            <input  type="hidden" id="estatus" name="estatus" value="<%=estatus%>" />
            <input type="hidden" value="<%=movimiento.getOficio()%>" id="folio" name="folio"  />
            <input type="hidden" value="<%=fecha%>" id="fecha" name="fecha"  />
            <input type="hidden" value="<%=urlRegresa%>" id="urlRegresa" name="urlRegresa"  />
            <input type="hidden" id="tipoOficio" name="tipoOficio" value="<%=tipoOficio%>" />
            <input type="hidden" id="nuevoT" name="nuevoT" value="<%=nuevo%>" />
            <input type="hidden" id="opcionT" name="opcionT" value="<%=opcion%>" />
            <input type="hidden" id="idCaratula" name="idCaratula" value="<%=idCaratula%>" />
            <input type="hidden" id="inicio" name="inicio" value="<%=inicio%>" />
            <input type="hidden" id="autorizacion" name="autorizacion" value="<%=autorizar%>" />
            <input type="hidden" id="ramoCons" name="ramoCons" value="<%=ramoCons%>" />
            <input type="hidden" id="sameUser" name="sameUser" value="<%=isSameUser%>" />
            <input type="hidden" id="justificacionTrans" name="justificacionTrans" value="<%=justificacionTrans%>" />
            <input type="hidden" id="tipoFlujo" name="tipoFlujo" value="<%=tipoFlujo%>" />
            <%if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || (!estatus.equals("T") && autorizar)) {
                    out.print(displaybuttos);
            %>
            <div id="div-aceptar">
                <input type="button" id="return-transferencia" value="Aceptar" onclick="salvarTransferencia()" />
                <input type="button" id="cancel-transferencia" value="Cancelar" onclick="cancelarTransferencia();" />            
            </div>
            <%}%>
        </form>
    </div>
    <div id="programacion" title="Programaci&oacute;n"></div>
    <%
        } catch (Exception ex) {
            Bitacora bitacora = new Bitacora();
            bitacora.setStrServer(request.getHeader("host"));
            bitacora.setStrUbicacion(getServletContext().getRealPath(""));
            bitacora.setITipoBitacora(1);
            bitacora.setStrInformacion(ex, request.getServletPath());
            bitacora.grabaBitacora();
        } finally {
            if (transBean != null) {
                transBean.resultSQLDesconecta();
            }
        }
    %>
    <script src="librerias/js/movimientoJS/_movimiento.js" type="text/javascript"></script>
    <jsp:include page="template/piePagina.jsp" />
</html>
<%-- 
    Document   : ajaxGetInfoMovAmpRedAccionReq
    Created on : Jan 18, 2016, 10:20:37 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.Articulo"%>
<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.util.Utileria"%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    request.setCharacterEncoding("UTF-8");
    ParametrosBean parametrosBean = null;
    Parametros objParametros = new Parametros();
    boolean enableAll = false;
    TransferenciaBean transferenciaBean = null;
    Meta metaObj = new Meta();
    Partida objPartida = new Partida();
    Requerimiento requerimiento = new Requerimiento();
    TransferenciaAccionReq transAccionReq = new TransferenciaAccionReq();
    List<TransferenciaAccionReq> transAccionReqList = new ArrayList<TransferenciaAccionReq>();
    TransferenciaAccion transAccion = new TransferenciaAccion();
    TransferenciaMeta transMeta = new TransferenciaMeta();
    List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
    List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
    List<Articulo> articuloList = new ArrayList<Articulo>();
    MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
    Transferencia transferencia = new Transferencia();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    Double importe = 0.0;
    Double sumEstimacion = 0.0;

    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat formatYear = new SimpleDateFormat("yyyy");
    DateFormat formatMonth = new SimpleDateFormat("MM");
    Calendar cal = Calendar.getInstance();
    String tipoDependencia = new String();
    String strResultado = new String();
    String ramoId = new String();
    String ramoDescr = new String();
    String programaId = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String status = new String();
    String inputAllDisable = "";
    String metaDescr = new String();
    String accionDescr = new String();
    String partida = new String();
    String partidaDescr = new String();
    String relLaboral = new String();
    String relLaboralDescr = new String();
    String fuente = new String();
    String fuenteDescr = new String();
    String infoReq[] = new String[19];
    String disabled = new String();
    String rol = new String();
    String tipoGasto = new String();
    String disabledRel = new String();
    String selected = new String();
    String tipoProy = new String();
    String fechaConta = new String();
    String disableJustig = new String();

    boolean fechaCont = false;
    boolean validaTrimestre = false;
    boolean banderaEstimacion = true;

    int meses[] = new int[3];
    int justific = 0;
    int edicion = 0;
    int year = 0;
    int metaId = 0;
    int accion = 0;
    int proy = 0;
    int requ = 0;
    int numAccionReq = 0;
    int identificador = 0;
    int transferenciaId = -1;
    int yearAct = Integer.parseInt(formatYear.format(cal.getTime()));
    int mesTemp = 0;
    int monthAct = 0;
    int mesConta = 0;
    int opcion = 0;
    int consulta = 0;
    double importeCap = 0;
    BigDecimal nuevoImporte = new BigDecimal(0.0);
    BigDecimal nuevoImporteCap = new BigDecimal(0.0);

    try {
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("strRol") != null && session.getAttribute("strRol") != "") {
            rol = (String) session.getAttribute("strRol");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = request.getParameter("ramo");
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programaId = request.getParameter("programa");
        }
        if (request.getParameter("programaDescr") != null && !request.getParameter("programaDescr").equals("")) {
            programaDescr = request.getParameter("programaDescr");
        }
        if (request.getParameter("proyectoDescr") != null && !request.getParameter("proyectoDescr").equals("")) {
            proyectoDescr = request.getParameter("proyectoDescr");
        }
        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proy = Integer.parseInt(request.getParameter("proyecto"));
        }
        if (request.getParameter("tipoProyecto") != null && !request.getParameter("tipoProyecto").equals("")) {
            tipoProy = request.getParameter("tipoProyecto");
        }
        if (request.getParameter("meta") != null && !request.getParameter("meta").equals("")) {
            metaId = Integer.parseInt(request.getParameter("meta"));
        }
        if (request.getParameter("metaDescr") != null && !request.getParameter("metaDescr").equals("")) {
            metaDescr = request.getParameter("metaDescr");
        }
        if (request.getParameter("accion") != null && !request.getParameter("accion").equals("")) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if (request.getParameter("accionDescr") != null && !request.getParameter("accionDescr").equals("")) {
            accionDescr = request.getParameter("accionDescr");
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (request.getParameter("partidaDescr") != null && !request.getParameter("partidaDescr").equals("")) {
            partidaDescr = request.getParameter("partidaDescr");
        }
        if (request.getParameter("importeTrans") != null && !request.getParameter("importeTrans").equals("")) {
            importe = new Double(request.getParameter("importeTrans"));
        }
        if (request.getParameter("importe") != null && !request.getParameter("importe").equals("")) {
            if (importe == 0.0) {
                importe = new Double(request.getParameter("importe"));
            } else {
                importeCap = new Double(request.getParameter("importe"));
            }
        }
        if (request.getParameter("relLaboral") != null && !request.getParameter("relLaboral").equals("")) {
            relLaboral = request.getParameter("relLaboral");
            if (relLaboral.equals("-1")) {
                relLaboral = "0";
            }
        }
        if (request.getParameter("relLaboralDescr") != null && !request.getParameter("relLaboralDescr").equals("")) {
            relLaboralDescr = request.getParameter("relLaboralDescr");
        }
        if (request.getParameter("fuente") != null && !request.getParameter("fuente").equals("")) {
            fuente = request.getParameter("fuente");
        }
        if (request.getParameter("fuenteDescr") != null && !request.getParameter("fuenteDescr").equals("")) {
            fuenteDescr = request.getParameter("fuenteDescr");
        }
        if (request.getParameter("req") != null && !request.getParameter("req").equals("")) {
            requ = Integer.parseInt(request.getParameter("req"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("transferenciaId") != null && !request.getParameter("transferenciaId").equals("")) {
            transferenciaId = Integer.parseInt(request.getParameter("transferenciaId"));
        }
        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }
        if (Utilerias.existeParametro("edicion", request)) {
            edicion = Integer.parseInt(request.getParameter("edicion"));
        }
        if (Utilerias.existeParametro("fecha", request)) {
            mesTemp = Integer.parseInt(request.getParameter("fecha").split("\\/")[1]);
        }
        if (Utilerias.existeParametro("fechaCont", request)) {
            fechaCont = Boolean.parseBoolean(request.getParameter("fechaCont"));
        }
        if (Utilerias.existeParametro("opcion", request)) {
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            status = request.getParameter("estatus");
            if ((!status.equalsIgnoreCase("C")) && (!status.equalsIgnoreCase("X")) && (!status.equalsIgnoreCase("R"))) {
                inputAllDisable = "disabled=''";
                disabled = "disabled";
            }
        }

        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            for (Transferencia transTemp : movTransferencia.getTransferenciaList()) {
                if (transTemp.getConsec() == transferenciaId) {
                    transferencia = transTemp;
                }
            }

            nuevoImporte = new BigDecimal(importe);
            transAccionReqList = transferencia.getTransferenciaAccionReqList();
            for (TransferenciaAccionReq movtoAccionReqtemp : transAccionReqList) {
                if (movtoAccionReqtemp.getIdentidicador() == identificador) {
                    transAccionReq = movtoAccionReqtemp;
                }
                nuevoImporte = nuevoImporte.subtract(new BigDecimal(movtoAccionReqtemp.getImporte()));
            }
            if (edicion > 0) {
                transMetaList = movTransferencia.getTransferenciaMetaList();
                transAccionList = movTransferencia.getTransferenciaAccionList();
                for (TransferenciaAccion transAccionTemp : transAccionList) {
                    if (transAccionTemp.getMovOficioAccion().getRamoId().equals(ramoId)
                            && transAccionTemp.getMovOficioAccion().getMetaId() == metaId
                            && transAccionTemp.getMovOficioAccion().getAccionId() == accion) {
                        transAccion = transAccionTemp;
                    }
                }
                for (TransferenciaMeta transMetaTemp : transMetaList) {
                    if (transMetaTemp.getMovOficioMeta().getRamoId().equals(ramoId)
                            && transMetaTemp.getMovOficioMeta().getMetaId() == metaId) {
                        transMeta = transMetaTemp;
                    }
                }
            }
        }
        for (TransferenciaMeta meta : movTransferencia.getTransferenciaMetaList()) {
            if (meta.getMovOficioMeta().getMetaId() == metaId) {
                for (MovOficioEstimacion estimacion : meta.getMovOficioEstimacion()) {
                    sumEstimacion += estimacion.getValor();
                }
                if (sumEstimacion == 0) {
                    banderaEstimacion = false;
                }
            }
        }
        sumEstimacion = 0.0;
        for (TransferenciaAccion accionT : movTransferencia.getTransferenciaAccionList()) {
            if (accionT.getMovOficioAccion().getRamoId().equals(ramoId)
                    && accionT.getMovOficioAccion().getMetaId() == metaId
                    && accionT.getMovOficioAccion().getAccionId() == accion) {
                for (MovOficioAccionEstimacion estimacion : accionT.getMovOficioAccionEstList()) {
                    sumEstimacion += estimacion.getValor();
                }
                if (sumEstimacion == 0) {
                    banderaEstimacion = false;
                }
            }
        }
        if (banderaEstimacion) {
            if (nuevoImporte.compareTo(new BigDecimal(0)) > 0 || edicion > 0) {
                transferenciaBean = new TransferenciaBean(tipoDependencia);
                transferenciaBean.setStrServer(request.getHeader("host"));
                transferenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
                transferenciaBean.resultSQLConecta(tipoDependencia);
                validaTrimestre = transferenciaBean.getResultSqlGetparametroTrimestre();
                monthAct = mesTemp;
                meses = transferenciaBean.getResultSQLgetMesTrimestreByPeriodo(monthAct, validaTrimestre);
                if (!validaTrimestre) {
                    if (meses.length > 0) {
                        monthAct = meses[0];
                    } else {
                        monthAct = 1;
                    }
                }
                if (fechaCont) {
                    fechaConta = dt.format(transferenciaBean.getResultSQLGetFechaAplicacionGasto(year));
                    mesConta = Integer.parseInt(fechaConta.split("/")[1]);
                }
                if (edicion > 0) {
                    if (accion < 0) {
                        ramoId = ramoId;
                        ramoDescr = transferenciaBean.getResultSQLGetRamoDescr(year, ramoId);
                        programaId = programaId;
                        programaDescr = transferenciaBean.getResultSQLGetProgramaDescr(year, programaId);
                        if (metaId > 0) {
                            metaObj = transferenciaBean.getResultGetMetaById(ramoId, programaId, transAccionReq.getProy(), metaId, year, transAccionReq.getTipoProy());
                            proyectoDescr = transferenciaBean.getResultSQLGetProyectoDescr(year,
                                    transAccionReq.getTipoProy(),
                                    transAccionReq.getProy());
                            metaId = metaId;
                            metaDescr = metaId + "-" + metaObj.getMeta();
                            proy = metaObj.getProyecto();
                            tipoProy = metaObj.getTipoProyecto();
                        } else {
                            proyectoDescr = transferenciaBean.getResultSQLGetProyectoDescr(year,
                                    transMeta.getMovOficioMeta().getTipoProy(),
                                    transMeta.getMovOficioMeta().getProyId());
                            metaId = transMeta.getMovOficioMeta().getMetaId();
                            metaDescr = transMeta.getMovOficioMeta().getMetaId() + "-" + transMeta.getMovOficioMeta().getMetaDescr();
                        }
                        accion = transAccion.getMovOficioAccion().getAccionId();
                        accionDescr = transAccion.getMovOficioAccion().getAccionId() + "-" + transAccion.getMovOficioAccion().getAccionDescr();
                        partida = transAccionReq.getPartida();
                        partidaDescr = transferenciaBean.getResultSQLGetPartidaDescr(year, transAccionReq.getPartida());
                        relLaboral = transAccionReq.getRelLaboral();
                        relLaboralDescr = transferenciaBean.getResultSQLGetRelLaboralDescr(year, transAccionReq.getRelLaboral());
                        fuente = transAccionReq.getFuente() + "." + transAccionReq.getFondo() + "." + transAccionReq.getRecurso();
                        fuenteDescr = transferenciaBean.getResultSQLGetFuenteFinDescr(year, transAccionReq.getFuente(),
                                transAccionReq.getFondo(), transAccionReq.getRecurso());
                    } else {
                        infoReq = transferenciaBean.getRequerimientoByDatosMovtos(year, ramoId, programaId,
                                metaId, accion, transAccionReq.getPartida(), transAccionReq.getRelLaboral(),
                                transAccionReq.getFuente(), transAccionReq.getFondo(), transAccionReq.getRecurso());
                        ramoId = infoReq[0];
                        ramoDescr = infoReq[1];
                        programaId = infoReq[2];
                        programaDescr = infoReq[3];
                        proyectoDescr = infoReq[6];
                        metaId = Integer.parseInt(infoReq[7]);
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
                }
                justific = transferenciaBean.isPartidaJustific(year, partida);
                tipoGasto = transferenciaBean.getTipoGastoByPartida(partida, year);
                objPartida = transferenciaBean.getResultSQLGetPartidaByIdPartidaYear(partida, year);
                if (opcion == 1) {
                    requerimiento.setReq("");
                    //disabledRel = "display: none;";
                    requerimiento.setPartida(partida);
                    requerimiento.setFuenteFin(fuente.split("\\.")[0]);
                    requerimiento.setFondo(fuente.split("\\.")[1]);
                    requerimiento.setRecurso(fuente.split("\\.")[2]);
                    requerimiento.setTipoGasto("");
                    requerimiento.setRelLaboral(relLaboral);
                    requerimiento.setCostoAnual(0.0);
                    requerimiento.setCostoUnitario(0.0);

                } else {
                    if (edicion == 1) {
                        requerimiento = new Requerimiento();
                        //requerimiento.setArticulo(new Double(movtoAccionReq.getMovOficioAccionReq().getArticulo()));
                        requerimiento.setReq(transAccionReq.getMovOficioAccionReq().getReqDescr());
                        requerimiento.setReqId(transAccionReq.getMovOficioAccionReq().getRequerimiento());
                        requerimiento.setJustif(transAccionReq.getMovOficioAccionReq().getJustificacion());
                        requerimiento.setCantEne(transAccionReq.getMovOficioAccionReq().getEne());
                        requerimiento.setCantFeb(transAccionReq.getMovOficioAccionReq().getFeb());
                        requerimiento.setCantMar(transAccionReq.getMovOficioAccionReq().getMar());
                        requerimiento.setCantAbr(transAccionReq.getMovOficioAccionReq().getAbr());
                        requerimiento.setCantMay(transAccionReq.getMovOficioAccionReq().getMay());
                        requerimiento.setCantJun(transAccionReq.getMovOficioAccionReq().getJun());
                        requerimiento.setCantJul(transAccionReq.getMovOficioAccionReq().getJul());
                        requerimiento.setCantAgo(transAccionReq.getMovOficioAccionReq().getAgo());
                        requerimiento.setCantSep(transAccionReq.getMovOficioAccionReq().getSep());
                        requerimiento.setCantOct(transAccionReq.getMovOficioAccionReq().getOct());
                        requerimiento.setCantNov(transAccionReq.getMovOficioAccionReq().getNov());
                        requerimiento.setCantDic(transAccionReq.getMovOficioAccionReq().getDic());
                        requerimiento.setCantidad(transAccionReq.getMovOficioAccionReq().getCantidad());
                        requerimiento.setCostoAnual(transAccionReq.getMovOficioAccionReq().getCostoAnual());
                        requerimiento.setCostoUnitario(transAccionReq.getMovOficioAccionReq().getCostoUnitario());
                        try {
                            requerimiento.setArticulo(Integer.parseInt(transAccionReq.getMovOficioAccionReq().getArticulo()));
                        } catch (NumberFormatException e) {
                            requerimiento.setArticulo(0);
                        }
                        justific = transferenciaBean.isPartidaJustific(year, transAccionReq.getPartida());
                        tipoGasto = transferenciaBean.getTipoGastoByPartida(transAccionReq.getPartida(), year);
                        //editDis = "disabled";
                        if (!transferenciaBean.isRelLaboral(year, requerimiento.getPartida())) {
                            disabledRel = "visibility:hidden;";
                        } else {
                            disabledRel = "visibility:visible";
                        }
                    }
                }

                strResultado += "<input id='justifReq' name='justifReq' type='hidden' value='" + objPartida.getJustifReq() + "' />";
                //requerimiento = recalBean.getRequerimientoById(year, ramoId, programaId, metaId, accion, requ);
                strResultado += "<div id='popUp-accion-req' style=''> ";
                strResultado += "	<div> ";
                strResultado += "		<table id='infoMetaRC' cellspacing='10'> ";
                strResultado += "			<tbody> ";
                strResultado += "				<tr>  ";
                strResultado += "					<td>Ramo:</td>  ";
                strResultado += "					<td> " + ramoDescr + " </td> ";
                strResultado += "				</tr> ";
                strResultado += "				<tr>  ";
                strResultado += "					<td>Programa:</td> ";
                strResultado += "					<td> " + programaDescr + " </td> ";
                strResultado += "				</tr>  ";
                strResultado += "				<tr>  ";
                strResultado += "					<td> Proyecto/Actividad: </td>  ";
                strResultado += "					<td> " + proyectoDescr + " </td>  ";
                strResultado += "				</tr> ";
                strResultado += "				<tr>  ";
                strResultado += "					<td>Meta:</td>  ";
                strResultado += "					<td> " + metaDescr + " </td>  ";
                strResultado += "				</tr> ";
                strResultado += "				<tr>  ";
                strResultado += "					<td>Acci&oacute;n:</td>  ";
                strResultado += "					<td> " + accionDescr + " </td>  ";
                strResultado += "				</tr> ";
                strResultado += "				<tr>  ";
                strResultado += "					<td>Partida</td>  ";
                strResultado += "					<td> " + partidaDescr + " </td>  ";
                strResultado += "				</tr> ";
                if (!relLaboral.equals("-1") && !relLaboral.equals("0")) {
                    strResultado += "				<tr>  ";
                    strResultado += "					<td>Relaci&oacute;n laboral: </td>  ";
                    strResultado += "					<td> " + relLaboralDescr + " </td>  ";
                    strResultado += "				</tr> ";
                }
                strResultado += "				<tr>  ";
                strResultado += "					<td>Fuente de financiamiento: </td>  ";
                strResultado += "					<td> " + fuenteDescr + " </td>  ";
                strResultado += "				</tr> ";
                strResultado += "			</tbody> ";
                strResultado += "		</table>  ";
                strResultado += "		<br> ";
                strResultado += "<div> ";
                strResultado += "</br>";

                //estimacionList = accionBean.getEstimacionByAccion(year, ramoId, metaId, accion);
                if (opcion == 2) {
                    strResultado += "<input id='reqJust' type='hidden' value='" + justific + "' />";
                }
                /*
                 if (estimacionList.size() > 0) {
                 strResultado += "<center>";
                 strResultado += "   <label>PROGRAMACI&Oacute;N DE ACCI&Oacute;N POR MES </label>";
                 strResultado += "</center>";
                 strResultado += "<div id='calenVista'>";
                 for (Estimacion estimacion : estimacionList) {
                 strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                 cont++;
                 }
                 strResultado += "</div>";

                 } else {

                 strResultado += "<center>";
                 strResultado += "   <label>PROGRAMACI&Oacute;N DE ACCI&Oacute;N POR MES </label>";
                 strResultado += "</center>";
                 strResultado += "</br>";

                 }
                 */

                if (opcion == 1) {
                    strResultado += "<table>";
                } else {
                    strResultado += "<table><tr><td> Requerimiento: &nbsp" + requerimiento.getReqId() + "</td> </tr>";
                }
                //strResultado += "<tr id='partidaGasto'> <td> <div> Partida </div> <input type='text' disabled value='"+partida +"-"+partidaDescr+"' /> </td>";
                strResultado += "<td id='divVwTipoGasto'>   <div> Tipo de gasto </div> <input id='vwTipoGasto' type='text' value='" + tipoGasto + "' disabled /> </td>";
                //strResultado += "<td id='relTd' style='" + disabledRel + "'> <div> Relaci&oacute;n laboral </div> <select id='selRelacion' value='"+relLaboral+"-"+relLaboralDescr+"' /> </td> </tr>";
                strResultado += "<td> <div> Importe a transferir: </div>";
                strResultado += "<input type='text' id='popImporte' value='" + numberF.format(nuevoImporte) + "' disabled/> </td>";
                strResultado += "<form id='frmReq'>";
                if (requerimiento.getJustif() == null) {
                    requerimiento.setJustif("");
                }
                strResultado += " <tr> <td id='colPartida' align='left' colspan='3'> <div> Requerimiento </div> ";
                if (!transferenciaBean.isArticuloPartida(year, partida)) {
                    articuloList = transferenciaBean.getArticuloPartida(year, partida);
                    strResultado += "<select id='ArtPartida' onChange='getCosto()' tabindex=\"2\" >";
                    strResultado += "<option value='-1'> -- Seleccione un art&iacute;culo -- </option>";
                    for (Articulo articulo : articuloList) {
                        if (requerimiento.getArticulo() == Integer.parseInt(articulo.getArticuloId())) {
                            selected = "selected";
                        }
                        strResultado += "<option value='" + articulo.getArticuloId() + "' " + selected + ">" + articulo.getArticuloId() + "-" + articulo.getArticulo() + "</option>";
                        selected = "";
                    }
                    strResultado += "</select>";
                } else {
                    strResultado += "<textArea id='txtAreaPart' name='limitedtextarea' class='no-enter' tabindex='0'"
                            + " maxlength='300' " + disabled + " >" + requerimiento.getReq() + "</textArea>";
                }
                if (justific == 0) {
                    disableJustig = "disabled";
                } else {
                    disableJustig = new String();
                }

                parametrosBean = new ParametrosBean(tipoDependencia);
                parametrosBean.setStrServer((request.getHeader("Host")));
                parametrosBean.setStrUbicacion(getServletContext().getRealPath("").toString());
                parametrosBean.resultSQLConecta(tipoDependencia);

                objParametros = parametrosBean.getParametros();

                if (objParametros.getValidaTodosTrimestre().equals("S")) {
                    enableAll = true;
                } else {
                    enableAll = false;
                }

                strResultado += "</td> </tr>";
                strResultado += "<tr> <td colspan='3'> <div> Justificaci&oacute;n </div>";
                strResultado += "<textArea id='txtAreaJust' maxlength='300' class='no-enter' tabindex='0' " + disableJustig + " >" + requerimiento.getJustif() + "</textArea> </td> <tr> ";
                strResultado += "<tr> <td colspan='3'>";
                strResultado += Utileria.getCalendarizacionAccionReqEspecial(requerimiento, "", true, monthAct, false, meses, mesConta, enableAll);

                strResultado += "<br/>";
                strResultado += "</td> </tr>";
                strResultado += "<tr> <td> <div> Cantidad <div> ";
                strResultado += "<input type='text' id='inpTxtCantidad' value='" + numberF.format(requerimiento.getCantidad()) + "' maxlength='14' disabled/> </td>";
                strResultado += "<td> <div> Costo unitario </div> <input type='text' id='inpTxtCantUnit' value='" + numberF.format(requerimiento.getCostoUnitario()) + "' onblur='agregarFormato(\"inpTxtCantUnit\");calculaCosto()' onkeyup='validaMascara(\"inpTxtCantUnit\")' tabindex='0' maxlength='16'  /> </td>";
                strResultado += "<td> <div> Costo anual </div> <input type='text' id='inpTxtCantAnual' maxlength='14' value='" + numberF.format(requerimiento.getCostoAnual()) + "' disabled/> </td> </tr>";
                strResultado += "<input type='hidden' id='importeOriginal' value='" + new BigDecimal(requerimiento.getCostoAnual()).add(nuevoImporte) + "'/>";
                strResultado += "</table> <br/>";
                strResultado += "<center> <div>";
                if (consulta != 1) {
                    if (edicion == 1) {
                        if (status.equals("R") || status.equals("K") || status.equals("X")) {
                            strResultado += " <input id='edtRequerimiento' type='button' value='Editar' onclick='editarTransferencia(" + identificador + ")'/>";
                            strResultado += " <input type='hidden' value='" + tipoGasto + "' id='selTipoGasto' />";
                        }
                    } else {
                        strResultado += " <input id='nvoRequerimiento' type='button' value='Guardar' onclick='saveTransferencia()'/>";
                    }
                }
                strResultado += " <input type='hidden' value='" + ramoId + "' id='popRamo' />";
                strResultado += " <input type='hidden' value='" + programaId + "' id='popPrg' />";
                strResultado += " <input type='hidden' value='" + proy + "' id='popProy' />";
                strResultado += " <input type='hidden' value='" + tipoProy + "' id='popTipoProy' />";
                strResultado += " <input type='hidden' value='" + metaId + "' id='popMeta' />";
                strResultado += " <input type='hidden' value='" + accion + "' id='popAccion' />";
                strResultado += " <input type='hidden' value='" + partida + "' id='popPartida' />";
                strResultado += " <input type='hidden' value='" + fuente + "' id='popFuente' />";
                strResultado += " <input type='hidden' value='" + relLaboral + "' id='popRelLaboral' />";
                strResultado += " <input type='hidden' value='" + transAccionReq.getImporte() + "' id='popImporte' />";
                strResultado += " <input type='hidden' value='" + transAccionReq.getDisponible() + "' id='popDisponible' />";
                strResultado += " <input type='button' value='Cerrar' onclick=\"$('#PopUpZone').html(''); fadeInPopUp('PopUpZone');\"/> ";
                strResultado += "</div> </center> </form>";
                strResultado += "<script type='text/javascript'>";
                strResultado += "              $('.capt-mes').keydown(function (e) {";
                strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
                strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
                strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
                strResultado += "                            return;";
                strResultado += "                  }";
                strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
                strResultado += " e.preventDefault();";
                strResultado += "                   }";
                strResultado += "                });";
                strResultado += "              $('#inpTxtCantUnit').keydown(function (e) {";
                strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
                strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
                strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
                strResultado += "                            return;";
                strResultado += "                  }";
                strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
                strResultado += " e.preventDefault();";
                strResultado += "                   }";
                strResultado += "                });";
                strResultado += " $('#txtAreaJust').keypress(function(event) {   ";
                strResultado += "     if (event.keyCode == 13) {   ";
                strResultado += "         event.preventDefault();   ";
                strResultado += "     }  ";
                strResultado += " });   ";

                strResultado += "</script>";
            } else {
                strResultado = "2|No hay disponible para otro requerimiento";
            }
        } else {
            strResultado = "-2";
            //alert("No es posible realizar una ampliaci\u00f3n debido a que la meta o acci\u00f3n seleccinadas fueron deshabilitadas");
        }

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (transferenciaBean != null) {
            transferenciaBean.resultSQLDesconecta();
        }
        if (parametrosBean != null) {
            parametrosBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

<script>
    $('.no-enter').bind('keypress', function(e){
        if(e.keyCode == 13)
        {
           return false;
        }
     });     
</script>
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
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.Articulo"%>
<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
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
    Meta metaObj = new Meta();
    Partida objPartida = new Partida();
    AmpliacionReduccionBean ampRedBean = null;
    Requerimiento requerimiento = new Requerimiento();
    AmpliacionReduccionAccionReq movtoAccionReq = new AmpliacionReduccionAccionReq();
    List<AmpliacionReduccionAccionReq> ampRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
    AmpliacionReduccionAccion ampRedAccion = new AmpliacionReduccionAccion();
    AmpliacionReduccionMeta ampRedMeta = new AmpliacionReduccionMeta();
    List<AmpliacionReduccionMeta> ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
    List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
    List<Articulo> articuloList = new ArrayList<Articulo>();
    MovimientosAmpliacionReduccion ampReduccion;
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    Double importe = 0.0;
    String tipoDependencia = new String();
    String strResultado = new String();
    String ramoId = new String();
    String ramoDescr = new String();
    String programaId = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String status = new String();
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
    String tipoGasto = new String();
    String selected = new String();
    String tipoProy = new String();
    String fechaConta = new String();
    String disableJustig = new String();

    boolean fechaCont = false;
    boolean validaTrimestre = false;
    boolean banderaEstimacion = true;
    double sumEstimacion = 0.0;
    int meses[] = new int[3];
    int justific = 0;
    int edicion = 0;
    int year = 0;
    int metaId = 0;
    int accion = 0;
    int proy = 0;
    int identificador = -1;
    int mesTemp = 0;
    int monthAct = 0;
    int mesConta = 0;
    int opcion = 0;
    int consulta = 0;

    try {
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
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
        if (request.getParameter("importe") != null && !request.getParameter("importe").equals("")) {
            importe = new Double(request.getParameter("importe"));
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
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
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
                disabled = "disabled";
            }
        }

        if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
            ampReduccion = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampRedAccionReqList = ampReduccion.getAmpReducAccionReqList();
            if (identificador != -1) {
                for (AmpliacionReduccionAccionReq movtoAccionReqtemp : ampRedAccionReqList) {
                    if (movtoAccionReqtemp.getIdentidicador() == identificador) {
                        movtoAccionReq = movtoAccionReqtemp;
                    }
                }
            }
            if (edicion > 0) {
                ampRedMetaList = ampReduccion.getAmpReducMetaList();
                ampRedAccionList = ampReduccion.getAmpReducAccionList();
                for (AmpliacionReduccionAccion ampRedAccionTemp : ampRedAccionList) {
                    if (ampRedAccionTemp.getMovOficioAccion().getRamoId().equals(ramoId)
                            && ampRedAccionTemp.getMovOficioAccion().getMetaId() == metaId
                            && ampRedAccionTemp.getMovOficioAccion().getAccionId() == accion) {
                        ampRedAccion = ampRedAccionTemp;
                    }
                }
                for (AmpliacionReduccionMeta ampRedMetaTemp : ampRedMetaList) {
                    if (ampRedMetaTemp.getMovOficioMeta().getRamoId().equals(ramoId)
                            && ampRedMetaTemp.getMovOficioMeta().getMetaId() == metaId) {
                        ampRedMeta = ampRedMetaTemp;
                    }
                }
            }
            for (AmpliacionReduccionMeta meta : ampReduccion.getAmpReducMetaList()) {
                if (meta.getMovOficioMeta().getMetaId() == metaId) {
                    for (MovOficioEstimacion estimacion : meta.getMovOficioEstimacionList()) {
                        sumEstimacion += estimacion.getValor();
                    }
                    if (sumEstimacion == 0) {
                        banderaEstimacion = false;
                    }
                }
            }
            sumEstimacion = 0.0;
            for (AmpliacionReduccionAccion accionT : ampReduccion.getAmpReducAccionList()) {
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
        }
        if (banderaEstimacion) {
            ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
            ampRedBean.setStrServer(request.getHeader("host"));
            ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
            ampRedBean.resultSQLConecta(tipoDependencia);
            validaTrimestre = ampRedBean.getResultSqlGetparametroTrimestre();
            monthAct = mesTemp;
            meses = ampRedBean.getMesesTrimestreByMes(monthAct, validaTrimestre);
            if (!validaTrimestre) {
                if (meses.length > 0) {
                    monthAct = meses[0];
                } else {
                    monthAct = 1;
                }
            }
            metaObj = ampRedBean.getResultGetMetaById(ramoId, programaId, movtoAccionReq.getProy(), metaId, year, movtoAccionReq.getTipoProy());
            if (metaId > 0) {
                if (ampRedMeta.getMovOficioMeta() == null) {
                    ampRedMeta.setMovOficioMeta(new MovimientoOficioMeta());
                    ampRedMeta.getMovOficioMeta().setMetaId(metaObj.getMetaId());
                    ampRedMeta.getMovOficioMeta().setMetaDescr(metaObj.getMeta());
                    ampRedMeta.getMovOficioMeta().setRamoId(metaObj.getRamo());
                    ampRedMeta.getMovOficioMeta().setPrgId(metaObj.getPrograma());
                    ampRedMeta.getMovOficioMeta().setProyId(metaObj.getProyecto());
                    ampRedMeta.getMovOficioMeta().setTipoProy(metaObj.getTipoProyecto());
                    ampRedMeta.getMovOficioMeta().setFuncion(metaObj.getFuncion());
                    ampRedMeta.getMovOficioMeta().setFinalidad(metaObj.getFinalidad());
                    ampRedMeta.getMovOficioMeta().setSubfuncion(metaObj.getSubfuncion());
                    ampRedMeta.getMovOficioMeta().setYear(year);
                    ampRedMeta.getMovOficioMeta().setCalculoId(metaObj.getCalculo());
                    ampRedMeta.getMovOficioMeta().setClaveMedida(metaObj.getClaveMedida());
                    ampRedMeta.getMovOficioMeta().setCompromisoId(metaObj.getCompromiso());
                    ampRedMeta.getMovOficioMeta().setLineaPedId(metaObj.getLineaPED());
                    ampRedMeta.getMovOficioMeta().setLineaSectorialId(metaObj.getLineaSectorial());
                    ampRedMeta.getMovOficioMeta().setPonderacionId(String.valueOf(metaObj.getPonderado()));
                    ampRedMeta.getMovOficioMeta().setCriterioTransversalidad(metaObj.getCriterio());
                    ampRedMeta.getMovOficioMeta().setClasificacionFuncionalId(metaObj.getFuncion()
                            + "." + metaObj.getFinalidad() + "." + metaObj.getSubfuncion());
                }
            }
            if (fechaCont) {
                mesConta = Integer.parseInt(fechaConta.split("/")[1]);
            }
            if (edicion > 0) {

                if (accion < 0) {
                    ramoId = ramoId;
                    ramoDescr = ampRedBean.getResultSQLGetRamoDescr(year, ramoId);
                    programaId = programaId;
                    programaDescr = ampRedBean.getResultSQLGetProgramaDescr(year, programaId);
                    proyectoDescr = ampRedBean.getResultSQLGetProyectoDescr(year,
                            ampRedMeta.getMovOficioMeta().getTipoProy(),
                            ampRedMeta.getMovOficioMeta().getProyId());
                    metaId = ampRedMeta.getMovOficioMeta().getMetaId();
                    metaDescr = ampRedMeta.getMovOficioMeta().getMetaId() + "-" + ampRedMeta.getMovOficioMeta().getMetaDescr();
                    accion = ampRedAccion.getMovOficioAccion().getAccionId();
                    accionDescr = ampRedAccion.getMovOficioAccion().getAccionId() + "-" + ampRedAccion.getMovOficioAccion().getAccionDescr();
                    partida = movtoAccionReq.getPartida();
                    partidaDescr = ampRedBean.getResultSQLGetPartidaDescr(year, movtoAccionReq.getPartida());
                    relLaboral = movtoAccionReq.getRelLaboral();
                    relLaboralDescr = ampRedBean.getResultSQLGetRelLaboralDescr(year, movtoAccionReq.getRelLaboral());
                    fuente = movtoAccionReq.getFuente() + "." + movtoAccionReq.getFondo() + "." + movtoAccionReq.getRecurso();
                    fuenteDescr = ampRedBean.getResultSQLGetFuenteFinDescr(year, movtoAccionReq.getFuente(),
                            movtoAccionReq.getFondo(), movtoAccionReq.getRecurso());
                } else {
                    infoReq = ampRedBean.getRequerimientoByDatosMovtos(year, ramoId, programaId,
                            metaId, accion, movtoAccionReq.getPartida(), movtoAccionReq.getRelLaboral(),
                            movtoAccionReq.getFuente(), movtoAccionReq.getFondo(), movtoAccionReq.getRecurso());
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

            justific = ampRedBean.isPartidaJustific(year, partida);
            tipoGasto = ampRedBean.getTipoGastoByPartida(partida, year);
            objPartida = ampRedBean.getResultSQLGetPartidaByIdPartidaYear(partida, year);

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
                    requerimiento.setReq(movtoAccionReq.getMovOficioAccionReq().getReqDescr());
                    requerimiento.setReqId(movtoAccionReq.getMovOficioAccionReq().getRequerimiento());
                    requerimiento.setJustif(movtoAccionReq.getMovOficioAccionReq().getJustificacion());
                    requerimiento.setCantEne(movtoAccionReq.getMovOficioAccionReq().getEne());
                    requerimiento.setCantFeb(movtoAccionReq.getMovOficioAccionReq().getFeb());
                    requerimiento.setCantMar(movtoAccionReq.getMovOficioAccionReq().getMar());
                    requerimiento.setCantAbr(movtoAccionReq.getMovOficioAccionReq().getAbr());
                    requerimiento.setCantMay(movtoAccionReq.getMovOficioAccionReq().getMay());
                    requerimiento.setCantJun(movtoAccionReq.getMovOficioAccionReq().getJun());
                    requerimiento.setCantJul(movtoAccionReq.getMovOficioAccionReq().getJul());
                    requerimiento.setCantAgo(movtoAccionReq.getMovOficioAccionReq().getAgo());
                    requerimiento.setCantSep(movtoAccionReq.getMovOficioAccionReq().getSep());
                    requerimiento.setCantOct(movtoAccionReq.getMovOficioAccionReq().getOct());
                    requerimiento.setCantNov(movtoAccionReq.getMovOficioAccionReq().getNov());
                    requerimiento.setCantDic(movtoAccionReq.getMovOficioAccionReq().getDic());
                    requerimiento.setCantidad(movtoAccionReq.getMovOficioAccionReq().getCantidad());
                    requerimiento.setCostoAnual(movtoAccionReq.getMovOficioAccionReq().getCostoAnual());
                    requerimiento.setCostoUnitario(movtoAccionReq.getMovOficioAccionReq().getCostoUnitario());
                    requerimiento.setArticulo(Integer.parseInt(movtoAccionReq.getMovOficioAccionReq().getArticulo()));
                    justific = ampRedBean.isPartidaJustific(year, movtoAccionReq.getPartida());
                    tipoGasto = ampRedBean.getTipoGastoByPartida(movtoAccionReq.getPartida(), year);
                    //editDis = "disabled";

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
            if (edicion != 1) {
                strResultado += "<td> <div> Importe: </div>";
                strResultado += "<input type='text' id='popImporte' value='" + numberF.format(importe) + "' disabled/> </td>";
            } else {
                strResultado += "<td> <div> Importe: </div>";
                strResultado += "<input type='text' id='popImporte' value='" + numberF.format(movtoAccionReq.getImporte()) + "' />  </td>";
            }
            strResultado += "<form id='frmReq'>";
            if (requerimiento.getJustif() == null) {
                requerimiento.setJustif("");
            }
            strResultado += " <tr> <td id='colPartida' align='left' colspan='3'> <div> Requerimiento </div> ";
            if (!ampRedBean.isArticuloPartida(year, partida)) {
                articuloList = ampRedBean.getArticuloPartida(year, partida);
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
                strResultado += "<textArea id='txtAreaPart' class='no-enter' name='limitedtextarea' tabindex='0'"
                        + " maxlength='300' " + disabled + " >" + requerimiento.getReq() + "</textArea>";
            }
            strResultado += "</td> </tr>";
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

            strResultado += "</table> <br/>";
            strResultado += "<center> <div>";
            if (consulta != 1) {
                if (edicion == 1) {
                    if ((status.equals("X") || status.equals("R") || status.equals("K"))) {
                        strResultado += " <input id='edtRequerimiento' type='button' value='Editar' onclick='editarAmpliacion(" + identificador + ")'/>";
                    }
                    strResultado += " <input type='hidden' value='" + tipoGasto + "' id='selTipoGasto' />";
                } else {
                    strResultado += " <input id='nvoRequerimiento' type='button' value='Guardar' onclick='SaveAmpliacion()'/>";
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
            strResultado += " <input type='hidden' value='" + movtoAccionReq.getImporte() + "' id='popImporte' />";
            strResultado += " <input type='hidden' value='" + movtoAccionReq.getDisponible() + "' id='popDisponible' />";
            strResultado += " <input type='button' value='Cancelar' onclick=\"$('#PopUpZone').html(''); fadeInPopUp('PopUpZone');\"/> </div> </center> </form>";
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
            strResultado = "-2";
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
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
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
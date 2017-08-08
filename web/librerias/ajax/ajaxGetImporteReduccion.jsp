<%-- 
    Document   : ajaxGetImporteReduccion
    Created on : Jan 28, 2016, 11:17:45 AM
    Author     : ugarcia
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    AmpliacionReduccionBean ampRedBean = null;
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    Meta metaObj = new Meta();
    AmpliacionReduccionMeta ampRedMeta = new AmpliacionReduccionMeta();
    AmpliacionReduccionAccion ampRedAccion = new AmpliacionReduccionAccion();
    List<AmpliacionReduccionAccionReq> ampRedAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
    List<AmpliacionReduccionMeta> ampRedMetaList = new ArrayList<AmpliacionReduccionMeta>();
    List<AmpliacionReduccionAccion> ampRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
    MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
    AmpliacionReduccionAccionReq ampRedAccionReq = new AmpliacionReduccionAccionReq();
    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String ramoDescr = new String();
    String programa = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String estatus = new String();
    String metaDescr = new String();
    String accionDescr = new String();
    String fechaConta = new String();
    String partida = new String();
    String partidaDescr = new String();
    String relLaboral = new String();
    String relLaboralDescr = new String();
    String fuente = new String();
    String fuenteDescr = new String();
    String infoReq[] = new String[19];
    String tipoGasto = new String();
    int identificador = 0;
    int meta = 0;
    int accion = 0;
    int year = 0;
    int proy = 0;
    int folio = 0;
    int edicion = 0;
    int mesTemp = 0;
    int monthAct = 0;
    int mesConta = 0;
    int justific = 0;
    boolean fechaCont = false;

    try {

        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("ramoDescr") != null && !request.getParameter("ramoDescr").equals("")) {
            ramoDescr = request.getParameter("ramoDescr");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programa = request.getParameter("programa");
        }
        if (request.getParameter("programaDescr") != null && !request.getParameter("programaDescr").equals("")) {
            programaDescr = request.getParameter("programaDescr");
        }
        if (request.getParameter("meta") != null && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if (request.getParameter("accion") != null && !request.getParameter("accion").equals("")) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        if (request.getParameter("accionDescr") != null && !request.getParameter("accionDescr").equals("")) {
            accionDescr = request.getParameter("accionDescr");
        }
        if (Utilerias.existeParametro("estatus", request)) {
            estatus = request.getParameter("estatus");
        }
        if (session.getAttribute("tipoDependencia") != null) {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null) {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("identificador", request)) {
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
        if (request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")) {
            proy = Integer.parseInt(request.getParameter("proyecto"));
        }
        if (request.getParameter("proyectoDescr") != null && !request.getParameter("proyectoDescr").equals("")) {
            proyectoDescr = request.getParameter("proyectoDescr");
        }
        if (request.getParameter("partida") != null && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if (request.getParameter("partidaDescr") != null && !request.getParameter("partidaDescr").equals("")) {
            partidaDescr = request.getParameter("partidaDescr");
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
        if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
            movimiento = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampRedAccionReqList = movimiento.getAmpReducAccionReqList();
            if (identificador != -1) {
                for (AmpliacionReduccionAccionReq movtoAccionReqtemp : ampRedAccionReqList) {
                    if (movtoAccionReqtemp.getIdentidicador() == identificador) {
                        ampRedAccionReq = movtoAccionReqtemp;
                    }
                }
            }
            if (edicion > 0) {
                ampRedMetaList = movimiento.getAmpReducMetaList();
                ampRedAccionList = movimiento.getAmpReducAccionList();
                for (AmpliacionReduccionAccion ampRedAccionTemp : ampRedAccionList) {
                    if (ampRedAccionTemp.getMovOficioAccion().getRamoId().equals(ramo) && ampRedAccionTemp.getMovOficioAccion().getMetaId() == meta && ampRedAccionTemp.getMovOficioAccion().getAccionId() == accion) {
                        ampRedAccion = ampRedAccionTemp;
                    }
                }
                for (AmpliacionReduccionMeta ampRedMetaTemp : ampRedMetaList) {
                    if (ampRedMetaTemp.getMovOficioMeta().getRamoId().equals(ramo) && ampRedMetaTemp.getMovOficioMeta().getMetaId() == meta) {
                        ampRedMeta = ampRedMetaTemp;
                    }
                }
            }
        }

        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);

        if (ampRedBean.getResultSqlGetparametroTrimestre()) {
            monthAct = mesTemp;
        } else {
            monthAct = 1;
        }

        metaObj = ampRedBean.getResultGetMetaById(ramo, programa, ampRedAccionReq.getProy(), meta, year, ampRedAccionReq.getTipoProy());

        if (meta > 0) {
            if (ampRedMeta.getMovOficioMeta() == null) {
                ampRedMeta.setMovOficioMeta(new MovimientoOficioMeta());
                ampRedMeta.getMovOficioMeta().setMetaId(metaObj.getMetaId());
                ampRedMeta.getMovOficioMeta().setMetaDescr(metaDescr);
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
                ampRedMeta.getMovOficioMeta().setClasificacionFuncionalId(metaObj.getFuncion() + "." + metaObj.getFinalidad() + "." + metaObj.getSubfuncion());
            }
        }

        if (fechaCont) {
            fechaConta = dt.format(ampRedBean.getResultSQLGetFechaAplicacionGasto(year));
            mesConta = Integer.parseInt(fechaConta.split("/")[1]);
        }

        if (edicion > 0) {

            if (accion < 0) {
                ramo = ramo;
                ramoDescr = ampRedBean.getResultSQLGetRamoDescr(year, ramo);
                programa = programa;
                programaDescr = ampRedBean.getResultSQLGetProgramaDescr(year, programa);
                proyectoDescr = ampRedBean.getResultSQLGetProyectoDescr(year, ampRedMeta.getMovOficioMeta().getTipoProy(), ampRedMeta.getMovOficioMeta().getProyId());
                meta = ampRedMeta.getMovOficioMeta().getMetaId();
                metaDescr = ampRedMeta.getMovOficioMeta().getMetaId() + "-" + ampRedMeta.getMovOficioMeta().getMetaDescr();
                accion = ampRedAccion.getMovOficioAccion().getAccionId();
                accionDescr = ampRedAccion.getMovOficioAccion().getAccionId() + "-" + ampRedAccion.getMovOficioAccion().getAccionDescr();
                partida = ampRedAccionReq.getPartida();
                partidaDescr = ampRedBean.getResultSQLGetPartidaDescr(year, ampRedAccionReq.getPartida());
                relLaboral = ampRedAccionReq.getRelLaboral();
                relLaboralDescr = ampRedBean.getResultSQLGetRelLaboralDescr(year, ampRedAccionReq.getRelLaboral());
                fuente = ampRedAccionReq.getFuente() + "." + ampRedAccionReq.getFondo() + "." + ampRedAccionReq.getRecurso();
                fuenteDescr = ampRedBean.getResultSQLGetFuenteFinDescr(year, ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso());
            } else {
                infoReq = ampRedBean.getRequerimientoByDatosMovtos(year, ramo, programa, meta, accion, ampRedAccionReq.getPartida(), ampRedAccionReq.getRelLaboral(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso());
                ramo = infoReq[0];
                ramoDescr = infoReq[1];
                programa = infoReq[2];
                programaDescr = infoReq[3];
                proyectoDescr = infoReq[6];
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
        }

        justific = ampRedBean.isPartidaJustific(year, partida);
        tipoGasto = ampRedBean.getTipoGastoByPartida(partida, year);

        ampRedAccionReq.setDisponible(ampRedBean.getDisponible(year, ampRedAccionReq.getRamo(), ampRedAccionReq.getPrograma(), ampRedAccionReq.getTipoProy(), ampRedAccionReq.getProy(), ampRedAccionReq.getMeta(), ampRedAccionReq.getAccion(), ampRedAccionReq.getPartida(), ampRedAccionReq.getFuente(), ampRedAccionReq.getFondo(), ampRedAccionReq.getRecurso(), ampRedAccionReq.getRelLaboral(), ampRedBean.getMonthTramite(folio)));
        //strResultado += "<div id='importeReduccion'>";
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
        strResultado += "<center>";
        strResultado += "<div>";
        strResultado += "		<table>  ";
        strResultado += "				<tr> ";
        if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {
            strResultado += "					<td> ";
            strResultado += "<label> Disponible: </label>";
            strResultado += "</br>";
            strResultado += "<input id='inpTxtDisponible' value='" + numberF.format(ampRedAccionReq.getDisponible()) + "' disabled/>";
            strResultado += "					</td>  ";
            strResultado += "					<td>  ";
            strResultado += "<label> Importe para reducir </label>";
            strResultado += "</br>";
            strResultado += "<input id='inpTxtImporte' value='" + numberF.format(ampRedAccionReq.getImporte()) + "' ";
            strResultado += "					</td>  ";
        } else {
            strResultado += "					<td> ";
            strResultado += "<label> Disponible: </label>";
            strResultado += "</br>";
            strResultado += "<input id='inpTxtDisponible' disabled value='" + numberF.format(ampRedAccionReq.getDisponible()) + "' disabled/>";
                        strResultado += "					</td>  ";
            strResultado += "					<td>  ";
            strResultado += "<label> Importe para reducir </label>";
            strResultado += "</br>";
            strResultado += "<input id='inpTxtImporte' disabled value='" + numberF.format(ampRedAccionReq.getImporte()) + "' " + "onblur='agregarFormato(\"inpTxtImporte\");calculaCosto()' onkeyup='validaMascara(\"inpTxtImporte\")'/>";
        }
        strResultado += "				</tr> ";
        strResultado += "		</table>  ";
        strResultado += "</div>";        
        strResultado += "</br>";
        
        if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {
            strResultado += "<input type='button' value='Editar' onclick='editReduccion(\"" + identificador + "\")'/>";
            strResultado += "<input type='button' value='Cancelar' onclick='$(\"#PopUpZone\").fadeOut();'/>";
        } else {
            strResultado += "<input type='button' value='Cerrar' onclick='$(\"#PopUpZone\").fadeOut();'/>";
        }
        strResultado += "</center>";
        strResultado += "</div>";
        strResultado += " <input type='hidden' value='" + ramo + "' id='popRamo' />";
        strResultado += " <input type='hidden' value='" + programa + "' id='popPrg' />";
        strResultado += " <input type='hidden' value='" + meta + "' id='popMeta' />";
        strResultado += " <input type='hidden' value='" + accion + "' id='popAccion' />";
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
        out.print(strResultado);
    }
%>
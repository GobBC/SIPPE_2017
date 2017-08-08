<%-- 
    Document   : ajaxGetInfoRecalendarizacionAccion
    Created on : Apr 24, 2015, 11:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="java.lang.Object"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="gob.gbc.entidades.TipoAccion"%>
<%@page import="gob.gbc.entidades.TipoCalculo"%>
<%@page import="gob.gbc.entidades.Localidad"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="gob.gbc.entidades.Municipio"%>
<%@page import="gob.gbc.entidades.GrupoPoblacional"%>
<%@page import="gob.gbc.entidades.UnidadMedida"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    request.setCharacterEncoding("UTF-8");

    AccionBean accionBean = null;
    Accion accionAct = new Accion();
    ParametrosBean parametrosBean = null;
    RecalendarizacionBean recalBean = null;
    Evaluacion evaluacion = new Evaluacion();
    Parametros objParametros = new Parametros();
    MovimientosRecalendarizacion recalendarizacion = new MovimientosRecalendarizacion();

    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<RecalendarizacionAccion> movtoAccionesList = new ArrayList<RecalendarizacionAccion>();
    List<MovOficioAccionEstimacion> movtoEstimacionList = new ArrayList<MovOficioAccionEstimacion>();

    Date date;
    DateFormat formatMonth = new SimpleDateFormat("MM");
    DateFormat formatYear = new SimpleDateFormat("yyyy");
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    String inputAllDisable = "";
    String tipoC = new String();
    String ramoId = new String();
    String status = new String();
    String inputMonthDisable = "";
    String tipoProy = new String();
    String ramoDescr = new String();
    String metaDescr = new String();
    String programaId = new String();
    String tipoCMovOf = new String();
    String accionDescr = new String();
    String strResultado = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String tipoDependencia = new String();

    int yearAct;
    int cont = 1;
    int year = 0;
    int folio = 0;
    int monthAct;
    int metaId = 0;
    int accionId = 0;
    int proyectoId = 0;
    int identificador = -1;
    int objIdentificado = 0;
    int meses[] = new int[3];
    int limitMonthDisable = 1;

    double calOriginal = 0.0;
    double calPropuesta = 0.0;
    double sumEstimacion = 0.0;
    double sumRecalendarizacion = 0.0;

    boolean autorizacion = false;
    boolean validaTrimestre = false;

    try {

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = request.getParameter("ramoId");
        }
        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }
        if (request.getParameter("autorizacion") != null && !request.getParameter("autorizacion").equals("")) {
            autorizacion = Boolean.valueOf(request.getParameter("autorizacion"));
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            status = request.getParameter("estatus");
            if ((!status.equalsIgnoreCase("X")) && (!status.equalsIgnoreCase("R")) && ((status.equalsIgnoreCase("V")) && !autorizacion)) {
                inputAllDisable = "disabled=''";
            }
        }
        if (request.getParameter("folio") != null && !request.getParameter("folio").equals("")) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != "") {
            recalendarizacion = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            movtoAccionesList = recalendarizacion.getMovAccionEstimacionList();
            if (identificador != -1) {
                for (RecalendarizacionAccion movtoAccionListTemp : movtoAccionesList) {
                    if (movtoAccionListTemp.getIdentificado() == identificador) {
                        movtoEstimacionList = movtoAccionListTemp.getMovEstimacionList();
                    }
                }
            }
        }

        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);

        parametrosBean = new ParametrosBean(tipoDependencia);
        parametrosBean.setStrServer((request.getHeader("Host")));
        parametrosBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        parametrosBean.resultSQLConecta(tipoDependencia);

        objParametros = parametrosBean.getParametros();

        accionAct = accionBean.getAccionByYearRamoMetaAccion(year, ramoId, metaId, accionId);

        ramoDescr = ramoId + " - " + accionAct.getRamoDescr();
        programaId = accionAct.getPrg();
        programaDescr = programaId + " - " + accionAct.getProgramaDescr();
        proyectoId = accionAct.getProyId();
        tipoProy = accionAct.getTipoproy();
        proyectoDescr = accionAct.getTipoproy() + accionAct.getProyId() + " - " + accionAct.getProyDescr();
        metaDescr = metaId + " - " + accionAct.getMetaDescr();
        accionDescr = accionId + " - " + accionAct.getAccion();
        tipoC = accionBean.getTipoCalculo(year, ramoId, metaId, accionId);
        if (!status.equalsIgnoreCase("A")) {
            estimacionList = accionBean.getEstimacionByAccion(year, ramoId, metaId, accionId);
        } else {
            estimacionList = accionBean.getHistEstimacion(year, ramoId, metaId, accionId, folio);
        }
        evaluacion = accionBean.getResultSQLGetEvaluacionMeta(year);

        strResultado += "<div id='estimacionMetaRC' style=''> ";
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
        strResultado += "					<td>Accion:</td>  ";
        strResultado += "					<td> " + accionDescr + " </td>  ";
        strResultado += "				</tr> ";
        strResultado += "			</tbody> ";
        strResultado += "		</table>  ";
        strResultado += "		<br> ";
        strResultado += "<div> ";
        strResultado += "			<fieldset id='fieldsetMetaRC'> ";
        strResultado += "				<legend><strong>Información Original</strong></legend> ";
        strResultado += "		<div class='calenVistaRC'> ";

        if (estimacionList.size() > 0) {
            for (Estimacion estimacion : estimacionList) {
                sumEstimacion += estimacion.getValor();
                strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                cont++;
            }
            sumEstimacion = accionBean.getValorCalculado(estimacionList, tipoC);
            calOriginal = accionBean.getValorCalculado(estimacionList, tipoC);
        } else {
            for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='' disabled/></div>";
                cont++;
            }
        }

        strResultado += "		</div> ";

        strResultado += "<div class='muestra-cal' style='width: 100% !important;' ><label>Calendarizaci&oacute;n original: </label> <input  id='calOriginal' style='text-align:right; font-size: 12px' disabled value='" + numberF.format(calOriginal) + "'></input></div> ";

        strResultado += "			</fieldset> ";
        strResultado += "</div> ";
        strResultado += "		<br>  ";
        strResultado += "		<div> ";
        strResultado += "				<fieldset id='fieldsetMetaRC'> ";
        strResultado += "					<legend><strong>Reprogramación</strong></legend> ";
        strResultado += "			<div class='calenVistaRC'> ";

        if (objParametros.getValidaTodosTrimestre().equals("S")) {

            cont = 1;
            tipoC = accionAct.getCalculo();

            if (movtoEstimacionList.size() > 0) {
                for (MovOficioAccionEstimacion reprogramacion : movtoEstimacionList) {
                    inputMonthDisable = "";
                    if (limitMonthDisable >= cont) {
                        inputMonthDisable = "";
                    }
                    sumRecalendarizacion += reprogramacion.getValor();
                    strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(reprogramacion.getValor()) + "' maxlength='14' type='text'  onBlur=' operarRecalendarizacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(reprogramacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'     " + inputAllDisable + "    /></div>";
                    cont++;
                }
                tipoCMovOf = accionAct.getCalculo();
                calPropuesta = accionBean.getValorCalculadoMovOficio(movtoEstimacionList, tipoCMovOf);
            } else {
                if (estimacionList.size() > 0) {
                    for (Estimacion estimacion : estimacionList) {
                        inputMonthDisable = "";
                        if (limitMonthDisable >= cont) {
                            inputMonthDisable = "";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input id='estimacion" + (cont - 1) + "' class='estimacion'  maxlength='14' type='text'  value='" + numberF.format(estimacion.getValor()) + "'   onBlur=' operarRecalendarizacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); ' onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'     " + inputAllDisable + "     /></div>";
                        cont++;
                    }
                    sumRecalendarizacion = sumEstimacion;
                    calPropuesta = sumEstimacion;
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        inputMonthDisable = "";
                        if (limitMonthDisable > cont) {
                            inputMonthDisable = "";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur=' operarRecalendarizacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'     " + inputAllDisable + "     /></div>";
                        cont++;
                    }
                }
            }

        } else {

            cont = 1;
            date = accionBean.getResultSQLgetServerDate();
            yearAct = Integer.parseInt(formatYear.format(date.getTime()));
            validaTrimestre = accionBean.getResultSqlGetparametroTrimestre();
            monthAct = Integer.parseInt(formatMonth.format(date.getTime()));
            meses = recalBean.getMesesTrimestreByMes(monthAct, validaTrimestre);
            if (!validaTrimestre) {
                if (meses.length > 0) {
                    monthAct = meses[0];
                } else {
                    monthAct = 1;
                }
            }
            tipoC = accionAct.getCalculo();

            if (movtoEstimacionList.size() > 0) {
                for (MovOficioAccionEstimacion reprogramacion : movtoEstimacionList) {
                    inputMonthDisable = "";
                    if (limitMonthDisable >= cont) {
                        inputMonthDisable = "";
                    }
                    sumRecalendarizacion += reprogramacion.getValor();
                    strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(reprogramacion.getValor()) + "' maxlength='14' type='text'  onBlur=' operarRecalendarizacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(reprogramacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'  " + Utileria.getDisabledMonth(cont, monthAct, meses) + "    " + inputAllDisable + "    /></div>";
                    cont++;
                }
                tipoCMovOf = accionAct.getCalculo();
                calPropuesta = accionBean.getValorCalculadoMovOficio(movtoEstimacionList, tipoCMovOf);
            } else {
                if (estimacionList.size() > 0) {
                    for (Estimacion estimacion : estimacionList) {
                        inputMonthDisable = "";
                        if (limitMonthDisable >= cont) {
                            inputMonthDisable = "";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input id='estimacion" + (cont - 1) + "' class='estimacion'  maxlength='14' type='text'  value='" + numberF.format(estimacion.getValor()) + "'   onBlur=' operarRecalendarizacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); ' onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")' " + Utileria.getDisabledMonth(cont, monthAct, meses) + "     " + inputAllDisable + "     /></div>";
                        cont++;
                    }
                    sumRecalendarizacion = sumEstimacion;
                    calPropuesta = sumEstimacion;
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        inputMonthDisable = "";
                        if (limitMonthDisable > cont) {
                            inputMonthDisable = "";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur=' operarRecalendarizacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'  " + Utileria.getDisabledMonth(cont, monthAct, meses) + "    " + inputAllDisable + "     /></div>";
                        cont++;
                    }
                }
            }

        }

        strResultado += "			</div> ";

        strResultado += "<div class='muestra-cal' style='width: 100% !important; visibility:hidden; ' ><label>Propuesta de calendarizaci&oacute;n: </label> <input  id='calPropuesta' style='text-align:right; font-size: 12px' disabled value='" + numberF.format(calPropuesta) + "'></input></div> ";

        strResultado += "				</fieldset> ";
        strResultado += "			<br>   ";
        strResultado += "			<script type='text/javascript'> ";
        strResultado += "				$('.estimacion').keydown(function (e) { ";
        strResultado += "					if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) { ";
        strResultado += "						return; ";
        strResultado += "					}  ";
        strResultado += "					if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {  ";
        strResultado += "						e.preventDefault();  ";
        strResultado += "					}                 ";
        strResultado += "				});              ";
        strResultado += "				$('#inpTxtCantUnit').keydown(function (e) { ";
        strResultado += "					if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) {  ";
        strResultado += "						return;  ";
        strResultado += "					} ";
        strResultado += "					if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {  ";
        strResultado += "						e.preventDefault();          ";
        strResultado += "					}          ";
        strResultado += "				}); ";
        strResultado += "			</script> ";
        strResultado += "		</div> ";
        strResultado += "	</div> ";

        strResultado += "	<br> ";
        strResultado += "<center>";
        strResultado += "   <label> Cantidad </label> &nbsp;&nbsp;";
        strResultado += "   <br/>";
        strResultado += "   <input type='text' id='inTxtTotalEst' value='" + calPropuesta + "' " + "disabled style='width: 250px;text-align: right;'/> ";
        strResultado += "</center>";

        strResultado += "	<br> ";
        strResultado += "    <center> ";
        if (inputAllDisable.equals("")) {
            strResultado += "        <input type='button' value='Guardar' onclick='actualizarRecalendarizacionAccion()'      /> ";
            strResultado += "        <input type='button' value='Cancelar' onclick=\"fadeOutPopUp('PopUpZone')\"     /> ";
        } else {
            strResultado += "        <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('PopUpZone')\"     /> ";
        }
        strResultado += "        <input type='hidden' id='identificador' value='" + identificador + "' /> ";
        strResultado += "        <input type='hidden' id='ramoId' name='ramoId' value='" + ramoId + "' /> ";
        strResultado += "        <input type='hidden' id='metaId' name='metaId' value='" + metaId + "' /> ";
        strResultado += "        <input type='hidden' id='accionId' name='metaId' value='" + accionId + "' /> ";
        strResultado += "        <input type='hidden' id='sumEstimacion' value='" + sumEstimacion + "' /> ";
        strResultado += "        <input type='hidden' id='sumRecalendarizacion' value='" + sumRecalendarizacion + "' /> ";
        strResultado += "        <input type='hidden' id='selCalculoRP' value='" + tipoC + "' /> ";
        strResultado += "    </center> ";
        strResultado += "</div> ";

    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        if (recalBean != null) {
            recalBean.resultSQLDesconecta();
        }
        if (parametrosBean != null) {
            parametrosBean.resultSQLDesconecta();
        }        
        out.print(strResultado);
    }
%>

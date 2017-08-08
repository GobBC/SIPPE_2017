<%-- 
    Document   : ajaxGetInfoReprogramacionRecalendarizacionMeta
    Created on : Dec 21, 2015, 08:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="gob.gbc.aplicacion.ReprogramacionBean"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="gob.gbc.entidades.Transversalidad"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="gob.gbc.entidades.TipoCompromiso"%>
<%@page import="gob.gbc.entidades.ClasificacionFuncional"%>
<%@page import="gob.gbc.entidades.TipoCalculo"%>
<%@page import="gob.gbc.entidades.UnidadMedida"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    Meta meta = new Meta();
    MetaBean metaBean = null;
    Meta metaAct = new Meta();
    ReprogramacionBean recalBean = null;
    ParametrosBean parametrosBean = null;
    Evaluacion evaluacion = new Evaluacion();
    MovimientoOficioMeta movtoOficioMeta = null;
    Parametros objParametros = new Parametros();
    MovimientosReprogramacion reprogramacion = new MovimientosReprogramacion();

    List<Linea> lineaPedList = new ArrayList<Linea>();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
    List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
    List<TipoCompromiso> tipoCompromisoList = new ArrayList<TipoCompromiso>();
    List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
    List<Transversalidad> transversalidadList = new ArrayList<Transversalidad>();
    List<ReprogramacionMeta> movtoMetasList = new ArrayList<ReprogramacionMeta>();
    List<MovOficioEstimacion> movtoEstimacionList = new ArrayList<MovOficioEstimacion>();
    List<ClasificacionFuncional> clasificacionList = new ArrayList<ClasificacionFuncional>();

    Date date;
    Calendar cal = Calendar.getInstance();
    DateFormat formatMonth = new SimpleDateFormat("MM");
    DateFormat formatYear = new SimpleDateFormat("yyyy");
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    String inputAllDisable = "";
    String tipoC = new String();
    String ramoId = new String();
    String status = new String();
    String inputMonthDisable = "";
    String selected = new String();
    String tipoProy = new String();
    String tipoMeta = new String();
    String ramoDescr = new String();
    String metaDescr = new String();
    String tipoCMovOf = new String();
    String programaId = new String();
    String strResultado = new String();
    String disabledClas = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String appEstimacion = new String();
    String tipoDependencia = new String();

    Double calOriginal = 0.0;
    Double calPropuesta = 0.0;
    Double sumEstimacion = 0.0;
    Double sumRecalendarizacion = 0.0;

    int yearAct;
    int monthAct;
    int cont = 1;
    int year = 0;
    int folio = 0;
    int metaId = 0;
    int disClasif = 0;
    int proyectoId = 0;
    int countAccion = 0;
    int identificador = -1;
    int objIdentificado = 0;
    int meses[] = new int[3];
    int limitMonthDisable = 1;

    boolean autorizacion = false;
    boolean validaTriestre = false;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    request.setCharacterEncoding("UTF-8");

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
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
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
            if ((!status.equalsIgnoreCase("X")) && (!status.equalsIgnoreCase("R")) && !autorizacion) {
                inputAllDisable = "disabled=''";
            }
        }
        if (request.getParameter("folio") != null && !request.getParameter("folio").equals("")) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }

        if (session.getAttribute("reprogramacion") != null && session.getAttribute("reprogramacion") != "") {
            reprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            movtoMetasList = reprogramacion.getMovOficioMetaList();
            if (identificador != -1) {
                for (ReprogramacionMeta movtoMetaListTemp : movtoMetasList) {
                    if (movtoMetaListTemp.getIdentificador() == identificador) {
                        movtoEstimacionList = movtoMetaListTemp.getMovEstimacionList();
                        movtoOficioMeta = movtoMetaListTemp.getMetaInfo();
                    }
                }
            }
        }

        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);

        recalBean = new ReprogramacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);

        parametrosBean = new ParametrosBean(tipoDependencia);
        parametrosBean.setStrServer((request.getHeader("Host")));
        parametrosBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        parametrosBean.resultSQLConecta(tipoDependencia);

        objParametros = parametrosBean.getParametros();

        metaAct = metaBean.getMetaByRamoMetaYear(ramoId, metaId, year);

        ramoId = metaAct.getRamo();
        ramoDescr = ramoId + " - " + metaAct.getRamoDescr();
        programaId = metaAct.getPrograma();
        programaDescr = programaId + " - " + metaAct.getProgramaDescr();
        proyectoId = metaAct.getProyecto();
        tipoProy = metaAct.getTipoProyecto();
        proyectoDescr = metaAct.getTipoProyecto() + metaAct.getProyecto() + " - " + metaAct.getProyectoDescr();
        metaId = metaAct.getMetaId();
        metaDescr = metaId + " - " + metaAct.getMeta();
        if (!status.equalsIgnoreCase("A")) {
            estimacionList = metaBean.getEstimacionByMeta(year, ramoId, metaId);
        } else {
            estimacionList = metaBean.getHistEstimacion(year, ramoId, metaId, folio);
        }
        tipoC = metaBean.getTipoCalculo(year, ramoId, programaId, proyectoId, metaId);
        evaluacion = metaBean.getEvaluacionMeta(year);
        tipoMeta = metaBean.getObraProyecto(year, proyectoId, tipoProy);
        disClasif = metaBean.getCountAccionByMeta(year, ramoId, programaId, metaId);
        meta = metaBean.getMetaById(ramoId, programaId, proyectoId, metaId, year, tipoProy);
        countAccion = metaBean.getCountAccionByMeta(year, ramoId, programaId, metaId);

        strResultado += "<div id='estimacionMetaRP' style=''> ";
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
        strResultado += "			</tbody> ";
        strResultado += "		</table>  ";
        strResultado += "		<br> ";
        strResultado += "<div> ";
        strResultado += "			<fieldset id='fieldsetMetaRC'> ";
        strResultado += "				<legend><strong>Información Original</strong></legend> ";
        strResultado += "		<div class='calenVistaRC'> ";

        if (estimacionList.size() > 0) {
            for (Estimacion estimacion : estimacionList) {
                appEstimacion += numberF.format(estimacion.getValor());
                strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                cont++;
            }
            sumEstimacion = metaBean.getValorCalculado(estimacionList, tipoC);
            calOriginal = metaBean.getValorCalculado(estimacionList, tipoC);
        } else {
            for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                appEstimacion += 0;
                strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='' disabled/></div>";
                cont++;
            }
        }

        strResultado += "		</div> ";
        strResultado += "<div class='muestra-cal' style='width: 100% !important;' ><label>Calendarizaci&oacute;n original: </label> <input id='calOriginal' style='text-align:right; font-size: 12px' disabled value='" + numberF.format(calOriginal) + "'></input></div> ";

        //Informacion meta
        strResultado += "		<div> ";
        strResultado += "<table id='tblInfoMeta'>";
        strResultado += "   <tr> <td> <div> Unidad de medida </div>";
        strResultado += "           <select id='selMedida' disabled='' class='selectOriginal'>";
        strResultado += "               <option value=''></option>";
        unidadMedidaList = metaBean.getUnidadMedidaList(year);
        for (UnidadMedida unidadMedida : unidadMedidaList) {
            if (unidadMedida.getUnidadMedidaId() == meta.getClaveMedida()) {
                selected = "selected";
            }
            strResultado += "           <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">";
            strResultado += unidadMedida.getUnidadMedida();
            strResultado += "           </option>";
            selected = "";
        }
        strResultado += "           </select> </td>";
        strResultado += "       <td> <div> C&aacute;lculo </div>";
        strResultado += "           <select id='selCalculo' disabled='' class='selectOriginal' >";
        strResultado += "               <option value=''></option>";
        if (meta.getCalculo() == null) {
            meta.setCalculo("");
        }
        tipoCalculoList = metaBean.getTipoCalculo();
        for (TipoCalculo tipoCalculo : tipoCalculoList) {
            if (meta.getCalculo().equals(tipoCalculo.getAbrev())) {
                selected = "selected";
            }
            strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
            selected = "";
        }
        strResultado += "           </select> </td>";
        strResultado += "       <td> <div> Clasificaci&oacute;n funcional </div>";
        clasificacionList = metaBean.getClasificacionFuncional(ramoId, programaId, proyectoId, year, tipoProy);
        strResultado += "           <select id='selClasificacion' disabled='' class='selectOriginal' >";
        strResultado += "               <option value=''></option>";

        if (clasificacionList.size() == 1) {
            if (clasificacionList.get(0).getClasificacion().equals(meta.getClasificacion())) {
                selected = "selected";
            }
            strResultado += "           <option value='" + clasificacionList.get(0).getClasificacion() + "' " + selected + "> " + clasificacionList.get(0).getClasificacion() + "-" + clasificacionList.get(0).getClasificacionDescr() + " </option>";
        } else {
            for (ClasificacionFuncional clasificacion : clasificacionList) {
                if (clasificacion.getClasificacion().equals(meta.getClasificacion())) {
                    selected = "selected";
                }
                strResultado += "           <option value='" + clasificacion.getClasificacion() + "' " + selected + "> " + clasificacion.getClasificacion() + "-" + clasificacion.getClasificacionDescr() + " </option>";
                selected = "";
            }
        }
        strResultado += "           </select> </td> ";
        strResultado += "    <td> <div> Compromiso </div>";
        strResultado += "           <select id='selCompromiso' disabled='' class='selectOriginal' >";
        strResultado += "               <option value=''></option>";
        tipoCompromisoList = metaBean.getTipoCompromiso();
        /*if(meta.getCompromiso() == null){
         meta.setCompromiso("");
         }*/
        for (TipoCompromiso tipoCompromiso : tipoCompromisoList) {
            if (meta.getCompromiso() == tipoCompromiso.getTipoCompromisoId()) {
                selected = "selected";
            }
            strResultado += "<option value='" + tipoCompromiso.getTipoCompromisoId() + "' " + selected + "> " + tipoCompromiso.getTipoCompromisoDes() + " </option>";
            selected = new String();
        }
        strResultado += "           </select> </td> </tr>";
        strResultado += "   <tr>    <td> <div> L&iacute;nea PED </div>";
        strResultado += "           <select id='selLineaPed' onchange='getLineaSectorialRPoriginal()' disabled='' class='selectOriginal'            >";
        strResultado += "               <option value=''></option>";
        lineaPedList = metaBean.getLineaRamoPrograma(year, ramoId, programaId);
        if (meta.getLineaPED() == null) {
            meta.setLineaPED("");
        }
        for (Linea lineaPedTemp : lineaPedList) {
            if (meta.getLineaPED().equals(lineaPedTemp.getLineaId())) {
                selected = "selected";
            }
            strResultado += "<option value='" + lineaPedTemp.getLineaId() + "' " + selected + ">";
            strResultado += lineaPedTemp.getLineaId() + "-" + lineaPedTemp.getLinea();
            strResultado += "</option>";
            selected = "";
        }
        strResultado += "           </select> </td>";
        strResultado += "       <td> <div> L&iacute;nea sectorial </div>";
        strResultado += "           <select id='selLineaSect' disabled='' class='selectOriginal' >";
        strResultado += "               <option value=''></option>";
        lineaSectorialList = metaBean.getLineaSectorialByPED(meta.getLineaPED(), year, ramoId, programaId, proyectoId);
        for (LineaSectorial lineaS : lineaSectorialList) {
            if (meta.getLineaSectorial().equals(lineaS.getLineaId())) {
                selected = "selected";
            }
            strResultado += "<option value='" + lineaS.getLineaId() + "' " + selected + " >"
                    + lineaS.getLineaId() + "-" + lineaS.getLineaSectorial() + "</option>";
        }

        strResultado += "           </select> </td> ";
        strResultado += "    <td> <div> Ponderaci&oacute;n </div> <select id='selPonderacion' value='" + meta.getPonderado() + "' disabled='' class='selectOriginal' >";
        strResultado += "               <option value=''></option>";
        if (meta.getPonderado() == 1) {
            strResultado += "       <option selected value='1'>";
        } else {
            strResultado += "       <option value='1'>";
        }
        strResultado += "           1-Baja";
        strResultado += "       </option>";
        if (meta.getPonderado() == 2) {
            strResultado += "       <option selected value='2'>";
        } else {
            strResultado += "       <option value='2'>";
        }
        strResultado += "           2-Media";
        strResultado += "       </option>";
        if (meta.getPonderado() == 3) {
            strResultado += "       <option selected value='3'>";
        } else {
            strResultado += "       <option value='3'>";
        }
        strResultado += "           3-Alta";
        strResultado += "       </option>";
        strResultado += "   </select> </td>";
        strResultado += "<td align='left'> <div> Criterio de transversalidad </div>";
        strResultado += "<select id='selTrasver' disabled='' class='selectOriginal' > <option value=''></option>";
        transversalidadList = metaBean.getTransversalidad();
        for (Transversalidad transv : transversalidadList) {
            if (meta.getCriterio() == transv.getTransversalidadId()) {
                selected = "selected";
            }
            strResultado += "<option value='" + transv.getTransversalidadId() + "' " + selected + ">" + transv.getTransversalidadId()
                    + "-" + transv.getTransvesalidad() + "</option>";
            selected = "";
        }
        strResultado += "</select> </td>";
        strResultado += "</tr>";
        strResultado += "</table>";
        strResultado += "		</div> ";
        strResultado += "			</fieldset> ";
        strResultado += "</div> ";
        strResultado += "		<br>  ";
        strResultado += "		<div> ";
        strResultado += "				<fieldset id='fieldsetMetaRC'> ";
        strResultado += "					<legend><strong>Reprogramación</strong></legend> ";
        strResultado += "<table> ";
        strResultado += "	<tr>  ";
        strResultado += "		<td>  ";
        strResultado += "			<p id='descrMeta'>Descripción de la meta</p>  ";
        strResultado += "		</td> ";
        strResultado += "		<td>  ";
        strResultado += "			<div>  ";
        if (movtoOficioMeta != null) {
            strResultado += "				<textarea maxlength='700' class='no-enter' id='txtAreaMeta' style='text-transform: uppercase;'  " + inputAllDisable + "  >" + movtoOficioMeta.getMetaDescr() + "</textarea> ";
        } else {
            strResultado += "				<textarea maxlength='700' class='no-enter' id='txtAreaMeta' style='text-transform: uppercase;'  " + inputAllDisable + "  >" + metaAct.getMeta() + "</textarea> ";
        }
        strResultado += "			</div>  ";
        strResultado += "		</td>  ";
        strResultado += "	</tr> ";
        strResultado += "</table>       ";
        strResultado += "			<div class='calenVistaRC'> ";

        if (objParametros.getValidaTodosTrimestre().equals("S")) {

            cont = 1;

            if (movtoEstimacionList.size() > 0) {
                for (MovOficioEstimacion estimacion : movtoEstimacionList) {
                    sumRecalendarizacion += estimacion.getValor();
                    strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"M\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'    " + inputAllDisable + " /></div>";
                    cont++;
                }
                tipoCMovOf = movtoOficioMeta.getCalculoId();
                calPropuesta = metaBean.getValorCalculadoMovOficio(movtoEstimacionList, tipoCMovOf);
            } else {
                if (estimacionList.size() > 0) {
                    for (Estimacion estimacion : estimacionList) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input id='estimacion" + (cont - 1) + "' class='estimacion'  maxlength='14' type='text'  value='" + numberF.format(estimacion.getValor()) + "'   onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"M\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); ' onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'    " + inputAllDisable + "  /></div>";
                        cont++;
                    }
                    sumRecalendarizacion = sumEstimacion;
                    calPropuesta = calOriginal;
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"\",\"M\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'    " + inputAllDisable + "  /></div>";
                        cont++;
                    }
                }
            }

        } else {

            cont = 1;
            date = metaBean.getResultSQLgetServerDate();
            yearAct = Integer.parseInt(formatYear.format(date.getTime()));
            validaTriestre = recalBean.getResultSqlGetparametroTrimestre();
            monthAct = Integer.parseInt(formatMonth.format(date.getTime()));
            meses = recalBean.getMesesTrimestreByMes(monthAct, validaTriestre);
            if (!validaTriestre) {
                if (meses.length > 0) {
                    monthAct = meses[0];
                } else {
                    monthAct = 1;
                }
            }
            if (autorizacion) {
                monthAct = 1;
            }
            if (movtoEstimacionList.size() > 0) {
                for (MovOficioEstimacion estimacion : movtoEstimacionList) {
                    sumRecalendarizacion += estimacion.getValor();
                    strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"M\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'  " + Utileria.getDisabledMonth(cont, monthAct, meses) + "  " + inputAllDisable + " /></div>";
                    cont++;
                }
                tipoCMovOf = movtoOficioMeta.getCalculoId();
                calPropuesta = metaBean.getValorCalculadoMovOficio(movtoEstimacionList, tipoCMovOf);
            } else {
                if (estimacionList.size() > 0) {
                    for (Estimacion estimacion : estimacionList) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input id='estimacion" + (cont - 1) + "' class='estimacion'  maxlength='14' type='text'  value='" + numberF.format(estimacion.getValor()) + "'   onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"M\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); ' onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")' " + Utileria.getDisabledMonth(cont, monthAct, meses) + "   " + inputAllDisable + "  /></div>";
                        cont++;
                    }
                    sumRecalendarizacion = sumEstimacion;
                    calPropuesta = calOriginal;
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"\",\"M\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'  " + Utileria.getDisabledMonth(cont, monthAct, meses) + "  " + inputAllDisable + "  /></div>";
                        cont++;
                    }
                }
            }

        }

        strResultado += "			</div> ";

        strResultado += "<div class='muestra-cal' style='width: 100% !important;' ><label>Propuesta de calendarizaci&oacute;n: </label> <input id='calPropuesta' style='text-align:right; font-size: 12px' disabled value='" + numberF.format(calPropuesta) + "'></input></div> ";

        //Informacion meta
        strResultado += "		<div> ";
        strResultado += "<table id='tblInfoMeta'>";

        if (movtoOficioMeta != null) {

            strResultado += "   <tr> <td> <div> Unidad de medida </div>";
            strResultado += "           <select id='selMedidaRP'  " + inputAllDisable + " >";
            strResultado += "               <option value='-1'> -- Seleccione unidad de medida -- </option>";
            unidadMedidaList = metaBean.getUnidadMedidaList(year);
            for (UnidadMedida unidadMedida : unidadMedidaList) {
                if (unidadMedida.getUnidadMedidaId() == movtoOficioMeta.getClaveMedida()) {
                    selected = "selected";
                }
                strResultado += "           <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">";
                strResultado += unidadMedida.getUnidadMedida();
                strResultado += "           </option>";
                selected = "";
            }
            strResultado += "           </select> </td>";

            strResultado += "       <td> <div> C&aacute;lculo </div>";
            strResultado += "           <select id='selCalculoRP'   " + inputAllDisable + "  onChange= 'calculaPropuesta();' >";
            strResultado += "               <option value='-1'> -- Seleccione tipo de c&aacute;lculo -- </option>";
            if (meta.getCalculo() == null) {
                meta.setCalculo("");
            }
            tipoCalculoList = metaBean.getTipoCalculo();
            for (TipoCalculo tipoCalculo : tipoCalculoList) {
                if (movtoOficioMeta.getCalculoId().equals(tipoCalculo.getAbrev())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";

            strResultado += "<td align='left'> <div> Criterio de transversalidad </div>";
            strResultado += "<select id='selTrasverRP'   " + inputAllDisable + " > <option value='-1'> -- Seleccione un criterio -- </option>";
            transversalidadList = metaBean.getTransversalidad();
            for (Transversalidad transv : transversalidadList) {
                if (movtoOficioMeta.getCriterioTransversalidad() == transv.getTransversalidadId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + transv.getTransversalidadId() + "' " + selected + ">" + transv.getTransversalidadId()
                        + "-" + transv.getTransvesalidad() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td>";

            strResultado += "    <td> <div> Compromiso </div>";
            strResultado += "           <select id='selCompromisoRP'  " + inputAllDisable + "  >";
            strResultado += "               <option value='-1'> -- Seleccione tipo de compromiso -- </option>";
            tipoCompromisoList = metaBean.getTipoCompromiso();
            /*if(meta.getCompromiso() == null){
             meta.setCompromiso("");
             }*/
            for (TipoCompromiso tipoCompromiso : tipoCompromisoList) {
                if (movtoOficioMeta.getCompromisoId() == tipoCompromiso.getTipoCompromisoId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCompromiso.getTipoCompromisoId() + "' " + selected + "> " + tipoCompromiso.getTipoCompromisoDes() + " </option>";
                selected = new String();
            }
            strResultado += "           </select> </td> </tr>";
            strResultado += "   <tr>    <td> <div> L&iacute;nea PED </div>";
            strResultado += "           <select id='selLineaPedRP' onchange='getLineaSectorialRPreprogramacion()'     " + inputAllDisable + "    >";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
            lineaPedList = metaBean.getLineaRamoPrograma(year, ramoId, programaId);
            if (movtoOficioMeta.getLineaPedId() == null) {
                movtoOficioMeta.setLineaPedId("");
            }
            for (Linea lineaPedTemp : lineaPedList) {
                if (movtoOficioMeta.getLineaPedId().equals(lineaPedTemp.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaPedTemp.getLineaId() + "' " + selected + ">";
                strResultado += lineaPedTemp.getLineaId() + "-" + lineaPedTemp.getLinea();
                strResultado += "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> L&iacute;nea sectorial </div>";
            strResultado += "           <select id='selLineaSectRP'  " + inputAllDisable + "  >";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea Sectorial -- </option>";
            lineaSectorialList = metaBean.getLineaSectorialByPED(movtoOficioMeta.getLineaPedId(), year, ramoId, programaId, proyectoId);
            for (LineaSectorial lineaS : lineaSectorialList) {
                if (movtoOficioMeta.getLineaSectorialId().equals(lineaS.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaS.getLineaId() + "' " + selected + " >"
                        + lineaS.getLineaId() + "-" + lineaS.getLineaSectorial() + "</option>";
            }

            strResultado += "           </select> </td> ";
            strResultado += "    <td> <div> Ponderaci&oacute;n </div> <select id='selPonderacionRP' value='" + movtoOficioMeta.getPonderacionId() + "'  " + inputAllDisable + " >";
            strResultado += "               <option value='-1'> -- Seleccione una ponderaci&oacute;n -- </option>";
            if (movtoOficioMeta.getPonderacionId().equals("1")) {
                strResultado += "       <option selected value='1'>";
            } else {
                strResultado += "       <option value='1'>";
            }
            strResultado += "           1-Baja";
            strResultado += "       </option>";
            if (movtoOficioMeta.getPonderacionId().equals("2")) {
                strResultado += "       <option selected value='2'>";
            } else {
                strResultado += "       <option value='2'>";
            }
            strResultado += "           2-Media";
            strResultado += "       </option>";
            if (movtoOficioMeta.getPonderacionId().equals("3")) {
                strResultado += "       <option selected value='3'>";
            } else {
                strResultado += "       <option value='3'>";
            }
            strResultado += "           3-Alta";
            strResultado += "       </option>";
            strResultado += "   </select> </td>";

            strResultado += "</tr>";

        } else {

            strResultado += "   <tr> <td> <div> Unidad de medida </div>";
            strResultado += "           <select id='selMedidaRP' " + inputAllDisable + "  >";
            strResultado += "               <option value='-1'> -- Seleccione unidad de medida -- </option>";
            unidadMedidaList = metaBean.getUnidadMedidaList(year);
            for (UnidadMedida unidadMedida : unidadMedidaList) {
                if (unidadMedida.getUnidadMedidaId() == meta.getClaveMedida()) {
                    selected = "selected";
                }
                strResultado += "           <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">";
                strResultado += unidadMedida.getUnidadMedida();
                strResultado += "           </option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> C&aacute;lculo </div>";
            strResultado += "           <select id='selCalculoRP'  " + inputAllDisable + " onChange= 'calculaPropuesta();' >";
            strResultado += "               <option value='-1'> -- Seleccione tipo de c&aacute;lculo -- </option>";
            if (meta.getCalculo() == null) {
                meta.setCalculo("");
            }
            tipoCalculoList = metaBean.getTipoCalculo();
            for (TipoCalculo tipoCalculo : tipoCalculoList) {
                if (meta.getCalculo().equals(tipoCalculo.getAbrev())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";

            strResultado += "<td align='left'> <div> Criterio de transversalidad </div>";
            strResultado += "<select id='selTrasverRP'  " + inputAllDisable + "  > <option value='-1'> -- Seleccione un criterio -- </option>";
            transversalidadList = metaBean.getTransversalidad();
            for (Transversalidad transv : transversalidadList) {
                if (meta.getCriterio() == transv.getTransversalidadId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + transv.getTransversalidadId() + "' " + selected + ">" + transv.getTransversalidadId()
                        + "-" + transv.getTransvesalidad() + "</option>";
                selected = "";
            }
            strResultado += "</select> </td>";

            strResultado += "    <td> <div> Compromiso </div>";
            strResultado += "           <select id='selCompromisoRP'  " + inputAllDisable + "  >";
            strResultado += "               <option value='-1'> -- Seleccione tipo de compromiso -- </option>";
            tipoCompromisoList = metaBean.getTipoCompromiso();

            for (TipoCompromiso tipoCompromiso : tipoCompromisoList) {
                if (meta.getCompromiso() == tipoCompromiso.getTipoCompromisoId()) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCompromiso.getTipoCompromisoId() + "' " + selected + "> " + tipoCompromiso.getTipoCompromisoDes() + " </option>";
                selected = new String();
            }
            strResultado += "           </select> </td> </tr>";
            strResultado += "   <tr>    <td> <div> L&iacute;nea PED </div>";
            strResultado += "           <select id='selLineaPedRP' onchange='getLineaSectorialRPreprogramacion()'      " + inputAllDisable + "         >";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
            lineaPedList = metaBean.getLineaRamoPrograma(year, ramoId, programaId);
            if (meta.getLineaPED() == null) {
                meta.setLineaPED("");
            }
            for (Linea lineaPedTemp : lineaPedList) {
                if (meta.getLineaPED().equals(lineaPedTemp.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaPedTemp.getLineaId() + "' " + selected + ">";
                strResultado += lineaPedTemp.getLineaId() + "-" + lineaPedTemp.getLinea();
                strResultado += "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> L&iacute;nea sectorial </div>";
            strResultado += "           <select id='selLineaSectRP'  " + inputAllDisable + "  >";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea Sectorial -- </option>";
            lineaSectorialList = metaBean.getLineaSectorialByPED(meta.getLineaPED(), year, ramoId, programaId, proyectoId);
            for (LineaSectorial lineaS : lineaSectorialList) {
                if (meta.getLineaSectorial().equals(lineaS.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaS.getLineaId() + "' " + selected + " >"
                        + lineaS.getLineaId() + "-" + lineaS.getLineaSectorial() + "</option>";
            }

            strResultado += "           </select> </td> ";
            strResultado += "    <td> <div> Ponderaci&oacute;n </div> <select id='selPonderacionRP' value='" + meta.getPonderado() + "'  " + inputAllDisable + "  >";
            strResultado += "               <option value='-1'> -- Seleccione una ponderaci&oacute;n -- </option>";
            if (meta.getPonderado() == 1) {
                strResultado += "       <option selected value='1'>";
            } else {
                strResultado += "       <option value='1'>";
            }
            strResultado += "           1-Baja";
            strResultado += "       </option>";
            if (meta.getPonderado() == 2) {
                strResultado += "       <option selected value='2'>";
            } else {
                strResultado += "       <option value='2'>";
            }
            strResultado += "           2-Media";
            strResultado += "       </option>";
            if (meta.getPonderado() == 3) {
                strResultado += "       <option selected value='3'>";
            } else {
                strResultado += "       <option value='3'>";
            }
            strResultado += "           3-Alta";
            strResultado += "       </option>";
            strResultado += "   </select> </td>";

            strResultado += "</tr>";

        }

        strResultado += "</table>";
        strResultado += "		</div> ";
        //Informacion meta

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
        strResultado += "    <center> ";
        if (inputAllDisable.equals("")) {
            strResultado += "        <input type='button' value='Guardar' onclick='actualizarReprogramacionRecalendarizacionMeta()'      /> ";
            strResultado += "        <input type='button' value='Cancelar' onclick=\"fadeOutPopUp('PopUpZone')\"     /> ";
        } else {
            strResultado += "        <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('PopUpZone')\"     /> ";
        }
        strResultado += "        <input type='hidden' id='identificador' value='" + identificador + "' /> ";
        strResultado += "        <input type='hidden' id='sumEstimacion' value='" + sumEstimacion + "' /> ";
        strResultado += "        <input type='hidden' id='sumRecalendarizacion' value='" + sumRecalendarizacion + "' /> ";
        strResultado += "        <input type='hidden' id='ramoId' value='" + ramoId + "' /> ";
        strResultado += "        <input type='hidden' id='metaId' value='" + metaId + "' /> ";
        strResultado += "        <input type='hidden' id='programaId' value='" + programaId + "' /> ";
        strResultado += "        <input type='hidden' id='proyectoId' value='" + proyectoId + "' /> ";
        strResultado += "        <input type='hidden' id='tipoProy' value='" + tipoProy + "' /> ";
        strResultado += "	   <input type='hidden' id='appEstimacion' value='" + appEstimacion.replaceAll(",", "") + "' /> ";
        strResultado += "	   <input type='hidden' id='txtAreaMetaRP' value='" + metaAct.getMeta() + "' /> ";
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
        if (metaBean != null) {
            metaBean.resultSQLDesconecta();
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

<script>
    $('.no-enter').bind('keypress', function(e){
        if(e.keyCode == 13)
        {
           return false;
        }
     });     
</script>
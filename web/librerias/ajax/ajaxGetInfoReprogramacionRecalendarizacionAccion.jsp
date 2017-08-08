<%-- 
    Document   : ajaxGetInfoReprogramacionRecalendarizacionAccion
    Created on : Dec 28, 2015, 08:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="gob.gbc.aplicacion.ReprogramacionBean"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.entidades.GrupoPoblacional"%>
<%@page import="gob.gbc.entidades.Localidad"%>
<%@page import="gob.gbc.entidades.Municipio"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.MovimientoOficioAccion"%>
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
    AccionBean accionBean = null;
    Accion accion = new Accion();
    Accion accionAct = new Accion();
    ParametrosBean parametrosBean = null;
    Parametros objParametros = new Parametros();
    ReprogramacionBean reprogramacionBean = null;
    Evaluacion evaluacion = new Evaluacion();
    MovimientoOficioAccion movtoOficioAccion = null;
    MovimientosReprogramacion reprogramacion = new MovimientosReprogramacion();

    List<Linea> lineaPedList = new ArrayList<Linea>();
    List<Municipio> municipioList = new ArrayList<Municipio>();
    List<Localidad> localidadList = new ArrayList<Localidad>();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
    List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
    List<GrupoPoblacional> grupoList = new ArrayList<GrupoPoblacional>();
    List<LineaSectorial> lineaSectorialList = new ArrayList<LineaSectorial>();
    List<TipoCompromiso> tipoCompromisoList = new ArrayList<TipoCompromiso>();
    List<Transversalidad> transversalidadList = new ArrayList<Transversalidad>();
    List<ReprogramacionMeta> movtoMetasList = new ArrayList<ReprogramacionMeta>();
    List<ReprogramacionAccion> movtoAccionesList = new ArrayList<ReprogramacionAccion>();
    List<ClasificacionFuncional> clasificacionList = new ArrayList<ClasificacionFuncional>();
    List<MovOficioAccionEstimacion> movtoEstimacionList = new ArrayList<MovOficioAccionEstimacion>();

    Date date;
    Calendar cal = Calendar.getInstance();
    DateFormat formatMonth = new SimpleDateFormat("MM");
    DateFormat formatYear = new SimpleDateFormat("yyyy");
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    String tipoC = new String();
    String inputAllDisable = "";
    String status = new String();
    String ramoId = new String();
    String inputMonthDisable = "";
    String compDis = new String();
    String tipoProy = new String();
    String tipoMeta = new String();
    String selected = new String();
    String ramoDescr = new String();
    String metaDescr = new String();
    String programaId = new String();
    String tipoCMovOf = new String();
    String accionDescr = new String();
    String strResultado = new String();
    String disabledClas = new String();
    String programaDescr = new String();
    String appEstimacion = new String();
    String proyectoDescr = new String();
    String tipoCompromiso = new String();
    String tipoDependencia = new String();

    int yearAct;
    int monthAct;
    int cont = 1;
    int year = 0;
    int folio = 0;
    int metaId = 0;
    int accionId = 0;
    int disClasif = 0;
    int proyectoId = 0;
    int countAccion = 0;
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
        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt(request.getParameter("accionId"));
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
            movtoAccionesList = reprogramacion.getMovOficioAccionList();
            if (identificador != -1) {
                for (ReprogramacionAccion movtoAccionListTemp : movtoAccionesList) {
                    if (movtoAccionListTemp.getIdentificador() == identificador) {
                        movtoEstimacionList = movtoAccionListTemp.getMovAcionEstimacionList();
                        movtoOficioAccion = movtoAccionListTemp.getMovOficioAccion();
                        tipoCMovOf = movtoOficioAccion.getCalculo();
                    }
                }
            }
        }

        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        reprogramacionBean = new ReprogramacionBean(tipoDependencia);
        reprogramacionBean.setStrServer(request.getHeader("host"));
        reprogramacionBean.setStrUbicacion(getServletContext().getRealPath(""));
        reprogramacionBean.resultSQLConecta(tipoDependencia);

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
        proyectoDescr = accionAct.getTipoproy() + accionAct.getProyId() + " - " + accionAct.getProgramaDescr();
        metaDescr = metaId + " - " + accionAct.getMetaDescr();
        accionDescr = accionId + " - " + accionAct.getAccion();
        if (!status.equalsIgnoreCase("A")) {
            //estimacionList = metaBean.getEstimacionByMeta(year, ramoId, metaId);
            estimacionList = accionBean.getEstimacionByAccion(year, ramoId, metaId, accionId);
        } else {
            estimacionList = accionBean.getHistEstimacion(year, ramoId, metaId, accionId, folio);
        }
        tipoC = accionBean.getTipoCalculo(year, ramoId, metaId, accionId);
        evaluacion = accionBean.getResultSQLGetEvaluacionMeta(year);
        accion = accionBean.getAccionById(year, ramoId, metaId, accionId);

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

                appEstimacion += numberF.format(estimacion.getValor());
                strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                cont++;
            }
            sumEstimacion = accionBean.getValorCalculado(estimacionList, tipoC);
            calOriginal = accionBean.getValorCalculado(estimacionList, tipoC);
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
        strResultado += " <tr>";

        strResultado += "<td> <div> Unidad ejecutora </div> ";
        strResultado += "<select id='selUnidadEj' " + " disabled='' class='selectOriginal' >";
        strResultado += "<option value='' " + "></option>";
        dependenciaList = accionBean.getDependencia(ramoId, year, programaId);
        for (Dependencia dependencia : dependenciaList) {
            if (dependencia.getDeptoId().equals(accion.getDeptoId())) {
                selected = "selected";
            }
            strResultado += "<option value='" + dependencia.getDeptoId() + "' " + selected + ">" + dependencia.getDeptoId() + "-" + dependencia.getDepartamento() + "</option>";
            selected = "";
        }
        strResultado += "</select> </td>";

        strResultado += "  <td> <div> Unidad de medida </div>";
        strResultado += "           <select id='selMedida' disabled='' class='selectOriginal'>";
        strResultado += "               <option value=''></option>";
        unidadMedidaList = accionBean.getUnidadMedida(year);
        for (UnidadMedida unidadMedida : unidadMedidaList) {
            if (unidadMedida.getUnidadMedidaId() == accion.getMedidaId()) {
                selected = "selected";
            }
            strResultado += "           <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">";
            strResultado += unidadMedida.getUnidadMedida();
            strResultado += "           </option>";
            selected = "";
        }
        strResultado += "           </select> </td>";

        strResultado += "<td> <div> Municipio </div> ";
        strResultado += "<select id='selMunicipio' onchange='getLocalidad()' " + " disabled='' class='selectOriginal' >";
        strResultado += "<option value=''></option>";
        municipioList = accionBean.getMunicipios();
        for (Municipio municipio : municipioList) {
            if (municipio.getMunicipioId() == accion.getMunicipio()) {
                selected = "selected";
            }
            strResultado += "<option value='" + municipio.getMunicipioId() + "' " + selected + ">" + municipio.getMunicipioId() + "-" + municipio.getMunicipio() + "</option>";
            selected = "";
        }
        strResultado += "</select> </td>";

        strResultado += "<td> <div> Localidad </div>";
        strResultado += "<select id='selLocalidad' " + " disabled='' class='selectOriginal' >";
        strResultado += "<option value=''></option>";
        localidadList = accionBean.getlocalidades(accion.getMunicipio());
        for (Localidad localidad : localidadList) {
            if (localidad.getLocalidadId() == accion.getLocalidad()) {
                selected = "selected";
            }
            strResultado += "<option value='" + localidad.getLocalidadId() + "' " + selected + ">" + localidad.getLocalidadId() + "-" + localidad.getLocalidad() + "</option>";
            selected = "";
        }
        strResultado += "</select> </td>";

        strResultado += "</tr> <tr>";

        strResultado += "       <td> <div> C&aacute;lculo </div>";
        strResultado += "           <select id='selCalculo' disabled='' class='selectOriginal' >";
        strResultado += "               <option value=''></option>";
        if (accion.getCalculo() == null) {
            accion.setCalculo("");
        }
        tipoCalculoList = accionBean.getTipoCalculo();
        for (TipoCalculo tipoCalculo : tipoCalculoList) {
            if (accion.getCalculo().equals(tipoCalculo.getAbrev())) {
                selected = "selected";
            }
            strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
            selected = "";
        }
        strResultado += "           </select> </td>";

        strResultado += "       <td> <div> L&iacute;nea PED </div>";
        strResultado += "           <select id='selLineaPed' onchange='getLineaSectorialRPoriginal()' disabled='' class='selectOriginal'            >";
        strResultado += "               <option value=''></option>";
        lineaPedList = accionBean.getLineaAccion(year, ramoId, programaId);
        if (accion.getLineaPed() == null) {
            accion.setLineaPed("");
        }
        for (Linea lineaPedTemp : lineaPedList) {
            if (accion.getLineaPed().equals(lineaPedTemp.getLineaId())) {
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
        lineaSectorialList = accionBean.getLineaSectorialByPED(accion.getLineaPed(), year, ramoId, programaId, proyectoId);
        for (LineaSectorial lineaS : lineaSectorialList) {
            if (accion.getLineaSectorial().equals(lineaS.getLineaId())) {
                selected = "selected";
            }
            strResultado += "<option value='" + lineaS.getLineaId() + "' " + selected + " >"
                    + lineaS.getLineaId() + "-" + lineaS.getLineaSectorial() + "</option>";
        }
        strResultado += "           </select> </td> ";

        tipoCompromiso = accionBean.getTipoCompromisoMeta(year, ramoId, metaId);

        for (ReprogramacionMeta movtoMetaListTemp : movtoMetasList) {
            if (movtoMetaListTemp.getMetaInfo().getMetaId() == metaId) {
                tipoCompromiso = accionBean.getTipoCompromisoByTipoCompromiso(movtoMetaListTemp.getMetaInfo().getCompromisoId());
            }
        }

        if (tipoCompromiso.equals("N")) {
            compDis = "style='display:none;'";
        }

        strResultado += "<td " + compDis + " > <div> Grupo poblacional </div>";
        strResultado += "<select id='selGrupo'  disabled='' class='selectOriginal'  >";
        strResultado += "<option value=''></option> ";
        grupoList = accionBean.getGrupoPoblacional();
        for (GrupoPoblacional grupo : grupoList) {
            if (grupo.getGrupoPobId() == accion.getGrupoPob()) {
                selected = "selected";
            }
            strResultado += "<option value='" + grupo.getGrupoPobId() + "' " + selected + ">" + grupo.getGripoPoblacional() + "</option>";
            selected = "";
        }
        strResultado += "</select> </td> ";

        strResultado += "</tr>";
        strResultado += "<tr>";
        strResultado += "<td " + compDis + "> <div id='divGenero'> <div> Mujeres <input type='text' id='accMuj' disabled=''  value='" + accion.getBenefMuj() + "' " + "/> </div>";
        strResultado += "<div> Hombres  <input type='text' id='accHom' disabled='' value='" + accion.getBenefHom() + "' " + "/> </div> </div> </td> ";
        strResultado += "<td></td>";
        strResultado += "<td></td>";
        strResultado += "<td></td>";
        strResultado += "</tr>";
        strResultado += "</table>";
        strResultado += "		</div> ";
        //Informacion meta

        strResultado += "			</fieldset> ";
        strResultado += "</div> ";

        strResultado += "<script type='text/javascript'>";

        strResultado += "              $('#accMuj').keydown(function (e) {";
        strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
        strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
        strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
        strResultado += "                            return;";
        strResultado += "                  }";
        strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
        strResultado += " e.preventDefault();";
        strResultado += "                   }";
        strResultado += "                });";
        strResultado += "              $('#accHom').keydown(function (e) {";
        strResultado += "                   if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||";
        strResultado += "                      (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || ";
        strResultado += "                      (e.keyCode >= 35 && e.keyCode <= 40)) {";
        strResultado += "                            return;";
        strResultado += "                  }";
        strResultado += " if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {";
        strResultado += " e.preventDefault();";
        strResultado += "                   }";
        strResultado += "                });";

        strResultado += "</script>";

        strResultado += "		<br>  ";
        strResultado += "		<div> ";
        strResultado += "				<fieldset id='fieldsetMetaRC'> ";
        strResultado += "					<legend><strong>Reprogramación</strong></legend> ";
        strResultado += "<table> ";
        strResultado += "	<tr>  ";
        strResultado += "		<td>  ";
        strResultado += "			<p id='descrMeta'>Descripción de la acción</p>  ";
        strResultado += "		</td> ";
        strResultado += "		<td>  ";
        strResultado += "			<div>  ";
        if (movtoOficioAccion != null) {
            strResultado += "				<textarea maxlength='300' class='no-enter' id='txtAreaAccion' style='text-transform: uppercase;'  " + inputAllDisable + " >" + movtoOficioAccion.getAccionDescr() + "</textarea> ";
        } else {
            strResultado += "				<textarea maxlength='300' class='no-enter' id='txtAreaAccion' style='text-transform: uppercase;'  " + inputAllDisable + " >" + accionAct.getAccion() + "</textarea> ";
        }
        strResultado += "			</div>  ";
        strResultado += "		</td>  ";
        strResultado += "	</tr> ";
        strResultado += "</table>       ";
        strResultado += "			<div class='calenVistaRC'> ";

        cont = 1;

        if (objParametros.getValidaTodosTrimestre().equals("S")) {

            if (movtoEstimacionList.size() > 0) {
                for (MovOficioAccionEstimacion estimacion : movtoEstimacionList) {
                    sumRecalendarizacion += estimacion.getValor();
                    strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'    " + inputAllDisable + "   /></div>";
                    cont++;
                }
                calPropuesta = accionBean.getValorCalculadoMovOficio(movtoEstimacionList, tipoCMovOf);
            } else {
                if (estimacionList.size() > 0) {
                    for (Estimacion estimacion : estimacionList) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input id='estimacion" + (cont - 1) + "' class='estimacion'  maxlength='14' type='text'  value='" + numberF.format(estimacion.getValor()) + "'   onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); ' onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'   " + inputAllDisable + "    /></div>";
                        cont++;
                    }
                    sumRecalendarizacion = sumEstimacion;
                    calPropuesta = sumEstimacion;
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'    " + inputAllDisable + "  /></div>";
                        cont++;
                    }
                }
            }

        } else {

            date = accionBean.getResultSQLgetServerDate();
            yearAct = Integer.parseInt(formatYear.format(date.getTime()));
            validaTrimestre = accionBean.getResultSqlGetparametroTrimestre();
            monthAct = Integer.parseInt(formatMonth.format(date.getTime()));
            meses = reprogramacionBean.getMesesTrimestreByMes(monthAct, validaTrimestre);
            if (!validaTrimestre) {
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
                for (MovOficioAccionEstimacion estimacion : movtoEstimacionList) {
                    sumRecalendarizacion += estimacion.getValor();
                    strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'  " + Utileria.getDisabledMonth(cont, monthAct, meses) + "  " + inputAllDisable + "   /></div>";
                    cont++;
                }
                calPropuesta = accionBean.getValorCalculadoMovOficio(movtoEstimacionList, tipoCMovOf);
            } else {
                if (estimacionList.size() > 0) {
                    for (Estimacion estimacion : estimacionList) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input id='estimacion" + (cont - 1) + "' class='estimacion'  maxlength='14' type='text'  value='" + numberF.format(estimacion.getValor()) + "'   onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"" + numberF.format(estimacion.getValor()) + "\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); ' onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")' " + Utileria.getDisabledMonth(cont, monthAct, meses) + "   " + inputAllDisable + "    /></div>";
                        cont++;
                    }
                    sumRecalendarizacion = sumEstimacion;
                    calPropuesta = sumEstimacion;
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur=' operarReprogramacion(this.value,\"" + tipoC + "\",\"estimacion" + (cont - 1) + "\",\"\",\"A\"," + cont + ",\"" + Utileria.getStringMesCompleto(cont) + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'  " + Utileria.getDisabledMonth(cont, monthAct, meses) + "  " + inputAllDisable + "  /></div>";
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

        if (movtoOficioAccion != null) {

            strResultado += "   <tr> <td> <div> Unidad de medida </div>";
            strResultado += "           <select id='selMedidaRP'  " + inputAllDisable + "  >";
            strResultado += "               <option value='-1'> -- Seleccione unidad de medida -- </option>";
            unidadMedidaList = accionBean.getUnidadMedida(year);
            for (UnidadMedida unidadMedida : unidadMedidaList) {
                if (unidadMedida.getUnidadMedidaId() == movtoOficioAccion.getClaveMedida()) {
                    selected = "selected";
                }
                strResultado += "           <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">";
                strResultado += unidadMedida.getUnidadMedida();
                strResultado += "           </option>";
                selected = "";
            }
            strResultado += "           </select> </td>";

            strResultado += "       <td> <div> C&aacute;lculo </div>";
            strResultado += "           <select id='selCalculoRP'   " + inputAllDisable + " onChange='calculaPropuesta();'>";
            strResultado += "               <option value='-1'> -- Seleccione tipo de c&aacute;lculo -- </option>";
            if (accion.getCalculo() == null) {
                accion.setCalculo("");
            }
            tipoCalculoList = accionBean.getTipoCalculo();
            for (TipoCalculo tipoCalculo : tipoCalculoList) {
                if (movtoOficioAccion.getCalculo().equals(tipoCalculo.getAbrev())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";

            strResultado += "      <td> <div> L&iacute;nea PED </div>";
            strResultado += "           <select id='selLineaPedRP' onchange='getLineaSectorialRPreprogramacion()'   " + inputAllDisable + "      >";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
            lineaPedList = accionBean.getLineaAccion(year, ramoId, programaId);
            if (movtoOficioAccion.getLinea() == null) {
                movtoOficioAccion.setLinea("");
            }
            for (Linea lineaPedTemp : lineaPedList) {
                if (movtoOficioAccion.getLinea().equals(lineaPedTemp.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaPedTemp.getLineaId() + "' " + selected + ">";
                strResultado += lineaPedTemp.getLineaId() + "-" + lineaPedTemp.getLinea();
                strResultado += "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> L&iacute;nea sectorial </div>";
            strResultado += "           <select id='selLineaSectRP'   " + inputAllDisable + " >";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea Sectorial -- </option>";
            lineaSectorialList = accionBean.getLineaSectorialByPED(movtoOficioAccion.getLinea(), year, ramoId, programaId, proyectoId);
            for (LineaSectorial lineaS : lineaSectorialList) {
                if (movtoOficioAccion.getLineaSectorial().equals(lineaS.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaS.getLineaId() + "' " + selected + " >"
                        + lineaS.getLineaId() + "-" + lineaS.getLineaSectorial() + "</option>";
            }
            strResultado += "           </select> </td> ";

            strResultado += "</tr>";
            strResultado += "<tr>";
            strResultado += "<td " + compDis + "> <div id='divGenero'> <div> Mujeres <input type='text' id='accMujRP'  value='" + movtoOficioAccion.getBenefMujer() + "' " + "/> </div>";
            strResultado += "<div> Hombres  <input type='text' id='accHomRP'  value='" + movtoOficioAccion.getBenefHombre() + "' " + "/> </div> </div> </td> ";
            strResultado += "<td></td>";
            strResultado += "<td></td>";
            strResultado += "<td></td>";
            strResultado += "</tr>";

        } else {

            strResultado += "   <tr> <td> <div> Unidad de medida </div>";
            strResultado += "           <select id='selMedidaRP'  " + inputAllDisable + " >";
            strResultado += "               <option value='-1'> -- Seleccione unidad de medida -- </option>";
            unidadMedidaList = accionBean.getUnidadMedida(year);
            for (UnidadMedida unidadMedida : unidadMedidaList) {
                if (unidadMedida.getUnidadMedidaId() == accion.getMedidaId()) {
                    selected = "selected";
                }
                strResultado += "           <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">";
                strResultado += unidadMedida.getUnidadMedida();
                strResultado += "           </option>";
                selected = "";
            }
            strResultado += "           </select> </td>";
            strResultado += "       <td> <div> C&aacute;lculo </div>";
            strResultado += "           <select id='selCalculoRP'   " + inputAllDisable + " onChange='calculaPropuesta();'>";
            strResultado += "               <option value='-1'> -- Seleccione tipo de c&aacute;lculo -- </option>";
            if (accion.getCalculo() == null) {
                accion.setCalculo("");
            }
            tipoCalculoList = accionBean.getTipoCalculo();
            for (TipoCalculo tipoCalculo : tipoCalculoList) {
                if (accion.getCalculo().equals(tipoCalculo.getAbrev())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
                selected = "";
            }
            strResultado += "           </select> </td>";

            strResultado += "      <td> <div> L&iacute;nea PED </div>";
            strResultado += "           <select id='selLineaPedRP' onchange='getLineaSectorialRPreprogramacion()'         " + inputAllDisable + "      >";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
            lineaPedList = accionBean.getLineaAccion(year, ramoId, programaId);
            if (accion.getLineaPed() == null) {
                accion.setLineaPed("");
            }
            for (Linea lineaPedTemp : lineaPedList) {
                if (accion.getLineaPed().equals(lineaPedTemp.getLineaId())) {
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
            lineaSectorialList = accionBean.getLineaSectorialByPED(accion.getLineaPed(), year, ramoId, programaId, proyectoId);
            for (LineaSectorial lineaS : lineaSectorialList) {
                if (accion.getLineaSectorial().equals(lineaS.getLineaId())) {
                    selected = "selected";
                }
                strResultado += "<option value='" + lineaS.getLineaId() + "' " + selected + " >"
                        + lineaS.getLineaId() + "-" + lineaS.getLineaSectorial() + "</option>";
            }

            strResultado += "           </select> </td> ";
            strResultado += "</tr>";
            strResultado += "<tr>";
            strResultado += "<td " + compDis + "> <div id='divGenero'> <div> Mujeres <input type='text' id='accMujRP'  value='" + accion.getBenefMuj() + "' " + "/> </div>";
            strResultado += "<div> Hombres  <input type='text' id='accHomRP'  value='" + accion.getBenefHom() + "' " + "/> </div> </div> </td> ";
            strResultado += "<td></td>";
            strResultado += "<td></td>";
            strResultado += "<td></td>";
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
            strResultado += "        <input type='button' value='Guardar' onclick='actualizarReprogramacionRecalendarizacionAccion()'      /> ";
            strResultado += "        <input type='button' value='Cancelar' onclick=\"fadeOutPopUp('PopUpZone')\"     /> ";
        } else {
            strResultado += "        <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('PopUpZone')\"     /> ";
        }
        strResultado += "        <input type='hidden' id='identificador' name='identificador' value='" + identificador + "' /> ";
        strResultado += "        <input type='hidden' id='sumEstimacion' value='" + sumEstimacion + "' /> ";
        strResultado += "        <input type='hidden' id='sumRecalendarizacion' value='" + sumRecalendarizacion + "' /> ";
        strResultado += "        <input type='hidden' id='ramoId' value='" + ramoId + "' /> ";
        strResultado += "        <input type='hidden' id='metaId' value='" + metaId + "' /> ";
        strResultado += "        <input type='hidden' id='accionId' value='" + accionId + "' /> ";
        strResultado += "        <input type='hidden' id='programaId' value='" + programaId + "' /> ";
        strResultado += "        <input type='hidden' id='proyectoId' value='" + proyectoId + "' /> ";
        strResultado += "        <input type='hidden' id='tipoProy' value='" + tipoProy + "' /> ";
        strResultado += "	   <input type='hidden' id='appEstimacion' value='" + appEstimacion.replaceAll(",", "") + "' /> ";
        strResultado += "	   <input type='hidden' id='txtAreaAccionRP' value='" + accionAct.getAccion() + "' /> ";
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
        if (reprogramacionBean != null) {
            reprogramacionBean.resultSQLDesconecta();
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
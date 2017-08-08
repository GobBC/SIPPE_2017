<%-- 
    Document   : ajaxGetInfoTransferenciaMeta
    Created on : Jan 25, 2016, 03:21:34 PM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="gob.gbc.entidades.Localidad"%>
<%@page import="gob.gbc.entidades.Municipio"%>
<%@page import="gob.gbc.entidades.GrupoPoblacional"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="gob.gbc.entidades.TipoCompromiso"%>
<%@page import="gob.gbc.entidades.ClasificacionFuncional"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="gob.gbc.entidades.LineaSectorial"%>
<%@page import="gob.gbc.entidades.UnidadMedida"%>
<%@page import="gob.gbc.entidades.TipoCalculo"%>
<%@page import="gob.gbc.entidades.Transversalidad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    ParametrosBean parametrosBean = null;
    Parametros objParametros = new Parametros();
    MovimientosTransferencia transferencia = null;
    TransferenciaBean transferenciaBean = null;
    MovimientoOficioMeta movtoOficioMeta = new MovimientoOficioMeta();
    Evaluacion evaluacion = new Evaluacion();
    MetaBean metaBean = null;
    Meta metaAct = null;
    List<Transversalidad> transversalidadList = new ArrayList<Transversalidad>();
    List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
    List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
    List<Linea> lineaSectorialList = new ArrayList<Linea>();
    List<Linea> lineaPedList = new ArrayList<Linea>();
    List<ClasificacionFuncional> clasificacionList = new ArrayList<ClasificacionFuncional>();
    List<TipoCompromiso> tipoCompromisoList = new ArrayList<TipoCompromiso>();
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    List<GrupoPoblacional> grupoList = new ArrayList<GrupoPoblacional>();
    List<Municipio> municipioList = new ArrayList<Municipio>();
    List<Linea> lineaList = new ArrayList<Linea>();
    List<Localidad> localidadList = new ArrayList<Localidad>();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<MovOficioEstimacion> movtoEstimacionMetaList = new ArrayList<MovOficioEstimacion>();
    List<TransferenciaMeta> movtoMetasList = null;
    List<MovOficioEstimacion> movtoEstimacionMetaLis = new ArrayList<MovOficioEstimacion>();
    List<Transferencia> movimientosTransferenciaRecibenList = new ArrayList<Transferencia>();
    String strResultado = new String();
    String selected = new String();
    String ramoId = new String();
    String ramoDescr = new String();
    String programaId = new String();
    String programaDescr = new String();
    String tipoProyecto = new String();
    String tipoDependencia = new String();
    String tipoC = new String();
    String tipoMeta = new String();
    String metaDescr = new String();
    String disabledClas = new String();
    String proyectoDescr = new String();
    String tipoProy = new String();
    String calculo = new String();
    String tipoCompromiso = new String();
    String inputMonthDisable = "";
    String lineaPedID = new String();
    String status = new String();
    String inputAllDisable = "";
    String tipoTransferencia = new String();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    Date date;
    DateFormat formatYear = new SimpleDateFormat("yyyy");
    DateFormat formatMonth = new SimpleDateFormat("MM");
    Calendar cal = Calendar.getInstance();
    int yearAct = Integer.parseInt(formatYear.format(cal.getTime()));
    int monthAct = Integer.parseInt(formatMonth.format(cal.getTime()));
    int limitMonthDisable = 1;
    int contFallas = 0;
    int cont = 1;
    int metaId = 0;
    int consulta = 0;
    int accionId = 0;
    int year = 0;
    int optAccion = 0;
    int proyectoId = 0;
    int contReq = 0;
    int disClasif = 0;
    int countAccion = 0;
    int optMeta = 0;
    int identificador = -1;
    int meses[] = new int[3];
    Double sumRecalendarizacion = 0.0;
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

        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = request.getParameter("programaId");
        }

        if (request.getParameter("proyectoId") != null && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt(request.getParameter("proyectoId"));
        }

        if (request.getParameter("tipoProyecto") != null && !request.getParameter("tipoProyecto").equals("")) {
            tipoProyecto = request.getParameter("tipoProyecto");
        }

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt(request.getParameter("identificador"));
        }

        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt(request.getParameter("metaId"));
        }

        if (request.getParameter("calculo") != null && !request.getParameter("calculo").equals("")) {
            calculo = request.getParameter("calculo");
        }

        if (request.getParameter("autorizacion") != null && !request.getParameter("autorizacion").equals("")) {
            autorizacion = Boolean.valueOf(request.getParameter("autorizacion"));
        }

        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            status = request.getParameter("estatus");
            if ((!status.equalsIgnoreCase("X")) && (!status.equalsIgnoreCase("R")) && (!status.equalsIgnoreCase("K")) && (status.equalsIgnoreCase("T") && autorizacion)) {
                inputAllDisable = "disabled=''";
            }
        }

        if (request.getParameter("tipoTransferencia") != null && !request.getParameter("tipoTransferencia").equals("")) {
            tipoTransferencia = request.getParameter("tipoTransferencia");
        }

        if (request.getParameter("tipoTransferencia") != null && !request.getParameter("tipoTransferencia").equals("")) {
            tipoTransferencia = request.getParameter("tipoTransferencia");
        }

        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            transferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            movtoMetasList = transferencia.getTransferenciaMetaList();
            if (identificador != -1) {
                for (TransferenciaMeta movtoMetaListTemp : movtoMetasList) {
                    if (movtoMetaListTemp.getIdentificador() == identificador) {
                        movtoEstimacionMetaList = movtoMetaListTemp.getMovOficioEstimacion();
                        movtoOficioMeta = movtoMetaListTemp.getMovOficioMeta();
                    }
                }
                programaId = movtoOficioMeta.getPrgId();
                proyectoId = movtoOficioMeta.getProyId();
                tipoProyecto = movtoOficioMeta.getTipoProy();
                year = movtoOficioMeta.getYear();
                ramoId = movtoOficioMeta.getRamoId();
            }
        }

        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);

        transferenciaBean = new TransferenciaBean(tipoDependencia);
        transferenciaBean.setStrServer(request.getHeader("host"));
        transferenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
        transferenciaBean.resultSQLConecta(tipoDependencia);

        parametrosBean = new ParametrosBean(tipoDependencia);
        parametrosBean.setStrServer((request.getHeader("Host")));
        parametrosBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        parametrosBean.resultSQLConecta(tipoDependencia);

        objParametros = parametrosBean.getParametros();

        evaluacion = metaBean.getEvaluacionMeta(year);

        ramoDescr = metaBean.getRamoDescrByRamoYear(ramoId, year);
        ramoDescr = ramoId + " - " + ramoDescr;
        programaDescr = metaBean.getProgramaDescrByProgramaYear(programaId, year);
        programaDescr = programaId + " - " + programaDescr;
        proyectoDescr = metaBean.getProyectoDescrByProyectoTipoProyYear(proyectoId, tipoProyecto, year);
        proyectoDescr = tipoProyecto + proyectoId + " - " + proyectoDescr;
        metaDescr = movtoOficioMeta.getMetaDescr();
        if (metaDescr == null) {
            metaDescr = "";
        }

        strResultado += "<div id='infoMetaAmpRed' style=''> ";

        strResultado += "   <table id='informacionMeta' cellspacing='10'>";
        strResultado += "       <tr>";
        strResultado += "           <td> Ramo </td>";
        strResultado += "           <td>" + ramoDescr + "</td>";
        strResultado += "       </tr>";
        strResultado += "       <tr>";
        strResultado += "           <td> Programa </td>";
        strResultado += "           <td>" + programaDescr + "</td>";
        strResultado += "       </tr>";
        strResultado += "       <tr> ";
        strResultado += "           <td> Proy/Act. </td>";
        strResultado += "           <td>" + proyectoDescr + "</td> ";
        strResultado += "       </tr>";
        strResultado += "       <tr> ";
        strResultado += "           <td> <p id='descrMeta'>Descripci&oacute;n de meta</p> </td> ";
        strResultado += "           <td> <div> <textArea class='no-enter' maxlength='700' id='txtAreaMeta' style='text-transform: uppercase;'>" + metaDescr + "</textArea></div> </td> ";
        strResultado += "       </tr> ";
        strResultado += "   </table> ";

        strResultado += "   <table id='tblInfoMeta'>";

        strResultado += "       <tr> ";
        strResultado += "           <td> ";
        strResultado += "               <div> Unidad de medida </div> ";
        strResultado += "               <select id='selMedida'>";
        strResultado += "                   <option value='-1'> -- Seleccione unidad de medida -- </option>";
        unidadMedidaList = metaBean.getUnidadMedidaList(year);
        for (UnidadMedida unidadMedida : unidadMedidaList) {
            if (unidadMedida.getUnidadMedidaId() == movtoOficioMeta.getClaveMedida()) {
                selected = "selected";
            }
            strResultado += "                   <option value=" + unidadMedida.getUnidadMedidaId() + " " + selected + ">" + unidadMedida.getUnidadMedida() + "</option>";
            selected = "";
        }
        strResultado += "               </select> ";
        strResultado += "           </td>";

        strResultado += "           <td> ";
        strResultado += "               <div> C&aacute;lculo </div>";
        strResultado += "               <select id='selCalculo' onChange='validarFlotanteNewMetaTransferencia(\"0\")'>";
        strResultado += "                   <option value='-1'> -- Seleccione tipo de c&aacute;lculo -- </option>";
        if (movtoOficioMeta.getCalculoId() == null) {
            movtoOficioMeta.setCalculoId("");
        }
        tipoCalculoList = metaBean.getTipoCalculo();
        for (TipoCalculo tipoCalculo : tipoCalculoList) {
            if (movtoOficioMeta.getCalculoId().equals(tipoCalculo.getAbrev())) {
                selected = "selected";
            }
            strResultado += "                   <option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
            selected = "";
        }
        strResultado += "               </select>";
        strResultado += "           </td>";

        strResultado += "           <td>";
        strResultado += "               <div> Clasificaci&oacute;n funcional </div>";
        clasificacionList = metaBean.getClasificacionFuncional(ramoId, programaId, proyectoId, year, tipoProyecto);
        strResultado += "               <select id='selClasificacion' " + disabledClas + ">";
        strResultado += "                   <option value='-1'> -- Seleccione una clasificaci&oacute;n -- </option>";
        for (ClasificacionFuncional clasificacion : clasificacionList) {
            if (clasificacion.getClasificacion().equals(movtoOficioMeta.getClasificacionFuncionalId())) {
                selected = "selected";
            }
            strResultado += "                   <option value='" + clasificacion.getClasificacion() + "' " + selected + "> " + clasificacion.getClasificacion() + "-" + clasificacion.getClasificacionDescr() + " </option>";
            selected = "";
        }
        strResultado += "               </select>";
        strResultado += "           </td> ";

        strResultado += "       </tr> ";
        strResultado += "       <tr> ";

        strResultado += "           <td>";
        strResultado += "               <div> Compromiso </div>";
        strResultado += "               <select id='selCompromiso' >";
        strResultado += "                   <option value='-1'> -- Seleccione tipo de compromiso -- </option>";
        tipoCompromisoList = metaBean.getTipoCompromiso();
        for (TipoCompromiso tipoCompromisos : tipoCompromisoList) {
            if (movtoOficioMeta.getCompromisoId() == tipoCompromisos.getTipoCompromisoId()) {
                selected = "selected";
            }
            strResultado += "                   <option value='" + tipoCompromisos.getTipoCompromisoId() + "' " + selected + "> " + tipoCompromisos.getTipoCompromisoDes() + " </option>";
            selected = new String();
        }
        strResultado += "               </select>";
        strResultado += "           </td>";

        strResultado += "           <td>";
        strResultado += "               <div> L&iacute;nea PED </div>";
        strResultado += "               <select id='selLineaPedRP' onchange='getLineaSectorialRPreprogramacion()'>";
        strResultado += "                   <option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
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
            strResultado += "                   </option>";
            selected = "";
        }
        strResultado += "               </select>";
        strResultado += "           </td> ";

        strResultado += "           <td>";
        strResultado += "               <div> L&iacute;nea sectorial </div>";
        strResultado += "               <select id='selLineaSectRP'>";
        strResultado += "                   <option value='-1'> -- Seleccione una l&iacute;nea Sectorial -- </option>";
        if (movtoOficioMeta.getLineaSectorialId() == null) {
            movtoOficioMeta.setLineaSectorialId("");
        }
        lineaSectorialList = metaBean.getLineaSectorialByLineaMeta(movtoOficioMeta.getLineaPedId(), year);
        for (Linea lineaS : lineaSectorialList) {
            if (movtoOficioMeta.getLineaSectorialId().equals(lineaS.getLineaId())) {
                selected = "selected";
            }
            strResultado += "                   <option value='" + lineaS.getLineaId() + "' " + selected + " >" + lineaS.getLineaId() + "-" + lineaS.getLinea() + "</option>";
            selected = "";
        }
        strResultado += "               </select>";
        strResultado += "           </td>";
        strResultado += "       </tr>";

        strResultado += "       <tr>";
        strResultado += "           <td>";
        strResultado += "               <div> Ponderaci&oacute;n </div> ";
        strResultado += "               <select id='selPonderacion' value='" + movtoOficioMeta.getPonderacionId() + "'>";
        strResultado += "                   <option value='-1'> -- Seleccione una ponderaci&oacute;n -- </option>";
        if (movtoOficioMeta.getPonderacionId() == null) {
            movtoOficioMeta.setPonderacionId("0");
        }
        if (Integer.parseInt(movtoOficioMeta.getPonderacionId()) == 1) {
            strResultado += "                   <option selected value='1'>";
        } else {
            strResultado += "                   <option value='1'>";
        }
        strResultado += "                       1-Baja";
        strResultado += "                   </option>";
        if (Integer.parseInt(movtoOficioMeta.getPonderacionId()) == 2) {
            strResultado += "                   <option selected value='2'>";
        } else {
            strResultado += "                   <option value='2'>";
        }
        strResultado += "                       2-Media";
        strResultado += "                   </option>";
        if (Integer.parseInt(movtoOficioMeta.getPonderacionId()) == 3) {
            strResultado += "                   <option selected value='3'>";
        } else {
            strResultado += "                   <option value='3'>";
        }
        strResultado += "                       3-Alta";
        strResultado += "                   </option>";
        strResultado += "               </select>";
        strResultado += "           </td>";

        strResultado += "           <td align='left'>";
        strResultado += "               <div> Criterio de transversalidad </div>";
        strResultado += "               <select id='selTrasver' > <option value='-1'> -- Seleccione un criterio -- </option>";
        transversalidadList = metaBean.getTransversalidad();
        for (Transversalidad transv : transversalidadList) {
            if (movtoOficioMeta.getCriterioTransversalidad() == transv.getTransversalidadId()) {
                selected = "selected";
            }
            strResultado += "               <option value='" + transv.getTransversalidadId() + "' " + selected + ">" + transv.getTransversalidadId()
                    + "-" + transv.getTransvesalidad() + "</option>";
            selected = "";
        }
        strResultado += "               </select>";
        strResultado += "           </td>";

        strResultado += "<input type='hidden' id='tipoMetaObra' value='" + movtoOficioMeta.getObra() + "'/>";

        if (tipoMeta.equals("S")) {
            if (movtoOficioMeta.getObra() == null) {
                movtoOficioMeta.setObra(new String());
            }
            strResultado += "           <td>";
            strResultado += "               <div> Inversi&oacute;n P&uacute;blica </div> ";
            if (countAccion > 0) {
                strResultado += "           <select id='selObraP'  onChange='validarTipoMeta()'> ";
                strResultado += "               <option value='-1'>-- Seleccione tipo de meta -- </option> ";
            } else {
                strResultado += "           <select id='selObraP'  > ";
                strResultado += "               <option value='-1'>-- Seleccione tipo de meta -- </option> ";
            }
            if (movtoOficioMeta.getObra().equals("G")) {
                strResultado += "               <option value='G' selected> Gasto corriente </option>";
            } else {
                strResultado += "               <option value='G' > Gasto corriente </option>";
            }
            if (movtoOficioMeta.getObra().equals("I")) {
                strResultado += "               <option value='I' selected> Inversi&oacute;n p&uacute;blica </option>";
            } else {
                strResultado += "               <option value='I' > Inversi&oacute;n p&uacute;blica </option>";
            }
            if (movtoOficioMeta.getObra().equals("A")) {
                strResultado += "               <option value='A' selected> Inversi&oacute;n p&uacute;blica y gasto corriente </option>";
            } else {
                strResultado += "               <option value='A' > Inversi&oacute;n p&uacute;blica y gasto corriente</option>";
            }
            strResultado += "           </td>";
        }
        strResultado += "       <tr>";
        strResultado += "   </table>";

        strResultado += "   <br>";

        strResultado += "    <center> ";
        if (inputAllDisable.equals("")) {
            strResultado += "       <input type='button' value='Guardar' onclick=\"validaDatosMetaTransferencia();\"      /> ";
            strResultado += "       <input type='button' value='Cancelar' onclick=\"fadeOutPopUp('infoMetaAmpRed'); fadeOutPopUp('estimacionMetaAR');\"     /> ";
        } else {
            strResultado += "       <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('infoMetaAmpRed'); fadeInPopUp('estimacionMetaAR');\"     /> ";
        }
        strResultado += "   </center> ";

        strResultado += "</div>";

        strResultado += "<div id='estimacionMetaAR' style='display:none;'> ";

        strResultado += "   <center>";
        strResultado += "       <label>CALENDARIZACI&Oacute;N " + evaluacion.getTipoEvaluacion() + "</label>";
        strResultado += "   </center>";

        strResultado += "   <div class='calenVistaCAR'> ";
        strResultado += "       <table id='tblEstimacion'> ";
        strResultado += "           <thead> ";
        strResultado += "               <tr> ";
        strResultado += "                   <th> Periodo </th> ";
        strResultado += "                   <th> Valor </th> ";
        strResultado += "               </tr> ";
        strResultado += "           </thead>";
        strResultado += "           <tbody>";

        cont = 1;
        tipoC = movtoOficioMeta.getCalculoId();

        if (objParametros.getValidaTodosTrimestre().equals("S")) {

            if (movtoEstimacionMetaList.size() > 0) {
                for (MovOficioEstimacion estimacion : movtoEstimacionMetaList) {
                    inputMonthDisable = "";
                    if (limitMonthDisable >= cont) {
                        inputMonthDisable = "disabled='disabled'";
                    }
                    strResultado += "           <tr>";
                    strResultado += "               <td>" + Utileria.getStringMes(cont) + "</td>";
                    strResultado += "               <td>" + "<div> " + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur='validarFlotanteNewMetaTransferencia(this.value); agregarFormato(\"estimacion" + (cont - 1) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'  " + "      " + inputAllDisable + "   /></div>" + "</td>";
                    strResultado += "           </tr>";
                    cont++;
                }
            } else {
                for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                    inputMonthDisable = "";
                    if (limitMonthDisable >= cont) {
                        inputMonthDisable = "disabled='disabled'";
                    }
                    strResultado += "           <tr>";
                    strResultado += "               <td>" + Utileria.getStringMes(cont) + "</td>";
                    strResultado += "               <td>" + "<div> " + " <input class='estimacion' id='estimacion" + it + "'  maxlength='14' type='text'  onBlur='validarFlotanteNewMetaTransferencia(this.value); agregarFormato(\"estimacion" + it + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'  " + "     " + inputAllDisable + "  /></div>" + "</td >";
                    strResultado += "           </tr>";
                    cont++;
                }
            }

        } else {

            date = metaBean.getResultSQLgetServerDate();
            yearAct = Integer.parseInt(formatYear.format(date.getTime()));
            validaTrimestre = metaBean.getResultSqlGetparametroTrimestre();
            monthAct = Integer.parseInt(formatMonth.format(date.getTime()));
            meses = metaBean.getResultSQLgetMesTrimestreByPeriodo(monthAct, validaTrimestre);
            if (!validaTrimestre) {
                if (meses.length > 0) {
                    monthAct = meses[0];
                } else {
                    monthAct = 1;
                }
            }

            if (movtoEstimacionMetaList.size() > 0) {
                for (MovOficioEstimacion estimacion : movtoEstimacionMetaList) {
                    inputMonthDisable = "";
                    if (limitMonthDisable >= cont) {
                        inputMonthDisable = "disabled='disabled'";
                    }
                    strResultado += "           <tr>";
                    strResultado += "               <td>" + Utileria.getStringMes(cont) + "</td>";
                    strResultado += "               <td>" + "<div> " + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur='validarFlotanteNewMetaTransferencia(this.value); agregarFormato(\"estimacion" + (cont - 1) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'  " + "   " + Utileria.getDisabledMonth(cont, monthAct, meses) + "   " + inputAllDisable + "   /></div>" + "</td>";
                    strResultado += "           </tr>";
                    cont++;
                }
            } else {
                for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                    inputMonthDisable = "";
                    if (limitMonthDisable >= cont) {
                        inputMonthDisable = "disabled='disabled'";
                    }
                    strResultado += "           <tr>";
                    strResultado += "               <td>" + Utileria.getStringMes(cont) + "</td>";
                    strResultado += "               <td>" + "<div> " + " <input class='estimacion' id='estimacion" + it + "'  maxlength='14' type='text'  onBlur='validarFlotanteNewMetaTransferencia(this.value); agregarFormato(\"estimacion" + it + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'  " + "   " + Utileria.getDisabledMonth(cont, monthAct, meses) + "  " + inputAllDisable + "  /></div>" + "</td >";
                    strResultado += "           </tr>";
                    cont++;
                }
            }

        }

        strResultado += "           </tbody>";
        strResultado += "       </table>";
        strResultado += "   </div>";

        strResultado += "   <br>";

        strResultado += "   <center>";
        strResultado += "       <label> Cantidad </label> &nbsp;&nbsp;";
        strResultado += "       <input type='text' id='inTxtTotalEst' value='" + metaBean.getValorCalculadoAmpliacionReduccion(movtoEstimacionMetaList, tipoC) + "' maxlength='14' onfocus='selectEnteros(\"inpTxtEne\")' " + "disabled style='width: 100px;text-align: right;' /> ";
        strResultado += "   </center>";

        strResultado += "   <script type='text/javascript'> ";
        strResultado += "       $('.estimacion').keydown(function (e) { ";
        strResultado += "           if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) { ";
        strResultado += "               return; ";
        strResultado += "           }  ";
        strResultado += "           if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {  ";
        strResultado += "               e.preventDefault();  ";
        strResultado += "           }                 ";
        strResultado += "	});              ";
        strResultado += "   </script> ";

        strResultado += "   <br>";

        strResultado += "   <center>";
        if (inputAllDisable.equals("")) {
            strResultado += "       <input type='button' value='Guardar' onclick=\"actualizarTransferenciaMeta();\"/> ";
            strResultado += "       <input type='button' value='Cancelar' onclick=\"fadeOutPopUp('infoMetaAmpRed'); fadeOutPopUp('estimacionMetaAR');\"     /> ";
        } else {
            strResultado += "       <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('infoMetaAmpRed'); fadeOutPopUp('estimacionMetaAR');\"     /> ";
        }
        strResultado += "   </center>";

        strResultado += "</div> ";

        strResultado += "   <input type='hidden' id='identificador' value='" + identificador + "' /> ";
        strResultado += "   <input type='hidden' id='ramoId' value='" + ramoId + "' /> ";
        strResultado += "   <input type='hidden' id='metaId' value='" + metaId + "' /> ";
        strResultado += "   <input type='hidden' id='programaId' value='" + programaId + "' /> ";
        strResultado += "   <input type='hidden' id='proyectoId' value='" + proyectoId + "' /> ";
        strResultado += "   <input type='hidden' id='tipoProy' value='" + tipoProyecto + "' /> ";
        strResultado += "   <input type='hidden' id='num_meses' value='" + evaluacion.getNumMeses() + "' /> ";
        strResultado += "   <input type='hidden' id='tipoTransferencia' value='" + tipoTransferencia + "' /> ";

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
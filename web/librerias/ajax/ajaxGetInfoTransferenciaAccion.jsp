<%-- 
    Document   : ajaxGetInfoTransferenciaAccion
    Created on : Jan 15, 2016, 12:00:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Parametros"%>
<%@page import="gob.gbc.aplicacion.ParametrosBean"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="gob.gbc.entidades.MovimientoOficioAccion"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.Localidad"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page import="gob.gbc.entidades.Linea"%>
<%@page import="gob.gbc.entidades.Municipio"%>
<%@page import="gob.gbc.entidades.GrupoPoblacional"%>
<%@page import="gob.gbc.entidades.UnidadMedida"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="gob.gbc.entidades.TipoCalculo"%>
<%@page import="gob.gbc.entidades.TipoAccion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    request.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    Meta metaAct = null;
    MetaBean metaBean = null;
    AccionBean accionBean = null;
    ParametrosBean parametrosBean = null;
    Evaluacion evaluacion = new Evaluacion();
    TransferenciaBean transferenciaBean = null;
    Parametros objParametros = new Parametros();
    MovimientosTransferencia transferencia = null;
    MovimientoOficioMeta movtoOficioMetaTemp = new MovimientoOficioMeta();
    MovimientoOficioAccion movtoOficioAccion = new MovimientoOficioAccion();

    List<Linea> lineaList = new ArrayList<Linea>();
    List<TransferenciaAccion> movtoAccionesList = null;
    List<Linea> lineaSectorialList = new ArrayList<Linea>();
    List<Localidad> localidadList = new ArrayList<Localidad>();
    List<Municipio> municipioList = new ArrayList<Municipio>();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<TipoAccion> tipoAccionList = new ArrayList<TipoAccion>();
    List<TipoCalculo> tipoCalculoList = new ArrayList<TipoCalculo>();
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    List<UnidadMedida> unidadMedidaList = new ArrayList<UnidadMedida>();
    List<GrupoPoblacional> grupoList = new ArrayList<GrupoPoblacional>();
    List<TransferenciaMeta> movtoMetasList = new ArrayList<TransferenciaMeta>();
    List<Transferencia> movimientosTransferenciaRecibenList = new ArrayList<Transferencia>();
    List<MovOficioEstimacion> movtoEstimacionMetaListAct = new ArrayList<MovOficioEstimacion>();
    List<MovOficioEstimacion> movtoEstimacionMetaListTemp = new ArrayList<MovOficioEstimacion>();
    List<MovOficioAccionEstimacion> movtoEstimacionAccionList = new ArrayList<MovOficioAccionEstimacion>();

    Date date;
    DateFormat formatMonth = new SimpleDateFormat("MM");
    DateFormat formatYear = new SimpleDateFormat("yyyy");
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    String tipoC = new String();
    String inputAllDisable = "";
    String status = new String();
    String ramoId = new String();
    String disReq = new String();
    String numObra = new String();
    String inputMonthDisable = "";
    String compDis = new String();
    String selected = new String();
    String tipoProy = new String();
    String ramoDescr = new String();
    String metaDescr = new String();
    String programaId = new String();
    String obraAccion = new String();
    String disConsulta = new String();
    String strResultado = new String();
    String programaDescr = new String();
    String proyectoDescr = new String();
    String tipoCompromiso = new String();
    String tipoDependencia = new String();
    String tipoTransferencia = new String();

    int yearAct;
    int cont = 1;
    int year = 0;
    int monthAct;
    int metaId = 0;
    int contReq = 0;
    int consulta = 0;
    int accionId = 0;
    int optAccion = 0;
    int proyectoId = 0;
    int identificador = -1;
    int meses[] = new int[3];
    int limitMonthDisable = 1;

    Double calOriginal = 0.0;
    Double sumRecalendarizacion = 0.0;

    boolean cierrePPTO = false;
    boolean validaTrimestre = false;
    boolean autorizacion = false;
    boolean metaHabilitada = true;

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
            if ((!status.equalsIgnoreCase("X")) && (!status.equalsIgnoreCase("R")) && (!status.equalsIgnoreCase("K")) && ((status.equalsIgnoreCase("T")) && autorizacion)) {
                inputAllDisable = "disabled=''";
            }
        }

        if (request.getParameter("tipoTransferencia") != null && !request.getParameter("tipoTransferencia").equals("")) {
            tipoTransferencia = request.getParameter("tipoTransferencia");
        }

        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            transferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            movtoMetasList = transferencia.getTransferenciaMetaList();
            movtoAccionesList = transferencia.getTransferenciaAccionList();
            if (identificador != -1) {
                for (TransferenciaAccion movtoAccionListTemp : movtoAccionesList) {
                    if (movtoAccionListTemp.getIdentificador() == identificador) {
                        movtoOficioAccion = movtoAccionListTemp.getMovOficioAccion();
                        movtoEstimacionAccionList = movtoAccionListTemp.getMovOficioAccionEstList();
                    }
                }
            }
        }

        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);

        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

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
        if (metaId > 0) {
            metaAct = metaBean.getMetaByRamoMetaYear(ramoId, metaId, year);
        }

        if (transferencia != null) {
            for (TransferenciaMeta meta : transferencia.getTransferenciaMetaList()) {
                if (meta.getMovOficioMeta().getMetaId() == metaId && meta.getMovOficioMeta().getRamoId().equals(ramoId)) {
                    for (MovOficioEstimacion estimacion : meta.getMovOficioEstimacion()) {
                        sumRecalendarizacion += estimacion.getValor();
                    }
                    if (sumRecalendarizacion == 0) {
                        metaHabilitada = false;
                    }
                }
            }
        }
        if (metaHabilitada) {
            if (metaAct == null) {

                for (TransferenciaMeta movtoMetaListTemp : movtoMetasList) {
                    movtoOficioMetaTemp = movtoMetaListTemp.getMovOficioMeta();
                    if ((ramoId.equalsIgnoreCase(movtoOficioMetaTemp.getRamoId())) && (metaId == movtoOficioMetaTemp.getMetaId()) && (year == movtoOficioMetaTemp.getYear())) {
                        ramoDescr = metaBean.getRamoDescrByRamoYear(ramoId, year);
                        ramoDescr = ramoId + " - " + ramoDescr;
                        programaId = movtoOficioMetaTemp.getPrgId();
                        programaDescr = metaBean.getProgramaDescrByProgramaYear(programaId, year);
                        programaDescr = programaId + " - " + programaDescr;
                        proyectoId = movtoOficioMetaTemp.getProyId();
                        tipoProy = movtoOficioMetaTemp.getTipoProy();
                        proyectoDescr = metaBean.getProyectoDescrByProyectoTipoProyYear(proyectoId, tipoProy, year);
                        proyectoDescr = tipoProy + proyectoId + " - " + proyectoDescr;
                        metaDescr = metaId + " - " + movtoOficioMetaTemp.getMetaDescr();
                        tipoCompromiso = accionBean.getTipoCompromisoByTipoCompromiso(movtoOficioMetaTemp.getCompromisoId());
                        movtoEstimacionMetaListTemp = movtoMetaListTemp.getMovOficioEstimacion();
                        tipoC = movtoOficioMetaTemp.getCalculoId();
                    }
                }

            } else {

                ramoId = metaAct.getRamo();
                ramoDescr = ramoId + " - " + metaAct.getRamoDescr();
                programaId = metaAct.getPrograma();
                programaDescr = programaId + " - " + metaAct.getProgramaDescr();
                proyectoId = metaAct.getProyecto();
                tipoProy = metaAct.getTipoProyecto();
                proyectoDescr = metaAct.getTipoProyecto() + metaAct.getProyecto() + " - " + metaAct.getProyectoDescr();
                metaId = metaAct.getMetaId();
                metaDescr = metaId + " - " + metaAct.getMeta();
                tipoCompromiso = accionBean.getTipoCompromisoMeta(year, ramoId, metaId);
                estimacionList = metaBean.getEstimacionByMeta(year, ramoId, metaId);
                tipoC = metaBean.getTipoCalculo(year, ramoId, programaId, proyectoId, metaId);

            }

            strResultado += "<div id='infoAccion' style=''> ";
            strResultado += "   <div id='' style=''> ";
            strResultado += "       <div> ";

            strResultado += "           <table id='informacionAccion' cellspacing='10'>";
            strResultado += "               <tr>";
            strResultado += "                   <td> Ramo </td>";
            strResultado += "                   <td>" + ramoDescr + "</td>";
            strResultado += "               </tr>";
            strResultado += "               <tr>";
            strResultado += "                   <td> Programa </td>";
            strResultado += "                   <td>" + programaDescr + "</td>";
            strResultado += "               </tr>";
            strResultado += "               <tr> ";
            strResultado += "                   <td> Proy./Act. </td>";
            strResultado += "                   <td>" + proyectoDescr + "</td> ";
            strResultado += "               </tr>";
            strResultado += "               <tr> ";
            strResultado += "                   <td> Meta </td>";
            strResultado += "                   <td>" + metaDescr + "</td> ";
            strResultado += "               </tr>";
            strResultado += "           </table>";

            strResultado += "           <table>";
            if (movtoOficioAccion.getAccionDescr() == null) {
                movtoOficioAccion.setAccionDescr("");
            }
            strResultado += "               <tr> ";
            strResultado += "                   <td> <p id='descrAccion'>Descripci&oacute;n </p> </td> ";
            strResultado += "                   <td> <textArea id='txtAreaAccion' class='no-enter' maxlength='300' style='text-transform:uppercase;' >" + movtoOficioAccion.getAccionDescr() + "</textArea></td>";
            strResultado += "               </tr>";
            strResultado += "           </table>";

            strResultado += "           <table> ";
            strResultado += "               <tr> ";
            strResultado += "                   <td> ";
            strResultado += "                       <div> Unidad ejecutora </div> ";
            strResultado += "                       <select id='selUnidadEj' " + disConsulta + " " + disReq + ">";
            strResultado += "                           <option value='-1' " + "" + "> -- Seleccione una unidad -- </option>";
            dependenciaList = accionBean.getDependencia(ramoId, year, programaId);
            for (Dependencia dependencia : dependenciaList) {
                if (dependencia.getDeptoId().equals(movtoOficioAccion.getDeptoId())) {
                    selected = "selected";
                }
                strResultado += "                       <option value='" + dependencia.getDeptoId() + "' " + selected + ">" + dependencia.getDeptoId() + "-" + dependencia.getDepartamento() + "</option>";
                selected = "";
            }
            strResultado += "                        </select> ";
            strResultado += "                   </td>";

            strResultado += "                   <td> ";
            strResultado += "                       <div> Unidad de medida </div> ";
            strResultado += "                       <select id='selMedida' " + "" + ">";
            strResultado += "                           <option value='-1'> -- Seleccione una medida -- </option>";
            unidadMedidaList = accionBean.getUnidadMedida(year);
            for (UnidadMedida unidad : unidadMedidaList) {
                if (unidad.getUnidadMedidaId() == movtoOficioAccion.getClaveMedida()) {
                    selected = "selected";
                }
                strResultado += "                       <option value='" + unidad.getUnidadMedidaId() + "' " + selected + ">" + unidad.getUnidadMedida() + "</option>";
                selected = "";
            }
            strResultado += "                       </select> ";
            strResultado += "                   </td>";

            for (TransferenciaMeta movtoMetaListTemp : movtoMetasList) {
                if (movtoMetaListTemp.getMovOficioMeta().getMetaId() == metaId) {
                    tipoCompromiso = accionBean.getTipoCompromisoByTipoCompromiso(movtoMetaListTemp.getMovOficioMeta().getCompromisoId());
                }
            }

            if (tipoCompromiso.equals("N")) {
                compDis = "style='display:none;'";
            }

            strResultado += "                   <td> ";
            strResultado += "                       <div " + compDis + " > Grupo poblacional </div>";
            strResultado += "                       <select id='selGrupo' " + compDis + ">";
            strResultado += "                           <option value='-1'> --Seleccione un grupo -- </option> ";
            grupoList = accionBean.getGrupoPoblacional();
            for (GrupoPoblacional grupo : grupoList) {
                if (grupo.getGrupoPobId() == movtoOficioAccion.getGrupoPoblacional()) {
                    selected = "selected";
                }
                strResultado += "                       <option value='" + grupo.getGrupoPobId() + "' " + selected + ">" + grupo.getGripoPoblacional() + "</option>";
                selected = "";
            }
            strResultado += "                       </select>";
            strResultado += "                   </td> ";

            strResultado += "               </tr>";
            strResultado += "               <tr> ";

            strResultado += "                   <td> ";
            strResultado += "                       <div> Municipio </div> ";
            strResultado += "                       <select id='selMunicipio' onchange='getLocalidad()' " + "" + " " + disReq + ">";
            strResultado += "                           <option value='-1'> --Seleccione un municipio-- </option>";

            if (movtoOficioAccion.getMunicipio() == null) {
                movtoOficioAccion.setMunicipio("");
            }
            municipioList = accionBean.getMunicipios();
            for (Municipio municipio : municipioList) {
                if (String.valueOf(municipio.getMunicipioId()).equals(movtoOficioAccion.getMunicipio())) {
                    selected = "selected";
                }
                strResultado += "                       <option value='" + municipio.getMunicipioId() + "' " + selected + ">" + municipio.getMunicipioId() + "-" + municipio.getMunicipio() + "</option>";
                selected = "";
            }
            strResultado += "                        </select>";
            strResultado += "                   </td>";

            strResultado += "                   <td> ";
            strResultado += "                       <div> Localidad </div>";
            strResultado += "                           <select id='selLocalidad' " + "" + " " + disReq + ">";
            strResultado += "                               <option value='-1'> -- Seleccione una localidad -- </option>";
            if (!movtoOficioAccion.getMunicipio().equals("")) {
                localidadList = accionBean.getlocalidades(Integer.parseInt(movtoOficioAccion.getMunicipio()));
            }
            for (Localidad localidad : localidadList) {
                if (localidad.getLocalidadId() == movtoOficioAccion.getLocalidad()) {
                    selected = "selected";
                }
                strResultado += "                           <option value='" + localidad.getLocalidadId() + "' " + selected + ">" + localidad.getLocalidadId() + "-" + localidad.getLocalidad() + "</option>";
                selected = "";
            }
            strResultado += "                           </select> ";
            strResultado += "                   </td>";

            strResultado += "       <td> ";
            strResultado += "           <div> Tipo  de c&aacute;lculo</div>";
            tipoCalculoList = accionBean.getTipoCalculo();
            if (movtoOficioAccion.getCalculo() == null) {
                movtoOficioAccion.setCalculo("");
            }
            strResultado += "           <select id='selCalculo' " + " onChange='validarFlotanteNew(\"0\")' " + ">";
            strResultado += "               <option value='-1' > -- Seleccione tipo de c&aacute;lculo --";
            for (TipoCalculo tipoCalculo : tipoCalculoList) {
                if (movtoOficioAccion.getCalculo().equals(tipoCalculo.getAbrev())) {
                    selected = "selected";
                }
                strResultado += "           <option value='" + tipoCalculo.getAbrev() + "' " + selected + ">" + tipoCalculo.getTipoCalculoId() + "-" + tipoCalculo.getTipoCalculo() + "</option>";
                selected = "";
            }
            strResultado += "           </select> ";
            strResultado += "       </td> ";

            strResultado += "   </tr>";
            strResultado += "   <tr> ";

            strResultado += "       <td> ";
            strResultado += "           <div> L&iacute;nea PED </div>";
            strResultado += "           <select id='selLineaPedRP' " + "" + " onchange='getLineaSectorialRPreprogramacion()'>";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea PED -- </option>";
            if (movtoOficioAccion.getLinea() == null) {
                movtoOficioAccion.setLinea("");
            }
            lineaList = accionBean.getLineaAccion(year, ramoId, programaId);
            for (Linea linea : lineaList) {
                if (linea.getLineaId().equalsIgnoreCase(movtoOficioAccion.getLinea())) {
                    selected = "selected";
                }
                strResultado += "           <option value='" + linea.getLineaId() + "' " + selected + ">" + linea.getLineaId() + "-" + linea.getLinea() + "</option>";
                selected = "";
            }
            strResultado += "           </select> ";
            strResultado += "       </td>";

            strResultado += "       <td> ";
            strResultado += "           <div> L&iacute;nea sectorial </div>";
            strResultado += "           <select id='selLineaSectRP' " + "" + "> ";
            strResultado += "               <option value='-1'> -- Seleccione una l&iacute;nea sectorial -- </option>";
            if (movtoOficioAccion.getLineaSectorial() == null) {
                movtoOficioAccion.setLineaSectorial("");
            }
            lineaSectorialList = accionBean.getLineaSectorialByLineaAccion(movtoOficioAccion.getLinea(), year);
            for (Linea lineaSect : lineaSectorialList) {
                if (lineaSect.getLineaId().equalsIgnoreCase(movtoOficioAccion.getLineaSectorial())) {
                    selected = "selected";
                }
                strResultado += "       <option value='" + lineaSect.getLineaId() + "' " + selected + "> " + lineaSect.getLineaId() + "-" + lineaSect.getLinea() + " </option>";
                selected = "";
            }
            strResultado += "           </select> ";
            strResultado += "       </td>";

            strResultado += "       <td " + compDis + "> ";
            strResultado += "           <div id='divGenero'> ";
            strResultado += "               <div> Mujeres <input type='text' id='accMuj' value='" + movtoOficioAccion.getBenefMujer() + "' " + "" + "/> </div>";
            strResultado += "               <div> Hombres <input type='text' id='accHom' value='" + movtoOficioAccion.getBenefHombre() + "' " + "" + "/> </div>";
            strResultado += "           </div> ";
            strResultado += "       </td> ";

            strResultado += "   </tr>";

            strResultado += "</table>";

            strResultado += "    <center> ";
            if (inputAllDisable.equals("")) {
                strResultado += "        <input type='button' value='Guardar' onclick=\"validarGuardarInfoTransferenciaAccion();\" /> ";
                strResultado += "        <input type='button' value='Cancelar' onclick=\"fadeOutPopUp('infoAccion'); fadeOutPopUp('estimacionMeta');\" /> ";
            } else {
                strResultado += "        <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('infoAccion'); fadeInPopUp('estimacionMeta');\" /> ";
            }
            strResultado += "    </center> ";

            strResultado += "  </div > ";
            strResultado += "</div> ";
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

            strResultado += "<div id='estimacionMeta' style='display:none;'> ";
            strResultado += "<div id='' style=''> ";
            strResultado += "   <div> ";

            strResultado += "<table id='informacionAccion' cellspacing='10'>";
            strResultado += "   <tr>";
            strResultado += "       <td> Ramo </td>";
            strResultado += "       <td>" + ramoDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr>";
            strResultado += "       <td> Programa </td>";
            strResultado += "       <td>" + programaDescr + "</td>";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Proy./Act. </td>";
            strResultado += "       <td>" + proyectoDescr + "</td> ";
            strResultado += "   </tr>";
            strResultado += "   <tr> ";
            strResultado += "       <td> Meta </td>";
            strResultado += "       <td>" + metaDescr + "</td> ";
            strResultado += "   </tr>";
            strResultado += "</table>";

            strResultado += "<center>";
            strResultado += "   <label>CALENDARIZACI&Oacute;N DE META</label>";
            strResultado += "</center>";

            strResultado += "<div class='calenVistaC'> ";
            if (metaAct == null) {

                if (movtoEstimacionMetaListTemp.size() > 0) {
                    for (MovOficioEstimacion estimacion : movtoEstimacionMetaListTemp) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                        cont++;
                    }
                    calOriginal = metaBean.getValorCalculadoMovOficio(movtoEstimacionMetaListTemp, tipoC);
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='' disabled/></div>";
                        cont++;
                    }
                    calOriginal = 0.0;
                }
            } else {
                if (estimacionList.size() > 0) {
                    for (Estimacion estimacion : estimacionList) {
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                        cont++;
                    }
                    calOriginal = metaBean.getValorCalculado(estimacionList, tipoC);
                } else {
                    if (movtoEstimacionMetaListTemp.size() > 0) {
                        for (MovOficioEstimacion estimacion : movtoEstimacionMetaListTemp) {
                            strResultado += "<div> " + Utileria.getStringMes(cont) + " <input type='text' value='" + numberF.format(estimacion.getValor()) + "' disabled/></div>";
                            cont++;
                        }
                        calOriginal = metaBean.getValorCalculadoMovOficio(movtoEstimacionMetaListTemp, tipoC);
                    }
                }
            }

            strResultado += "<br/>";
            strResultado += "<div class='muestra-cal' style='width: 100% !important;' ><label>Calendarizaci&oacute;n de la Meta: </label> <input id='calOriginal' style='text-align:right; font-size: 12px' disabled value='" + numberF.format(calOriginal) + "'></input></div> ";

            strResultado += "</div>";

            strResultado += "<br/>";
            strResultado += "<br/>";

            tipoC = movtoOficioAccion.getCalculo();
            strResultado += "<center>";
            strResultado += "   <label>CALENDARIZACI&Oacute;N DE ACCI&Oacute;N POR MES </label>";
            strResultado += "</center>";

            strResultado += "<div class='calenVistaC'> ";
            cont = 1;

            if (objParametros.getValidaTodosTrimestre().equals("S")) {

                if (movtoEstimacionAccionList.size() > 0) {
                    for (MovOficioAccionEstimacion estimacion : movtoEstimacionAccionList) {
                        inputMonthDisable = "";
                        if (limitMonthDisable >= cont) {
                            inputMonthDisable = "disabled='disabled'";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur='validarFlotanteNew(this.value); agregarFormato(\"estimacion" + (cont - 1) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'  " + "      " + inputAllDisable + "   /></div>";
                        cont++;
                    }
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        inputMonthDisable = "";
                        if (limitMonthDisable >= cont) {
                            inputMonthDisable = "disabled='disabled'";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur='validarFlotanteNew(this.value); agregarFormato(\"estimacion" + it + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'  " + "     " + inputAllDisable + "  /></div>";
                        cont++;
                    }
                }

            } else {

                date = accionBean.getResultSQLgetServerDate();
                yearAct = Integer.parseInt(formatYear.format(date.getTime()));
                validaTrimestre = metaBean.getResultSqlGetparametroTrimestre();
                monthAct = Integer.parseInt(formatMonth.format(date.getTime()));
                meses = accionBean.getResultSQLgetMesTrimestreByPeriodo(monthAct, validaTrimestre);
                if (!validaTrimestre) {
                    if (meses.length > 0) {
                        monthAct = meses[0];
                    } else {
                        monthAct = 1;
                    }
                }

                if (movtoEstimacionAccionList.size() > 0) {
                    for (MovOficioAccionEstimacion estimacion : movtoEstimacionAccionList) {
                        inputMonthDisable = "";
                        if (limitMonthDisable >= cont) {
                            inputMonthDisable = "disabled='disabled'";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + (cont - 1) + "' value='" + numberF.format(estimacion.getValor()) + "' maxlength='14' type='text'  onBlur='validarFlotanteNew(this.value); agregarFormato(\"estimacion" + (cont - 1) + "\"); '   onkeyup='validaMascara(\"estimacion" + (cont - 1) + "\")'  " + "   " + Utileria.getDisabledMonth(cont, monthAct, meses) + "   " + inputAllDisable + "   /></div>";
                        cont++;
                    }
                } else {
                    for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                        inputMonthDisable = "";
                        if (limitMonthDisable >= cont) {
                            inputMonthDisable = "disabled='disabled'";
                        }
                        strResultado += "<div> " + Utileria.getStringMes(cont) + " <input class='estimacion' id='estimacion" + it + "' value='' maxlength='14' type='text'  onBlur='validarFlotanteNew(this.value); agregarFormato(\"estimacion" + it + "\"); '   onkeyup='validaMascara(\"estimacion" + it + "\")'  " + "   " + Utileria.getDisabledMonth(cont, monthAct, meses) + "  " + inputAllDisable + "  /></div>";
                        cont++;
                    }
                }

            }

            strResultado += "</div>";

            strResultado += "			<script type='text/javascript'> ";
            strResultado += "				$('.estimacion').keydown(function (e) { ";
            strResultado += "					if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 || (e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) || (e.keyCode >= 35 && e.keyCode <= 40)) { ";
            strResultado += "						return; ";
            strResultado += "					}  ";
            strResultado += "					if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {  ";
            strResultado += "						e.preventDefault();  ";
            strResultado += "					}                 ";
            strResultado += "				});              ";
            strResultado += "			</script> ";

            strResultado += "<center>";
            strResultado += "   <label> Cantidad </label> &nbsp;&nbsp;";
            strResultado += "   <br/>";
            strResultado += "   <input type='text' id='inTxtTotalEst' value='" + numberF.format(accionBean.getValorCalculadoAmpliacionReduccion(movtoEstimacionAccionList, tipoC)) + "' " + "disabled style='width: 250px;text-align: right;'/> ";
            strResultado += "</center>";

            strResultado += "<center>";
            if (inputAllDisable.equals("")) {
                strResultado += "        <input type='button' value='Guardar' onclick=\"actualizarTransferenciaAccion();\" /> ";
                strResultado += "        <input type='button' value='Cancelar' onclick=\"fadeOutPopUp('infoAccion'); fadeOutPopUp('estimacionMeta');\" /> ";
            } else {
                strResultado += "        <input type='button' value='Cerrar' onclick=\"fadeOutPopUp('infoAccion'); fadeOutPopUp('estimacionMeta');\" /> ";
            }
            strResultado += "        <input type='hidden' id='identificador' value='" + identificador + "' /> ";
            strResultado += "        <input type='hidden' id='ramoId' value='" + ramoId + "' /> ";
            strResultado += "        <input type='hidden' id='metaId' value='" + metaId + "' /> ";
            strResultado += "        <input type='hidden' id='accionId' value='" + accionId + "' /> ";
            strResultado += "        <input type='hidden' id='programaId' value='" + programaId + "' /> ";
            strResultado += "        <input type='hidden' id='proyectoId' value='" + proyectoId + "' /> ";
            strResultado += "        <input type='hidden' id='tipoProy' value='" + tipoProy + "' /> ";
            strResultado += "        <input type='hidden' id='tipoTransferencia' value='" + tipoTransferencia + "' /> ";
            strResultado += "    </center> ";

            strResultado += "   </div > ";
            strResultado += "</div> ";
            strResultado += "</div> ";
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
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
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
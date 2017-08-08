<%-- 
    Document   : ajaxActualizarTransferenciaRecalendarizacionAccion
    Created on : Jan 15, 2016, 11:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.MovimientoOficioAccion"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    AccionBean accionBean = null;
    Evaluacion evaluacion = new Evaluacion();
    MovimientosTransferencia transferencia = new MovimientosTransferencia();
    MovimientoOficioAccion movtoOficioAccion = new MovimientoOficioAccion();

    List<TransferenciaMeta> movtoMetasList = null;
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<TransferenciaAccion> movtoAccionesList = new ArrayList<TransferenciaAccion>();
    List<Transferencia> movimientosTransferenciaRecibenList = new ArrayList<Transferencia>();
    List<MovOficioAccionEstimacion> movtoEstimacionAccionList = new ArrayList<MovOficioAccionEstimacion>();

    String arrValores[];
    String linea = new String();
    String ramoId = new String();
    String funcion = new String();
    String calculo = new String();
    String tipoProy = new String();
    String finalidad = new String();
    String cadValores = new String();
    String subFuncion = new String();
    String programaId = new String();
    String accionDescr = new String();
    String selUnidadEj = new String();
    String ponderacion = new String();
    String selMunicipio = new String();
    String strResultado = new String();
    String clasificacion = new String();
    String lineaSectorial = new String();
    String tipoDependencia = new String();

    int cont = 1;
    int year = 0;
    int metaId = 0;
    int accMuj = -1;
    int accHom = -1;
    int accionId = 0;
    int selGrupo = -1;
    int contFallas = 0;
    int contValores = 0;
    int compromiso = -1;
    int proyectoId = -1;
    int unidadMedida = -1;
    int selLocalidad = -1;
    int identificador = -1;
    int objIdentificado = 0;
    int contForEvalucion = 0;
    int transversalidad = -1;
    int maxIdentificador = 0;
    int minIdentificador = 0;

    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    Double valor = 0.0;

    boolean resultadoAct = false;

    double sumRecalendarizacion = 0.0;
    double sumEstimacion = 0.0;
    double calPropuesta = 0.0;
    double calOriginal = 0.0;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }

        if (request.getParameter("ramoId") != null && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }

        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }

        if (request.getParameter("accionId") != null && !request.getParameter("accionId").equals("")) {
            accionId = Integer.parseInt((String) request.getParameter("accionId"));
        }
        if (request.getParameter("accionDescr") != null && !request.getParameter("accionDescr").equals("")) {
            accionDescr = (String) request.getParameter("accionDescr");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt((String) request.getParameter("identificador"));
        }

        if (request.getParameter("valores") != null && !request.getParameter("valores").equals("")) {
            cadValores = (String) request.getParameter("valores");
        }

        if (request.getParameter("unidadMedida") != null && !request.getParameter("unidadMedida").equals("")) {
            unidadMedida = Integer.parseInt((String) request.getParameter("unidadMedida"));
        }

        if (request.getParameter("calculo") != null && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }

        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = (String) request.getParameter("programaId");
        }

        if (request.getParameter("tipoProy") != null && !request.getParameter("tipoProy").equals("")) {
            tipoProy = (String) request.getParameter("tipoProy");
        }

        if (request.getParameter("proyectoId") != null && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt((String) request.getParameter("proyectoId"));
        }

        if (request.getParameter("linea") != null && !request.getParameter("linea").equals("")) {
            linea = (String) request.getParameter("linea");
        }

        if (request.getParameter("lineaSectorial") != null && !request.getParameter("lineaSectorial").equals("")) {
            lineaSectorial = (String) request.getParameter("lineaSectorial");
        }

        if (request.getParameter("selLocalidad") != null && !request.getParameter("selLocalidad").equals("")) {
            selLocalidad = Integer.parseInt((String) request.getParameter("selLocalidad"));
        }

        if (request.getParameter("selGrupo") != null && !request.getParameter("selGrupo").equals("")) {
            selGrupo = Integer.parseInt((String) request.getParameter("selGrupo"));
        }

        if (request.getParameter("accMuj") != null && !request.getParameter("accMuj").equals("")) {
            accMuj = Integer.parseInt((String) request.getParameter("accMuj"));
        }

        if (request.getParameter("accHom") != null && !request.getParameter("accHom").equals("")) {
            accHom = Integer.parseInt((String) request.getParameter("accHom"));
        }

        if (request.getParameter("selUnidadEj") != null && !request.getParameter("selUnidadEj").equals("")) {
            selUnidadEj = (String) request.getParameter("selUnidadEj");
        }

        if (request.getParameter("selMunicipio") != null && !request.getParameter("selMunicipio").equals("")) {
            selMunicipio = (String) request.getParameter("selMunicipio");
        }

        if (request.getParameter("sumRecalendarizacion") != null && !request.getParameter("sumRecalendarizacion").equals("")) {
            sumRecalendarizacion = new Double((String) request.getParameter("sumRecalendarizacion"));
        }
        if (request.getParameter("sumEstimacion") != null && !request.getParameter("sumEstimacion").equals("")) {
            sumEstimacion = new Double((String) request.getParameter("sumEstimacion"));
        }

        if (request.getParameter("calPropuesta") != null && !request.getParameter("calPropuesta").equals("")) {
            calPropuesta = new Double((String) request.getParameter("calPropuesta"));
        }

        if (request.getParameter("calOriginal") != null && !request.getParameter("calOriginal").equals("")) {
            calOriginal = new Double((String) request.getParameter("calOriginal"));
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

        maxIdentificador = 0;
        minIdentificador = 0;
        contForEvalucion = 0;
        if (movtoAccionesList.size() > 0) {
            for (TransferenciaAccion movtoAccionListTemp : movtoAccionesList) {
                if (contForEvalucion == 0) {
                    minIdentificador = movtoAccionListTemp.getIdentificador();
                    maxIdentificador = movtoAccionListTemp.getIdentificador();
                }
                if (maxIdentificador < movtoAccionListTemp.getIdentificador()) {
                    maxIdentificador = movtoAccionListTemp.getIdentificador();
                }
                if (minIdentificador > movtoAccionListTemp.getIdentificador()) {
                    minIdentificador = movtoAccionListTemp.getIdentificador();
                }
                contForEvalucion++;
            }
        }

        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);

        evaluacion = accionBean.getResultSQLGetEvaluacionMeta(year);

        arrValores = new String[cadValores.split(",").length];
        arrValores = cadValores.split(",");

        if (movtoEstimacionAccionList.size() > 0) {
            for (MovOficioAccionEstimacion reprogramacion : movtoEstimacionAccionList) {
                reprogramacion.setAccion(accionId);
                reprogramacion.setMeta(metaId);
                reprogramacion.setRamo(ramoId);
                reprogramacion.setYear(year);
                reprogramacion.setPrograma(accionBean.getResultSQLgetProgramaByRamoMeta(year, ramoId, metaId));
                if (!arrValores[contValores].trim().equals("")) {
                    valor = Double.parseDouble(arrValores[contValores].trim());
                    reprogramacion.setPeriodo((contValores + 1));
                    reprogramacion.setValor(valor);
                } else {
                    valor = 0.0;
                    reprogramacion.setPeriodo((contValores + 1));
                    reprogramacion.setValor(valor);
                }
                contValores++;
            }
        } else {
            for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                MovOficioAccionEstimacion reprogramacion = new MovOficioAccionEstimacion();
                reprogramacion.setAccion(accionId);
                reprogramacion.setMeta(metaId);
                reprogramacion.setRamo(ramoId);
                reprogramacion.setYear(year);
                reprogramacion.setPrograma(accionBean.getResultSQLgetProgramaByRamoMeta(year, ramoId, metaId));
                if (!arrValores[it].trim().equals("")) {
                    valor = Double.parseDouble(arrValores[it].trim());
                    reprogramacion.setPeriodo((contValores + 1));
                    reprogramacion.setValor(valor);
                } else {
                    valor = 0.0;
                    reprogramacion.setPeriodo((contValores + 1));
                    reprogramacion.setValor(valor);
                }
                movtoEstimacionAccionList.add(reprogramacion);
                contValores++;
            }
        }

        MovimientoOficioAccion accionInfo = new MovimientoOficioAccion();

        accionInfo.setNvaCreacion("N");
        accionInfo.setYear(year);
        accionInfo.setRamoId(ramoId);
        accionInfo.setMetaId(metaId);
        accionInfo.setAccionId(accionId);
        accionInfo.setProgramaId(accionBean.getResultSQLgetProgramaByRamoMeta(year, ramoId, metaId));
        accionInfo.setAccionDescr(accionDescr);
        accionInfo.setProgramaId(programaId);
        accionInfo.setProyectoId(proyectoId);
        accionInfo.setTipoProy(tipoProy);
        accionInfo.setClaveMedida(unidadMedida);
        accionInfo.setCalculo(calculo);
        accionInfo.setLinea(linea);
        accionInfo.setObra(movtoOficioAccion.getObra());

        if (lineaSectorial.equals("-1")) {
            lineaSectorial = new String();
        }
        accionInfo.setLineaSectorial(lineaSectorial);
        accionInfo.setLocalidad(selLocalidad);
        if (selGrupo != -1) {
            accionInfo.setGrupoPoblacional(selGrupo);
        }
        accionInfo.setBenefHombre(accHom);
        accionInfo.setBenefMujer(accMuj);
        accionInfo.setMunicipio(selMunicipio);
        accionInfo.setDeptoId(selUnidadEj);


        if (identificador != -1) {
            for (TransferenciaAccion movtoAccionListTemp : movtoAccionesList) {
                if (movtoAccionListTemp.getIdentificador() == identificador) {
                    movtoAccionListTemp.setMovOficioAccion(accionInfo);
                    movtoAccionListTemp.setMovOficioAccionEstList(movtoEstimacionAccionList);
                    movtoAccionListTemp.setEstimacion(calOriginal);
                    movtoAccionListTemp.setPropuestaEstimacion(calPropuesta);
                    movtoAccionListTemp.setValAutorizado("S");
                }
            }
        } else {
            TransferenciaAccion movtoAccionListTemp = new TransferenciaAccion();
            movtoAccionListTemp.setIdentificador(maxIdentificador + 1);
            movtoAccionListTemp.setMovOficioAccion(accionInfo);
            movtoAccionListTemp.setMovOficioAccionEstList(movtoEstimacionAccionList);
            movtoAccionListTemp.setEstimacion(calOriginal);
            movtoAccionListTemp.setPropuestaEstimacion(calPropuesta);
            movtoAccionListTemp.setValAutorizado("P");
            movtoAccionesList.add(movtoAccionListTemp);
        }

        transferencia.setTransferenciaAccionList(movtoAccionesList);

        session.setAttribute("transferencia", transferencia);

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
        out.print(strResultado);
    }
%>

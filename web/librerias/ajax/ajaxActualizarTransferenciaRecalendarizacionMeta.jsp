<%-- 
    Document   : ajaxActualizarTransferenciaRecalendarizacionMeta
    Created on : Jan 15, 2015, 11:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MetaBean metaBean = null;
    Evaluacion evaluacion = new Evaluacion();
    TransferenciaBean transferenciaBean = null;
    MovimientoOficioMeta movtoOficioMeta = new MovimientoOficioMeta();
    MovimientosTransferencia transferencia = new MovimientosTransferencia();

    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<TransferenciaMeta> movtoMetasList = new ArrayList<TransferenciaMeta>();
    List<MovOficioEstimacion> movtoEstimacionList = new ArrayList<MovOficioEstimacion>();
    List<Transferencia> movimientosTransferenciaRecibenList = new ArrayList<Transferencia>();

    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    Double valor = 0.0;

    String arrValores[];
    String linea = new String();
    String ramoId = new String();
    String funcion = new String();
    String calculo = new String();
    String tipoProy = new String();
    String finalidad = new String();
    String metaDescr = new String();
    String subFuncion = new String();
    String cadValores = new String();
    String programaId = new String();
    String ponderacion = new String();
    String strResultado = new String();
    String clasificacion = new String();
    String lineaSectorial = new String();
    String tipoDependencia = new String();

    int cont = 1;
    int year = 0;
    int metaId = 0;
    int contFallas = 0;
    int compromiso = -1;
    int proyectoId = -1;
    int unidadMedida = -1;
    int identificador = -1;
    int objIdentificado = 0;
    int transversalidad = -1;
    double sumRecalendarizacion = 0.0;
    double sumEstimacion = 0.0;
    int maxIdentificador = 0;
    int minIdentificador = 0;
    int contForEvalucion = 0;
    double calPropuesta = 0.0;
    double calOriginal = 0.0;

    boolean resultadoAct = false;

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
        if (request.getParameter("metaDescr") != null && !request.getParameter("metaDescr").equals("")) {
            metaDescr = (String) request.getParameter("metaDescr");
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt((String) request.getParameter("identificador"));
        }

        if (request.getParameter("unidadMedida") != null && !request.getParameter("unidadMedida").equals("")) {
            unidadMedida = Integer.parseInt((String) request.getParameter("unidadMedida"));
        }

        if (request.getParameter("calculo") != null && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }

        if (request.getParameter("clasificacion") != null && !request.getParameter("clasificacion").equals("")) {
            clasificacion = (String) request.getParameter("clasificacion");
        }

        if (request.getParameter("compromiso") != null && !request.getParameter("compromiso").equals("")) {
            compromiso = Integer.parseInt((String) request.getParameter("compromiso"));
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

        if (request.getParameter("sumRecalendarizacion") != null && request.getParameter("sumRecalendarizacion") != "") {
            sumRecalendarizacion = new Double(request.getParameter("sumRecalendarizacion"));
        }
        if (request.getParameter("sumEstimacion") != null && request.getParameter("sumEstimacion") != "") {
            sumEstimacion = new Double(request.getParameter("sumEstimacion"));
        }
        if (request.getParameter("calOriginal") != null && !request.getParameter("calOriginal").equals("")) {
            calOriginal = new Double((String) request.getParameter("calOriginal"));
        }
        if (request.getParameter("calPropuesta") != null && !request.getParameter("calPropuesta").equals("")) {
            calPropuesta = new Double((String) request.getParameter("calPropuesta"));
        }
        if (request.getParameter("ponderacion") != null && !request.getParameter("ponderacion").equals("")) {
            ponderacion = (String) request.getParameter("ponderacion");
        }

        if (request.getParameter("transversalidad") != null && !request.getParameter("transversalidad").equals("")) {
            transversalidad = Integer.parseInt((String) request.getParameter("transversalidad"));
        }

        if (request.getParameter("valores") != null && !request.getParameter("valores").equals("")) {
            cadValores = (String) request.getParameter("valores");
        }

        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            transferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            movtoMetasList = transferencia.getTransferenciaMetaList();
            if (identificador != -1) {
                for (TransferenciaMeta movtoMetaListTemp : movtoMetasList) {
                    if (movtoMetaListTemp.getIdentificador() == identificador) {
                        movtoEstimacionList = movtoMetaListTemp.getMovOficioEstimacion();
                        movtoOficioMeta = movtoMetaListTemp.getMovOficioMeta();
                    }
                }
            }
        }

        maxIdentificador = 0;
        minIdentificador = 0;
        contForEvalucion = 0;
        if (movtoMetasList.size() > 0) {
            for (TransferenciaMeta movtoMetaListTemp : movtoMetasList) {
                if (contForEvalucion == 0) {
                    minIdentificador = movtoMetaListTemp.getIdentificador();
                    maxIdentificador = movtoMetaListTemp.getIdentificador();
                }
                if (maxIdentificador < movtoMetaListTemp.getIdentificador()) {
                    maxIdentificador = movtoMetaListTemp.getIdentificador();
                }
                if (minIdentificador > movtoMetaListTemp.getIdentificador()) {
                    minIdentificador = movtoMetaListTemp.getIdentificador();
                }
                contForEvalucion++;
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

        evaluacion = metaBean.getEvaluacionMeta(year);
        arrValores = new String[cadValores.split(",").length];
        arrValores = cadValores.split(",");
        int contValores = 0;

        if (movtoEstimacionList.size() > 0) {

            for (MovOficioEstimacion reprogramacion : movtoEstimacionList) {

                reprogramacion.setMeta(metaId);
                reprogramacion.setRamo(ramoId);
                reprogramacion.setYear(year);
                reprogramacion.setPrograma(metaBean.getResultSQLgetProgramaByRamoMeta(year, ramoId, metaId));

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

                MovOficioEstimacion reprogramacion = new MovOficioEstimacion();
                reprogramacion.setMeta(metaId);
                reprogramacion.setRamo(ramoId);
                reprogramacion.setYear(year);
                reprogramacion.setPrograma(metaBean.getResultSQLgetProgramaByRamoMeta(year, ramoId, metaId));

                if (!arrValores[it].trim().equals("")) {

                    valor = Double.parseDouble(arrValores[it].trim());
                    reprogramacion.setPeriodo((contValores + 1));
                    reprogramacion.setValor(valor);

                } else {

                    valor = 0.0;
                    reprogramacion.setPeriodo((contValores + 1));
                    reprogramacion.setValor(valor);

                }

                movtoEstimacionList.add(reprogramacion);
                contValores++;

            }

        }

        MovimientoOficioMeta metaInfo = new MovimientoOficioMeta();
        metaInfo.setNva_creacion("N");
        metaInfo.setYear(year);
        metaInfo.setRamoId(ramoId);
        metaInfo.setMetaId(metaId);
        metaInfo.setMetaDescr(metaDescr);
        metaInfo.setPrgId(programaId);
        metaInfo.setProyId(proyectoId);
        metaInfo.setTipoProy(tipoProy);
        metaInfo.setUnidadMedidaId(unidadMedida);
        metaInfo.setClaveMedida(unidadMedida);
        metaInfo.setCalculoId(calculo);
        metaInfo.setClasificacionFuncionalId(clasificacion);

        String clasificacionArreglo[] = new String[0];
        clasificacionArreglo = new String[clasificacion.split("\\.").length];
        clasificacionArreglo = clasificacion.split("\\.");
        if (clasificacionArreglo.length > 0) {
            metaInfo.setFinalidad(clasificacionArreglo[0]);
            metaInfo.setFuncion(clasificacionArreglo[1]);
            metaInfo.setSubfuncion(clasificacionArreglo[2]);
        }

        metaInfo.setCompromisoId(compromiso);
        metaInfo.setLineaPedId(linea);
        if (lineaSectorial.equals("-1")) {
            lineaSectorial = new String();
        }
        metaInfo.setLineaSectorialId(lineaSectorial);
        metaInfo.setPonderacionId(ponderacion);
        metaInfo.setCriterioTransversalidad(transversalidad);

        if (identificador != -1) {
            for (TransferenciaMeta movtoMetaListTemp : movtoMetasList) {
                if (movtoMetaListTemp.getIdentificador() == identificador) {
                    movtoMetaListTemp.setMovOficioMeta(metaInfo);
                    movtoMetaListTemp.setMovOficioEstimacion(movtoEstimacionList);
                    movtoMetaListTemp.setPropuestaEstimacion(calPropuesta);
                    movtoMetaListTemp.setEstimacion(calOriginal);
                    movtoMetaListTemp.setValAutorizado("S");
                }
            }
        } else {
            TransferenciaMeta movtoMetaListTemp = new TransferenciaMeta();
            movtoMetaListTemp.setIdentificador(maxIdentificador + 1);
            movtoMetaListTemp.setMovOficioMeta(metaInfo);
            movtoMetaListTemp.setMovOficioEstimacion(movtoEstimacionList);
            movtoMetaListTemp.setPropuestaEstimacion(calPropuesta);
            movtoMetaListTemp.setEstimacion(calOriginal);
            movtoMetaListTemp.setValAutorizado("P");
            movtoMetasList.add(movtoMetaListTemp);
        }

        transferencia.setTransferenciaMetaList(movtoMetasList);

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
        if (metaBean != null) {
            metaBean.resultSQLDesconecta();
        }
        if (transferenciaBean != null) {
            transferenciaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

<%-- 
    Document   : ajaxActualizarTransferenciaMeta
    Created on : Jan 15, 2015, 11:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.MovimientoOficioMeta"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.Estimacion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Evaluacion"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%

    MetaBean metaBean = null;
    TransferenciaBean ampliacionReduccionBean = null;
    Evaluacion evaluacion = new Evaluacion();
    MovimientosTransferencia transferencia = new MovimientosTransferencia();
    MovimientoOficioMeta movtoOficioMeta = new MovimientoOficioMeta();
    List<Transferencia> movimientosTransferenciaRecibenList = new ArrayList<Transferencia>();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<TransferenciaMeta> movtoMetaList = new ArrayList<TransferenciaMeta>();
    List<MovOficioEstimacion> movtoEstimacionList = new ArrayList<MovOficioEstimacion>();
    String tipoDependencia = new String();
    String strResultado = new String();
    String ramoId = new String();
    String cadValores = new String();
    String arrValores[];
    String calculo = new String();
    String clasificacion = new String();
    String finalidad = new String();
    String funcion = new String();
    String subFuncion = new String();
    String obra = new String();
    String linea = new String();
    String lineaSectorial = new String();
    String ponderacion = new String();
    String programaId = new String();
    String metaDescr = new String();
    String tipoProy = new String();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    int cont = 1;
    int year = 0;
    int metaId = 0;
    int identificador = -1;
    int objIdentificado = 0;
    int contFallas = 0;
    int unidadMedida = -1;
    int compromiso = -1;
    int proyectoId = -1;
    int transversalidad = -1;
    int maxIdentificador = 0;
    int minIdentificador = 0;
    int contForEvalucion = 0;
    int contValores = 0;
    Double valor = 0.0;
    boolean resultadoAct = false;
    int minMetaId = 0;
    int metaIdNeg = 0;

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

        if (request.getParameter("programaId") != null && !request.getParameter("programaId").equals("")) {
            programaId = (String) request.getParameter("programaId");
        }

        if (request.getParameter("tipoProy") != null && !request.getParameter("tipoProy").equals("")) {
            tipoProy = (String) request.getParameter("tipoProy");
        }

        if (request.getParameter("proyectoId") != null && !request.getParameter("proyectoId").equals("")) {
            proyectoId = Integer.parseInt((String) request.getParameter("proyectoId"));
        }

        if (request.getParameter("metaId") != null && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }

        if (request.getParameter("metaDescr") != null && !request.getParameter("metaDescr").equals("")) {
            metaDescr = (String) request.getParameter("metaDescr");
        }

        if (request.getParameter("calculo") != null && !request.getParameter("calculo").equals("")) {
            calculo = (String) request.getParameter("calculo");
        }

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt((String) request.getParameter("identificador"));
        }

        if (request.getParameter("linea") != null && !request.getParameter("linea").equals("")) {
            linea = (String) request.getParameter("linea");
        }

        if (request.getParameter("lineaSectorial") != null && !request.getParameter("lineaSectorial").equals("")) {
            lineaSectorial = (String) request.getParameter("lineaSectorial");
        }

        if (request.getParameter("compromiso") != null && !request.getParameter("compromiso").equals("")) {
            compromiso = Integer.parseInt((String) request.getParameter("compromiso"));
        }

        if (request.getParameter("clasificacion") != null && !request.getParameter("clasificacion").equals("")) {
            clasificacion = (String) request.getParameter("clasificacion");
        }

        if (request.getParameter("ponderacion") != null && !request.getParameter("ponderacion").equals("")) {
            ponderacion = (String) request.getParameter("ponderacion");
        }

        if (request.getParameter("transversalidad") != null && !request.getParameter("transversalidad").equals("")) {
            transversalidad = Integer.parseInt((String) request.getParameter("transversalidad"));
        }

        if (request.getParameter("unidadMedida") != null && !request.getParameter("unidadMedida").equals("")) {
            unidadMedida = Integer.parseInt((String) request.getParameter("unidadMedida"));
        }

        if (request.getParameter("obra") != null && !request.getParameter("obra").equals("")) {
            obra = (String) request.getParameter("obra");
        }

        if (request.getParameter("valores") != null && !request.getParameter("valores").equals("")) {
            cadValores = (String) request.getParameter("valores");
        }

        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            transferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
                movtoMetaList = transferencia.getTransferenciaMetaList();
                if (identificador != -1) {
                    for (TransferenciaMeta movtoMetaListTemp : movtoMetaList) {
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

        if (movtoMetaList.size() > 0) {
            for (TransferenciaMeta movtoMetaListTemp : movtoMetaList) {
                if (contForEvalucion == 0) {
                    minIdentificador = movtoMetaListTemp.getIdentificador();
                    maxIdentificador = movtoMetaListTemp.getIdentificador();
                    minMetaId = movtoMetaListTemp.getMovOficioMeta().getMetaId();
                }
                if (maxIdentificador < movtoMetaListTemp.getIdentificador()) {
                    maxIdentificador = movtoMetaListTemp.getIdentificador();
                }
                if (minIdentificador > movtoMetaListTemp.getIdentificador()) {
                    minIdentificador = movtoMetaListTemp.getIdentificador();
                }

                if (minMetaId > movtoMetaListTemp.getMovOficioMeta().getMetaId()) {
                    minMetaId = movtoMetaListTemp.getMovOficioMeta().getMetaId();
                }

                contForEvalucion++;
            }
        }

        ampliacionReduccionBean = new TransferenciaBean(tipoDependencia);
        ampliacionReduccionBean.setStrServer(request.getHeader("host"));
        ampliacionReduccionBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampliacionReduccionBean.resultSQLConecta(tipoDependencia);

        if (movtoMetaList.size() > 0) {            
            if (minMetaId > 0) {
                metaIdNeg = 0;
                minMetaId = 0;
            }
        }

        if (identificador == -1) {
            metaIdNeg = minMetaId;
            metaIdNeg--;
            if (metaIdNeg == -1) {
                metaIdNeg--;
            }
            metaId = metaIdNeg;
        }

        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);

        evaluacion = metaBean.getResultSQLGetEvaluacionMeta(year);

        arrValores = new String[cadValores.split(",").length];
        arrValores = cadValores.split(",");

        contValores = 0;

        if (movtoEstimacionList.size() > 0) {

            for (MovOficioEstimacion reprogramacionTemp : movtoEstimacionList) {
                reprogramacionTemp.setMeta(metaId);
                reprogramacionTemp.setRamo(ramoId);
                reprogramacionTemp.setYear(year);
                reprogramacionTemp.setPrograma(programaId);
                if (!arrValores[contValores].trim().equals("")) {
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    valor = Double.parseDouble(arrValores[contValores].trim());
                    reprogramacionTemp.setValor(valor);
                } else {
                    valor = 0.0;
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    reprogramacionTemp.setValor(valor);
                }
                contValores++;
            }

        } else {

            for (int it = 0; it < evaluacion.getNumMeses(); it++) {
                MovOficioEstimacion reprogramacionTemp = new MovOficioEstimacion();
                reprogramacionTemp.setMeta(metaId);
                reprogramacionTemp.setRamo(ramoId);
                reprogramacionTemp.setYear(year);
                reprogramacionTemp.setPrograma(programaId);
                if (!arrValores[it].trim().equals("")) {
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    valor = Double.parseDouble(arrValores[it].trim());
                    reprogramacionTemp.setValor(valor);
                } else {
                    valor = 0.0;
                    reprogramacionTemp.setPeriodo((contValores + 1));
                    reprogramacionTemp.setValor(valor);
                }
                movtoEstimacionList.add(reprogramacionTemp);
                contValores++;
            }

        }

        MovimientoOficioMeta metaInfo = new MovimientoOficioMeta();
        metaInfo.setYear(year);
        metaInfo.setRamoId(ramoId);
        if ((metaId * -1) > 999) {
            metaId = Integer.parseInt(String.valueOf(metaId).substring(3));
        }
        metaInfo.setMetaId(metaId);
        metaInfo.setMetaDescr(metaDescr);
        metaInfo.setPrgId(programaId);
        metaInfo.setProyId(proyectoId);
        metaInfo.setTipoProy(tipoProy);
        metaInfo.setClaveMedida(unidadMedida);
        metaInfo.setCalculoId(calculo);
        metaInfo.setLineaPedId(linea);
        metaInfo.setCompromisoId(compromiso);
        metaInfo.setClasificacionFuncionalId(clasificacion);
        metaInfo.setPonderacionId(ponderacion);
        metaInfo.setCriterioTransversalidad(transversalidad);
        metaInfo.setObra(obra);
        metaInfo.setNva_creacion("S");
        if (lineaSectorial.equals("-1")) {
            lineaSectorial = new String();
        }
        metaInfo.setLineaSectorialId(lineaSectorial);

        if (identificador != -1) {

            for (TransferenciaMeta movtoMetaListTemp : movtoMetaList) {
                if (movtoMetaListTemp.getIdentificador() == identificador) {
                    movtoMetaListTemp.setMovOficioMeta(metaInfo);
                    movtoMetaListTemp.setMovOficioEstimacion(movtoEstimacionList);
                    movtoMetaListTemp.setValAutorizado("S");
                }
            }

        } else {

            TransferenciaMeta movtoMetaListTemp = new TransferenciaMeta();
            movtoMetaListTemp.setIdentificador(maxIdentificador + 1);
            movtoMetaListTemp.setMovOficioMeta(metaInfo);
            movtoMetaListTemp.setMovOficioEstimacion(movtoEstimacionList);
            movtoMetaListTemp.setValAutorizado("P");
            movtoMetaList.add(movtoMetaListTemp);

        }

            transferencia.setTransferenciaMetaList(movtoMetaList);

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
        if (ampliacionReduccionBean != null) {
            ampliacionReduccionBean.resultSQLDesconecta();
        }
        if (metaBean != null) {
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);

    }
%>

<%-- 
    Document   : ajaxActualizarRecalendarizacionMeta
    Created on : Apr 24, 2015, 11:30:34 AM
    Author     : jarguelles
--%>

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
    MetaBean metaBean = null;
    Evaluacion evaluacion = new Evaluacion();
    MovimientosRecalendarizacion recalendarizacion = new MovimientosRecalendarizacion();

    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    List<RecalendarizacionMeta> movtoMetasList = new ArrayList<RecalendarizacionMeta>();
    List<MovOficioEstimacion> movtoEstimacionList = new ArrayList<MovOficioEstimacion>();

    String arrValores[];
    String ramoId = new String();
    String cadValores = new String();
    String strResultado = new String();
    String tipoDependencia = new String();

    int cont = 1;
    int year = 0;
    int metaId = 0;
    int contFallas = 0;
    int identificador = -1;
    int objIdentificado = 0;
    int maxIdentificador = 0;
    int minIdentificador = 0;
    int contForEvalucion = 0;

    NumberFormat numberF = NumberFormat.getInstance(Locale.US);

    double valor = 0.0;
    double calOriginal = 0.0;
    double calPropuesta = 0.0;
    double sumEstimacion = 0.0;
    double sumRecalendarizacion = 0.0;

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
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt((String) request.getParameter("identificador"));
        }
        if (request.getParameter("sumRecalendarizacion") != null && request.getParameter("sumRecalendarizacion") != "") {
            sumRecalendarizacion = new Double(request.getParameter("sumRecalendarizacion"));
        }
        if (request.getParameter("sumEstimacion") != null && request.getParameter("sumEstimacion") != "") {
            sumEstimacion = new Double(request.getParameter("sumEstimacion"));
        }
        if (request.getParameter("calPropuesta") != null && !request.getParameter("calPropuesta").equals("")) {
            calOriginal = new Double((String) request.getParameter("calPropuesta"));
        }
        if (request.getParameter("calPropuesta") != null && !request.getParameter("calPropuesta").equals("")) {
            calPropuesta = new Double((String) request.getParameter("calPropuesta"));
        }

        if (session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != "") {
            recalendarizacion = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            movtoMetasList = recalendarizacion.getMovEstimacionList();
            if (identificador != -1) {
                for (RecalendarizacionMeta movtoMetaListTemp : movtoMetasList) {
                    if (movtoMetaListTemp.getIdentificado() == identificador) {
                        movtoEstimacionList = movtoMetaListTemp.getMovEstimacionList();
                    }
                }
            }
        }

        maxIdentificador = 0;
        minIdentificador = 0;
        contForEvalucion = 0;

        if (movtoMetasList.size() > 0) {

            for (RecalendarizacionMeta movtoMetaListTemp : movtoMetasList) {

                if (contForEvalucion == 0) {
                    minIdentificador = movtoMetaListTemp.getIdentificado();
                    maxIdentificador = movtoMetaListTemp.getIdentificado();
                }

                if (maxIdentificador < movtoMetaListTemp.getIdentificado()) {
                    maxIdentificador = movtoMetaListTemp.getIdentificado();
                }

                if (minIdentificador > movtoMetaListTemp.getIdentificado()) {
                    minIdentificador = movtoMetaListTemp.getIdentificado();
                }

                contForEvalucion++;

            }
        }

        if (request.getParameter("valores") != null && !request.getParameter("valores").equals("")) {
            cadValores = (String) request.getParameter("valores");
        }

        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
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
                    reprogramacion.setPeriodo((contValores + 1));
                    valor = Double.parseDouble(arrValores[contValores].trim());
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
                    reprogramacion.setPeriodo((contValores + 1));
                    valor = Double.parseDouble(arrValores[it].trim());
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

        if (identificador != -1) {
            for (RecalendarizacionMeta movtoMetaListTemp : movtoMetasList) {
                if (movtoMetaListTemp.getIdentificado() == identificador) {
                    movtoMetaListTemp.setMovEstimacionList(movtoEstimacionList);
                    movtoMetaListTemp.setEstimacion(calOriginal);
                    movtoMetaListTemp.setValAutorizado("S");
                    movtoMetaListTemp.setPropuestaEstimacion(calPropuesta);
                }
            }
        } else {
            RecalendarizacionMeta movtoMetaListTemp = new RecalendarizacionMeta();
            movtoMetaListTemp.setIdentificado(maxIdentificador + 1);
            movtoMetaListTemp.setMovEstimacionList(movtoEstimacionList);
            movtoMetaListTemp.setEstimacion(calOriginal);
            movtoMetaListTemp.setPropuestaEstimacion(calPropuesta);
            movtoMetaListTemp.setValAutorizado("S");
            movtoMetasList.add(movtoMetaListTemp);
        }
        recalendarizacion.setMovEstimacionList(movtoMetasList);
        session.setAttribute("recalendarizacion", recalendarizacion);


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
        out.print(strResultado);
    }
%>

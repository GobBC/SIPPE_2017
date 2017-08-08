<%-- 
    Document   : ajaxGetInfoAccionEstimacion
    Created on : Apr 24, 2015, 11:30:34 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
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
    AccionBean accionBean = null;
    Evaluacion evaluacion = new Evaluacion();
    List<Estimacion> estimacionList = new ArrayList<Estimacion>();
    String tipoDependencia = new String();
    String strResultado = new String();

    int cont = 1;
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    int year = 0;
    String ramoId = new String();
    int metaId = 0;
    int accionId = 0;
    int identificador = -1;
    int objIdentificado = 0;
    String cadValores = new String();
    List<RecalendarizacionAccion> movtoAccionesList = new ArrayList<RecalendarizacionAccion>();
    List<MovOficioAccionEstimacion> movtoEstimacionList = new ArrayList<MovOficioAccionEstimacion>();
    MovimientosRecalendarizacion recalendarizacion = new MovimientosRecalendarizacion();
    String arrValores[];
    Double valor = 0.0;
    boolean resultadoAct = false;
    int contFallas = 0;

    int maxIdentificador = 0;
    int minIdentificador = 0;
    int contForEvalucion = 0;

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
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("identificador") != null && !request.getParameter("identificador").equals("")) {
            identificador = Integer.parseInt((String) request.getParameter("identificador"));
        }
        if (request.getParameter("sumRecalendarizacion") != null && !request.getParameter("sumRecalendarizacion").equals("")) {
            sumRecalendarizacion = new Double((String) request.getParameter("sumRecalendarizacion"));
        }
        if (request.getParameter("sumEstimacion") != null && !request.getParameter("sumEstimacion").equals("")) {
            sumEstimacion = new Double((String) request.getParameter("sumEstimacion"));
        }
        if (request.getParameter("calOriginal") != null && !request.getParameter("calOriginal").equals("")) {
            calOriginal = new Double((String) request.getParameter("calOriginal"));
        }
        if (request.getParameter("calPropuesta") != null && !request.getParameter("calPropuesta").equals("")) {
            calPropuesta = new Double((String) request.getParameter("calPropuesta"));
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

        maxIdentificador = 0;
        minIdentificador = 0;
        contForEvalucion = 0;

        if (movtoAccionesList.size() > 0) {

            for (RecalendarizacionAccion movtoAccionListTemp : movtoAccionesList) {

                if (contForEvalucion == 0) {
                    minIdentificador = movtoAccionListTemp.getIdentificado();
                    maxIdentificador = movtoAccionListTemp.getIdentificado();
                }

                if (maxIdentificador < movtoAccionListTemp.getIdentificado()) {
                    maxIdentificador = movtoAccionListTemp.getIdentificado();
                }

                if (minIdentificador > movtoAccionListTemp.getIdentificado()) {
                    minIdentificador = movtoAccionListTemp.getIdentificado();
                }

                contForEvalucion++;

            }
        }

        if (request.getParameter("valores") != null && !request.getParameter("valores").equals("")) {
            cadValores = (String) request.getParameter("valores");
        }

        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        evaluacion = accionBean.getResultSQLGetEvaluacionMeta(year);

        arrValores = new String[cadValores.split(",").length];
        arrValores = cadValores.split(",");
        int contValores = 0;

        if (movtoEstimacionList.size() > 0) {
            for (MovOficioAccionEstimacion reprogramacion : movtoEstimacionList) {
                reprogramacion.setAccion(accionId);
                reprogramacion.setMeta(metaId);
                reprogramacion.setRamo(ramoId);
                reprogramacion.setYear(year);
                reprogramacion.setPrograma(accionBean.getResultSQLgetProgramaByRamoMetaAccion(year, ramoId, metaId, accionId));
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
                reprogramacion.setPrograma(accionBean.getResultSQLgetProgramaByRamoMetaAccion(year, ramoId, metaId, accionId));
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

        if (identificador != -1) {
            for (RecalendarizacionAccion movtoAccionListTemp : movtoAccionesList) {
                if (movtoAccionListTemp.getIdentificado() == identificador) {
                    movtoAccionListTemp.setMovEstimacionList(movtoEstimacionList);
                    movtoAccionListTemp.setEstimacion(calOriginal);
                    movtoAccionListTemp.setValAutorizacion("S");
                    movtoAccionListTemp.setPropuestaEstimacion(calPropuesta);
                }
            }
        } else {
            RecalendarizacionAccion movtoAccionListTemp = new RecalendarizacionAccion();
            movtoAccionListTemp.setIdentificado(maxIdentificador + 1);
            movtoAccionListTemp.setMovEstimacionList(movtoEstimacionList);
            movtoAccionListTemp.setEstimacion(calOriginal);
            movtoAccionListTemp.setPropuestaEstimacion(calPropuesta);
            movtoAccionListTemp.setValAutorizacion("S");
            movtoAccionesList.add(movtoAccionListTemp);

        }

        recalendarizacion.setMovAccionEstimacionList(movtoAccionesList);
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
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

<%-- 
    Document   : ajaxSaveMovtoAmpRed
    Created on : Jan 12, 2016, 10:59:49 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String ramoSession = new String();
    long selCaratula = -1;
    AmpliacionReduccionBean ampRedBean = null;
    MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
    String tipoDependencia = new String();
    String strResultado = new String();
    String autorizacion = new String();
    String appLogin = new String();
    String justificacion = new String();
    String estatus = new String();
    String tipoMov = new String();
    String tipoOficio = new String();
    String capturaEspecial = new String();
    String mensaje = new String();
    String date = new String();
    String comentarioPlan = new String();
    int folio = 0;
    int year = 0;
    boolean isActual = true;
    boolean checkImpacto = false;
    boolean fechaConta = false;
    boolean isRechazado = false;

    try {
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("ramoAsignado") != null && session.getAttribute("ramoAsignado") != "") {
            ramoSession = (String) session.getAttribute("ramoAsignado");
        }
        if (request.getParameter("selCaratula") != null && !request.getParameter("selCaratula").equals("")) {
            selCaratula = Long.parseLong(request.getParameter("selCaratula"));
        }
        if (request.getParameter("autorizacion") != null && !request.getParameter("autorizacion").equals("")) {
            autorizacion = request.getParameter("autorizacion");
        }
        if (request.getParameter("justificacion") != null && !request.getParameter("justificacion").equals("")) {
            justificacion = request.getParameter("justificacion");
            justificacion = justificacion.replaceAll("'","''");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = request.getParameter("estatus");
        }
        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = request.getParameter("tipoMov");
        }
        if (Utilerias.existeParametro("checkImpacto", request)) {
            checkImpacto = Boolean.valueOf(request.getParameter("checkImpacto"));
        }
        if (Utilerias.existeParametro("folio", request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("isActual", request)) {
            isActual = Boolean.parseBoolean(request.getParameter("isActual"));
        }
        if (Utilerias.existeParametro("dateFor", request)) {
            date = request.getParameter("dateFor");
        }
        if (Utilerias.existeParametro("comentarioPlan", request)) {
            comentarioPlan = request.getParameter("comentarioPlan");
        }
        if (Utilerias.existeParametro("fechaContable", request)) {
            fechaConta = Boolean.parseBoolean(request.getParameter("fechaContable"));
        }
        if (Utilerias.existeParametro("tipoOficio", request)) {
            tipoOficio = request.getParameter("tipoOficio");
        }
        if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
            movAmpRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            movAmpRed.setAppLogin(appLogin);
            movAmpRed.setJutificacion(justificacion);
            //if(!comentarioPlan.isEmpty()){
            movAmpRed.setComentarioPlaneacion(comentarioPlan);
            //}
            
            if (movAmpRed.getStatus() == null || movAmpRed.getStatus().isEmpty()) {
                movAmpRed.setStatus(estatus);
            }
            movAmpRed.setTipoMovimiento(tipoMov);
        }
        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);
        if (fechaConta) {
            capturaEspecial = "S";
        } else {
            capturaEspecial = "N";
        }
        if (movAmpRed.getAmpReducAccionReqList().size() > 0) {
            if (checkImpacto) {
                if (movAmpRed.getAmpReducMetaList().size() > 0 || movAmpRed.getAmpReducAccionList().size() > 0) {

                    if (movAmpRed.getRamo() == null) {
                        mensaje = ampRedBean.saveMovimientoAmpliacionReduccion(movAmpRed, folio, isActual, year, date, justificacion, capturaEspecial, ramoSession, selCaratula, estatus, tipoDependencia, tipoOficio);
                    } else {
                        mensaje = ampRedBean.saveMovimientoAmpliacionReduccion(movAmpRed, folio, isActual, year, date, justificacion, capturaEspecial, movAmpRed.getRamo(), selCaratula, estatus, tipoDependencia, tipoOficio);
                    }
                    
                } else {
                    mensaje = "3|Al seleccionar 'Impacto programático' debe de realizar al menos un movimiento en metas o acciones";
                }
            } else {
                
                if (movAmpRed.getRamo() == null) {
                    mensaje = ampRedBean.saveMovimientoAmpliacionReduccion(movAmpRed, folio, isActual, year, date, justificacion, capturaEspecial, ramoSession, selCaratula, estatus, tipoDependencia, tipoOficio);
                } else {
                    mensaje = ampRedBean.saveMovimientoAmpliacionReduccion(movAmpRed, folio, isActual, year, date, justificacion, capturaEspecial, movAmpRed.getRamo(), selCaratula, estatus, tipoDependencia, tipoOficio);
                }
                
            }
        } else {
            mensaje = "2|Debe de realizar al meno una Ampliación/Reduccion";
        }
        if (mensaje.isEmpty()) {
            strResultado += "4|Ocurrió un error al procesar la solicitud";
        }
        if (mensaje.startsWith("$")) {
            if (mensaje.replace("$", "").trim().split("\\|")[1].equals("N")) {
                isRechazado = true;
            }
            movAmpRed.setOficio(Integer.parseInt(mensaje.replace("$", "").split("\\|")[0].trim()));
            if (isRechazado) {
                strResultado = "1|Se creo un nuevo folio para continuar su trámite|" + movAmpRed.getOficio();
            } else {
                strResultado = "1|Los registros se grabaron satisfactoriamente|" + movAmpRed.getOficio();
            }
            movAmpRed.getMovCaratula().setsIdCaratula(selCaratula);
            session.setAttribute("ampliacionReduccion", movAmpRed);
        } else {
            strResultado += mensaje;
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
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
<%-- 
    Document   : ajaxEnviarAutorizacionMovimientoReq
    Created on : Dec 10, 2015, 1:52:58 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    MovimientosRecalendarizacion movRecal = new MovimientosRecalendarizacion();
    String tipoDependencia = new String();
    String strResultado = new String();
    String autorizacion = new String();
    String appLogin = new String();
    String justificacion = new String();
    String estatus = new String();
    String tipoMov = new String();
    String mensaje = new String();
    String date = new String();
    int folio = 0;
    int year = 0;
    boolean isActual = true;
    boolean checkImpacto = false;
    
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != ""){
            appLogin = (String) session.getAttribute("strUsuario");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("autorizacion") != null && !request.getParameter("autorizacion").equals("")) {
            autorizacion = (String) request.getParameter("autorizacion");
        }
        if (request.getParameter("justificacion") != null && !request.getParameter("justificacion").equals("")) {
            justificacion = (String) request.getParameter("justificacion");
            justificacion = justificacion.replaceAll("'","''");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = (String) request.getParameter("estatus");
        }
        if (request.getParameter("tipoMov") != null && !request.getParameter("tipoMov").equals("")) {
            tipoMov = (String) request.getParameter("tipoMov");
        }
        if(Utilerias.existeParametro("checkImpacto", request)){
            checkImpacto = Boolean.parseBoolean((String) request.getParameter("checkImpacto")); 
        }
        if(Utilerias.existeParametro("folio", request)){
            folio = Integer.parseInt((String) request.getParameter("folio"));
        }
        if(Utilerias.existeParametro("isActual", request)){
            isActual = Boolean.parseBoolean((String) request.getParameter("isActual"));
        }
        if(Utilerias.existeParametro("dateFor", request)){
            date = (String) request.getParameter("dateFor");
        }
        if(session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != ""){
            movRecal = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            movRecal.setAppLogin(appLogin);
            movRecal.setJutificacion(justificacion);
            movRecal.setStatus(estatus);
            movRecal.setTipoMovimiento(tipoMov);
        }
        if(movRecal.getMovEstimacionList().size() > 0 || movRecal.getMovAccionEstimacionList().size() > 0 || movRecal.getMovOficiosAccionReq().size() > 0){
            if(checkImpacto){
                if(movRecal.getMovEstimacionList().size() > 0 || movRecal.getMovAccionEstimacionList().size() > 0){
                    recalBean = new RecalendarizacionBean(tipoDependencia);
                    recalBean.setStrServer(request.getHeader("host"));
                    recalBean.setStrUbicacion(getServletContext().getRealPath(""));
                    recalBean.resultSQLConecta(tipoDependencia);
                    mensaje = recalBean.saveMovimientoRecalendarizacion(movRecal, isActual, year, folio,justificacion,date);
                    if(mensaje.startsWith("$")){
                        strResultado = "1|Los registros se grabaron satisfactoriamente|"+mensaje.replace("$", "");
                    }else{
                        strResultado = "2|"+mensaje;
                    }
                }else{
                    strResultado += "4|Al seleccionar 'Impacto programático' debe de realizar al menos un movimiento en metas o acciones";
                }
            }else{
                recalBean = new RecalendarizacionBean(tipoDependencia);
                recalBean.setStrServer(request.getHeader("host"));
                recalBean.setStrUbicacion(getServletContext().getRealPath(""));
                recalBean.resultSQLConecta(tipoDependencia);
                mensaje = recalBean.saveMovimientoRecalendarizacion(movRecal, isActual, year, folio,justificacion,date);
                if(mensaje.startsWith("$")){
                    strResultado = "1|Los registros se grabaron satisfactoriamente|"+mensaje.replace("$", "");
                }else{
                    strResultado = "2|"+mensaje;
                }
            }
        }else{
            strResultado += "6|Debe de realizar al menos un moviemiento antes de guardar";
        }        
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (recalBean != null) {
            recalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

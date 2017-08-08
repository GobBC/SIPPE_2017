<%-- 
    Document   : ajaxValidarMovimientosPendientes
    Created on : Dec 17, 2015, 11:43:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<RecalendarizacionMeta> recalMetaList = new ArrayList<RecalendarizacionMeta>();
    List<RecalendarizacionAccion> recalAccionList = new ArrayList<RecalendarizacionAccion>();
    List<RecalendarizacionAccionReq> recalAccionReqList = new ArrayList<RecalendarizacionAccionReq>();
    MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();
    String strResult = new String();
    int year = 0;
    String ramoId = new String();
    int metaId = 0;
    int accionId = 0;
    int oficio = 0;
    ResultSQL validadorBean = null;
    String tipoDependencia = new String();
    int existenMovMetas = 0;
    int existenMovAcciones = 0;
    int contAccRec = 0;
    int opcion = 0;
    try {
        if (session.getAttribute("recalendarizacion") != null) {
            movimiento = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
        }
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
        if(Utilerias.existeParametro("opcion", request)){
            opcion = Integer.parseInt(request.getParameter("opcion"));
        }
        if (request.getParameter("oficio") != null && !request.getParameter("oficio").equals("")) {
            oficio = Integer.parseInt((String) request.getParameter("oficio"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

        validadorBean = new ResultSQL(tipoDependencia);
        validadorBean.setStrServer(request.getHeader("host"));
        validadorBean.setStrUbicacion(getServletContext().getRealPath(""));
        validadorBean.resultSQLConecta(tipoDependencia);

        recalMetaList = movimiento.getMovEstimacionList();
        recalAccionList = movimiento.getMovAccionEstimacionList();
        recalAccionReqList = movimiento.getMovOficiosAccionReq();
        strResult = "0";
        
        /*if(recalAccionReqList.size() > 0){
            if(opcion == 1){
                for(RecalendarizacionAccionReq recalAccionReq : recalAccionReqList){
                    if(recalAccionReq.getMovAccionReq().getRamo().equals(ramoId)  && recalAccionReq.getMovAccionReq().getMeta() == metaId){
                        contAccRec ++;
                    }
                }
                if(contAccRec == 0){
                    strResult = "6";
                }
            }
            if(opcion == 2){
                for(RecalendarizacionAccionReq recalAccionReq : recalAccionReqList){
                    if(recalAccionReq.getMovAccionReq().getRamo().equals(ramoId) && recalAccionReq.getMovAccionReq().getMeta() == metaId &&
                            recalAccionReq.getMovAccionReq().getAccion()== accionId){
                        contAccRec ++;
                    }
                }
                if(contAccRec == 0){
                    strResult = "7";
                }
            }
            
        }else{
            strResult = "5";
        }*/
        //if(contAccRec > 0){
            if (recalMetaList.size() > 0) {
                for (RecalendarizacionMeta movMeta : recalMetaList) {
                    if ((movMeta.getMovEstimacionList().get(0).getRamo().equalsIgnoreCase(ramoId) && movMeta.getMovEstimacionList().get(0).getMeta() == metaId && movMeta.getMovEstimacionList().get(0).getYear() == year)) {
                        strResult = "3";
                    }
                }
            } else {
                existenMovMetas = validadorBean.getResultExistenMovMetaByClave(year, ramoId, metaId, oficio);
                if (existenMovMetas != 0) {
                    strResult = "1|" + existenMovMetas;
                }
            }

            if (recalAccionList.size() > 0) {
                for (RecalendarizacionAccion movAccion : recalAccionList) {
                    if ((movAccion.getMovEstimacionList().get(0).getRamo().equalsIgnoreCase(ramoId) && movAccion.getMovEstimacionList().get(0).getMeta() == metaId && movAccion.getMovEstimacionList().get(0).getAccion() == accionId && movAccion.getMovEstimacionList().get(0).getYear() == year)) {
                        strResult = "4";
                    }
                }
            } else {
                existenMovAcciones = validadorBean.getResultExistenMovAccionByClave(year, ramoId, metaId, accionId, oficio);
                if (existenMovAcciones != 0) {
                    strResult = "2|" + existenMovAcciones;
                }
            }
        //}

    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResult);
    }
%>

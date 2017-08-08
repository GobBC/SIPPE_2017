<%-- 
    Document   : ajaxBorrarRecalendarizacion
    Created on : Dec 17, 2015, 1:16:54 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.RecalendarizacionMeta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccion"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MovimientosRecalendarizacion movRecal = new MovimientosRecalendarizacion();
    List<RecalendarizacionMeta> movtoMetasList = new ArrayList<RecalendarizacionMeta>();
    RecalendarizacionMeta recalMetaTemp = new RecalendarizacionMeta();
    List<RecalendarizacionAccion> movtoAccionList = new ArrayList<RecalendarizacionAccion>();
    RecalendarizacionAccion recalAccionTemp = new RecalendarizacionAccion();
    List<RecalendarizacionAccionReq> movtoAccionReqList = new ArrayList<RecalendarizacionAccionReq>();
    RecalendarizacionAccionReq recalAccionReqTemp = new  RecalendarizacionAccionReq();
    String strResultado = new String();
    int identidicador = -1;
    int tipoRecal = 0;
    
    try {
        if(session.getAttribute("recalendarizacion") != null && session.getAttribute("recalendarizacion") != ""){
            movRecal = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
            movtoMetasList = movRecal.getMovEstimacionList();
            movtoAccionList = movRecal.getMovAccionEstimacionList();
            movtoAccionReqList = movRecal.getMovOficiosAccionReq();
        }
        if(Utilerias.existeParametro("identificador", request)){
            identidicador = Integer.parseInt((String) request.getParameter("identificador"));
        }
        if(Utilerias.existeParametro("tipoRecal", request)){
            tipoRecal = Integer.parseInt((String) request.getParameter("tipoRecal"));
        }
        if(tipoRecal == 1){
            for(RecalendarizacionMeta recalMeta : movtoMetasList){
                if(recalMeta.getIdentificado() == identidicador){
                    recalMetaTemp = recalMeta;
                    break;
                }
            }
            movtoMetasList.remove(recalMetaTemp);
            movRecal.setMovEstimacionList(movtoMetasList);
        }
        if(tipoRecal == 2){
            for(RecalendarizacionAccion recalAccion : movtoAccionList){
                if(recalAccion.getIdentificado() == identidicador){
                    recalAccionTemp = recalAccion;
                    break;
                }
            }
            movtoAccionList.remove(recalAccionTemp);
            movRecal.setMovAccionEstimacionList(movtoAccionList);
        }
        if(tipoRecal == 3){
            for(RecalendarizacionAccionReq recalAccionReq : movtoAccionReqList){
                if(recalAccionReq.getIdentificador() == identidicador){
                    recalAccionReqTemp = recalAccionReq;
                    break;
                }
            }
            movtoAccionReqList.remove(recalAccionReqTemp);
            movRecal.setMovOficiosAccionReq(movtoAccionReqList);
        }
        session.setAttribute("recalendarizacion", movRecal);
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResultado);
    }
%>
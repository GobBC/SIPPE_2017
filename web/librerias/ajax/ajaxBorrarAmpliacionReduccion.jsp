<%-- 
    Document   : ajaxBorrarAmpliacionReduccion
    Created on : Jan 19, 2016, 8:45:16 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    MovimientosAmpliacionReduccion movAmplRed = new MovimientosAmpliacionReduccion();
    List<AmpliacionReduccionMeta> movtoMetasList = new ArrayList<AmpliacionReduccionMeta>();
    AmpliacionReduccionMeta recalMetaTemp = new AmpliacionReduccionMeta();
    List<AmpliacionReduccionAccion> movtoAccionList = new ArrayList<AmpliacionReduccionAccion>();
    AmpliacionReduccionAccion recalAccionTemp = new AmpliacionReduccionAccion();
    List<AmpliacionReduccionAccionReq> movtoAccionReqList = new ArrayList<AmpliacionReduccionAccionReq>();
    AmpliacionReduccionAccionReq recalAccionReqTemp = new  AmpliacionReduccionAccionReq();
    String strResultado = new String();
    String mensaje = new String();
    int identidicador = -1;
    int tipoRecal = 0;
    boolean bandera = true;
    
    try {
        if(session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != ""){
            movAmplRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            movtoMetasList = movAmplRed.getAmpReducMetaList();
            movtoAccionList = movAmplRed.getAmpReducAccionList();
            movtoAccionReqList = movAmplRed.getAmpReducAccionReqList();
        }
        if(Utilerias.existeParametro("identificador", request)){
            identidicador = Integer.parseInt( request.getParameter("identificador"));
        }
        if(Utilerias.existeParametro("tipoRecal", request)){
            tipoRecal = Integer.parseInt( request.getParameter("tipoRecal"));
        }
        if(tipoRecal == 1){
            for(AmpliacionReduccionMeta ampRedMeta : movtoMetasList){
                if(ampRedMeta.getIdentificador() == identidicador){
                    recalMetaTemp = ampRedMeta;
                    break;
                }
            }
            for(AmpliacionReduccionAccion transTemp : movtoAccionList){
                if(transTemp.getMovOficioAccion().getMetaId() == recalMetaTemp.getMovOficioMeta().getMetaId() &&
                        recalMetaTemp.getMovOficioMeta().getMetaId() < 0){
                    bandera = false;
                    mensaje = "Esta meta esta relacionada a una acción";
                    break;
                }
            }
            if(!bandera){
                for(AmpliacionReduccionAccionReq transTemp : movtoAccionReqList){
                    if(transTemp.getMeta() == recalMetaTemp.getMovOficioMeta().getMetaId() &&
                        recalMetaTemp.getMovOficioMeta().getMetaId() < 0){
                        bandera = false;
                        mensaje = "Esta meta esta relacionada a un requerimiento";
                        break;
                    }
                }
            }
            if(bandera)
                movtoMetasList.remove(recalMetaTemp);
            movAmplRed.setAmpReducMetaList(movtoMetasList);
        }
        if(tipoRecal == 2){
            for(AmpliacionReduccionAccion ampRedAccion : movtoAccionList){
                if(ampRedAccion.getIdentificador() == identidicador){
                    recalAccionTemp = ampRedAccion;
                    break;
                }
            }
            for(AmpliacionReduccionAccionReq transTemp : movtoAccionReqList){
                if(transTemp.getAccion() == recalAccionTemp.getMovOficioAccion().getAccionId() &&
                        recalAccionTemp.getMovOficioAccion().getAccionId() < 0){
                    bandera = false;
                    mensaje = "Esta accion esta relacionada a un requerimiento";
                    break;
                }
            }
            if(bandera)
                movtoAccionList.remove(recalAccionTemp);
            movAmplRed.setAmpReducAccionList(movtoAccionList);
        }
        if(tipoRecal == 3){
            for(AmpliacionReduccionAccionReq ampRedAccionReq : movtoAccionReqList){
                if(ampRedAccionReq.getIdentidicador()== identidicador){
                    recalAccionReqTemp = ampRedAccionReq;
                    break;
                }
            }
            bandera = true;
            movtoAccionReqList.remove(recalAccionReqTemp);
            movAmplRed.setAmpReducAccionReqList(movtoAccionReqList);
        }
        if(bandera){
            strResultado = "1|Exito";
        }else{
            strResultado = "2|"+ mensaje;
        }
        session.setAttribute("ampliacionReduccion", movAmplRed);
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
<%-- 
    Document   : ajaxActualizaTablaMovtoReprogramacion
    Created on : Dec 22, 2015, 10:24:06 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<ReprogramacionAccion> reprogAccionList = new ArrayList<ReprogramacionAccion>();
    List<ReprogramacionMeta> reprogMetaList = new ArrayList<ReprogramacionMeta>();
    MovimientosReprogramacion movimiento = new MovimientosReprogramacion();
    String strResult = new String();
    String estatus = new String();
    
    boolean autoriza = false;
    try{
        if (session.getAttribute("reprogramacion") != null) {
            movimiento = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            reprogMetaList = movimiento.getMovOficioMetaList();
            reprogAccionList = movimiento.getMovOficioAccionList();
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = request.getParameter("estatus");
        }
        if (Utilerias.existeParametro("autorizar", request)) {
            autoriza = Boolean.parseBoolean(request.getParameter("autorizar"));
        }

        for (ReprogramacionMeta reprogMeta : reprogMetaList) {
            out.print("<tr>");
            out.print("<td> " + reprogMeta.getMetaInfo().getRamoId() + "</td>");
            out.print("<td> " + reprogMeta.getMetaInfo().getPrgId() + "</td>");
            out.print("<td> " + reprogMeta.getMetaInfo().getTipoProy() + reprogMeta.getMetaInfo().getProyId() + "</td>");
            out.print("<td> " + reprogMeta.getMetaInfo().getMetaId() + "</td>");
            out.print("<td> - </td>");
            out.print("<td> - </td>");
            if (estatus.equals("X") || estatus.equals("R") || autoriza) {
                if(reprogMeta.getMetaInfo().getNvaCreacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoMetaReprogramacionTabla"
                            + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                            + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\")' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoMetaReprogramacionRecalendarizacionTabla"
                            + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                            + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\")' /></td>");
                }
                out.print("<td><input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarReprogramacion(\"" + reprogMeta.getIdentificador() + "\",\"1\");' /></td>");
            } else {
                if(reprogMeta.getMetaInfo().getNvaCreacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                            + "onclick='getInfoMetaReprogramacionTabla"
                            + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                            + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\")' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                            + "onclick='getInfoMetaReprogramacionRecalendarizacionTabla"
                            + "(\"" + reprogMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogMeta.getMetaInfo().getRamoId() + "\","
                            + "\"" + reprogMeta.getMetaInfo().getMetaId() + "\")' /></td>");
                }
            }
            out.print("</tr>");
        }
        for (ReprogramacionAccion reprogAccion : reprogAccionList) {
            out.print("<tr>");
            out.print("<td> " + reprogAccion.getMovOficioAccion().getRamoId() + "</td>");
            out.print("<td> " + reprogAccion.getMovOficioAccion().getProgramaId() + " </td>");
            out.print("<td> - </td>");
            out.print("<td> " + reprogAccion.getMovOficioAccion().getMetaId() + "</td>");
            out.print("<td> " + reprogAccion.getMovOficioAccion().getAccionId() + " </td>");
            out.print("<td> " + reprogAccion.getMovOficioAccion().getDeptoId() + " </td>");
            if (estatus.equals("X") || estatus.equals("R") || autoriza) {
                if(reprogAccion.getMovOficioAccion().getNvaCreacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoAccionReprogramacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoAccionReprogramacionRecalendarizacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }
                out.print("<td><input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarReprogramacion(\"" + reprogAccion.getIdentificador() + "\",\"2\");' /></td>");
            } else {
                if(reprogAccion.getMovOficioAccion().getNvaCreacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                            + "onclick='getInfoAccionReprogramacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-ver' "
                            + "onclick='getInfoAccionReprogramacionRecalendarizacionTabla(\"" + reprogAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + reprogAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }
            }
            out.print("</tr>");
        }
    }catch (Exception ex) {
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

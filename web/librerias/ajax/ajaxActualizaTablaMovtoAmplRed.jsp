<%-- 
    Document   : ajaxActualizaTablaMovtoReprogramacion
    Created on : Dec 22, 2015, 10:24:06 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<AmpliacionReduccionAccion> ampReduccionAccionList = new ArrayList<AmpliacionReduccionAccion>();
    List<AmpliacionReduccionMeta> ampReduccionMetaList = new ArrayList<AmpliacionReduccionMeta>();
    MovimientosAmpliacionReduccion movimiento = new MovimientosAmpliacionReduccion();
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    String strResult = new String();
    String estatus = new String();
    
    boolean autorizar = false;
    try {
        if (session.getAttribute("ampliacionReduccion") != null) {
            movimiento = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
            ampReduccionMetaList = movimiento.getAmpReducMetaList();
            ampReduccionAccionList = movimiento.getAmpReducAccionList();
        }
        if (Utilerias.existeParametro("estatus", request)) {
            estatus = request.getParameter("estatus");
        }
        if (Utilerias.existeParametro("autorizar", request)) {
            autorizar = Boolean.parseBoolean(request.getParameter("autorizar"));
        }

        for (AmpliacionReduccionMeta movMeta : ampReduccionMetaList) {
            out.print("<tr>");
            out.print("<td>" + movMeta.getMovOficioMeta().getRamoId() + "</td>");
            out.print("<td>" + movMeta.getMovOficioMeta().getMetaId() + "</td>");
            out.print("<td></td>");
            out.print("<td>"+numberF.format(movMeta.getEstimacion())+"</td>");
            out.print("<td>"+numberF.format(movMeta.getPropuestaEstimacion())+"</td>");
            if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || autorizar) {
                if(movMeta.getMovOficioMeta().getNva_creacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaAmpliacionReduccionTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaAmpliacionReduccionRecalendarizacionTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' /></td>");
                }
                out.print("<td><input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarAmpliacionReduccion(\"" + movMeta.getIdentificador() + "\",\"1\");' /></td>");
            } else {
                if(movMeta.getMovOficioMeta().getNva_creacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaAmpliacionReduccionTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaAmpliacionReduccionRecalendarizacionTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\")' /></td>");
                }
            }
            out.print("</tr>");
        }

        for (AmpliacionReduccionAccion movAccion : ampReduccionAccionList) {
            out.print("<tr>");
            out.print("<td>" + movAccion.getMovOficioAccion().getRamoId() + "</td>");
            out.print("<td>" + movAccion.getMovOficioAccion().getMetaId() + "</td>");
            out.print("<td>" + movAccion.getMovOficioAccion().getAccionId() + "</td>");
            out.print("<td>" + numberF.format(movAccion.getEstimacion()) + "</td>");
            out.print("<td>" + numberF.format(movAccion.getPropuestaEstimacion()) + "</td>");
            //out.print("<td>" + movAccion.getMovOficioAccion().getDeptoId() + "</td>");
            if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || autorizar) {
                if(movAccion.getMovOficioAccion().getNvaCreacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                        + "onclick='getInfoAccionAmpliacionReduccionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                        + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                        + "onclick='getInfoAccionAmpliacionReduccionRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                        + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }
                out.print("<td><input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarAmpliacionReduccion(\"" + movAccion.getIdentificador() + "\",\"2\");' /></td>");
            } else {
                if(movAccion.getMovOficioAccion().getNvaCreacion().equals("S")){
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                        + "onclick='getInfoAccionAmpliacionReduccionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                        + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }else{
                    out.print("<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                        + "onclick='getInfoAccionAmpliacionReduccionRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                        + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                        + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\" )' /></td>");
                }
            }
            out.print("</tr>");
        }
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

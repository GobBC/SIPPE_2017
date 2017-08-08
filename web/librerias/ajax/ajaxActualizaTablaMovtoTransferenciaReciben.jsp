<%-- 
    Document   : ajaxActualizaTablaMovtoTransferencia
    Created on : Feb 10, 2016, 11:33:36 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
    List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
    Transferencia transferencia = new Transferencia();
    MovimientosTransferencia movimiento = new MovimientosTransferencia();
    NumberFormat  dFormat = NumberFormat.getInstance(Locale.US);
    String strResult = new String();
    String estatus = new String();
    int transferenciaId = 0;
    
    boolean autorizar = false;
    try {
        if (Utilerias.existeParametro("estatus",request)) {
            estatus = request.getParameter("estatus");
        }
        if (Utilerias.existeParametro("transferenciaId",request)){
            transferenciaId = Integer.parseInt( request.getParameter("transferenciaId"));
        }
        if (session.getAttribute("transferencia") != null) {
            movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
            transMetaList = movimiento.getTransferenciaMetaList();
            transAccionList = movimiento.getTransferenciaAccionList();
        }

        if (Utilerias.existeParametro("autorizar",request)) {
            autorizar =  Boolean.parseBoolean(request.getParameter("autorizar"));
        }
         for (TransferenciaMeta movMeta : transMetaList) {
            strResult +="<tr>";
            strResult +="<td>" + movMeta.getMovOficioMeta().getRamoId() + "</td>";
            strResult +="<td>" + movMeta.getMovOficioMeta().getMetaId() + "</td>";
            strResult +="<td> - </td>";
            strResult +="<td> "+dFormat.format(movMeta.getEstimacion())+" </td>";
            strResult +="<td> "+dFormat.format(movMeta.getPropuestaEstimacion())+" </td>";
            if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || autorizar) {
                if (movMeta.getMovOficioMeta().getNva_creacion().equals("S")) {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoMetaTransferenciaTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' /></td>";
                } else {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' /></td>";
                }
                strResult +="<td><input type='button' class='btnbootstrap-drop btn-borrar' "
                            + "onclick='borrarTransferencia(\""+movMeta.getIdentificador()+"\",\"1\",\""+transferenciaId+"\");' /></td>";
            } else {
                if (movMeta.getMovOficioMeta().getNva_creacion().equals("S")) {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoMetaTransferenciaTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' /></td>";
                } else {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                            + "(\"" + movMeta.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movMeta.getMovOficioMeta().getRamoId() + "\","
                            + "\"" + movMeta.getMovOficioMeta().getMetaId() + "\",\"R\")' /></td>";
                }
            }
            strResult +="</tr>";
        }

        for (TransferenciaAccion movAccion : transAccionList) {
            strResult +="<tr>";
            strResult +="<td>" + movAccion.getMovOficioAccion().getRamoId() + "</td>";
            strResult +="<td>" + movAccion.getMovOficioAccion().getMetaId() + "</td>";
            strResult +="<td>" + movAccion.getMovOficioAccion().getAccionId() + "</td>";
            strResult +="<td> "+dFormat.format(movAccion.getEstimacion())+" </td>";
                strResult +="<td> "+dFormat.format(movAccion.getPropuestaEstimacion())+" </td>";
            //strResult +="<td>" + movAccion.getMovOficioAccion().getDeptoId() + "</td>");
            if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K") ||autorizar) {
                if (movAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoAccionTransferenciaTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\")' /></td>";
                } else {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\" )' /></td>";
                }
                strResult +="<td><input type='button' class='btnbootstrap-drop btn-borrar' "
                            + "onclick='borrarTransferencia(\""+movAccion.getIdentificador()+"\",\"2\",\""+transferenciaId+"\");' /></td>";
            } else {
                if (movAccion.getMovOficioAccion().getNvaCreacion().equals("S")) {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoAccionTransferenciaTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\" )' /></td>";
                } else {
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\"" + movAccion.getIdentificador() + "\",\"" + estatus + "\","
                            + "\"" + movAccion.getMovOficioAccion().getRamoId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getMetaId() + "\","
                            + "\"" + movAccion.getMovOficioAccion().getAccionId() + "\",\"R\")' /></td>";
                }
            }
            strResult +="</tr>";
        }
        strResult +="<tr><td><input type='hidden' id='cambio-movto' value='true'/></td></tr>";
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
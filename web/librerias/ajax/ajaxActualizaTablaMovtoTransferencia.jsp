<%-- 
    Document   : ajaxActualizaTablaMovtoTransferencia
    Created on : Feb 10, 2016, 11:33:36 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
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
    MovimientosTransferencia movimiento = new MovimientosTransferencia();
    NumberFormat  dFormat = NumberFormat.getInstance(Locale.US);
    String strResult = new String();
    String estatus = new String();
    
    boolean autorizar = false;
    try{
        if (session.getAttribute("transferencia") != null) {
            movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
            transMetaList = movimiento.getTransferenciaMetaList();
            transAccionList = movimiento.getTransferenciaAccionList();
        }
        if (Utilerias.existeParametro("estatus",request)) {
            estatus =  request.getParameter("estatus");
        }
        
        if (Utilerias.existeParametro("autorizar",request)) {
            autorizar =  Boolean.parseBoolean(request.getParameter("autorizar"));
        }

        for(TransferenciaMeta transMeta : transMetaList){
            if(transMeta.getMovOficioMeta().getNva_creacion().equals("N")){
                strResult += "<tr>";
                strResult +="<td>" + transMeta.getMovOficioEstimacion().get(0).getRamo() + "</td>";
                strResult +="<td>" + transMeta.getMovOficioEstimacion().get(0).getPrograma()+ "</td>";
                //strResult +="<td>" + transMeta.getMovOficioEstimacion().get(0).get)+transMeta.getMetaInfo().getProyId() + "</td>";
                strResult +="<td>" + transMeta.getMovOficioEstimacion().get(0).getMeta()+ "</td>";
                strResult +="<td> - </td>";
                strResult +="<td> "+dFormat.format(transMeta.getEstimacion())+" </td>";
                strResult +="<td> "+dFormat.format(transMeta.getPropuestaEstimacion())+" </td>";
                //strResult +="<td> - </td>";
                if(estatus.equals("X") || estatus.equals("R") || estatus.equals("K") || autorizar ){
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                                            + "(\""+transMeta.getIdentificador()+"\",\""+estatus+"\","
                                            + "\""+transMeta.getMovOficioEstimacion().get(0).getRamo()+"\","
                                            + "\""+transMeta.getMovOficioEstimacion().get(0).getMeta()+"\",\"T\")' /></td>";
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-borrar' "
                            + "onclick='borrarTransferencia(\""+transMeta.getIdentificador()+"\",\"1\",\"-1\");' /></td>";
                }else{
                    strResult +="<td><input type='button' class='btnbootstrap-drop btn-ver' "
                            + "onclick='getInfoMetaTransferenciaRecalendarizacionTabla"
                                            + "(\""+transMeta.getIdentificador()+"\",\""+estatus+"\","
                                            + "\""+transMeta.getMovOficioEstimacion().get(0).getRamo()+"\","
                                            + "\""+transMeta.getMovOficioEstimacion().get(0).getMeta()+"\",\"T\")' /></td>";
                }
                strResult +="</tr>";
            }
        }
        for(TransferenciaAccion reprogAccion : transAccionList){
            if(reprogAccion.getMovOficioAccion().getNvaCreacion().equals("N")){
                strResult += "<tr>";
                strResult +="<td>" + reprogAccion.getMovOficioAccion().getRamoId() + "</td>";
                strResult +="<td>" + reprogAccion.getMovOficioAccion().getProgramaId()+ "</td>";
                //strResult +="<td> - </td>";
                strResult +="<td>" + reprogAccion.getMovOficioAccion().getMetaId() + "</td>";
                strResult +="<td> "+reprogAccion.getMovOficioAccion().getAccionId()+" </td>";                
                strResult +="<td> "+dFormat.format(reprogAccion.getEstimacion())+" </td>";
                strResult +="<td> "+dFormat.format(reprogAccion.getPropuestaEstimacion())+" </td>";
                //strResult +="<td> "+reprogAccion.getMovOficioAccion().getDeptoId()+" </td>";
                if(estatus.equals("X") || estatus.equals("R") || estatus.equals("K") ||  autorizar){
                    strResult += "<td><input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\""+reprogAccion.getIdentificador()+"\",\""+estatus+"\","
                            + "\""+reprogAccion.getMovOficioAccion().getRamoId()+"\","
                            + "\""+reprogAccion.getMovOficioAccion().getMetaId()+"\","
                            + "\""+reprogAccion.getMovOficioAccion().getAccionId()+"\",\"T\" )' /></td>";
                    strResult += "<td><input type='button' class='btnbootstrap-drop btn-borrar' "
                            + "onclick='borrarTransferencia(\""+reprogAccion.getIdentificador()+"\",\"2\",\"-1\");' /></td>";
                }else{
                    strResult += "<td><input type='button' class='btnbootstrap-drop btn-ver' "
                             + "onclick='getInfoAccionTransferenciaRecalendarizacionTabla(\""+reprogAccion.getIdentificador()+"\",\""+estatus+"\","
                             + "\""+reprogAccion.getMovOficioAccion().getRamoId()+"\","
                             + "\""+reprogAccion.getMovOficioAccion().getMetaId()+"\","
                             + "\""+reprogAccion.getMovOficioAccion().getAccionId()+"\", \"T\")' /></td>";
                }
                strResult +="</tr>";
            }
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
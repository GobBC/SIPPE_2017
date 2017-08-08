<%-- 
    Document   : ajaxActualizaTablaMovimientos
    Created on : Dec 10, 2015, 9:03:06 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
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
    MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();
    String strResult = new String();
    String estatus = new String();
    int cont = 0;
    try{
        if (session.getAttribute("recalendarizacion") != null) {
            movimiento = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
        }
        if(Utilerias.existeParametro("estatus", request)){
            estatus = request.getParameter("estatus");
        }
        recalMetaList = movimiento.getMovEstimacionList();
        recalAccionList = movimiento.getMovAccionEstimacionList();

        for(RecalendarizacionMeta movMeta : recalMetaList){
            strResult += "<tr>";
            strResult +="<td>" + movMeta.getMovEstimacionList().get(cont).getRamo() + "</td>";
            strResult +="<td>" + movMeta.getMovEstimacionList().get(cont).getPrograma() + "</td>";
            strResult +="<td>" + movMeta.getMovEstimacionList().get(cont).getMeta() + "</td>";
            strResult +="<td>-</td>";
            strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoMetaEstimacionTabla"
                                        + "(\""+movMeta.getIdentificado()+"\",\""+estatus+"\","
                                        + "\""+movMeta.getMovEstimacionList().get(cont).getRamo()+"\","
                                        + "\""+movMeta.getMovEstimacionList().get(cont).getMeta()+"\")' /></td>";
            strResult +="<td><input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarRecalendarizacion(\""+movMeta.getIdentificado()+"\",\"1\");'/></td>";
            strResult +="</tr>";
            cont ++;
        }
        cont = 0;
        for(RecalendarizacionAccion movAccion : recalAccionList){
            strResult +="<tr>";
            strResult +="<td>" + movAccion.getMovEstimacionList().get(cont).getRamo() + "</td>";
            strResult +="<td>" + movAccion.getMovEstimacionList().get(cont).getPrograma() + "</td>";
            strResult +="<td>" + movAccion.getMovEstimacionList().get(cont).getMeta() + "</td>";
            strResult +="<td>" + movAccion.getMovEstimacionList().get(cont).getAccion() + "</td>";
            strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoAccionEstimacionTabla(\""+movAccion.getIdentificado()+"\",\""+estatus+"\","
                                        + "\""+movAccion.getMovEstimacionList().get(cont).getRamo()+"\","
                                        + "\""+movAccion.getMovEstimacionList().get(cont).getMeta()+"\","
                                        + "\""+movAccion.getMovEstimacionList().get(cont).getAccion()+"\" )' /></td>";
            
            strResult +="<td><input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarRecalendarizacion(\""+movAccion.getIdentificado()+"\",\"2\");'/></td>";
            strResult +="</tr>";
            cont ++;
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

<%-- 
    Document   : ajaxActualizaMovimientos
    Created on : Dec 10, 2015, 9:03:06 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
    List<Transferencia> transfDeleteList = new ArrayList<Transferencia>();
    NumberFormat  dFormat = NumberFormat.getInstance(Locale.US);
    MovimientosTransferencia movimiento = new MovimientosTransferencia();
    TransferenciaBean transBean = null;
    BigDecimal totalImporte = new BigDecimal(0.0);
    String strResult = new String();
    String estatus = new String();
    String tipoDependencia = new String();
    boolean isParaestatal = false;
    try{
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("transferencia") != null) {
            movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");            
            transferenciaList = movimiento.getTransferenciaList();
        }
        if (session.getAttribute("tipoDependencia") != null) {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");     
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = request.getParameter("estatus");
        }
        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer(
                ( request.getHeader("Host")));
        transBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        transBean.resultSQLConecta(tipoDependencia);
        isParaestatal = transBean.isParaestatal();
        for (Transferencia trasnferencia : transferenciaList) {
            if(trasnferencia.getRamo() != null){
                totalImporte = totalImporte.add(new BigDecimal(trasnferencia.getImporte()));
                out.print("<tr>");
                out.print("<td></td>");
                out.print("<td>" + trasnferencia.getRamo() + "</td>");
                out.print("<td>" + trasnferencia.getDepto() + "</td>");
                out.print("<td>" + trasnferencia.getPrograma() + "</td>");
                out.print("<td>" + trasnferencia.getTipoProy() + "-" + trasnferencia.getProyecto() + "</td>");
                out.print("<td>" + trasnferencia.getMeta() + "</td>");
                out.print("<td>" + trasnferencia.getAccion() + "</td>");
                out.print("<td>" + trasnferencia.getPartida() + "</td>");
                out.print("<td>" + trasnferencia.getFuente() + "."
                        + trasnferencia.getFondo() + "."
                        + trasnferencia.getRecurso() + "</td>");
                out.print("<td>" + trasnferencia.getRelLaboral() + "</td>");
                out.print("<td>" + dFormat.format(trasnferencia.getQuincePor()) + "</td>");
                out.print("<td>" + dFormat.format(trasnferencia.getAcumulado()) + "</td>");
                out.print("<td>" + dFormat.format(trasnferencia.getImporte()) + "<input type='button' class='btnbootstrap-drop btn-money'"
                                            + "onClick='editarImporteTrans(\""+trasnferencia.getConsec()+"\")' /></td>");            
                out.print("<td>" + dFormat.format(transBean.getImporteDisponible(trasnferencia.getImporte(), trasnferencia)) + "</td>");
                if(!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()){
                    out.print("<td>" + dFormat.format(trasnferencia.getDisponible()) + "</td>");
                    out.print("<td>" + dFormat.format(trasnferencia.getDisponibleAnual()) + "</td>");
                }
                if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {
                    out.print("<td><a onClick='mostrarProgramacion(\""+trasnferencia.getRamo()+"\","+trasnferencia.getMeta()+","+trasnferencia.getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                            + "<input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoEdicionTransferencia(\"" + trasnferencia.getRamo() + "\",\"" + trasnferencia.getPrograma() + "\","
                            + "\"" + trasnferencia.getProyecto() + "," + trasnferencia.getTipoProy() + "\",\"" + trasnferencia.getMeta() + "\","
                            + "\"" + trasnferencia.getAccion() + "\",\"" + trasnferencia.getPartida() + "\","
                            + "\"" + trasnferencia.getFuente() + "." + trasnferencia.getFondo() + "." + trasnferencia.getRecurso() + "\",\"" + trasnferencia.getRelLaboral() + "\","
                            + "\"" + trasnferencia.getConsec() + "\")' />");
                    out.print("<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarTransferencia(\"" + trasnferencia.getConsec()+ "\",\"3\",\"-1\");' /></td>");
                } else {
                    out.print("<td><a onClick='mostrarProgramacion(\""+trasnferencia.getRamo()+"\","+trasnferencia.getMeta()+","+trasnferencia.getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                            + "<input type='button' class='btnbootstrap-drop btn-ver' "
                            + "onclick='getInfoEdicionTransferencia(\"" + trasnferencia.getRamo() + "\",\"" + trasnferencia.getPrograma() + "\","
                            + "\"" + trasnferencia.getTipoProy() + "," + trasnferencia.getProyecto() + "\",\"" + trasnferencia.getMeta() + "\","
                            + "\"" + trasnferencia.getAccion() + "\",\"" + trasnferencia.getPartida() + "\","
                            + "\"" + trasnferencia.getFuente() + "." + trasnferencia.getFondo() + "." + trasnferencia.getRecurso() + "\",\"" + trasnferencia.getRelLaboral() + "\","
                            + "\"" + trasnferencia.getConsec()+ "\")' />");
                }
                out.print("</tr>");
            }else{
                transfDeleteList.add(trasnferencia);
            }
        }
        out.print("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><label>TOTAL:</label></td><td>" + dFormat.format(totalImporte.doubleValue()) + "</td></tr>");
        for(Transferencia transferencia : transfDeleteList){
            transferenciaList.remove(transferencia);
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
        if(transBean != null){
            transBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>

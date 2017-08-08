<%-- 
    Document   : ajaxActualizaMovimientos
    Created on : Dec 10, 2015, 9:03:06 AM
    Author     : ugarcia
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosRecalendarizacion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    List<RecalendarizacionAccionReq> recalAccionRecList = new ArrayList<RecalendarizacionAccionReq>();
    MovimientosRecalendarizacion movimiento = new MovimientosRecalendarizacion();
    NumberFormat  dFormat = NumberFormat.getInstance(Locale.US);
    BigDecimal sumaReq = new BigDecimal(0.0);
    BigDecimal sumaRecal = new BigDecimal(0.0);
    String strResult = new String();
    String estatus = new String();
    try{
        if (session.getAttribute("recalendarizacion") != null) {
            movimiento = (MovimientosRecalendarizacion) session.getAttribute("recalendarizacion");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus =  request.getParameter("estatus");
        }
        recalAccionRecList = movimiento.getMovOficiosAccionReq();

        for(RecalendarizacionAccionReq movAccionReq : recalAccionRecList){
            strResult += "<tr>";
            strResult +="<td>" + movAccionReq.getMovAccionReq().getRamo() + "</td>";
            strResult +="<td>" + movAccionReq.getMovAccionReq().getPrograma() + "</td>";
            strResult +="<td>" + movAccionReq.getMovAccionReq().getMeta() + "</td>";
            strResult +="<td>" + movAccionReq.getMovAccionReq().getAccion() + "</td>";
            strResult +="<td>"+movAccionReq.getMovAccionReq().getDepto()+"</td>";
            strResult +="<td>" + movAccionReq.getMovAccionReq().getPartida() + "</td>";
            strResult +="<td>" + movAccionReq.getMovAccionReq().getFuente()+"."+
                    movAccionReq.getMovAccionReq().getFondo()+ "." + 
                    movAccionReq.getMovAccionReq().getRecurso()+ "</td>";
            if(movAccionReq.getMovAccionReq().getRelLaboral().equals("-1"))
                strResult +="<td>0</td>";
            else
                strResult +="<td>" + movAccionReq.getMovAccionReq().getRelLaboral() + "</td>";
            strResult +="<td>" + movAccionReq.getMovAccionReq().getRequerimiento() + "</td>";
            strResult +="<td>" + dFormat.format(movAccionReq.getMovAccionReq().getCostoAnual()) + "</td>";
            strResult +="<td>" + dFormat.format(movAccionReq.getMovAccionReq().getRecalendarizado()) + "</td>";
            /*strResult +="<td><input type='button' class='btnbootstrap-drop btn-edicion' onclick='getInfoEdicionAccionRequerimiento(\""+movAccionReq.getIdentificador()+"\",\""+estatus+"\",\""+
                                        movAccionReq.getMovAccionReq().getRamo()+"\",\""+movAccionReq.getMovAccionReq().getPrograma()+"\",\""+movAccionReq.getMovAccionReq().getMeta()+"\","
                                        + "\""+movAccionReq.getMovAccionReq().getAccion()+"\",\""+movAccionReq.getMovAccionReq().getRequerimiento()+"\")' />";
            strResult +="<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarRecalendarizacion(\""+movAccionReq.getIdentificador()+"\",\"3\");'/></td>";
            */
            strResult +="<td><a onClick='mostrarProgramacion(\""+movAccionReq.getMovAccionReq().getRamo()+"\","+movAccionReq.getMovAccionReq().getMeta()+","+movAccionReq.getMovAccionReq().getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                    + "<input type='button' class='btnbootstrap-drop btn-edicion' " + "onclick='getInfoEdicionAccionRequerimiento(\"" + movAccionReq.getIdentificador() + "\",\"" + estatus + "\",\"" + movAccionReq.getMovAccionReq().getRamo() + "\",\"" + movAccionReq.getMovAccionReq().getPrograma() + "\",\"" + movAccionReq.getMovAccionReq().getMeta() + "\"," + "\"" + movAccionReq.getMovAccionReq().getAccion() + "\",\"" + movAccionReq.getMovAccionReq().getRequerimiento() + "\",\"" + movAccionReq.getMovAccionReq().getFuente() + "." + movAccionReq.getMovAccionReq().getFondo() + "." + movAccionReq.getMovAccionReq().getRecurso() + "\",\"" + movAccionReq.getMovAccionReq().getPartida() +  "\",\"" + movAccionReq.getMovAccionReq().getRelLaboral() + "\")' />";
            strResult +="</tr>";
            sumaReq = sumaReq.add(new BigDecimal(movAccionReq.getMovAccionReq().getCostoAnual()));
            sumaRecal = sumaRecal.add(new BigDecimal(movAccionReq.getMovAccionReq().getRecalendarizado()));
        }
        sumaReq = sumaReq.setScale(2, RoundingMode.HALF_UP);
        strResult += "<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><b>Total:</b></td><td class='tdCosto'><b>" + dFormat.format(sumaReq.doubleValue()) + "<b></td><td class='tdCosto'><b>" + dFormat.format(sumaRecal.doubleValue()) + "<b></td><td></td></tr>";
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

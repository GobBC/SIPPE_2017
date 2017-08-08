<%-- 
    Document   : ajaxActualizaMovimientos
    Created on : Dec 10, 2015, 9:03:06 AM
    Author     : ugarcia
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<TransferenciaAccionReq> transAccionReqList = new ArrayList<TransferenciaAccionReq>();
    TransferenciaBean transBean = null;
    Transferencia transferencia = new Transferencia();
    MovimientosTransferencia movimiento = new MovimientosTransferencia();
    NumberFormat dFormat = NumberFormat.getInstance(Locale.US);
    BigDecimal totalImporte = new BigDecimal(0.0);
    String strResult = new String();
    String estatus = new String();
    String tipoDependencia = new String();
    int transferenciaId = -1;
    boolean isParaestatal = false;
    try{
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("transferencia") != null) {
            movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
        }
        if (session.getAttribute("tipoDependencia") != null) {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
            estatus = request.getParameter("estatus");
        }
        if (request.getParameter("transferenciaId") != null && !request.getParameter("transferenciaId").equals("")) {
            transferenciaId = Integer.parseInt( request.getParameter("transferenciaId"));
        }
        for(Transferencia transTemp : movimiento.getTransferenciaList()){
            if(transTemp.getConsec() == transferenciaId){
                transferencia = transTemp;
            }
        }
        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer(
                ( request.getHeader("Host")));
        transBean.setStrUbicacion(getServletContext().getRealPath("").toString());
        transBean.resultSQLConecta(tipoDependencia);
        isParaestatal = transBean.isParaestatal();
        transAccionReqList = transferencia.getTransferenciaAccionReqList();
        for (TransferenciaAccionReq transf : transAccionReqList) {
            totalImporte = totalImporte.add(new BigDecimal(transf.getImporte()));
            strResult += "<tr>";
            strResult += "<td></td>";
            strResult += "<td>" + transf.getRamo() + "</td>";
            strResult += "<td>" + transf.getDepto() + "</td>";
            strResult += "<td>" + transf.getPrograma() + "</td>";
            strResult += "<td>" + transf.getTipoProy() + "-" + transf.getProy() + "</td>";
            strResult += "<td>" + transf.getMeta() + "</td>";
            strResult += "<td>" + transf.getAccion() + "</td>";
            strResult += "<td>" + transf.getPartida() + "</td>";
            strResult += "<td>" + transf.getFuente() + "."
                    + transf.getFondo() + "."
                    + transf.getRecurso() + "</td>";
            strResult += "<td>" + transf.getRelLaboral() + "</td>";
            strResult += "<td>" + dFormat.format(transf.getQuincePor()) + "</td>";
            strResult += "<td>" + dFormat.format(transf.getAcumulado()) + "</td>";
            strResult += "<td>" + dFormat.format(transf.getImporte()) + "</td>";
            if(!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()){
                strResult += "<td>" + dFormat.format(transf.getDisponible()) + "</td>";
                strResult += "<td>" + dFormat.format(transf.getDisponibleAnual()) + "</td>";
            }
            if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {
                strResult +="<td><a onClick='mostrarProgramacion(\""+transf.getRamo()+"\","+transf.getMeta()+","+transf.getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                        + "<input type='button' class='btnbootstrap-drop btn-edicion' "
                            + "onclick='getInfoEdicionAccionRequerimientoTransferencia(\"" + transf.getIdentidicador() + "\",\"" + estatus + "\",\""
                            + transf.getRamo() + "\",\"" + transf.getPrograma() + "\",\"" + transf.getMeta() + "\","
                            + "\"" + transf.getAccion() + "\",\"" + transf.getConsecutivo() + "\",\"" + transf.getImporte() + "\")' />";
                strResult += "<input type='button' class='btnbootstrap-drop btn-borrar' onclick='borrarTransferencia(\""+transf.getIdentidicador()+"\",\"6\",\""+transferenciaId+"\");' /></td>";
            } else {
                strResult += "<td><a onClick='mostrarProgramacion(\""+transf.getRamo()+"\","+transf.getMeta()+","+transf.getAccion()+")'><span class='fa fa-calendar botones-img'></span></a>"
                        + "<input type='button' class='btnbootstrap-drop btn-ver' "
                            + "onclick='getInfoEdicionAccionRequerimientoTransferencia(\"" + transf.getMovOficioAccionReq() + "\",\"" + estatus + "\",\""
                            + transf.getRamo() + "\",\"" + transf.getPrograma() + "\",\"" + transf.getMeta() + "\","
                            + "\"" + transf.getAccion() + "\",\"" + transf.getConsecutivo()+ "\",\"" + transf.getImporte() + "\")' /></td>";
            }
            strResult += "</tr>";
        }
        strResult += "<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td><label>TOTAL:</label></td><td>" + dFormat.format(totalImporte.doubleValue()) + "</td></tr>";
        strResult +="<tr><td><input type='hidden' id='cambio-transferencia' value='true'/></td></tr>";
    }catch (Exception ex) {
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

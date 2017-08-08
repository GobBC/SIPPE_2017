<%-- 
    Document   : ajaxGetImporteReduccion
    Created on : Jan 28, 2016, 11:17:45 AM
    Author     : ugarcia
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    TransferenciaBean transBean = null;
    NumberFormat numberF = NumberFormat.getInstance(Locale.US);
    List<Transferencia> transferenciaReqList = new ArrayList<Transferencia>();
    MovimientosTransferencia movimiento = new MovimientosTransferencia();
    Transferencia transferencia = new Transferencia();
    Date fecha = new Date();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String date = new String();
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String programa = new String();
    String estatus = new String();
    boolean isParaestatal = false;
    int mesActual = 1;
    int identificador = 0;
    int meta = 0;
    int accion = 0;
    int year = 0;
    int folio = 0;
    try{
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramo =  request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null && !request.getParameter("programa").equals("")) {
            programa =  request.getParameter("programa");
        }
        if (request.getParameter("meta") != null && !request.getParameter("meta").equals("")) {
            meta = Integer.parseInt( request.getParameter("meta"));
        }
        if (request.getParameter("accion") != null && !request.getParameter("accion").equals("")) {
            accion = Integer.parseInt( request.getParameter("accion"));
        }
        
        if (Utilerias.existeParametro("estatus", request)) {
            estatus = request.getParameter("estatus");
        }
        
        if (session.getAttribute("transferencia") != null) {
            movimiento = (MovimientosTransferencia) session.getAttribute("transferencia");
            transferenciaReqList = movimiento.getTransferenciaList();
        }
        if (session.getAttribute("tipoDependencia") != null) {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }  
        if (session.getAttribute("year") != null) {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }  
        if(Utilerias.existeParametro("folio", request)){
            folio = Integer.parseInt( request.getParameter("folio"));
        }
        if(Utilerias.existeParametro("identificador", request)){
            identificador = Integer.parseInt( request.getParameter("identificador"));
        }
        for(Transferencia transTemp : transferenciaReqList){
            if(transTemp.getConsec() == identificador){
                transferencia = transTemp;
            }
        }
        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer(request.getHeader("host"));
        transBean.setStrUbicacion(getServletContext().getRealPath(""));
        transBean.resultSQLConecta(tipoDependencia);
        isParaestatal = transBean.isParaestatal();
        fecha = transBean.getResultSQLgetServerDate();
        date = df.format(fecha);
        mesActual  = Integer.parseInt(date.split("/")[1]);
        if(folio != 0)
            transferencia.setDisponible(transBean.getDisponible(year, transferencia.getRamo(), transferencia.getPrograma(), transferencia.getTipoProy(),
                    transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(), transferencia.getPartida(), transferencia.getFuente(),
                    transferencia.getFondo(), transferencia.getRecurso(), transferencia.getRelLaboral(), transBean.getMonthTramite(folio)));
        else
            transferencia.setDisponible(transBean.getDisponible(year, transferencia.getRamo(), transferencia.getPrograma(), transferencia.getTipoProy(),
                    transferencia.getProyecto(), transferencia.getMeta(), transferencia.getAccion(), transferencia.getPartida(), transferencia.getFuente(),
                    transferencia.getFondo(), transferencia.getRecurso(), transferencia.getRelLaboral(), mesActual));
        strResultado += "<div id='importeReduccion'>";
        if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {
            if(!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()){ 
                strResultado += "<label> Disponible: </label>";
                strResultado += "<input id='inpTxtDisponible' value='"+numberF.format(transferencia.getDisponible())+"' disabled/>";
            }
            strResultado += "<label> Importe: </label>";
            strResultado += "<input id='inpTxtImporte' value='"+numberF.format(transferencia.getImporte())+"' onblur='agregarFormato(\"inpTxtImporte\");' onkeyup='validaMascara(\"inpTxtImporte\")'/>";
        }else{
            if(!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()){ 
                strResultado += "<label> Disponible: </label>";
                strResultado += "<input id='inpTxtDisponible' disabled value='"+numberF.format(transferencia.getDisponible())+"' disabled/>";
            }
            strResultado += "<label> Importe: </label>";
            strResultado += "<input id='inpTxtImporte' disabled value='"+numberF.format(transferencia.getImporte())+"' onblur='agregarFormato(\"inpTxtImporte\");' onkeyup='validaMascara(\"inpTxtImporte\")'/>";
        }
        strResultado += "<div id='btnsEdicion'>";
        if(!isParaestatal || transBean.getResultSqlGetIsAyuntamiento()){
            isParaestatal = false;
        }else{
            isParaestatal = true;
        }
        if (estatus.equals("X") || estatus.equals("R") || estatus.equals("K")) {
            strResultado += "<center><input type='button' value='Editar' onclick='editTransferencia(\""+identificador+"\","+isParaestatal+")'/>";
            strResultado += "<input type='button' value='Cancelar' onclick='$(\"#PopUpZone\").fadeOut();'/></center>";
        }else{
            strResultado += "<center><input type='button' value='Cerrar' onclick='$(\"#PopUpZone\").fadeOut();'/></center>";
        }
        strResultado += "</div>";
        strResultado += "</div>";
        strResultado += " <input type='hidden' value='" + ramo + "' id='popRamo' />";
        strResultado += " <input type='hidden' value='" + programa + "' id='popPrg' />";
        strResultado += " <input type='hidden' value='" + meta + "' id='popMeta' />";
        strResultado += " <input type='hidden' value='" + accion + "' id='popAccion' />";
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (transBean != null) {
            transBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
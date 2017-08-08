<%-- 
    Document   : ajaxChangeConsideraTransAmp
    Created on : Jul 26, 2016, 3:45:07 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utileria"%>
<%@page import="gob.gbc.entidades.RecalendarizacionAccionReq"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>
<%@page import="gob.gbc.entidades.MovOficiosAccionReq"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Requerimiento"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%


    MovOficiosAccionReq movAccReq = null;
    AmpliacionReduccionBean ampRecalBean = null;
    MovimientosAmpliacionReduccion amplReduccion = null;
    MovimientosTransferencia transferencia = null;
    Requerimiento requerimiento = new Requerimiento();

    List<RecalendarizacionAccionReq> recalAccionReq = new ArrayList<RecalendarizacionAccionReq>();

    String considerar = new String();
    String strResultado = new String();
    String tipoDependencia = new String();
    String movimiento = new String();

    int folio = 0;
    int consec = -1;
    int tipoMovimiento = -1;
    
    boolean bandera = false;

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");

    try {

        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (Utilerias.existeParametro("consec",request)) {
            consec = Integer.parseInt(request.getParameter("consec"));
        }
        if (Utilerias.existeParametro("folio",request)) {
            folio = Integer.parseInt(request.getParameter("folio"));
        }
        if (Utilerias.existeParametro("tipoMovimiento", request)) {
            tipoMovimiento = Integer.parseInt(request.getParameter("tipoMovimiento"));
        }
        if (Utilerias.existeParametro("considerar",request)) {
            considerar = request.getParameter("considerar");
        }

        ampRecalBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRecalBean.setStrServer(request.getHeader("host"));
        ampRecalBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRecalBean.resultSQLConecta(tipoDependencia);

        if(tipoMovimiento == 0){
            if(session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "")
                amplReduccion = (MovimientosAmpliacionReduccion)session.getAttribute("ampliacionReduccion");
            if(consec > -1){
                for(AmpliacionReduccionAccionReq ampRed : amplReduccion.getAmpReducAccionReqList()){
                    if(ampRed.getConsecutivo() == consec){
                        if (ampRecalBean.getResultSQLUpdateConsiderarTransAmp("AMPLIACIONES",considerar, folio,consec)) {
                            ampRed.setConsiderar(considerar);
                            strResultado = "Se logro modificar el campo considerar del movimiento";
                            bandera = true;
                        }else{                            
                            strResultado = "No se logro modificar el campo considerar del movimiento";
                            bandera = false;
                        }                            
                        break;
                    }
                }
                if(bandera)
                    session.setAttribute("ampliacionReduccion", amplReduccion);
            }
        }else{
            if(session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "")
                transferencia = (MovimientosTransferencia)session.getAttribute("transferencia");
            if(consec > -1){
                for(Transferencia trans : transferencia.getTransferenciaList()){
                    if(trans.getConsec() == consec){
                        if (ampRecalBean.getResultSQLUpdateConsiderarTransAmp("TRANSFERENCIAS",considerar, folio,consec)) {
                            trans.setConsiderar(considerar);
                            strResultado = "Se logro modificar el campo considerar del movimiento";
                            bandera = true;
                        }else{                            
                            strResultado = "No se logro modificar el campo considerar del movimiento";
                            bandera = false;
                        }                            
                        break;
                    }
                }
                if(bandera)
                    session.setAttribute("transferencia", transferencia);
            }
        }
        if(bandera)
            ampRecalBean.transaccionCommit();
        else
            ampRecalBean.transaccionRollback();
    } catch (Exception ex) {
        strResultado = "No se logro modificar el campo considerar del requerimiento";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (ampRecalBean != null) {
            ampRecalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>


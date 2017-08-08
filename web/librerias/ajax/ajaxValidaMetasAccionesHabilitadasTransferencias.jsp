<%-- 
    Document   : ajaxValidaMetasAccionesHabilitadasTransferencias
    Created on : Jun 28, 2016, 10:34:41 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
    AutorizacionBean autBean = null;
    MovimientosTransferencia movTransf = new MovimientosTransferencia();
    
    List<Transferencia> transferenciaList = null;
    
    String strResultado = new String();
    String tipoDependencia = new String();
    
    boolean isUsuario = false;
    boolean bandera = false;
    
    double totalMeta = 0.0;
    double totalAccion = 0.0;
    
    int year = 0;
    int oficio = 0;
    
    
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String)session.getAttribute("year"));
        }
        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movTransf = (MovimientosTransferencia) session.getAttribute("transferencia");
        }
        if(Utilerias.existeParametro("isUsuario", request)){
            isUsuario = Boolean.parseBoolean(request.getParameter("isUsuario"));
        }
        if(Utilerias.existeParametro("oficio", request)){
            oficio = Integer.parseInt(request.getParameter("oficio"));
        }
        autBean = new AutorizacionBean(tipoDependencia);
        autBean.setStrServer(request.getHeader("host"));
        autBean.setStrUbicacion(getServletContext().getRealPath(""));
        autBean.resultSQLConecta(tipoDependencia);
        transferenciaList = movTransf.getTransferenciaList();
        
        for(Transferencia trans : transferenciaList){
            totalMeta = 0.0;
            totalAccion = 0.0;
            for(Transferencia transT : transferenciaList){
                if(trans.getRamo().equals(transT.getRamo()) &&
                        trans.getMeta() == transT.getMeta())
                    totalMeta += (transT.getImporte() * -1);
            }
            for(Transferencia transT : transferenciaList){
                if(trans.getRamo().equals(transT.getRamo()) &&
                        trans.getMeta() == transT.getMeta() &&
                        trans.getAccion()== transT.getAccion() )
                    totalAccion += (transT.getImporte() * -1);
            }
            if(!autBean.getResultSQLValidaMetaInhabilitadaTransfrec(trans.getRamo(), trans.getMeta(), oficio))
                continue;
            else
                if(!autBean.getResultSQLValidaAccionInhabilitadaTransfrec(trans.getRamo(), trans.getMeta(),trans.getAccion(),trans.getOficio()))
                    continue;
                else{
                    if(autBean.validaMetaInhabilitada(year,trans.getRamo(),trans.getMeta(),totalMeta, isUsuario) && trans.getMeta() > 0){
                        bandera = true;
                        strResultado = trans.getRamo()+"-"+trans.getMeta()+"-"+trans.getAccion()+"-"+trans.getPartida();
                        break;
                    }
                    if(!bandera && trans.getAccion() > 0){
                        if(autBean.validaAccionInhabilitada(year, trans.getRamo(), trans.getMeta(),
                                trans.getAccion(),totalAccion, isUsuario)){
                            bandera = true;
                            break;
                        }
                    }
                    if(bandera)
                        break;
                }
        }
        //bandera = false;
        if(bandera){
            strResultado = "2|"+strResultado;
        }else{
            if(!isUsuario)
                strResultado = "0";
            else
                strResultado = "3";
        }
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (autBean != null) {
            autBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
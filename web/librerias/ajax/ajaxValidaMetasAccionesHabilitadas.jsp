<%-- 
    Document   : ajaxValidaMetasAccionesHabilitadas
    Created on : Jun 28, 2016, 10:34:41 AM
    Author     : ugarcia
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccionReq"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.entidades.MovOficioEstimacion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionMeta"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
    AutorizacionBean autBean = null;
    MovimientosAmpliacionReduccion movAmpRed = new MovimientosAmpliacionReduccion();
    
    List<AmpliacionReduccionAccionReq> accionReqList = null;
    
    String strResultado = new String();
    String tipoDependencia = new String();
    
    boolean isUsuario = false;
    boolean bandera = false;
    boolean isAuth = false;
    
    double totalMeta = 0.0;
    double totalAccion = 0.0;
    
    int year = 0;
    
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String)session.getAttribute("year"));
        }
        if (session.getAttribute("ampliacionReduccion") != null && session.getAttribute("ampliacionReduccion") != "") {
            movAmpRed = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
        }
        if(Utilerias.existeParametro("isUsuario", request)){
            isUsuario = Boolean.parseBoolean(request.getParameter("isUsuario"));
        }
        autBean = new AutorizacionBean(tipoDependencia);
        autBean.setStrServer(request.getHeader("host"));
        autBean.setStrUbicacion(getServletContext().getRealPath(""));
        autBean.resultSQLConecta(tipoDependencia);
                
        accionReqList = movAmpRed.getAmpReducAccionReqList();
        for(AmpliacionReduccionAccionReq amplRed : accionReqList){
            totalMeta = 0.0;
            totalAccion = 0.0;
            for(AmpliacionReduccionAccionReq amplRedT : accionReqList){
                if(amplRed.getRamo().equals(amplRedT.getRamo()) &&
                        amplRed.getMeta() == amplRedT.getMeta())
                    totalMeta += (amplRedT.getImporte());
            }
            for(AmpliacionReduccionAccionReq amplRedT : accionReqList){
                if(amplRed.getRamo().equals(amplRedT.getRamo()) &&
                        amplRed.getMeta() == amplRedT.getMeta() &&
                        amplRed.getAccion()== amplRedT.getAccion() )
                    totalAccion += (amplRedT.getImporte());
            }
            if(!isUsuario)
                isAuth = true;
            
            if(amplRed.getTipoMovAmpRed().equals("C") && !isUsuario){
                isUsuario = true;
                isAuth = true;
            }
            if(!amplRed.getTipoMovAmpRed().equals("A") && !amplRed.getTipoMovAmpRed().equals("C")) {
                if(autBean.validaMetaInhabilitada(year,amplRed.getRamo(),amplRed.getMeta(),totalMeta, isUsuario) && amplRed.getMeta() > 0){
                    bandera = true;
                    strResultado = amplRed.getRamo()+"-"+amplRed.getMeta()+"-"+amplRed.getAccion()+"-"+amplRed.getPartida();
                }
                if(!bandera && amplRed.getAccion() > 0 ){
                    if(autBean.validaAccionInhabilitada(year, amplRed.getRamo(), amplRed.getMeta(), amplRed.getAccion(),totalAccion, isUsuario)){
                        bandera = true;
                        strResultado = amplRed.getRamo()+"-"+amplRed.getMeta()+"-"+amplRed.getAccion()+"-"+amplRed.getPartida();
                    }
                }
                if(bandera)
                    break;
            }
        }
        //bandera = false;
        if(bandera){
                strResultado = "2|"+ strResultado;
        }else{
            if(isAuth)
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

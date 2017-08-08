<%-- 
    Document   : ajaxValidaMetasAccionesHabilitadasTransferencias
    Created on : Jun 28, 2016, 10:34:41 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
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
    MovimientosReprogramacion movRepro = new MovimientosReprogramacion();
    
    List<Transferencia> reprogferenciaList = null;
    List<ReprogramacionMeta> reproMeta = null;
    List<ReprogramacionAccion> reproAccion = null;
    
    String strResultado = new String();
    String tipoDependencia = new String();
    
    boolean isUsuario = false;
    boolean bandera = false;
    
    int year = 0;
    
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String)session.getAttribute("year"));
        }
        if (session.getAttribute("reprogramacion") != null && session.getAttribute("reprogramacion") != "") {
            movRepro = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
        }
        if(Utilerias.existeParametro("isUsuario", request)){
            isUsuario = Boolean.parseBoolean(request.getParameter("isUsuario"));
        }
        
        autBean = new AutorizacionBean(tipoDependencia);
        autBean.setStrServer(request.getHeader("host"));
        autBean.setStrUbicacion(getServletContext().getRealPath(""));
        autBean.resultSQLConecta(tipoDependencia);
        reproMeta = movRepro.getMovOficioMetaList();
        reproAccion = movRepro.getMovOficioAccionList();
        for(ReprogramacionMeta reprog : reproMeta){
            if(reprog.getMetaInfo().getNvaCreacion().equals("N")){
                if(autBean.validaMetaInhabilitada(year,reprog.getMetaInfo().getRamoId(),reprog.getMetaInfo().getMetaId())){
                    bandera = true;
                    strResultado = "Ramo: "+reprog.getMetaInfo().getRamoId()+"- Meta: "+reprog.getMetaInfo().getMetaId();
                }
                if(bandera)
                    break;
            }
        }
        if(!bandera){
            for(ReprogramacionAccion reprog : reproAccion){
                if(reprog.getMovOficioAccion().getNvaCreacion().equals("N")){
                    if(autBean.validaAccionInhabilitada(year,
                            reprog.getMovOficioAccion().getRamoId(),
                            reprog.getMovOficioAccion().getMetaId(),
                            reprog.getMovOficioAccion().getAccionId())){
                        bandera = true;
                        strResultado = "Ramo: "+reprog.getMovOficioAccion().getRamoId()+
                                "- Meta: "+reprog.getMovOficioAccion().getMetaId() +
                                "- Accion: "+reprog.getMovOficioAccion().getAccionId();
                    }
                    if(bandera)
                        break;
                }
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

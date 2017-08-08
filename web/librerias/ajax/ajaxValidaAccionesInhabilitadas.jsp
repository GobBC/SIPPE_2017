<%-- 
    Document   : ajaxValidaAccionesInhabilitadas
    Created on : Feb 3, 2017, 1:30:00 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.MovOficioAccionEstimacion"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="gob.gbc.entidades.AmpliacionReduccionAccion"%>
<%@page import="gob.gbc.entidades.ReprogramacionAccion"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.entidades.MovimientosAmpliacionReduccion"%>
<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.Accion"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    
    boolean bandera = false;
    
    String strResultado = new String();
    String tipo = new String();
    String ramo = new String();
    String tipoDependencia = new String();
    int year = 0;
    int meta = 0;
    int acumEstimacion = 0;
    
    AutorizacionBean autBean = null;
    List<Accion> accionList = new ArrayList<Accion>();
    List<ReprogramacionAccion> reproAccionList = new ArrayList<ReprogramacionAccion>();
    List<AmpliacionReduccionAccion> amplRedAccionList = new ArrayList<AmpliacionReduccionAccion>();
    List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
    
    MovimientosReprogramacion reprogramacion;
    MovimientosAmpliacionReduccion ampliacion;
    MovimientosTransferencia transferencia;

    try {

        
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        
        if (Utilerias.existeParametro("ramo", request)) {
            ramo = (String) request.getParameter("ramo");
        }
        
        if (Utilerias.existeParametro("tipo", request)) {
            tipo = (String)request.getParameter("tipo");
        }
        
        if (Utilerias.existeParametro("meta", request)) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        
        
        autBean = new AutorizacionBean(tipoDependencia);
        autBean.setStrServer(request.getHeader("host"));
        autBean.setStrUbicacion(getServletContext().getRealPath(""));
        autBean.resultSQLConecta(tipoDependencia);
        
        accionList = autBean.getResultSQLValidaAccionesInabilitadasByMeta(year, ramo, meta);
        
        if(accionList.size() > 0){
            if(tipo.equals("R")){
                reprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
                if(reprogramacion != null){
                    reproAccionList = reprogramacion.getMovOficioAccionList();
                    for(Accion accion : accionList){
                        for(ReprogramacionAccion movAccion : reproAccionList){
                            bandera = false;
                            if(accion.getRamo().equalsIgnoreCase(movAccion.getMovOficioAccion().getRamoId()) &&
                                    accion.getMeta() == movAccion.getMovOficioAccion().getMetaId() &&
                                    accion.getAccionId() == movAccion.getMovOficioAccion().getAccionId()){
                                if(autBean.validaAccionInhabilitada(year,ramo,meta,accion.getAccionId()))
                                    for(MovOficioAccionEstimacion movEst : movAccion.getMovAcionEstimacionList()){
                                        acumEstimacion += movEst.getValor();
                                    }
                                    if(acumEstimacion == 0)
                                        bandera = true;
                                break;
                            }
                        }
                        if(!bandera)
                            break;
                    }
                }else{
                    bandera = false;
                }
            }else if(tipo.equals("A")){
                ampliacion = (MovimientosAmpliacionReduccion) session.getAttribute("ampliacionReduccion");
                if(ampliacion != null){
                    amplRedAccionList = ampliacion.getAmpReducAccionList();
                    for(Accion accion : accionList){                        
                        for(AmpliacionReduccionAccion movAccion : amplRedAccionList){
                            bandera = false;
                            if(accion.getRamo().equalsIgnoreCase(movAccion.getMovOficioAccion().getRamoId()) &&
                                    accion.getMeta() == movAccion.getMovOficioAccion().getMetaId() &&
                                    accion.getAccionId() == movAccion.getMovOficioAccion().getAccionId()){
                                if(autBean.validaAccionInhabilitada(year,ramo,meta,accion.getAccionId()))
                                    for(MovOficioAccionEstimacion movEst : movAccion.getMovOficioAccionEstList()){
                                        acumEstimacion += movEst.getValor();
                                    }
                                    if(acumEstimacion == 0)
                                        bandera = true;
                                break;
                            }
                        }
                        if(!bandera)
                            break;
                    }
                }
            }else if(tipo.equals("T")){
                transferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
                if(transferencia != null){
                    transAccionList = transferencia.getTransferenciaAccionList();
                    for(Accion accion : accionList){                        
                        for(TransferenciaAccion movAccion : transAccionList){
                            bandera = false;
                            if(accion.getRamo().equalsIgnoreCase(movAccion.getMovOficioAccion().getRamoId()) &&
                                    accion.getMeta() == movAccion.getMovOficioAccion().getMetaId() &&
                                    accion.getAccionId() == movAccion.getMovOficioAccion().getAccionId()){
                                for(MovOficioAccionEstimacion movEst : movAccion.getMovOficioAccionEstList()){
                                    acumEstimacion += movEst.getValor();
                                }
                                if(acumEstimacion == 0)
                                    bandera = true;
                                break;
                            }
                        }
                        if(!bandera)
                            break;
                    }
                }
            }
            if(!bandera){
                strResultado = "2";
            }else{
                strResultado = "1";
            }
        }else{
            strResultado = "1";
        }


    } catch (Exception ex) {
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

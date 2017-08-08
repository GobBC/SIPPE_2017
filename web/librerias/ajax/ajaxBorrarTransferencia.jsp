<%-- 
    Document   : ajaxBorrarTransferencia
    Created on : Feb 10, 2016, 1:44:58 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.TransferenciaMeta"%>
<%@page import="gob.gbc.entidades.TransferenciaAccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    MovimientosTransferencia movTranferencia = new MovimientosTransferencia();
    List<TransferenciaAccionReq> transAccionReqList = new ArrayList<TransferenciaAccionReq>();
    TransferenciaAccionReq transAccionReqTemp = new TransferenciaAccionReq();
    List<TransferenciaAccion> transAccionList = new ArrayList<TransferenciaAccion>();
    TransferenciaAccion transAccionTemp = new TransferenciaAccion();
    List<TransferenciaMeta> transMetaList = new ArrayList<TransferenciaMeta>();
    TransferenciaMeta transMetaTemp = new TransferenciaMeta();
    List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
    Transferencia transferenciaTemp = new  Transferencia();
    String strResultado = new String();
    String mensaje = new String();
    int identidicador = -1;
    int tipoRecal = 0;
    int transferenciaId = 0;
    boolean bandera = true;
    
    try {
        if(session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != ""){
            movTranferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            transAccionList = movTranferencia.getTransferenciaAccionList();
            transMetaList = movTranferencia.getTransferenciaMetaList();
            transferenciaList = movTranferencia.getTransferenciaList();
        }
        if(Utilerias.existeParametro("identificador", request)){
            identidicador = Integer.parseInt( request.getParameter("identificador"));
        }
        if(Utilerias.existeParametro("tipoProg", request)){
            tipoRecal = Integer.parseInt( request.getParameter("tipoProg"));
        }
        if(Utilerias.existeParametro("transferenciaId", request)){
            transferenciaId = Integer.parseInt( request.getParameter("transferenciaId"));
        }
        if(tipoRecal <= 3){
            if(tipoRecal == 1){
                for(TransferenciaMeta reprogMeta : transMetaList){
                    if(reprogMeta.getIdentificador()== identidicador){
                        transMetaTemp = reprogMeta;
                        break;
                    }
                }
                for(TransferenciaAccion transTemp : transAccionList){
                    if(transTemp.getMovOficioAccion().getMetaId() == transMetaTemp.getMovOficioMeta().getMetaId() && 
                            transMetaTemp.getMovOficioMeta().getMetaId() < 0){
                        bandera = false;
                        mensaje = "Esta meta esta relacionada a una acción";
                    }
                }
                if(!bandera){
                    for(TransferenciaAccionReq transTemp :transAccionReqList){
                        if(transTemp.getMovOficioAccionReq().getMeta() == transMetaTemp.getMovOficioMeta().getMetaId()&& 
                            transMetaTemp.getMovOficioMeta().getMetaId() < 0){
                            bandera = false;
                            mensaje = "Esta meta esta relacionada a un requerimiento";
                        }
                    }
                }                
                if(bandera)
                    transMetaList.remove(transMetaTemp);
                movTranferencia.setTransferenciaMetaList(transMetaList);
            }
            if(tipoRecal == 2){
                for(TransferenciaAccion reprogAccion : transAccionList){
                    if(reprogAccion.getIdentificador() == identidicador){
                        transAccionTemp = reprogAccion;
                        break;
                    }
                }
                for(TransferenciaAccionReq transTemp :transAccionReqList){
                    if(transTemp.getMovOficioAccionReq().getAccion() == transAccionTemp.getMovOficioAccion().getAccionId() &&
                            transAccionTemp.getMovOficioAccion().getAccionId() < 0){
                        bandera = false;
                        mensaje = "Esta accion esta relacionada a un requerimiento";
                    }
                }
                if(bandera)
                    transAccionList.remove(transAccionTemp);
                movTranferencia.setTransferenciaAccionList(transAccionList);
            }
            if(tipoRecal == 3){
                for(Transferencia transferencia : transferenciaList){
                    if(transferencia.getConsec()== identidicador){
                        transferenciaTemp = transferencia;
                        break;
                    }
                }
                transferenciaList.remove(transferenciaTemp);
                movTranferencia.setTransferenciaList(transferenciaList);
            }
        }else{
            for(Transferencia transferencia : movTranferencia.getTransferenciaList()){
                if(transferencia.getConsec() == transferenciaId){
                    transferenciaTemp = transferencia;
                }
            }
            transAccionReqList = transferenciaTemp.getTransferenciaAccionReqList();            
            if(tipoRecal == 6){
                for(TransferenciaAccionReq transAccionReq : transAccionReqList){
                    if(transAccionReq.getIdentidicador()== identidicador){
                        transAccionReqTemp = transAccionReq;
                        break;
                    }
                }
                transAccionReqList.remove(transAccionReqTemp);
                for(Transferencia transferencia : movTranferencia.getTransferenciaList()){
                    if(transferencia.getConsec() == transferenciaId){
                        transferencia.setTransferenciaAccionReqList(transAccionReqList);
                    }
                }
            }
            
        }
        if(bandera){
            strResultado = "1|Exito";
        }else{
            strResultado = "2|"+ mensaje;
        }
        session.setAttribute("transferencia", movTranferencia);
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        out.print(strResultado);
    }
    %>

<%-- 
    Document   : ajaxEditarImporteTransferencia
    Created on : Jan 7, 2016, 4:34:57 PM
    Author     : ugarcia
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="gob.gbc.entidades.TransferenciaAccionReq"%>
<%@page import="gob.gbc.entidades.Transferencia"%>
<%@page import="gob.gbc.entidades.MovimientosTransferencia"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.entidades.CodigoPPTO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
    TransferenciaBean transBean = null;
    CodigoPPTO codigoPPTO = new CodigoPPTO();
    MovimientosTransferencia movTransferencia = new MovimientosTransferencia();
    Transferencia transferencia = new Transferencia();
    List<Transferencia> transferenciaList = new ArrayList<Transferencia>();
    List<TransferenciaAccionReq> transAccReqList = new ArrayList<TransferenciaAccionReq>();
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramo = new String();
    String programa = new String();
    String partida = new String();
    String fuente = new String();
    String relLaboral = new String();
    String proyecto = new String();
    String fecha = new String();
    BigDecimal importeTransferido = new BigDecimal(0.0);
    BigDecimal importeNuevo = new BigDecimal(0.0);    
    int identificador = 0;
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("transferencia") != null && session.getAttribute("transferencia") != "") {
            movTransferencia = (MovimientosTransferencia) session.getAttribute("transferencia");
            transferenciaList = movTransferencia.getTransferenciaList();
        }
        if (request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")) {
            ramo = request.getParameter("ramo");
        }
        if (request.getParameter("programa") != null
                && !request.getParameter("programa").equals("")) {
            programa = request.getParameter("programa");
        }
        if (request.getParameter("partida") != null
                && !request.getParameter("partida").equals("")) {
            partida = request.getParameter("partida");
        }
        if(Utilerias.existeParametro("identificador", request)){
            identificador = Integer.parseInt( request.getParameter("identificador"));
        }
        if(Utilerias.existeParametro("importe", request)){
            importeNuevo = new BigDecimal(Double.valueOf(request.getParameter("importe")));
        }
        if (request.getParameter("relLaboral") != null
                && !request.getParameter("relLaboral").equals("")) {
            relLaboral =  request.getParameter("relLaboral");
            if(relLaboral.equals("-1")){
                relLaboral = "0";
            }
        }
        transBean = new TransferenciaBean(tipoDependencia);
        transBean.setStrServer(request.getHeader("host"));
        transBean.setStrUbicacion(getServletContext().getRealPath(""));
        transBean.resultSQLConecta(tipoDependencia);
        for(Transferencia transTemp : transferenciaList){
            if(transTemp.getConsec() == identificador){
                transferencia = transTemp;
            }
        }
        transAccReqList = transferencia.getTransferenciaAccionReqList();
        for(TransferenciaAccionReq transTemp : transAccReqList){
            importeTransferido = importeTransferido.add(new BigDecimal(transTemp.getImporte()));
        }
        if(importeNuevo.compareTo(importeTransferido) >= 0){
            for(Transferencia transTemp : transferenciaList){
                if(transTemp.getConsec() == identificador){
                    transTemp.setImporte(importeNuevo.doubleValue());
                }
            }            
            movTransferencia.setTransferenciaList(transferenciaList);
            session.setAttribute("transferencia", movTransferencia);
        }else{
            strResultado += "1";
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
        out.print(strResultado);
    }
%>

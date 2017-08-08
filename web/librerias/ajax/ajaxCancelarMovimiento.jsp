<%-- 
    Document   : ajaxCancelarMovimiento
    Created on : Feb 18, 2016, 8:52:57 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.TransferenciaBean"%>
<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    AmpliacionReduccionBean ampRedBean = null;
    TransferenciaBean transBean = null;
    String tipoDependencia = new String();
    String tipoMovimiento = new String();
    String strResultado = new String();
    String estatus = new String();
    String mensaje = new String();
    int folio = 0;
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependencia") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(Utilerias.existeParametro("tipoMovimiento", request)){
            tipoDependencia = (String) session.getAttribute("tipoMovimiento");
        }
        if(Utilerias.existeParametro("folio", request)){
            folio = Integer.parseInt((String) session.getAttribute("folio"));
        }
        if(tipoMovimiento.equals("A")){
            ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
            ampRedBean.setStrServer(request.getHeader("host"));
            ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
            ampRedBean.resultSQLConecta(tipoDependencia);
            mensaje = ampRedBean.cancelarMovimiento(folio, estatus);
            if(mensaje.isEmpty()){
                strResultado += "1|El movimiento fue cancelado";
            }
        }else if(tipoMovimiento.equals("T")){
            transBean = new TransferenciaBean(tipoDependencia);
            transBean.setStrServer(request.getHeader("host"));
            transBean.setStrUbicacion(getServletContext().getRealPath(""));
            transBean.resultSQLConecta(tipoDependencia);
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
        if (transBean != null) {
            transBean.resultSQLDesconecta();
        }
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
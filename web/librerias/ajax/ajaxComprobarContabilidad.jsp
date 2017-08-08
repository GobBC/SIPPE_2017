<%-- 
    Document   : AjaxComprobarContabilidad
    Created on : Jan 22, 2016, 10:54:29 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
    AutorizacionBean autoBean = null;
    String strResultado = new String();
    String tipoDependencia = new String();
    int year = 0;
    boolean contabilidad;
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        autoBean = new AutorizacionBean(tipoDependencia);
        autoBean.setStrServer(request.getHeader("host"));
        autoBean.setStrUbicacion(getServletContext().getRealPath(""));
        autoBean.resultSQLConecta(tipoDependencia);
        contabilidad = autoBean.getValidaContabilidad(year);
        if(contabilidad){
            strResultado = "1";
        }else{
            strResultado = "2";
        }
    }catch(Exception ex){
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    }finally{
        if(autoBean != null)
            autoBean.resultSQLDesconecta();
        out.print(strResultado);
    }
%>

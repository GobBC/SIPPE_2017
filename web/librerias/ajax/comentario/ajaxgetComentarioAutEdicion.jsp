<%-- 
    Document   : ajaxgetComentarioAutEdicion
    Created on : May 15, 2017, 11:13:56 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    AutorizacionBean autoBean = null;
    
    String tipoDependencia = new String();
    String strResultado = new String();
    String comentario = new String();
    String appLogin = new String();
    
    int oficio = 0;
    int numComentario = 0;
    
    boolean resultado = false;
    
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            appLogin = (String) session.getAttribute("strUsuario");
        }
        
        if(Utilerias.existeParametro("oficio", request)){
            oficio = Integer.valueOf(request.getParameter("oficio"));
        }
        
        if(Utilerias.existeParametro("numComentario", request)){
            numComentario = Integer.valueOf(request.getParameter("numComentario"));
        }
        
        if(Utilerias.existeParametro("comentario", request)){
            comentario = request.getParameter("comentario");
        }
        
        autoBean = new AutorizacionBean(tipoDependencia);
        autoBean.setStrServer(request.getHeader("host"));
        autoBean.setStrUbicacion(getServletContext().getRealPath(""));
        autoBean.resultSQLConecta(tipoDependencia);
        
        strResultado = autoBean.getComentarioEdicion(oficio,numComentario);
        
    }catch(Exception ex){
        strResultado="-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    }finally {
        if (autoBean != null) {
            autoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
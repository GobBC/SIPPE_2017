<%-- 
    Document   : ajaxUpdateComentarioAut
    Created on : May 15, 2017, 9:02:44 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
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
    int borrar = 0;
    
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
        
        if(Utilerias.existeParametro("borrar", request)){
            borrar = Integer.valueOf(request.getParameter("borrar"));
        }
        
        if(Utilerias.existeParametro("comentario", request)){
            comentario = request.getParameter("comentario");
        }
        
        autoBean = new AutorizacionBean(tipoDependencia);
        autoBean.setStrServer(request.getHeader("host"));
        autoBean.setStrUbicacion(getServletContext().getRealPath(""));
        autoBean.resultSQLConecta(tipoDependencia);
        
        if(numComentario >0){
            if(borrar > 0){
                resultado = autoBean.getDeleteComentarioAut(oficio, numComentario);
                if(resultado){
                    strResultado += autoBean.getUltimoComentario(oficio);
                }
            }else
                resultado = autoBean.getUpdateComentarioAut(oficio, appLogin, comentario, numComentario);
        }else
            resultado = autoBean.getInsertComentarioAut(oficio, appLogin, comentario);
        
        if(resultado){
            autoBean.transaccionCommit();
            strResultado = "1|" + strResultado;
        }else{
            autoBean.transaccionRollback();
            strResultado = "0";
        }
        
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
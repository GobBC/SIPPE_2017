<%-- 
    Document   : ajaxRebootMovoficiosSequence
    Created on : Dec 20, 2016, 10:59:08 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.SecuenciaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SecuenciaBean secuenciaBean = null;
    
    String tipoDependencia = new String(),
            strResultado = new String();
    
    int year = 0;
    
    boolean resultado = false;
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }        
        
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        secuenciaBean = new SecuenciaBean(tipoDependencia);
        secuenciaBean.setStrServer(request.getHeader("host"));
        secuenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
        secuenciaBean.resultSQLConecta(tipoDependencia);
        
        resultado = secuenciaBean.rebootMovoficiosSecuencia(year);
        
        if(resultado)
            secuenciaBean.transaccionCommit();
        else
            secuenciaBean.transaccionRollback();
        
        if(!secuenciaBean.mensaje.isEmpty())
            strResultado = "-1|" + secuenciaBean.mensaje;
    }catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (secuenciaBean != null) {
            secuenciaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
<%-- 
    Document   : ajaxValidaDeptoPlantilla
    Created on : Oct 7, 2016, 10:08:29 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String strResultado = new String();
    String ramo = new String();
    String prg = new String();
    String depto = new String();
    String tipoDependencia = new String();
    
    int meta = 0;
    int accion = 0;
    int year = 0;
    
    boolean plantBool = false;
    
    AccionBean accionBean = null;
    
    try{
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        
        
        if (Utilerias.existeParametro("ramo", request)) {
            ramo = request.getParameter("ramo");
        }
        if (Utilerias.existeParametro("programa", request)) {
            prg = request.getParameter("programa");
        }
        if (Utilerias.existeParametro("depto", request)) {
            depto = request.getParameter("depto");
        }
        if (Utilerias.existeParametro("meta", request)) {
            meta = Integer.parseInt(request.getParameter("meta"));
        }
        if (Utilerias.existeParametro("accion", request)) {
            accion = Integer.parseInt(request.getParameter("accion"));
        }
        
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        
        plantBool = accionBean.getResultSQLvalidaDeptoPlantilla(year, ramo, prg, depto, meta, accion);
        
        if(plantBool)
            strResultado = "1";
        else
            strResultado = "0";
        
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
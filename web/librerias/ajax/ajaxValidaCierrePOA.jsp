<%-- 
    Document   : ajaxValidaCierrePOA
    Created on : May 18, 2015, 9:00:14 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.MetaBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    MetaBean metaBean = null;
    String strResultado = new String();
    String tipoDependencia = new String();
    boolean presup = false;
    String ramoId = new String();
    String programaId = new String();
    int metaId = 0;
    int year = 0;
    boolean isPOACerrado = false;
    double estMeta = 0;
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")) {
            ramoId = (String) request.getParameter("ramoId");
        }
        if (request.getParameter("programaId") != null
                && !request.getParameter("programaId").equals("")) {
            programaId = (String) request.getParameter("programaId");
        }
        if (request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")) {
            metaId = Integer.parseInt((String) request.getParameter("metaId"));
        }
        metaBean = new MetaBean(tipoDependencia);
        metaBean.setStrServer(request.getHeader("host"));
        metaBean.setStrUbicacion(getServletContext().getRealPath(""));
        metaBean.resultSQLConecta(tipoDependencia);
        isPOACerrado = metaBean.getCierrePOA(ramoId, year);
        if(isPOACerrado){
            strResultado = "Cerrado";
            //strResultado = "Abierto";
        }else{
            strResultado = "Abierto";
            presup = metaBean.isPOAPresupuestable(ramoId, programaId, metaId, year);
            if(presup){
                strResultado = "noPres";
            }else{                
                estMeta = metaBean.getCountEstimacionMeta(year, ramoId, metaId);
                if(estMeta > 0){
                    strResultado = "Abierto";
                }else{
                    strResultado = "calendarizar";
                }                
            }
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
        if (metaBean != null) {
            metaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

<%-- 
    Document   : ajaxIsRamoPOACerrado
    Created on : Aug 20, 2015, 11:45:04 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.CargaProyeccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    CargaProyeccionBean cargaBean = null;
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramoId = new String();
    int year = 0;
    boolean isPOACerrado = false;
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
        cargaBean = new CargaProyeccionBean(tipoDependencia);
        cargaBean.setStrServer(request.getHeader("host"));
        cargaBean.setStrUbicacion(getServletContext().getRealPath(""));
        cargaBean.resultSQLConecta(tipoDependencia);
        isPOACerrado = cargaBean.isRamoCierrePOA(ramoId, year);
        if(isPOACerrado){
            strResultado = "Cerrado";
        }else{
            strResultado = "Abierto";
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
        if (cargaBean != null) {
            cargaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

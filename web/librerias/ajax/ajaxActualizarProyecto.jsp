<%-- 
    Document   : ajaxActualizarProyecto
    Created on : Apr 1, 2015, 4:32:00 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.LineaBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="gob.gbc.aplicacion.ProyectoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  
    ProyectoBean proyectoBean = null;
    Proyecto proyecto = new Proyecto();

    String ramoId = new String();
    String programaId = new String();
    int proyId = 0;
    String depto = new String();
    int year = 0;
    String tipoProy = "";
    String rfc = new String();
    String homoclave = new String();
    String strResult = new String();
    String tipoProyecto = new String();
    String tipoDependencia = new String();
    boolean blResultado = false;
    LineaBean lineaBean = new LineaBean();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("tipoProyecto") != null && session.getAttribute("tipoProyecto") != "") {
            tipoProyecto = (String) session.getAttribute("tipoProyecto");
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
        if (request.getParameter("proyectoId") != null
                && !request.getParameter("proyectoId").equals("")) {
            proyId = Integer.parseInt((String) request.getParameter("proyectoId"));
        }
        if (request.getParameter("tipoProy") != null
                && !request.getParameter("tipoProy").equals("")) {
            tipoProy = (String) request.getParameter("tipoProy");
        }
        if (request.getParameter("depto") != null
                && !request.getParameter("depto").equals("")) {
            depto = (String) request.getParameter("depto");
        }
        if (request.getParameter("rfc") != null
                && !request.getParameter("rfc").equals("")) {
            rfc = (String) request.getParameter("rfc");
        }
        if (request.getParameter("homoclave") != null
                && !request.getParameter("homoclave").equals("")) {
            homoclave = (String) request.getParameter("homoclave");
        }
        proyectoBean = new ProyectoBean(tipoDependencia);
        lineaBean = new LineaBean(tipoDependencia);
        lineaBean.setStrServer(request.getHeader("host"));
        lineaBean.setStrUbicacion(getServletContext().getRealPath(""));
        lineaBean.resultSQLConecta(tipoDependencia);
        if ((!homoclave.equals("") && !rfc.equals(""))) {
            blResultado = lineaBean.updateProyectoByTipoProyecto(rfc, homoclave, depto, ramoId, programaId, proyId, year, tipoProy);
            if (blResultado) {
                strResult += "1";
            }
        }
    } catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (proyectoBean != null) {
            proyectoBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>

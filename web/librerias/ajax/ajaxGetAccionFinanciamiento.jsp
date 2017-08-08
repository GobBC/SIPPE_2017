<%-- 
    Document   : ajaxGetAccionFinanciamiento
    Created on : May 5, 2015, 3:54:32 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String tipoDependencia = new String();
    String ramoId = new String();
    int opcion = 0;
    int metaId = 0;
    int accionId = 0;
    int year = 0;
    AccionBean accionBean = null;
    List<FuenteFinanciamiento> accionFuenteList = new ArrayList<FuenteFinanciamiento>();
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
     try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(request.getParameter("ramoId") != null
                && !request.getParameter("ramoId").equals("")){
             ramoId = (String)request.getParameter("ramoId");
        }
        
        if(request.getParameter("metaId") != null
                && !request.getParameter("metaId").equals("")){
             metaId = Integer.parseInt((String)request.getParameter("metaId"));
        }
        
        if(request.getParameter("accionId") != null
                && !request.getParameter("accionId").equals("")){
             accionId = Integer.parseInt((String)request.getParameter("accionId"));
        }
        if(request.getParameter("opcion") != null
                && !request.getParameter("opcion").equals("")){
             opcion = Integer.parseInt((String)request.getParameter("opcion"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        accionFuenteList = accionBean.getAccionFuente(year, ramoId, metaId, accionId);
        strResultado += "<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>";
        for(FuenteFinanciamiento fuente : accionFuenteList){
            strResultado += "<option value='"+fuente.getFuenteId()+"'>"+fuente.getFuenteId()+"-"+fuente.getFuenteFinanciamiento()+"</option>";
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
        if (accionBean != null) {
            accionBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
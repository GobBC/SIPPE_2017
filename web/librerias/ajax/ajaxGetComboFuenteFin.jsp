<%-- 
    Document   : getCombosFuenteGasto
    Created on : May 6, 2015, 10:23:09 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.TipoGasto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.FuenteFinanciamiento"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    AccionBean accionBean = null;
    List<FuenteFinanciamiento> fuenteList = new ArrayList<FuenteFinanciamiento>();
    String strResultado = new String();
    String ramoId = new String();
    String tipoDependencia = new String();
    int metaId = 0;
    int accionId = 0;
    int year = 0;
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
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        if(!ramoId.equals(-1)){
            fuenteList = accionBean.getFuenteFinanciamiento(year);
            strResultado += "<option value='-1'> -- Seleccione una fuente de financiamiento -- </option>";
            for(FuenteFinanciamiento fuente : fuenteList){
                strResultado += "<option value='"+ fuente.getFuenteId() +"'>"+ 
                        fuente.getFuenteId()+"-"+ fuente.getFuenteFinanciamiento() +"</option>";
            }
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

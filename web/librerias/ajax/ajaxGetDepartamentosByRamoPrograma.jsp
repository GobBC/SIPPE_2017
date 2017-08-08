<%-- 
    Document   : ajaxGetDepartamentosByRamoPrograma
    Created on : May 13, 2015, 9:31:49 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.aplicacion.DependenciaBean"%>
<%@page import="gob.gbc.entidades.Persona"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.entidades.Dependencia"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int ramo = 0;
    int programa = 0;
    int year = 0;
    DependenciaBean dependenciaBean = null;
    //ResultSQL resultSQL = new ResultSQL();
    String tipoDependencia = new String();
    String strResult = new String();
    List<Dependencia> dependenciaList = new ArrayList<Dependencia>();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(request.getParameter("ramo") != null
                && !request.getParameter("ramo").equals("")){
             ramo = Integer.parseInt((String)request.getParameter("ramo"));
        }
        if(request.getParameter("programa") != null
                && !request.getParameter("programa").equals("")){
             programa = Integer.parseInt((String)request.getParameter("programa"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        dependenciaBean = new DependenciaBean(tipoDependencia);
        dependenciaBean.setStrServer(request.getHeader("host"));
        dependenciaBean.setStrUbicacion(getServletContext().getRealPath(""));
        dependenciaBean.resultSQLConecta(tipoDependencia);
        dependenciaList = dependenciaBean.getDepartamentosByRamoPrograma(ramo, programa,year);
        strResult += "<option value='-1' >";
        strResult += "  -- Seleccione un departamento --";
        strResult += "</option>";
        for(Dependencia dependencia : dependenciaList){
            strResult += "<option value='"+dependencia.getDeptoId()+"' >";
            strResult += dependencia.getDeptoId()+"-"+dependencia.getDepartamento();
            strResult += "</option>";
        }
    }catch (Exception ex) {
        strResult = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
        if (dependenciaBean != null) {
            dependenciaBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>

<%-- 
    Document   : ajaxGetProyectosByPrograma
    Created on : Mar 26, 2015, 9:31:49 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="gob.gbc.aplicacion.ProyectoBean"%>
<%@page import="gob.gbc.entidades.Persona"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String ramo = new String();
    String programa = new String();
    int year = 0;
    ProyectoBean proyectoBean = null;
    //ResultSQL resultSQL = new ResultSQL();
    String strResult = new String();
    String tipoDependencia = new String();
    List<Proyecto> proyectoList = new ArrayList<Proyecto>();
    
    request.setCharacterEncoding("UTF-8");
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(Utilerias.existeParametro("ramo",request)){
             ramo = request.getParameter("ramo");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(Utilerias.existeParametro("programa",request)){
             programa = request.getParameter("programa");
        }
        proyectoBean = new ProyectoBean(tipoDependencia);
        proyectoBean.setStrServer(request.getHeader("host"));
        proyectoBean.setStrUbicacion(getServletContext().getRealPath(""));
        proyectoBean.resultSQLConecta(tipoDependencia);
        ramo = ramo.replace("'", "");
        proyectoList = proyectoBean.getResultSQLProyectosByPrograma(ramo, programa, year);
        strResult += "<option value='-1' >";
        strResult += "  -- Seleccione un proyecto/Actividad --";
        strResult += "</option>";
        for(Proyecto proyecto : proyectoList){
            strResult += "<option value='"+proyecto.getProyectoId()+","+proyecto.getTipoProyecto()+"' >";
            if(proyecto.getObra().equals("S")){
                strResult += proyecto.getTipoProyecto()+proyecto.getProyectoId()+"-"+proyecto.getProyecto() + " (OBRA)";
            }else{
                strResult += proyecto.getTipoProyecto()+proyecto.getProyectoId()+"-"+proyecto.getProyecto();                
            }
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
        if (proyectoBean != null) {
            proyectoBean.resultSQLDesconecta();
        }
        out.print(strResult);
    }
%>

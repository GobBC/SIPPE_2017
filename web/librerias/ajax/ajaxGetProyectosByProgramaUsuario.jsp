<%-- 
    Document   : ajaxGetProyectosByProgramaUsuario
    Created on : Dec 7, 2015, 12:50:45 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="gob.gbc.entidades.Proyecto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;
    List<Proyecto> proyectoList = new ArrayList<Proyecto>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    int year = 0;
    
    try{
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if(session.getAttribute("year") != null && session.getAttribute("year") != ""){
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if(session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != ""){
            appLogin = (String)session.getAttribute("strUsuario");
        }
        if(request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")){
             ramo = (String)request.getParameter("ramo");
        }
        if(request.getParameter("programa") != null && !request.getParameter("programa").equals("")){
             prg = (String)request.getParameter("programa");
        }
        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        proyectoList = recalBean.getProyectoByProgramaUsuario(year, appLogin, ramo, prg);
        if(proyectoList.size() > 0){
            strResultado += "<option value='-1'> -- Seleccione un proyecto/actividad -- </option>";
            for(Proyecto proyecto : proyectoList){
                strResultado += "<option value='"+proyecto.getProyId()+","+proyecto.getTipoProyecto()+"'> "
                        + ""+proyecto.getTipoProyecto()+proyecto.getProyId()+"-"+proyecto.getProyecto()+"</option>";
            }
        }else{
            strResultado += "0|Este programa no tiene proyectos asignados";
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
        if (recalBean != null) {
            recalBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
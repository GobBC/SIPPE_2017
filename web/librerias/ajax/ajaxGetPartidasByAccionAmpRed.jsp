<%-- 
    Document   : ajaxGetPartidasByAccionAmpRed
    Created on : Jan 5, 2016, 4:00:12 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.AmpliacionReduccionBean"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.entidades.Partida"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    AmpliacionReduccionBean ampRedBean = null;
    List<Partida> partidaList = new ArrayList<Partida>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String proy = new String();
    String tipoProy = new String();
    int year = 0;
    int proyecto = -1;
    int meta = -1;
    int accion = -1;
    
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
        if(request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")){
             proy = request.getParameter("proyecto");
             proyecto = Integer.parseInt(proy.split(",")[0]);
             tipoProy = proy.split(",")[1];
        }
        if(request.getParameter("meta") != null && !request.getParameter("meta").equals("")){
             meta = Integer.parseInt(request.getParameter("meta"));
        }
        if(request.getParameter("accion") != null && !request.getParameter("accion").equals("")){
             accion = Integer.parseInt(request.getParameter("accion"));
        }
        ampRedBean = new AmpliacionReduccionBean(tipoDependencia);
        ampRedBean.setStrServer(request.getHeader("host"));
        ampRedBean.setStrUbicacion(getServletContext().getRealPath(""));
        ampRedBean.resultSQLConecta(tipoDependencia);
        //partidaList = ampRedBean.getPartidasByAccionAmpRed(year, appLogin, ramo, prg, proyecto, tipoProy, meta, accion);
        partidaList = ampRedBean.getResultSQLGetPartidasGeneral(year);
        if(partidaList.size() > 0){
            strResultado += "<option value='-1'> -- Seleccione una partida -- </option>";
            for(Partida partida : partidaList){
                strResultado += "<option value='"+partida.getPartidaId()+"'> "
                        + ""+partida.getPartidaId()+"-"+partida.getPartida()+"</option>";
            }
        }else{
            strResultado += "0|Esta acción no tiene partidas asignados";
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
        if (ampRedBean != null) {
            ampRedBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>
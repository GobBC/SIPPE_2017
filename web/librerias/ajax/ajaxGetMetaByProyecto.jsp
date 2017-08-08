<%-- 
    Document   : ajaxGetMetaByProyectoUsuario
    Created on : Dec 7, 2015, 1:53:28 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.entidades.MovimientosReprogramacion"%>
<%@page import="gob.gbc.entidades.ReprogramacionMeta"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="java.util.List"%>
<%@page import="gob.gbc.aplicacion.RecalendarizacionBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.Meta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    RecalendarizacionBean recalBean = null;  
    MovimientosReprogramacion reprogramacion = new MovimientosReprogramacion();  
    List<ReprogramacionMeta> reprogramacionMetaList = new ArrayList<ReprogramacionMeta>();
    List<Meta> metaList = new ArrayList<Meta>();
    String strResultado = new String();
    String ramo = new String();
    String appLogin = new String();
    String tipoDependencia = new String();
    String prg = new String();
    String proy = new String();
    String tipoProy = new String();
    int year = 0;
    int proyecto = -1;
    
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
             ramo = request.getParameter("ramo");
        }
        if(request.getParameter("programa") != null && !request.getParameter("programa").equals("")){
             prg = request.getParameter("programa");
        }
        if(request.getParameter("proyecto") != null && !request.getParameter("proyecto").equals("")){
             proy = request.getParameter("proyecto");
             proyecto = Integer.parseInt(proy.split(",")[0]);
             tipoProy = proy.split(",")[1];
        }
        if (session.getAttribute("reprogramacion") != null) {
            reprogramacion = (MovimientosReprogramacion) session.getAttribute("reprogramacion");
            reprogramacionMetaList = reprogramacion.getMovOficioMetaList();
        }
        recalBean = new RecalendarizacionBean(tipoDependencia);
        recalBean.setStrServer(request.getHeader("host"));
        recalBean.setStrUbicacion(getServletContext().getRealPath(""));
        recalBean.resultSQLConecta(tipoDependencia);
        metaList = recalBean.getResultGetMetaByProyecto(year, ramo, prg, proyecto, tipoProy);
        if (metaList.size() > 0 || reprogramacionMetaList.size() > 0) {
            strResultado += "<option value='-1'> -- Seleccione una meta -- </option>";
            for(Meta meta : metaList){
                strResultado += "<option value='"+meta.getMetaId()+"'> "
                        + ""+meta.getMetaId()+"-"+meta.getMeta()+"</option>";
            }
            for (ReprogramacionMeta movMeta : reprogramacionMetaList) {
                if (movMeta.getMetaInfo().getMetaId() < 0 && proyecto == movMeta.getMetaInfo().getProyId()
                        && tipoProy.equals(movMeta.getMetaInfo().getTipoProy())) {
                    strResultado += "<option value='" + movMeta.getMetaInfo().getMetaId() + "'> " + "" + movMeta.getMetaInfo().getMetaId() + "-" + movMeta.getMetaInfo().getMetaDescr() + "</option>";
                }
            }
        }else{
            strResultado += "0|Este proyecto/actividad no tiene metas asignadas";
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
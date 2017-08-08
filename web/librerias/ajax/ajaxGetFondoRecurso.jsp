<%-- 
    Document   : ajaxGetFondoRecurso
    Created on : Aug 3, 2015, 11:54:17 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.ParametroPrgBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.FuenteRecurso"%>
<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int year = 0;
    int fuente = 0;
    
    List<FuenteRecurso> fuenteList = new ArrayList<FuenteRecurso>();
    
    String strResultado = new String();
    String tipoDependencia = new String();    
    String usuario = new String();
    
    ParametroPrgBean parametroPrgBean = null;
    AccionBean accionBean = new AccionBean();
    
    try{
        if (request.getParameter("fuente") != null
                && !request.getParameter("fuente").equals("")) {
            fuente = Integer.parseInt((String) request.getParameter("fuente"));
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }
        if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
            usuario = (String) session.getAttribute("strUsuario");
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        parametroPrgBean = new ParametroPrgBean(tipoDependencia);
        parametroPrgBean.setStrServer(request.getHeader("host"));
        parametroPrgBean.setStrUbicacion(getServletContext().getRealPath(""));
        parametroPrgBean.resultSQLConecta(tipoDependencia);
        
        if (parametroPrgBean.getPuedeEditarFinPropositoByRol(usuario) || tipoDependencia.equals("PROGD")){
            fuenteList = accionBean.getFuenteRecuros(year, fuente);
        }else{
            fuenteList = accionBean.getFuenteFiltrado(year, fuente);
        }
        parametroPrgBean.resultSQLDesconecta();
        strResultado += "<option value='-1'>--Seleccione un fondo-recurso--</option>";
        for(FuenteRecurso fuenteR : fuenteList){
            strResultado += "<option value='"+fuenteR.getFuenteRecurso()+"'> "+ fuenteR.getFuenteRecurso() + 
                    "-" + fuenteR.getFuenteRecursoDescr() +"</option>";
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
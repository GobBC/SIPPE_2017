<%-- 
    Document   : ajaxGetLocalidad
    Created on : Apr 27, 2015, 9:56:11 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.AccionBean"%>
<%@page import="gob.gbc.entidades.Localidad"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    
    String strResultado = new String();
    List<Localidad> localidadList = new ArrayList<Localidad>();
    AccionBean accionBean = null;
    String tipoDependencia = new String();
    int municipioId = 0;
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("municipio") != null && !request.getParameter("municipio").equals("")) {
            municipioId = Integer.parseInt((String) request.getParameter("municipio"));
        }
        accionBean = new AccionBean(tipoDependencia);
        accionBean.setStrServer(request.getHeader("host"));
        accionBean.setStrUbicacion(getServletContext().getRealPath(""));
        accionBean.resultSQLConecta(tipoDependencia);
        if(municipioId != -1){
            localidadList = accionBean.getlocalidades(municipioId);
            if(localidadList.size() > 0){
                strResultado += "<option value='-1'>-- Seleccione una localidad --</option>";
                for(Localidad localidad : localidadList){
                    strResultado += "<option value='"+localidad.getLocalidadId()+"'>"+ 
                            localidad.getLocalidadId()+"-"+localidad.getLocalidad() +"</option>";
                }
            }
        }else
            strResultado += "<option value='-1'>-- Seleccione una localidad --</option>";
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

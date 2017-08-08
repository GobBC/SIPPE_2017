<%-- 
    Document   : ajaxPreguntaBorradoPlantilla
    Created on : Oct 3, 2015, 4:30:28 PM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.aplicacion.CargaCodigoBean"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    String ramoId = new String();
    String tipoDependencia = new String();
    String rol = new String();
    CargaCodigoBean cargaBean = null;
    int year = 0;
    int presup = 0;
    int opt = 0;
    boolean bandera = false;
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if ((String) session.getAttribute("strRol") != null) {
            rol = (String) session.getAttribute("strRol");
        }
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = (String) request.getParameter("ramo");
        } 
        if (request.getParameter("opt") != null && !request.getParameter("opt").equals("")) {
            opt = Integer.parseInt(request.getParameter("opt"));
        } 
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }        
        cargaBean = new CargaCodigoBean(tipoDependencia);
        cargaBean.setStrServer(request.getHeader("host"));
        cargaBean.setStrUbicacion(getServletContext().getRealPath(""));
        cargaBean.resultSQLConecta(tipoDependencia);
        if(rol != cargaBean.getResultSQLGetRolesPrg()){
            bandera = false;//cargaBean.getResultSQLValidaRamo(ramoId,year);
        }else{
            bandera = false;
        }
        if(!bandera){
            if(opt == 1){
                presup = cargaBean.getCountProyeccion(year-1, ramoId);
            }else{
                presup = cargaBean.getCountPresupPlantill(year, ramoId);
            }
            if(presup > 0){
                strResultado = "S";
            }else{
                strResultado = "N";
            }
        }else{
            strResultado = "C";
        }
    } catch (Exception ex) {
        strResultado = "-1";
        Bitacora bitacora = new Bitacora();
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();        
    } finally {
        if (cargaBean != null) {
            cargaBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

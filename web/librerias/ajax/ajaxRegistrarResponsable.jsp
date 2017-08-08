<%-- 
    Document   : ajaxRegistrarResponsable
    Created on : Mar 20, 2015, 11:01:31 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.entidades.Ramo"%>
<%@page import="gob.gbc.aplicacion.RamoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String strResultado = new String();
    int ramoId = 0;
    String strRfc = new String();
    String strHomoclave = new String();
    String tipoDependencia = new String();
    RamoBean ramoBean = null;
    int year = 0;
    boolean blResultado = false;
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    try {
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
        if (request.getParameter("ramo") != null && !request.getParameter("ramo").equals("")) {
            ramoId = Integer.parseInt(request.getParameter("ramo"));
        }
        if (request.getParameter("rfc") != null && !request.getParameter("rfc").equals("")) {
            strRfc = request.getParameter("rfc");
        }
        if (request.getParameter("homoclave") != null && !request.getParameter("homoclave").equals("")) {
            strHomoclave = request.getParameter("homoclave");
        }
        if (session.getAttribute("year") != null && session.getAttribute("year") != "") {
            year = Integer.parseInt((String) session.getAttribute("year"));
        }        
        ramoBean = new RamoBean(tipoDependencia);
        ramoBean.setStrServer(request.getHeader("host"));
        ramoBean.setStrUbicacion(getServletContext().getRealPath(""));
        ramoBean.resultSQLConecta(tipoDependencia);
        if((strRfc.isEmpty() || strRfc == null) || (strHomoclave.isEmpty() || strHomoclave == null)){
            blResultado = false;
            strResultado = "El RFC capturado no es válido";
        }
        blResultado = ramoBean.actualizaResponsableRamo(strRfc, strHomoclave, ramoId, year);
        if(strResultado.isEmpty())
            if(blResultado){
                strResultado = "Información Actualizada";
            }
            else{
                strResultado = "No se grabo la información";
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
        if (ramoBean != null) {
            ramoBean.resultSQLDesconecta();
        }
        out.print(strResultado);
    }
%>

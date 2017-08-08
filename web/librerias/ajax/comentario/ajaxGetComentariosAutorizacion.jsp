<%-- 
    Document   : ajaxGetComentariosAutorizacion
    Created on : May 10, 2017, 10:08:05 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.util.Utilerias"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.entidades.BitMovto"%>
<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.aplicacion.AutorizacionBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    
AutorizacionBean autoBean = null;

String strResultado = new String();
String appLogin = new String();
String tipoDependencia = new String();

int contBitacora = 0;

List<BitMovto> bitacoraList = new ArrayList<BitMovto>();

int oficio = 0;
    
try{

    if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
    }
    if(Utilerias.existeParametro("oficio", request)){
        oficio = Integer.valueOf(request.getParameter("oficio"));
    }
    if (session.getAttribute("strUsuario") != null && session.getAttribute("strUsuario") != "") {
        appLogin = (String) session.getAttribute("strUsuario");
    }
    
    autoBean = new AutorizacionBean(tipoDependencia);
    autoBean.setStrServer(request.getHeader("host"));
    autoBean.setStrUbicacion(getServletContext().getRealPath(""));
    autoBean.resultSQLConecta(tipoDependencia);
    
    bitacoraList = autoBean.getResultGetComentariosAutorizacion(oficio);
    
    if(bitacoraList.size() > 0){
        for(BitMovto bitMovto : bitacoraList){
            contBitacora ++;
            strResultado += "<tr>";
            strResultado += "<td>"+ bitMovto.getAppLogin()+ "</td>";
            strResultado += "<td>"+ bitMovto.getComentarioAut()+ "</td>";
            strResultado += "<td>"+ bitMovto.getFecha()+ "</td>";
            if(appLogin.equalsIgnoreCase(bitMovto.getAppLogin()) && bitacoraList.size() == contBitacora){
                strResultado += "<td><div><a title='editar' onclick='editComentarioAut("+bitMovto.getNumComentario()+")'><span class='fa fa-pencil botones-img' ></span></a><a title='borrar' onclick='deleteComentarioAut("+bitMovto.getNumComentario()+")'><span class='fa fa-times botones-img'></span></a><div></td>";
            }else{
                strResultado += "<td></td>";
            }
            strResultado += "</tr>";
        }
    }else{
        strResultado="0";
    }
    
}catch(Exception ex){
    strResultado="-1";
    Bitacora bitacora = new Bitacora();
    bitacora.setStrServer(request.getHeader("host"));
    bitacora.setStrUbicacion(getServletContext().getRealPath(""));
    bitacora.setITipoBitacora(1);
    bitacora.setStrInformacion(ex, request.getServletPath());
    bitacora.grabaBitacora();
}finally {
    if (autoBean != null) {
        autoBean.resultSQLDesconecta();
    }
    out.print(strResultado);
}
%>
<%@page import="gob.gbc.util.Utilerias"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="gob.gbc.util.Bitacora"%>
<!DOCTYPE html>
<%
    int reporte = -1;
    if (Utilerias.existeParametro("reporte", request)) {
        reporte = Integer.parseInt(request.getParameter("reporte"));
    }
    if(reporte == 0){
        Thread.sleep(1000); 
    }else{
        Thread.sleep(5500);        
    }
   session.setAttribute("strUsuario",null);
   session.setAttribute("strRol",null);
   session.invalidate();
%>
<jsp:include page="template/encabezado.jsp" >
    <jsp:param name="logout" value="3" />
</jsp:include>
<p align="center">Finalizando sesi&oacute;n....</p>
<jsp:include page="template/piePagina.jsp" />
<%-- 
    Document   : congelarInformacionAutorizada
    Created on : Dec 3, 2015, 9:26:04 AM
    Author     : ugarcia
--%>

<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Congelar presupuesto</title>
    </head>
    <jsp:include page="template/encabezado.jsp" />
    <div Id="TitProcess"><label  >Congelar presupuesto<label/></div>
    
    <div class="col-md-8 col-md-offset-2">
        <div class="botones-menu_bootstrap">
            <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
                <br/> <small> Inicio </small>
            </button>
        </div>
    </div>
    <div class="center-div" style="width: 62%">
        <%--<form id="frmReporteCongelado" action='<%="ejecutaReporte"+File.separatorChar+"ejecutarReporte.jsp"%>' 
              target="_BLANK" method="POST"/>
            <input type="hidden" value="<%=File.separatorChar +"rptProgramadoCongelado" + File.separatorChar + "RptPOAweb.jasper"%>" 
                   name="filename" id="filename" />
            <input type="hidden" value="" id="" />
        </form>
        --%>
        <input type="button" value="Congelar presupuesto" style="display:block; margin: 80px auto" onclick="congelaPresupuesto()"/>
    </div>
    <jsp:include page="template/piePagina.jsp" />
</html>

<%-- 
    Document   : inicio
    Created on : Jun 3, 2015, 3:10:11 PM
    Author     : SYSTEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="template/encabezado.jsp" />
    <form id="frmInicio" action="inicioSesion.jsp" method="POST">
        <center>
            <div> <label>Seleccione el tipo de dependencia en la que presupuestar&aacute;</label> </div>
            <div id="btnSeleccionBD">
                
                <div>
                    <input type="button" id="btnCentrales" value="Centrales" onclick="setCentrales()"/>
                </div>
                <div>
                    <input type="button" id="btnParaestatales" value="Paraestatales" onclick="setParaestatales()"/>
                </div>  
            </div>
        </center>
        <input type="hidden" id="tipoDependencia" name="tipoDependencia" />
    </form>
    <jsp:include page="template/piePagina.jsp" />
</html>

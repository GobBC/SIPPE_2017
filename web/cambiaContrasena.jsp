<%-- 
    Document   : cambiaContrasena
    Created on : Feb 22, 2016, 10:03:06 AM
    Author     : jarguelles
--%>

<%@page import="gob.gbc.util.Bitacora"%>
<%@page import="gob.gbc.estandar.ResultSQLEstandar"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java" errorPage="error.jsp" %>
<%@page import = "java.util.Vector"%>
<%

    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    
    Bitacora bitacora = new Bitacora();
    boolean bPassVencido = false;

    try {

        if (session.getAttribute("passVencido") != null && session.getAttribute("passVencido") != "") {
            bPassVencido = (Boolean) session.getAttribute("passVencido");
        }

%>
<jsp:include page="template/encabezado.jsp" />
<script src="librerias/js/jquery.msgBox.js" type="text/javascript"></script>
<link href="librerias/css/msgBoxLight.css" rel="stylesheet" type="text/css">
<div Id="TitProcess"><label >CAMBIAR CONTRASEÑA<label/></div>
<div class="col-md-8 col-md-offset-2">
    <%        if (!bPassVencido) {
    %>
    <div class="botones-menu_bootstrap">
        <button type="button" class="btnbootstrap btn-inicio" onclick="Home()">
            <br/> <small> Inicio </small>
        </button>
    </div>
    <%        }
    %>
</div>
<div class="center-div" style="width: 60%">
    <center>
        <form name="form1" method="post" >
            <br/>
            <table border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" width="400">
                <tr>
                    <th height="18" colspan="2" bgcolor="#CDE99E" class="submhead">POR FAVOR LLENE LOS CAMPOS</th>
                </tr>
                <tr>
                    <th height="20" bgcolor="#E5E8EF" class="submhead">CONTRASEÑA ACTUAL:</th>
                    <td width="50%">
                        <input type="password" name="strContrasenaAct" id="strContrasenaAct" class="campo_02" style="text-transform:uppercase;width: 100%;" onKeyPress="" onChange="this.value = mayuscula(this.value);">
                    </td>
                </tr>
                <tr>
                    <th height="20" bgcolor="#E5E8EF" class="submhead">CONTRASEÑA NUEVA:</th>
                    <td width="50%">
                        <input type="password" name="strContrasenaNva" id="strContrasenaNva" class="campo_02" style="text-transform:uppercase;width: 100%;" onKeyPress="" onChange="this.value = mayuscula(this.value);">
                    </td>
                </tr>
                <tr>
                    <th height="20" bgcolor="#E5E8EF" class="submhead">CONFIRMACIÓN DE CONTRASEÑA:</th>
                    <td width="50%">
                        <input type="password" name="strContrasenaConf"  id="strContrasenaConf" class="campo_02" style="text-transform:uppercase;width: 100%;" onKeyPress="" onChange="this.value = mayuscula(this.value);">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" >
                        <br />
                        <input type="button" id="btnLogin" name="aceptar"  value="ACEPTAR" class="estilo" onClick="verificaCambioContrasena()">
                    </td>
                </tr>	
            </table>	
            <input type="hidden" id="bPassVencido" name="bPassVencido"  value="<%= bPassVencido%>" >
        </form>	
    </center>
</div>
<div id="mensaje" style="position: relative"></div>       
<script type="text/javascript">
    $(".campo_02").keyup(function(event) {
        if (event.keyCode == 13) {
            $("#btnLogin").click();
        }
    });
</script>
<%
    } catch (Exception ex) {
        bitacora.setStrServer(request.getHeader("host"));
        bitacora.setStrUbicacion(getServletContext().getRealPath(""));
        bitacora.setITipoBitacora(1);
        bitacora.setStrInformacion(ex, request.getServletPath());
        bitacora.grabaBitacora();
    } finally {
    }
%>

<jsp:include page="template/piePagina.jsp" />
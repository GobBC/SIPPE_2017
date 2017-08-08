<%-- 
    Document   : index
    Created on : Mar 9, 2015, 8:57:06 AM
    Author     : ugarcia
--%>

<%@page import="gob.gbc.estandar.ResultSQLEstandar"%>
<%@page import="gob.gbc.sql.ResultSQL"%>
<%@page contentType="text/html; charset=iso-8859-1" language="java" errorPage="error.jsp" %>
<%@page import = "java.util.Vector"%>

<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
%>
<%

    String strAccion = "", strUsuario = "", strContrasena = "", action = "";
    String strPass = "";
    String tipoDependencia = new String();
    
    int iResultado = 0;
    if (request.getParameter("tipoDependencia") != null) {
        tipoDependencia = (String) request.getParameter("tipoDependencia");
        session.setAttribute("tipoDependencia", tipoDependencia);
    }
    if (session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != "") {
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
    }
    if (request.getParameter("strAccion") != null) {
        strAccion = (String) request.getParameter("strAccion");
    }
    if (request.getParameter("strUsuario") != null) {
        strUsuario = (String) request.getParameter("strUsuario");
    }
    if (request.getParameter("strEncrypt") != null) {
        strContrasena = (String) request.getParameter("strEncrypt");
    }
    if (request.getParameter("strContrasena") != null) {
        strPass = (String) request.getParameter("strContrasena");
    }
    if (strAccion.equals("validar")) {
        try {
            ResultSQL resultSQL = new ResultSQL(tipoDependencia);
            //ResultSQLEstandar resultEstandar = new ResultSQLEstandar();
            resultSQL.setStrServer(((String) request.getHeader("Host")));
            resultSQL.setStrUbicacion(getServletContext().getRealPath("").toString());
            resultSQL.resultSQLConecta(tipoDependencia);
            Vector vecResultado = resultSQL.getResultSQLConsultaEstatusUsuario(strUsuario, strContrasena);
            //boolean blUsuarioExiste = resultEstandar.getExisteUsuario(strUsuario);
            resultSQL.resultSQLDesconecta();

            if (vecResultado.size() > 1) {
                session.setAttribute("strUsuario", strUsuario);
                session.setAttribute("strNombre", (String) vecResultado.get(1));
                session.setAttribute("strCorreo", (String) vecResultado.get(2));
                session.setAttribute("strRol", (String) vecResultado.get(5));
                session.setAttribute("strTipoCont", (String) vecResultado.get(4));
                session.setAttribute("ramoAsignado", (String) vecResultado.get(6));
                iResultado = Integer.parseInt((String) vecResultado.get(0), 10);
                if (iResultado == 1 || iResultado > 100) {
                    
                    if (!((String) vecResultado.get(4)).equals("3")) {//es proporcionada por el sistema
                        action = "usuarios/cambiaContrasena.jsp";
                    } else {
                        if (((String) vecResultado.get(3)).equals("1")) //es administrador de usuarios
                        {
                            action = "usuarios/usuarios.jsp";
                        } else {
                            action = "seleccionaAnio.jsp";  //es para imprimir constancias
                        }
                    }
                }
            }else if(vecResultado.size() > 0){
                iResultado = Integer.parseInt((String) vecResultado.get(0), 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<%if (iResultado == 1 || iResultado >= 100) {%>
<html>
    <body>
        <form name="form1" action="<%=(action)%>"></form>
        <script language="javascript">
            <% if (iResultado > 100) {%>
                alert("La contrase\u00f1a ingresada es autogenerada, queda(n) " + <%=(iResultado - 100)%> + " d\u00eda(s) para que venza, en la opci\u00f3n Cambiar Contrase\u00f1a de esta aplicaci\u00f3n puede hacer la actualizaci\u00f3n.")
            <% } else if (iResultado == 100) {%>
                alert("La contrase\u00f1a ingresada es autogenerada y ha vencido.")
                document.form1.action = "cambiaContrasena.jsp";
            <%
                    session.setAttribute("passVencido", true);
                }
            %>
                
                document.form1.submit();
                
       </script>
    </body>
</html>
<%		  }%>
<jsp:include page="template/encabezado.jsp" />
<center>
    <br/> 
    <br/> 
    <br/> 
    <form name="form1" method="post" action="inicioSesion.jsp">
        <input type="hidden" name="strAccion">
        <input type="hidden" name="strEncrypt">
        <input type="hidden" name="TipoDepTMP" value="<%=tipoDependencia%>">
        <table border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF" width="400">

            <tr>
                <th height="18" colspan="2" bgcolor="#CDE99E" class="submhead">POR FAVOR INTRODUZCA SU USUARIO Y CONTRASEÑA</th>
            </tr>
            <tr >
                <th height="20" bgcolor="#E5E8EF" class="submhead">USUARIO:</th>
                <td width="50%">
                    <input type="text" name="strUsuario" class="campo_02"  style="text-transform:uppercase;width: 100%;"
                           onKeyPress="" onChange="this.value = mayuscula(this.value);">
                </td>
            </tr>	
            <tr>
                <th height="20" bgcolor="#E5E8EF" class="submhead">CONTRASEÑA:</th>
                <td width="50%">
                    <input type="password" name="strContrasena" class="campo_02" style="text-transform:uppercase;width: 100%;"
                           onKeyPress="" onChange="this.value = mayuscula(this.value);">
                </td>
            </tr>	
            <tr>
                <td colspan="2" align="center" >
                    <br />
                    <input type="button" id="btnLogin" name="aceptar"  value="ACEPTAR" class="estilo" onClick="verifica_datos()">
                </td>
            </tr>					
        </table>	

        <br/><br/>
        <!--<a href="usuarios/solicitaContrasena.jsp"  class="submheadaz">SI OLVIDASTE TU CONTRASEÑA PRESIONA AQU&Iacute;</a>-->
        <br/><br/>	
        <% if (iResultado == 2) {%>
        <center><span class="alerta"><br/><br/><br/>NOMBRE DE USUARIO INV&Aacute;LIDO</span></center>
                <% }%>	
                <% if (iResultado == 3) {%>
        <center><span class="alerta"><br/><br/><br/>USUARIO INACTIVO</span></center>
                <% }%>	
                <% if (iResultado == 4) {%>
        <center><span class="alerta"><br/><br/><br/>CLAVE DE USUARIO Y CONTRASE&Ntilde;A NO COINCIDEN</span></center>
                <% }%>	
    </form>	
</center>
<script type="text/javascript">
    $(".campo_02").keyup(function(event){
        if(event.keyCode == 13){
            $("#btnLogin").click();
        }
    });
</script>
<jsp:include page="template/piePagina.jsp" />




<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="../error.jsp" %>
<%@ page import="gob.gbc.sql.ResultSQL"  %>
<%@ page import="gob.gbc.poa.Notificacion"  %>
<%@ page import="java.util.Vector"  %>

<%
    String strUsuario = "", strEstatus = "0", strAccion = "", strContrasena = "", strContrasenaSE = "", strAction2 = "", strCorreo = "";
    Vector vecResultado = new Vector();
    String tipoDependencia = new String();
    int iResultado = 0;
    if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
        tipoDependencia = (String) session.getAttribute("tipoDependencia");
    }
    if (request.getParameter("strUsuario") != null) {
        strUsuario = (String) request.getParameter("strUsuario");
    }
    if (request.getParameter("strAccion") != null) {
        strAccion = (String) request.getParameter("strAccion");
    }
    if (request.getParameter("strEncrypt") != null) {
        strContrasena = (String) request.getParameter("strEncrypt");
    }
    if (request.getParameter("strContrasena") != null) {
        strContrasenaSE = (String) request.getParameter("strContrasena");
    }

    if (strAccion.equals("solicitar")) {
        ResultSQL resultSql = new ResultSQL(tipoDependencia);
        resultSql.setStrServer(((String) request.getHeader("Host")));
        resultSql.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSql.resultSQLConecta(tipoDependencia);

        vecResultado = resultSql.getResultSQLSolicitaContrasena(strUsuario, strContrasena);
        resultSql.resultSQLDesconecta();
        if (vecResultado.size() > 0) {
            iResultado = Integer.parseInt((String) vecResultado.get(0), 10);
            if (iResultado == 2) {
                strAccion = "no_usuario";
            }
            if (iResultado == 3) {
                strAccion = "usuario_inactivo";
            }
            if (iResultado == 1) {
                strCorreo = (String) vecResultado.get(1);
                String strHostActual = "";
                if (request.getHeader("$WSSC") != null) {
                    strHostActual = (String) request.getHeader("$WSSC");
                }
                if (strHostActual.equals("")) {
                    if (request.getHeader("referer") != null) {
                        strHostActual = ((String) request.getHeader("referer")).substring(0, ((String) request.getHeader("referer")).indexOf(":"));
                    }
                }
                if (request.getHeader("Host") != null) {
                    strHostActual = strHostActual + "://" + (String) request.getHeader("Host");
                }
                String strMensaje = "";
                strMensaje = "<img src='" + strHostActual + "/oficial/template/imagenes/banner.jpg'/>"
                        + "<br /><font style='font-size:12px; font-family:Arial, Helvetica, sans-serif'>"
                        + " <br />El sistema ha generado de manera autom&aacute;tica y confidencial su contrase&ntilde;a de acceso. "
                        + " <br />Usuario:  <b>" + strUsuario + "</b>"
                        + " <br />Contrase&ntilde;a:  <b>" + strContrasenaSE + "</b>"
                        + " <br />Le sugerimos cambiarla al momento de acceder al sistema. Gracias. "
                        + " <br /><br/>Liga de Acceso: <a href='" + strHostActual + "/poa/'>Plan Opeativo Anual</a>"
                        + "</font>";
                Notificacion notificacion = new Notificacion();
                notificacion.setStrServer(((String) request.getHeader("Host")));
                notificacion.setStrUbicacion(getServletContext().getRealPath("").toString());
                notificacion.setStrAsunto("RECUPERACIÓN DE CONTRASEÑA");
                notificacion.setStrDestinatarios(strCorreo);
                notificacion.setStrMensaje(strMensaje);
                notificacion.enviaCorreo();
                strAccion = "mensaje";
            }
        }
    }


%>
<html>
    <head>
        <title>Plan Operativo Anual</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link href="../librerias/site.css" rel="stylesheet" type="text/css" />
        <link href="../librerias/portal.css" rel="stylesheet" type="text/css" />
        <link href="../librerias/oficial.css" rel="stylesheet" type="text/css" />
        <script src="../librerias/funciones.js"></script>
    </head>
    <body>
        <jsp:include page="../template/encabezadoSub.jsp" />
    <center>
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td height="19" class="estilo"><div align="center" class="submheadaz">CONSULTA DE ÍNDICE DEL PERIÓDICO OFICIAL<br />
                    </div></td>
            </tr>
        </table>
        <br/>
        <% if (!strAccion.equals("mensaje")) {%>
        <form name="form1" method="post">
            <input type="hidden" name="strAccion" value="<%=(strAccion)%>">
            <input type="hidden" name="strEncrypt">
            <input type="hidden" name="strContrasena">
            <table width="500" border="0" align="center">
                <tr bgcolor="#CDE99E">
                    <th height="18" colspan="2" bgcolor="#CDE99E" class="submhead">DATOS DEL USUARIO</th>
                </tr>
                <tr  class="printfont"  >
                    <td align="left" bgcolor="#E5E8EF" >CLAVE DE USUARIO: &nbsp;</td>
                    <td align="left" >
                        <input type="text" name="strUsuario"  style="text-transform:uppercase"  
                               onKeyPress="if (!letraynum(event.keyCode))
                                                    event.returnValue = false;"  class="campo_01"
                               onchange="this.value = mayuscula(this.value)" maxlength="20" value="<%=(strUsuario)%>">
                    </td>
                </tr>
            </table>	
            <% if (strAccion.equals("no_usuario")) { %>
            <br/><br/>
            <span class="alerta" class="submhead"><b>EL NOMBRE DE USUARIO NO ENCONTRADO. <br/>POR FAVOR VERIFIQUE.</b></span>
            <br/><br/>
            <% } %>
            <% if (strAccion.equals("usuario_inactivo")) { %>
            <br/><br/>
            <span class="alerta" class="submhead"><b>USUARIO INACTIVO. <br/> POR FAVOR COMUN&Iacute;QUESE CON SU ADMINISTRADOR DE SISTEMA.</b></span>
            <br/><br/>
            <% } %>
            <br/>
            <input type="button" name="regresar" value="REGRESAR" onClick="document.forms[0].action = '../inicioSesion.jsp';
                                document.forms[0].submit()" class="estilo">
            <input type="button" name="guardar" value="SOLICITAR CONTRASEÑA" onClick="valida_solicitar()" class="estilo">
        </form>
        <% } else {
        %>
        <span class="alerta" >
            <br/>SE HA ENVIADO LA CONTRASE&Ntilde;A AL CORREO ELECTR&Oacute;NICO INDICADO.
        </span>
        <span class="estilo"><br/>USUARIO: <b><%=(strUsuario)%></b></span>
        <br/><br/>
        <form name="form1" method="post">
            <input type="button" name="aceptar" value="ACEPTAR" 
                   onClick="document.form1.action = '../inicioSesion.jsp';
                                        document.form1.submit()" class="estilo">
        </form>
        <% }%>		
    </center>	
    <jsp:include page="../template/piePaginaSub.jsp" />
</body>	
</html>

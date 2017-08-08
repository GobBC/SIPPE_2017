<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="../error.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="gob.gbc.sql.ResultSQL"  %>
<%@ page import="gob.gbc.poa.Notificacion"  %>
<%@ page import="java.util.Vector"  %> 

<%
if(session.getAttribute("strUsuario")==null) 	
{ %>
	<html>
		<body>
			<form name="form1" method="post" action="../inicioSesion.jsp"></form>
				<script language="javascript">
					alert("Su Sesión ha Expirado. Por Favor, Introduzca Nuevamente su Usuario y Contraseña");
					document.form1.submit();
				</script>
		</body>
	</html>
<% } else {

	String strUsuario="", strCorreo="", strEstatus="0", strAccion="", strContrasena="", mensaje="", query="", strContrasenaSE="", strAction2="", strNombre="";
        int iResultado = 0;
        Vector vecResultado = new Vector();
        String tipoDependencia = new String();
	if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }

		if(request.getParameter("strUsuario")!=null) 	strUsuario=(String)request.getParameter("strUsuario");
		if(request.getParameter("strEncrypt")!=null)       strContrasena=(String)request.getParameter("strEncrypt");
		if(request.getParameter("strContrasena")!=null) strContrasenaSE=(String)request.getParameter("strContrasena");
		if(request.getParameter("strCorreo")!=null) 	strCorreo=(String)request.getParameter("strCorreo");
		if(request.getParameter("strEstatus")!=null) 	strEstatus=(String)request.getParameter("strEstatus");
		if(request.getParameter("strAccion")!=null) 	strAccion=(String)request.getParameter("strAccion");
		if(request.getParameter("strNombre")!=null) 	strNombre=(String)request.getParameter("strNombre");

        ResultSQL resultSql = new ResultSQL(tipoDependencia);
        resultSql.setStrServer(((String) request.getHeader("Host")));
        resultSql.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSql.resultSQLConecta(tipoDependencia);

	if(strAccion.equals("guardar")) {	
        iResultado = resultSql.getResultSQLInsertaUsuario(strUsuario, strContrasena, strCorreo, strEstatus, strNombre);
		if(iResultado == 2){
			strAccion="otro_usuario";	
			strUsuario="";
		}
		if(iResultado == 1){
            String strHostActual = "";
            if (request.getHeader("$WSSC") != null) {
                strHostActual = (String) request.getHeader("$WSSC");
            }
            if(strHostActual.equals("")) {
                if (request.getHeader("referer") != null) {
                    strHostActual = ((String) request.getHeader("referer")).substring(0,((String) request.getHeader("referer")).indexOf(":"));
                }
            }
            if (request.getHeader("Host") != null) {
                strHostActual = strHostActual + "://" + (String) request.getHeader("Host");
            }

            String strMensaje = "";
            strMensaje = "<img src='"+strHostActual+"/oficial/template/imagenes/banner.jpg'/>" +
                          "<br /><font style='font-size:12px; font-family:Arial, Helvetica, sans-serif'>"+
                          " <br />El sistema ha generado de manera autom&aacute;tica y confidencial su contrase&ntilde;a de acceso. " +
                          " <br />Usuario:  <b>"+strUsuario+"</b>"+
                          " <br />Contrase&ntilde;a:  <b>"+strContrasenaSE+"</b>"+
                          " <br />Le sugerimos cambiarla al momento de acceder al sistema. Gracias. "+
                          " <br /><br/>Liga de Acceso: <a href='"+strHostActual+"/poa/'>Plan Operativo Anual</a>"+
                          "</font>";
            Notificacion notificacion = new Notificacion();
            notificacion.setStrServer(((String) request.getHeader("Host")));
            notificacion.setStrUbicacion(getServletContext().getRealPath("").toString());
            notificacion.setStrAsunto("BIENVENIDO AL SISTEMA");
            notificacion.setStrDestinatarios(strCorreo);
            notificacion.setStrMensaje(strMensaje);
            notificacion.enviaCorreo();
            strAccion="mensaje";
		}
	}//if guardar
        resultSql.resultSQLDesconecta();
%>
<html>
    <head>
        <title>Sistema de Captura de Indices del Periódico Oficial</title>
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
              <td height="19" class="estilo"><div align="center" class="submheadaz">ALTA DE USUARIOS PLAN OPERATIVO ANUAL<br />
              </div></td>
            </tr>
          </table>
	<br/>
	<% if(!strAccion.equals("mensaje")) { %>
  <form name="form1" method="post">
	<input type="hidden" name="strAccion" value="<%=(strAccion)%>">
	<input type="hidden" name="strEncrypt">
	<input type="hidden" name="strContrasena">
      <table width="500" border="0" align="center">
        <tr bgcolor="#003366">
          <th height="18" colspan="2" bgcolor="#CDE99E" class="submhead">DATOS DEL USUARIO</th>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF"  width="200" >CLAVE DE USUARIO: &nbsp;</td>
          <td align="left" >
		  			<input type="text" name="strUsuario"  style="text-transform:uppercase"  
					onKeyPress="if(!letraynum(event.keyCode)) event.returnValue=false;"  class="campo_01"
					onchange="this.value=mayuscula(this.value)" maxlength="20" value="<%=(strUsuario)%>">
			</td>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >NOMBRE: &nbsp;</td>
          <td align="left" >
		  			<input type="text" name="strNombre"  style="text-transform:uppercase"  
					onKeyPress="if(!textoynum(event.keyCode)) event.returnValue=false;"  class="campo_01"
					onchange="this.value=mayuscula(this.value)"  maxlength="50" value="<%=(strNombre)%>">
			</td>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >ESTATUS: &nbsp;</td>
          <td align="left" >
		  			<select name="strEstatus" class="campo_01">
						<option <% if(strEstatus.equals("0")) out.print("selected='selected'"); %> value="0">SELECCIONE ESTATUS</option>
						<option <% if(strEstatus.equals("1")) out.print("selected='selected'"); %>value="1">ACTIVO</option>
						<option <% if(strEstatus.equals("2")) out.print("selected='selected'"); %>value="2">INACTIVO</option>
					</select>
			</td>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >CORREO ELECTR&Oacute;NICO: &nbsp;</td>
          <td align="left" >
		  			<input type="text" name="strCorreo" maxlength="100" value="<%=(strCorreo)%>" class="campo_01">
			</td>
        </tr>
	</table>	
	<br/>
	<% if(strAccion.equals("otro_usuario")) { %>
	<br/><br/>
		<span class="alerta"><b>EL NOMBRE DE USUARIO YA EXISTE. <br/>POR FAVOR PROPORCIONE OTRO.</b></span>
	<br/><br/>
	<% } %>
	<br/>
			<input type="button" name="regresar" value="REGRESAR" onClick="document.forms[0].action='usuarios.jsp'; document.forms[0].submit()" class="estilo">
			<input type="button" name="guardar" value="GUARDAR DATOS" onClick="valida_usuario()" class="estilo">
			</form>
	<% } else { %>
	<br/>
		<b>
			<span class="alerta">
				<br/>SE HA ENVIADO LA CONTRASE&Ntilde;A AL CORREO ELECTR&Oacute;NICO INDICADO. 
			</span>			
		</b>	
			<br/><br/>USUARIO: <b><%=(strUsuario)%></b>
			<br/>CORREO ELECTR&Oacute;NICO: <b><%=(strCorreo)%></b>
<br/><br/>
		<form name="form1" method="post">
				<input type="button" name="aceptar" value="ACEPTAR" 
				onClick="document.form1.action='usuarios.jsp'; document.form1.submit()" class="estilo">
</form>
	<% } %>		
		</center>	
        <jsp:include page="../template/piePaginaSub.jsp" />
	</body>	
</html>
	<% 
	} 
	%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="../error.jsp" %>
<%@ page import="gob.gbc.sql.ResultSQL"  %>
<%@ page import="java.util.Vector"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%if(session.getAttribute("strUsuario")==null) 	{ %>
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
        String tipoDependencia = new String();
	String strUsuario="", strCorreo="", strEstatus="0", strAccion="", strNombre="";
        boolean blResultado = false;
        Vector vecResultado = new Vector();
	if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
	
		if(request.getParameter("strUsuario")!=null) 	strUsuario=(String)request.getParameter("strUsuario");
		if(request.getParameter("strCorreo")!=null) 	strCorreo=(String)request.getParameter("strCorreo");
		if(request.getParameter("strEstatus")!=null) 	strEstatus=(String)request.getParameter("strEstatus");
		if(request.getParameter("strAccion")!=null) 	strAccion=(String)request.getParameter("strAccion");
		if(request.getParameter("strNombre")!=null) 	strNombre=(String)request.getParameter("strNombre");

        ResultSQL resultSql = new ResultSQL(tipoDependencia);
        resultSql.setStrServer(((String) request.getHeader("Host")));
        resultSql.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSql.resultSQLConecta(tipoDependencia);
		if(strAccion.equals("guardar")) {
            blResultado = resultSql.getResultSQLActualizaDatosUsuario(strUsuario, strCorreo, strEstatus, strNombre);
        } else {
            vecResultado = resultSql.getResultSQLConsultaDatosUsuario(strUsuario);
        }
        resultSql.resultSQLDesconecta();



	if(strAccion.equals("guardar") && blResultado) {
%>
	<html>
		<body>
			<form name="form1" method="post" action="usuarios.jsp"></form>
				<script language="javascript">
					document.form1.submit();
				</script>
		</body>
	</html>
<% }
    if(vecResultado.size()>0){
        vecResultado = (Vector)vecResultado.get(0);
        strNombre=(String)vecResultado.get(0);
        strEstatus=(String)vecResultado.get(1);
        strCorreo=(String)vecResultado.get(2);
        blResultado = true;
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
              <td height="19" class="estilo"><div align="center" class="submheadaz">CONSULTA DE USUARIOS DEL PLAN OPERATIVO ANUAL<br />
              </div></td>
            </tr>
          </table>
	<br/>
<% if(blResultado) { %>
  <form name="form1" method="post">
	<input type="hidden" name="strAccion" value="<%=(strAccion)%>">
	<input type="hidden" name="strUsuario" value="<%=(strUsuario)%>">
      <table width="500" border="0" align="center">
        <tr bgcolor="#003366">
          <th height="18" colspan="2" bgcolor="#CDE99E" class="submhead">DATOS DEL USUARIO</th>
        </tr>
        <tr  class="printfont"  >
          <td align="left" bgcolor="#E5E8EF" >CLAVE DE USUARIO: &nbsp;</td>
          <td align="left" ><%=(strUsuario)%></td>
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
			<input type="button" name="regresar" value="REGRESAR" onClick="document.forms[0].action='usuarios.jsp'; document.forms[0].submit()" class="estilo">
			<input type="button" name="guardar" value="GUARDAR DATOS" onClick="valida_usuario_actualiza()" class="estilo">
			</form>
<br/><br/>
<% } else { %>
		<span class="alerta"><b>USUARIO NO ENCONTRADO. <br/>POR FAVOR VERIFIQUE.</b></span>
	<br/>
		<form name="form1" method="post">
			<input type="button" name="regresar" value="REGRESAR" onClick="document.forms[0].action='usuarios.jsp'; document.forms[0].submit()" class="estilo">
			</form>
<br/><br/>
<% } %>
		</center>	
        <jsp:include page="../template/piePaginaSub.jsp" />
</body>	
</html>
<% } %>
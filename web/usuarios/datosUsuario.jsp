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

	String  strUsuario="";	
        String tipoDependencia = new String();
	Vector vecResultado = new Vector();
        if(session.getAttribute("tipoDependencia") != null && session.getAttribute("tipoDependeica") != ""){
            tipoDependencia = (String) session.getAttribute("tipoDependencia");
        }
	if(request.getParameter("strUsuario")!=null) strUsuario=(String)request.getParameter("strUsuario");
	
        ResultSQL resultSql = new ResultSQL(tipoDependencia);
        resultSql.setStrServer(((String) request.getHeader("Host")));
        resultSql.setStrUbicacion(getServletContext().getRealPath("").toString());
        resultSql.resultSQLConecta(tipoDependencia);

        vecResultado = resultSql.getResultSQLConsultaUsuario(strUsuario);
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
              <td height="19" class="estilo"><div align="center" class="submheadaz">CONSULTA DE ÍNDICE DEL PERIÓDICO OFICIAL<br />
              </div></td>
            </tr>
          </table>
	<br/>
<% if(vecResultado.size()>0) {
    vecResultado = (Vector)vecResultado.get(0);
    %>
      <table width="500" border="0" align="center">
        <tr bgcolor="#003366">
          <th height="18" colspan="2" bgcolor="#CDE99E" class="submhead">DATOS DEL USUARIO</th>
        </tr>
        <tr  class="estilo"  >
          <td align="left" bgcolor="#E5E8EF"  width="200">CLAVE DE USUARIO: &nbsp;</td>
          <td align="left" ><%=(strUsuario)%></td>
        </tr>
        <tr  class="estilo"  >
          <td align="left" bgcolor="#E5E8EF" >NOMBRE: &nbsp;</td>
          <td align="left" ><%=(vecResultado.get(0))%>	</td>
        </tr>
        <tr  class="estilo"  >
          <td align="left" bgcolor="#E5E8EF" >ESTATUS: &nbsp;</td>
          <td align="left" ><%=(vecResultado.get(1))%>	</td>
        </tr>
        <tr  class="estilo"  >
          <td align="left" bgcolor="#E5E8EF" >CORREO ELECTR&Oacute;NICO: &nbsp;</td>
          <td align="left" ><%=(vecResultado.get(2))%>	</td>
        </tr>
	</table>	
<% } else { %>
		<span class="alerta"><b>USUARIO NO ENCONTRADO. <br/>POR FAVOR VERIFIQUE.</b></span>
<% } %>
	<br/>
		<form name="form1" method="post">
			<input type="button" name="regresar" value="REGRESAR" onClick="document.forms[0].action='usuarios.jsp'; document.forms[0].submit()" class="estilo">
			</form>
<br/><br/>
		</center>	
        <jsp:include page="../template/piePaginaSub.jsp" />
	</body>	
</html>
	<% 
	} 
	%>
